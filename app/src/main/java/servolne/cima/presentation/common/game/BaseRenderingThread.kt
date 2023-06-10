package servolne.cima.presentation.common.game

import android.graphics.Canvas
import android.view.SurfaceHolder
import servolne.cima.presentation.utils.time

abstract class BaseRenderingThread(
    private val redrawTime: Long = Constants.RedrawTime,
    private val holder: SurfaceHolder
) : Thread() {

    private var isRunning: Boolean = false
    protected var startGameTime: Long = 0L
    protected var lastRenderingTime: Long = 0L

    fun pauseRender() {
        isRunning = false
    }

    fun startRender() {
        isRunning = true
    }

    override fun run() {
        startGameTime = time()
        while (true) {
            if (!isRunning) continue

            val curTime = time()
            if (curTime - lastRenderingTime < redrawTime) continue

            onFrame()

            lastRenderingTime = time()
        }
    }

    protected fun onFrame() {
        val canvas = holder.lockCanvas()
        canvas.render()
        holder.unlockCanvasAndPost(canvas)
    }

    protected abstract fun Canvas.render()

    private object Constants {
        const val RedrawTime = 16L
    }
}