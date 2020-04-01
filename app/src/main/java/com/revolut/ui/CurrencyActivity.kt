package com.revolut.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.revolut.R
import com.revolut.di.factory.ViewModelFactory
import dagger.android.AndroidInjection
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_currency.*
import javax.inject.Inject

class CurrencyActivity : AppCompatActivity() {
    lateinit var currencyRatesAdapter: RatesAdapter

    private var newState: Int = SCROLL_STATE_IDLE

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var isReorder = -1

    private val model: CurrencyRatesViewModel by viewModels { viewModelFactory }
    val editChanges = BehaviorSubject.createDefault(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        list.setHasFixedSize(true)
        currencyRatesAdapter = RatesAdapter { updateList(it) }
        list.adapter = currencyRatesAdapter
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                this@CurrencyActivity.newState = newState
            }
        })
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(model.currencyRates
            .toFlowable(BackpressureStrategy.LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (newState == SCROLL_STATE_IDLE && !editChanges.value!!) {
                    if (this.isReorder != -1) {
                        currencyRatesAdapter.notifyItemMoved(isReorder, 0)
                        currencyRatesAdapter.notifyItemChanged(isReorder)
                        currencyRatesAdapter.notifyItemRangeChanged(1, it.size)
                        isReorder = -1
                        list.scrollToPosition(0)
                    } else {
                        currencyRatesAdapter.updateCurrencyRates(it)
                    }
                }
            })
        model.getCurrencyRates()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
        model.stop()
    }

    private fun updateList(index: Int) {
        isReorder = index
        model.reorderList(currencyRatesAdapter.getItems())
    }
}

