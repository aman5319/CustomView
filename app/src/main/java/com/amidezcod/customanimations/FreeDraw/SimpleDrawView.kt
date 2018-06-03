package com.amidezcod.customanimations.FreeDraw

import android.content.Context
import android.graphics.*
import android.media.MediaScannerConnection
import android.os.Environment
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream


class SimpleDrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {


    // setup initial color
    private val paintColor = Color.parseColor("#487F6E")
    // defines paint and canvas
    lateinit var drawPaint: Paint
    private val path = Path()

    init {
        isFocusable = true

        /**
         * Set whether this view can receive focus while in touch mode.
         *
         * Setting this to true will also ensure that this view is focusable.
         */
        isFocusableInTouchMode = true

        setupPaint()
    }

    private fun setupPaint() {
        drawPaint = Paint()
        drawPaint.apply {
            color = paintColor
            isAntiAlias = true
            strokeWidth = 10f
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }


    }

    //Not Implemented
    fun savePicture() {

        val paint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)


        Canvas(bitmap).run {
            // color the entire area with white color a.k.a background color
            drawPaint(paint)
            // draw the the path on canvas object
            drawPath(path, drawPaint)
        }

        // get the storage directory it is in the Internal storage
        val dirs = File(Environment.getExternalStorageDirectory().path + "/mypic")
        if (!dirs.exists()) dirs.mkdirs()

        //create the photo file
        val myPhoto = File(dirs, "photo.jpg")
        if (myPhoto.exists()) myPhoto.delete()

        // write the bitmap in file
        FileOutputStream(myPhoto).apply {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            flush()
            close()
        }
        notifyGallery(myPhoto)
    }

    private fun notifyGallery(photo: File) {

        MediaScannerConnection.scanFile(context, arrayOf(photo.absolutePath), null) { path, uri ->
            Handler(context.mainLooper).post({
                Toast.makeText(context, "${photo.name} saved to gallery", Toast.LENGTH_SHORT).show()
                Log.v("TAG", uri.toString())
            })
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, drawPaint)
    }

    fun clearScreen() {
        Toast.makeText(context, "Clear Path", Toast.LENGTH_SHORT).show()
        path.reset()
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val pointX = event!!.x
        val pointY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(pointX, pointY)
                return true
            }
            MotionEvent.ACTION_MOVE -> path.lineTo(pointX, pointY)
            else -> return false
        }
        postInvalidate() // Indicate view should be redrawn
        return true
    }

}