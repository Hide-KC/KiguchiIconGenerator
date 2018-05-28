package com.development.kc.kiguchiicongenerator

import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.constraint.ConstraintLayout
import android.support.graphics.drawable.VectorDrawableCompat
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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.main_views.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val commentLayer = findViewById<ConstraintLayout>(R.id.comment_layer)
        val commentHeight = commentLayer.height
        val textSize = (commentHeight * 0.1).roundToInt()
        val teststring = "₍₍(ง˘ω˘)ว⁾⁾"
        val testBitmap = BitmapGenerator.textToBitmap(Color.RED, teststring, textSize)

        val commentView = findViewById<ImageView>(R.id.comment)
        commentView.setImageBitmap(testBitmap)


        //テストコード
        /*ここから１セット*/
        var backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_backhair_1_color, null)
        var lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_backhair_1_line, null)
        setParts(R.id.hair_b_layer, lineDrawable, null, backDrawable, R.color.black)
        /*ここまで１セット*/

        /*ここから１セット*/
        backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_body_1_color, null)
        lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_body_1_line, null)
        setParts(R.id.body_layer, lineDrawable, null, backDrawable, R.color.pale_orange)
        /*ここまで１セット*/

        /*ここから１セット*/
        lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_eye_1_line, null)
        setParts(R.id.eye_layer, lineDrawable, null, null, null)
        /*ここまで１セット*/

        backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_mouth_1_color, null)
        lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_mouth_1_line, null)
        setParts(R.id.mouth_layer, lineDrawable, null, backDrawable, R.color.pink)

        backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_bang_1_color, null)
        lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_bang_1_line, null)
        setParts(R.id.bang_layer, lineDrawable, null, backDrawable, R.color.black)
        //テストここまで

        val backGround = findViewById<ConstraintLayout>(R.id.canvas_background)
        backGround.background.setColorFilter(getMyColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this, "ca-app-pub-4566858215490503~4860130627")
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        toolbar.setTitle(R.string.app_name)
//        toolbar.setSubtitle(R.string.subtitle)
        toolbar.setTitleTextColor(getMyColor(android.R.color.white))
        toolbar.setSubtitleTextColor(getMyColor(android.R.color.white))
//        toolbar.setBackgroundColor(getMyColor(R.color.colorPrimaryDark))

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

        val adapter: ArrayAdapter<Int> = ItemListAdapter(this)
        val partsList = findViewById<ListView>(R.id.parts_names)
        val arr = resources.getStringArray(R.array.parts_names)
//        adapter.addAll(arr.toList())
        partsList.adapter = adapter

        //ColorPickerDialogの呼出し
        this.imageView.setOnClickListener{ v: View ->
            var selectColor: Int = android.R.color.black
            val listener = ColorPickerDialog.OnColorChangedListener{ color: Int ->
                selectColor = color
            }
            val dialog = ColorPickerDialog(this, listener, android.R.color.black)
            dialog.show()
        }

    }

    //sdk23からはgetColorが非推奨になり、ContextCompat.getColorを使うようになりました。
    private fun getMyColor(id: Int): Int{
        return ContextCompat.getColor(this, id)
    }

    private fun setParts(layerId: Int, @Nullable lineDrawable: VectorDrawableCompat?, @Nullable lineColorId: Int?, @Nullable backDrawable: VectorDrawableCompat?, @Nullable backColorId: Int?){
        if (lineColorId is Int && lineDrawable is VectorDrawableCompat){
            lineDrawable.setColorFilter(getMyColor(lineColorId), PorterDuff.Mode.SRC_ATOP)
        }

        if (backColorId != null && backDrawable is VectorDrawableCompat){
            backDrawable.setColorFilter(getMyColor(backColorId), PorterDuff.Mode.SRC_ATOP)
        }

        val layer =  findViewById<ConstraintLayout>(layerId)
        val backImg = layer.findViewById<ImageView>(R.id.base_back)
        backImg.setImageDrawable(backDrawable)
        val lineImg = layer.findViewById<ImageView>(R.id.base_line)
        lineImg.setImageDrawable(lineDrawable)
    }
}
