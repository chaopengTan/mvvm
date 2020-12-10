package com.sotens.mvvm.android

import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import com.sotens.mvvm.android.bean.LoginBean

class UtilToast {
    private var toast: Toast? = null

    companion object {
        var utilToast:UtilToast ? = null
        fun getToast(): UtilToast? {
            if(utilToast==null){
                synchronized(UtilToast::class.java){
                    if(utilToast==null)
                        utilToast = UtilToast()
                }
            }
            return utilToast
        }
    }

    fun sendToast(message: String){
        if(TextUtils.isEmpty(message)){
            return
        }
        if(toast==null){
            toast = Toast.makeText(App.context, message, Toast.LENGTH_SHORT)
        }
        try {
            toast!!.setText(message)
            toast!!.setGravity(Gravity.CENTER, 0, 0)
            toast!!.show()
        } catch (e:Exception ) {
            e.printStackTrace()
        }
    }
}