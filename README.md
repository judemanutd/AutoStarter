# Autostarter

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

    dependencies {
        // ... other dependencies
        implementation 'com.judemanutd:autostarter:1.0.0'
    }

### Maven

     <dependency>
       <groupId>com.judemanutd</groupId>
       <artifactId>autostarter</artifactId>
       <version>1.0.0</version>
       <type>pom</type>
     </dependency>
 
 Once you have integrated the library calling the following function will bring up the autostart permission manager of the phone.
 
     AutoStartPermissionHelper.getInstance().getAutoStartPermission(context);
     
 In order to keep the library small and simple I have not included any dialogs or pop ups, It is upto you to provide the user with a message if necessary.
 
 On phones that are not running a custom UI or that do not require an autostart permission, this code will not open any new screen.
 
 ##### Note
 As of now the library has support for the following manufacturers :
 
 1. Xiaomi
 2. Letv
 3. Honor [ Untested ]
 4. Oppo [ Untested ]
 5. Vivo [ Untested ]
 
 I will be adding support for other manufacturers as and when possible. I am also open to PR's and contributions from others.