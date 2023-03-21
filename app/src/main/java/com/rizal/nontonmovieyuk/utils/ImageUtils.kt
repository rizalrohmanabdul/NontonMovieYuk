package com.rizal.nontonmovieyuk.utils

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rizal.nontonmovieyuk.R
import java.util.*

object ImageUtils {

    fun ImageView.load(context: Context, url:String) {
        Glide.with(context)
            .load(url.trim())
            .placeholder(R.drawable.baseline_account_box_24)
            .into(this)
    }

    fun ImageView.loadCircle(context: Context, url:String) {
        Glide.with(context)
            .load(url.trim())
            .circleCrop()
            .into(this)
    }

    fun getRandomColor(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
}