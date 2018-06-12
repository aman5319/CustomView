package com.amidezcod.customanimations.revealBasedAnimation

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.amidezcod.customanimations.R


class RevealView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    private var toggle: Boolean = false
    private var lessArrow = ContextCompat.getDrawable(context, R.drawable.ic_expand_less_black_24dp)
    private var moreArrow = ContextCompat.getDrawable(context, R.drawable.ic_expand_more_black_24dp)
    private var topBarText: TextView? = null
    private var bottomBarText: TextView? = null
    private var topBar: View? = null
    private var bottomBar: ViewGroup? = null
    private var toggleButton: ImageView? = null

    fun setTitle(title: String) {
        topBarText?.text = title
    }

    fun setMessage(message: String) {
        bottomBarText?.text = message
    }

    fun setTitleBarColor(color: Int) {
        topBar?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    fun setBottomBarColor(color: Int) {
        bottomBar?.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    fun setToggle(imageView: ImageView, bottomBar: View): View.OnClickListener {
        return OnClickListener {
            var drawable = lessArrow
            var visibility = View.VISIBLE
            if (toggle) {
                drawable = moreArrow
                visibility = View.GONE
                toggle = false
            } else {
                toggle = true
            }
            toggleButton?.setImageDrawable(drawable)
            bottomBar.visibility = visibility
        }
    }

    init {
        View.inflate(context, R.layout.reveal_view, this)
        topBar = findViewById<View>(R.id.topBar)
        bottomBar = findViewById<View>(R.id.bottomBar) as ViewGroup
        toggleButton = findViewById<View>(R.id.toggle_button) as ImageView
        topBarText = findViewById<View>(R.id.top_bar_title) as TextView
        bottomBarText = findViewById<View>(R.id.bottom_bar_text) as TextView
        topBar?.setOnClickListener(setToggle(toggleButton!!, bottomBarText!!))
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RevealView)
        try {
            toggle = typedArray.getBoolean(R.styleable.RevealView_showMessage, false)
            bottomBar?.visibility = if (toggle) View.VISIBLE else View.GONE
            toggleButton?.setImageDrawable(if (toggle) lessArrow else moreArrow)
            if (typedArray.hasValue(R.styleable.RevealView_barTitle)) {
                topBarText?.text = typedArray.getString(R.styleable.RevealView_barTitle)
            }
            if (typedArray.hasValue(R.styleable.RevealView_colorBar)) {
                topBar?.setBackgroundColor(typedArray.getColor(R.styleable.RevealView_colorBar, resources.getColor(android.R.color.holo_green_light)))
            }
            if (typedArray.hasValue(R.styleable.RevealView_contentBar)) {
                bottomBar?.setBackgroundColor(typedArray.getColor(R.styleable.RevealView_contentBar, resources.getColor(android.R.color.darker_gray)))
            }
            if (typedArray.hasValue(R.styleable.RevealView_message)) {
                bottomBarText?.text = typedArray.getString(R.styleable.RevealView_message)
            }
        } finally {
            typedArray.recycle()
        }

    }
}
