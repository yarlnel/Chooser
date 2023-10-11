package com.PINUP.platforms.presentation.view.chooser

sealed interface ChooserSideEffect {
    data class GameFinished(
        val score: Int
    ) : ChooserSideEffect
}