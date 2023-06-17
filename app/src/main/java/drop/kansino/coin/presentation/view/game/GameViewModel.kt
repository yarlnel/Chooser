package drop.kansino.coin.presentation.view.game

import android.graphics.RectF
import drop.kansino.coin.presentation.common.game.RenderViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class GameViewModel(
    private val height: Int,
    private val width: Int,
    private val resourceLoader: GameResourceLoader
) : RenderViewModel<GameState, GameSideEffect>(GameState()) {

    private val gliderHeight by lazy {
        resourceLoader.gemGlider.height
    }

    private val gliderWidth by lazy {
        resourceLoader.gemGlider.width
    }

    private val coinSize by lazy {
        resourceLoader.coin.height
    }

    private var framesToNewCoin = 32
    private var coinDeltaY = 3

    private var framesToNewBullet = 10
    private var bulletDeltaY = 5

    override fun onFrame() = intent {
        if (currentFrame % framesToNewCoin == 0) {
            produceCoin()
        }

        if (currentFrame % 100 == 0) {
            if (framesToNewCoin > 1) framesToNewCoin--
        }

        if (currentFrame > 1000 && currentFrame % 50 == 0 && coinDeltaY < 18) {
            coinDeltaY++
        }

        if (currentFrame % framesToNewBullet == 0) {
            produceGliderBullet()
        }

        applyCoinsGravity()
        applyBulletImpulse()

        if (currentFrame % 10 == 0) {
            clearUnusedStateModels()
        }

        handleBulletIntersection()
        handleGliderIntersection()
    }

    private fun clearUnusedStateModels() = intent {
        val bullets = state.bullets.filter { bullet ->
            bullet.y < -resourceLoader.bulletHeight
        }
        val coins = state.coins.filter { coin ->
            coin.y < height
        }

        reduce {
            state.copy(
                bullets = bullets,
                coins = coins
            )
        }
    }

    private fun handleBulletIntersection() = intent {
        for (bullet in state.bullets) {
            val bulletRect = bullet.rect()

            for (coin in state.coins) {
                val coinRect = coin.rect()

                if (bulletRect.intersect(coinRect)) reduce {
                    val bullets = state.bullets.toMutableList()
                    bullets.remove(bullet)

                    val coins = state.coins.toMutableList()
                    coins.remove(coin)

                    state.copy(
                        score = state.score + 1,
                        bullets = bullets,
                        coins = coins
                    )
                }
            }
        }
    }

    private fun GameState.Coin.rect() = RectF(
        x,
        y,
        x + coinSize,
        y + coinSize
    )

    private fun GameState.GliderBullet.rect() = RectF(
        x,
        y,
        x + resourceLoader.bulletWidth,
        y + resourceLoader.bulletHeight
    )

    private fun GameState.Glider.rect() = RectF(
        x,
        y,
        x + gliderWidth,
        y + gliderHeight
    )

    private fun handleGliderIntersection() = intent {
        val gliderRect = state.glider.rect()
        for (coin in state.coins) {
            val coinRect = coin.rect()
            if (gliderRect.intersect(coinRect)) {
                postSideEffect(
                    GameSideEffect.FinishGame(score = state.score)
                )
                return@intent
            }
        }
    }

    private fun applyCoinsGravity() = intent {
        val newCoins = state.coins.map { coin ->
            GameState.Coin(coin.x, coin.y - coinDeltaY)
        }
        reduce {
            state.copy(coins = newCoins)
        }
    }

    private fun produceCoin() = intent {
        val coin = GameState.Coin(
            x = (0 until (width - coinSize)).random().toFloat(),
            y = -coinSize.toFloat()
        )
        reduce {
            state.copy(
                coins = state.coins + coin
            )
        }
    }

    private fun applyBulletImpulse() = intent {
        val bullets = state.bullets.map { bullet ->
            GameState.GliderBullet(bullet.x, bullet.y - bulletDeltaY)
        }
        reduce {
            state.copy(bullets = bullets)
        }
    }

    private var gliderCenterX = 0f

    private fun produceGliderBullet() = intent {
        val bulletColor = resourceLoader.bulletColors.random()
        val bulletX = gliderCenterX - (resourceLoader.bulletWidth / 2)
        val bulletY = state.glider.y + resourceLoader.bulletHeight
        val bullet = GameState.GliderBullet(
            x = bulletX,
            y = bulletY,
            color = bulletColor
        )
        reduce {
            state.copy(
                bullets = state.bullets + bullet
            )
        }
    }

    fun moveGlider(centerX: Float, centerY: Float) = intent {
        gliderCenterX = centerX
        var gliderX = centerX - (gliderWidth / 2)
        var gliderY = centerY - (gliderHeight / 2)

        if (gliderX <= 0f) {
            gliderX = 0f
        }

        if (gliderX + gliderWidth >= width) {
            gliderX = width - gliderX
        }

        if (gliderY <= 0f) {
            gliderY = 0f
        }

        if (gliderY + gliderHeight >= height) {
            gliderY = height - gliderY
        }

        reduce {
            state.copy(
                glider = GameState.Glider(
                    x = gliderX,
                    y = gliderY
                )
            )
        }
    }
}
