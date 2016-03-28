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
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

import com.pepperonas.andbasx.system.DeviceUtils;
import com.pepperonas.jbasx.div.MaterialColor;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class DrawableSquareLetter extends Drawable {

    private final Paint paint;
    private Paint textPaint;
    private final Builder builder;


    public DrawableSquareLetter(Builder builder) {
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


        textPaint = new Paint();
        textPaint.setColor(builder.textColor);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(builder.isBold);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(builder.typeface);
        textPaint.setTextAlign(Paint.Align.CENTER);

        if (builder.applyTextShadow) {
            textPaint.setShadowLayer(
                    DeviceUtils.dp2px(2),
                    DeviceUtils.dp2px(1),
                    DeviceUtils.dp2px(1),
                    Color.parseColor(MaterialColor.GREY_800));
        }

    }


    @Override
    public void draw(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0, 0, builder.size, builder.size, builder.rounded, builder.rounded, paint);
        } else canvas.drawRect(0, 0, builder.size, builder.size, paint);

        if (builder.textSize == -1) {
            textPaint.setTextSize(builder.size * .96f);
        } else textPaint.setTextSize(builder.textSize);

        float width = builder.size;
        float height = builder.size;

        String text = builder.isUpperCase ? builder.text.toUpperCase() : builder.text;
        canvas.drawText(text, width / 2f, height / 2f - (((textPaint.descent() + textPaint.ascent())) / 2f), textPaint);
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


    public static class Builder {

        private final float size;
        private final int color;
        private String tag = "";
        private float rounded;
        private final String text;
        private int textColor = Color.BLACK;
        private float textSize = -1;
        private Typeface typeface = Typeface.DEFAULT;

        private boolean isUpperCase = true;
        private boolean isBold = true;
        private boolean applyTextShadow = true;
        private boolean applySurfaceShadow = true;


        public Builder(int sizeDp, int color, String text) {
            this.text = text;
            this.size = DeviceUtils.dp2px(sizeDp);
            this.color = color;
        }


        public Builder(int sizeDp, String color, String text) {
            this.text = text;
            this.size = DeviceUtils.dp2px(sizeDp);
            this.color = Color.parseColor(color);
        }


        public Builder textSize(float textSize) {
            this.textSize = DeviceUtils.sp2px(textSize);
            return this;
        }


        public Builder rounded(int radiusDp) {
            this.rounded = DeviceUtils.dp2px(radiusDp);
            return this;
        }


        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }


        public Builder textColor(String textColor) {
            this.textColor = Color.parseColor(textColor);
            return this;
        }


        public Builder typeface(Typeface typeface) {
            this.typeface = typeface;
            return this;
        }


        public Builder disableUpperCase() {
            this.isUpperCase = false;
            return this;
        }


        public Builder disableBold() {
            this.isBold = false;
            return this;
        }


        public Builder disableShadowOnSurface() {
            this.applySurfaceShadow = false;
            return this;
        }


        public Builder disableShadowOnText() {
            this.applyTextShadow = false;
            return this;
        }


        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }


        public DrawableSquareLetter build() {
            return new DrawableSquareLetter(this);
        }


        public void show(ImageView imageView) {
            imageView.setImageDrawable(this.build());
        }
    }


    public String getTag() { return builder.tag; }

}
