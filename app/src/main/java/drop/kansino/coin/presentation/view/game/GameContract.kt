package drop.kansino.coin.presentation.view.game

import android.graphics.Color
import android.graphics.Point
import androidx.annotation.ColorInt

data class GameState(
    val coins: List<Coin> = emptyList(),
    val bullets: List<GliderBullet> = emptyList(),
    val glider: Glider = Glider(0f, 0f),
    val score: Int = 0
) {

    class GliderBullet(
        val x: Float,
        val y: Float,
        @ColorInt val color: Int
    )

    class Coin(
        val x: Float,
        val y: Float
    )

    data class Glider(
        val x: Float,
        val y: Float
    )
}

sealed interface GameSideEffect {
    data class FinishGame(
        val score: Int
    ) : GameSideEffect
}
