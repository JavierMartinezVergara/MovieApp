package com.example.movieapp.presentation.util

import android.widget.ImageView
import android.widget.ImageView.ScaleType.CENTER_CROP
import coil.load
import coil.size.Scale.FILL
import coil.transform.RoundedCornersTransformation
import com.example.movieapp.R

fun ImageView.loadRoundedImage(url: String) {
    this.load(url) {
        scaleType = CENTER_CROP
        scale(FILL)
        transformations(RoundedCornersTransformation(30f, 30f, 30f, 30f))
        crossfade(true)
        crossfade(500)
        placeholder(R.drawable.border)
    }
}
