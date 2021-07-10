# Autostarter

[![](https://jitpack.io/v/judemanutd/AutoStarter.svg)](https://jitpack.io/#judemanutd/AutoStarter)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg?style=flat-square)](https://opensource.org/licenses/MIT) 
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)

<img src="Logotype primary.png" width="70%" height="70%" />

This library helps bring up the autostart permission manager of a phone to the user so they can add
an app to autostart.

# Why this library?

In my experience so far when using Firebase to integrate cloud messaging or notifications in app, phones running stock android OS receive notifications
perfectly whereas phones which have custom UI installed on them from the OEM such as Xiaomi, Letv do not receive any fcm calls.

This is because the OEM's by defaut _add an unrecognized app to the blacklist_ which prevents it from running in the
background and receiving notifications, apps like Whatsapp on the other hand are known apps which are whitelisted by the OEM and can hence receive notifications.

To work around this the user must allow your app to autostart which gives your app the required permission to run in the background and listen for any messages from Firebase.
Unfortunately since this is an OEM specific problem and not an android sdk problem, there is no documented api for bringing up the autostart permission manager of a phone.

Each manufacturer has their own version of it with different package names, hence this library was created to curate a list of all the packages used by each OEM for their version
of the autostart permissiong manager.

# Usage

### Gradle

Add this to your module's `build.gradle` file:

```groovy
dependencies {
        // ... other dependencies
        implementation 'com.github.judemanutd:autostarter:1.1.0'
}
```

### Maven

```xml
<dependency>
    <groupId>com.github.judemanutd</groupId>
    <artifactId>autostarter</artifactId>
    <version>1.1.0</version>
    <type>pom</type>
</dependency>
```
 
 Once you have integrated the library calling the following function will bring up the autostart permission manager of the phone. 
 The function returns a boolean to indicate if the action was as success or a failure.
 
 Parameters
 * If true is passed as the second parameter, it will attempt to open the activity, otherwise it will just check its existence
 * if true is passed as the third parameter, the activity is attempted to be opened it will add FLAG_ACTIVITY_NEW_TASK to the intent
 
 ```kotlin
     AutoStartPermissionHelper.getInstance().getAutoStartPermission(context)
```

In order to check if your phone is supported by the library you can call the following command. If true is passed as the second parameter, the method will only return true if the screen is supported by the library. 
If false, the method will return true as long as the permission exist even if the screen is not supported by the library.
 
```kotlin
     AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(context)
```
     
 In order to keep the library small and simple I have not included any dialogs or pop ups, It is upto you to provide the user with a message if necessary.
 
 On phones that are not running a custom UI or that do not require an autostart permission, this code will not open any new screen.
 
 ##### Note
 As of now the library has support for the following manufacturers :
 
 1. Xiaomi
 2. Redmi
 3. Letv
 4. Honor [ Untested ]
 5. Oppo [ Untested ]
 6. Vivo [ Untested ]
 7. Huawei
 8. Samsung
 9. Asus
 10. One Plus [ Untested ]

 I will be adding support for other manufacturers as and when possible. I am also open to PR's and contributions from others.

## Related Info

Since this depends entirely on the OEM and not on android itself, the underlying component that this library makes use of is continuously changing.
Do check out these repositories for further information on this issue

- [dont-kill-my-app](https://github.com/urbandroid-team/dont-kill-my-app)
- [backgroundable-android](https://github.com/dirkam/backgroundable-android)
- [AppKillerManager](https://github.com/thelittlefireman/AppKillerManager)
- [CRomAppWhitelist](https://github.com/WanghongLin/CRomAppWhitelist)
