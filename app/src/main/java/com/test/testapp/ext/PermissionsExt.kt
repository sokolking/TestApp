package com.test.testapp.ext

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.hasLocationPermissions(): Boolean = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

fun Fragment.hasCameraPermissions(): Boolean = hasPermission(Manifest.permission.CAMERA)

fun Fragment.hasStoragePermissions(): Boolean = hasPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)

fun Fragment.checkLocationPermissions(permissionLauncher: ActivityResultLauncher<String>): Boolean =
    if (hasLocationPermissions()) {
        true
    } else {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        false
    }

fun Fragment.checkCameraPermissions(permissionLauncher: ActivityResultLauncher<String>): Boolean =
    if (hasCameraPermissions()) {
        true
    } else {
        permissionLauncher.launch(Manifest.permission.CAMERA)
        false
    }

fun Fragment.checkStoragePermissions(permissionLauncher: ActivityResultLauncher<String>): Boolean =
    if (hasStoragePermissions()) {
        true
    } else {
        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        false
    }

private fun Fragment.hasPermission(permission: String): Boolean = hasPermissions(permission)

private fun Fragment.hasPermissions(vararg permissions: String): Boolean = requireContext().hasPermissions(*permissions)

private fun Context.hasPermissions(vararg permissions: String): Boolean =
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        true
    } else {
        permissions.all {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }