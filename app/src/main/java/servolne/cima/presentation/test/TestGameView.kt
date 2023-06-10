package servolne.cima.presentation.test

import android.content.Context
import android.util.AttributeSet
import servolne.cima.presentation.common.game.BaseGameView

class TestGameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseGameView(context, attrs) {

    override val renderingThread = TestGameRenderingThread(holder, resources)
}