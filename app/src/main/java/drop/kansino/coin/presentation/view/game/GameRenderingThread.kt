package drop.kansino.coin.presentation.view.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.SurfaceHolder
import androidx.core.graphics.scale
import drop.kansino.coin.R
import drop.kansino.coin.presentation.common.game.ViewModelRenderingThread

class GameRenderingThread(
    holder: SurfaceHolder,
    viewModel: GameViewModel,
    private val resourceLoader: GameResourceLoader
) : ViewModelRenderingThread<GameState, GameSideEffect>(
    holder = holder,
    viewModel = viewModel
) {

    override fun Canvas.render() {
        renderBackground()
        renderGliderBullets()
        renderScore()
        renderGlider()
        renderCoins()
    }

    private var background: Bitmap? = null
    private fun Canvas.renderBackground() {
        if (background == null) {
            background = resourceLoader
                .bitmap(R.drawable.background)
                .scale(width, height)
        }
        drawBitmap(background!!, 0f, 0f, bitmapPaint)
    }

    private val textPaint by lazy {
        Paint().apply {
            textSize = resourceLoader.textSize
            color = Color.WHITE
            style = Paint.Style.FILL
        }
    }

    private fun Canvas.renderScore() {
        drawText(
            state.score.toString(),
            20f,
            20f + resourceLoader.textSize,
            textPaint
        )
    }

    private val bitmapPaint = Paint(Paint.DITHER_FLAG)
    private fun Canvas.renderGlider() {
        drawBitmap(
            resourceLoader.gemGlider,
            state.glider.x,
            state.glider.y,
            bitmapPaint
        )
    }

    private val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private fun Canvas.renderGliderBullets() {
        for (bullet in state.bullets) {
            val bulletRect = bullet.run {
                RectF(
                    x,
                    y,
                    x + resourceLoader.bulletWidth,
                    y + resourceLoader.bulletHeight
                )
            }

            rectPaint.color = bullet.color

            drawRoundRect(
                bulletRect,
                resourceLoader.bulletRadius,
                resourceLoader.bulletRadius,
                rectPaint
            )
        }
    }

    private fun Canvas.renderCoins() {
        for (coin in state.coins) {
            drawBitmap(
                resourceLoader.coin,
                coin.x,
                coin.y,
                bitmapPaint
            )
        }
    }
}