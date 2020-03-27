package com.revolut.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.revolut.di.factory.ViewModelFactory
import com.revolut.di.factory.ViewModelKey
import com.revolut.ui.CurrencyRatesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyRatesViewModel::class)
    abstract fun bindCurrencyRatesViewModel(currencyRatesViewModel: CurrencyRatesViewModel): ViewModel
}