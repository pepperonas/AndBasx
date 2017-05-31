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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pepperonas.andbasx.AndBasx;

/**
 * The type Permission utils.
 */
public class PermissionUtils {

    private static final String TAG = "PermissionUtils";


    /**
     * Ensure permissions boolean.
     *
     * @param permissions the permissions
     * @return the boolean
     */
    public static boolean ensurePermissions(@NonNull String... permissions) {
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PackageManager.PERMISSION_GRANTED != AndBasx.getContext().checkSelfPermission(permission)) {
                    Log.i(TAG, "ensurePermissions:  " + permission + " is not granted.");
                    return false;
                } else Log.i(TAG, "ensurePermissions:  " + permission + " is granted.");
            } else {
                Log.i(TAG, "ensurePermissions: Build.VERSION( " + Build.VERSION.SDK_INT + ") < 23. Return true.");
                return true;
            }
        }
        return true;
    }


    /**
     * Launch intent to manage overlay permission.
     *
     * @param activity    the activity
     * @param requestCode the request code
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void launchIntentToManageOverlayPermission(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, requestCode);
    }

}
