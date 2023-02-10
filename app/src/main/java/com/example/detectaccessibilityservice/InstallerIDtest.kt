package com.example.detectaccessibilityservice

import java.util.ArrayList

enum class InstallerIDtest(private val text: String) {
    GOOGLE_PLAY("com.android.vending|com.google.android.feedback"),
    AMAZON_APP_STORE("com.amazon.venezia"),
    GALAXY_APPS("com.sec.android.app.samsungapps"),
    HUAWEI_APP_GALLERY("com.huawei.appmarket"),
    VIVO_APP_STORE("com.vivo.appstore"),
    OPPO_APP_STORE("com.heytap.market"),
    XIAOMI_APP_STORE("com.xiaomi.mipicks");

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    override fun toString(): String {
        return text
    }
    
    fun toIDs(): List<String> = if (text.contains("|")) {
        val split = text.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        ArrayList(listOf(*split))
    } else {
        ArrayList(listOf(text))
    }
}
