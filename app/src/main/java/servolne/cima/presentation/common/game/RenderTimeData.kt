package servolne.cima.presentation.common.game

interface RenderTimeData {
    val startGameTime: Long
    val lastRenderingTime: Long
    val currentFrame: Int
}