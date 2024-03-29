package com.example.detectaccessibilityservice

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.content.pm.Signature
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

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
//        val enabledService2 = getEnabledAccessibilityServiceList()
//        tvResult?.text = ""
//        if (enabledService2.isEmpty()) {
//            tvResult?.text = "NOT APP  ENABLE ACCESSIBILITY"
//        } else {
//            tvResult?.text = "APP  ENABLE ACCESSIBILITY"
//            enabledService2.forEach {
//                val packageName = it.packageName
//                val installerPackage = getInstallerPackageName(packageName)
//                val appLabel = getApplicationLabelName(packageName)
//                val signature = getCertificateInstaller(packageName)
//                tvResult?.text =
//                    tvResult?.text.toString() + "\n" +
//                            "PACKAGE NAME =" + packageName + "\n" +
//                            "APP NAME =" + appLabel + "\n" +
//                            "INSTALLER ID = " + installerPackage + "\n" +
//                            "Signature =" + signature
//                tvResult?.text = tvResult?.text.toString() + "\n" + "-----------------------" + "\n"
//            }
//
//        }

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
//        tvResult?.setOnClickListener {
//            finishAffinity()
//            startActivity(Intent("android.settings.CAST_SETTINGS"))
//            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
//        }

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
                packageManager.getInstallSourceInfo(packageName).initiatingPackageName
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
        try {
            if (event.action == MotionEvent.ACTION_DOWN) {
                Log.e("event", event.action.toString())
                val flag_FLAG_WINDOW_IS_OBSCURED =
                    (event.flags and MotionEvent.FLAG_WINDOW_IS_OBSCURED) == MotionEvent.FLAG_WINDOW_IS_OBSCURED
                val flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED =
                    (event.flags and MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED) == MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED
                Log.e("FLAG_WINDOW_IS_OBSCURED", flag_FLAG_WINDOW_IS_OBSCURED.toString())
                Log.e(
                    "FLAG_WINDOW_IS_PARTIALLY_OBSCURED",
                    flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED.toString()
                )
                if (flag_FLAG_WINDOW_IS_OBSCURED) {
                    tvResult?.text = tvResult?.text.toString() + "FLAG_WINDOW_IS_OBSCURED\n"
                }
                if (flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED) {
                    tvResult?.text =
                        tvResult?.text.toString() + "FLAG_WINDOW_IS_PARTIALLY_OBSCURED\n"
                }
            }
        } catch (e: Exception) {
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

    fun isAccessibilityServiceEnable(
        context: Context,
        accessibilityServiceInfo: AccessibilityServiceInfo
    ): ServiceInfo? {
        try {
            val isAccessibilitySettingEnable = Settings.Secure.getInt(
                context.applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
            if (isAccessibilitySettingEnable == 1) {
                val settingValue: String = Settings.Secure.getString(
                    context.applicationContext.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
                )
                val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')
                val splitter: TextUtils.SimpleStringSplitter = mStringColonSplitter
                splitter.setString(settingValue)
                while (splitter.hasNext()) {
                    val accessibilityService: String = splitter.next().split("/").first()
                    if (accessibilityService == accessibilityServiceInfo.resolveInfo.serviceInfo.packageName) {
                        return accessibilityServiceInfo.resolveInfo.serviceInfo
                    }
                }
            }
        } catch (e: Settings.SettingNotFoundException) {
            return null
        }
        return null
    }


    private fun getSigningSignaturesFromPackageName(packageName: String): Array<Signature> {
        val signatures = with(packageManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                try {
                    getPackageInfo(
                        packageName,
                        PackageManager.GET_SIGNING_CERTIFICATES
                    ).signingInfo.apkContentsSigners
                } catch (e: Exception) {
                    return arrayOf()
                }
            } else {
                try {
                    getPackageInfo(
                        packageName,
                        PackageManager.GET_SIGNATURES
                    ).signatures
                } catch (e: Exception) {
                    return arrayOf()
                }
            }
        }
        return signatures
    }

    fun sha256String(source: ByteArray?): String {
        if (source == null || source.isEmpty()) {
            return ""
        }
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(source)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }


    fun getEnabledAccessibilityServiceList(): List<ServiceInfo> {
        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        return accessibilityManager
            .installedAccessibilityServiceList
            .mapNotNull {
                getValidSettingEnabledAccessibilityServiceInfo(it)
            }
    }

    @VisibleForTesting
    fun getValidSettingEnabledAccessibilityServiceInfo(
        service: AccessibilityServiceInfo
    ): ServiceInfo? {
        val settingValue = getServiceSettingString()
        val serviceInfo = service.resolveInfo.serviceInfo

        return settingValue
            .split(":")
            .mapNotNull { it.split("/").firstOrNull() }
            .firstOrNull { it == serviceInfo.packageName }
            ?.let { serviceInfo }
    }

    fun getServiceSettingString(): String {
        return try {
            if (isAccessibilitySettingEnabled()) {
                Settings.Secure.getString(
                    contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
                )
            } else {
                ""
            }
        } catch (e: Settings.SettingNotFoundException) {
            return ""
        }
    }

    fun isAccessibilitySettingEnabled(): Boolean {
        return try {
            Settings.Secure.getInt(
                contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            ) == 1
        } catch (e: Settings.SettingNotFoundException) {
            false
        }
    }

    @Suppress("NewApi", "DEPRECATION")
    fun getInstallerPackageName(packageName: String): String? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                packageManager.getInstallSourceInfo(packageName).installingPackageName
            } else {
                packageManager.getInstallerPackageName(packageName)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getApplicationLabelName(packageName: String): String {
        return try {
            val appInfo = getApplicationInfoCompat(packageName)
            packageManager.getApplicationLabel(appInfo).toString()
        } catch (e: Exception) {
            "null"
        }
    }

    @Suppress("NewApi", "DEPRECATION")
    private fun getApplicationInfoCompat(packageName: String): ApplicationInfo =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(0)
            )
        } else {
            packageManager.getApplicationInfo(packageName, 0)
        }

    fun getCertificateInstaller(packageName: String?): List<String> {
        packageName?.let {
            val signature = getSigningSignaturesFromPackageName(packageName)
            return signature.map { sha256String(it.toByteArray()) }
        } ?: return listOf()
    }
}