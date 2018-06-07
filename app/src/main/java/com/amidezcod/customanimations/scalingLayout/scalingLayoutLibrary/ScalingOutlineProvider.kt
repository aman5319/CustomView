package com.amidezcod.customanimations.scalingLayout.scalingLayoutLibrary

import android.annotation.SuppressLint
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

@SuppressLint("NewApi")
data class ScalingOutlineProvider(var width: Int, var height: Int, var radius: Float) : ViewOutlineProvider() {


    @SuppressLint("NewApi")
    override fun getOutline(view: View?, outline: Outline?) {
        outline!!.setRoundRect(0, 0, width, height, radius)
    }
}