package com.sotens.mvvm.android.test

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.sotens.mvvm.android.UtilToast
import com.sotens.mvvm.android.bean.LoginBean
import com.sotens.mvvm.android.dto.LoginDTO
import com.sotens.mvvm.android.http.BaseDTO
import com.sotens.mvvm.lib.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel<RepositoryImpl>(application){
    var loginBean: ObservableField<LoginBean> = ObservableField<LoginBean>()

    var loginDTO = MutableLiveData<BaseDTO<LoginDTO>>()

    init {
        loginBean.set(LoginBean())
    }

    override fun getRepositoryImpl(): RepositoryImpl {
        return RepositoryImpl()
    }



    fun sendRequest(view:View){
        var bean:LoginBean = loginBean.get()!!
        viewModelScope.launch {
//            getRepository()
//                    .observeGo( getRepository().sendLogin(bean),loginDTO)
//                    .
//                    .observeForever {  }
            getRepository().sendLogin(bean)
                    .await()
                    .let {
                        var baseDTO = it!!
                        if(baseDTO!=null){
                            UtilToast.getToast()!!.sendToast(it.message)
                        }
                    }

        }


    }


}