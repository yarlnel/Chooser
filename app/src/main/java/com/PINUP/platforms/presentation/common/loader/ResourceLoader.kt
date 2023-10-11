package com.PINUP.platforms.presentation.common.loader

import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes

abstract class ResourceLoader(
    private val resources: Resources
) {

    fun bitmap(@DrawableRes resId: Int) = BitmapFactory.decodeResource(resources, resId)!!

    fun lazyBitmap(@DrawableRes resId: Int) = lazy { bitmap(resId) }

    fun dimen(@DimenRes resId: Int) = resources.getDimension(resId)

    fun lazyDimen(@DimenRes resId: Int) = lazy { dimen(resId) }
}
