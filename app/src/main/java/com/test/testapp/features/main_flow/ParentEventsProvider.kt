package com.test.testapp.features.main_flow

import kotlinx.coroutines.flow.Flow

interface ParentEventsProvider <T , D> {

    fun getParentEventsChannel() : Flow<T>

    fun onChildEvent(event: D)
}