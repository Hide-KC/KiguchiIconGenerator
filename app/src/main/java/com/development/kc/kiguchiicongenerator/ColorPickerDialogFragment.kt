package com.development.kc.kiguchiicongenerator

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log

class ColorPickerDialogFragment: DialogFragment() {
    override fun onDestroy() {
        super.onDestroy()
        mColorChangeSubject.detachAll()
    }

    private val mColorChangeSubject: Subject = ColorChangeSubject()
    private var group: IconLayout.GroupEnum = IconLayout.GroupEnum.BACK_HAIR
    private var targetColorArea: IconLayout.BaseTypeEnum = IconLayout.BaseTypeEnum.TINT
    private val changed = intArrayOf(0,0)

    override fun onCreateDialog(savedInstanceState: Bundle?):  Dialog {
//        if (this.targetFragment is /*Fragment継承型*/){
//            //なんかの処理
//        }

        val fragment = activity?.supportFragmentManager?.findFragmentByTag(MainActivity.FragmentTag.ICON_VIEW.name)
        val icon = when(fragment){
            is IconViewFragment -> fragment.getIcon()
            else -> IconDTO()
        }

        //レイアウト展開
        val view = activity?.layoutInflater?.inflate(R.layout.color_select_dialog, null)
        if (view != null){
            val args = arguments
            if (args != null){
                val ordinal = args.getInt("group", 0)
                group = IconLayout.GroupEnum.values()[ordinal]
            }

            /*
            * イベント付与したり値をセットしたり
            * */

            //初期カラー
            changed[0] = icon.getColorFilter(group, IconLayout.BaseTypeEnum.TINT)
            changed[1] = icon.getColorFilter(group, IconLayout.BaseTypeEnum.LINE)

            //プレビューウィンドウの取得
            val iconLayout = view.findViewById<IconLayout>(R.id.icon_preview)
            for (group in IconLayout.GroupEnum.values()){
                iconLayout.setParts(group, icon.getPartsId(group))
                for (type in IconLayout.BaseTypeEnum.values()){
                    iconLayout.setColorFilter(group, type, icon.getColorFilter(group, type))
                }
            }


            //TODO セリフの設定


            //TINT OR LINEの変更はImageView.onClickで切り替え
            val tintColorImage = view.findViewById<ObservableImageView>(R.id.tint_color_image)
            val lineColorImage = view.findViewById<ObservableImageView>(R.id.line_color_image)
            tintColorImage.setOnClickListener{ targetColorArea = IconLayout.BaseTypeEnum.TINT }
            lineColorImage.setOnClickListener{ targetColorArea = IconLayout.BaseTypeEnum.LINE }

            if (tintColorImage is IColorObserver){
                tintColorImage.setObserver(object: IColorObserver{
                    override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {
                        if (targetColorArea == IconLayout.BaseTypeEnum.TINT){
                            tintColorImage.setColorFilter(Color.HSVToColor(floatArrayOf(hue,saturation,brightness)))
                        }
                    }
                })
                mColorChangeSubject.attach(tintColorImage as IColorObserver)
            }

            if (lineColorImage is IColorObserver){
                lineColorImage.setObserver(object: IColorObserver{
                    override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {
                        if (targetColorArea == IconLayout.BaseTypeEnum.LINE){
                            lineColorImage.setColorFilter(Color.HSVToColor(floatArrayOf(hue,saturation,brightness)))
                        }
                    }
                })
                mColorChangeSubject.attach(lineColorImage as IColorObserver)
            }

            val sbPlane = view.findViewById<AbsHSBView>(R.id.sb_plane)
            sbPlane?.setOnHSBChangeListener(object: AbsHSBView.OnHSBChangedListener{
                override fun onHSBChanged(hue: Float, saturation: Float, brightness: Float) {
                    val color = Color.HSVToColor(floatArrayOf(hue, saturation, brightness))
                    //targetColorArea, パーツのImageViewにもIObserverを実装する（カスタムビュー化）
                    //→HueBarやSBPlaneなど、notify()を叩いたら全てに反映される。

                    iconLayout.setColorFilter(group, targetColorArea, color)
                    when(targetColorArea){
                        IconLayout.BaseTypeEnum.TINT -> changed[0] = color
                        IconLayout.BaseTypeEnum.LINE -> changed[1] = color
                    }
                    mColorChangeSubject.notifyColorChange(hue, saturation, brightness)
                }
            })

            val hueBar = view.findViewById<AbsHSBView>(R.id.hue_bar)
            hueBar?.setOnHSBChangeListener(object: AbsHSBView.OnHSBChangedListener{
                override fun onHSBChanged(hue: Float, saturation: Float, brightness: Float) {
                    mColorChangeSubject.notifyColorChange(hue, saturation, brightness)
                }
            })

            if (sbPlane is IColorObserver){
                mColorChangeSubject.attach(sbPlane)
            }

            if (hueBar is IColorObserver){
                mColorChangeSubject.attach(hueBar)
            }

            val redValue = view.findViewById<ObservableTextView>(R.id.red_value)
            val greenValue = view.findViewById<ObservableTextView>(R.id.green_value)
            val blueValue = view.findViewById<ObservableTextView>(R.id.blue_value)
            val code = view.findViewById<ObservableTextView>(R.id.code)
            redValue.setObserver(object: IColorObserver{
                override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {
                    val color = Color.HSVToColor(floatArrayOf(hue, saturation, brightness))
                    redValue.text = Color.red(color).toString()
                }
            })
            greenValue.setObserver(object: IColorObserver{
                override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {
                    val color = Color.HSVToColor(floatArrayOf(hue, saturation, brightness))
                    greenValue.text = Color.green(color).toString()
                }
            })
            blueValue.setObserver(object: IColorObserver{
                override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {
                    val color = Color.HSVToColor(floatArrayOf(hue, saturation, brightness))
                    blueValue.text = Color.blue(color).toString()
                }
            })
            code.setObserver(object: IColorObserver{
                override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {
                    val color = Color.HSVToColor(floatArrayOf(hue, saturation, brightness))
                    code.text = Integer.toHexString(color)
                }
            })
            mColorChangeSubject.attach(redValue)
            mColorChangeSubject.attach(greenValue)
            mColorChangeSubject.attach(blueValue)
            mColorChangeSubject.attach(code)



        }

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
                .setPositiveButton(android.R.string.ok) { dialog, resultCode ->
                    Log.d(this.javaClass.simpleName, "OK")
                    val intent = Intent().also {
                        it.putExtra("partsId", icon.getPartsId(group))
                        it.putExtra("colors", changed)
                    }
                    val pi = activity?.createPendingResult(targetRequestCode, intent, PendingIntent.FLAG_ONE_SHOT)
                    try {
                        pi?.send(Activity.RESULT_OK)
                    } catch (e: PendingIntent.CanceledException){
                        e.printStackTrace()
                    }
                    dismiss()
                }
        return builder.create()

    }

    companion object {
        fun newInstance(targetFragment: Fragment?, group: IconLayout.GroupEnum): ColorPickerDialogFragment{
            val args = Bundle()
            args.putInt("group", group.ordinal)
            val fragment = ColorPickerDialogFragment()
            fragment.arguments = args
            fragment.setTargetFragment(targetFragment, 100)
            return fragment
        }
    }
}