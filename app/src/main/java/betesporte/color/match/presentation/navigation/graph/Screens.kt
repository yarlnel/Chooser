package betesporte.color.match.presentation.navigation.graph

import betesporte.color.match.presentation.fragments.ChooserFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen
import betesporte.color.match.presentation.fragments.GameFragment

object Screens {

    fun Game() = FragmentScreen {
        ChooserFragment()
    }
}