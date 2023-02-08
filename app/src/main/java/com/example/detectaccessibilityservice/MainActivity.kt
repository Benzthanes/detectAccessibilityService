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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.setHideOverlayWindows(true)
        }

        val text = findViewById<TextView>(R.id.text)
//        text.filterTouchesWhenObscured = true
        text.setOnClickListener {
            Log.e("clicktext","clicktext")
        }

        val view = findViewById<ConstraintLayout>(R.id.rootView)
        view.filterTouchesWhenObscured = true
        view.setOnClickListener {
            Log.e("clickroot","clickroot")
        }
        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val installedServices = accessibilityManager.installedAccessibilityServiceList
        val enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK
        )


        // check all app in device
        val pkg = packageManager.getInstalledPackages(0)
        pkg.forEach {
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
        }

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
        enabledServices.forEach { enable ->
            val packageName = enable.resolveInfo.serviceInfo.packageName
            Log.e("-> test APP IS ENABLE ACCESSIBILITY", "////")
            Log.e("test packageName enable", packageName)
            Log.e("test ---------------", "-----------------")
            verifyInstallerId(
                listOf(
                    InstallerIDtest.GOOGLE_PLAY,
                    InstallerIDtest.GALAXY_APPS
                ),
                packageName,
                "have and enable accessibility"
            )
        }
        val isOverlay = getAppsWhichHaveOverlaySettingEnabled()
        Log.e("-> test APP IS isOverlay", isOverlay.toString())

//        piracyChecker {
//            enableInstallerId(
//                InstallerID.GOOGLE_PLAY,
//                InstallerID.AMAZON_APP_STORE,
//                InstallerID.GALAXY_APPS
//            )
//            addAppToCheck(app)
//        }.doNotAllow { piracyCheckerError, pirateApp ->
//            Log.e("",piracyCheckerError.name)
//        }.allow {
//
//        }.onError { }.start()
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

    fun getAppsWhichHaveOverlaySettingEnabled(): ArrayList<String>? {
        val apps: ArrayList<String> = ArrayList()
        val pm = packageManager
        val installedPackages: List<PackageInfo> =
            pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        for (packageInfo in installedPackages) {
//            Log.e("-> test APP IS grant SYSTEM_ALERT_WINDOW", packageInfo.packageName)
            val requestedPermissions: Array<String>? = packageInfo.requestedPermissions
            if (requestedPermissions != null && requestedPermissions.contains(SYSTEM_ALERT_WINDOW)) {
                checkPermissionGrant(packageInfo.packageName)
                val name =
                    pm.getApplicationLabel(packageInfo.applicationInfo).toString()
                apps.add(name + " (" + packageInfo.packageName + ")")
            }
        }
        return apps
    }

    fun checkPermissionGrant(packageName: String): Boolean {
        Log.e(
            "//////////",
            packageName + " is grant SYSTEM_ALERT_WINDOW = " +
                    (packageManager.checkPermission(
                        SYSTEM_ALERT_WINDOW,
                        packageName
                    ) == PackageManager.PERMISSION_GRANTED).toString()
        )
        return packageManager.checkPermission(
            SYSTEM_ALERT_WINDOW,
            packageName
        ) == PackageManager.PERMISSION_GRANTED
    }

//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        val isDetect = event.flags and MotionEvent.FLAG_WINDOW_IS_OBSCURED != 0
//        val isDetectPartial = event.flags and MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED != 0
//        Log.e("is detecth FLAG_WINDOW_IS_OBSCURED overlay", "isDetect = $isDetect")
//        Log.e("is detecth FLAG_WINDOW_IS_PARTIALLY_OBSCURED overlay", "isDetect = $isDetectPartial")
//        return super.dispatchTouchEvent(event)
//    }


}