package com.development.kc.kiguchiicongenerator

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.ImageView

class ColorPickerDialogFragment: DialogFragment() {
    override fun onDestroy() {
        super.onDestroy()
        mColorChangeSubject.detachAll()
    }

    public val BACK = 0
    public val LINE = 1
    private val mColorChangeSubject: Subject = ColorChangeSubject()
    private var targetPartsId: Int = 0
    private var targetColorArea: Int = BACK

    init {

    }

    override fun onCreateDialog(savedInstanceState: Bundle?):  Dialog {
//        if (this.targetFragment is /*Fragment継承型*/){
//            //なんかの処理
//        }

        //レイアウト展開
        val view = activity?.layoutInflater?.inflate(R.layout.color_select_dialog, null)
        val args = arguments
        if (args != null){
            targetPartsId = args.getInt("parts_id", -1)
        }

        /*
        * イベント付与したり値をセットしたり
        * */

        //プレビューウィンドウの取得
        val preview = view?.findViewById<ConstraintLayout>(R.id.preview_layout)

        //セリフの設定
        if (preview != null){
            val viewTreeObserver = preview.viewTreeObserver
            viewTreeObserver.addOnGlobalLayoutListener {
                val textSize = Math.round(preview.height * 0.1f)
                val teststring = "ぷれびゅーがめん！"
                val testBitmap = DrawableController.textToBitmap(this.activity!!, Color.BLACK, teststring, textSize)

                val commentView = preview.findViewById<ImageView>(R.id.comment)
                commentView?.setImageBitmap(testBitmap)
            }
        }



        //セットするImageViewを取得
        var baseLineImage = preview?.findViewById<ConstraintLayout>(R.id.hair_b_layer)?.findViewById<ImageView>(R.id.base_line)
        var baseBackImage = preview?.findViewById<ConstraintLayout>(R.id.hair_b_layer)?.findViewById<ImageView>(R.id.base_tint)
        var lineVD = VectorDrawableCompat.create(resources, R.drawable.ic_backhair_001_line, null)
        var backVD = VectorDrawableCompat.create(resources, R.drawable.ic_backhair_001_tint, null)
        baseLineImage?.setImageDrawable(lineVD)
        baseBackImage?.setImageDrawable(backVD)

        baseLineImage = preview?.findViewById<ConstraintLayout>(R.id.body_layer)?.findViewById(R.id.base_line)
        baseBackImage = preview?.findViewById<ConstraintLayout>(R.id.body_layer)?.findViewById(R.id.base_tint)
        lineVD = VectorDrawableCompat.create(resources, R.drawable.ic_body_001_line, null)
        backVD = VectorDrawableCompat.create(resources, R.drawable.ic_body_001_tint, null)
        baseLineImage?.setImageDrawable(lineVD)
        baseBackImage?.setImageDrawable(backVD)

        baseLineImage = preview?.findViewById<ConstraintLayout>(R.id.eye_layer)?.findViewById(R.id.base_line)
        lineVD = VectorDrawableCompat.create(resources, R.drawable.ic_eye_001_line, null)
        baseLineImage?.setImageDrawable(lineVD)

        baseLineImage = preview?.findViewById<ConstraintLayout>(R.id.mouth_layer)?.findViewById(R.id.base_line)
        baseBackImage = preview?.findViewById<ConstraintLayout>(R.id.mouth_layer)?.findViewById(R.id.base_tint)
        lineVD = VectorDrawableCompat.create(resources, R.drawable.ic_mouth_001_line, null)
        backVD = VectorDrawableCompat.create(resources, R.drawable.ic_mouth_001_tint, null)
        baseLineImage?.setImageDrawable(lineVD)
        baseBackImage?.setImageDrawable(backVD)

        baseLineImage = preview?.findViewById<ConstraintLayout>(R.id.bang_layer)?.findViewById(R.id.base_line)
        baseBackImage = preview?.findViewById<ConstraintLayout>(R.id.bang_layer)?.findViewById(R.id.base_tint)
        lineVD = VectorDrawableCompat.create(resources, R.drawable.ic_bang_001_line, null)
        backVD = VectorDrawableCompat.create(resources, R.drawable.ic_bang_001_tint, null)
        baseLineImage?.setImageDrawable(lineVD)
        baseBackImage?.setImageDrawable(backVD)

        //BACK OR LINEの変更はImageView.onClickで切り替え
        val backColorImage = view?.findViewById<ImageView>(R.id.back_color_image)
        val lineColorImage = view?.findViewById<ImageView>(R.id.line_color_image)
        backColorImage?.setOnClickListener{ targetColorArea = BACK }
        lineColorImage?.setOnClickListener{ targetColorArea = LINE }

        val sbPlane = view?.findViewById<AbsHSBView>(R.id.sb_plane)
        sbPlane?.setOnHSBChangeListener(object: AbsHSBView.OnHSBChangedListener{
            override fun onHSBChanged(hue: Float, saturation: Float, brightness: Float) {
                val color = Color.HSVToColor(floatArrayOf(hue, saturation, brightness))
                //targetColorArea, パーツのImageViewにもIObserverを実装する（カスタムビュー化）
                //→HueBarやSBPlaneなど、notify()を叩いたら全てに反映される。

                if (targetColorArea == BACK){
                    backColorImage?.setColorFilter(color)
                    baseBackImage?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                } else if (targetColorArea == LINE){
                    lineColorImage?.setColorFilter(color)
                    baseLineImage?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                }

                mColorChangeSubject.notifyColorChange(hue, saturation, brightness)
            }
        })

        val hueBar = view?.findViewById<AbsHSBView>(R.id.hue_bar)
        hueBar?.setOnHSBChangeListener(object: AbsHSBView.OnHSBChangedListener{
            override fun onHSBChanged(hue: Float, saturation: Float, brightness: Float) {
                mColorChangeSubject.notifyColorChange(hue, saturation, brightness)
            }
        })

        if (sbPlane is IObserver){
            mColorChangeSubject.attach(sbPlane)
        }

        if (hueBar is IObserver){
            mColorChangeSubject.attach(hueBar)
        }

        Log.d(this.javaClass.simpleName, hueBar?.width.toString())

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    Log.d(this.javaClass.simpleName, "OK")
                    dialog.dismiss()
                }
        return builder.create()
    }

    companion object {
        fun newInstance(targetFragment: Fragment?, targetPartsId: Int): ColorPickerDialogFragment{
            val args = Bundle()
            args.putInt("parts_id", targetPartsId)
            val fragment = ColorPickerDialogFragment()
            fragment.arguments = args
            fragment.setTargetFragment(targetFragment, 0)
            return fragment
        }
    }
}