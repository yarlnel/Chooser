package betesporte.color.match.presentation.view.chooser

import betesporte.color.match.presentation.common.game.RenderViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import java.lang.IllegalStateException

class ChooserViewModel : RenderViewModel<ChooserState, ChooserSideEffect>(ChooserState()) {


    private var rotateValue = 0f
    override fun onFrame() = intent {

        if (rotateValue < 357) {
            if (currentFrame < Constants.StopFirstFortuneWheelFrame) {
                rotateValue += 10f
            }
        } else {
            rotateValue = 0f
        }
        setTopRotate(rotateValue)
        setMiddleRotate(rotateValue)
        setBottomRotate(rotateValue)
    }

    private fun setTopRotate(rotate: Float) = intent {
        reduce {
            state.copy(
                firstFortuneWheel = computeState(rotate)
            )
        }
    }

    private fun setMiddleRotate(rotate: Float) = intent {
        reduce {
            state.copy(
                secondFortuneWheel = computeState(rotate)
            )
        }
    }

    private fun setBottomRotate(rotate: Float) = intent {
        reduce {
            state.copy(
                thirdFortuneWheel = computeState(rotate)
            )
        }
    }


    private fun computeState(rotate: Float) : ChooserState.FortuneWheelState {
        val color = computeColorForRotateAngle(rotate)
        return ChooserState.FortuneWheelState(
            selectedColor = color,
            rotate = rotate,
            isStopped = false
        )
    }

    private fun computeColorForRotateAngle(rotate: Float) : ChooserState.FortuneWheelColor = when(rotate) {
        in 0f .. 120f -> ChooserState.FortuneWheelColor.Red
        in 120f ..240f -> ChooserState.FortuneWheelColor.Green
        in 240f .. 360f -> ChooserState.FortuneWheelColor.Blue
        else -> throw IllegalStateException("""
            I fuck this color, cannot compute that
        """.trimIndent())
    }

    fun startRoll() = intent {

    }

    private fun rollTop() = intent {

    }

    private object Constants {
        const val StopFirstFortuneWheelFrame = 50
        const val StopSecondFortuneWheelFrame = 70
        const val StopThirdFortuneWheelFrame = 90
    }
}