package betesporte.color.match.presentation.view.chooser

import android.content.Context
import android.util.AttributeSet
import betesporte.color.match.presentation.common.game.BaseGameView

class ChooserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseGameView(context, attrs) {

    val viewModel = ChooserViewModel()
    private val resourceLoader = ChooserResourceLoader(resources)

    override fun onSurfaceCreated() {
        viewModel.setScreenSize(width, height)
    }

    override val renderingThread = ChooserRenderingThread(
        resourceLoader = resourceLoader,
        viewModel = viewModel,
        holder = holder,
    )
}