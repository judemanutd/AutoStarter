package com.judemanutd.autostarter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import java.util.*

class AutoStartPermissionHelper private constructor() {

    /***
     * Xiaomi
     */
    private val BRAND_XIAOMI = "xiaomi"
    private val BRAND_XIAOMI_POCO = "poco"
    private val BRAND_XIAOMI_REDMI = "redmi"
    private val PACKAGE_XIAOMI_MAIN = "com.miui.securitycenter"
    private val PACKAGE_XIAOMI_COMPONENT =
        "com.miui.permcenter.autostart.AutoStartManagementActivity"

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
    private val PACKAGE_ASUS_COMPONENT_FALLBACK =
        "com.asus.mobilemanager.autostart.AutoStartActivity"

    /***
     * Honor
     */
    private val BRAND_HONOR = "honor"
    private val PACKAGE_HONOR_MAIN = "com.huawei.systemmanager"
    private val PACKAGE_HONOR_COMPONENT =
        "com.huawei.systemmanager.optimize.process.ProtectActivity"

    /***
     * Huawei
     */
    private val BRAND_HUAWEI = "huawei"
    private val PACKAGE_HUAWEI_MAIN = "com.huawei.systemmanager"
    private val PACKAGE_HUAWEI_COMPONENT =
        "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
    private val PACKAGE_HUAWEI_COMPONENT_FALLBACK =
        "com.huawei.systemmanager.optimize.process.ProtectActivity"

    /**
     * Oppo
     */
    private val BRAND_OPPO = "oppo"
    private val PACKAGE_OPPO_MAIN = "com.coloros.safecenter"
    private val PACKAGE_OPPO_FALLBACK = "com.oppo.safe"
    private val PACKAGE_OPPO_COMPONENT =
        "com.coloros.safecenter.permission.startup.StartupAppListActivity"
    private val PACKAGE_OPPO_COMPONENT_FALLBACK =
        "com.oppo.safe.permission.startup.StartupAppListActivity"
    private val PACKAGE_OPPO_COMPONENT_FALLBACK_A =
        "com.coloros.safecenter.startupapp.StartupAppListActivity"

    /**
     * Vivo
     */

    private val BRAND_VIVO = "vivo"
    private val PACKAGE_VIVO_MAIN = "com.iqoo.secure"
    private val PACKAGE_VIVO_FALLBACK = "com.vivo.permissionmanager"
    private val PACKAGE_VIVO_COMPONENT = "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
    private val PACKAGE_VIVO_COMPONENT_FALLBACK =
        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
    private val PACKAGE_VIVO_COMPONENT_FALLBACK_A =
        "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"

    /**
     * Nokia
     */

    private val BRAND_NOKIA = "nokia"
    private val PACKAGE_NOKIA_MAIN = "com.evenwell.powersaving.g3"
    private val PACKAGE_NOKIA_COMPONENT =
        "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity"

    /***
     * Samsung
     */
    private val BRAND_SAMSUNG = "samsung"
    private val PACKAGE_SAMSUNG_MAIN = "com.samsung.android.lool"
    private val PACKAGE_SAMSUNG_COMPONENT = "com.samsung.android.sm.ui.battery.BatteryActivity"
    private val PACKAGE_SAMSUNG_COMPONENT_2 =
        "com.samsung.android.sm.battery.ui.usage.CheckableAppListActivity"
    private val PACKAGE_SAMSUNG_COMPONENT_3 = "com.samsung.android.sm.battery.ui.BatteryActivity"

    /***
     * One plus
     */
    private val BRAND_ONE_PLUS = "oneplus"
    private val PACKAGE_ONE_PLUS_MAIN = "com.oneplus.security"
    private val PACKAGE_ONE_PLUS_COMPONENT =
        "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
    private val PACKAGE_ONE_PLUS_ACTION = "com.android.settings.action.BACKGROUND_OPTIMIZE"

    private val PACKAGES_TO_CHECK_FOR_PERMISSION = listOf(
        PACKAGE_ASUS_MAIN,
        PACKAGE_XIAOMI_MAIN,
        PACKAGE_LETV_MAIN,
        PACKAGE_HONOR_MAIN,
        PACKAGE_OPPO_MAIN,
        PACKAGE_OPPO_FALLBACK,
        PACKAGE_VIVO_MAIN,
        PACKAGE_VIVO_FALLBACK,
        PACKAGE_NOKIA_MAIN,
        PACKAGE_HUAWEI_MAIN,
        PACKAGE_SAMSUNG_MAIN,
        PACKAGE_ONE_PLUS_MAIN
    )

