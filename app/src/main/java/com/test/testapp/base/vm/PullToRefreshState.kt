package com.test.testapp.base.vm

data class PullToRefreshState(
    val enabled: Boolean,
    val refreshing: Boolean
)