package com.sotens.mvvm.android.http

data class BaseDTO<T> (var code:Int = 0,
                       var message:String="",
                       var status:String ="",
                       var data:T ?=null)
