package com.example.appdisplayroadwork.presentation.roadwork

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MarkerViewState(
    val status: String,
    val lat: Double,
    val long: Double,
    val id: Int,
    val mtitle: String,
    val mainStreet: String,
    val suburb: String
    ) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(lat, long)

    override fun getTitle(): String = mtitle

    override fun getSnippet(): String? = null
    override fun getZIndex(): Float? =null
}