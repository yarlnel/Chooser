package com.PINUP.platforms.presentation.utils


import android.view.View

infix fun View.onclick(voidLambda: () -> Unit) {
    setOnClickListener { voidLambda.invoke() }
}
