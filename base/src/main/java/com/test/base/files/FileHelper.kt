package com.test.base.files

import android.content.Context
import java.io.File

class FileHelper(
    private val context: Context
) {

    fun createTempImageFile(): File =
        FileUtils.createTempImageFile(context)
}