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

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.pepperonas.andbasx.AndBasx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * The type Device utils.
 */
public class DeviceUtils {

    /**
     * The constant TAG.
     */
    public static final String TAG = "DeviceUtils";


    /**
     * Gets info.
     *
     * @return the info
     */
    public static List<List<String>> getInfo() {
        DisplayMetrics dm = AndBasx.getContext().getResources().getDisplayMetrics();
        List<String> descList = new ArrayList<String>();
        List<String> infoList = new ArrayList<String>();

        Collections.addAll(
                descList, "MODEL", "DEVICE", "BRAND",
                "PRODUCT", "DISPLAY", "MANUFACTURE",
                "SCREEN_WIDTH",
                "SCREEN_HEIGHT",
                "DENSITY");

        Collections.addAll(
                infoList, android.os.Build.MODEL, android.os.Build.DEVICE, android.os.Build.BRAND,
                android.os.Build.PRODUCT, android.os.Build.DISPLAY, android.os.Build.MANUFACTURER,
                String.valueOf(dm.widthPixels),
                String.valueOf(dm.heightPixels),
                String.valueOf(dm.density));

        List<List<String>> resultList = new ArrayList<List<String>>();
        resultList.add(descList);
        resultList.add(infoList);
        return resultList;
    }


    /**
     * Gets bluetooth mac.
     *
     * @return the bluetooth mac
     */
    @SuppressWarnings("ResourceType")
    public static String getBluetoothMac() {
        BluetoothAdapter adapter;
        String bluetoothMac = null;
        try {
            adapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothMac = adapter.getAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bluetoothMac;
    }


    /**
     * Gets wlan mac.
     *
     * @return the wlan mac
     */
    @SuppressWarnings("ResourceType")
    public static String getWlanMac() {
        String wlanMac = null;
        try {
            WifiManager wm = (WifiManager)
                    AndBasx.getContext().getSystemService(Context.WIFI_SERVICE);
            wlanMac = wm.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wlanMac;
    }


    /**
     * Gets android id.
     *
     * @return the android id
     */
    public static String getAndroidId() {
        String androidID = null;
        try {
            androidID = Settings.Secure.getString(
                    AndBasx.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidID;
    }


    /**
     * Gets imei.
     *
     * @return the imei
     */
    public static String getIMEI() {
        String deviceIMEI = null;
        try {
            TelephonyManager teleManager = (TelephonyManager)
                    AndBasx.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            deviceIMEI = teleManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceIMEI;
    }


    /**
     * Gets screen inches.
     *
     * @return the screen inches
     */
    public static float getScreenInches() {
        float screenInches = -1;
        try {
            Resources resources = AndBasx.getContext().getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            double width = Math.pow(dm.widthPixels / dm.xdpi, 2);
            double height = Math.pow(dm.heightPixels / dm.ydpi, 2);
            screenInches = (float) (Math.sqrt(width + height));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenInches;
    }


    /**
     * Dp 2 px int.
     *
     * @param dip the dip
     * @return the int
     */
    public static int dp2px(int dip) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, AndBasx.getContext().getResources().getDisplayMetrics()));
    }


    /**
     * Px 2 dp int.
     *
     * @param px the px
     * @return the int
     */
    public static int px2dp(int px) {
        return Math.round(px / (AndBasx.getContext().getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    /**
     * Sp 2 px int.
     *
     * @param sp the sp
     * @return the int
     */
    public static int sp2px(float sp) {
        return Math.round(sp * AndBasx.getContext().getResources().getDisplayMetrics().scaledDensity);
    }


    /**
     * Gets density.
     *
     * @return the density
     */
    public static int getDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) AndBasx.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi;
    }


    /**
     * Gets locale.
     *
     * @return the locale
     */
    public static Locale getLocale() {
        return AndBasx.getContext().getResources().getConfiguration().locale;
    }

}
