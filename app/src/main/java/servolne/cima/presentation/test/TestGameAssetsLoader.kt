package servolne.cima.presentation.test

import android.content.res.Resources
import servolne.cima.R
import servolne.cima.presentation.common.loader.AssetLoader


class TestGameAssetsLoader(
    resources: Resources
) : AssetLoader(resources) {

    val redGem by lazyBitmap(R.drawable.brill_red)
    val greenGem by lazyBitmap(R.drawable.brill_green)
    val yellowGem by lazyBitmap(R.drawable.bril_yellow)
    val blueGem by lazyBitmap(R.drawable.brill_blue)

    val background by lazyBitmap(R.drawable.background)
}