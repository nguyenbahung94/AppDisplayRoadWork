package com.example.appdisplayroadwork.presentation.roadwork

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdisplayroadwork.core.util.EnumError
import com.example.appdisplayroadwork.core.util.Resource
import com.example.appdisplayroadwork.core.util.Utils.parseErrorMessage
import com.example.appdisplayroadwork.domain.model.Feature
import com.example.appdisplayroadwork.domain.model.RoadWork
import com.example.appdisplayroadwork.domain.use_case.GetRoadWorkInfo
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainRoadWorkViewModel @Inject constructor(private val getRoadWorkInfo: GetRoadWorkInfo) :
    ViewModel() {
    private val _viewState = MutableLiveData<MainRoadWorkViewState>()
    val viewState: LiveData<MainRoadWorkViewState>
        get() = _viewState

    private var fullDataRoadWork: RoadWork? = null
    private var getRoadWorkJob: Job? = null

    fun getRoadWork(lng: LatLng) {
        getRoadWorkJob?.cancel()
        getRoadWorkJob = viewModelScope.launch {
            getRoadWorkInfo(lng.latitude, lng.longitude).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { roadWorkData ->
                            fullDataRoadWork = roadWorkData
                            _viewState.postValue(MainRoadWorkViewState.Content(roadWorkData.toMarkerViewState()))
                        } ?: _viewState.postValue(
                            MainRoadWorkViewState.Error(
                                parseErrorMessage(
                                    EnumError.NO_DATA
                                )
                            )
                        )

                    }
                    is Resource.Error -> {
                        _viewState.postValue(
                            if (result.message == EnumError.NO_NETWORK) {
                                MainRoadWorkViewState.NetWorkError
                            } else {
                                MainRoadWorkViewState.Error(
                                    parseErrorMessage(result.message)
                                )
                            }
                        )
                    }
                    is Resource.Loading -> {
                        _viewState.postValue(
                            MainRoadWorkViewState.Loading
                        )
                    }
                }

            }.launchIn(this)
        }
    }

    fun getFeatureById(id: Int): Feature? {
        return fullDataRoadWork?.features?.find { it.id == id }
    }
}