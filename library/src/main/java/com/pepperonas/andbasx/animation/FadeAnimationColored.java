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

package com.pepperonas.andbasx.animation;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.pepperonas.andbasx.AndBasx;

/**
 * The type Fade animation colored.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class FadeAnimationColored {

    private View view;
    private float maxBrightness = 1.0f;
    private float minBrightness = 0.0f;
    private long duration = 400L;
    private long startOffset = 0L;
    private int colorId = android.R.color.white;


    /**
     * Instantiates a new Fade animation colored.
     *
     * @param view the view
     */
    public FadeAnimationColored(View view) {
        this.view = view;
        prepareView();
    }


    /**
     * Instantiates a new Fade animation colored.
     *
     * @param view    the view
     * @param colorId the color id
     */
    public FadeAnimationColored(View view, int colorId) {
        this.view = view;
        this.colorId = AndBasx.getContext().getResources().getColor(colorId);
        prepareView();
    }


    /**
     * Instantiates a new Fade animation colored.
     *
     * @param view  the view
     * @param color the color
     */
    public FadeAnimationColored(View view, String color) {
        this.view = view;
        this.colorId = Color.parseColor(color);
        prepareView();
    }


    /**
     * Instantiates a new Fade animation colored.
     *
     * @param view          the view
     * @param maxBrightness the max brightness
     * @param minBrightness the min brightness
     * @param duration      the duration
     * @param startOffset   the start offset
     */
    public FadeAnimationColored(View view, float maxBrightness, float minBrightness, long duration, long startOffset) {
        this.view = view;
        this.maxBrightness = maxBrightness;
        this.minBrightness = minBrightness;
        this.duration = duration;
        this.startOffset = startOffset;
        prepareView();
    }


    /**
     * Instantiates a new Fade animation colored.
     *
     * @param view          the view
     * @param color         the color
     * @param maxBrightness the max brightness
     * @param minBrightness the min brightness
     * @param duration      the duration
     * @param startOffset   the start offset
     */
    public FadeAnimationColored(View view, String color, float maxBrightness, float minBrightness, long duration,
                                long startOffset) {
        this.view = view;
        this.colorId = Color.parseColor(color);
        this.maxBrightness = maxBrightness;
        this.minBrightness = minBrightness;
        this.duration = duration;
        this.startOffset = startOffset;
        prepareView();
    }


    /**
     * Instantiates a new Fade animation colored.
     *
     * @param view          the view
     * @param colorId       the color id
     * @param maxBrightness the max brightness
     * @param minBrightness the min brightness
     * @param duration      the duration
     * @param startOffset   the start offset
     */
    public FadeAnimationColored(View view, int colorId, float maxBrightness, float minBrightness, long duration, long
            startOffset) {
        this.view = view;
        this.colorId = AndBasx.getContext().getResources().getColor(colorId);
        this.maxBrightness = maxBrightness;
        this.minBrightness = minBrightness;
        this.duration = duration;
        this.startOffset = startOffset;
        prepareView();
    }


    /**
     * Fade out.
     */
    public void fadeOut() {
        this.view.setAlpha(1f);
        Animation anim = new AlphaAnimation(minBrightness, maxBrightness);
        anim.setDuration(duration);
        anim.setStartOffset(startOffset);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }


    /**
     * Fade in.
     */
    public void fadeIn() {
        Animation anim = new AlphaAnimation(maxBrightness, minBrightness);
        anim.setDuration(duration);
        anim.setStartOffset(startOffset);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }


    private void prepareView() {
        this.view.setBackgroundColor(ContextCompat.getColor(AndBasx.getContext(), this.colorId));
        this.view.setAlpha(0f);
    }

}
