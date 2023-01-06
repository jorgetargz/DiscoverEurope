package com.jorgetargz.europa.ui.utils

import android.view.*
import android.widget.ImageView
import android.widget.ListAdapter
import androidx.core.view.forEach
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.jorgetargz.europa.R

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Menu.findItemByTitle(title: String): MenuItem? {
    forEach { menuItem ->
        if (menuItem.title == title) {
            return menuItem
        }
    }
    return null
}

fun ListAdapter?.getPosition(toString: String): Int {
    for (i in 0 until this?.count!!) {
        if (getItem(i).toString() == toString) {
            return i
        }
    }
    return -1
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