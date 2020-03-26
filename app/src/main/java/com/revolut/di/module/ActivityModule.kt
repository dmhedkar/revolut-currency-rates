package com.revolut.di.module

import com.revolut.ui.CurrencyActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeCurrencyActivity(): CurrencyActivity
}