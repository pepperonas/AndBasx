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

package com.pepperonas.andbasx.base;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.pepperonas.andbasx.AndBasx;

/**
 * The type Toast utils.
 */
public class ToastUtils {

    private static final String TAG = "ToastUtils";


    /**
     * Toast short.
     *
     * @param message the message
     */
    public static void toastShort(CharSequence message) {
        Toast.makeText(AndBasx.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Toast long.
     *
     * @param message the message
     */
    public static void toastLong(CharSequence message) {
        Toast.makeText(AndBasx.getContext(), message, Toast.LENGTH_LONG).show();
    }


    /**
     * Toast short.
     *
     * @param stringId the string id
     */
    public static void toastShort(int stringId) { toastShort(AndBasx.getContext().getString(stringId)); }


    /**
     * Toast long.
     *
     * @param stringId the string id
     */
    public static void toastLong(int stringId) { toastLong(AndBasx.getContext().getString(stringId)); }


    /**
     * Toast short from background.
     *
     * @param message the message
     */
    public static void toastShortFromBackground(final CharSequence message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.toastShort(message);
            }
        });
    }


    /**
     * Toast long from background.
     *
     * @param message the message
     */
    public static void toastLongFromBackground(final CharSequence message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.toastLong(message);
            }
        });
    }


    /**
     * Toast short from background.
     *
     * @param stringId the string id
     */
    public static void toastShortFromBackground(final int stringId) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.toastShort(stringId);
            }
        });
    }


    /**
     * Toast long from background.
     *
     * @param stringId the string id
     */
    public static void toastLongFromBackground(final int stringId) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.toastLong(stringId);
            }
        });
    }

}
