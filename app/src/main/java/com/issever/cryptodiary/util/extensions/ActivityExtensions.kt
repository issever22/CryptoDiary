package com.issever.cryptodiary.util.extensions

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.issever.cryptodiary.R

fun <T> AppCompatActivity.showChoiceDialog(
    map: Map<String, T>,
    selectedKey: String,
    title: String,
    setSelected: (T) -> Unit
) {
    val keys = map.keys.toTypedArray()
    val selected = map.entries.find { it.value.toString() == selectedKey }?.key ?: keys[0]
    val checkedItem = keys.indexOf(selected)

    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)

    builder.setSingleChoiceItems(keys, checkedItem) { dialog, which ->
        val chosenKey = keys[which]
        val chosenValue = map[chosenKey] ?: map.values.first()
        setSelected(chosenValue)
        this.recreate()
        dialog.dismiss()
    }

    builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
        dialog.dismiss()
    }

    builder.show()
}

fun AppCompatActivity.startNewActivity(targetActivity: Class<*>,finishActivity: Boolean? = false) {
    val intent = Intent(this, targetActivity)
    this.startActivity(intent)
    if (finishActivity == true) this.finish()
}

fun AppCompatActivity.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    with(intent) {
        data = Uri.fromParts("package", this@openAppSettings.packageName, null)
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }
    startActivity(intent)
}

fun AppCompatActivity.openAppSettings(packageName: String) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    with(intent) {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

fun AppCompatActivity.openPackageUsageStatsSetting() {
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    with(intent) {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

@RequiresApi(Build.VERSION_CODES.R)
fun AppCompatActivity.openManageStorageSetting() {
    val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
    startActivity(intent)
}

fun AppCompatActivity.openWriteSetting() {
    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
    with(intent) {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

fun AppCompatActivity.checkWriteSettingPermission(): Boolean {

    return if (Settings.System.canWrite(this)) {
        true
    } else {

        this.showPopupWithAction(getString(R.string.give_access_write_settings))
            .setPositiveButton(getString(R.string.settings)) { dialog, _ ->
                dialog.dismiss()

               openWriteSetting()

            }.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.show()

        false
    }
}

fun AppCompatActivity.disableTouch() {
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun AppCompatActivity.enableTouch() {
    this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun AppCompatActivity.setupFullScreenMode(){
    supportActionBar?.hide()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
    } else {
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}

fun AppCompatActivity.exitFullScreenMode() {
    supportActionBar?.show()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.show(WindowInsets.Type.statusBars())
    } else {
        @Suppress("DEPRECATION")
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
