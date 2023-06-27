package com.example.appdisplayroadwork.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object Utils {

    fun parseErrorMessage(enumError: EnumError?): String {
        return when (enumError) {
            EnumError.NO_DATA -> "No Data"
            EnumError.NO_NETWORK -> "Couldn't reach server, check your internet connection."
            EnumError.SOMETHING_WRONG -> "Something went wrong!"
            else -> {
                return "Something went wrong!"
            }
        }
    }

     fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

}
