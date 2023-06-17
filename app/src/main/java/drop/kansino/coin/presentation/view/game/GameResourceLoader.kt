package drop.kansino.coin.presentation.view.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import androidx.annotation.ColorInt
import drop.kansino.coin.R
import drop.kansino.coin.presentation.common.loader.ResourceLoader

class GameResourceLoader(resources: Resources) : ResourceLoader(resources) {

    val gemGlider by lazy {
        val source = bitmap(R.drawable.brill_red)
        val matrix = Matrix().apply {
            postRotate(90f)
        }

        Bitmap.createBitmap(
            source,
            0,
            0,
            source.width,
            source.height,
            matrix,
            true
        )!!
    }

    val coin by lazyBitmap(R.drawable.coin_v)

    val bulletHeight by lazyDimen(R.dimen.glider_bullet_height)
    val bulletWidth by lazyDimen(R.dimen.glider_bullet_width)

    @ColorInt
    val bulletColors: Array<Int> = arrayOf(
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
    )
}