    /**
     * It will attempt to open the specific manufacturer settings screen with the autostart permission
     * If [open] is changed to false it will just check the screen existence
     *
     * @param context
     * @param open, if true it will attempt to open the activity, otherwise it will just check its existence
     * @param newTask, if true when the activity is attempted to be opened it will add FLAG_ACTIVITY_NEW_TASK to the intent
     * @return true if the activity was opened or is confirmed that it exists (depending on [open]]), false otherwise
     */
    fun getAutoStartPermission(
        context: Context,
        open: Boolean = true,
        newTask: Boolean = false
    ): Boolean {

        when (Build.BRAND.lowercase(Locale.ROOT)) {

            BRAND_ASUS -> return autoStartAsus(context, open, newTask)

            BRAND_XIAOMI, BRAND_XIAOMI_POCO, BRAND_XIAOMI_REDMI -> return autoStartXiaomi(
                context,
                open,
                newTask
            )

            BRAND_LETV -> return autoStartLetv(context, open, newTask)

            BRAND_HONOR -> return autoStartHonor(context, open, newTask)

            BRAND_HUAWEI -> return autoStartHuawei(context, open, newTask)

            BRAND_OPPO -> return autoStartOppo(context, open, newTask)

            BRAND_VIVO -> return autoStartVivo(context, open, newTask)

            BRAND_NOKIA -> return autoStartNokia(context, open, newTask)

            BRAND_SAMSUNG -> return autoStartSamsung(context, open, newTask)

            BRAND_ONE_PLUS -> return autoStartOnePlus(context, open, newTask)

            else -> {
                return false
            }
        }
    }

