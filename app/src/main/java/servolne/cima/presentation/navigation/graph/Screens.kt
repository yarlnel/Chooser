package servolne.cima.presentation.navigation.graph

import com.github.terrakok.cicerone.androidx.FragmentScreen
import servolne.cima.presentation.fragments.GameFragment

object Screens {


    fun Game() = FragmentScreen {
        GameFragment()
    }
}