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

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.pepperonas.andbasx.AndBasx;

/**
 * The type Toast utils.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class ToastUtils {

    private static final String TAG = "ToastUtils";


    /**
     * Show a {@link Toast} with duration {@link Toast#LENGTH_SHORT}.
     *
     * @param message The {@link CharSequence} which should be shown.
     */
    public static void toastShort(CharSequence message) {
        Toast.makeText(AndBasx.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Show a {@link Toast} with duration {@link Toast#LENGTH_LONG}.
     *
     * @param message The {@link CharSequence} which should be shown.
     */
    public static void toastLong(CharSequence message) {
        Toast.makeText(AndBasx.getContext(), message, Toast.LENGTH_LONG).show();
    }


    /**
     * Show a {@link Toast} with duration {@link Toast#LENGTH_SHORT}.
     *
     * @param stringId The resource-id of the {@link String} which should be shown.
     */
    public static void toastShort(int stringId) { toastShort(AndBasx.getContext().getString(stringId)); }


    /**
     * Show a {@link Toast} with duration {@link Toast#LENGTH_SHORT}.
     *
     * @param stringId The resource-id of the {@link String} which should be shown.
     */
    public static void toastLong(int stringId) { toastLong(AndBasx.getContext().getString(stringId)); }


    /**
     * Show a {@link Toast} with duration {@link Toast#LENGTH_SHORT} from a background thread.
     *
     * @param message The {@link CharSequence} which should be shown.
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
     * Show a {@link Toast} with duration {@link Toast#LENGTH_LONG} from a background thread.
     *
     * @param message The {@link CharSequence} which should be shown.
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
     * Show a {@link Toast} with duration {@link Toast#LENGTH_SHORT} from a background thread.
     *
     * @param stringId The resource-id of the {@link String} which should be shown.
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
     * Show a {@link Toast} with duration {@link Toast#LENGTH_LONG} from a background thread.
     *
     * @param stringId The resource-id of the {@link String} which should be shown.
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
