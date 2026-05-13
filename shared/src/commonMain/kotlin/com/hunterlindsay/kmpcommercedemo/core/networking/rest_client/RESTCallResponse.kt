package com.hunterlindsay.kmpcommercedemo.core.networking.rest_client

/**
 * Created by Hunter Lindsay on 11/05/2026.
 */

class RESTCallResponse(val success: Boolean, val statusCode: Int?, val responseBodyString: String?, errorMessage: String? = null) {
     val errorMessage: String? = errorMessage ?: if (!success) "Unknown error" else null
}