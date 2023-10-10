package betesporte.color.match.presentation.view.chooser

sealed interface ChooserSideEffect {
    data class GameFinished(
        val score: Int
    ) : ChooserSideEffect
}