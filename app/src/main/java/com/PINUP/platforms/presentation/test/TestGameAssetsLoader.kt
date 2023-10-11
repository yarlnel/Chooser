package com.PINUP.platforms.presentation.test

import android.content.res.Resources
import com.PINUP.platforms.R
import com.PINUP.platforms.presentation.common.loader.ResourceLoader


class TestGameAssetsLoader(
    resources: Resources
) : ResourceLoader(resources) {

    val redGem by lazyBitmap(R.drawable.brill_red)
    val greenGem by lazyBitmap(R.drawable.brill_green)
    val yellowGem by lazyBitmap(R.drawable.bril_yellow)
    val blueGem by lazyBitmap(R.drawable.brill_blue)

    val background by lazyBitmap(R.drawable.background)
}