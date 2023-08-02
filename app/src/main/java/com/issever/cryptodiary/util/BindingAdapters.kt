package com.issever.cryptodiary.util

import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.issever.cryptodiary.util.extensions.capitalizeFirstLetter
import com.issever.cryptodiary.util.extensions.convertToSystemDateFormat
import com.issever.cryptodiary.util.extensions.loadImage
import com.issever.cryptodiary.util.extensions.showChangePercent


@BindingAdapter("android:toString")
fun toString(view: TextView, value: Any?) {
    view.text = value.toString()
}

@BindingAdapter("android:dateText")
fun TextView.setDateText(date: String?) {
    date?.let {
        text = it.convertToSystemDateFormat()
    }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("android:goneIfEmpty")
fun goneIfEmpty(view: View, value: String?) {
    view.visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter("android:goneIfEmpty")
fun goneIfEmpty(view: View, value: ArrayList<Any>?) {
    view.visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter("android:htmlText")
fun setHtmlText(view: TextView, text: String?) {
    if (text != null) {
        view.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        view.text = ""
    }
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        view.loadImage(url)
    }
}

@BindingAdapter("android:showChangePercent")
fun showChangePercent(view: TextView, percent: Double?) {
    if (percent != null) {
        view.showChangePercent(percent)
    }
}

@BindingAdapter("android:openUrl")
fun openUrl(view: View, url: String?) {
    view.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(view.context.packageManager) != null) {
            view.context.startActivity(intent)
        }
    }
}

@BindingAdapter("android:capitalizedText")
fun setCapitalizedText(view: TextView, text: String?) {
    view.text = text?.capitalizeFirstLetter() ?: ""
}


