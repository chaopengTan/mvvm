package net.util.manager.config

import android.content.Context

class AppOpts {
    companion object{
        private var context: Context? =null
        var isDebug = false
        lateinit var TAG:String
        fun initContent(context: Context){
            this.context = context
            TAG = getContenxt().packageName + "_TAG"
        }

        fun getContenxt():Context{
            return this!!.context!!
        }



    }
}