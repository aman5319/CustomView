package com.amidezcod.customanimations.scalingLayout.scalingLayoutLibrary

import android.content.Context
import android.util.AttributeSet
import com.amidezcod.customanimations.R


class ScalingLayoutSettings(private val context: Context?, private val attributeSet: AttributeSet?) {
    private val DEFAULT_RADIUS_FACTOR = 1.0f

    var maxRadius = 0f
    var maxWidth = 0
    var intialWidth = 0
    var elevation = 0f
    var radiusFactor = 0f
    var isIntialized: Boolean = false

    init {
        context!!.obtainStyledAttributes(R.styleable.ScalingLayout).run {
            radiusFactor = this.getFloat(R.styleable.ScalingLayout_radiusFactor, DEFAULT_RADIUS_FACTOR)
            maxWidth = context.resources.displayMetrics.widthPixels
            recycle()
            if (radiusFactor > DEFAULT_RADIUS_FACTOR) {
                radiusFactor = DEFAULT_RADIUS_FACTOR
            }
        }
    }

    fun intialized(width: Int, height: Int) {
        if (!isIntialized) {
            isIntialized = true
            intialWidth = width
            /*
                radius = height
                        ---------  * radius_factor
                            2
             */
            maxRadius = (height * radiusFactor) / 2f

        }
    }

}