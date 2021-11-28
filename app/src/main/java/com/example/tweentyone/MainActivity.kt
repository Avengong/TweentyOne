package com.example.tweentyone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Choreographer
import android.view.View
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {

    private var choreographer: Choreographer? = null
    private var callbackQueueLock: Any? = null
    private var addInputQueue: Method? = null
    private var callbackQueues: Array<Any>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun reflectTest(view: View) {
        var input = 0

        choreographer = Choreographer.getInstance()
        callbackQueueLock = ReflectUtils.reflectObject(choreographer, "mLock", Any())

        callbackQueues = ReflectUtils.reflectObject(choreographer, "mCallbackQueues", null)
        addInputQueue = ReflectUtils.reflectMethod(
            callbackQueues?.get(input),
            "addCallbackLocked",
            Long::class.javaPrimitiveType,
            Any::class.java,
            Any::class.java
        )

        addInputQueue?.invoke(
            callbackQueues!![input], -1,
            Runnable {
                //经测试，Android10是可以反射的。
                Log.d("aaa", "input run !")
                //Android11 ：
//                mlock反射异常。 为啥mCallbackQueues却成功了呢？
                //因为 并不是所有的api都被限制。是大部分隐藏的变量会被限制。
            },
            null
        )
        //这个本质上是调用的java的hashmap
//        val hashm=HashMap<String,String>(3)

//       val kMap= hashMapOf<String,String>()

//        view.requestLayout()
//        val day19 = Day19()
//        day19.test()
    }


}