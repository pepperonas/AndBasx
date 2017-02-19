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

package com.pepperonas.andbasx.concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * The type Timer utils.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class TimerUtils {

    /**
     * Run continuously.
     *
     * @param rate     the rate
     * @param callable the callable
     */
    public static void runContinuously(int rate, final Callable<Void> callable) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, rate);
    }


    /**
     * Run continuously.
     *
     * @param rate     the rate
     * @param period   the period
     * @param callable the callable
     */
    public static void runContinuously(int rate, int period, final Callable<Void> callable) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, period, rate);
    }

}
