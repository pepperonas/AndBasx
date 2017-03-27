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

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.TypedValue;
import com.pepperonas.andbasx.AndBasx;

/**
 * The type Loader.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class Loader {

    /**
     * G str string.
     *
     * @param stringId the string id
     * @return the string
     */
    public static String gStr(@StringRes int stringId) {
        return AndBasx.getContext().getString(stringId);
    }


    /**
     * Gets drawable.
     *
     * @param drawableId the drawable id
     * @return the drawable
     */
    public static Drawable getDrawable(@DrawableRes int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return AndBasx.getContext().getResources()
                .getDrawable(drawableId, AndBasx.getContext().getTheme());
        } else {
            return AndBasx.getContext().getResources().getDrawable(drawableId);
        }
    }


    /**
     * Resolve drawable id int.
     *
     * @param source the source
     * @return the int
     */
    public static int resolveDrawableId(@NonNull String source) {
        return AndBasx.getContext().getResources()
            .getIdentifier("drawable/" + source, null, AndBasx.getContext().getPackageName());
    }

    /**
     * Resolve drawable id int.
     *
     * @param source the source
     * @return the int
     */
    public static int resolveDrawableIdAlt(@NonNull String source) {
        return AndBasx.getContext().getResources()
            .getIdentifier("@drawable/" + source, null, AndBasx.getContext().getPackageName());
    }


    /**
     * Gets attr.
     *
     * @param context the context
     * @param attrId the attr id
     * @return the attr
     */
    public static int getAttr(@NonNull Context context, @AttrRes int attrId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrId, typedValue, true);
        return typedValue.data;
    }


    /**
     * Gets color.
     *
     * @param colorId the color id
     * @return the color
     */
    public static int getColor(@ColorRes int colorId) {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            return AndBasx.getContext().getColor(colorId);
        } else {
            return AndBasx.getContext().getResources().getColor(colorId);
        }
    }

}
