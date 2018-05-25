package com.amidezcod.customanimations.droidCardOverlap

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.os.AsyncTask
import android.support.v4.content.ContextCompat
import android.view.View
import java.util.*

class DroidClassView(context: Context, var droids: Array<Droid>, var droidImageWIdth: Float, var cardSpacing: Float) : View(context) {

    /**
     * A list of DroidCards objected. We use Asynctasks to populate the contents of this list. See
     * the DroidCardWorkerTask class that extends AsyncTask.
     */
    private val mDroidCards = ArrayList<DroidCard>()
    private var cardLeft: Float = 1f

    init {
        for (i in 0 until droids.size) {
            DroidCardWorkerTask().execute(droids[i])
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (droids.isNotEmpty() && droids.size == mDroidCards.size) {
            for (i in 0 until mDroidCards.size - 1) {
                cardLeft = i * cardSpacing
                canvas!!.save()
                canvas.clipRect(cardLeft.toInt(), 0, (cardLeft + cardSpacing).toInt(), mDroidCards[i].cardHeight.toInt())
                drawDroidCard(canvas, mDroidCards[i], cardLeft, 0f)
                canvas.restore()
            }
            drawDroidCard(canvas, mDroidCards[mDroidCards.size - 1], cardLeft + cardSpacing, 0f)

        }
        invalidate()
    }

    private fun drawDroidCard(canvas: Canvas?, droidCard: DroidCard, cardLeft: Float, top: Float) {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        val whiteRect = RectF(cardLeft, top, cardLeft + droidCard.cardWidth, top + droidCard.cardHeight)
        canvas!!.drawRect(whiteRect, paint)

        paint.style = Paint.Style.STROKE
        paint.color = Color.DKGRAY
        canvas.drawRect(whiteRect, paint)

        // getting bitmap drawn in the center
        canvas.drawBitmap(droidCard.mBitmap,
                (cardLeft + (droidCard.cardWidth / 2) - (droidCard.mBitmap.width / 2)),
                (top + droidCard.mHeaderHeight + (droidCard.mBodyHeight / 2) - (droidCard.mBitmap.height / 2)),
                null)
        paint.textSize = droidCard.mTitleSize
        paint.style = Paint.Style.STROKE
        paint.color = ContextCompat.getColor(context, droidCard.mDroid.titleColor)
        // Calculate the the left and top offsets for the title.
        val titleLeftOffset = whiteRect.left + droidCard.titleXYOffset
        val titleTopOffset = whiteRect.top + droidCard.titleXYOffset +
                droidCard.mTitleSize

        // Draw the title text.
        canvas.drawText(droidCard.mDroid.title, titleLeftOffset, titleTopOffset, paint)
    }

    fun makeBitmap(res: Resources, resId: Int, reqWidth: Float): Bitmap {
        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        options.inSampleSize = calculateSampleSize(options, reqWidth)

        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)

    }

    private fun calculateSampleSize(options: BitmapFactory.Options, reqWidth: Float): Int {
        val width = options.outWidth
        var inSampleSize = 1

        if (width > reqWidth) {
            val halfWidth = width / 2
            while ((halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    inner class DroidCardWorkerTask : AsyncTask<Droid, Unit, Bitmap>() {
        lateinit var droid: Droid
        override fun doInBackground(vararg params: Droid?): Bitmap {
            droid = params[0]!!
            return getScaledBitmap(
                    makeBitmap(resources, droid.imageId, droidImageWIdth),
                    droidImageWIdth
            )
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            mDroidCards.add(DroidCard(droid, result!!))
        }
    }

    private fun getScaledBitmap(bitmap: Bitmap, droidImageWIdth: Float): Bitmap {
        val scale: Float = droidImageWIdth / bitmap.width
        return Bitmap.createScaledBitmap(bitmap,
                (bitmap.width * scale).toInt(),
                (bitmap.height * scale).toInt(), false)
    }
}