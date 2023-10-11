package com.PINUP.platforms.presentation.view.chooser

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.SurfaceHolder
import androidx.core.graphics.scale
import com.PINUP.platforms.R
import com.PINUP.platforms.presentation.common.game.ViewModelRenderingThread

class ChooserRenderingThread(
    private val resourceLoader: ChooserResourceLoader,
    viewModel: ChooserViewModel,
    holder: SurfaceHolder
) : ViewModelRenderingThread<ChooserState, ChooserSideEffect>(holder, viewModel) {


    override fun Canvas.render() {
        renderBackground()
        renderText()
        renderBoxes()
        renderFortuneWheels()
        renderOnSelectedText()
        renderLevel()
        renderMarkers()
    }

    private val whitePaint = Paint().apply {
        color = Color.BLACK
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
            resourceLoader.defaultMargin * 0.8f + resourceLoader.boxSize,
            textPaint
        )
    }


    private val rectFirst by lazy {
        with(resourceLoader) {
            RectF(
                defaultMargin * 2 + textWidth,
                defaultMargin,
                defaultMargin * 2 + boxSize + textWidth,
                defaultMargin + boxSize
            )
        }
    }


    private val rectSecond by lazy {
        with(resourceLoader) {
            RectF(
                rectFirst.right + defaultMargin,
                defaultMargin,
                rectFirst.right + defaultMargin + boxSize,
                defaultMargin + boxSize
            )
        }
    }

    private val rectThird by lazy {
        with(resourceLoader) {
            RectF(
                rectSecond.right + defaultMargin,
                defaultMargin,
                rectSecond.right + defaultMargin + boxSize,
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


    private val redTextPaint = redPaint.apply {
        textSize = resourceLoader.bigTextSize
    }

    private val greenTextPaint = greenPaint.apply {
        textSize = resourceLoader.bigTextSize
    }

    private val blueTextPaint = bluePaint.apply {
        textSize = resourceLoader.bigTextSize
    }

    private fun ChooserState.FortuneWheelColor.getTextPaint() : Paint = when(this) {
        ChooserState.FortuneWheelColor.Red -> redTextPaint
        ChooserState.FortuneWheelColor.Green -> greenTextPaint
        ChooserState.FortuneWheelColor.Blue -> blueTextPaint
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
    
    private val topMarkerRect by lazy {  
        val left = topFortuneWheelOval.right - resourceLoader.defaultMargin
        val top = topFortuneWheelOval.centerY() + 3f
        val right = topFortuneWheelOval.right
        val bottom = topFortuneWheelOval.centerY() - 3f
        
        RectF(
            left, top, right, bottom
        )
    }

    private val middleMarkerRect by lazy {
        val left = middleFortuneWheelOval.right - resourceLoader.defaultMargin
        val top = middleFortuneWheelOval.centerY() + 3f
        val right = middleFortuneWheelOval.right
        val bottom = middleFortuneWheelOval.centerY() - 3f

        RectF(
            left, top, right, bottom
        )
    }

    private val bottomMarkerRect by lazy {
        val left = bottomFortuneWheelOval.right - resourceLoader.defaultMargin
        val top = bottomFortuneWheelOval.centerY() + 3f
        val right = bottomFortuneWheelOval.right
        val bottom = bottomFortuneWheelOval.centerY() - 3f

        RectF(
            left, top, right, bottom
        )
    }

    private fun Canvas.renderMarkers() {
        drawRect(topMarkerRect, whitePaint)
        drawRect(middleMarkerRect, whitePaint)
        drawRect(bottomMarkerRect, whitePaint)
    }

    private val bitmapPaint = Paint(Paint.DITHER_FLAG)

    private var background: Bitmap? = null
    private fun Canvas.renderBackground() {
        if (background == null) {
            background = resourceLoader
                .bitmap(R.drawable.background_gem_min)
                .scale(width, height)
        }
        drawBitmap(background!!, 0f, 0f, bitmapPaint)
    }

    private val defaultSweepAngle = 120f

    private fun ChooserState.FortuneWheelColor.getTextForColor() = when (this) {
        ChooserState.FortuneWheelColor.Red -> "Red"
        ChooserState.FortuneWheelColor.Green -> "Green"
        ChooserState.FortuneWheelColor.Blue -> "Blue"
    }

    private fun Canvas.renderOnSelectedText() {
        renderTopOnSelectedText()
        renderMiddleOnSelectedText()
        renderBottomOnSelectedText()
    }

    private fun Canvas.renderTopOnSelectedText() = with(state.firstFortuneWheel) {
        if (isStopped) {
            val text = selectedColor.getTextForColor()
            val paint = selectedColor.getTextPaint()

            drawText(
                text,
                topFortuneWheelOval.right + 60f,
                topFortuneWheelOval.bottom,
                paint
            )
        }
    }

    private fun Canvas.renderMiddleOnSelectedText() = with(state.secondFortuneWheel) {
        if (isStopped) {
            val text = selectedColor.getTextForColor()
            val paint = selectedColor.getTextPaint()

            drawText(
                text,
                middleFortuneWheelOval.right + 60f,
                middleFortuneWheelOval.bottom,
                paint
            )
        }
    }
    private fun Canvas.renderBottomOnSelectedText() = with(state.thirdFortuneWheel) {
        if (isStopped) {
            val text = selectedColor.getTextForColor()
            val paint = selectedColor.getTextPaint()

            drawText(
                text,
                bottomFortuneWheelOval.right + 60f,
                bottomFortuneWheelOval.bottom,
                paint
            )
        }
    }

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

    private fun Canvas.renderLevel() = with(state){
        val levelTitle = "$level/$maxLevel"
        val width = textPaint.measureText(levelTitle)
        val x = (screenWidth - width) / 2
        drawText(
            levelTitle,
            x,
            rectFirst.bottom + resourceLoader.defaultMargin * 2,
            textPaint
        )
    }

    private object Constants {
        const val TextFortuneColors = "Fortune colors: "
        const val FortuneWheelGap = 40f
    }
}