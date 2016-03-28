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

package com.pepperonas.andbasx.animation;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.pepperonas.andbasx.AndBasx;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class FadeAnimationColored {

    private View view;
    private float maxBrightness = 1.0f;
    private float minBrightness = 0.0f;
    private long duration = 400L;
    private long startOffset = 0L;
    private int colorId = android.R.color.white;


    public FadeAnimationColored(View view) {
        this.view = view;
        prepareView();
    }


    public FadeAnimationColored(View view, int colorId) {
        this.view = view;
        this.colorId = AndBasx.getContext().getResources().getColor(colorId);
        prepareView();
    }


    public FadeAnimationColored(View view, String color) {
        this.view = view;
        this.colorId = Color.parseColor(color);
        prepareView();
    }


    public FadeAnimationColored(View view, float maxBrightness, float minBrightness, long duration, long startOffset) {
        this.view = view;
        this.maxBrightness = maxBrightness;
        this.minBrightness = minBrightness;
        this.duration = duration;
        this.startOffset = startOffset;
        prepareView();
    }


    public FadeAnimationColored(View view, String color, float maxBrightness, float minBrightness, long duration, long startOffset) {
        this.view = view;
        this.colorId = Color.parseColor(color);
        this.maxBrightness = maxBrightness;
        this.minBrightness = minBrightness;
        this.duration = duration;
        this.startOffset = startOffset;
        prepareView();
    }


    public FadeAnimationColored(View view, int colorId, float maxBrightness, float minBrightness, long duration, long startOffset) {
        this.view = view;
        this.colorId = AndBasx.getContext().getResources().getColor(colorId);
        this.maxBrightness = maxBrightness;
        this.minBrightness = minBrightness;
        this.duration = duration;
        this.startOffset = startOffset;
        prepareView();
    }


    public void fadeOut() {
        this.view.setAlpha(1f);
        Animation anim = new AlphaAnimation(minBrightness, maxBrightness);
        anim.setDuration(duration);
        anim.setStartOffset(startOffset);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }


    public void fadeIn() {
        Animation anim = new AlphaAnimation(maxBrightness, minBrightness);
        anim.setDuration(duration);
        anim.setStartOffset(startOffset);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }


    private void prepareView() {
        this.view.setBackgroundColor(this.colorId);
        this.view.setAlpha(0f);
    }

}
