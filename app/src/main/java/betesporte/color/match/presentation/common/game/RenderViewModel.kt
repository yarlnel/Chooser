package betesporte.color.match.presentation.common.game

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
    private var isStarted: Boolean = false

    fun handleNewFrame() : Job = intent {
        if (!isStarted) {
            onStart()
            isStarted = true
        }
        onFrame()
        currentFrame++
    }

    protected open fun onStart() {}
    protected abstract fun onFrame() : Job
}
