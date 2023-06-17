package betesporte.color.match.presentation.test

import android.graphics.PointF
import android.graphics.Rect
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import betesporte.color.match.presentation.common.game.RenderViewModel

class TestGameViewModel(
    private val assetsLoader: TestGameAssetsLoader,
) : RenderViewModel<TestGameState, TestGameSideEffect>(TestGameState()) {
    private var width: Int = 0
    private var height: Int = 0

    fun setGameSize(width: Int, height: Int) {
        this@TestGameViewModel.width = width
        this@TestGameViewModel.height = height
        deltaY = (height * 0.02).toInt()
    }

    private var deltaY: Int = (height * 0.02).toInt()
    private var framesToNewGem = 56

    private val gemWidth by lazy {
        assetsLoader.greenGem.width
    }

    private val gemHeight by lazy {
        assetsLoader.greenGem.height
    }

    override fun onFrame() = intent {
        reduce {
            state.copy(
                linePoints = state.linePoints.drop(
                    1 + (state.linePoints.size / 5)
                ),
                gems = state.gems.map { gem ->
                    gem.apply { y += deltaY }
                }
            )
        }

        for (gem in state.gems) {
            if (gem.y >= height + gemHeight) {
                val newGems = state.gems.toMutableList().apply {
                    remove(gem)
                }
                reduce {
                    state.copy(
                        gems = newGems,
                        gemLoose = state.gemLoose + 1
                    )
                }
                break
            }
        }

        if (currentFrame % framesToNewGem == 0) {
            generateGem()
        }

        if (currentFrame % 500 == 0) {
            framesToNewGem -= 1
        }

        if (state.gemLoose >= 10) {
            postSideEffect(TestGameSideEffect.GameFinished(
                score = state.score
            ))
        }
    }

    fun onMove(x: Float, y: Float) = intent {
        verifyLineIntersectGem(x, y)
        reduce {
            state.copy(
                linePoints = state.linePoints.toMutableList() + PointF(x, y)
            )
        }
    }

    private fun verifyLineIntersectGem(x: Float, y: Float) = intent {
        for (gem in state.gems) {
            val gemRect = Rect(
                gem.x,
                gem.y,
                gem.x + gemWidth,
                gem.y + gemHeight
            )
            if (gemRect.contains(x.toInt(), y.toInt())) {
                val newGems = state.gems.toMutableList().apply {
                    remove(gem)
                }
                reduce {
                    state.copy(
                        gems = newGems,
                        score = state.score + 1
                    )
                }
            }
        }
    }

    fun onTouchDown(x: Float, y: Float) = intent {
        reduce {
            state.copy(
                linePoints = listOf(PointF(x, y))
            )
        }
    }

    private fun generateGem() = intent {
        val x = (0 until (width - gemWidth)).random()
        val y = -gemHeight
        val colors = TestGameState.Gem.Color.values()
        val color = colors[(1 until colors.size).random()]
        val newGem = TestGameState.Gem(
            x = x,
            y = y,
            color = color
        )

        reduce {
            state.copy(
                gems = state.gems + newGem
            )
        }
    }

    fun clean() = intent{
        deltaY = (height * 0.02).toInt()
        framesToNewGem = 56
        reduce { TestGameState() }
    }
}
