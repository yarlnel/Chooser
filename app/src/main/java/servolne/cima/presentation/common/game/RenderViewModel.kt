package servolne.cima.presentation.common.game

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container

abstract class RenderViewModel<STATE : Any, SIDE_EFFECT : Any>(
    initState: STATE
) : ViewModel(),
    ContainerHost<STATE, SIDE_EFFECT> {

    override val container = container<STATE, SIDE_EFFECT>(initState)

    protected var currentFrame: Int = 0
    open fun handleNewFrame() : Job = intent {
        onFrame()
        currentFrame++
    }

    abstract fun onFrame() : Job
}
