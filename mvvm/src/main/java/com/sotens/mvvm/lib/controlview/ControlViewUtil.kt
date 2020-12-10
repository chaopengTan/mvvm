package com.sotens.mvvm.lib.controlview

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.util.forEach

class ControlViewUtil {
    var spArray : SparseArray<View> = SparseArray()
    lateinit var viewControImp:ViewControImp
    fun initControlView(contentId:Int,contentView:View,imp: ViewControImp){
       if( spArray[contentId]==null){
           spArray.put(contentId,contentView)
       }
        viewControImp = imp
    }

    fun showContent(contentId:Int){
        holdAllView()
        spArray.get(contentId).visibility = View.VISIBLE
    }

    fun showNotDataView(context:Context,frameLayout: FrameLayout){
        var notDataView = viewControImp.getNotDataView()
        if(notDataView==0){
            return
        }
        holdAllView()
        if(spArray[notDataView]==null){
            var view = LayoutInflater.from(context).inflate(notDataView,null)
            frameLayout.addView(view)
            spArray.put(notDataView,view)
        }
        spArray[notDataView].visibility = View.VISIBLE
    }


    fun showNetworkErrorView(context:Context,frameLayout: FrameLayout){
        var netWorkView = viewControImp.getNetworkErrorView()
        if(netWorkView==0){
            return
        }
        holdAllView()
        if(spArray[netWorkView]==null){
            var view = LayoutInflater.from(context).inflate(netWorkView,null)
            frameLayout.addView(view)
            spArray.put(netWorkView,view)
        }
        spArray[netWorkView].visibility = View.VISIBLE
    }

    fun showTimeOutView(context:Context,frameLayout: FrameLayout){
        var timeOutErrorView = viewControImp.getTimeOutErrorView()
        if(timeOutErrorView==0){
            return
        }
        holdAllView()
        if(spArray[timeOutErrorView]==null){
            var view = LayoutInflater.from(context).inflate(timeOutErrorView,null)
            frameLayout.addView(view)
            spArray.put(timeOutErrorView,view)
        }
        spArray[timeOutErrorView].visibility = View.VISIBLE
    }

    fun showOtherErrorView(context:Context,frameLayout: FrameLayout){
        var ortherErrorView = viewControImp.getOrtherErrorView()
        if(ortherErrorView==0){
            return
        }
        holdAllView()
        if(spArray[ortherErrorView]==null){
            var view = LayoutInflater.from(context).inflate(ortherErrorView,null)
            frameLayout.addView(view)
            spArray.put(ortherErrorView,view)
        }
        spArray[ortherErrorView].visibility = View.VISIBLE
    }



    private fun holdAllView(){
        spArray.forEach { _, value -> value.visibility = View.GONE }
    }

    fun clear(){
        spArray.clear()
    }
}