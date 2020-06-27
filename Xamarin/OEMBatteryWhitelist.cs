using System;
using System.Collections.Generic;
using System.Globalization;

using Android.Content;
using Android.Content.PM;
using Android.OS;

namespace CGSJDSportsNotification {
    class OEMBatteryWhitelist {
        Exception Exception;
        Context Context { get; set; }

        readonly string CURRENT_BRAND = Build.Brand.ToLower(CultureInfo.CurrentCulture);

        /* XIAOMI */
        const string BRAND_XIAOMI = "xiaomi";
        const string BRAND_XIAOMI_REDMI = "redmi";
        const string PACKAGE_XIAOMI_MAIN = "com.miui.securitycenter";
        const string PACKAGE_XIAOMI_COMPONENT = "com.miui.permcenter.autostart.AutoStartManagementActivity";

        /* LETV */
        const string BRAND_LETV = "letv";
        const string PACKAGE_LETV_MAIN = "com.letv.android.letvsafe";
        const string PACKAGE_LETV_COMPONENT = "com.letv.android.letvsafe.AutobootManageActivity";

        /* ASUS ROG */
        const string BRAND_ASUS = "asus";
        const string PACKAGE_ASUS_MAIN = "com.asus.mobilemanager";
        const string PACKAGE_ASUS_COMPONENT = "com.asus.mobilemanager.powersaver.PowerSaverSettings";
        const string PACKAGE_ASUS_COMPONENT_FALLBACK = "com.asus.mobilemanager.autostart.AutoStartActivity";

        /* HONOR */
        const string BRAND_HONOR = "honor";
        const string PACKAGE_HONOR_MAIN = "com.huawei.systemmanager";
        const string PACKAGE_HONOR_COMPONENT = "com.huawei.systemmanager.optimize.process.ProtectActivity";

        /* HUAWEI */
        const string BRAND_HUAWEI = "huawei";
        const string PACKAGE_HUAWEI_MAIN = "com.huawei.systemmanager";
        const string PACKAGE_HUAWEI_COMPONENT = "com.huawei.systemmanager.optimize.process.ProtectActivity";
        const string PACKAGE_HUAWEI_COMPONENT_FALLBACK = "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity";

        /* OPPO */
        const string BRAND_OPPO = "oppo";
        const string PACKAGE_OPPO_MAIN = "com.coloros.safecenter";
        const string PACKAGE_OPPO_FALLBACK = "com.oppo.safe";
        const string PACKAGE_OPPO_COMPONENT = "com.coloros.safecenter.permission.startup.StartupAppListActivity";
        const string PACKAGE_OPPO_COMPONENT_FALLBACK = "com.oppo.safe.permission.startup.StartupAppListActivity";
        const string PACKAGE_OPPO_COMPONENT_FALLBACK_A = "com.coloros.safecenter.startupapp.StartupAppListActivity";

        /* VIVO */

        const string BRAND_VIVO = "vivo";
        const string PACKAGE_VIVO_MAIN = "com.iqoo.secure";
        const string PACKAGE_VIVO_FALLBACK = "com.vivo.permissionmanager";
        const string PACKAGE_VIVO_COMPONENT = "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity";
        const string PACKAGE_VIVO_COMPONENT_FALLBACK = "com.vivo.permissionmanager.activity.BgStartUpManagerActivity";
        const string PACKAGE_VIVO_COMPONENT_FALLBACK_A = "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager";

        /* NOKIA */

        const string BRAND_NOKIA = "nokia";
        const string PACKAGE_NOKIA_MAIN = "com.evenwell.powersaving.g3";
        const string PACKAGE_NOKIA_COMPONENT = "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity";

        /* SAMSUNG */

        const string BRAND_SAMSUNG = "samsung";
        const string PACKAGE_SAMSUNG_MAIN = "com.samsung.android.lool";
        const string PACKAGE_SAMSUNG_COMPONENT = "com.samsung.android.sm.ui.battery.BatteryActivity";

        /* ONE PLUS */

        const string BRAND_ONE_PLUS = "oneplus";
        const string PACKAGE_ONE_PLUS_MAIN = "com.oneplus.security";
        const string PACKAGE_ONE_PLUS_COMPONENT = "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity";

        readonly List<string> PACKAGES_TO_CHECK_FOR_PERMISSION = new List<string>() {
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
        };

        public OEMBatteryWhitelist(Context context) {
            Context    = context;
        }

        bool IsRequestPermissionsAvailable {
            get {
                foreach (var packageInfo in Context.PackageManager.GetInstalledApplications(PackageInfoFlags.MatchAll)) {
                    if (PACKAGES_TO_CHECK_FOR_PERMISSION.Contains(packageInfo.PackageName))
                        return true;
                }

                return false;
            }
        }

