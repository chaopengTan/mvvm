package net.util.manager.apiutil

import android.text.TextUtils
import com.google.gson.Gson
import net.util.manage.api.IApiServerAbs
import net.util.manager.Client
import net.util.manager.HttpManager
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*
import java.util.concurrent.TimeoutException

class ApiHelper {
    companion object{
         fun createRequestBody(request: Any): RequestBody {
            return RequestBody.create(MediaType.parse("application/json"), Gson().toJson(request))
        }

         fun createRequestMap(obj: Any): Map<String, String> {
            val map = HashMap<String,String>()
            val clazz = obj.javaClass
            for (field in clazz.declaredFields) {
                field.isAccessible = true
                val fieldName = field.name
                var value = ""
                try {
                    value = if (field.get(obj) != null) {
                        field.get(obj).toString()
                    } else {
                        ""
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                if (!TextUtils.isEmpty(value)) {
                    map[fieldName] = value
                }
            }
            return map
        }

        fun <T> getHttpService(): T {
            val bookList = ServiceLoader.load(IApiServerAbs::class.java, javaClass.classLoader).toList()
            if(bookList.isEmpty()){
                throw TimeoutException("it is Null")
            }
            val tClass = bookList[0].getServer()
            return HttpManager.getService(
                tClass,
                Client::class.java
            ) as T
        }
    }


}