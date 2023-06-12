package drop.kansino.coin.presentation.navigation.graph

import com.github.terrakok.cicerone.androidx.FragmentScreen
import drop.kansino.coin.presentation.fragments.GameFragment

object Screens {


    fun Game() = FragmentScreen {
        GameFragment()
    }
}