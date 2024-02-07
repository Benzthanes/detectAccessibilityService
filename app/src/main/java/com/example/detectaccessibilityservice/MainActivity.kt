package com.example.detectaccessibilityservice

//import com.google.android.gms.common.GoogleApiAvailability

import android.Manifest
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.content.pm.Signature
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.huawei.hms.api.HuaweiApiAvailability
import java.security.MessageDigest


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
        val phone = getMyPhoneNumber()
        Log.e("phone", phone.toString())
        tvResult?.text = phone.toString()
//        val packageName = "com.example.detectaccessibilityservice"
//        tvResult?.text = "Build.BOARD =" + Build.BOARD + "\n" +
//                "Build.BOOTLOADER =" + Build.BOOTLOADER + "\n" +
//                "Build.BRAND =" + Build.BRAND + "\n" +
//                "Build.DEVICE =" + Build.DEVICE + "\n" +
//                "Build.DISPLAY =" + Build.DISPLAY + "\n" +
//                "Build.FINGERPRINT =" + Build.FINGERPRINT + "\n" +
//                "Build.HARDWARE =" + Build.HARDWARE + "\n" +
//                "Build.HOST =" + Build.HOST + "\n" +
//                "Build.ID =" + Build.ID + "\n" +
//                "Build.MANUFACTURER =" + Build.MANUFACTURER + "\n" +
//                "Build.MODEL =" + Build.MODEL + "\n" +
//                "Build.PRODUCT =" + Build.PRODUCT + "\n" +
//                "Build.RADIO =" + Build.RADIO + "\n" +
//                "Build.SERIAL =" + Build.SERIAL
        tvResult?.text = tvResult?.text.toString() + "\n" + "-----------------------" + "\n"


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

//        val mGoogleApiClient = GoogleApiClient.Builder(this)
//            .addApi(Auth.CREDENTIALS_API)
//            .build()
//
//        val hintRequest = HintRequest.Builder()
//            .setPhoneNumberIdentifierSupported(true)
//            .build()
//
//        val intent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest)
//        try {
//            startIntentSenderForResult(intent.intentSender, 1008, null, 0, 0, 0, null)
//        } catch (e: SendIntentException) {
//            Log.e("", "Could not start hint picker Intent", e)
//        }
//
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect()
//        }

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

    fun getMyPhoneNumber(): String? {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_NUMBERS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // phone
            val subscriptionManager =
                getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            val subsInfoList = subscriptionManager.activeSubscriptionInfoList
            Log.d("Test", "Current list = $subsInfoList")
            for (subscriptionInfo in subsInfoList) {
                val number = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    subscriptionManager.getPhoneNumber(subscriptionInfo.subscriptionId)
                } else {
                    subscriptionInfo.number
                }

                // host + port
                Log.d("Test", " Number is  $number")
                var proxyAddress = "host:"+System.getProperty("http.proxyHost");
                proxyAddress += "port:" + System.getProperty("http.proxyPort");
                return number + "  "+ proxyAddress
            }
            return ""
        }
        return ""
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


    fun isHMSAvailable(context: Context): Boolean {
        val hms: HuaweiApiAvailability = HuaweiApiAvailability.getInstance()
        val isHMS: Int = hms.isHuaweiMobileServicesAvailable(context)
        return isHMS == com.huawei.hms.api.ConnectionResult.SUCCESS
    }

    fun isGMSAvailable(context: Context): Boolean {
        val gms = GoogleApiAvailability.getInstance()
        val isGMS = gms.isGooglePlayServicesAvailable(context)
        return isGMS == com.google.android.gms.common.ConnectionResult.SUCCESS
    }

//    private fun invokeSysIntegrity() {
//        val nonce = "abc"
//        val sysIntegrityRequest = SysIntegrityRequest()
//        sysIntegrityRequest.appId = APP_ID
//        sysIntegrityRequest.nonce = nonce
//        sysIntegrityRequest.alg = alg
//
//        SafetyDetect.getClient(this)
//            .sysIntegrity(sysIntegrityRequest)
//            .addOnSuccessListener { response -> // Indicates communication with the service was successful.
//                // Use response.getResult() to obtain the result data.
//                val jwsStr = response.result
//
//// Process the result data here.
//                val jwsSplit = jwsStr.split(".").toTypedArray()
//                val jwsPayloadStr = jwsSplit[1]
//                val payloadDetail = String(
//                    Base64.decode(
//                        jwsPayloadStr.toByteArray(StandardCharsets.UTF_8),
//                        Base64.URL_SAFE
//                    ), StandardCharsets.UTF_8
//                )
//                try {
//                    val jsonObject = JSONObject(payloadDetail)
//                    val basicIntegrity = jsonObject.getBoolean("basicIntegrity")
//                    fg_button_sys_integrity_go.setBackgroundResource(if (basicIntegrity) R.drawable.btn_round_green else R.drawable.btn_round_red)
//                    fg_button_sys_integrity_go.setText(R.string.rerun)
//                    val isBasicIntegrity = basicIntegrity.toString()
//                    val basicIntegrityResult = "Basic Integrity: $isBasicIntegrity"
//                    fg_payloadBasicIntegrity.text = basicIntegrityResult
//                    if (!basicIntegrity) {
//                        val advice = "Advice: " + jsonObject.getString("advice")
//                        fg_payloadAdvice.text = advice
//                    }
//                } catch (e: JSONException) {
//                    val errorMsg = e.message
//                    Log.e(TAG, errorMsg ?: "unknown error")
//                }
//
//
//            }
//            .addOnFailureListener { e -> // There was an error communicating with the service.
//                val errorMsg: String?
//                errorMsg = if (e is ApiException) {
//// An error with the HMS API contains some additional details.
//                    val apiException = e as ApiException
//                    SafetyDetectStatusCodes.getStatusCodeString(apiException.statusCode) +
//                            ": " + apiException.message
//// You can use the apiException.getStatusCode() method to obtain the status code.
//                } else {
//// An unknown type of error has occurred.
//                    e.message
//                }
////                Log.e(TAG, errorMsg)
////                Toast.makeText(activity?.applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
////                fg_button_sys_integrity_go.setBackgroundResource(R.drawable.btn_round_yellow)
////                fg_button_sys_integrity_go.setText(R.string.rerun)
//            }
//    }
}