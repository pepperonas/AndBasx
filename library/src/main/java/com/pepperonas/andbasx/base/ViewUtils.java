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

import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class ViewUtils {

    private static final String TAG = "ViewUtils";


    public static int measureTextWidth(TextView view, String text) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }

        TextPaint paint = view.getPaint();
        return (int) paint.measureText(text);
    }


    public static boolean eventInView(MotionEvent event, View view) {
        if (event == null || view == null) {
            return false;
        }

        int eventX = (int) event.getRawX();
        int eventY = (int) event.getRawY();

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int width = view.getWidth();
        int height = view.getHeight();
        int left = location[0];
        int top = location[1];
        int right = left + width;
        int bottom = top + height;

        Rect rect = new Rect(left, top, right, bottom);
        return rect.contains(eventX, eventY);
    }


    public static Point getViewCenter(View view) {
        if (view == null) {
            return new Point();
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0] + view.getWidth() / 2;
        int y = location[1] + view.getHeight() / 2;
        return new Point(x, y);
    }

}
