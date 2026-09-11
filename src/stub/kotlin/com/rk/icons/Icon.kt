package com.rk.icons

sealed class Icon {
    data class TextIcon(val text: String) : Icon()
}
