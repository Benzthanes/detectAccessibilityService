package com.example.detectaccessibilityservice

import android.Manifest.permission.SYSTEM_ALERT_WINDOW
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    override fun onResume() {
        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK
        )
//        Log.e("-> test APP IS ENABLE ACCESSIBILITY", "////")
//        if (enabledServices.isEmpty()) {
//            Log.e("-> Not have APP IS ENABLE ACCESSIBILITY", "////")
//        }
//        enabledServices.forEach { enable ->
//            val packageName = enable.resolveInfo.serviceInfo.packageName
//            Log.e("test packageName enable", packageName)
//            Log.e("test ---------------", "-----------------")
//            verifyInstallerId(
//                listOf(
//                    InstallerIDtest.GOOGLE_PLAY,
//                    InstallerIDtest.GALAXY_APPS
//                ),
//                packageName,
//                "have and enable accessibility"
//            )
//        }

        val pkg = packageManager.getInstalledPackages(0)
        pkg.forEach {
            if(it.packageName.contains("scb")){
                tvResult?.text = tvResult?.text.toString() + "\n" + it.packageName + " | " +
                verifyInstallerIdReturnString(
                    listOf(
                        InstallerIDtest.GOOGLE_PLAY,
                        InstallerIDtest.GALAXY_APPS
                    ),
                    it.packageName,
                    "all package"
                ) + " | " +
                    verifyInstallerId(
                        listOf(
                            InstallerIDtest.GOOGLE_PLAY,
                            InstallerIDtest.GALAXY_APPS
                        ),
                        it.packageName,
                        "all package"
                    )
                Log.e(
                    "checker", it.packageName + " " +
                            verifyInstallerId(
                                listOf(
                                    InstallerIDtest.GOOGLE_PLAY,
                                    InstallerIDtest.GALAXY_APPS
                                ),
                                it.packageName,
                                "all package"
                            )
                )
            }
        }
        super.onResume()
    }

    var tvResult: TextView? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.setHideOverlayWindows(true)
        }
        tvResult = findViewById(R.id.text)

//        val text = findViewById<TextView>(R.id.text)
//        text.filterTouchesWhenObscured = true
//        text.setOnClickListener {
//            Log.e("clicktext", "clicktext")
//        }

//        val view = findViewById<ConstraintLayout>(R.id.rootView)
//        view.filterTouchesWhenObscured = true
//        view.setOnClickListener {
//            Log.e("clickroot", "clickroot")
//        }
        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val installedServices = accessibilityManager.installedAccessibilityServiceList
        val enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK
        )


        // check all app in device
//        val pkg = packageManager.getInstalledPackages(0)
//        pkg.forEach {
//            checkPermissionGrant(it.packageName)
//            Log.e(
//                "checker", it.packageName + " " +
//                        verifyInstallerId(
//                            listOf(
//                                InstallerIDtest.GOOGLE_PLAY,
//                                InstallerIDtest.GALAXY_APPS
//                            ),
//                            it.packageName,
//                            "all package"
//                        )
//            )
//        }

        // check app has request permission ACCESSIBILITY in device
//        installedServices.forEach { installed ->
//            val svcInfo = installed.resolveInfo.serviceInfo
//            Log.e("-> test APP HAVE ACCESSIBILITY", "////")
//            Log.e("test packageName", svcInfo.packageName)
//            val appLabel = packageManager.getApplicationLabel(
//                packageManager.getApplicationInfo(
//                    svcInfo.packageName,
//                    0
//                )
//            )
//            verifyInstallerId(
//                listOf(
//                    InstallerIDtest.GOOGLE_PLAY,
//                    InstallerIDtest.AMAZON_APP_STORE,
//                    InstallerIDtest.GALAXY_APPS
//                ),
//                svcInfo.packageName,
//                "have accessibility"
//            )
//            Log.e("test packageName appLabel", appLabel.toString())
//            Log.e("test ---------------", "-----------------")
//        }
//        Log.e("################## test END CHECK HAVE ##################", "")

        // check app has request and ENABLE permission ACCESSIBILITY in device
//        enabledServices.forEach { enable ->
//            val packageName = enable.resolveInfo.serviceInfo.packageName
//            Log.e("-> test APP IS ENABLE ACCESSIBILITY", "////")
//            Log.e("test packageName enable", packageName)
//            Log.e("test ---------------", "-----------------")
//            verifyInstallerId(
//                listOf(
//                    InstallerIDtest.GOOGLE_PLAY,
//                    InstallerIDtest.GALAXY_APPS
//                ),
//                packageName,
//                "have and enable accessibility"
//            )
//        }

    }

    fun Context.verifyInstallerId(
        installerID: List<InstallerIDtest>,
        packageName: String,
        tag: String,
    ): Boolean {
        val validInstallers = ArrayList<String>()
        val installer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            packageManager.getInstallSourceInfo(packageName).installingPackageName
        } else {
            packageManager.getInstallerPackageName(packageName)
        }
        for (id in installerID) {
            validInstallers.addAll(id.toIDs())
        }
        Log.e(
            "checkerPackageName from $tag",
            "PACKAGE NAME :$packageName \n" +
                    "INSTALLER NAME :" + "$installer \n" +
                    "IS PASS = " + (installer != null && validInstallers.contains(
                installer
            ))
        )
        return installer != null && validInstallers.contains(installer)
    }

    fun Context.verifyInstallerIdReturnString(
        installerID: List<InstallerIDtest>,
        packageName: String,
        tag: String,
    ): String? {
        val validInstallers = ArrayList<String>()
        val installer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            packageManager.getInstallSourceInfo(packageName).installingPackageName
        } else {
            packageManager.getInstallerPackageName(packageName)
        }
        for (id in installerID) {
            validInstallers.addAll(id.toIDs())
        }
        Log.e(
            "checkerPackageName from $tag",
            "PACKAGE NAME :$packageName \n" +
                    "INSTALLER NAME :" + "$installer \n" +
                    "IS PASS = " + (installer != null && validInstallers.contains(
                installer
            ))
        )
        return installer
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val flag_FLAG_WINDOW_IS_OBSCURED =
            (event.flags and MotionEvent.FLAG_WINDOW_IS_OBSCURED) == MotionEvent.FLAG_WINDOW_IS_OBSCURED
        val flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED =
            (event.flags and MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED) == MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED
        Log.e("FLAG_WINDOW_IS_OBSCURED", flag_FLAG_WINDOW_IS_OBSCURED.toString())
        Log.e(
            "FLAG_WINDOW_IS_PARTIALLY_OBSCURED",
            flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED.toString()
        )
        if (flag_FLAG_WINDOW_IS_OBSCURED || flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED) {
//            finishAffinity()
        }
        return super.dispatchTouchEvent(event)
    }

}