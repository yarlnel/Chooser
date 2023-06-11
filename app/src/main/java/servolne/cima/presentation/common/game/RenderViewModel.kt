package servolne.cima.presentation.common.game

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

abstract class RenderViewModel<STATE : Any, SIDE_EFFECT : Any>(
    initState: STATE,
    timeData: RenderTimeData
) : ViewModel(),
    ContainerHost<STATE, SIDE_EFFECT>,
    RenderTimeData by timeData {

    override val container = container<STATE, SIDE_EFFECT>(initState)
}
