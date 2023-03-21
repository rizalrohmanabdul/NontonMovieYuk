package com.rizal.nontonmovieyuk.utils


import android.widget.TextView

object TextUtils {

    fun TextView.loadMovieRuntime(runtimeInMinutes: Int?) {
        runtimeInMinutes?.let {
            val hoursText: String = appendZeroBeforeNumber((it / 60f).toInt())
            val minutesText: String = appendZeroBeforeNumber((it % 60f).toInt())
            val text = "$hoursText:$minutesText / $runtimeInMinutes min"
            this.text = text
        }
    }

    private fun appendZeroBeforeNumber(num: Int): String {
        return if (num < 10) "0$num" else num.toString()
    }
}