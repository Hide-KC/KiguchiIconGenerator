package com.development.kc.kiguchiicongenerator

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.constraint.ConstraintLayout
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlin.math.roundToInt
import android.os.Build
import android.preference.PreferenceManager


class MainActivity : AppCompatActivity(), PartsGridFragment.OnPartsClickListener, ControllerFragment.OnKeyClickedListener {
    private val layerMap = mapOf(
            "backhair" to R.id.hair_b_layer,
            "bang" to R.id.bang_layer,
            "body" to R.id.body_layer,
            "face" to R.id.face_layer,
            "eye" to R.id.eye_layer,
            "mouth" to R.id.mouth_layer,
            "hair-acc" to R.id.hair_acc_layer,
            "face-acc" to R.id.face_acc_layer
    )


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private val CONTROLLER = "controller"
    private val PARTS_GRID = "parts_grid"
    private val FRAGMENT_STATE = "fragment_state"

    private var swapFragmentState = PARTS_GRID

    override fun onPartsClicked(resStr: String) {
        //resStr: ic_[Group]_[Parts]_[PartsID]_tint/line
        Toast.makeText(this, resStr, Toast.LENGTH_SHORT).show()
        val tintResName = resStr.replace("line", "tint")
        val lineResName = resStr.replace("tint", "line")
        val tintDrwId = resources.getIdentifier(tintResName, "drawable", this.packageName)
        val lineDrwId = resources.getIdentifier(lineResName, "drawable", this.packageName)

        val arr = resStr.split('_')
        val layerId = layerMap[arr[1]]
        if (layerId != null){
            setParts(layerId, getDrawableResource(lineDrwId), Color.DKGRAY, getDrawableResource(tintDrwId), Color.LTGRAY)
        }
    }

    override fun onKeyClicked(keyDistraction: ControllerFragment.KeyDistraction) {

    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (outState is Bundle){
            outState.putString(FRAGMENT_STATE, swapFragmentState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState is Bundle){
            val state = savedInstanceState[FRAGMENT_STATE]
            if (state != null){
                swapFragmentState = state.toString()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            //TODO DefaultPreferenceから引き出す？どう選択値を保存する？


            val commentLayer = findViewById<ConstraintLayout>(R.id.comment_layer)
            val commentHeight = commentLayer.height
            val textSize = (commentHeight * 0.1).roundToInt()
            val teststring = "せりふ！"
            val testBitmap = DrawableController.textToBitmap(this, getMyColor(R.color.black), teststring, textSize)

            val commentView = findViewById<ImageView>(R.id.comment)
            commentView.setImageBitmap(testBitmap)





            //テストコード
            /*ここから１セット*/
            var backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_backhair_001_tint, null)
            var lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_backhair_001_line, null)
            setParts(R.id.hair_b_layer, lineDrawable, null, backDrawable, null)
            /*ここまで１セット*/

            /*ここから１セット*/
            backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_body_001_tint, null)
            lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_body_001_line, null)
            setParts(R.id.body_layer, lineDrawable, null, backDrawable, null)
            /*ここまで１セット*/

            /*ここから１セット*/
            lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_eye_001_line, null)
            setParts(R.id.eye_layer, lineDrawable, null, null, null)
            /*ここまで１セット*/

            backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_mouth_001_tint, null)
            lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_mouth_001_line, null)
            setParts(R.id.mouth_layer, lineDrawable, null, backDrawable, null)

            backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_bang_001_tint, null)
            lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_bang_001_line, null)
            setParts(R.id.bang_layer, lineDrawable, null, backDrawable, null)
            //テストここまで

            val backGround = findViewById<ConstraintLayout>(R.id.canvas_background)
            backGround.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this, "ca-app-pub-4566858215490503~4860130627")
        val adRequest = AdRequest.Builder().build()
        val adView = findViewById<AdView>(R.id.adView)
        adView.loadAd(adRequest)

        //Toolbarの生成
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

        //Drawerの生成
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener{
            v: View -> drawerLayout.openDrawer(Gravity.LEFT)
        }

        //HistoryListの生成
        val historyList = drawerLayout.findViewById<ListView>(R.id.history_list)
        val historyAdapter = HistoryListAdapter(this)
        historyList.adapter = historyAdapter

