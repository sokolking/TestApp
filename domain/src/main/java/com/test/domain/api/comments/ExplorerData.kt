package com.test.domain.api.comments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExplorerData(
    var imageRes: Int = 1,
    var title: String? = null,
    var job: String? = null,
    var location: String? = null,
    var type: String? = null,
    var salary: String? = null,
    var percent: String? = null
) : Parcelable
