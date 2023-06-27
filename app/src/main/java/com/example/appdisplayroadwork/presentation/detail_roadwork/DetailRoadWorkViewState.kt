package com.example.appdisplayroadwork.presentation.detail_roadwork

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailRoadWorkViewState(
    val lat: Double,
    val long: Double,
    val suburb: String,
    val mainStreet: String,
    val advice: String,
    val otherAdvice: String,
    val diversions: String,
    val title:String,
    val status:String
) : Parcelable