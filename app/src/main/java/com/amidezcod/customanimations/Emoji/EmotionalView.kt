package com.amidezcod.customanimations.Emoji

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.amidezcod.customanimations.R

class EmotionalView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    companion object {
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.BLACK
        private const val DEFAULT_MOUTH_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 4f

        const val HAPPY = 1L
        const val SAD = 0L
    }


    private var faceColor = DEFAULT_FACE_COLOR
    private var eyesColor = DEFAULT_EYES_COLOR
    private var mouthColor = DEFAULT_MOUTH_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH
    private var size = 0
    private var radius = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mouthPath = Path()

    var hapinessState = HAPPY
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    init {
        setUpAttributes(attrs)
    }

    private fun setUpAttributes(attrs: AttributeSet?) {
        val typedArray: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.EmotionalView, 0, 0)
        hapinessState = typedArray.getInt(R.styleable.EmotionalView_state, HAPPY.toInt()).toLong()
        faceColor = typedArray.getColor(R.styleable.EmotionalView_faceColor, DEFAULT_FACE_COLOR)
        eyesColor = typedArray.getColor(R.styleable.EmotionalView_eyesColor, DEFAULT_EYES_COLOR)
        mouthColor = typedArray.getColor(R.styleable.EmotionalView_mouthColor, DEFAULT_MOUTH_COLOR)
        borderColor = typedArray.getColor(R.styleable.EmotionalView_borderColor, DEFAULT_BORDER_COLOR)
        borderWidth = typedArray.getDimension(R.styleable.EmotionalView_borderWidth, DEFAULT_BORDER_WIDTH)
        typedArray.recycle()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBorder(canvas)
        drawFaceBackground(canvas)
        drawMouth(canvas)
        drawEyes(canvas)

    }

    private fun drawBorder(canvas: Canvas?) {
        paint.run {
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
        }
        canvas!!.drawCircle(width / 2f, height / 2f, radius - 10f, paint)
    }

    private fun drawEyes(canvas: Canvas?) {
        paint.color = eyesColor
        paint.style = Paint.Style.FILL
        val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)
        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)
        canvas!!.run {
            drawOval(leftEyeRect, paint)
            drawOval(rightEyeRect, paint)
        }
    }

    private fun drawMouth(canvas: Canvas?) {
        mouthPath.run {
            reset()
            moveTo(size * 0.22f, size * 0.7f)

            if (hapinessState == HAPPY) {
                quadTo(size * 0.50f, size * 0.80f, size * 0.80f, size * 0.7f)
                quadTo(size * 0.50f, size * 0.95f, size * 0.22f, size * 0.7f)
            } else {
                quadTo(size * 0.5f, size * 0.50f, size * 0.78f, size * 0.7f)
                quadTo(size * 0.5f, size * 0.60f, size * 0.22f, size * 0.7f)
            }

        }
        paint.run {
            color = mouthColor
            paint.style = Paint.Style.FILL
        }
        canvas!!.drawPath(mouthPath, paint)
    }

    private fun drawFaceBackground(canvas: Canvas?) {
        paint.apply {
            color = faceColor
            style = Paint.Style.FILL
        }
        canvas!!.drawCircle(width / 2f, height / 2f, radius - 10f, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)
        radius = (size / 2f)
        setMeasuredDimension(size, size)
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putLong("happinessState", hapinessState)
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var viewstate = state
        if (viewstate is Bundle) {
            hapinessState = viewstate.getLong("happinessState", HAPPY)
            viewstate = viewstate.getParcelable("superState")
        }
        super.onRestoreInstanceState(viewstate)
    }

}