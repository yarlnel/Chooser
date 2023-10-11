package com.PINUP.platforms.presentation.view.chooser

import android.content.res.Resources
import com.PINUP.platforms.R
import com.PINUP.platforms.presentation.common.loader.ResourceLoader

class ChooserResourceLoader(resources: Resources) : ResourceLoader(resources) {

    val textSize by lazyDimen(R.dimen.default_text_size)
    val bigTextSize by lazyDimen(R.dimen.big_text_size)
    val fortuneWheelSize by lazyDimen(R.dimen.fortune_wheel_size)
    val topFortuneWheelToTopMargin by lazyDimen(R.dimen.top_fw_constraint_top_value)
    val defaultMargin by lazyDimen(R.dimen.default_margin)
    val boxAngle by lazyDimen(R.dimen.default_box_angle)
    val boxSize by lazyDimen(R.dimen.default_box_size)
}