package com.test.testapp.base.insets

import androidx.core.view.WindowInsetsCompat
import com.test.base.ext.BottomInsetsType

interface InsetsConsumer {

    fun consumeInsets(
        insets: WindowInsetsCompat,
        bottomInsetsType: BottomInsetsType
    ): WindowInsetsCompat
}