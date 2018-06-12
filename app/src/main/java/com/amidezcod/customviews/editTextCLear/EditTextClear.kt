package com.amidezcod.customanimations.editTextCLear

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.amidezcod.customanimations.R

class EditTextClear : AppCompatEditText, View.OnTouchListener, TextWatcher, View.OnFocusChangeListener {
    var mClearButtonImage: Drawable

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_grey_24dp, null)!!
        // : If the X (clear) button is tapped, clear the text.
        setOnTouchListener(this)
        // : If the text changes, show or hide the X (clear) button.
        addTextChangedListener(this)
        // focus
        onFocusChangeListener = this

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null) {
            val x = event!!.x
            val y = event.y
            // Adding free space around image to increase area for tap on cross
            val FREE_SPACE = 10f
            val right = v!!.width - compoundDrawables[2].intrinsicWidth - FREE_SPACE
            val left = v.width
            val tapped: Boolean = x >= right && x <= left// && y >= drawableY1 && y <= drawableY2

            if (tapped) {
                if (event.action == MotionEvent.ACTION_UP) {
                    setText("")
                    return true
                }
            }
        }
        return false
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        changeAccordintToFocus(hasFocus, text.isNotEmpty())
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        changeAccordintToFocus(isFocused, text!!.isNotEmpty())
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    fun showCross(boolean: Boolean) {
        if (boolean) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mClearButtonImage, null)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }

    fun changeAccordintToFocus(focus: Boolean, text: Boolean) {
        if (focus) {
            if (text) {
                showCross(true)
            } else {
                showCross(false)
            }
        } else {
            showCross(false)
        }
    }
}
