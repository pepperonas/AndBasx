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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.pepperonas.andbasx.AndBasx;
import com.pepperonas.andbasx.concurrency.ThreadUtils;
import com.pepperonas.jbasx.Jbasx;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;

import static android.content.Context.WIFI_SERVICE;

/**
 * The type Network utils.
 */
public class NetworkUtils {

    private static final String TAG = "NetworkUtils";


    /**
     * Gets ip address.
     *
     * @param useIpV4 the use ip v 4
     * @return the ip address
     */
    public static String getIpAddress(boolean useIpV4) {
        String ip = "";
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        ip = addr.getHostAddress();
                        boolean isIPv4 = ip.indexOf(':') < 0;
                        if (useIpV4) {
                            if (isIPv4) {
                                return ip;
                            }
                        } else {
                            if (!isIPv4) {
                                int delim = ip.indexOf('%');
                                return delim < 0 ? ip.toUpperCase() : ip.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getIpAddress: " + e);
        }
        return ip;
    }


    /**
     * Gets network addresses.
     *
     * @param onlyIps the only ips
     * @return the network addresses
     */
    public static List<String> getNetworkAddresses(boolean onlyIps) {
        List<String> ipList = new ArrayList<>();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface nwInterface = en.nextElement();
                for (Enumeration<InetAddress> ipAddress = nwInterface.getInetAddresses(); ipAddress.hasMoreElements(); ) {
                    String ip;
                    if (onlyIps) {
                        ip = ipAddress.nextElement().toString().replace("/", "");
                        if (ip.contains(".")) {
                            ipList.add(ip);
                        }
                    } else {
                        ip = ipAddress.nextElement().toString();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            if (Jbasx.mLog == Jbasx.LogMode.ALL) {
                Log.e(TAG, "getNetworkAddresses - Retrieving network interfaces failed.");
                e.printStackTrace();
            }
        }
        return ipList;
    }


    /**
     * The enum Network type.
     */
    public enum NetworkType {
        /**
         * Wifi fast network type.
         */
        WIFI_FAST,
        /**
         * Mobile fast network type.
         */
        MOBILE_FAST,
        /**
         * Mobile middle network type.
         */
        MOBILE_MIDDLE,
        /**
         * Mobile slow network type.
         */
        MOBILE_SLOW,
        /**
         * None network type.
         */
        NONE,
    }


    private NetworkType type;
    private NetworkListener listener;


    /**
     * The interface Network listener.
     */
    public interface NetworkListener {

        /**
         * On network changed.
         *
         * @param ot the ot
         * @param nt the nt
         */
        void onNetworkChanged(NetworkType ot, NetworkType nt);
    }


    /**
     * Is wifi connected boolean.
     *
     * @return the boolean
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager)
                AndBasx.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info.isConnected();
    }


    /**
     * Gets network type.
     *
     * @return the network type
     */
    public final synchronized NetworkType getNetworkType() {
        return type;
    }


    /**
     * Sets listener.
     *
     * @param l the l
     */
    public final void setListener(NetworkListener l) {
        listener = l;
    }


    private NetworkUtils() {
        type = NetworkType.NONE;
        updateNetwork();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        BroadcastReceiver receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateNetwork();
            }
        };
        AndBasx.getContext().registerReceiver(receiver, filter);
    }


    private void updateNetwork() {
        NetworkInfo networkInfo = getNetworkInfo();
        NetworkType networkType = type;
        type = checkType(networkInfo);
        if (type != networkType && listener != null) {
            listener.onNetworkChanged(networkType, type);
        }
        Log.i(TAG, "Network type: " + type);
    }


    private synchronized NetworkInfo getNetworkInfo() {
        ConnectivityManager manager =
                (ConnectivityManager) AndBasx.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo();
    }


    private static NetworkType checkType(NetworkInfo info) {
        if (info == null || !info.isConnected()) {
            return NetworkType.NONE;
        }

        int type = info.getType();
        int subType = info.getSubtype();
        if ((type == ConnectivityManager.TYPE_WIFI)
                || (type == ConnectivityManager.TYPE_ETHERNET)) {
            return NetworkType.WIFI_FAST;
        }

        if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                // 2G
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NetworkType.MOBILE_SLOW;

                // 3G
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NetworkType.MOBILE_MIDDLE;

                // 4G
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NetworkType.MOBILE_FAST;
            }
        }

        return NetworkType.NONE;
    }


    /**
     * Is wifi enabled boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isWifiEnabled(Context context) {
        return ((WifiManager) (context
                .getApplicationContext()
                .getSystemService(WIFI_SERVICE)))
                .isWifiEnabled();
    }


    /**
     * Is connected to wifi boolean.
     *
     * @param context the context
     * @param which   the which
     * @return the boolean
     */
    public static boolean isConnectedToWifi(Context context, String which) {
        if (!isWifiEnabled(context)) {
            Log.w(TAG, "isConnectedToWifi: Wifi is disabled. Returning false...");
            return false;
        }

        WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                .getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();

        if (ssid.replace("\"", "").equals(which)) {
            Log.i(TAG, "Connected to " + which);
            return true;
        }
        Log.i(TAG, "Not connected to " + which);
        return false;
    }

    /**
     * Enable wifi.
     *
     * @param context the context
     * @param enabled the enabled
     */
    public static void enableWifi(Context context, boolean enabled) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(enabled);
    }


    /**
     * Connect to wifi.
     *
     * @param context the context
     * @param ssid    the ssid
     */
    public static void connectToWifi(final Context context, final String ssid) {
        NetworkUtils.enableWifi(context, true);
        ThreadUtils.runOnBackgroundThread(new Callable() {
            @Override
            public Object call() throws Exception {

                try {
                    WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                            .getSystemService(WIFI_SERVICE);

                    List<WifiConfiguration> wifiConfigurations = wifiManager.getConfiguredNetworks();

                    for (WifiConfiguration wifiConfiguration : wifiConfigurations) {
                        if (wifiConfiguration.SSID.equals("\"" + ssid + "\"")) {
                            boolean success = wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                            Log.i(TAG, "Enable " + wifiConfiguration.SSID + "success=" + success + "\nWifi-State: " + wifiManager.getWifiState());
                        }
                    }
                } catch (NullPointerException | IllegalStateException e) {
                    Log.e(TAG, "Missing network configuration.");
                }
                return null;
            }
        });
    }

}
