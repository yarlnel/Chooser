package servolne.cima.presentation.test

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.view.SurfaceHolder
import servolne.cima.presentation.common.game.ViewModelRenderingThread

class TestGameRenderingThread(
    holder: SurfaceHolder,
    viewModel: TestGameViewModel,
    private val assetsLoader: TestGameAssetsLoader,
    private val resources: Resources
) : ViewModelRenderingThread<TestGameState, TestGameSideEffect>(
    holder = holder,
    viewModel = viewModel
) {

    override fun Canvas.render() {
        renderGems()
    }

    private val drawablePaint by lazy {
        Paint(Paint.DITHER_FLAG)
    }

    private fun Canvas.renderGems() {
        for (gem in state.gems) {
            val gemBitmap = when(gem.color) {
                TestGameState.Gem.Color.GREEN -> assetsLoader.greenGem
                TestGameState.Gem.Color.RED -> assetsLoader.redGem
                TestGameState.Gem.Color.YELLOW -> assetsLoader.yellowGem
                TestGameState.Gem.Color.BLUE -> assetsLoader.blueGem
            }

            drawBitmap(
                gemBitmap,
                gem.x.toFloat(),
                gem.y.toFloat(),
                drawablePaint
            )
        }
    }
}