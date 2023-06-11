package servolne.cima.presentation.test

import android.graphics.Point

sealed interface TestGameSideEffect

data class TestGameState(
    val gems: List<Gem> = emptyList(),
    val line: Line = Line()
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

    class Line(
        val points: List<Point> = emptyList()
    )
}
