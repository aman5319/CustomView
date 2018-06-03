package com.amidezcod.customanimations.FreeDraw

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.amidezcod.customanimations.R
import kotlinx.android.synthetic.main.activity_free_draw.*

class FreeDrawActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_free_draw)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.`as`, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.clear -> simpleDrawingView1.clearScreen()
            R.id.click -> simpleDrawingView1.savePicture()
            else -> return true
        }
        return true
    }

}
