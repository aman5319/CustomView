package com.amidezcod.customanimations.knob_packa

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.PI

class KnobView(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : View(context, attributeSet, defStyleAttr) {
    constructor(context: Context) :
            this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    val angleList = mapOf(-60 to "4", -90 to "3", -120 to "2", -150 to "1", -180 to "0")
    var size = 300
    var radius = size / 2f
    var dot_size = size / 10f
    var paint = Paint()
    var backGroundColor = Color.BLUE
    var indicatorColor = Color.GRAY

    init {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = backGroundColor
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        canvas!!.drawCircle(width / 2f, height / 2f, radius, paint)
        paint.textSize = SpToPx(20)
        val centerx = (width / 2)
        val centery = (height / 2)
        for (angle in angleList) {

            var y: Float
            var x: Float
            if (angle.key >= -90) {
                y = Math.sin((PI / 180) * angle.key).toFloat() * (radius + 15)
                x = Math.cos((PI / 180) * angle.key).toFloat() * (radius + 15)

            } else {
                y = Math.sin((PI / 180) * angle.key).toFloat() * (radius + 30)
                x = Math.cos((PI / 180) * angle.key).toFloat() * (radius + 30)
            }

            canvas.drawText(angle.value, x + centerx, y + centery, paint)
        }
        paint.color = indicatorColor
        val y: Float = Math.sin((PI / 180) * -60).toFloat() * (radius - 40)
        val x: Float = Math.cos((PI / 180) * -60).toFloat() * (radius - 40)
        canvas.drawCircle(x + centerx, y + centery, dot_size / 2f, paint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension((size + 80), (size + 100))
    }

    fun SpToPx(sp: Int): Float {
        return sp * this.context.resources.displayMetrics.scaledDensity
    }
}