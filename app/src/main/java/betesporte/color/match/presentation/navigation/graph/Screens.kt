package betesporte.color.match.presentation.navigation.graph

import com.github.terrakok.cicerone.androidx.FragmentScreen
import betesporte.color.match.presentation.fragments.GameFragment

object Screens {


    fun Game() = FragmentScreen {
        GameFragment()
    }
}