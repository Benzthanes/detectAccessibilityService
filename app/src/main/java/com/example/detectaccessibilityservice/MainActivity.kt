package com.example.detectaccessibilityservice

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val whiteListStore = listOf(
        InstallerIDtest.GOOGLE_PLAY,
        InstallerIDtest.GALAXY_APPS,
        InstallerIDtest.HUAWEI_APP_GALLERY,
        InstallerIDtest.VIVO_APP_STORE,
        InstallerIDtest.OPPO_APP_STORE,
        InstallerIDtest.XIAOMI_APP_STORE
    )

    override fun onResume() {
        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val installedServices = accessibilityManager.installedAccessibilityServiceList
        val enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK
        )
        tvResult?.text = ""
        Log.e("-> test APP IS ENABLE ACCESSIBILITY", "////")
        if (enabledServices.isEmpty()) {
            tvResult?.text = "NOT APP  ENABLE ACCESSIBILITY"
            Log.e("-> Not have APP IS ENABLE ACCESSIBILITY", "////")
        } else {
            tvResult?.text = "APP  ENABLE ACCESSIBILITY"
            installedServices.forEach {
                val packageName = it.resolveInfo.serviceInfo.packageName
                val appLabel = getApplicationLabelName(packageName)
                Log.e("test packageName enable", packageName)
                Log.e("test ---------------", "-----------------")
                tvResult?.text =
                    tvResult?.text.toString() + "\n" +
                            "PACKAGE NAME =" + packageName + "\n" +
                            "APP NAME =" + appLabel + "\n" +
                            "INSTALLER ID = " + verifyInstallerIdReturnString(
                        whiteListStore,
                        packageName,
                        "have and enable accessibility"
                    ) + "\n" +
                            "INSTALLER VALIDATE ID= " + verifyInstallerId(
                        whiteListStore,
                        packageName,
                        "have and enable accessibility"
                    ) + "\n" +
                            "IS SYSTEM APP = " + checkPreInstalledApp(it.resolveInfo.serviceInfo.applicationInfo) +
                            "\n"
            }
            tvResult?.text = tvResult?.text.toString() + "\n" + "-----------------------" + "\n"
        }

        val pkg = packageManager.getInstalledPackages(0)
        pkg.forEach {
//            if (it.packageName.contains("scb")) {
            val appLabel = getApplicationLabelName(it.packageName)
            tvResult?.text =
                tvResult?.text.toString() + "\n" +
                        "PACKAGE NAME =" + it.packageName + "\n" +
                        "APP NAME =" + appLabel + "\n" +
                        "INSTALLER ID = " + verifyInstallerIdReturnString(
                    whiteListStore,
                    packageName,
                    "have and enable accessibility"
                ) + "\n" +
                        "INSTALLER VALIDATE ID= " + verifyInstallerId(
                    whiteListStore,
                    packageName,
                    "have and enable accessibility"
                ) + "\n" +
                        "IS SYSTEM APP = " + checkPreInstalledApp(it.applicationInfo) +
                        "\n"
            Log.e(
                "checker", it.packageName + " " +
                        verifyInstallerId(
                            whiteListStore,
                            it.packageName,
                            "all package"
                        )
            )
//            }
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
        val installer = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                packageManager.getInstallSourceInfo(packageName).installingPackageName
            } else {
                packageManager.getInstallerPackageName(packageName)
            }
        } catch (e: Exception) {
            null
        }
        for (id in installerID) {
            validInstallers.addAll(id.toIDs())
        }
        Log.e(
            "checkerPackageName from $tag",
            "PACKAGE NAME :$packageName \n" +
                    "INSTALLER NAME :" + "$installer \n" +
                    "IS PASS = " + (installer != null && validInstallers.contains(installer))
        )
        return installer != null && validInstallers.contains(installer)
    }

    fun Context.verifyInstallerIdReturnString(
        installerID: List<InstallerIDtest>,
        packageName: String,
        tag: String,
    ): String? {
        val validInstallers = ArrayList<String>()
        val installer = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                packageManager.getInstallSourceInfo(packageName).installingPackageName
            } else {
                packageManager.getInstallerPackageName(packageName)
            }
        } catch (e: Exception) {
            null
        }
        for (id in installerID) {
            validInstallers.addAll(id.toIDs())
        }
//        Log.e(
//            "checkerPackageName from $tag",
//            "PACKAGE NAME :$packageName \n" +
//                    "INSTALLER NAME :" + "$installer \n" +
//                    "IS PASS = " + (installer != null && validInstallers.contains(
//                installer
//            ))
//        )
        return installer
    }

    fun checkPreInstalledApp(appInfo: ApplicationInfo): Boolean {
        if (appInfo.flags and ApplicationInfo.FLAG_SYSTEM == ApplicationInfo.FLAG_SYSTEM) {
            return true
        }
        return false
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
        if (flag_FLAG_WINDOW_IS_OBSCURED) {
            tvResult?.text = tvResult?.text.toString() + "FLAG_WINDOW_IS_OBSCURED\n"
        }
        if (flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED) {
            tvResult?.text = tvResult?.text.toString() + "FLAG_WINDOW_IS_PARTIALLY_OBSCURED\n"
        }
        return super.dispatchTouchEvent(event)
    }

    // Ref : https://github.com/javiersantos/PiracyChecker
    fun Context.verifyInstallerId(
        packageName: String
    ): Boolean {
        val installerID = whiteListStore
        val validInstallers = ArrayList<String>()
        val installer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            packageManager.getInstallSourceInfo(packageName).installingPackageName
        } else {
            packageManager.getInstallerPackageName(packageName)
        }
        for (id in installerID) {
            validInstallers.addAll(id.toIDs())
        }
        val isVerifyPass = installer != null && validInstallers.contains(installer)

        return isVerifyPass
    }

    fun Context.getApplicationLabelName(packageName: String): String {
        return try {
            packageManager.getApplicationLabel(
                packageManager.getApplicationInfoCompat(
                    packageName,
                    0
                )
            ).toString()
        } catch (e: Exception) {
            "null"
        }
    }

    fun PackageManager.getApplicationInfoCompat(
        packageName: String,
        flags: Int = 0
    ): ApplicationInfo =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(flags.toLong()))
        } else {
            @Suppress("DEPRECATION") getApplicationInfo(packageName, flags)
        }

}