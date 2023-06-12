package servolne.cima.presentation.common.game

import android.view.SurfaceHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class ViewModelRenderingThread<STATE : Any, SIDE_EFFECT : Any>(
    holder: SurfaceHolder,
    private val viewModel: RenderViewModel<STATE, SIDE_EFFECT>,
) : BaseRenderingThread(
    holder = holder
), CoroutineScope {

    protected val state: STATE get() = viewModel.container.stateFlow.value

    protected open fun handleSideEffect(sideEffect: SIDE_EFFECT) {}
    protected open fun handleState(state: STATE) {}

    private val job = Job()

    override val coroutineContext = Dispatchers.Default + job

    fun cancel() {
        job.cancel()
    }
}