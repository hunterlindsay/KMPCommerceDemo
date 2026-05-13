package com.hunterlindsay.kmpcommercedemo.concerns.deprecated.parks

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * Created by Hunter Lindsay on 11/05/2026.
 */

class ParksGeoJsonParser(
    private val json: Json = Json {
        ignoreUnknownKeys = true
    }
) {
    fun parse(rawJson: String): List<Park> {
        val rawParksGeoJson = json.decodeFromString<RawParksGeoJson>(rawJson)

        return rawParksGeoJson.features.mapNotNull { feature ->
            val properties = feature.properties

            val id = properties.assetId
                ?: properties.objectId?.toString()
                ?: return@mapNotNull null

            val name = properties.name
                ?: return@mapNotNull null

            Park(
                id = id,
                name = name,
                geometry = parseGeometry(feature.geometry),
                hasPlayground = properties.playgrounds.equals("Yes", ignoreCase = true)
            )
        }
    }

    private fun parseGeometry(rawGeometry: RawParkGeometry): ParkGeometry {
        return when (rawGeometry.type) {
            "Polygon" -> {
                val rawPolygon = json.decodeFromJsonElement<List<List<List<Double>>>>(
                    rawGeometry.coordinates
                )

                ParkGeometry.Polygon(
                    rings = rawPolygon.map { rawRing ->
                        rawRing.map { rawCoordinate ->
                            parseCoordinate(rawCoordinate)
                        }
                    }
                )
            }

            "MultiPolygon" -> {
                val rawMultiPolygon = json.decodeFromJsonElement<List<List<List<List<Double>>>>>(
                    rawGeometry.coordinates
                )

                ParkGeometry.MultiPolygon(
                    polygons = rawMultiPolygon.map { rawPolygon ->
                        rawPolygon.map { rawRing ->
                            rawRing.map { rawCoordinate ->
                                parseCoordinate(rawCoordinate)
                            }
                        }
                    }
                )
            }

            else -> error("Unsupported park geometry type: ${rawGeometry.type}")
        }
    }

    private fun parseCoordinate(rawCoordinate: List<Double>): Coordinate {
        val longitude = rawCoordinate.getOrNull(0)
            ?: error("GeoJSON coordinate missing longitude")

        val latitude = rawCoordinate.getOrNull(1)
            ?: error("GeoJSON coordinate missing latitude")

        return Coordinate(
            latitude = latitude,
            longitude = longitude
        )
    }
}