        /// <summary>
        /// Returns true if the device is using an OEM battery optimization app
        /// </summary>
        public bool CanRequestPermissions { get { return IsRequestPermissionsAvailable; } }

        /// <summary>
        /// Returns null when the operation succeeded and the exception object when failed
        /// </summary>
        public Exception RequestPermissions() {
            return _requestPermissions() == true ? null : Exception;
        }

        bool _requestPermissions() {
            bool status = false;

            switch (CURRENT_BRAND) {
                case BRAND_ASUS:
                    status = IntentAsus();
                break;

                case BRAND_XIAOMI:
                case BRAND_XIAOMI_REDMI:
                    status = IntentXiaomi();
                break;

                case BRAND_LETV:
                    status = IntentLetv();
                break;

                case BRAND_HONOR:
                    status = IntentHonor();
                break;

                case BRAND_HUAWEI:
                    status = IntentHuawei();
                break;

                case BRAND_OPPO:
                    status = IntentOppo();
                break;

                case BRAND_VIVO:
                    status = IntentVivo();
                break;

                case BRAND_NOKIA:
                    status = IntentNokia();
                break;

                case BRAND_SAMSUNG:
                    status = IntentSamsung();
                break;

                case BRAND_ONE_PLUS:
                    status = IntentOnePlus();
                break;
            }


            return status;
        }

        #region [START_ACTIVITY]
        bool StartIntent(string pkg, string componentName) {
            try {
                Intent intent = new Intent();
                intent.SetComponent(new ComponentName(pkg, componentName));

                Context.StartActivity(intent);

                return true;
            } catch (Exception e) {
                Exception = e;

                return false;
            }
        }

        bool IntentXiaomi() {
            try {
                StartIntent(PACKAGE_XIAOMI_MAIN, PACKAGE_XIAOMI_COMPONENT);

                return true;
            } catch (Exception e) {
                Exception = e;

                return false;
            }
        }

        bool IntentAsus() {
            try {
                StartIntent(PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT);

                return true;
            } catch {
                try {
                    StartIntent(PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT_FALLBACK);

                    return true;
                } catch (Exception e) {
                    Exception = e;

                    return false;
                }
            }
        }

        bool IntentLetv() {
            try {
                StartIntent(PACKAGE_LETV_MAIN, PACKAGE_LETV_COMPONENT);

                return true;
            } catch (Exception e) {
                Exception = e;

                return false;
            }
        }

        bool IntentHonor() {
            try {
                StartIntent(PACKAGE_HONOR_MAIN, PACKAGE_HONOR_COMPONENT);

                return true;
            } catch (Exception e) {
                Exception = e;

                return false;
            }
        }

        bool IntentHuawei() {
            try {
                StartIntent(PACKAGE_HUAWEI_MAIN, PACKAGE_HUAWEI_COMPONENT);

                return true;
            } catch {
                try {
                    StartIntent(PACKAGE_HUAWEI_MAIN, PACKAGE_HUAWEI_COMPONENT_FALLBACK);

                    return true;
                } catch (Exception e) {
                    Exception = e;

                    return false;
                }
            }
        }

        bool IntentOppo() {
            try {
                StartIntent(PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT);

                return true;
            } catch {
                try {
                    StartIntent(PACKAGE_OPPO_FALLBACK, PACKAGE_OPPO_COMPONENT_FALLBACK);

                    return true;
                } catch {
                    try {
                        StartIntent(PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT_FALLBACK_A);

                        return true;
                    } catch (Exception e) {
                        Exception = e;

                        return false;
                    }
                }
            }
        }

        bool IntentVivo() {
            try {
                StartIntent(PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT);

                return true;
            } catch {
                try {
                    StartIntent(PACKAGE_VIVO_FALLBACK, PACKAGE_VIVO_COMPONENT_FALLBACK);

                    return true;
                } catch {
                    try {
                        StartIntent(PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT_FALLBACK_A);

                        return true;
                    } catch (Exception e) {
                        Exception = e;

                        return false;
                    }
                }
            }
        }

        bool IntentNokia() {
            try {
                StartIntent(PACKAGE_NOKIA_MAIN, PACKAGE_NOKIA_COMPONENT);

                return true;
            } catch (Exception e) {
                Exception = e;

                return false;
            }
        }

        bool IntentSamsung() {
            try {
                StartIntent(PACKAGE_SAMSUNG_MAIN, PACKAGE_SAMSUNG_COMPONENT);

                return true;
            } catch (Exception e) {
                Exception = e;

                return false;
            }
        }

        bool IntentOnePlus() {
            try {
                StartIntent(PACKAGE_ONE_PLUS_MAIN, PACKAGE_ONE_PLUS_COMPONENT);

                return true;
            } catch (Exception e) {
                Exception = e;

                return false;
            }
        }
        #endregion
    }
}
