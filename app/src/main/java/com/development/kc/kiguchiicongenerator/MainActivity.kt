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
import android.os.Build
import android.preference.PreferenceManager


class MainActivity : AppCompatActivity(), PartsGridFragment.OnPartsClickListener, ControllerFragment.OnKeyClickedListener, IconViewFragment.OnIconUpdateListener {
    private val FRAGMENT_STATE = "fragment_state"
    private var swapFragmentState = FragmentTag.PARTS_GRID

    enum class FragmentTag(tag: String){
        CONTROLLER("controller"), PARTS_GRID("parts_grid"), ICON_VIEW("icon_view")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun iconUpdate(group: IconViewFragment.GroupEnum, partsId: Int, tintColor: Int, lineColor: Int) {
        val fragment = supportFragmentManager.findFragmentByTag(FragmentTag.ICON_VIEW.name)
        if (fragment is IconViewFragment){
            fragment.iconUpdate(group, partsId, tintColor, lineColor)
        }
    }

    override fun onPartsClicked(resStr: String) {
        //resStr: ic_[Group]_[PartsID]_tint/line
        Toast.makeText(this, resStr, Toast.LENGTH_SHORT).show()

        val arr = resStr.split('_')
        val group = IconViewFragment.GroupEnum.getTypeByValue(arr[1])
        val partsId = arr[2].toInt()
        //TODO 色の取得方法どうするかー！？
        iconUpdate(group, partsId, getMyColor(R.color.pale_orange), getMyColor(R.color.black))
    }

    override fun onKeyClicked(keyDirection: ControllerFragment.KeyDirection) {

    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (outState is Bundle){
            outState.putString(FRAGMENT_STATE, swapFragmentState.name)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState is Bundle){
            val state = savedInstanceState[FRAGMENT_STATE].toString()
            if (state != ""){
                swapFragmentState = FragmentTag.valueOf(state)
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            //TODO DefaultPreferenceから引き出す？どう選択値を保存する？


//            val commentLayer = findViewById<ConstraintLayout>(R.id.comment_layer)
//            val commentHeight = commentLayer.height
//            val textSize = (commentHeight * 0.1).roundToInt()
//            val teststring = "せりふ！"
//            val testBitmap = DrawableController.textToBitmap(this, getMyColor(R.color.black), teststring, textSize)
//
//            val commentView = findViewById<ImageView>(R.id.comment)
//            commentView.setImageBitmap(testBitmap)
//
//
//
//
//
//            //テストコード
//            /*ここから１セット*/
//            var backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_backhair_001_tint, null)
//            var lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_backhair_001_line, null)
//            updateIconView(R.id.backhair_layer, lineDrawable, null, backDrawable, null)
//            /*ここまで１セット*/
//
//            /*ここから１セット*/
//            backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_body_001_tint, null)
//            lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_body_001_line, null)
//            updateIconView(R.id.body_layer, lineDrawable, null, backDrawable, null)
//            /*ここまで１セット*/
//
//            /*ここから１セット*/
//            lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_eye_001_line, null)
//            updateIconView(R.id.eye_layer, lineDrawable, null, null, null)
//            /*ここまで１セット*/
//
//            backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_mouth_001_tint, null)
//            lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_mouth_001_line, null)
//            updateIconView(R.id.mouth_layer, lineDrawable, null, backDrawable, null)
//
//            backDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_bang_001_tint, null)
//            lineDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_bang_001_line, null)
//            updateIconView(R.id.bang_layer, lineDrawable, null, backDrawable, null)
//            //テストここまで
//
//            val backGround = findViewById<ConstraintLayout>(R.id.canvas_background)
//            backGround.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
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
            if (swapFragmentState == FragmentTag.PARTS_GRID){
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
        //PartsGridの生成
        if (selectedGroup == ""){
            fragmentTransaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, "backhair"))
        } else {
            fragmentTransaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, selectedGroup))
        }
        //IconViewの生成
        val icon = IconDTO().also {
            it.backHairID = 1
            it.bangID = 1
            it.bodyID = 1
            it.eyeID = 1
            it.mouthID = 1
            it.hairTintColor = Color.LTGRAY
            it.hairLineColor = Color.DKGRAY
            it.bodyTintColor = getMyColor(R.color.pale_orange)
            it.bodyLineColor = Color.DKGRAY
            it.eyeLineColor = Color.DKGRAY
            it.mouthTintColor = getMyColor(R.color.pink)
            it.mouthLineColor = Color.DKGRAY
        }
        fragmentTransaction.replace(R.id.canvas_layout, IconViewFragment.newInstance(null,icon), FragmentTag.ICON_VIEW.name)
        fragmentTransaction.commit()

        val swapBtn = findViewById<Button>(R.id.swap_button)
        swapBtn.setOnClickListener{v: View ->
            val group = pref.getString("group", "backhair")

            val transaction = supportFragmentManager.beginTransaction()
            if (swapFragmentState == FragmentTag.PARTS_GRID){
                val f = supportFragmentManager.findFragmentByTag(FragmentTag.CONTROLLER.name)
                if (f == null){
                    transaction.replace(R.id.swap_layout, ControllerFragment.newInstance(null, group), FragmentTag.CONTROLLER.name)
                } else {
                    transaction.attach(f)
                }
                transaction.commit()
                swapFragmentState = FragmentTag.CONTROLLER
            } else if (swapFragmentState == FragmentTag.CONTROLLER){
                val f = supportFragmentManager.findFragmentByTag(FragmentTag.PARTS_GRID.name)
                if (f == null){
                    transaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, group), FragmentTag.PARTS_GRID.name)
                } else {
                    transaction.attach(f)
                }
                transaction.commit()
                swapFragmentState = FragmentTag.PARTS_GRID
            }
        }

    }

    //sdk23からはgetColorが非推奨になり、ContextCompat.getColorを使うようになりました。
    private fun getMyColor(id: Int): Int{
        return ContextCompat.getColor(this, id)
    }

    //アイコン表示部がFragment化されたため、書換が必要
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
