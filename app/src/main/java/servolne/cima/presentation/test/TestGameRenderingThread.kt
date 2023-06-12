package servolne.cima.presentation.test

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
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
        drawColor(Color.BLUE)
        renderGems()
        renderLine()
    }

    private val drawablePaint by lazy {
        Paint(Paint.DITHER_FLAG)
    }

    private val textPaint = Paint().apply{
        style = Paint.Style.FILL
        textSize = 50f
        color = Color.WHITE
    }

    private val linePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        strokeWidth = 5f
    }

    private fun Canvas.renderLine() {
        val path = Path()
        for ((i, point) in state.linePoints.withIndex()) {
            if (i == 0) {
                path.moveTo(point.x, point.y)
                continue
            }
            path.lineTo(point.x, point.y)
        }
        drawPath(path, linePaint)
    }

    private fun Canvas.renderGems() {
        drawText("Gems{${state.gems.size}}", 20f,100f,Paint().apply{
            style = Paint.Style.FILL
            textSize = 50f
            color = Color.WHITE
        })


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