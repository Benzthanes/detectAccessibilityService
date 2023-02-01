package com.example.detectaccessibilityservice

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity

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


        // check all app in device
        val pkg = packageManager.getInstalledPackages(0)
        pkg.forEach {
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

        // check app has request permission ACCESSIBILITY in device
        installedServices.forEach { installed ->
            val svcInfo = installed.resolveInfo.serviceInfo
            Log.e("-> test APP HAVE ACCESSIBILITY", "////")
            Log.e("test packageName", svcInfo.packageName)
            val appLabel = packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(
                    svcInfo.packageName,
                    0
                )
            )
            verifyInstallerId(
                listOf(
                    InstallerIDtest.GOOGLE_PLAY,
                    InstallerIDtest.AMAZON_APP_STORE,
                    InstallerIDtest.GALAXY_APPS
                ),
                svcInfo.packageName,
                "have accessibility"
            )
            Log.e("test packageName appLabel", appLabel.toString())
            Log.e("test ---------------", "-----------------")
        }
        Log.e("################## test END CHECK HAVE ##################", "")

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
        tag:String,
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
                    "INSTALLER NAME :"+"$installer \n" +
                    "IS PASS = " + (installer != null && validInstallers.contains(
                installer
            ))
        )
        return installer != null && validInstallers.contains(installer)
    }
}