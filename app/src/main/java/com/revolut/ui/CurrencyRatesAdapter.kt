package com.revolut.ui

import android.text.TextUtils
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
import kotlin.math.roundToInt

class CurrencyRatesAdapter(
    private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<RatesViewHolder>() {
    private var list: Array<CurrencyModel> = emptyArray()

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
        if (!TextUtils.equals(
                holder.currencyValue.text,
                list[position].rate.roundToInt().toString()
            )
        )
            holder.currencyValue.setText(list[position].rate.roundToInt().toString())
        holder.currencyName.text = list[position].currencyText
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateCurrencyRates(list: Array<CurrencyModel>) {
        val empty = this.list.isEmpty()
        this.list = list
        if (empty) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(0, list.size)
        }
    }

    fun updateCurrencyRates(index: Int, list: Array<CurrencyModel>) {
        this.list = list
        notifyItemMoved(index, 0)
        notifyItemRangeChanged(1, index)
    }
}