package com.rizal.nontonmovieyuk.utils

import android.content.SharedPreferences

class AppPreferences(sharedPreferences: SharedPreferences) {

    private var pref: SharedPreferences = sharedPreferences
    private var editor: SharedPreferences.Editor = pref.edit()

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
    }

    var movieTokenAccess: String
        get() {
            return pref.getString(KEY_TOKEN, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkYjllZTdlM2QxYWVmNjM5Nzg2MjNmZjMyYmEzOTIyMSIsInN1YiI6IjVlM2VkM2RhNDE0NjVjMDAxNGM5NmRlMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lpQR7o3yBAflQC-r0BryMRR_QeGgvu6OS6n2TzaqVI8").orEmpty()
        }
        set(value) {
            editor.putString(KEY_TOKEN, value)
            editor.apply()
        }
}