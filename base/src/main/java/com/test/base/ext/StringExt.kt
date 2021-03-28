package com.test.base.ext

private const val SCHEME_HTTP = "http"
private const val SCHEME_HTTPS = "https"

fun String.normalizeUrl(): String {
    return if (startsWith(SCHEME_HTTP)) {
        this
    } else {
        "${SCHEME_HTTPS}://$this"
    }
}

fun String?.nullIfEmpty(): String? =
    if (this == null || this.trim().isEmpty()) {
        null
    } else {
        this
    }