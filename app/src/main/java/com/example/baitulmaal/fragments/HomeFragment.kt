package com.example.baitulmaal.fragments

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baitulmaal.R
import com.example.baitulmaal.adapter.DateAdapter
import com.example.baitulmaal.model.*
import com.example.baitulmaal.viewmodel.SavingsViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.DecimalFormat

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var savingsViewModel: SavingsViewModel
    private lateinit var bottomSheetFragment: BottomSheetFragment

    val adapter = DateAdapter()
    var group: MutableList<Group> = mutableListOf()
    var dateList: MutableList<String> = mutableListOf()

    var pemasukan: Double = 0.0
    var pengeluaran: Double = 0.0

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.itemsView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        savingsViewModel = ViewModelProvider(this)[SavingsViewModel::class.java]

        savingsViewModel.getAllData.observe(viewLifecycleOwner, Observer { savings ->
            val dataItem = mutableListOf<DataItem>()
            setNull()
            dataItem.clear()
            savings.forEach {
                dateList += it.dateSaving
                if (it.categorySaving == "Uang Masuk") {
                    pemasukan += it.moneySaving
                } else {
                    pengeluaran += it.moneySaving
                }
            }
            dateList.distinct().forEach { dates ->
                group += Group( dates, savings.filter { i ->
                    i.dateSaving == dates
                })
            }
            group.forEach {
                dataItem.add(DataItem.DataTitle(it.title))
                it.savings.forEach { s ->
                    dataItem.add(DataItem.DataList(s))
                }
            }
            adapter.items = dataItem
            setTotal(pemasukan - pengeluaran)
            setTotalPemasukan(pemasukan)
            setTotalPengeluaran(pengeluaran)
        })

        adapter.setOnClickListener(object: DateAdapter.OnClickListener {
            override fun onClick(position: Int, model: Savings) {
                bottomSheetFragment = BottomSheetFragment()
                bottomSheetFragment.setDataUpdate(model)
                bottomSheetFragment.show(parentFragmentManager, "")
            }

        })

        reset.setOnClickListener {
            savingsViewModel.deleteData()
            Toast.makeText(context, "Successfully reset data !", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setNull () {
        group.clear()
        dateList.clear()
        pemasukan = 0.0
        pengeluaran = 0.0
    }

    private fun setTotalPengeluaran(amount: Double) {
        val spannableString: SpannableString = SpannableString(
            "Rp." +
                    " " +
                    DecimalFormat("#,###").format(amount) +
                    ",-"
        )
        spannableString.setSpan(RelativeSizeSpan(0.6f), 1, 1, 0)
        pengeluaranIdr.text = spannableString
    }

    private fun setTotal(amount: Double) {
        val spannableString: SpannableString = SpannableString(
            "Rp." +
                    " " +
                    DecimalFormat("#,###").format(amount) +
                    ",-"
        )
        spannableString.setSpan(RelativeSizeSpan(0.6f), 1, 1, 0)
        totalHome.text = spannableString
    }

    private fun setTotalPemasukan(amount: Double) {
        val spannableString: SpannableString = SpannableString(
            "Rp." +
                    " " +
                    DecimalFormat("#,###").format(amount) +
                    ",-"
        )
        spannableString.setSpan(RelativeSizeSpan(0.6f), 1, 1, 0)
        pemasukanIdr.text = spannableString
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}