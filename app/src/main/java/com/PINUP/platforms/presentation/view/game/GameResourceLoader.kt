package com.PINUP.platforms.presentation.view.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import androidx.annotation.ColorInt
import com.PINUP.platforms.R
import com.PINUP.platforms.presentation.common.loader.ResourceLoader

class GameResourceLoader(resources: Resources) : ResourceLoader(resources) {

    val gemGlider by lazy {
        val source = bitmap(R.drawable.brill_red)
        val matrix = Matrix().apply {
            postRotate(180f)
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

    val textSize by lazyDimen(R.dimen.default_text_size)
    val bulletRadius by lazyDimen(R.dimen.glider_bullet_radius)
}