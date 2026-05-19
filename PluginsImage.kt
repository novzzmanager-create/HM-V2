package com.hypers.hm

import android.content.Context
import android.graphics.*
import android.graphics.drawable.*
import android.view.View
import android.util.LruCache
import kotlinx.coroutines.*

object PluginsImage {

    private val bitmapCache = object : LruCache<String, Bitmap>(20 * 1024 * 1024) {
        override fun sizeOf(key: String, value: Bitmap): Int = value.byteCount
    }

    private const val KEY_ZOOM_STATE = -1002

    @JvmStatic
    fun applyImageBackground(context: Context, target: View, path: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var srcBmp = bitmapCache.get(path)

                if (srcBmp == null) {
                    val options = BitmapFactory.Options().apply {
                        inPreferredConfig = Bitmap.Config.RGB_565
                    }
                    val decoded = BitmapFactory.decodeFile(path, options)
                    if (decoded != null) {
                        srcBmp = decoded
                        bitmapCache.put(path, decoded)
                    }
                }

                if (srcBmp != null) {
                    withContext(Dispatchers.Main) {
                        // PAKAI CUSTOM DRAWABLE BIAR OTOMATIS CENTER CROP PAS UKURAN BERUBAH
                        val customBg = object : Drawable() {
                            val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
                            val matrix = Matrix()

                            override fun draw(canvas: Canvas) {
                                val bW = srcBmp.width.toFloat()
                                val bH = srcBmp.height.toFloat()
                                val vW = bounds.width().toFloat()
                                val vH = bounds.height().toFloat()

                                if (vW <= 0 || vH <= 0) return

                                // LOGIC CENTER CROP (COVER) - BIAR GAK GEPENG
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
                                matrix.postTranslate(dx + bounds.left, dy + bounds.top)
                                
                                val shader = BitmapShader(srcBmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                                shader.setLocalMatrix(matrix)
                                paint.shader = shader

                                // Gambar Rounded Rect sesuai ukuran View saat ini
                                canvas.drawRoundRect(RectF(bounds), 28f, 28f, paint)
                                
                                // Kasih overlay gelap dikit biar teks kebaca
                                canvas.drawColor(Color.parseColor("#600E0E0E"), PorterDuff.Mode.SRC_ATOP)
                            }

                            override fun setAlpha(alpha: Int) { paint.alpha = alpha }
                            override fun setColorFilter(cf: ColorFilter?) { paint.colorFilter = cf }
                            override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
                        }

                        // Gabungin sama Gradient Gradient asli lo
                        val gradient = GradientDrawable(
                            GradientDrawable.Orientation.BOTTOM_TOP,
                            intArrayOf(0x66BDBDBD, 0x22000000, 0x50BDBDBD)
                        )
                        gradient.cornerRadius = 28f

                        target.background = LayerDrawable(arrayOf(customBg, gradient))
                        target.clipToOutline = true
                    }
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    @JvmStatic
    fun enableZoomOnClick(target: View) {
        target.setOnClickListener {
            val isZoomed = target.getTag(KEY_ZOOM_STATE) as? Boolean ?: false
            val scale = if (isZoomed) 1.0f else 1.08f
            
            target.animate()
                .scaleX(scale)
                .scaleY(scale)
                .setDuration(250)
                .start()

            target.setTag(KEY_ZOOM_STATE, !isZoomed)
        }
    }
}
