package com.PINUP.platforms.presentation.view.game

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.PINUP.platforms.presentation.common.game.BaseGameView


class GameView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null
) : BaseGameView(context, attr) {

    private val resourceLoader = GameResourceLoader(resources)
    private val viewModel = GameViewModel(resourceLoader)

    val sideEffectFlow = viewModel.container.sideEffectFlow

    override val renderingThread = GameRenderingThread(
        holder = holder,
        resourceLoader = resourceLoader,
        viewModel = viewModel
    )

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> viewModel.moveGlider(
                centerX = event.x,
                centerY = event.y
            )
            MotionEvent.ACTION_MOVE -> viewModel.moveGlider(
                centerX = event.x,
                centerY = event.y
            )
        }
        return true
    }

    override fun onSurfaceCreated() {
        viewModel.setGameSize(width, height)
    }


    fun newGame() {
        viewModel.newGame()
        resumeGame()
    }
}