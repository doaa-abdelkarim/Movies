package com.example.movies.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movies.R

@BindingAdapter("imageURL")
fun ImageView.loadImage(imageUri: Uri?) {
    imageUri?.let {
        Glide
            .with(this)
            .load(imageUri)
            .centerCrop()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(this)
    }
}




