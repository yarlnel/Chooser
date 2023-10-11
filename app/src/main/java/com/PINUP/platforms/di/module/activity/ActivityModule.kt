package com.PINUP.platforms.di.module.activity

import com.PINUP.platforms.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun mainActivity() : MainActivity
}
