package servolne.cima.presentation.test
import android.graphics.PointF
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import servolne.cima.presentation.common.game.RenderViewModel

class TestGameViewModel(
    private val assetsLoader: TestGameAssetsLoader
) : RenderViewModel<TestGameState, TestGameSideEffect>(TestGameState()) {
    private var width: Int = 0
    private var height: Int = 0

    fun setGameSize(width: Int, height: Int) {
        this@TestGameViewModel.width = width
        this@TestGameViewModel.height = height
        deltaY = (height * 0.02).toInt()
    }

    private var deltaY: Int = (height * 0.02).toInt()
    private var framesToNewGem = 56

    private val gemWidth by lazy {
        assetsLoader.greenGem.width
    }

    private val gemHeight by lazy {
        assetsLoader.greenGem.height
    }

    override fun onFrame() = intent {
        val linePoints = state.linePoints.toMutableList().apply {
            if (state.linePoints.size > 1) removeFirst()
        }

        reduce {
            state.copy(linePoints = linePoints)
        }

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

    fun onMove(x: Float, y: Float) = intent {
        reduce {
            state.copy(
                linePoints = state.linePoints + PointF(x, y)
            )
        }
    }

    fun onTouchDown(x: Float, y: Float) = intent {
        reduce {
            state.copy(
                linePoints = listOf(PointF(x, y))
            )
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
