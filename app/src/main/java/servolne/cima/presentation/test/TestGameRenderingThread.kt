package servolne.cima.presentation.test

import android.content.res.Resources
import android.graphics.Canvas
import android.view.SurfaceHolder
import servolne.cima.presentation.common.game.BaseRenderingThread

class TestGameRenderingThread(
    holder: SurfaceHolder,
    private val resources: Resources
) : BaseRenderingThread(holder = holder) {

    override fun Canvas.render() {

    }
}