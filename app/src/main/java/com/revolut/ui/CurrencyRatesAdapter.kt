package com.revolut.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.revolut.R
import com.revolut.dto.CurrencyModel
import com.revolut.ui.CurrencyRatesAdapter.RatesViewHolder

class CurrencyRatesAdapter(
    private var list: Array<CurrencyModel>,
    private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<RatesViewHolder>() {
    inner class RatesViewHolder(view: View) : ViewHolder(view) {
        val currencyCode: TextView = view.findViewById(R.id.currencyCode)
        val currencyName: TextView = view.findViewById(R.id.currencyName)
        val currencyValue: EditText = view.findViewById(R.id.currencyValue)

        init {
            view.setOnClickListener { itemClick.invoke(layoutPosition) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RatesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.currency_row, parent, false)
    )

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        holder.currencyCode.text = list[position].currency
        holder.currencyValue.setText(list[position].rate.toString())
        holder.currencyName.text = list[position].currencyText
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateCurrencyRates(list: Array<CurrencyModel>) {
        this.list = list
        notifyDataSetChanged()
    }
}