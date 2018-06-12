package com.amidezcod.customanimations.droidCardOverlap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import com.amidezcod.customanimations.R
import kotlinx.android.synthetic.main.activity_droid.*

class DroidActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_droid)

        val droids = arrayOf(Droid("Joanna", R.drawable.alex, android.R.color.holo_blue_bright),
                Droid("Shailen", R.drawable.claire, android.R.color.holo_green_light),
                Droid("Chris", R.drawable.chris, android.R.color.holo_red_light))

        // Create the DroidCardsView object.
        val droidCardView = DroidClassView(
                this,
                droids,
                200f,
                200f / 3f)

        droidCardView.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)

        // Add the view to the container.
        activity_droid_cards_container.addView(droidCardView)
    }
}
