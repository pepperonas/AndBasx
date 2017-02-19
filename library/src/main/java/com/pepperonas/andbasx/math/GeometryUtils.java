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

package com.pepperonas.andbasx.math;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * The type Geometry utils.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class GeometryUtils {

    private static final String TAG = "GeometryUtils";


    /**
     * Points to rectangle rect.
     *
     * @param a the a
     * @param b the b
     * @return the rect
     */
    public static Rect pointsToRectangle(Point a, Point b) {
        int tmp_sX = (a.x < b.x) ? a.x : b.x;
        int tmp_sY = (a.y < b.y) ? a.y : b.y;
        int tmp_lX = (a.x > b.x) ? a.x : b.x;
        int tmp_lY = (a.y > b.y) ? a.y : b.y;
        return new Rect(tmp_sX, tmp_sY, tmp_lX, tmp_lY);
    }

}
