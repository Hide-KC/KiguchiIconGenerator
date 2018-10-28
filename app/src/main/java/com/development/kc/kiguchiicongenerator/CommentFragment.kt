package com.development.kc.kiguchiicongenerator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.comment_fragment.view.*

class CommentFragment: Fragment(){
    interface ICommentUpdate{
        fun commentUpdate(comment: String?)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.comment_fragment, container, false)
        view.comment_edit.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val c = context
                if (c is ICommentUpdate){
                    c.commentUpdate(p0.toString())
                }
            }
        })

        view.clear_btn.setOnClickListener{
            view.comment_edit.editableText.clear()
        }

        return view
    }

    companion object {
        fun newInstance(targetFragment: Fragment?): CommentFragment{
            val args = Bundle()
            val fragment = CommentFragment()
            fragment.arguments = args

            fragment.setTargetFragment(targetFragment, 0)
            return fragment

        }
    }
}