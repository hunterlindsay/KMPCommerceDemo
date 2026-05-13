package com.hunterlindsay.kmpcommercedemo.concerns.deprecated.parks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Created by Hunter Lindsay on 11/05/2026.
 */

@Serializable
data class RawParksGeoJson(
    val type: String,
    val features: List<RawParkFeature>
)

@Serializable
data class RawParkFeature(
    val type: String,
    val id: Int? = null,
    val geometry: RawParkGeometry,
    val properties: RawParkProperties
)

@Serializable
data class RawParkGeometry(
    val type: String,
    val coordinates: JsonElement
)

@Serializable
data class RawParkProperties(
    @SerialName("OBJECTID")
    val objectId: Int? = null,

    @SerialName("Asset_ID")
    val assetId: String? = null,

    @SerialName("Type")
    val type: String? = null,

    @SerialName("Name")
    val name: String? = null,

    @SerialName("Playgrounds")
    val playgrounds: String? = null,

    @SerialName("ESRI_OID")
    val esriOid: Int? = null,

    @SerialName("Shape__Area")
    val shapeArea: Double? = null,

    @SerialName("Shape__Length")
    val shapeLength: Double? = null
)