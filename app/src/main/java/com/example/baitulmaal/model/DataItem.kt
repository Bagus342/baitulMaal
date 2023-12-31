package com.example.baitulmaal.model

sealed class DataItem(val type: ViewType ) {
    data class DataTitle(val title: String): DataItem(ViewType.TITLE)
    data class DataList(val items: Savings) : DataItem(ViewType.LIST)

    enum class ViewType { TITLE, LIST }
}
