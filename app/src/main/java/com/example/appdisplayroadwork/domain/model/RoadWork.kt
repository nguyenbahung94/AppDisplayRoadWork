package com.example.appdisplayroadwork.domain.model

import com.example.appdisplayroadwork.presentation.detail_roadwork.DetailRoadWorkViewState
import com.example.appdisplayroadwork.presentation.roadwork.MarkerViewState


data class RoadWork(
    val features: List<Feature>?,
    val lastPublished: Long,
    val layerName: String?,
    val rights: Rights?,
    val type: String?
) {
    fun toMarkerViewState(): List<MarkerViewState> {
        val listMarkerViewState = arrayListOf<MarkerViewState>()
        features?.let { feature ->
            feature.forEach { item ->
                var newStatus = ""
                if (item.properties.periods.isNotEmpty()) {
                    newStatus = item.properties.periods[0].roadextent ?: ""
                }

                listMarkerViewState.add(
                    MarkerViewState(
                        id = item.id,
                        lat = item.geometry.coordinates[1],
                        long = item.geometry.coordinates[0],
                        status = newStatus,
                        mtitle = item.properties.displayName ?: "",
                        mainStreet = item.properties.roads?.get(0)?.mainStreet ?: "",
                        suburb = item.properties.roads?.get(0)?.suburb ?: ""
                    )
                )
            }

        }
        return listMarkerViewState
    }
}

data class Feature(
    val geometry: Geometry,
    val id: Int,
    val properties: Properties,
    val type: String?
) {
    fun toDetailRoadWorkViewState(): DetailRoadWorkViewState {
        var newStatus = ""
        if (properties.periods.isNotEmpty()) {
            newStatus = properties.periods[0].roadextent ?: ""
        }
        return DetailRoadWorkViewState(
            lat = geometry.coordinates[1],
            long = geometry.coordinates[0],
            suburb = properties.roads?.get(0)?.suburb ?: "",
            mainStreet = properties.roads?.get(0)?.mainStreet ?: "",
            advice = properties.adviceA + "\n" + properties.adviceB + "\n" + properties.adviceC,
            otherAdvice = properties.otherAdvice ?: "",
            diversions = properties.diversions ?: "",
            title = properties.displayName ?: "",
            status = newStatus
        )
    }
}

data class Rights(
    val copyright: String?,
    val licence: String?
)

data class Geometry(
    val collections: List<Collection>?,
    val coordinates: List<Double>,
    val type: String?
)

data class Properties(
    val adviceA: String?,
    val adviceB: String?,
    val adviceC: String?,
    val created: Long?,
    val displayName: String?,
    val encodedPolylines: List<EncodedPolyline>?,
    val end: Long,
    val ended: Boolean?,
    val expectedDelay: Int?,
    val headline: String?,
    val impactingNetwork: Boolean?,
    val isNewIncident: Boolean?,
    val lastUpdated: Long?,
    val mainCategory: String?,
    val otherAdvice: String?,
    val diversions: String?,
    val periods: List<Period>,
    val roads: List<Road>?,
    val speedLimit: Int,
    val start: Long,
    val subCategoryA: String?,
)

data class Collection(
    val coordinates: List<Double>?,
    val type: String?
)

data class EncodedPolyline(
    val coords: String?,
    val levels: String?
)

data class Period(
    val closureType: String?,
    val direction: String?,
    val finishTime: String?,
    val fromDay: String?,
    val roadextent: String?,
    val roadtype: String?,
    val startTime: String?,
    val toDay: String?
)

data class Road(
    val crossStreet: String?,
    val locationQualifier: String?,
    val mainStreet: String?,
    val region: String?,
    val suburb: String?,
)
