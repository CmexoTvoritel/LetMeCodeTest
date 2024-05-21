package com.letmecode.core.extensions

fun Int.convertDateToStr(): String = if(this >= 10) this.toString() else "0$this"