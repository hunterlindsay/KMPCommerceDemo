package com.hunterlindsay.kmpcommercedemo.core.networking.rest_client

import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess

/**
 * Created by Hunter Lindsay on 11/05/2026.
 */

class RESTClientService(
    private val httpClient: HttpClient
) {
    suspend fun makeRESTCall(restCall: RESTCall): RESTCallResponse {
        if (restCall.retriesRemaining <= 0) {
            return RESTCallResponse(
                success = false,
                statusCode = null,
                responseBodyString = null,
                errorMessage = "Retries exhausted"
            )
        }

        return try {
            val response = httpClient.request(restCall.urlString) {
                method = restCall.callType.toKtorMethod()

                headers {
                    append("Accept", "application/json")
                    append("Content-Type", "application/json")

                    restCall.customHeaders?.forEach { (key, value) ->
                        append(key, value)
                    }
                }

                restCall.customBody?.let { body ->
                    setBody(body)
                }
            }

            RESTCallResponse(
                success = response.status.isSuccess(),
                statusCode = response.status.value,
                responseBodyString = response.bodyAsText(),
                errorMessage = null
            )
        } catch (throwable: Throwable) {
            restCall.decrementRetries()
            makeRESTCall(restCall)
        }
    }

    private fun RESTCallType.toKtorMethod(): HttpMethod {
        return when (this) {
            RESTCallType.GET -> HttpMethod.Get
            RESTCallType.POST -> HttpMethod.Post
            RESTCallType.PUT -> HttpMethod.Put
            RESTCallType.DELETE -> HttpMethod.Delete
        }
    }
}