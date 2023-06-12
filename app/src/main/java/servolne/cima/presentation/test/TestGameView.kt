package servolne.cima.presentation.test

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import servolne.cima.presentation.common.game.BaseGameView

class TestGameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseGameView(context, attrs) {

    private val assetsLoader = TestGameAssetsLoader(resources)

    private val viewModel = TestGameViewModel(assetsLoader)


    override fun onSurfaceCreated() {
        viewModel.setGameSize(width, height)
    }

    override val renderingThread = TestGameRenderingThread(
        holder = holder,
        assetsLoader = assetsLoader,
        viewModel = viewModel,
        resources = resources
    )

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        when (event.action) {
            MotionEvent.ACTION_MOVE -> viewModel.onMove(event.x, event.y)
            MotionEvent.ACTION_DOWN -> viewModel.onTouchDown(event.x, event.y)
        }
        return true
    }
}
