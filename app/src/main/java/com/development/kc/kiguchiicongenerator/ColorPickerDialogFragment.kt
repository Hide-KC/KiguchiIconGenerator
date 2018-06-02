package com.development.kc.kiguchiicongenerator

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.SeekBar
import android.widget.SeekBar.*

class ColorPickerDialogFragment: DialogFragment() {
    private var targetPartsId: Int = 0

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

        val svPlane = view?.findViewById<SVPlaneView>(R.id.sv_plane)
        val hueBar = view?.findViewById<SeekBar>(R.id.hue_bar)
        hueBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                svPlane?.setHue(progress * 1f)
                svPlane?.invalidate()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                    Log.d(this.javaClass.simpleName, "OK")
                    dialog.dismiss()
                })
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