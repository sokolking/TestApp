package com.test.base.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

private const val DEFAULT_EMAIL_SCHEME = "mailto"
private const val DEFAULT_SHARE_TEXT_TYPE = "text/plain"
private const val NL_PHONE_START_WHATS_APP = "06"
private const val NL_PHONE_WHATS_APP_COUNTRY_CODE = "+316"

class IntentHelper(
    private val context: Context
) {

    fun openUrl(url: String, onError: (Throwable) -> Unit = {}) {
        val original = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url.normalizeUrl())
        }
        startIntent(original, onError)
    }

    fun openEmailApp(onError: (Throwable) -> Unit = {}) {
        val original = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_EMAIL)
        }
        startIntent(original, onError)
    }

    fun makeCall(
        number: String,
        onError: (Throwable) -> Unit
    ) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startIntent(intent, onError)
    }

    fun sendEmail(
        email: String,
        onError: (Throwable) -> Unit
    ) {
        val intent = Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts(
                DEFAULT_EMAIL_SCHEME,
                email,
                null
            )
        )
        startIntent(intent, onError)
    }

    fun shareText(
        subject: String,
        text: String,
        onError: (Throwable) -> Unit
    ) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = DEFAULT_SHARE_TEXT_TYPE
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startIntent(intent, onError)
    }

    fun openWebLink(
        link: String,
        onError: (Throwable) -> Unit
    ) {
        val normalizedLink = if (link.startsWith("http")) {
            link
        } else {
            "https://$link"
        }
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(normalizedLink)
        ).also {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startIntent(intent, onError)
    }

    fun openAddressInMap(
        address: String,
        onError: (Throwable) -> Unit
    ) {
        val uri = "http://maps.google.co.in/maps?q=$address"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).also {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startIntent(intent, onError)
    }

    fun shareImage(
        file: File,
        fileProviderAuthority: String,
        onError: (Throwable) -> Unit
    ) {
        val uri = FileProvider.getUriForFile(
            context,
            fileProviderAuthority,
            file
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "image/png"
        }
        startIntent(intent, onError)
    }

    fun openWhatsApp(
        number: String,
        onError: (Throwable) -> Unit
    ) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://api.whatsapp.com/send?phone=${fixNumberForWhatsApp(number)}")
        }
        startIntent(intent, onError)
    }

    private fun fixNumberForWhatsApp(number: String): String =
        if (number.startsWith(NL_PHONE_START_WHATS_APP)) {
            number.replaceFirst(NL_PHONE_START_WHATS_APP, NL_PHONE_WHATS_APP_COUNTRY_CODE)
        } else {
            number
        }

    private fun startIntent(
        original: Intent,
        onError: (Throwable) -> Unit
    ) {
        val chooser = Intent.createChooser(original, "").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(chooser)
        } catch (ex: Exception) {
            onError(UnableToOpenLinkException(ex))
        }
    }
}

class UnableToOpenLinkException(cause: Throwable) : Throwable(cause)