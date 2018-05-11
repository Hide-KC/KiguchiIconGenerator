package com.development.kc.kiguchiicongenerator

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val rinkakuLayer = findViewById<ConstraintLayout>(R.id.rinkaku_layer)
        val rinkakuLayerHeight = rinkakuLayer.height
        val textSize = (rinkakuLayerHeight * 0.1).roundToInt()
        val teststring = "ずいずい"
        val testBitmap = BitmapGenerator.textToBitmap(this, teststring, textSize)

        val commentView = findViewById<ImageView>(R.id.comment)
        commentView.setImageBitmap(testBitmap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        toolbar.setSubtitle(R.string.subtitle)
        toolbar.setTitleTextColor(getMyColor(android.R.color.white))
        toolbar.setSubtitleTextColor(getMyColor(android.R.color.white))
        toolbar.setBackgroundColor(getMyColor(R.color.colorPrimaryDark))

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = object : ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.app_name, R.string.app_name
        ){
            override fun onDrawerClosed(view: View){
                super.onDrawerClosed(view)
                Log.d("Drawer", "onDrawerClosed")
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                Log.d("Drawer", "onDrawerOpened")
            }
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener{
            v: View -> drawerLayout.openDrawer(Gravity.LEFT)
        }

        toolbar.setNavigationIcon(R.mipmap.rinkaku_naviicon)

        val adapter: ArrayAdapter<String> = ItemListAdapter(this)
        val partsList = findViewById<ListView>(R.id.parts_names)
        val arr = resources.getStringArray(R.array.parts_names)
        adapter.addAll(arr.toList())
        partsList.adapter = adapter

        val iv_eye = findViewById<ImageView>(R.id.eye)
        iv_eye.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> Log.d("onLayoutChange", "change") }
    }

    //sdk23からはgetColorが非推奨になり、ContextCompat.getColorを使うようになりました。
    private fun getMyColor(id: Int): Int{
        return ContextCompat.getColor(this, id)
    }

    private fun convertDpToPx(dp: Int): Int{
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

}
