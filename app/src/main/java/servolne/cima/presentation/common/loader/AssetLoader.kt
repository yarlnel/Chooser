package servolne.cima.presentation.common.loader

import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes

abstract class AssetLoader(
    private val resources: Resources
) {

    fun bitmap(@DrawableRes resId: Int) = BitmapFactory.decodeResource(resources, resId)!!

    fun lazyBitmap(@DrawableRes resId: Int) = lazy { bitmap(resId) }
}