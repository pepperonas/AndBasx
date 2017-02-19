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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.pepperonas.andbasx.AndBasx;

/**
 * The type Clipboard utils.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class ClipboardUtils {

    /**
     * The constant TAG.
     */
    public static final String TAG = "ClipboardUtils";


    /**
     * Copy text to the clipboard.
     *
     * @param text The text to be copied.
     */
    @SuppressWarnings("deprecation")
    public static void setClipboardOlderApis(CharSequence text) {
        android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                AndBasx.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(text);
    }


    /**
     * Sets clipboard.
     *
     * @param text the text
     */
    public static void setClipboard(CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) AndBasx.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
    }


    /**
     * Retrieve text from the clipboard.
     *
     * @return The text which is stored in the clipboard.
     */
    @SuppressWarnings("deprecation")
    public static String getClipboard() {
        android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                AndBasx.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        return clipboard.getText().toString();
    }
}