//        toolbar.setNavigationIcon(android.R.drawable.)

        //GroupListの生成
        val groupAdapter: ArrayAdapter<Int> = GroupListAdapter(this)
        val groupList = findViewById<ListView>(R.id.group_list)
        val arr = resources.getStringArray(R.array.group_names)
        groupAdapter.add(R.drawable.ic_backhair_001_tint)
        groupAdapter.add(R.drawable.ic_bang_001_tint)
        groupAdapter.add(R.drawable.ic_body_001_tint)
        groupAdapter.add(R.drawable.ic_eye_001_line)
        groupAdapter.add(R.drawable.ic_mouth_001_line)
        groupList.adapter = groupAdapter
        groupList.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val resId = adapterView.adapter.getItem(i) as Int //R.drawable.xxx
            val resArray = resources.getResourceEntryName(resId).split('_')
            val group = resArray[1]
            val defaultPref = PreferenceManager.getDefaultSharedPreferences(this)
            //selectedGroupの保存
            defaultPref.edit().putString("group", group).apply()
            val transaction = supportFragmentManager.beginTransaction()
            if (swapFragmentState == PARTS_GRID){
                transaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, group))
                transaction.commit()
            }
        }
        //ColorPickerDialogの呼出し
        val iv = findViewById<ImageView>(R.id.imageView)
        iv.setOnClickListener{ v: View ->
            //色選択対象のIDを渡す。R.drawable.xxx
            val dialog = ColorPickerDialogFragment.newInstance(null, 0)
            dialog.show(this.supportFragmentManager, this.javaClass.simpleName)
        }

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedGroup = pref.getString("group", "")
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (selectedGroup == ""){
            fragmentTransaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, "backhair"))
        } else {
            fragmentTransaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, selectedGroup))
        }
        fragmentTransaction.commit()

        val swapBtn = findViewById<Button>(R.id.swap_button)
        swapBtn.setOnClickListener{v: View ->
            val group = pref.getString("group", "backhair")

            val transaction = supportFragmentManager.beginTransaction()
            if (swapFragmentState == PARTS_GRID || swapFragmentState == ""){
                val f = supportFragmentManager.findFragmentByTag(CONTROLLER)
                if (f == null){
                    transaction.replace(R.id.swap_layout, ControllerFragment.newInstance(null, group), CONTROLLER)
                } else {
                    transaction.attach(f)
                }
                transaction.commit()
                swapFragmentState = CONTROLLER
            } else if (swapFragmentState == CONTROLLER){
                val f = supportFragmentManager.findFragmentByTag(PARTS_GRID)
                if (f == null){
                    transaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, group), PARTS_GRID)
                } else {
                    transaction.attach(f)
                }
                transaction.commit()
                swapFragmentState = PARTS_GRID
            }
        }

    }

    //sdk23からはgetColorが非推奨になり、ContextCompat.getColorを使うようになりました。
    private fun getMyColor(id: Int): Int{
        return ContextCompat.getColor(this, id)
    }

    private fun setParts(layerId: Int, lineDrawable: Drawable?, lineColor: Int?, backDrawable: Drawable?, backColor: Int?){
        if (lineColor is Int && lineDrawable is VectorDrawableCompat){
            lineDrawable.setColorFilter(lineColor, PorterDuff.Mode.SRC_ATOP)
        }

        if (backColor != null && backDrawable is VectorDrawableCompat){
            backDrawable.setColorFilter(backColor, PorterDuff.Mode.SRC_ATOP)
        }

        val layer =  findViewById<ConstraintLayout>(layerId)
        val backImg = layer.findViewById<ImageView>(R.id.base_tint)
        backImg.setImageDrawable(backDrawable)
        val lineImg = layer.findViewById<ImageView>(R.id.base_line)
        lineImg.setImageDrawable(lineDrawable)
    }

    private fun subStringResName(resName: String, cutLength: Int): String{
        return if (resName.length - cutLength <= 0){
            ""
        } else {
            resName.substring(0, resName.length - cutLength)
        }
    }

    @SuppressWarnings("deprecation")
    private fun getDrawableResource(id: Int): Drawable? {
        return when {
            id == 0 -> null
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> resources.getDrawable(id, null)
            else -> resources.getDrawable(id)
        }
    }
}
