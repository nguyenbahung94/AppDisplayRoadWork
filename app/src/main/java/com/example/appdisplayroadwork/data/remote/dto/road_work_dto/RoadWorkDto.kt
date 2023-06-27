package com.example.appdisplayroadwork.data.remote.dto.road_work_dto

import com.example.appdisplayroadwork.domain.model.*
import com.example.appdisplayroadwork.domain.model.Collection


data class RoadWorkDto(
    val features: List<FeatureDto>?,
    val lastPublished: Long,
    val layerName: String?,
    val rights: RightsDto?,
    val type: String?
) {
    fun toRoadWork(): RoadWork {
        val newFeatures = arrayListOf<Feature>()
        features?.forEach { newFeatures.add(it.toFeature()) }
        return RoadWork(
            features = newFeatures,
            lastPublished = lastPublished,
            layerName = layerName,
            rights = rights?.toRight(),
            type = type

        )
    }
}

data class FeatureDto(
    val geometry: GeometryDto,
    val id: Int,
    val properties: PropertiesDto,
    val type: String?
) {
    fun toFeature(): Feature {
        val newGeometry = geometry.toGeometry()
        val newproperties = properties.toProperties()
        return Feature(
            geometry = newGeometry, id = id, properties = newproperties, type = type

        )
    }
}

data class RightsDto(
    val copyright: String?,
    val licence: String?
) {
    fun toRight(): Rights {
        return Rights(
            copyright = copyright,
            licence = licence
        )
    }
}

data class GeometryDto(
    val collections: List<CollectionDto>?,
    val coordinates: List<Double>,
    val type: String?
) {
    fun toGeometry(): Geometry {
        val newCollection = arrayListOf<Collection>()
        collections?.forEach { newCollection.add(it.toCollection()) }
        return Geometry(
            collections = newCollection, coordinates = coordinates, type = type

        )
    }
}

data class PropertiesDto(
    val adviceA: String?,
    val adviceB: String?,
    val adviceC: String?,
    val created: Long?,
    val displayName: String?,
    val encodedPolylines: List<EncodedPolylineDto>?,
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
    val periods: List<PeriodDto>?,
    val roads: List<RoadDto>?,
    val speedLimit: Int,
    val start: Long,
    val subCategoryA: String?,
) {
    fun toProperties(): Properties {
        val newEncodedPolylines = arrayListOf<EncodedPolyline>()
        encodedPolylines?.forEach { newEncodedPolylines.add(it.toEncodedPolyline()) }

        val newPeriods = arrayListOf<Period>()
        periods?.forEach { newPeriods.add(it.toPeriod()) }

        val newRoads = arrayListOf<Road>()
        roads?.forEach { newRoads.add(it.toRoad()) }

        return Properties(
            adviceA = adviceA,
            adviceB = adviceB,
            adviceC = adviceC,
            created = created,
            displayName = displayName,
            encodedPolylines = newEncodedPolylines,
            end = end,
            ended = ended,
            expectedDelay = expectedDelay,
            headline = headline,
            impactingNetwork = impactingNetwork,
            isNewIncident = isNewIncident,
            lastUpdated = lastUpdated,
            mainCategory = mainCategory,
            otherAdvice = otherAdvice,
            diversions = diversions,
            periods = newPeriods,
            roads = newRoads,
            speedLimit = speedLimit,
            start = start,
            subCategoryA = subCategoryA

        )
    }
}

data class CollectionDto(
    val coordinates: List<Double>?,
    val type: String?
) {
    fun toCollection(): Collection {
        return Collection(
            coordinates = coordinates, type = type

        )
    }
}

data class EncodedPolylineDto(
    val coords: String?,
    val levels: String?
) {
    fun toEncodedPolyline(): EncodedPolyline {
        return EncodedPolyline(
            coords = coords, levels = levels
        )
    }
}

data class PeriodDto(
    val closureType: String?,
    val direction: String?,
    val finishTime: String?,
    val fromDay: String?,
    val roadextent: String?,
    val roadtype: String?,
    val startTime: String?,
    val toDay: String?
) {
    fun toPeriod(): Period {
        return Period(
            closureType = closureType,
            direction = direction,
            finishTime = finishTime,
            fromDay = fromDay,
            roadextent = roadextent,
            roadtype = roadtype,
            startTime = startTime,
            toDay = toDay
        )
    }
}

data class RoadDto(
    val crossStreet: String?,
    val locationQualifier: String?,
    val mainStreet: String?,
    val region: String?,
    val suburb: String?,
) {
    fun toRoad(): Road {
        return Road(
            crossStreet = crossStreet,
            locationQualifier = locationQualifier,
            mainStreet = mainStreet,
            region = region,
            suburb = suburb

        )
    }
}
