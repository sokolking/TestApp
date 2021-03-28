package com.test.testapp.base.properties

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow

fun <T : Any?> Flow<T>.observeEvents(
    fragment: Fragment,
    propertyObserver: suspend (T) -> Unit
): EventsObserver<T> = EventsObserver(fragment.viewLifecycleOwner, this, propertyObserver)