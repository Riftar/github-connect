package com.riftar.common.helper

import android.view.View
import android.widget.TextView

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.showOrHide(isShow: Boolean, hideVisibility: Int = View.GONE) {
    visibility = if (isShow) View.VISIBLE else hideVisibility
}

fun View.showOrHide(text: String?, hideVisibility: Int = View.GONE) {
    visibility = if (!text.isNullOrEmpty()) View.VISIBLE else hideVisibility
}

fun TextView.setOrHide(text: String?, hideVisibility: Int = View.GONE) {
    if (!text.isNullOrEmpty()) {
        this.text = text
        this.visibility = View.VISIBLE
    } else {
        this.visibility = hideVisibility
    }
}