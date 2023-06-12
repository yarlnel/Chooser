package drop.kansino.coin.presentation.test

import android.graphics.PointF

sealed interface TestGameSideEffect {

    data class GameFinished(
        val score: Int
    ) : TestGameSideEffect
}

data class TestGameState(
    val gems: List<Gem> = emptyList(),
    val linePoints: List<PointF> = emptyList(),
    val score: Int = 0,
    val gemLoose: Int = 0
) {

    class Gem(
        var x: Int,
        var y: Int,
        val color: Color
    ) {
        enum class Color {
            YELLOW, RED, GREEN, BLUE
        }
    }
}
