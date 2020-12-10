package net.util.manager

import android.util.Log
import net.util.manager.config.ClientOpts
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        if(ClientOpts.isDebug){
            Log.d("log_message",message)
        }
    }
}