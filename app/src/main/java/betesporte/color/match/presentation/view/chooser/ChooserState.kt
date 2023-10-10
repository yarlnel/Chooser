package betesporte.color.match.presentation.view.chooser

data class ChooserState(
    val firstFortuneWheel: FortuneWheelState = FortuneWheelState.Default,
    val secondFortuneWheel: FortuneWheelState = FortuneWheelState.Default,
    val thirdFortuneWheel: FortuneWheelState = FortuneWheelState.Default,
    val level: Int = 0,
    val maxLevel: Int = 10,
    val score: Int = 0,
    val boxes: Boxes = Boxes(FortuneWheelColor.Red, FortuneWheelColor.Red, FortuneWheelColor.Red),
    val screenWidth: Int = 0,
    val screenHeight: Int = 0,
    val btnState: BtnState = BtnState.None
) {

    enum class BtnState {
        None, Red, Green
    }
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