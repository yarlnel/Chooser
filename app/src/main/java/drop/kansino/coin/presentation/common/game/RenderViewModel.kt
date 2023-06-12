package drop.kansino.coin.presentation.common.game

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container

abstract class RenderViewModel<STATE : Any, SIDE_EFFECT : Any>(
    initState: STATE
) : ViewModel(),
    ContainerHost<STATE, SIDE_EFFECT> {

    override val container = container<STATE, SIDE_EFFECT>(initState)

    protected var currentFrame: Int = 0
    fun handleNewFrame() : Job = intent {
        onFrame()
        currentFrame++
    }

    protected abstract fun onFrame() : Job
}
