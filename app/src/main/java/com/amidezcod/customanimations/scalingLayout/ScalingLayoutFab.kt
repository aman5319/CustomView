package com.amidezcod.customanimations.scalingLayout

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.amidezcod.customanimations.R
import com.amidezcod.customanimations.scalingLayout.scalingLayoutLibrary.ScalingLayout
import com.amidezcod.customanimations.scalingLayout.scalingLayoutLibrary.ScalingLayoutListener
import com.amidezcod.customanimations.scalingLayout.scalingLayoutLibrary.States

class ScalingLayoutFab : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scaling_layout_fab)

        val fabIcon = findViewById<ImageView>(R.id.fabIcon)
        val filterLayout = findViewById<LinearLayout>(R.id.filterLayout)
        val scalingLayout = findViewById<ScalingLayout>(R.id.scalingLayout)

        scalingLayout.scalingLayoutListener = object : ScalingLayoutListener {
            override fun OnCollapsed() {
                ViewCompat.animate(fabIcon).alpha(1f).setDuration(150).start()
                ViewCompat.animate(filterLayout).alpha(0f).setDuration(150).setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationStart(view: View) {
                        fabIcon.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(view: View) {
                        filterLayout.setVisibility(View.INVISIBLE)
                    }

                    override fun onAnimationCancel(view: View) {

                    }
                }).start()
            }

            override fun OnExpanded() {
                ViewCompat.animate(fabIcon).alpha(0f).setDuration(200).start()
                ViewCompat.animate(filterLayout).alpha(1f).setDuration(200).setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationStart(view: View) {
                        filterLayout.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(view: View) {
                        fabIcon.visibility = View.INVISIBLE
                    }

                    override fun onAnimationCancel(view: View) {

                    }
                }).start()
            }

            override fun OnProgress(progress: Float) {
                if (progress > 0) {
                    fabIcon.visibility = View.INVISIBLE
                }

                if (progress < 1) {
                    filterLayout.visibility = View.INVISIBLE
                }
            }
        }

        scalingLayout.setOnClickListener {
            if (scalingLayout.currentState.state == States.COLLAPSED) {
                scalingLayout.expand()
            }
        }


        findViewById<RelativeLayout>(R.id.rootLayout).setOnClickListener {
            if (scalingLayout.currentState.state == States.EXPANDED) {
                scalingLayout.collapse()
            }
        }

    }

}
