package com.example.detectaccessibilityservice

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val installedServices = accessibilityManager.installedAccessibilityServiceList
        val enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK
        )
        installedServices.forEach { installed ->
            val svcInfo = installed.resolveInfo.serviceInfo
            Log.e("-> test APP HAVE ACCESSIBILITY" ,"////")
            Log.e("test packageName" ,svcInfo.packageName)
            val appLabel = packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(
                    svcInfo.packageName,
                    0
                )
            )
            Log.e("test packageName appLabel" ,appLabel.toString())
            Log.e("test ---------------" ,"-----------------")
        }
        Log.e("################## test END CHECK HAVE ##################" ,"")
        enabledServices.forEach { enable ->
            Log.e("-> test APP IS ENABLE ACCESSIBILITY" ,"////")
            Log.e( "test packageName enable" ,enable.resolveInfo.serviceInfo.packageName)
            Log.e("test ---------------" ,"-----------------")
        }
    }
}