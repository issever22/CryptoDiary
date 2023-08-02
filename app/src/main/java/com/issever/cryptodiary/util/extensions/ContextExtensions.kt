package com.issever.cryptodiary.util.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.text.format.DateFormat
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.issever.cryptodiary.R
import com.issever.cryptodiary.util.Constants.LocalData.DARK
import com.issever.cryptodiary.util.Constants.LocalData.FOLLOW_SYSTEM
import com.issever.cryptodiary.util.Constants.LocalData.LIGHT
import com.issever.cryptodiary.util.Errors.OPEN_ERROR
import java.io.File
import java.util.Date
import java.util.Locale

fun Context.formatDate(date: Date): String {
    val dateFormat = DateFormat.getDateFormat(this)
    return dateFormat.format(date)
}

fun Context.showPopupWithAction(message: String): MaterialAlertDialogBuilder {
    return MaterialAlertDialogBuilder(this, R.style.CustomAlertDialog).apply {
        setCancelable(false)
        setMessage(message)
    }
}

fun Context.showConfirmationDialog(
    title: String,
    message: String,
    positiveButton: String? = getString(R.string.yes),
    negativeButton: String? = getString(R.string.no),
    action: (() -> Unit)? = null,
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButton) { dialog, _ ->
            action?.invoke()
            dialog.dismiss()
        }
        .setNegativeButton(negativeButton) { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun Context.updateLocale(selectedLanguage: String): Context {
    val localeToSwitchTo = Locale(selectedLanguage)
    val configuration: Configuration = resources.configuration
    configuration.setLocale(localeToSwitchTo)
    return createConfigurationContext(configuration)
}

fun changeTheme(theme: String) {
    when (theme) {
        DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        FOLLOW_SYSTEM -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            }
        }
    }
}

fun Context.clearAppCache(): Boolean {
    return try {
        val dir: File = this.cacheDir
        deleteDir(dir)
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

private fun deleteDir(dir: File): Boolean {
    if (dir.isDirectory) {
        val children: Array<String> = dir.list() as Array<String>
        for (i in children.indices) {
            val success: Boolean = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
    }
    return dir.delete()
}

fun Context.openGooglePlay() {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=$packageName")
        startActivity(intent)
    } catch (anfe: ActivityNotFoundException) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        startActivity(intent)
    }
}

fun Context.openWebPage(url: String) {
    val webpage: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, webpage)

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        Toast.makeText(this, OPEN_ERROR, Toast.LENGTH_LONG).show()
    }
}