package betesporte.color.match.presentation.view.chooser

data class ChooserState(
    val firstFortuneWheel: FortuneWheelState = FortuneWheelState.Default,
    val secondFortuneWheel: FortuneWheelState = FortuneWheelState.Default,
    val thirdFortuneWheel: FortuneWheelState = FortuneWheelState.Default,
    val boxes: Boxes = Boxes(FortuneWheelColor.Red, FortuneWheelColor.Red, FortuneWheelColor.Red)
) {

    data class Boxes(
        val firstBoxColor: FortuneWheelColor,
        val secondBoxColor: FortuneWheelColor,
        val thirdBoxColor: FortuneWheelColor,
    )


    data class FortuneWheelState(
        val selectedColor: FortuneWheelColor,
        val rotate: Float,
        val isStopped: Boolean
    ) {
        companion object {
            val Default = FortuneWheelState(
                selectedColor = FortuneWheelColor.Red,
                rotate = 0F,
                isStopped = false
            )
        }
    }

    enum class FortuneWheelColor {
        Red, Green, Blue
    }
}