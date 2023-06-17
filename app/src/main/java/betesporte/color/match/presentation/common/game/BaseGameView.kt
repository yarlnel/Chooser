package betesporte.color.match.presentation.common.game

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

abstract class BaseGameView(
    context: Context,
    attrs: AttributeSet?
) : SurfaceView(context, attrs) {

    protected abstract val renderingThread: BaseRenderingThread

    private val surfaceHolderCallback = object : SurfaceHolder.Callback {

        override fun surfaceCreated(p0: SurfaceHolder) {
            onSurfaceCreated()
            renderingThread.start()
            renderingThread.startRender()
        }

        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            onSurfaceChanged()
        }

        override fun surfaceDestroyed(p0: SurfaceHolder) {
            onSurfaceDestroyed()
            renderingThread.pauseRender()
            var retry = true
            while (retry) {
                try {
                    renderingThread.join()
                    retry = false
                } catch (ex: Exception) {}
            }
        }
    }

    protected open fun onSurfaceCreated() {}
    protected open fun onSurfaceChanged() {}
    protected open fun onSurfaceDestroyed() {}

    fun pauseGame() {
        renderingThread.pauseRender()
    }

    fun resumeGame() {
        renderingThread.startRender()
    }

    init {
        holder.addCallback(surfaceHolderCallback)
    }
}
