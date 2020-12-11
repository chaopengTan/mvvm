package com.sotens.mvvm.lib.base

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sotens.mvvm.lib.R
import com.sotens.mvvm.lib.controlview.ControlViewUtil
import com.sotens.mvvm.lib.controlview.ViewControImp
import com.sotens.mvvm.lib.databinding.ActivityRoomMainBinding

open abstract class BaseActivity <T:BaseRepositoryImpl,VM :BaseViewModel<T>, VDB : ViewDataBinding> : AppCompatActivity(){
    //获取当前activity布局文件
    protected abstract fun getContentViewId(): Int

    //处理逻辑业务
    open  fun initInject(){}

    open fun setListener(){}

    open  fun getActivityTitleId():Int {
        return 0
    }

    open fun getViewControlImp(): ViewControImp? {
        return null
    }

    protected var binding: ActivityRoomMainBinding? = null
    protected lateinit var contentBindg:VDB

    var controlViewUtil = ControlViewUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_room_main)
        binding!!.lifecycleOwner = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        createContentView()
        initInject()
        setListener()
    }


    private fun createContentView(){
        if(getActivityTitleId()!=0){
            binding!!.root.findViewById<FrameLayout>(R.id.fl_top_view)
                .addView(LayoutInflater.from(this).inflate(getActivityTitleId(),null))
        }

        var contentView = LayoutInflater.from(this).inflate(getContentViewId(),null)

        contentBindg = DataBindingUtil.bind<VDB>(contentView)!!
        binding!!.root.findViewById<FrameLayout>(R.id.fl_content_view)
            .addView(contentView)
        setControlView(contentView)
    }

    private fun setControlView(contentView:View){
        if(getViewControlImp()!=null){
            var controlImp = getViewControlImp() as ViewControImp
            controlViewUtil.initControlView(getContentViewId(),contentView,controlImp)
        }
    }


    open fun showContent(){
        controlViewUtil.showContent(getContentViewId())
    }

    open fun showNotDataView(){
        controlViewUtil.showNotDataView(this, binding!!.root.findViewById<FrameLayout>(R.id.fl_content_view))
    }

    open fun showNetworkErrorView(){
        controlViewUtil.showNetworkErrorView(this, binding!!.root.findViewById<FrameLayout>(R.id.fl_content_view))
    }

    open fun showTimeOutView(){
        controlViewUtil.showTimeOutView(this, binding!!.root.findViewById<FrameLayout>(R.id.fl_content_view))
    }

    open fun showOtherErrorView(){
        controlViewUtil.showOtherErrorView(this, binding!!.root.findViewById<FrameLayout>(R.id.fl_content_view))
    }

    open fun showToast( msg:Int) {
        Toast.makeText(this, "" + resources.getString(msg), Toast.LENGTH_SHORT).show();
    }

    open fun showToast( msg:String) {
        if(TextUtils.isEmpty(msg)|| msg == "null"){
            return;
        }
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    open fun showToast(view: View?) {
        val toast = Toast(this)
        toast.view = view
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        controlViewUtil.clear()
    }

}