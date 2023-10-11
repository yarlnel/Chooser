package com.PINUP.platforms.di.module.fragment

import com.PINUP.platforms.presentation.fragments.ChooserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.PINUP.platforms.presentation.fragments.GameFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun gameFragment(): GameFragment

    @ContributesAndroidInjector
    fun chooserFragment(): ChooserFragment
}
