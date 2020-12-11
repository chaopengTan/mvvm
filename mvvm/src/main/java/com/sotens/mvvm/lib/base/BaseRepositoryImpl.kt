package com.sotens.mvvm.lib.base

import androidx.lifecycle.MutableLiveData
import com.sotens.mvvm.lib.HTTPCompAbs
import kotlinx.coroutines.Deferred
import java.util.*
import java.util.concurrent.TimeoutException

abstract class BaseRepositoryImpl {

    open suspend fun <T> observeGo(
        observable: Deferred<T>,
        liveData: MutableLiveData<T>
    ): MutableLiveData<T>? {
        return observe(observable, liveData)
    }

    open suspend fun <T> observe(
        observable: Deferred<T>,
        liveData: MutableLiveData<T>
    ): MutableLiveData<T>? {
        val httpCompAbs = ServiceLoader.load(HTTPCompAbs::class.java, javaClass.classLoader).toList()
        if(httpCompAbs.isEmpty()){
            throw TimeoutException("it is Null")
        }
        var t = observable.await()
        (httpCompAbs[0] as HTTPCompAbs<T>).checkHttpResults(t)
        liveData.postValue(t)
        return liveData
    }
}