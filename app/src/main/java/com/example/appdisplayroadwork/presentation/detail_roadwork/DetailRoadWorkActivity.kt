package com.example.appdisplayroadwork.presentation.detail_roadwork

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.appdisplayroadwork.R
import com.example.appdisplayroadwork.core.util.Utils
import com.example.appdisplayroadwork.core.util.parcelable
import com.example.appdisplayroadwork.databinding.ActivityDetailRoadWorkBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailRoadWorkActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityDetailRoadWorkBinding
    private var detailViewState: DetailRoadWorkViewState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRoadWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mMapView.onCreate(savedInstanceState)
        binding.mMapView.getMapAsync(this)
        detailViewState = intent.parcelable(STATE_DETAIL)
        init()
    }


    private fun init() {
        binding.toolBar.toolbarTitle.text = getString(R.string.tv_detail_road_work)
        binding.toolBar.imvBack.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(mMap: GoogleMap) {
        mMap.uiSettings.setAllGesturesEnabled(true)
        val closeMarker = Utils.bitmapDescriptorFromVector(this, R.drawable.icon_location_roadwork_close)
        val workingMarker = Utils.bitmapDescriptorFromVector(this, R.drawable.icon_location_roadwork_working)
        detailViewState?.let { state ->
            val lng = LatLng(state.lat, state.long)
            val markerOptions = MarkerOptions()
                .position(LatLng(state.lat, state.long))

            if (state.status.isEmpty() || state.status == "Closed") {
                markerOptions.icon(closeMarker).title("Closed")
            } else {
                markerOptions.icon(workingMarker).title(state.title)
            }
            mMap.addMarker(
                markerOptions
            )?.showInfoWindow()
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lng, 13f))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13f))
            binUi(state)
        }

    }

    private fun binUi(viewState: DetailRoadWorkViewState) {
        with(binding) {
            tvSubNub.text = viewState.suburb.ifEmpty { "N/A" }
            tvMainStreet.text = viewState.mainStreet
            tvAdvice.text = viewState.advice
            tvOtherAdvice.text =
                HtmlCompat.fromHtml(viewState.otherAdvice, HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvDiversions.text = HtmlCompat.fromHtml(
                viewState.diversions.ifEmpty { "N/A" },
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mMapView.onResume()
    }

    override fun onStop() {
        super.onStop()
        binding.mMapView.onStop()
    }

    override fun onPause() {
        binding.mMapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.mMapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mMapView.onLowMemory()
    }

    companion object {
        const val STATE_DETAIL = "state_detail"
        fun startActivity(viewState: DetailRoadWorkViewState, context: Context) {
            context.startActivity(Intent(context, DetailRoadWorkActivity::class.java).apply {
                putExtra(STATE_DETAIL, viewState)
            })
        }
    }
}