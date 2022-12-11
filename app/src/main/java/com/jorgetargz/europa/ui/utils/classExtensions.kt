package com.jorgetargz.europa.ui.utils

import android.view.*
import android.widget.ImageView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.jorgetargz.europa.R

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Menu.findItemByTitle(nombre: String): MenuItem? {
    for (i in 0 until size()) {
        val item = getItem(i)
        if (item.title == nombre) {
            return item
        }
    }
    return null
}

fun ImageView.loadUrl(url: String) {
    this.load(url) {
        scale(Scale.FIT)
        crossfade(true)
        transformations(RoundedCornersTransformation(20f))
        placeholder(R.drawable.arrows_rotate_solid)
        error(R.drawable.ic_launcher_foreground)
    }
}