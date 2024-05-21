package com.letmecode.core.extensions

import android.view.View

fun View.beGone() {
    visibility = View.GONE
}

fun View.beVisible() {
    visibility = View.VISIBLE
}

fun View.beVisibleIf(condition: Boolean) = if(condition) beVisible() else beGone()