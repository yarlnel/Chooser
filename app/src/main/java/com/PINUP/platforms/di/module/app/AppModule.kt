package com.PINUP.platforms.di.module.app

import com.PINUP.platforms.di.module.activity.ActivityModule
import com.PINUP.platforms.di.module.fragment.FragmentModule
import com.PINUP.platforms.di.module.navigation.NavigationModule
import dagger.Module

@Module(includes = [
    ActivityModule::class,
    FragmentModule::class,
    NavigationModule::class
])
interface AppModule
