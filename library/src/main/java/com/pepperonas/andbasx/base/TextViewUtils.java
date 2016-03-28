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

import android.graphics.Typeface;
import android.text.Html;
import android.widget.TextView;

import com.pepperonas.jbasx.div.MaterialColor;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class TextViewUtils {

    public enum TextSize {
        SMALL("small"), NORMAL("normal"), LARGE("big");

        String textSize;


        TextSize(String size) {
            this.textSize = size;
        }
    }


    /**
     * Sets the text of a {@link TextView} to the default style.
     */
    public static void setNormalStyle(TextView textView) {
        textView.setTypeface(null, Typeface.NORMAL);
    }


    /**
     * Sets the text of a {@link TextView} italic.
     */
    public static void setItalic(TextView textView) {
        textView.setTypeface(null, Typeface.ITALIC);
    }


    /**
     * Sets the text of a {@link TextView} bold.
     */
    public static void setBold(TextView textView) {
        textView.setTypeface(null, Typeface.BOLD);
    }


    /**
     * Sets the text of a {@link TextView} bold and italic.
     */
    public static void setBoldAndItalic(TextView textView) {
        textView.setTypeface(null, Typeface.BOLD_ITALIC);
    }


    /**
     * Colorizes the text of a {@link TextView}.
     *
     * @param color use {@link MaterialColor}.
     */
    public static void colorize(TextView textView, String text, String color) {
        textView.setText(Html.fromHtml("<normal><font color=\"" + color + "\">" + text + "</font></normal>"));
    }


    /**
     * Resize the font size of a {@link TextView}.
     *
     * @param size use {@link TextSize}.
     */
    public static void resize(TextView textView, String text, TextSize size) {
        textView.setText(Html.fromHtml("<" + size.textSize + ">" + text + "</" + size.textSize + ">"));
    }


    /**
     * Resize the font size of a {@link TextView} and colorizes the text.
     *
     * @param color use {@link MaterialColor}.
     * @param size  use {@link TextSize}.
     */
    public static void resizeColorized(TextView textView, String text, String color, TextSize size) {
        textView.setText(Html.fromHtml("<" + size.textSize + "><font color=\"" + color + "\">" + text + "</font></" + size.textSize + ">"));
    }

}
