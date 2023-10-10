package betesporte.color.match.presentation.view.chooser

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.SurfaceHolder
import androidx.core.graphics.scale
import betesporte.color.match.R
import betesporte.color.match.presentation.common.game.ViewModelRenderingThread

class ChooserRenderingThread(
    private val resourceLoader: ChooserResourceLoader,
    viewModel: ChooserViewModel,
    holder: SurfaceHolder
) : ViewModelRenderingThread<ChooserState, ChooserSideEffect>(holder, viewModel) {


    private var width: Int = 0


    private var height: Int = 0


    private fun setGameSize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    override fun Canvas.render() {
        renderText()
        renderBackground()
        renderBoxes()
        renderFortuneWheels()
    }

    private val redPaint = Paint().apply {
        color = Color.RED
    }

    private val greenPaint = Paint().apply {
        color = Color.GREEN
    }

    private val bluePaint = Paint().apply {
        color = Color.BLUE
    }


    private val redBoxPaint = Paint().apply {
        color = Color.RED

    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = resourceLoader.textSize
        style = Paint.Style.FILL
    }

    private val textWidth = textPaint.measureText(Constants.TextFortuneColors)

    private fun Canvas.renderText() {
        drawText(
            Constants.TextFortuneColors,
            resourceLoader.defaultMargin,
            resourceLoader.defaultMargin,
            textPaint
        )
    }


    private val rectFirst by lazy {
        with(resourceLoader) {
            val rect1End = defaultMargin + boxSize
            RectF(
                defaultMargin,
                defaultMargin,
                rect1End,
                defaultMargin + boxSize
            )
        }
    }


    private val rectSecond by lazy {
        with(resourceLoader) {
            RectF(
                defaultMargin * 2 + boxSize,
                defaultMargin,
                (defaultMargin + boxSize) * 2,
                defaultMargin + boxSize
            )
        }
    }

    private val rectThird by lazy {
        with(resourceLoader) {
            RectF(
                (defaultMargin + boxSize) * 2 + defaultMargin,
                defaultMargin,
                (defaultMargin + boxSize) * 3,
                defaultMargin + boxSize
            )
        }
    }

    private fun Canvas.renderBoxes() = with(resourceLoader) {
        val firstPaint = state.boxes.firstBoxColor.getPaint()
        drawRoundRect(
            rectFirst,
            boxAngle,
            boxAngle,
            firstPaint
        )

        val secondPaint = state.boxes.secondBoxColor.getPaint()
        drawRoundRect(
            rectSecond,
            boxAngle,
            boxAngle,
            secondPaint
        )

        val thirdPaint = state.boxes.thirdBoxColor.getPaint()
        drawRoundRect(
            rectThird,
            boxAngle,
            boxAngle,
            thirdPaint
        )
    }

    private fun ChooserState.FortuneWheelColor.getPaint() : Paint = when(this) {
        ChooserState.FortuneWheelColor.Blue -> bluePaint
        ChooserState.FortuneWheelColor.Red -> redPaint
        ChooserState.FortuneWheelColor.Green -> greenPaint
    }

    private val topFortuneWheelOval by lazy {
        val left = 40f // (width / 2) - (resourceLoader.fortuneWheelSize / 2)
        val top = resourceLoader.topFortuneWheelToTopMargin
        val right = left + resourceLoader.fortuneWheelSize
        val bottom = top + resourceLoader.fortuneWheelSize

        RectF(
            left, top, right, bottom
        )
    }

    private val middleFortuneWheelOval by lazy {
        val left = 40f
        val top = topFortuneWheelOval.bottom + Constants.FortuneWheelGap
        val right = left + resourceLoader.fortuneWheelSize
        val bottom = top + resourceLoader.fortuneWheelSize

        RectF(
            left, top, right, bottom
        )
    }

    private val bottomFortuneWheelOval by lazy {
        val left = 40f
        val top = middleFortuneWheelOval.bottom + Constants.FortuneWheelGap
        val right = left + resourceLoader.fortuneWheelSize
        val bottom = top + resourceLoader.fortuneWheelSize

        RectF(
            left, top, right, bottom
        )
    }



    private val bitmapPaint = Paint(Paint.DITHER_FLAG)

    private var background: Bitmap? = null
    private fun Canvas.renderBackground() {
        if (background == null) {
            background = resourceLoader
                .bitmap(R.drawable.background)
                .scale(width, height)
        }
        drawBitmap(background!!, 0f, 0f, bitmapPaint)
    }

    private val defaultSweepAngle = 120f

    private fun Canvas.renderFortuneWheels() {
        renderTopFortuneWheel()
        renderMiddleFortuneWheel()
        renderBottomFortuneWheel()
    }

    private fun Canvas.renderTopFortuneWheel() = with(state.firstFortuneWheel) {
        drawArc(
            topFortuneWheelOval,
            0F + rotate,
            defaultSweepAngle,
            true,
            redPaint
        )

        drawArc(
            topFortuneWheelOval,
            120f + rotate,
            defaultSweepAngle,
            true,
            greenPaint
        )

        drawArc(
            topFortuneWheelOval,
            240f + rotate,
            defaultSweepAngle,
            true,
            bluePaint
        )
    }

    private fun Canvas.renderMiddleFortuneWheel() = with(state.secondFortuneWheel) {
        drawArc(
            middleFortuneWheelOval,
            0F + rotate,
            defaultSweepAngle,
            true,
            redPaint
        )

        drawArc(
            middleFortuneWheelOval,
            120f + rotate,
            defaultSweepAngle,
            true,
            greenPaint
        )

        drawArc(
            middleFortuneWheelOval,
            240f + rotate,
            defaultSweepAngle,
            true,
            bluePaint
        )
    }

    private fun Canvas.renderBottomFortuneWheel() = with(state.thirdFortuneWheel) {
        drawArc(
            bottomFortuneWheelOval,
            0F + rotate,
            defaultSweepAngle,
            true,
            redPaint
        )

        drawArc(
            bottomFortuneWheelOval,
            120f + rotate,
            defaultSweepAngle,
            true,
            greenPaint
        )

        drawArc(
            bottomFortuneWheelOval,
            240f + rotate,
            defaultSweepAngle,
            true,
            bluePaint
        )
    }



    private object Constants {
        const val TextFortuneColors = "Fortune colors: "
        const val FortuneWheelGap = 40f
    }
}