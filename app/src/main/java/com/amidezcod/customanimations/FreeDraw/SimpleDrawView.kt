package com.amidezcod.customanimations.FreeDraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.R.attr.path




class SimpleDrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    // setup initial color
    private val paintColor = Color.BLACK
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

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, drawPaint)
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