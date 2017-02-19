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

package com.pepperonas.andbasx.graphic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.pepperonas.andbasx.system.DeviceUtils;
import com.pepperonas.jbasx.div.MaterialColor;

/**
 * The type Drawable circle.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class DrawableCircle extends Drawable {

    private final Paint paint;
    private final Builder builder;


    /**
     * Instantiates a new Drawable circle.
     *
     * @param builder the builder
     */
    public DrawableCircle(Builder builder) {
        this.builder = builder;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(builder.color);

        if (builder.applySurfaceShadow) {
            paint.setShadowLayer(
                    DeviceUtils.dp2px(4),
                    DeviceUtils.dp2px(2),
                    DeviceUtils.dp2px(2),
                    Color.parseColor(MaterialColor.GREY_900));
        }
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(builder.radius, builder.radius, builder.radius, paint);
    }


    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }


    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }


    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


    /**
     * The type Builder.
     */
    public static class Builder {

        private final float radius;
        private final int color;
        private String tag = "";

        private boolean applySurfaceShadow = true;


        /**
         * Instantiates a new Builder.
         *
         * @param diameterDp the diameter dp
         * @param color      the color
         */
        public Builder(int diameterDp, int color) {
            this.radius = DeviceUtils.dp2px(diameterDp / 2);
            this.color = color;
        }


        /**
         * Instantiates a new Builder.
         *
         * @param diameterDp the diameter dp
         * @param color      the color
         */
        public Builder(int diameterDp, String color) {
            this.radius = DeviceUtils.dp2px(diameterDp / 2);
            this.color = Color.parseColor(color);
        }


        /**
         * Disable shadow on surface builder.
         *
         * @return the builder
         */
        public Builder disableShadowOnSurface() {
            this.applySurfaceShadow = false;
            return this;
        }


        /**
         * Sets tag.
         *
         * @param tag the tag
         * @return the tag
         */
        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }


        /**
         * Build drawable circle.
         *
         * @return the drawable circle
         */
        public DrawableCircle build() {
            return new DrawableCircle(this);
        }


        /**
         * Show.
         *
         * @param imageView the image view
         */
        public void show(ImageView imageView) {
            imageView.setImageDrawable(new DrawableCircle(this));
        }
    }


    /**
     * Gets tag.
     *
     * @return the tag
     */
    public String getTag() { return builder.tag; }

}
