package net.util.manager.config

abstract class ClientOpts {
    companion object {
        var isDebug:Boolean = false
        var parameter:Map<String,String>?=null
        var DEFAULT_TIMEOUT = 10
        var DEFAULT_UPLOAD_TIMEOUT = 60
        fun initParameter(parameter:Map<String,String>){
            this.parameter = parameter
        }
        var URL_GITHUB:String =""
    }
}