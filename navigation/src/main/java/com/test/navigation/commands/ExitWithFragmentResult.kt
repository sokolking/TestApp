package com.test.navigation.commands

import android.os.Bundle
import com.github.terrakok.cicerone.Command

data class ExitWithFragmentResult(
    val requestCode: String,
    val data: Bundle
) : Command