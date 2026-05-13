package com.hunterlindsay.kmpcommercedemo.concerns.deprecated.parks

import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTCall
import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTCallResponse
import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTCallType
import com.hunterlindsay.kmpcommercedemo.core.networking.rest_client.RESTClientService

/**
 * Created by Hunter Lindsay on 11/05/2026.
 */

class ParksAPI(
    private val restClientService: RESTClientService
) {
    suspend fun retrieveParks(): RESTCallResponse {
        val call = RESTCall(
            callDescription = "Retrieve parks",
            callType = RESTCallType.GET,
            urlString = PARKS_URL,
            customBody = null,
            customHeaders = null,
            responseCodeDictionary = null
        )

        return restClientService.makeRESTCall(call)
    }

    private companion object {
        const val PARKS_URL = "https://services1.arcgis.com/cNVyNtjGVZybOQWZ/arcgis/rest/services/Parks/FeatureServer/0/query?outFields=*&where=1%3D1&f=geojson"
    }
}