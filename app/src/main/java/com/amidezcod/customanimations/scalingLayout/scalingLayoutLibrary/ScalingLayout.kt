package com.amidezcod.customanimations.scalingLayout.scalingLayoutLibrary

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

class ScalingLayout(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    /**
     * values to draw rounded Layout
     */

    var maskPaint: Paint
    var rectF: RectF
    var path: Path

    /**
     * current radius
     */
    var currentRadius = 0f

    /**
     * currentWidth
     * Here the width is dependant on currentRadius
     * the width is inversely proportional to radius
     * when expanding and collapsing the view
     */
    var currentWidth = 0
    /**
     * If layout has margins, margin has to be change
     * according to radius.
     */
    var maxMargins: IntArray
    var currentMargins: IntArray

    /**
     * current state of the layout
     */
    var currentState: States

    var scalingLayoutListener: ScalingLayoutListener? = null

    var scalingLayoutSettings: ScalingLayoutSettings = ScalingLayoutSettings(context, attrs)

    lateinit var scalingOutlineProvider: ScalingOutlineProvider
    private val valueAnimator: ValueAnimator

    init {
        scalingLayoutSettings.elevation = ViewCompat.getElevation(this)
        currentState = States()
        currentState.state = States.COLLAPSED

        path = Path()
        rectF = RectF(0f, 0f, 0f, 0f)

        maskPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        /**
         * These two steps are very important we are taking poterdef because we want to draw an
         * circular surface on a rectangular box (as the child view in this layout are in a rectangular box
         * to get a circular surface we use poterduff wit DST_IN )
         *
         * SetLayerType as hardware because we are updating our layout more often
         */
        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)

        valueAnimator = ValueAnimator.ofFloat(0f, 0f)
                .apply {
                    duration = 200
                    addUpdateListener {
                        /**
                         * as we are animating between collapse and expand
                         * we are increasing and decreasing our radius
                         */
                        setRadius(it.animatedValue as Float)
                    }
                }

        maxMargins = IntArray(4)
        currentMargins = IntArray(4)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        (layoutParams as ViewGroup.MarginLayoutParams).apply {
            listOf(leftMargin, topMargin, rightMargin, bottomMargin)
                    .forEachIndexed { index: Int, value: Int ->
                        currentMargins[index] = value
                        maxMargins[index] = value
                    }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (!scalingLayoutSettings.isIntialized) {
            scalingLayoutSettings.intialized(w, h)
            currentRadius = scalingLayoutSettings.maxRadius
            currentWidth = w
            scalingOutlineProvider = ScalingOutlineProvider(w, h, currentRadius)

        }
        rectF.set(0f, 0f, w.toFloat(), h.toFloat())
        updateViewOutline(h, currentWidth, currentRadius)
        invalidate()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)

        path.reset()
        path.addRoundRect(rectF, currentRadius, currentRadius, Path.Direction.CCW)
        canvas?.drawPath(path, maskPaint)
    }

    /**
     * Expand the layout screen when it is expanded radius is zero
     * as the animation runs radius of the view is also changing
     */
    fun expand() {
        valueAnimator.setFloatValues(scalingLayoutSettings.maxRadius, 0f)
        valueAnimator.start()
    }

    /**
     * collapse the layout here the radius will be maximum
     */
    fun collapse() {
        valueAnimator.setFloatValues(0f, scalingLayoutSettings.maxRadius)
        valueAnimator.start()
    }

    /**
     *
     */
    /**
     * Updates view outline borders and radius
     *
     * @param height
     * @param width
     * @param radius
     */
    private fun updateViewOutline(height: Int, width: Int, radius: Float) {
        scalingOutlineProvider.height = height
        scalingOutlineProvider.width = width
        scalingOutlineProvider.radius = radius
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && ViewCompat.getElevation(this) > 0f) {
            try {
                outlineProvider = scalingOutlineProvider
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun setRadius(radius: Float) {
        if (radius < 0) return
        /**
         * Using this method we update current radius
         */
        updateCurrentRadius(radius)
        /**
         * Rest all the methods works on current Radius
         */
        updateCurrentWidth(currentRadius)
        updateCurrentMargins(currentRadius)
        updateCurrentState(currentRadius)
        updateCurrentElevation()

        requestLayout()
    }

    private fun updateCurrentRadius(radius: Float) {
        currentRadius = if (radius > scalingLayoutSettings.maxRadius)
            scalingLayoutSettings.maxRadius
        else
            radius
    }

    private fun updateCurrentWidth(currentRadius: Float) {
        val differenceWidth = scalingLayoutSettings.maxWidth - scalingLayoutSettings.intialWidth
        /*
         *                               currentRadius * differenceWidth
         * width = differenceWidth -  --------------------------------------- + intialWidth
         *                                      maxRadius
         * when currentRadius = maxRadius , width = intialWidth
         * when currentRadius = 0 , width = maxWidth
         */
        val calculateWidth = differenceWidth - ((currentRadius * differenceWidth) / scalingLayoutSettings.maxRadius) + scalingLayoutSettings.intialWidth
        currentWidth = when {
            calculateWidth > scalingLayoutSettings.maxWidth -> scalingLayoutSettings.maxWidth
            calculateWidth < scalingLayoutSettings.intialWidth -> scalingLayoutSettings.intialWidth
            else -> calculateWidth.toInt()
        }
    }

    private fun updateCurrentMargins(currentRadius: Float) {
        for (i in 0 until currentMargins.size) {
            currentMargins[i] = (maxMargins[i] * currentRadius / scalingLayoutSettings.maxRadius).toInt()
        }
        layoutParams.width = currentWidth
        (layoutParams as ViewGroup.MarginLayoutParams)
                .setMargins(currentMargins[0],
                        currentMargins[1],
                        currentMargins[2],
                        currentMargins[3])

    }

    private fun updateCurrentState(currentRadius: Float) {
        currentState.state = when (currentRadius) {
            0f -> States.EXPANDED
            scalingLayoutSettings.maxRadius -> States.COLLAPSED
            else -> States.PROGRESSING
        }
        notifyListener()
    }

    private fun updateCurrentElevation() {
        ViewCompat.setElevation(this, scalingLayoutSettings.elevation)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = outlineProvider
        }
    }

    /**
     * Notify observers about change
     */

    private fun notifyListener() {
        if (scalingLayoutListener != null) {
            when {
                currentState.state == States.COLLAPSED -> scalingLayoutListener!!.OnCollapsed()
                currentState.state == States.EXPANDED -> scalingLayoutListener!!.OnExpanded()
                else -> scalingLayoutListener!!.OnProgress(currentRadius / scalingLayoutSettings.maxRadius)
            }
        }
    }

}


