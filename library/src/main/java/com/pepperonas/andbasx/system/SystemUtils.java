/*
 * Copyright (c) 2017 Martin Pfeffer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pepperonas.andbasx.system;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.MotionEvent;

import com.pepperonas.andbasx.AndBasx;
import com.pepperonas.andbasx.base.ToastUtils;
import com.pepperonas.andbasx.datatype.InstalledApp;
import com.pepperonas.jbasx.log.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The type System utils.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class SystemUtils {

    private static final String TAG = "SystemUtils";

    /**
     * The constant MAX_BRIGHTNESS.
     */
    public static final int MAX_BRIGHTNESS = 255;
    /**
     * The constant MIN_BRIGHTNESS.
     */
    public static final int MIN_BRIGHTNESS = 0;


    /**
     * The enum Network type.
     */
    public enum NetworkType {
        /**
         * Mobile network type.
         */
        Mobile, /**
         * Wifi network type.
         */
        Wifi
    }


    /**
     * Gets android id.
     *
     * @return the android id
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static String getAndroidId() {
        return Settings.Secure.getString(AndBasx.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * Is main thread boolean.
     *
     * @return the boolean
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    /**
     * Launch app settings.
     *
     * @param activity    the activity
     * @param requestCode the request code
     */
    public static void launchAppSettings(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.getPackageName
                ()));
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * Close entire app.
     *
     * @param activity the activity
     */
    public static void closeEntireApp(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(intent);
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        activity.finish();
    }


    /**
     * Is installed boolean.
     *
     * @param packageName the package name
     * @return the boolean
     */
    public static boolean isInstalled(String packageName) {
        PackageInfo packageInfo;
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        final PackageManager packageManager = AndBasx.getContext().getPackageManager();
        List<PackageInfo> packageInformation = packageManager.getInstalledPackages(0);
        if (packageInformation == null) {
            return false;
        }
        for (PackageInfo packageInfo1 : packageInformation) {
            packageInfo = packageInfo1;
            final String name = packageInfo.packageName;
            if (packageName.equals(name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Gets installed app infos.
     *
     * @return the installed apps
     */
    public static List<ApplicationInfo> getInstalledAppInfos() {
        final PackageManager packageManager = AndBasx.getContext().getPackageManager();
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
    }


    /**
     * Gets installed app names.
     *
     * @return the installed app names
     */
    public static List<String> getInstalledAppNames() {
        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = AndBasx.getContext().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        List<String> appNames = new ArrayList<>();
        for (ResolveInfo resolveInfo : activities) {
            appNames.add(resolveInfo.loadLabel(packageManager).toString());
        }
        return appNames;
    }


    /**
     * Gets installed apps.
     *
     * @return the installed apps
     */
    public static List<InstalledApp> getInstalledApps() {
        List<InstalledApp> installedApps = new ArrayList<>();
        PackageManager packageManager = AndBasx.getContext().getApplicationContext().getPackageManager();
        for (ApplicationInfo ai : getInstalledAppInfos()) {
            try {
                ai = packageManager.getApplicationInfo(ai.packageName, 0);
            } catch (final Exception e) {
                ai = null;
            }
            installedApps.add(new InstalledApp(ai, (String) (ai != null ? packageManager.getApplicationLabel(ai) : "(unknown)")));
        }
        return installedApps;
    }


    /**
     * Gets status bar height.
     *
     * @return the status bar height
     */
    public static int getStatusBarHeight() {
        int height = 0;
        if (AndBasx.getContext() == null) {
            return height;
        }
        Resources resources = AndBasx.getContext().getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = resources.getDimensionPixelSize(resId);
        }
        return height;
    }


    /**
     * Uninstall app.
     *
     * @param packageName the package name
     */
    public static void uninstallApp(String packageName) {
        boolean installed = isInstalled(packageName);
        if (!installed) {
            ToastUtils.toastShort("Package not installed.");
            return;
        }

        boolean isRooted = isRooted();
        if (isRooted) {
            runAsRoot(new String[]{"pm uninstall " + packageName});
        } else {
            Uri uri = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AndBasx.getContext().startActivity(intent);
        }
    }


    /**
     * Is service running boolean.
     *
     * @param context      the context
     * @param serviceClass the service class
     * @return the boolean
     */
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) return true;
        }
        return false;
    }


    /**
     * Is screen on boolean.
     *
     * @return the boolean
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
    public static boolean isScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            DisplayManager dm = (DisplayManager) AndBasx.getContext().getSystemService(Context.DISPLAY_SERVICE);
            for (Display display : dm.getDisplays()) {
                return display.getState() != Display.STATE_OFF;
            }
        } else {
            PowerManager powerManager = (PowerManager) AndBasx.getContext().getSystemService(Context.POWER_SERVICE);
            return powerManager.isScreenOn();
        }
        return false;
    }


    /**
     * Lock the screen.
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static void lockScreen() {
        DevicePolicyManager deviceManager = (DevicePolicyManager)
                AndBasx.getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        deviceManager.lockNow();
    }


    /**
     * Gets system screen brightness.
     *
     * @return the system screen brightness
     */
    public static int getSystemScreenBrightness() {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(
                    AndBasx.getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }


    /**
     * Sets system screen brightness.
     *
     * @param brightness the brightness
     */
    public static void setSystemScreenBrightness(int brightness) {
        try {
            if (brightness < MIN_BRIGHTNESS) {
                brightness = MIN_BRIGHTNESS;
            }
            if (brightness > MAX_BRIGHTNESS) {
                brightness = MAX_BRIGHTNESS;
            }
            ContentResolver resolver = AndBasx.getContext().getContentResolver();
            Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
            resolver.notifyChange(uri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets brightness.
     *
     * @return the brightness
     */
    public static int getBrightness() {
        return Settings.System.getInt(
                AndBasx.getContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, -1);
    }


    /**
     * Gets brightness mode.
     *
     * @return the brightness mode
     */
    public static int getBrightnessMode() {
        int brightnessMode = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
        try {
            brightnessMode = Settings.System.getInt(
                    AndBasx.getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brightnessMode;
    }


    /**
     * Sets brightness mode.
     *
     * @param brightnessMode the brightness mode
     */
    public static void setBrightnessMode(int brightnessMode) {
        try {
            Settings.System.putInt(
                    AndBasx.getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE, brightnessMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Is airplane mode enabled boolean.
     *
     * @return the boolean
     */
    public static boolean isAirplaneModeEnabled() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(AndBasx.getContext().getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(AndBasx.getContext().getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }


    /**
     * Is network available boolean.
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     *
     * @return the boolean
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) AndBasx.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * Is wifi enabled boolean.
     * {@link android.Manifest.permission#ACCESS_WIFI_STATE}
     *
     * @return the boolean
     */
    public static boolean isWifiEnabled() {
        boolean enabled = false;
        try {
            WifiManager wifiManager = (WifiManager)
                    AndBasx.getContext().getSystemService(Context.WIFI_SERVICE);
            enabled = wifiManager.isWifiEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enabled;
    }


    /**
     * Sets wifi enabled.
     * {@link android.Manifest.permission#CHANGE_WIFI_STATE}
     *
     * @param enable the enable
     */
    public static void setWifiEnabled(boolean enable) {
        try {
            WifiManager wifiManager = (WifiManager)
                    AndBasx.getContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(enable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets network type.
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     *
     * @return the network type
     */
    public static NetworkType getNetworkType() {
        ConnectivityManager conMan = (ConnectivityManager) AndBasx.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = conMan.getNetworkInfo(0).getState();
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            return NetworkType.Mobile;
        } else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            return NetworkType.Wifi;
        }
        if (AndBasx.mLog == AndBasx.LogMode.ALL || AndBasx.mLog == AndBasx.LogMode.DEFAULT) {
            Log.w(TAG, "getNetworkType " + "invalid NetworkType.");
        }
        return null;
    }


    /**
     * Gets carrier name.
     *
     * @return the carrier name
     */
    public static String getCarrierName() {
        TelephonyManager manager = (TelephonyManager) AndBasx.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getNetworkOperatorName();
    }


    /**
     * Gets wifi name.
     * {@link android.Manifest.permission#CHANGE_WIFI_STATE}
     *
     * @return the wifi name
     */
    public static String getWifiName() {
        WifiManager wifiManager = (WifiManager) AndBasx.getContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getSSID();
    }


    /**
     * Gets wifi signal strength.
     *
     * @return the wifi signal strength
     */
    public static int getWifiSignalStrength() {
        WifiManager wifiManager = (WifiManager) AndBasx.getContext().getSystemService(Context.WIFI_SERVICE);
        int numberOfLevels = 101;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
    }


    /**
     * Is master sync enabled boolean.
     * {@link android.Manifest.permission#READ_SYNC_SETTINGS}
     *
     * @return the boolean
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static boolean isMasterSyncEnabled() {
        return ContentResolver.getMasterSyncAutomatically();
    }


    /**
     * Is gps enabled boolean.
     *
     * @return the boolean
     */
    public static boolean isGpsEnabled() {
        return ((LocationManager) AndBasx.getContext().getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    /**
     * Is gps network enabled boolean.
     *
     * @return the boolean
     */
    public static boolean isGpsNetworkEnabled() {
        return ((LocationManager) AndBasx.getContext().getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    /**
     * Is gps passive enabled boolean.
     *
     * @return the boolean
     */
    public static boolean isGpsPassiveEnabled() {
        return ((LocationManager) AndBasx.getContext().getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
    }


    /**
     * Is charging boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isCharging(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = 0;
        if (intent != null) {
            plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        }
        return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
    }


    /**
     * Is rooted boolean.
     *
     * @return the boolean
     */
    public static boolean isRooted() {
        String binaryName = "su";
        boolean rooted = false;
        String[] places = {"/sbin/", "/system/bin/", "/system/xbin/",
                "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/"};
        for (String where : places) {
            if (new File(where + binaryName).exists()) {
                rooted = true;
                break;
            }
        }
        return rooted;
    }


    /**
     * Input key event.
     *
     * @param keyCode the key code
     */
    public static void inputKeyEvent(int keyCode) {
        try {
            runAsRoot(new String[]{"Input key-event: " + keyCode});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Run cmd string.
     *
     * @param cmd the cmd
     * @return the string
     */
    public static String runCmd(String cmd) {
        if (TextUtils.isEmpty(cmd)) {
            return null;
        }
        Process process;
        String result = null;

        String[] commands = {"/system/bin/sh", "-c", cmd};

        try {
            process = Runtime.getRuntime().exec(commands);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read;
            InputStream errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write('\n');

            InputStream inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }

            byte[] data = baos.toByteArray();
            result = new String(data);

            Log.d(TAG, "runCmd result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Run as root.
     *
     * @param cmds the cmds
     */
    public static void runAsRoot(String[] cmds) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets distance.
     *
     * @param e1 the e 1
     * @param e2 the e 2
     * @return the distance
     */
    public static int getDistance(MotionEvent e1, MotionEvent e2) {
        float x = e1.getX() - e2.getX();
        float y = e1.getY() - e2.getY();
        return (int) Math.sqrt(x * x + y * y);
    }


    /**
     * Gets max memory.
     *
     * @return the max memory
     */
    public static long getMaxMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        Log.d(TAG, "Application max memory: " + maxMemory);
        return maxMemory;
    }


    /**
     * Is debuggable boolean.
     *
     * @return the boolean
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public static boolean isDebuggable() {
        return ((AndBasx.getContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
    }


    /**
     * Gets application dir.
     *
     * @return the application dir
     */
    public static String getApplicationDir() {
        PackageManager packageManager = AndBasx.getContext().getPackageManager();
        String packageName = AndBasx.getContext().getPackageName();
        String applicationDir = null;
        try {
            PackageInfo p = packageManager.getPackageInfo(packageName, 0);
            applicationDir = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationDir;
    }

}
