package drop.kansino.coin.presentation.test

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.SurfaceHolder
import androidx.core.graphics.scale
import drop.kansino.coin.R
import drop.kansino.coin.presentation.common.game.ViewModelRenderingThread

class TestGameRenderingThread(
    holder: SurfaceHolder,
    viewModel: TestGameViewModel,
    private val assetsLoader: TestGameAssetsLoader,
    private val resources: Resources,
) : ViewModelRenderingThread<TestGameState, TestGameSideEffect>(
    holder = holder,
    viewModel = viewModel
) {

    private val bitmapPaint by lazy {
        Paint(Paint.DITHER_FLAG)
    }

    override fun Canvas.render() {
        renderBackground()
        renderGems()
        renderLine()
        renderScore()
        renderLooseGems()
    }

    private var backgroundBitmap: Bitmap? = null

    private fun Canvas.renderBackground() {
        if (backgroundBitmap == null) {
            backgroundBitmap = assetsLoader.background.scale(width, height)
        }
        drawBitmap(backgroundBitmap!!, 0f,0f, bitmapPaint)
    }
    private val defaultTextSize by lazy {
        resources.getDimension(R.dimen.default_text_size)
    }

    private val textPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = defaultTextSize
            color = Color.WHITE
            style = Paint.Style.FILL
        }
    }

    private fun Canvas.renderScore() {
        val text = state.score.toString()
        drawText(
            text,
            20f,
            defaultTextSize + 20f,
            textPaint
        )
    }

    private fun Canvas.renderLooseGems() {
        val text = resources.getString(R.string.losse_gems_template, state.gemLoose)
        val textWidth = textPaint.measureText(text)

        drawText(
            text,
            width - (textWidth + 20f),
            defaultTextSize + 20f,
            textPaint
        )
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
                bitmapPaint
            )
        }
    }
}