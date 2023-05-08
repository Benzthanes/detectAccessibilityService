package com.example.detectaccessibilityservice

enum class InstallerID(private val text: String, private val cer: List<String>) {
    GOOGLE_PLAY(
        "com.android.vending",
        listOf(
            "f0fd6c5b410f25cb25c3b53346c8972fae30f8ee7411df910480ad6b2d60db83",
            "7ce83c1b71f3d572fed04c8d40c5cb10ff75e6d87d9df6fbd53f0468c2905053"
        )
    ),
    GOOGLE_PLAY_2(
        "com.google.android.feedback", listOf(
            "f0fd6c5b410f25cb25c3b53346c8972fae30f8ee7411df910480ad6b2d60db83",
            "7ce83c1b71f3d572fed04c8d40c5cb10ff75e6d87d9df6fbd53f0468c2905053"
        )
    ),
    GALAXY_APPS(
        "com.sec.android.app.samsungapps",
        listOf("fba3af4e7757d9016e953fb3ee4671ca2bd9af725f9a53d52ed4a38eaaa08901")
    ),
    HUAWEI_APP_GALLERY(
        "com.huawei.appmarket",
        listOf("3baf59a2e5331c30675fab35ff5fff0d116142d3d4664f1c3cb804068b40614f")
    ),
    VIVO_APP_STORE(
        "com.vivo.appstore",
        listOf("bcc35d4d3606f154f0402ab7634e8490c0b244c2675c3c6238986987024f0c02")
    ),
    OPPO_APP_STORE(
        "com.heytap.market",
        listOf("289cc3429f496460b66ac2a876c040091aaef55b5eb942c2ce697ef3b1201459")
    ),
    XIAOMI_APP_STORE(
        "com.xiaomi.mipicks",
        listOf("c9009d01ebf9f5d0302bc71b2fe9aa9a47a432bba17308a3111b75d7b2149025")
    );

    fun toModel(): InstallerModel = InstallerModel(text, cer)

    companion object {
        fun toMap(): HashMap<String, List<String>> {
            val map = hashMapOf<String, List<String>>()
            InstallerID.values().toList().forEach {
                map[it.text] = it.cer
            }
            return map
        }
    }

    fun toIDs(): List<String> = listOf(text)

    data class InstallerModel(
        val installerId: String,
        val certificateInstallerId: List<String>
    )
}