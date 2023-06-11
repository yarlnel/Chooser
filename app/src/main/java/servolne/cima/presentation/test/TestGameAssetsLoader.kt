package servolne.cima.presentation.test

import android.content.res.Resources
import servolne.cima.R
import servolne.cima.presentation.common.loader.ResourceLoader

class TestGameAssetsLoader(
    resources: Resources
) : ResourceLoader(resources) {

    private val redGem by lazy {
        bitmap(R.drawable.brill_red)
    }

    private val greenGem by lazy {
        bitmap(R.drawable.brill_green)
    }


}