package com.sotens.mvvm.lib.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

abstract class BaseViewModel <T : BaseRepositoryImpl>(application: Application) :
    AndroidViewModel(application) {
    private var repository: T? = null

    init {
        repository = getRepositoryImpl()
    }

    abstract fun getRepositoryImpl():T

    open fun getRepository(): T {
        return repository!!
    }
}