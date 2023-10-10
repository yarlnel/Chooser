package betesporte.color.match.presentation.view.chooser

import android.content.Context
import android.util.AttributeSet
import betesporte.color.match.presentation.common.game.BaseGameView

class ChooserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseGameView(context, attrs) {

    private val viewModel = ChooserViewModel()
    private val resourceLoader = ChooserResourceLoader(resources)

    override val renderingThread = ChooserRenderingThread(
        resourceLoader = resourceLoader,
        viewModel = viewModel,
        holder = holder,
    )
}