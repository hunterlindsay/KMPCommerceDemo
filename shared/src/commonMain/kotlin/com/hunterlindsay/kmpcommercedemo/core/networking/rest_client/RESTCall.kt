package com.hunterlindsay.kmpcommercedemo.core.networking.rest_client

/**
 * Created by Hunter Lindsay on 11/05/2026.
 */

class RESTCall(
    val callDescription: String,
    val callType: RESTCallType,
    val urlString: String,
    val customBody: String? = null,
    var customHeaders: MutableMap<String, String>? = null,
    var responseCodeDictionary: Map<Int, Any>? = null,
    var retriesRemaining: Int = 2,
) {
    /**
     * A stable identity for deduping calls in the REST client.
     * We intentionally exclude mutable fields (headers, retries) so map lookups remain valid.
     */
    val dedupeKey: String = buildString {
        append(callType.name)
        append(':')
        append(urlString)
    }

    fun decrementRetries() {
        retriesRemaining--
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RESTCall) return false
        return dedupeKey == other.dedupeKey
    }

    override fun hashCode(): Int = dedupeKey.hashCode()
}
