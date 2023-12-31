package com.example.baitulmaal.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.baitulmaal.R
import com.example.baitulmaal.model.Savings
import com.example.baitulmaal.viewmodel.SavingsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mSavingsViewModel: SavingsViewModel

    private var isUpdate: Boolean = false
    private var idSaving: Int = 0
    private var moneySaving: Double = 0.0
    private var noteSaving: String = String()
    private var dateSaving: String = String()
    private var categorySaving: String = String()

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
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.editCategory)
        val items = resources.getStringArray(R.array.categoryArray)
        mSavingsViewModel = ViewModelProvider(this)[SavingsViewModel::class.java]
        if (spinner != null) {
            val adapter = ArrayAdapter(view.context,
                R.layout.dropdown_category, items
                )
            spinner.adapter = adapter
            if (isUpdate) {
                val getPosition: Int = adapter.getPosition(categorySaving)
                spinner.setSelection(getPosition)
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
        if (isUpdate) {
            header.text = "Update Data"
            editIdr.setText(setFormatEdit(moneySaving))
            editCatatan.setText(noteSaving)
            deleteItem.setOnClickListener {
                val savings = Savings(moneySaving, noteSaving, categorySaving, dateSaving, idSaving)
                mSavingsViewModel.deleteItemSavings(savings)
                dismiss()
                Toast.makeText(activity, "Successfully Deleting Data !", Toast.LENGTH_SHORT).show()
            }
        } else {
            deleteItem.visibility = View.INVISIBLE
        }

        submitButton.setOnClickListener {
            addData()
        }

        editIdr.addTextChangedListener(object: TextWatcher {
            var current = editIdr.text.toString().trim()

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if(!p0.toString().equals(current)) {
                    editIdr.removeTextChangedListener(this)
                    var replace = p0.toString().replace("[Rp. ]".toRegex(), "")
                    if (!replace.isEmpty()) {
                        current = setFormatEdit(replace.toDouble())
                    } else {
                        current = ""
                    }
                    moneySaving = replace.toDouble()
                    editIdr.setText(current)
                    editIdr.setSelection(current.length)
                    editIdr.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(p0: Editable) {
            }

        })
    }

    private fun setFormatEdit(number: Double): String {
        var locale = Locale("IND", "ID")
        var numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(locale)
        var formatIdr = numberFormat.format(number)
        var split = formatIdr.split(",")
        var length = split[0].length
        return split[0].substring(0,2)+". "+split[0].substring(2, length)
    }

    fun setDataUpdate(item: Savings) {
        isUpdate = true
        idSaving = item.id
        moneySaving = item.moneySaving
        categorySaving = item.categorySaving
        noteSaving = item.noteSaving
        dateSaving = item.dateSaving
    }

    private fun addData() {
        val money = editIdr.text.toString()
        val note = editCatatan.text.toString()
        val category = editCategory.selectedItem.toString()

        if (money.isNotEmpty() && note.isNotEmpty()) {
            if (isUpdate) {
                val updateSavings = Savings(moneySaving, note, category, dateSaving, idSaving)

                mSavingsViewModel.updateData(updateSavings)

                Toast.makeText(activity, "Successfully updating data !", Toast.LENGTH_SHORT).show()
                dismiss()
            } else {
                val savings = Savings(moneySaving, note, category, getStringCurrentDate())

                mSavingsViewModel.addData(savings)

                Toast.makeText(activity, "Successfully creating data !", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        } else {
            Toast.makeText(activity, "Please Insert Data !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStringCurrentDate(): String {
        val current: LocalDate = LocalDate.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd LLLL yyy")
        return current.format(formatter)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}