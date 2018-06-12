package com.amidezcod.customanimations.droidCardOverlap

import android.graphics.Bitmap

data class DroidCard(val mDroid: Droid, val mBitmap: Bitmap) {

    // Free space around card
    private val FREE_SPACE_AROUND_IMAGE = 20F

    // 25 header sixe comapre to mBitmap image size
    private val BITMAP_TO_HEADER_RATIO = 0.25f

     var mBodyHeight: Float
     var mHeaderHeight: Float
     var mTitleSize: Float

    init {
        mBodyHeight = mBitmap.height + FREE_SPACE_AROUND_IMAGE
        mHeaderHeight = mBitmap.height * BITMAP_TO_HEADER_RATIO
        mTitleSize = mHeaderHeight / 2
    }

     val cardHeight
        get() = mBodyHeight + mHeaderHeight

     val cardWidth
        get() = mBitmap.width + (2 * FREE_SPACE_AROUND_IMAGE)

     val titleXYOffset
        get() = FREE_SPACE_AROUND_IMAGE
}