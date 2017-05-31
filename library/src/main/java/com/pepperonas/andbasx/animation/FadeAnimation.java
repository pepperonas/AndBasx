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

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * The type Fade animation.
 */
public class FadeAnimation {

    private View view;
    private float maxBrightness = 1.0f;
    private float minBrightness = 0.1f;
    private long duration = 400L;
    private long startOffset = 0L;


    /**
     * Instantiates a new Fade animation.
     *
     * @param view the view
     */
    public FadeAnimation(View view) {
        this.view = view;
    }


    /**
     * Instantiates a new Fade animation.
     *
     * @param view          the view
     * @param maxBrightness the max brightness
     * @param minBrightness the min brightness
     * @param duration      the duration
     * @param startOffset   the start offset
     */
    public FadeAnimation(View view, float maxBrightness, float minBrightness, long duration, long startOffset) {
        this.view = view;
        this.maxBrightness = maxBrightness;
        this.minBrightness = minBrightness;
        this.duration = duration;
        this.startOffset = startOffset;
    }


    /**
     * Fade out.
     */
    public void fadeOut() {
        Animation anim = new AlphaAnimation(maxBrightness, minBrightness);
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
        Animation anim = new AlphaAnimation(minBrightness, maxBrightness);
        anim.setDuration(duration);
        anim.setStartOffset(startOffset);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

}
