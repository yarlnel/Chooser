package com.PINUP.platforms.presentation.common.game

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.PINUP.platforms.presentation.utils.time

abstract class BaseRenderingThread(
    private val redrawTime: Long = Constants.RedrawTime,
    private val holder: SurfaceHolder
) : Thread(), RenderTimeController {

    private var isRunning: Boolean = false
    override var startGameTime: Long = 0L
    override var lastRenderingTime: Long = 0L
    override var currentFrame: Int = 0

    override fun pauseRender() {
        isRunning = false
    }

    override fun startRender() {
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

    protected open fun onFrame() {
        val canvas = holder.lockCanvas()
        canvas.render()
        holder.unlockCanvasAndPost(canvas)
        currentFrame++
    }

    protected abstract fun Canvas.render()

    private object Constants {
        const val RedrawTime = 16L
    }
}