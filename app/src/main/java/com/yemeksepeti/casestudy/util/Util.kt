package com.yemeksepeti.casestudy.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*

@BindingAdapter(value = ["imgUrl"])
fun ImageView.loadImageFromUrl(url: String?) {
    Glide.with(this.context).load(url).into(this)
}

fun String.fullPosterPath() = "https://image.tmdb.org/t/p/w500$this"