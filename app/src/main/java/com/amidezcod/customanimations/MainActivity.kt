package com.amidezcod.customanimations

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amidezcod.customanimations.Emoji.EmotionalActivity
import com.amidezcod.customanimations.droidCardOverlap.DroidActivity
import com.amidezcod.customanimations.knob_packa.KnobActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emotional_button.setOnClickListener {
            intent11<EmotionalActivity>()
        }
        droid_button.setOnClickListener {
            intent11<DroidActivity>()
        }
/*
         The Theme's windowBackground is masked by the opaque background of the activity, and
         the windowBackground causes an unnecessary overdraw. Nullifying the windowBackground
         removes that overdraw.
    getWindow().setBackgroundDrawable(null);
*/
        knob.setOnClickListener {
            intent11<KnobActivity>()
        }
    }

    inline fun <reified T : Any> intent11() {
        startActivity(Intent(this@MainActivity, T::class.java))
    }


}
