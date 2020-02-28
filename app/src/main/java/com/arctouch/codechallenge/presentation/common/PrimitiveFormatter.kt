package br.com.softbuilder.appplus2.presentation.common

import java.text.DecimalFormat

object PrimitiveFormatter {
    val dateFormat = "dd/MM/yyyy"

    val moneyFormatter by lazy {
        DecimalFormat("R$ 0.00")
    }

    val decimalFormatter by lazy {
        DecimalFormat("###,###,###,##0.00")
    }

    val integerFormatter by lazy {
        DecimalFormat("###,###,###,##0")
    }

    fun format(value : Int?) : String {
        return value?.let { integerFormatter.format(it) } ?: ""
    }

    fun format(value : Boolean?) : String {
        return value?.let { if(it) "Sim" else "Não" } ?: ""
    }

    fun formatMoney(value : Double?) : String {
        return if(value != null) moneyFormatter.format(value) else ""
    }
}