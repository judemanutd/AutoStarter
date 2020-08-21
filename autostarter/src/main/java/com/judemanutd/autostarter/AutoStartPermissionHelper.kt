package com.judemanutd.autostarter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.os.Build
import android.provider.Settings
import java.util.*

class AutoStartPermissionHelper private constructor() {

    /***
     * Xiaomi
     */
    private val BRAND_XIAOMI = "xiaomi"
    private val BRAND_XIAOMI_REDMI = "redmi"
    private val PACKAGE_XIAOMI_MAIN = "com.miui.securitycenter"
    private val PACKAGE_XIAOMI_COMPONENT = "com.miui.permcenter.autostart.AutoStartManagementActivity"

    /***
     * Letv
     */
    private val BRAND_LETV = "letv"
    private val PACKAGE_LETV_MAIN = "com.letv.android.letvsafe"
    private val PACKAGE_LETV_COMPONENT = "com.letv.android.letvsafe.AutobootManageActivity"

    /***
     * ASUS ROG
     */
    private val BRAND_ASUS = "asus"
    private val PACKAGE_ASUS_MAIN = "com.asus.mobilemanager"
    private val PACKAGE_ASUS_COMPONENT = "com.asus.mobilemanager.powersaver.PowerSaverSettings"
    private val PACKAGE_ASUS_COMPONENT_FALLBACK = "com.asus.mobilemanager.autostart.AutoStartActivity"

    /***
     * Honor
     */
    private val BRAND_HONOR = "honor"
    private val PACKAGE_HONOR_MAIN = "com.huawei.systemmanager"
    private val PACKAGE_HONOR_COMPONENT = "com.huawei.systemmanager.optimize.process.ProtectActivity"

    /***
     * Huawei
     */
    private val BRAND_HUAWEI = "huawei"
    private val PACKAGE_HUAWEI_MAIN = "com.huawei.systemmanager"
    private val PACKAGE_HUAWEI_COMPONENT = "com.huawei.systemmanager.optimize.process.ProtectActivity"
    private val PACKAGE_HUAWEI_COMPONENT_FALLBACK = "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"

    /**
     * Oppo
     */
    private val BRAND_OPPO = "oppo"
    private val PACKAGE_OPPO_MAIN = "com.coloros.safecenter"
    private val PACKAGE_OPPO_FALLBACK = "com.oppo.safe"
    private val PACKAGE_OPPO_COMPONENT = "com.coloros.safecenter.permission.startup.StartupAppListActivity"
    private val PACKAGE_OPPO_COMPONENT_FALLBACK = "com.oppo.safe.permission.startup.StartupAppListActivity"
    private val PACKAGE_OPPO_COMPONENT_FALLBACK_A = "com.coloros.safecenter.startupapp.StartupAppListActivity"

    /**
     * Vivo
     */

    private val BRAND_VIVO = "vivo"
    private val PACKAGE_VIVO_MAIN = "com.iqoo.secure"
    private val PACKAGE_VIVO_FALLBACK = "com.vivo.permissionmanager"
    private val PACKAGE_VIVO_COMPONENT = "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
    private val PACKAGE_VIVO_COMPONENT_FALLBACK = "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
    private val PACKAGE_VIVO_COMPONENT_FALLBACK_A = "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"

    /**
     * Nokia
     */

    private val BRAND_NOKIA = "nokia"
    private val PACKAGE_NOKIA_MAIN = "com.evenwell.powersaving.g3"
    private val PACKAGE_NOKIA_COMPONENT = "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity"

    /***
     * Samsung
     */
    private val BRAND_SAMSUNG = "samsung"
    private val PACKAGE_SAMSUNG_MAIN = "com.samsung.android.lool"
    private val PACKAGE_SAMSUNG_COMPONENT = "com.samsung.android.sm.ui.battery.BatteryActivity"

    /***
     * One plus
     */
    private val BRAND_ONE_PLUS = "oneplus"
    private val PACKAGE_ONE_PLUS_MAIN = "com.oneplus.security"
    private val PACKAGE_ONE_PLUS_COMPONENT = "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"

    private val PACKAGES_TO_CHECK_FOR_PERMISSION = listOf(PACKAGE_ASUS_MAIN, PACKAGE_XIAOMI_MAIN, PACKAGE_LETV_MAIN, PACKAGE_HONOR_MAIN, PACKAGE_OPPO_MAIN,
            PACKAGE_OPPO_FALLBACK, PACKAGE_VIVO_MAIN, PACKAGE_VIVO_FALLBACK, PACKAGE_NOKIA_MAIN, PACKAGE_HUAWEI_MAIN, PACKAGE_SAMSUNG_MAIN, PACKAGE_ONE_PLUS_MAIN)

