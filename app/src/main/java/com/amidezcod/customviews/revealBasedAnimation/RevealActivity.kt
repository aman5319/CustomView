package com.amidezcod.customanimations.revealBasedAnimation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amidezcod.customanimations.R
import kotlinx.android.synthetic.main.activity_reveal.*

class RevealActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reveal)
        reveal_view.run {
            setMessage("This is a body message containing multiple lines \n aman \nbodyguard" +
                    "\n thoda tujhse ")
            setTitle("This is a title")
        }
    }

}
