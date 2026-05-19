package com.hypers.hm

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.LruCache

object ImageHelper {

    private val cache = object : LruCache<String, Bitmap>(200) {}

    @JvmStatic
    fun putCache(pkg: String, bitmap: Bitmap) {
        if (pkg.isNotEmpty() && !bitmap.isRecycled) {
            cache.put(pkg, bitmap)
        }
    }

    @JvmStatic
    fun getCache(pkg: String): Bitmap? = cache.get(pkg)

    @JvmStatic
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable && drawable.bitmap != null) {
            return drawable.bitmap
        }

        val w = drawable.intrinsicWidth.coerceAtLeast(1)
        val h = drawable.intrinsicHeight.coerceAtLeast(1)

        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bmp
    }

    @JvmStatic
    fun processIcon(drawable: Drawable): Bitmap {
        val srcBmp = drawableToBitmap(drawable)

        val cardW = 650
        val cardH = 250

        val result = Bitmap.createBitmap(cardW, cardH, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG or Paint.DITHER_FLAG)

        val bW = srcBmp.width.toFloat()
        val bH = srcBmp.height.toFloat()
        val vW = cardW.toFloat()
        val vH = cardH.toFloat()

        val matrix = Matrix()
        val scale: Float
        var dx = 0f
        var dy = 0f

        if (bW * vH > vW * bH) {
            scale = vH / bH
            dx = (vW - bW * scale) * 0.5f
        } else {
            scale = vW / bW
            dy = (vH - bH * scale) * 0.5f
        }

        matrix.setScale(scale, scale)
        matrix.postTranslate(dx, dy)

        val shader = BitmapShader(srcBmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        shader.setLocalMatrix(matrix)
        paint.shader = shader
        
        paint.alpha = 200

        canvas.drawRect(0f, 0f, vW, vH, paint)

        val overlayPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        
        val gradient = LinearGradient(
            0f, 0f, 0f, vH,
            intArrayOf(
                Color.parseColor("#20000000"),
                Color.parseColor("#50000000"),
                Color.parseColor("#80000000")
            ),
            null,
            Shader.TileMode.CLAMP
        )

        overlayPaint.shader = gradient
        canvas.drawRect(0f, 0f, vW, vH, overlayPaint)

        val glassPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        glassPaint.color = Color.WHITE
        glassPaint.alpha = 10
        canvas.drawRect(0f, 0f, vW, vH, glassPaint)

        return result
    }

    @JvmStatic
    fun clearCache() {
        cache.evictAll()
    }
}
