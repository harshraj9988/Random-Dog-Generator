package com.harshlabs.randomdoggenerator.utils

import android.content.Context
import android.widget.Toast

fun Context.toToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}