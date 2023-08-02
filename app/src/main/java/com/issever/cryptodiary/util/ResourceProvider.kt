package com.issever.cryptodiary.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import javax.inject.Inject

/**
 * A helper class to provide resources in a way that is decoupled from the Android Context.
 *
 * This class provides a level of abstraction over Android's normal resource handling system,
 * enabling resources to be retrieved without requiring a Context. This can be useful in
 * situations where a Context isn't available or where providing a Context could result in
 * memory leaks, such as in ViewModels or Repositories.
 *
 * By using this class instead of directly using Context, we also ensure that our code is
 * more testable, as we can easily mock this class in unit tests.
 */

class ResourceProvider @Inject constructor(
    private val context: Context
) {
    fun getString(@StringRes resId: Int): String = context.getString(resId)

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String = context.getString(resId, *formatArgs)

    fun getDrawable(@DrawableRes resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)

    @ColorInt
    fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(context, resId)

    fun getDimension(@DimenRes resId: Int): Float = context.resources.getDimension(resId)

    fun getInteger(@IntegerRes resId: Int): Int = context.resources.getInteger(resId)
}
