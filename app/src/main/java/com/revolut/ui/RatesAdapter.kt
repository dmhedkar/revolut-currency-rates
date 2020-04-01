package com.revolut.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.revolut.R
import com.revolut.cache.CurrencyRateEntity
import kotlin.math.roundToInt

class RatesAdapter(
    private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<RatesAdapter.RatesViewHolder>() {
    private var list: List<CurrencyRateEntity> = emptyList()

    private var defaultMultiplier = 100


    inner class RatesViewHolder(view: View) : ViewHolder(view) {
        val currencyCode: TextView = view.findViewById(R.id.currencyCode)
        val currencyName: TextView = view.findViewById(R.id.currencyName)
        val currencyValue: EditText = view.findViewById(R.id.currencyValue)

        init {
            view.setOnClickListener {
                if (layoutPosition > 0) {
                    moveToTop(layoutPosition)
                    itemClick.invoke(layoutPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RatesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.currency_row, parent, false)
    )

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        holder.currencyCode.text = list[position].currencyCode
        holder.currencyValue.setText(
            (list[position].currencyRate * defaultMultiplier).roundToInt().toString()
        )
        holder.currencyName.text = list[position].currencyName

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateCurrencyRates(list: List<CurrencyRateEntity>) {
        val empty = this.list.isEmpty()
        this.list = list
        if (empty) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(0, list.size)
        }
    }

    fun moveToTop(index: Int) {
        val mutableList = list.toMutableList()
        val element = mutableList.removeAt(index)
        defaultMultiplier = (defaultMultiplier * element.currencyRate).toInt()
        mutableList.add(0, element)
        list = mutableList
    }

    fun getItems() = list
}