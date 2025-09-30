package com.example.detectaccessibilityservice

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.content.pm.Signature
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.BulletSpan
import android.text.style.ImageSpan
import android.text.style.LeadingMarginSpan
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    fun Context.dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        ).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun formatBulletedListWithGap(context: Context, stringList: List<String>, descriptionText: String, textView: TextView) {

        val gapDp = 8f
        val gapPx = context.dpToPx(gapDp)
        val bulletRadius = 10

        // 1. คำนวณระยะเยื้องทั้งหมดของ Bullet (Base Indent)
        val baseIndentPx = (2 * bulletRadius) + gapPx

        // 2. กำหนดระยะเยื้องเพิ่มเติม (Additional Tab/Indent)
        // ใช้ค่า 16dp เป็นตัวอย่าง
        val extraIndentDp = 16f
        val extraIndentPx = context.dpToPx(extraIndentDp)

        // 3. ระยะเยื้องทั้งหมดสำหรับ Description คือ Base Indent + Extra Indent
        val totalDescriptionIndentPx = baseIndentPx + extraIndentPx

        val ssb = SpannableStringBuilder()

        for (i in stringList.indices) {
            val item = stringList[i]

            val startBullet = ssb.length
            ssb.append(item).append("\n")

            val endBullet = ssb.length - 1

            // ใช้งาน BulletSpan สำหรับรายการ
            ssb.setSpan(
                BulletSpan(gapPx , Color.GREEN, bulletRadius),
                startBullet,
                endBullet,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // ถ้าเป็นรายการสุดท้าย ให้เพิ่ม Description
            if (i == stringList.size - 1) {

                // ไม่ต้องเพิ่มบรรทัดว่างเพิ่มเติม ถ้าต้องการให้ติดกัน

                // เพิ่มข้อความคำอธิบาย
                val startDesc = ssb.length
                ssb.append(descriptionText)
                val endDesc = ssb.length

                // กำหนด LeadingMarginSpan ด้วยระยะเยื้องใหม่ที่เพิ่ม Extra Indent เข้าไป
                ssb.setSpan(
                    // ใช้ totalDescriptionIndentPx สำหรับทั้งบรรทัดแรกและบรรทัดที่เหลือ
                    LeadingMarginSpan.Standard(totalDescriptionIndentPx, totalDescriptionIndentPx),
                    startDesc,
                    endDesc,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        textView.text = ssb
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.setHideOverlayWindows(true)
        }
        tvResult = findViewById(R.id.text)
//        val message = "กด # เพื่อเพิ่ม favorite ในหน้าหลัก ในหน้าหลักในหน้าหลักในหน้าหลักในหน้าหลัก"
//        val spannable = SpannableString(message)
//
//        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_easy_x_favorite_empty)
//        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
//
//        val imageSpan = drawable?.let { ImageSpan(it, ImageSpan.ALIGN_BASELINE) }
//
//        val index = message.indexOf("#")
//
//        if (imageSpan != null) {
//            spannable.setSpan(imageSpan, index, index+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//        }
//        tvResult?.text = spannable

        // ข้อมูล
        val englishItems = listOf(
            "Savings",
            "Personal consumption",
            "Salary",
            "Loan repayment",
            "Investment",
            "Business",
            "Others"
        )

        val englishDescription = "To receive income from freelance work, online sales, and digital platforms, and to manage financial transactions related to e-commerce."


// เรียกใช้ฟังก์ชันสำหรับแต่ละคอลัมน์
        formatBulletedListWithGap(this, englishItems, englishDescription, tvResult!!)

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
//                    tvResult?.text = tvResult?.text.toString() + "FLAG_WINDOW_IS_OBSCURED\n"
                }
                if (flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED) {
//                    tvResult?.text =
//                        tvResult?.text.toString() + "FLAG_WINDOW_IS_PARTIALLY_OBSCURED\n"
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