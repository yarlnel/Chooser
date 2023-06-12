package servolne.cima.presentation.test
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import servolne.cima.presentation.common.game.RenderViewModel

class TestGameViewModel(
    private val width: Int,
    private val height: Int,
    private val assetsLoader: TestGameAssetsLoader
) : RenderViewModel<TestGameState, TestGameSideEffect>(TestGameState()) {
    private var deltaY: Int = (height * 0.02).toInt()
    private var framesToNewGem = 36

    private val gemWidth by lazy {
        assetsLoader.greenGem.width
    }

    private val gemHeight by lazy {
        assetsLoader.greenGem.height
    }

    override fun onFrame() = intent {
        val newGems = state.gems.map { gem ->
            gem.apply { y += deltaY }
        }

        reduce {
            state.copy(gems = newGems)
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
        val y = -gemHeight
        val colors = TestGameState.Gem.Color.values()
        val color = colors[(1 until colors.size).random()]
        val newGem = TestGameState.Gem(
            x = x,
            y = y,
            color = color
        )

        reduce {
            state.copy(
                gems = state.gems + newGem
            )
        }
    }
}
