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

package com.pepperonas.andbasx.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.pepperonas.andbasx.AndBasx;
import com.pepperonas.jbasx.log.Log;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class AndroidStorageUtils {

    private static final String TAG = "AndroidStorageUtils";

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    /**
     * Checks if the app has permission to write to device storage.
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions.
     *
     * @param activity The calling activity.
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }


    /**
     * The app's internal directory (such as '/data/data/com.pepperonas.testapp/files').
     */
    public static String getAppsInternalDataDir() {
        return AndBasx.getContext().getFilesDir().getAbsolutePath();
    }


    /**
     * The app's external files directory (such as '/storage/emulated/0/Android/data/com.pepperonas.testapp/files/dirName').
     */
    public static String getAppsExternalFileDir(String dirName) {
        if (AndBasx.getContext().getExternalFilesDir(dirName) != null) {
            return AndBasx.getContext().getExternalFilesDir(dirName).getAbsolutePath();
        } else {
            Log.e(TAG, "getDownloadDir - Not found.");
            return null;
        }
    }


    /**
     * The app's external cache directory (such as '/storage/emulated/0/Android/data/com.pepperonas.testapp/cache').
     */
    public static String getAppsExternalCacheDir() {
        if (AndBasx.getContext().getExternalCacheDir() != null) {
            return AndBasx.getContext().getExternalCacheDir().getAbsolutePath();
        } else {
            Log.e(TAG, "getAppsExternalCacheDir - Not found.");
            return null;
        }
    }


    /**
     * The external root directory (such as '/storage/emulated/0').
     */
    public static String getExternalDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }


    /**
     * The download directory (such as '/storage/emulated/0/Download').
     */
    public static String getDownloadDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
    }

}
