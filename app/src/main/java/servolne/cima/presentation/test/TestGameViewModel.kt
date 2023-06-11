package servolne.cima.presentation.test

import org.orbitmvi.orbit.syntax.simple.intent
import servolne.cima.presentation.common.game.RenderTimeController
import servolne.cima.presentation.common.game.RenderViewModel

class TestGameViewModel(
    private val width: Int,
    private val height: Int,
    private val gemWidth: Int,
    private val gemHeight: Int
    timeController: RenderTimeController,

) : RenderViewModel<TestGameState, TestGameSideEffect>(
    TestGameState(),
    timeController
) {
    private var deltaY: Int = (height * 0.02).toInt()
    private var framesToNewGem = 36

    fun onFrame() = intent {
        val newGems = state.gems.map { gem ->
            gem.y += deltaY
        }
        if (currentFrame % framesToNewGem == 0) {
            generateGem()
        }

        if (currentFrame % 500 == 0) {
            framesToNewGem -= 1
        }
    }

    private fun generateGem() = intent {

        val x = (0 until (width - gemWidth)).random()
        val y = -
        balls += Ball(x, y)
    }
}