    /**
     * Checks whether the autostart permission is present in the manufacturer and supported by the library
     *
     * @param context
     * @param onlyIfSupported if true, the method will only return true if the screen is supported by the library.
     *          If false, the method will return true as long as the permission exist even if the screen is not supported
     *          by the library.
     * @return true if autostart permission is present in the manufacturer and supported by the library, false otherwise
     */
    fun isAutoStartPermissionAvailable(
        context: Context,
        onlyIfSupported: Boolean = false
    ): Boolean {
        val packages: List<ApplicationInfo>
        val pm = context.packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (PACKAGES_TO_CHECK_FOR_PERMISSION.contains(packageInfo.packageName)
                && (!onlyIfSupported || getAutoStartPermission(context, open = false))
            ) return true
        }
        return false
    }

    private fun autoStartXiaomi(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return autoStart(
            context,
            listOf(PACKAGE_XIAOMI_MAIN),
            listOf(getIntent(PACKAGE_XIAOMI_MAIN, PACKAGE_XIAOMI_COMPONENT, newTask)),
            open
        )
    }

    private fun autoStartAsus(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return autoStart(
            context,
            listOf(PACKAGE_ASUS_MAIN),
            listOf(
                getIntent(PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT, newTask),
                getIntent(PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT_FALLBACK, newTask)
            ),
            open
        )
    }

    private fun autoStartLetv(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return autoStart(
            context,
            listOf(PACKAGE_LETV_MAIN),
            listOf(getIntent(PACKAGE_LETV_MAIN, PACKAGE_LETV_COMPONENT, newTask)),
            open
        )
    }

    private fun autoStartHonor(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return autoStart(
            context,
            listOf(PACKAGE_HONOR_MAIN),
            listOf(getIntent(PACKAGE_HONOR_MAIN, PACKAGE_HONOR_COMPONENT, newTask)),
            open
        )
    }

    private fun autoStartHuawei(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return autoStart(
            context,
            listOf(PACKAGE_HUAWEI_MAIN),
            listOf(
                getIntent(PACKAGE_HUAWEI_MAIN, PACKAGE_HUAWEI_COMPONENT, newTask),
                getIntent(PACKAGE_HUAWEI_MAIN, PACKAGE_HUAWEI_COMPONENT_FALLBACK, newTask)
            ),
            open
        )
    }

    private fun autoStartOppo(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return if (autoStart(
                context,
                listOf(PACKAGE_OPPO_MAIN, PACKAGE_OPPO_FALLBACK),
                listOf(
                    getIntent(PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT, newTask),
                    getIntent(PACKAGE_OPPO_FALLBACK, PACKAGE_OPPO_COMPONENT_FALLBACK, newTask),
                    getIntent(PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT_FALLBACK_A, newTask)
                ),
                open
            )
        ) true
        else launchOppoAppInfo(context, open, newTask)
    }

    private fun launchOppoAppInfo(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return try {
            val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            i.addCategory(Intent.CATEGORY_DEFAULT)
            i.data = Uri.parse("package:${context.packageName}")
            if (open) {
                context.startActivity(i)
                true
            } else {
                isActivityFound(context, i)
            }
        } catch (exx: Exception) {
            exx.printStackTrace()
            false
        }
    }

    private fun autoStartVivo(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return autoStart(
            context,
            listOf(PACKAGE_VIVO_MAIN, PACKAGE_VIVO_FALLBACK),
            listOf(
                getIntent(PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT, newTask),
                getIntent(PACKAGE_VIVO_FALLBACK, PACKAGE_VIVO_COMPONENT_FALLBACK, newTask),
                getIntent(PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT_FALLBACK_A, newTask)
            ),
            open
        )
    }

    private fun autoStartNokia(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return autoStart(
            context,
            listOf(PACKAGE_NOKIA_MAIN),
            listOf(getIntent(PACKAGE_NOKIA_MAIN, PACKAGE_NOKIA_COMPONENT, newTask)),
            open
        )
    }

    private fun autoStartSamsung(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return autoStart(
            context,
            listOf(PACKAGE_SAMSUNG_MAIN),
            listOf(
                getIntent(PACKAGE_SAMSUNG_MAIN, PACKAGE_SAMSUNG_COMPONENT, newTask),
                getIntent(PACKAGE_SAMSUNG_MAIN, PACKAGE_SAMSUNG_COMPONENT_2, newTask),
                getIntent(PACKAGE_SAMSUNG_MAIN, PACKAGE_SAMSUNG_COMPONENT_3, newTask)
            ),
            open
        )
    }

    private fun autoStartOnePlus(context: Context, open: Boolean, newTask: Boolean): Boolean {
        return autoStart(
            context,
            listOf(PACKAGE_ONE_PLUS_MAIN),
            listOf(getIntent(PACKAGE_ONE_PLUS_MAIN, PACKAGE_ONE_PLUS_COMPONENT, newTask)),
            open
        ) || autoStartFromAction(
            context,
            listOf(getIntentFromAction(PACKAGE_ONE_PLUS_ACTION, newTask)),
            open
        )
    }

    @Throws(Exception::class)
    private fun startIntent(context: Context, intent: Intent) {
        try {
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

        private val myInstance by lazy { AutoStartPermissionHelper() }

        fun getInstance(): AutoStartPermissionHelper {
            return myInstance
        }
    }

    /**
     * Generates an intent with the passed package and component name
     * @param packageName
     * @param componentName
     * @param newTask
     *
     * @return the intent generated
     */
    private fun getIntent(packageName: String, componentName: String, newTask: Boolean): Intent {
        return Intent().apply {
            component = ComponentName(packageName, componentName)
            if (newTask) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    /**
     * Generates an intent with the passed action
     * @param intentAction
     * @param newTask
     *
     * @return the intent generated
     */
    private fun getIntentFromAction(intentAction: String, newTask: Boolean): Intent {
        return Intent().apply {
            action = intentAction
            if (newTask) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    /**
     * Will query the passed intent to check whether the Activity really exists
     *
     * @param context
     * @param intent, intent to open an activity
     *
     * @return true if activity is found, false otherwise
     */
    private fun isActivityFound(context: Context, intent: Intent): Boolean {
        return context.packageManager.queryIntentActivities(
            intent, PackageManager.MATCH_DEFAULT_ONLY
        ).isNotEmpty()
    }

    /**
     * Will query the passed list of intents to check whether any of the activities exist
     *
     * @param context
     * @param intents, list of intents to open an activity
     *
     * @return true if activity is found, false otherwise
     */
    private fun areActivitiesFound(context: Context, intents: List<Intent>): Boolean {
        return intents.any { isActivityFound(context, it) }
    }

    /**
     * Will attempt to open the AutoStart settings activity from the passed list of intents in order.
     * The first activity found will be opened.
     *
     * @param context
     * @param intents list of intents
     *
     * @return true if an activity was opened, false otherwise
     */
    private fun openAutoStartScreen(context: Context, intents: List<Intent>): Boolean {
        intents.forEach {
            if (isActivityFound(context, it)) {
                startIntent(context, it)
                return@openAutoStartScreen true
            }
        }
        return false
    }

    /**
     * Will trigger the common autostart permission logic. If [open] is true it will attempt to open the specific
     * manufacturer setting screen, otherwise it will just check for its existence
     *
     * @param context
     * @param packages, list of known packages of the corresponding manufacturer
     * @param intents, list of known intents that open the corresponding manufacturer settings screens
     * @param open, if true it will attempt to open the settings screen, otherwise it just check its existence
     * @return true if the screen was opened or exists, false if it doesn't exist or could not be opened
     */
    private fun autoStart(
        context: Context,
        packages: List<String>,
        intents: List<Intent>,
        open: Boolean
    ): Boolean {
        return if (packages.any { isPackageExists(context, it) }) {
            if (open) openAutoStartScreen(context, intents)
            else areActivitiesFound(context, intents)
        } else false
    }

    /**
     * Will trigger the common autostart permission logic. If [open] is true it will attempt to open the specific
     * manufacturer setting screen, otherwise it will just check for its existence
     *
     * @param context
     * @param intentActions, list of known intent actions that open the corresponding manufacturer settings screens
     * @param open, if true it will attempt to open the settings screen, otherwise it just check its existence
     * @return true if the screen was opened or exists, false if it doesn't exist or could not be opened
     */
    private fun autoStartFromAction(
        context: Context,
        intentActions: List<Intent>,
        open: Boolean
    ): Boolean {
        return if (open) openAutoStartScreen(context, intentActions)
        else areActivitiesFound(context, intentActions)
    }
}

