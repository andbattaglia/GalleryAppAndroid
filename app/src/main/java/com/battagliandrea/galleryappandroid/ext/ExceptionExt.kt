package com.battagliandrea.galleryappandroid.ext

import android.content.Context
import com.battagliandrea.galleryappandroid.R

fun Context?.getErrorMessage(errorCode: Int): String {
    if(this != null){
        return when(errorCode){
            1 -> { this.getString(R.string.error_message_1) }
            2 -> { this.getString(R.string.error_message_2) }
            3 -> { this.getString(R.string.error_message_3) }
            else -> { this.getString(R.string.error_message_0) }
        }
    } else {
        return "Error!!"
    }
}