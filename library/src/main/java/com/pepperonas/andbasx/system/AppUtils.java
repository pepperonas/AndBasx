/*
 * Copyright (c) 2016 Martin Pfeffer
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

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.pepperonas.andbasx.AndBasx;

/**
 * The type App utils.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class AppUtils {

    /**
     * Gets version code.
     *
     * @return The app's version-code (such as '1').
     */
    public static int getVersionCode() {
        try {
            PackageInfo pi = AndBasx.getContext().getPackageManager().getPackageInfo(AndBasx.getContext().getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * Gets version name.
     *
     * @return The app's version-name (such as '1.2.3b').
     */
    public static String getVersionName() {
        try {
            PackageInfo pi = AndBasx.getContext().getPackageManager()
                    .getPackageInfo(AndBasx.getContext().getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * Gets app name.
     *
     * @return The app's name (such as 'My Android App').
     */
    public static String getAppName() {
        try {
            ApplicationInfo ai = AndBasx.getContext().getPackageManager()
                    .getApplicationInfo(AndBasx.getContext().getPackageName(), 0);
            return (String) (ai != null ? AndBasx.getContext().getPackageManager().getApplicationLabel(ai) : "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * * @return The app's version-info (such as 'My Android App 1.2').
     *
     * @return the version info
     */
    public static String getVersionInfo() {
        return getAppName() + " " + getVersionName();
    }

}
