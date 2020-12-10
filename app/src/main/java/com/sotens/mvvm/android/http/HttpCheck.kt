package com.sotens.mvvm.android.http

import android.util.Log
import android.widget.Toast
import com.google.auto.service.AutoService
import com.sotens.mvvm.android.App
import com.sotens.mvvm.lib.HTTPCompAbs

@AutoService(HTTPCompAbs::class)
class HttpCheck<T> : HTTPCompAbs<BaseDTO<T>>() {
    override fun checkHttpResults(t: BaseDTO<T>) {
        if(t.code!=200){
            Log.d("checkHttpResults","请求网络失败:"+t.message)
        }else{
            Log.d("checkHttpResults","请求网络成功:"+t.message)
        }
    }
}