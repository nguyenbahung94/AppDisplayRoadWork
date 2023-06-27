package com.example.appdisplayroadwork.presentation.roadwork

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdisplayroadwork.R
import com.example.appdisplayroadwork.databinding.ActivityMainRoadWorkBinding
import com.example.appdisplayroadwork.core.network.ConnectivityObserver
import com.example.appdisplayroadwork.core.network.NetworkConnectivityObserver
import com.example.appdisplayroadwork.presentation.detail_roadwork.DetailRoadWorkActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainRoadWorkActivity : AppCompatActivity(), OnMapReadyCallback,
    ClusterManager.OnClusterClickListener<MarkerViewState>,
    ClusterManager.OnClusterItemClickListener<MarkerViewState> {
    private lateinit var binding: ActivityMainRoadWorkBinding
    private val viewModel by viewModels<MainRoadWorkViewModel>()
    private var mMap: GoogleMap? = null
    private val roadWorkAdapter = RoadWorkAdapter(::onItemClicked)
    private var currentLngMarker = LatLng(DEFAULT_LAT, DEFAULT_LONG)
    private lateinit var connectivityObserver: ConnectivityObserver

    private lateinit var clusterManager: ClusterManager<MarkerViewState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRoadWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        binding.mMapView.onCreate(savedInstanceState)
        binding.mMapView.getMapAsync(this)
        init()
        event()
    }


    private fun event() {
        with(binding) {
            btnMapView.setOnClickListener {
                switchLayout(true)
            }
            btnListView.setOnClickListener {
                switchLayout(false)
            }
            imvRefreshData.setOnClickListener {
                viewModel.getRoadWork(currentLngMarker)
            }
        }
    }

    private fun init() {
        viewModel.viewState.observe(this) { viewState ->
            updateMarkerUi(viewState)
        }

        binding.rcvRoadWork.apply {
            setHasFixedSize(true)
            isMotionEventSplittingEnabled = true
            layoutManager = LinearLayoutManager(this@MainRoadWorkActivity)
            adapter = roadWorkAdapter
        }
    }

    private fun updateMarkerUi(viewState: MainRoadWorkViewState) {
        with(binding) {
            when (viewState) {
                MainRoadWorkViewState.Loading -> {
                    loadingView.isVisible = true
                }
                is MainRoadWorkViewState.Error -> {
                    loadingView.isVisible = false
                    showUiError(errorView)
                }
                is MainRoadWorkViewState.NetWorkError -> {
                    loadingView.isVisible = false
                    listenerNetwork()
                    showUiError(errorNetWorkView)
                }
                is MainRoadWorkViewState.Content -> {
                    loadingView.isVisible = false
                    addMarkerOnMap(viewState.listMarker)
                    roadWorkAdapter.submitData(viewState.listMarker)
                }
            }
        }

    }

    private fun showUiError(view: TextView) {
        view.isVisible = true
        view.postDelayed({
            view.isVisible = false
        }, 5000)
    }

    private fun addMarkerOnMap(listMarker: List<MarkerViewState>) {
        listMarker.forEach {
            clusterManager.addItem(it)
        }
        clusterManager.cluster()
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLngMarker, 5f))
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

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.getRoadWork(currentLngMarker)
        mMap = googleMap
        clusterManager = ClusterManager(this, googleMap)
        mMap?.setOnCameraIdleListener(clusterManager)
        clusterManager.setOnClusterClickListener(this)
        clusterManager.setOnClusterItemClickListener(this)

        clusterManager.renderer =
            CustomClusterRenderer(this@MainRoadWorkActivity, googleMap, clusterManager)

        mMap?.addMarker(
            MarkerOptions().position(LatLng(currentLngMarker.latitude, currentLngMarker.longitude))
                .title(getString(R.string.tv_default_marker))
        )?.showInfoWindow()
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLngMarker, 5f))
    }

    private fun onItemClicked(viewState: MarkerViewState) {
        openDetailRoadWork(viewState.id)
    }

    private fun switchLayout(isMainMap: Boolean) {
        with(binding) {
            if (isMainMap) {
                btnMapView.setBackgroundResource(R.drawable.background_order_orange)
                btnListView.background = null
                mMapView.isVisible = true
                rcvRoadWork.isVisible = false
            } else {
                btnListView.setBackgroundResource(R.drawable.background_order_orange)
                btnMapView.background = null
                mMapView.isVisible = false
                rcvRoadWork.isVisible = true
            }
        }

    }

    private fun listenerNetwork() {
        connectivityObserver.observe().onEach {
            if (it.toString() == "Available") {
                viewModel.getRoadWork(currentLngMarker)
            } else {
                showUiError(binding.errorNetWorkView)
            }
        }.launchIn(lifecycleScope)
    }

    private fun openDetailRoadWork(id: Int) {
        val feature = viewModel.getFeatureById(id)
        feature?.let {
            val detailStateView = it.toDetailRoadWorkViewState()
            DetailRoadWorkActivity.startActivity(detailStateView, this@MainRoadWorkActivity)
        }

    }

    companion object {
        const val DEFAULT_LAT = -32.422878
        const val DEFAULT_LONG = 146.275678
    }

    override fun onClusterClick(cluster: Cluster<MarkerViewState>?): Boolean {
        val zoomLevel = when (cluster?.size) {
            in 1..9 -> 12f
            in 10..20 -> 11f
            in 21..75 -> 10f
            else -> 8f
        }
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster?.position ?: currentLngMarker, zoomLevel))
        return true
    }

    override fun onClusterItemClick(item: MarkerViewState?): Boolean {
        item?.let {
            openDetailRoadWork(it.id)
        }
        return true
    }

}