package betesporte.color.match.presentation.view.chooser

import android.util.Log
import betesporte.color.match.presentation.common.game.RenderViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.lang.IllegalStateException

class ChooserViewModel : RenderViewModel<ChooserState, ChooserSideEffect>(ChooserState()) {

    init {
        nextLevel()
    }

    private fun getRandomFortuneWheelColor() = ChooserState.FortuneWheelColor.values().random()

    private var currentSessionStart = 0

    fun restart() = intent {
        reduce {
            ChooserState(
                screenWidth = state.screenWidth,
                screenHeight = state.screenHeight
            )
        }
        nextLevel()
    }
    fun nextLevel() = intent {
        isPrepared = false


        reduce {
            state.copy(btnState = ChooserState.BtnState.None)
        }

        val newBoxes = ChooserState.Boxes(
            getRandomFortuneWheelColor(),
            getRandomFortuneWheelColor(),
            getRandomFortuneWheelColor()
        )

        reduce { 
            state.copy(
                boxes = newBoxes,
                level = state.level + 1,
                firstFortuneWheel = state.firstFortuneWheel.copy(isStopped = false),
                secondFortuneWheel = state.secondFortuneWheel.copy(isStopped = false),
                thirdFortuneWheel = state.thirdFortuneWheel.copy(isStopped = false)
            )
        }

        currentSessionStart = currentFrame

        if (state.level >= 10) {
            postSideEffect(ChooserSideEffect.GameFinished(state.score))
            return@intent
        }
    }

    fun setScreenSize(width: Int, height: Int) = intent {
        reduce {
            state.copy(
                screenWidth = width,
                screenHeight = height
            )
        }
    }
    
    private var topRotateValue = 0f
    private var middleRotateValue = 0f
    private var bottomRotateValue = 0f


    override fun onFrame() = intent {
        val isFirstStopped = state.firstFortuneWheel.isStopped
        if (!isFirstStopped && currentFrame - currentSessionStart < Constants.StopFirstFortuneWheelFrame) {
            topRotateValue = (topRotateValue + (1..3).random() + 3) % 360
            setTopRotate(topRotateValue)
            return@intent
        } else {
           stopTop()
        }
        val isSecondStopped = state.secondFortuneWheel.isStopped
        if (!isSecondStopped && currentFrame - currentSessionStart < Constants.StopSecondFortuneWheelFrame) {
            middleRotateValue = (middleRotateValue + (1..2).random() + 4) % 360
            setMiddleRotate(middleRotateValue)
            return@intent
        } else {
            stopMiddle()
        }


        val isThirdStopped = state.thirdFortuneWheel.isStopped
        if (!isThirdStopped && currentFrame - currentSessionStart < Constants.StopThirdFortuneWheelFrame) {
            bottomRotateValue = (bottomRotateValue + (1..4).random() + 2) % 360
            setBottomRotate(bottomRotateValue)
            return@intent
        } else {
            stopBottom()
        }
    }

    private fun stopTop() = intent {
        if (!state.firstFortuneWheel.isStopped) reduce {
            state.copy(
                firstFortuneWheel = state.firstFortuneWheel.copy(
                    isStopped = true
                )
            )
        }
    }

    private fun stopMiddle() = intent {
        if (!state.secondFortuneWheel.isStopped) reduce {
            state.copy(
                secondFortuneWheel = state.secondFortuneWheel.copy(
                    isStopped = true
                )
            )
        }
    }

    private fun stopBottom() = intent {
        if (!state.thirdFortuneWheel.isStopped) reduce {
            state.copy(
                thirdFortuneWheel = state.thirdFortuneWheel.copy(
                    isStopped = true
                )
            )
        }

        prepareToNewLevel()
    }

    private var isPrepared = false
    private fun prepareToNewLevel() = intent {
        if (isPrepared) return@intent

        with(state) {
            val isSuccess = boxes.firstBoxColor == firstFortuneWheel.selectedColor
                    && boxes.secondBoxColor == secondFortuneWheel.selectedColor
                    && boxes.thirdBoxColor == thirdFortuneWheel.selectedColor
            if (isSuccess) reduce {
                state.copy(score = state.score + 1)
            }


            reduce {
                state.copy(btnState = if (isSuccess)
                    ChooserState.BtnState.Green
                else
                    ChooserState.BtnState.Red)
            }
        }

        isPrepared = true
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
        Log.e("WTF", "SelectedColor: ${color.name}")
        return ChooserState.FortuneWheelState(
            selectedColor = color,
            rotate = rotate,
            isStopped = false
        )
    }

    private fun computeColorForRotateAngle(rotate: Float) : ChooserState.FortuneWheelColor = when(rotate) {
        in 0f .. 120f -> ChooserState.FortuneWheelColor.Blue
        in 120f ..240f -> ChooserState.FortuneWheelColor.Green
        in 240f .. 360f -> ChooserState.FortuneWheelColor.Red
        else -> throw IllegalStateException("""
            I fuck this color, cannot compute that
        """.trimIndent())
    }

    private object Constants {
        const val StopFirstFortuneWheelFrame = 100
        const val StopSecondFortuneWheelFrame = 200
        const val StopThirdFortuneWheelFrame = 300
    }
}