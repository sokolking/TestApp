package com.test.testapp.ext

import androidx.annotation.DrawableRes
import coil.request.ImageRequest
import coil.target.ImageViewTarget

fun ImageRequest.Builder.errorImage(@DrawableRes image: Int) {
    listener(
        onError = { request, _ ->
            (request.target as? ImageViewTarget)?.view?.setImageResource(image)
        }
    )
}