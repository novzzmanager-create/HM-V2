package com.hypers.hm.ktx

import android.os.Build
import android.text.Html
import android.text.Spanned

fun CharSequence.toHtml(flags: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this.toString(), flags)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this.toString())
    }
}

fun CharSequence.toHtml(tagHandler: Html.TagHandler): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this.toString(), Html.FROM_HTML_MODE_LEGACY, null, tagHandler)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this.toString(), null, tagHandler)
    }
}

fun CharSequence.toHtml(flags: Int, tagHandler: Html.TagHandler): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this.toString(), flags, null, tagHandler)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this.toString(), null, tagHandler)
    }
}