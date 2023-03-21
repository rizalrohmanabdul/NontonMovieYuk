package com.rizal.nontonmovieyuk.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.rizal.nontonmovieyuk.R

class ButtonRetry @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflateView()
    }

    private fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.layout_retry_button, this)
    }
}