package com.development.kc.kiguchiicongenerator

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
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
import android.preference.PreferenceManager


class MainActivity : AppCompatActivity(), PartsGridFragment.OnPartsClickListener, ControllerFragment.OnKeyClickedListener, IconViewFragment.OnIconUpdatedListener, CommentFragment.ICommentUpdate {
    private val FRAGMENT_STATE = "fragment_state"
    private var swapFragmentState = FragmentTag.PARTS_GRID

    enum class FragmentTag(tag: String){
        CONTROLLER("controller"), PARTS_GRID("parts_grid"), ICON_VIEW("icon_view")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK){
            if (data != null){
                val partsId = data.getIntExtra("partsId", 1)
                val colors = data.getIntArrayExtra("colors")
                val defaultPref = PreferenceManager.getDefaultSharedPreferences(this)
                val ordinal = defaultPref.getInt("group", 0)
                val group = IconLayout.GroupEnum.values()[ordinal]
                iconUpdate(group, partsId, colors[0], colors[1])
            }
        }
    }

    override fun commentUpdate(comment: String?) {
        val fragment = supportFragmentManager.findFragmentByTag(FragmentTag.ICON_VIEW.name)
        if (fragment is IconViewFragment){
            fragment.commentUpdate(comment)
        }
    }

    override fun iconUpdate(group: IconLayout.GroupEnum, partsId: Int, tintColor: Int, lineColor: Int) {
        val fragment = supportFragmentManager.findFragmentByTag(FragmentTag.ICON_VIEW.name)
        if (fragment is IconViewFragment){
            fragment.iconUpdate(group, partsId, tintColor, lineColor)
        }
    }

    override fun partsSelected(group: IconLayout.GroupEnum, partsId: Int) {
        val fragment = supportFragmentManager.findFragmentByTag(FragmentTag.ICON_VIEW.name)
        if (fragment is IconViewFragment){
            fragment.partsSelected(group, partsId)
        }
    }

    override fun onPartsClicked(resStr: String) {
        //resStr: ic_[Group]_[PartsID]_tint/line
        Toast.makeText(this, resStr, Toast.LENGTH_SHORT).show()

        //TODO 不安定すぎる実装
        val arr = resStr.split('_')
        val group = IconLayout.GroupEnum.getTypeByValue(arr[1])
        val partsId = arr[2].toInt()

        partsSelected(group, partsId)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this, getString(R.string.adunit_id))
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
        val defaultPref = PreferenceManager.getDefaultSharedPreferences(this)
        val ordinal = defaultPref.getInt("group", 0)
        val group = IconLayout.GroupEnum.values()[ordinal]

        groupList.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val resId = adapterView.adapter.getItem(i) as Int //R.drawable.xxx
            val resArray = resources.getResourceEntryName(resId).split('_') //ic_Group_PartsId_tint
            val g = IconLayout.GroupEnum.getTypeByValue(resArray[1])
            //selectedGroupの保存
            PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("group", g.ordinal).apply()
            if (swapFragmentState == FragmentTag.PARTS_GRID){
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, g))
                transaction.commit()
            }
        }


        val fragmentTransaction = supportFragmentManager.beginTransaction()
        //PartsGridの生成
        fragmentTransaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, group))
        fragmentTransaction.replace(R.id.canvas_layout, IconViewFragment.newInstance(null), FragmentTag.ICON_VIEW.name)
        fragmentTransaction.commit()

        //ColorPickerDialogの呼出し
        val iv = findViewById<ImageView>(R.id.imageView)
        iv.setOnClickListener{ v: View ->
            //色選択対象のIDを渡す。R.drawable.xxx
            val groupInt = defaultPref.getInt("group", 0)
            val groupEnum = IconLayout.GroupEnum.values()[groupInt]

            //TODO カラーピッカーはダイアログじゃなくてSwapLayoutにしたほうがいいかも
            val dialog = ColorPickerDialogFragment.newInstance(null, groupEnum)
            dialog.show(this.supportFragmentManager, this.javaClass.simpleName)
        }

        //SwapFragmentの設定
        val swapBtn = findViewById<Button>(R.id.swap_button)
        swapBtn.setOnClickListener{v: View ->
            val childOrdinal = PreferenceManager.getDefaultSharedPreferences(this).getInt("group", 0)
            val groupEnum = IconLayout.GroupEnum.values()[childOrdinal]

            val transaction = supportFragmentManager.beginTransaction()
            if (swapFragmentState == FragmentTag.PARTS_GRID){
                val f = supportFragmentManager.findFragmentByTag(FragmentTag.CONTROLLER.name)
                if (f == null){
                    transaction.replace(R.id.swap_layout, ControllerFragment.newInstance(null, groupEnum), FragmentTag.CONTROLLER.name)
                } else {
                    transaction.attach(f)
                }
                transaction.commit()
                swapFragmentState = FragmentTag.CONTROLLER
            } else if (swapFragmentState == FragmentTag.CONTROLLER){
                val f = supportFragmentManager.findFragmentByTag(FragmentTag.PARTS_GRID.name)
                if (f == null){
                    transaction.replace(R.id.swap_layout, PartsGridFragment.newInstance(null, groupEnum), FragmentTag.PARTS_GRID.name)
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
}
