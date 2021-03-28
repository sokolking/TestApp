package com.test.base.log

import android.util.Log

object TestAppLog {

    private const val DEFAULT_TAG = "MIINE"

    fun log(log: String) {
        log(log, DEFAULT_TAG)
    }

    fun log(log: String, tag: String) {
        Log.d(tag, log)
    }
}