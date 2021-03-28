package com.test.domain.api.comments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
        var postId: Int? = null,
        var id: Int? = null,
        var name: String? = null,
        var email: String? = null,
        var body: String? = null
) : Parcelable