    fun getAutoStartPermission(context: Context): Boolean {
        when (Build.BRAND.toLowerCase(Locale.getDefault())) {

            BRAND_ASUS -> return autoStartAsus(context)

            BRAND_XIAOMI, BRAND_XIAOMI_REDMI -> return autoStartXiaomi(context)

            BRAND_LETV -> return autoStartLetv(context)

            BRAND_HONOR -> return autoStartHonor(context)

            BRAND_HUAWEI -> return autoStartHuawei(context)

            BRAND_OPPO -> return autoStartOppo(context)

            BRAND_VIVO -> return autoStartVivo(context)

            BRAND_NOKIA -> return autoStartNokia(context)

            BRAND_SAMSUNG -> return autoStartSamsung(context)

            BRAND_ONE_PLUS -> return autoStartOnePlus(context)

            else -> {
                return false
            }
        }

    }

    fun isAutoStartPermissionAvailable(context: Context): Boolean {

        val packages: List<ApplicationInfo>
        val pm = context.packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (PACKAGES_TO_CHECK_FOR_PERMISSION.contains(packageInfo.packageName)) {
                return true
            }
        }
        return false
    }

    private fun autoStartXiaomi(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_XIAOMI_MAIN)) {
            try {
                startIntent(context, PACKAGE_XIAOMI_MAIN, PACKAGE_XIAOMI_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        } else {
            return false
        }

        return true
    }

    private fun autoStartAsus(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_ASUS_MAIN)) {
            try {
                startIntent(context, PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    startIntent(context, PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT_FALLBACK)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    return false
                }
            }
        } else {
            return false
        }

        return true
    }

    private fun autoStartLetv(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_LETV_MAIN)) {
            try {
                startIntent(context, PACKAGE_LETV_MAIN, PACKAGE_LETV_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        } else {
            return false
        }

        return true
    }

    private fun autoStartHonor(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_HONOR_MAIN)) {
            try {
                startIntent(context, PACKAGE_HONOR_MAIN, PACKAGE_HONOR_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        } else {
            return false
        }

        return true
    }

    private fun autoStartHuawei(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_HUAWEI_MAIN)) {
            try {
                startIntent(context, PACKAGE_HUAWEI_MAIN, PACKAGE_HUAWEI_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    startIntent(context, PACKAGE_HUAWEI_MAIN, PACKAGE_HUAWEI_COMPONENT_FALLBACK)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    return false
                }
            }
        } else {
            return false
        }

        return true
    }

    private fun autoStartOppo(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_OPPO_MAIN) || isPackageExists(context, PACKAGE_OPPO_FALLBACK)) {
            try {
                startIntent(context, PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    startIntent(context, PACKAGE_OPPO_FALLBACK, PACKAGE_OPPO_COMPONENT_FALLBACK)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    try {
                        startIntent(context, PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT_FALLBACK_A)
                    } catch (exx: Exception) {
                        exx.printStackTrace()
                        return launchOppoAppInfo(context)
                    }
                }
            }
        } else {
            return launchOppoAppInfo(context)
        }
        return true
    }

    private fun launchOppoAppInfo(context: Context): Boolean {
        return try {
            val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            i.addCategory(Intent.CATEGORY_DEFAULT)
            i.data = Uri.parse("package:${context.packageName}")
            context.startActivity(i)
            true
        } catch (exx: Exception) {
            exx.printStackTrace()
            false
        }
    }

    private fun autoStartVivo(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_VIVO_MAIN) || isPackageExists(context, PACKAGE_VIVO_FALLBACK)) {
            try {
                startIntent(context, PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    startIntent(context, PACKAGE_VIVO_FALLBACK, PACKAGE_VIVO_COMPONENT_FALLBACK)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    try {
                        startIntent(context, PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT_FALLBACK_A)
                    } catch (exx: Exception) {
                        exx.printStackTrace()
                        return false
                    }
                }
            }
        } else {
            return false
        }

        return true
    }

    private fun autoStartNokia(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_NOKIA_MAIN)) {
            try {
                startIntent(context, PACKAGE_NOKIA_MAIN, PACKAGE_NOKIA_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        } else {
            return false
        }

        return true
    }

    private fun autoStartSamsung(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_SAMSUNG_MAIN)) {
            try {
                startIntent(context, PACKAGE_SAMSUNG_MAIN, PACKAGE_SAMSUNG_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        } else {
            return false
        }

        return true
    }

    private fun autoStartOnePlus(context: Context): Boolean {
        if (isPackageExists(context, PACKAGE_ONE_PLUS_MAIN)) {
            try {
                startIntent(context, PACKAGE_ONE_PLUS_MAIN, PACKAGE_ONE_PLUS_COMPONENT)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        } else {
            return false
        }

        return true
    }

    @Throws(Exception::class)
    private fun startIntent(context: Context, packageName: String, componentName: String) {
        try {
            val intent = Intent()
            intent.component = ComponentName(packageName, componentName)
            context.startActivity(intent)
        } catch (exception: Exception) {
            exception.printStackTrace()
            throw exception
        }
    }

    private fun isPackageExists(context: Context, targetPackage: String): Boolean {
        val packages: List<ApplicationInfo>
        val pm = context.packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == targetPackage) {
                return true
            }
        }
        return false
    }

    companion object {
        @JvmStatic
        fun getInstance(): AutoStartPermissionHelper {
            return AutoStartPermissionHelper()
        }

    }
}