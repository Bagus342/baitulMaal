package com.example.baitulmaal.fragments

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baitulmaal.R
import com.example.baitulmaal.adapter.DateAdapter
import com.example.baitulmaal.model.*
import com.example.baitulmaal.viewmodel.SavingsViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_pemasukan.*
import kotlinx.android.synthetic.main.fragment_pemasukan.view.*
import java.text.DecimalFormat

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PemasukanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var savingsViewModel: SavingsViewModel
    private lateinit var bottomSheetFragment: BottomSheetFragment
    val adapter = DateAdapter()

    var group: MutableList<Group> = mutableListOf()
    var dateList: MutableList<String> = mutableListOf()
    var saving: MutableList<Savings> = mutableListOf()
    var pemasukan: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pemasukan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = recyclerPemasukan

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        savingsViewModel = ViewModelProvider(this)[SavingsViewModel::class.java]

        savingsViewModel.getAllData.observe(viewLifecycleOwner, Observer { savings ->
            val dataItem = mutableListOf<DataItem>()
            dataItem.clear()
            setNull()
            savings.forEach {
                if (it.categorySaving == "Uang Masuk") {
                    dateList += it.dateSaving
                    pemasukan += it.moneySaving
                }
            }
            dateList.distinct().forEach { dates ->
                group += Group( dates, savings.filter { i ->
                    i.dateSaving == dates && i.categorySaving == "Uang Masuk"
                })
            }
            group.forEach {
                dataItem.add(DataItem.DataTitle(it.title))
                it.savings.forEach { s ->
                    dataItem.add(DataItem.DataList(s))
                }
            }
            adapter.items = dataItem
            setTotalPemasukan(pemasukan)
        })

        adapter.setOnClickListener(object: DateAdapter.OnClickListener {
            override fun onClick(position: Int, model: Savings) {
                bottomSheetFragment = BottomSheetFragment()
                bottomSheetFragment.setDataUpdate(model)
                bottomSheetFragment.show(parentFragmentManager, "")
            }

        })
    }

    private fun setNull () {
        group.clear()
        dateList.clear()
        pemasukan = 0.0
    }

    private fun setTotalPemasukan(amount: Double) {
        val spannableString: SpannableString = SpannableString(
            "Rp." +
                    " " +
                    DecimalFormat("#,###").format(amount) +
                    ",-"
        )
        spannableString.setSpan(RelativeSizeSpan(0.6f), 1, 1, 0)
        totalPemasukanFr.text = spannableString
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PemasukanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}