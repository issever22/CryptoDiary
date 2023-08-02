package com.issever.cryptodiary.util.extensions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.issever.cryptodiary.R
import com.issever.cryptodiary.data.model.SnackbarMessage
import com.issever.cryptodiary.util.SnackbarType


fun View.slideInFromRight(onEnd: (() -> Unit)? = null) {
    val slideInAnimation = Slide(Gravity.END).apply {
        duration = 300
        addTarget(this@slideInFromRight)
    }
    visibility = View.INVISIBLE

    post {
        TransitionManager.beginDelayedTransition(rootView as ViewGroup, slideInAnimation)
        visibility = View.VISIBLE
    }

    // Do something after the animation ends
    slideInAnimation.addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            onEnd?.invoke()
        }

        override fun onTransitionResume(transition: Transition) {}
        override fun onTransitionPause(transition: Transition) {}
        override fun onTransitionCancel(transition: Transition) {}
        override fun onTransitionStart(transition: Transition) {}
    })
}

fun View.slideOutToLeft(onEnd: (() -> Unit)? = null) {
    val slideOutAnimation = Slide(Gravity.START).apply {
        duration = 300
        addTarget(this@slideOutToLeft)
    }

    post {
        TransitionManager.beginDelayedTransition(rootView as ViewGroup, slideOutAnimation)
        visibility = View.GONE
    }

    // Do something after the animation ends
    slideOutAnimation.addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            onEnd?.invoke()
        }

        override fun onTransitionResume(transition: Transition) {}
        override fun onTransitionPause(transition: Transition) {}
        override fun onTransitionCancel(transition: Transition) {}
        override fun onTransitionStart(transition: Transition) {}
    })
}

fun TextView.showChangePercent(change: Double) {
    val changePercent = "%.2f %%".format(change).trimBrackets()

    text = changePercent

    if (changePercent.contains("-")) {
        setTextColor(
            ContextCompat.getColor(context, R.color.red)
        )
        setCompoundDrawablesWithIntrinsicBounds(
            null, null,
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_downward_24),
            null
        )
    } else {
        setTextColor(
            ContextCompat.getColor(context, R.color.green)
        )
        setCompoundDrawablesWithIntrinsicBounds(
            null, null,
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_upward_24),
            null
        )
    }
}

fun ImageView.heartAnimation(
    onSaveStart: (() -> Unit)? = null,
    onDeleteStart: (() -> Unit)? = null
) {
    val duration = 500L
    val scaleDown = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f)
    val scaleDownY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f)
    val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 180f)

    val animator = ObjectAnimator.ofPropertyValuesHolder(this, rotation, scaleDown, scaleDownY)
    animator.duration = duration / 2
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE

    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            if (this@heartAnimation.tag == null || this@heartAnimation.tag == true) {
                onSaveStart?.invoke()
            } else {
                onDeleteStart?.invoke()
            }
        }

        override fun onAnimationEnd(animation: Animator) {
            // not needed for this animation
        }

        override fun onAnimationCancel(animation: Animator) {
            // not needed for this animation
        }

        override fun onAnimationRepeat(animation: Animator) {
            if (this@heartAnimation.tag == null || this@heartAnimation.tag == true) {
                this@heartAnimation.setImageResource(R.drawable.baseline_favorite_24)
                this@heartAnimation.tag = false
            } else {
                this@heartAnimation.setImageResource(R.drawable.baseline_favorite_border_24)
                this@heartAnimation.tag = true
            }
        }
    })

    animator.start()
}

fun View.rotateCircularly(
    duration: Long = 2000L,
    onAnimationEnd: (() -> Unit)? = null,
    infinite: Boolean = false
) {
    val objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
    objectAnimator.duration = duration
    if (infinite){
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
    }
    objectAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}

        override fun onAnimationEnd(animation: Animator) {
            onAnimationEnd?.invoke()
        }

        override fun onAnimationCancel(animation: Animator) {}

        override fun onAnimationRepeat(animation: Animator) {}
    })
    objectAnimator.start()
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun View.showSnackbar(
    data: SnackbarMessage
) {
    val snackbar = Snackbar.make(this, data.message.toString(), Snackbar.LENGTH_LONG)

    /*
    If you are using the AndroidX Material library, replace android.support.design.R.id.snackbar_text
     with com.google.android.material.R.id.snackbar_text.
*/
    val textView =
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    val actionView =
        snackbar.view.findViewById<Button>(com.google.android.material.R.id.snackbar_action)

    when (data.type) {
        SnackbarType.SUCCESS -> {
            snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.success_color))
            textView.setTextColor(ContextCompat.getColor(context, R.color.white))
            setCompoundDrawable(textView, R.drawable.snack_success, GravityCompat.START)
        }

        SnackbarType.ERROR -> {
            snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.error_color))
            textView.setTextColor(ContextCompat.getColor(context, R.color.white))
            setCompoundDrawable(textView, R.drawable.snack_error, GravityCompat.START)
        }

        SnackbarType.INFO -> {
            snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.info_color))
            textView.setTextColor(ContextCompat.getColor(context, R.color.white))
            setCompoundDrawable(textView, R.drawable.snack_info, GravityCompat.START)
        }

        SnackbarType.WARNING -> {
            snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.warning_color))
            textView.setTextColor(ContextCompat.getColor(context, R.color.white))
            setCompoundDrawable(textView, R.drawable.snack_warning, GravityCompat.START)
        }

        else -> {
            snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.default_color))
        }
    }
    if (!data.actionText.isNullOrEmpty() && data.action != null) {
        actionView.visibility = View.VISIBLE
        actionView.setTextColor(ContextCompat.getColor(context, R.color.white))
        actionView.text = data.actionText
        actionView.setOnClickListener { data.action.invoke() }
    } else {
        actionView.visibility = View.GONE
    }
    snackbar.show()
}

private fun setCompoundDrawable(textView: TextView, @DrawableRes resId: Int, gravity: Int) {
    val drawable = ContextCompat.getDrawable(textView.context, resId)
    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
    textView.compoundDrawablePadding =
        textView.resources.getDimensionPixelOffset(R.dimen.snackbar_icon_padding)
    textView.gravity = Gravity.CENTER_VERTICAL or gravity
}