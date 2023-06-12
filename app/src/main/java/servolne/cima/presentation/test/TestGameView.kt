package servolne.cima.presentation.test

import android.content.Context
import android.util.AttributeSet
import servolne.cima.presentation.common.game.BaseGameView

class TestGameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseGameView(context, attrs) {

    private val assetsLoader = TestGameAssetsLoader(resources)

    private val viewModel = TestGameViewModel(width, height, assetsLoader)

    override val renderingThread = TestGameRenderingThread(
        holder = holder,
        assetsLoader = assetsLoader,
        viewModel = viewModel
    )
}