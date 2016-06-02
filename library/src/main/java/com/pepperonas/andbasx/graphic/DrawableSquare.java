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

package com.pepperonas.andbasx.graphic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

import com.pepperonas.andbasx.system.DeviceUtils;
import com.pepperonas.jbasx.div.MaterialColor;

/**
 * The type Drawable square.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class DrawableSquare extends Drawable {

    private final Paint paint;
    private final Builder builder;


    /**
     * Instantiates a new Drawable square.
     *
     * @param builder the builder
     */
    public DrawableSquare(Builder builder) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0, 0, builder.size, builder.size, builder.rounded, builder.rounded, paint);
        } else canvas.drawRect(0, 0, builder.size, builder.size, paint);
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

        private final float size;
        private final int color;
        private String tag = "";
        private float rounded;

        private boolean applySurfaceShadow = true;


        /**
         * Instantiates a new Builder.
         *
         * @param sizeDp the size dp
         * @param color  the color
         */
        public Builder(int sizeDp, int color) {
            this.size = DeviceUtils.dp2px(sizeDp);
            this.color = color;
        }


        /**
         * Instantiates a new Builder.
         *
         * @param sizeDp the size dp
         * @param color  the color
         */
        public Builder(int sizeDp, String color) {
            this.size = DeviceUtils.dp2px(sizeDp);
            this.color = Color.parseColor(color);
        }


        /**
         * Rounded builder.
         *
         * @param radiusDp the radius dp
         * @return the builder
         */
        public Builder rounded(int radiusDp) {
            this.rounded = DeviceUtils.dp2px(radiusDp);
            return this;
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
         * Build drawable square.
         *
         * @return the drawable square
         */
        public DrawableSquare build() {
            return new DrawableSquare(this);
        }


        /**
         * Show.
         *
         * @param imageView the image view
         */
        public void show(ImageView imageView) {
            imageView.setImageDrawable(this.build());
        }
    }


    /**
     * Gets tag.
     *
     * @return the tag
     */
    public String getTag() { return builder.tag; }

}
