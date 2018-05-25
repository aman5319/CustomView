package com.amidezcod.customanimations.Emoji

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amidezcod.customanimations.R
import kotlinx.android.synthetic.main.activity_emotional.*

class EmotionalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotional);
        emotional.setOnClickListener {
            emotional.hapinessState = EmotionalView.SAD
        }
    }
}