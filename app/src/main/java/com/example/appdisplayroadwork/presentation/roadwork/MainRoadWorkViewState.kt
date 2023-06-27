package com.example.appdisplayroadwork.presentation.roadwork


sealed class MainRoadWorkViewState {
    object Loading : MainRoadWorkViewState()
    class Error(val message: String) : MainRoadWorkViewState()
    object NetWorkError:MainRoadWorkViewState()
    data class Content(val listMarker: List<MarkerViewState>) : MainRoadWorkViewState()
}