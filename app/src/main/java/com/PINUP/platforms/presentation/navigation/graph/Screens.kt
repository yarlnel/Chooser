package com.PINUP.platforms.presentation.navigation.graph

import com.PINUP.platforms.presentation.fragments.ChooserFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun Game() = FragmentScreen {
        ChooserFragment()
    }
}