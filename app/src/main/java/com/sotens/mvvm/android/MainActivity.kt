package com.sotens.mvvm.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sotens.mvvm.android.databinding.ActivityMainBinding
import com.sotens.mvvm.android.test.LoginViewModel
import com.sotens.mvvm.android.test.RepositoryImpl
import com.sotens.mvvm.lib.base.BaseActivity

class MainActivity : BaseActivity<RepositoryImpl, LoginViewModel, ActivityMainBinding>() {
    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun initInject() {
        super.initInject()
        contentBindg.setVariable(BR.loginModel,LoginViewModel(application))
    }

}