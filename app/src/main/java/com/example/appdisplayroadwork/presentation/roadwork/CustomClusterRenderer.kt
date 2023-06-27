package com.example.appdisplayroadwork.presentation.roadwork

import android.content.Context
import com.example.appdisplayroadwork.R
import com.example.appdisplayroadwork.core.util.Utils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class CustomClusterRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<MarkerViewState>
) : DefaultClusterRenderer<MarkerViewState>(context, map, clusterManager) {
    override fun onBeforeClusterItemRendered(
        marker: MarkerViewState,
        markerOptions: MarkerOptions
    ) {
        // marker item
        val closeMarker =
            Utils.bitmapDescriptorFromVector(context, R.drawable.icon_location_roadwork_close)
        val workingMarker =
            Utils.bitmapDescriptorFromVector(context, R.drawable.icon_location_roadwork_working)

        markerOptions.position(LatLng(marker.lat, marker.long))
            .title(marker.title)
        if (marker.status.isEmpty() || marker.status == "Closed") {
            markerOptions.icon(closeMarker)
        } else {
            markerOptions.icon(workingMarker)
        }
        markerOptions.draggable(true)
        super.onBeforeClusterItemRendered(marker, markerOptions)
    }

    override fun onBeforeClusterRendered(
        cluster: Cluster<MarkerViewState>,
        markerOptions: MarkerOptions
    ) {
        //  cluster marker
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        super.onBeforeClusterRendered(cluster, markerOptions)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<MarkerViewState>): Boolean {
        // size cluster
        return cluster.size > 4
    }
}