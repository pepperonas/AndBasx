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

package com.pepperonas.andbasx.system;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.pepperonas.andbasx.R;
import com.pepperonas.jbasx.base.TextUtils;
import com.pepperonas.jbasx.color.ColorUtils;

/**
 * The type Notification utils.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class NotificationUtils {

    /**
     * The enum Led color.
     */
    public enum LedColor {
        /**
         * Green led color.
         */
        Green("0x00ff00"), /**
         * Blue led color.
         */
        Blue("0x0000ff"), /**
         * Red led color.
         */
        Red("0xff0000"), /**
         * Yellow led color.
         */
        Yellow("0xfff000"), /**
         * White led color.
         */
        White("0xffffff"), /**
         * Purple led color.
         */
        Purple("0xff00ff"), /**
         * Orange led color.
         */
        Orange("0xff6600");

        private String color;


        LedColor(String s) {
            this.color = s;
        }


        /**
         * Gets color.
         *
         * @return the color
         */
        public String getColor() {
            return color;
        }
    }


    /**
     * Instantiates a new Notification utils.
     *
     * @param builder the builder
     */
    public NotificationUtils(Builder builder) {
        int color = 0;
        if (builder.ledColor != null) {
            color = ColorUtils.setBrightness(builder.ledColor.getColor(), builder.ledBrightnessPercent);
        }

        Intent notificationIntent = new Intent(builder.context, builder.receiver);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(builder.context, 0, notificationIntent, 0);

        Notification.Builder nb = new Notification.Builder(builder.context);
        nb.setContentIntent(pendingIntent)
                .setSmallIcon(builder.iconId)
                .setTicker(builder.ticker)
                .setContentTitle(builder.title)
                .setContentText(builder.message)
                .setWhen(builder.when == 0 ? System.currentTimeMillis() : builder.when)
                .setAutoCancel(builder.autoCancel)
                .setDefaults(builder.flags)
                .setOnlyAlertOnce(builder.onlyOnce)
                .setOngoing(builder.onGoing);

        if (builder.ledColor != null) {
            nb.setLights(color, builder.onMs, builder.offMs);
        }

        if (!builder.vibration) {
            nb.setVibrate(new long[]{});
            if (builder.ledColor != null && !builder.sound) {
                nb.setDefaults(Notification.FLAG_SHOW_LIGHTS);
            } else if (builder.ledColor != null) {
                nb.setDefaults(Notification.FLAG_SHOW_LIGHTS & Notification.DEFAULT_SOUND);
            } else if (builder.sound) {
                nb.setDefaults(Notification.DEFAULT_SOUND);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && !TextUtils.isEmpty(builder.subtext)) {
            nb.setSubText(builder.subtext);
        }

        Notification notification = nb.getNotification();

        NotificationManager nm = (NotificationManager) builder.context.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(builder.notificationId, notification);
    }


    /**
     * The type Builder.
     */
    public static class Builder {

        private final Context context;
        private final Class<?> receiver;
        private final int notificationId;
        private CharSequence title;
        private CharSequence message;
        private CharSequence ticker;
        private CharSequence subtext;
        private int iconId = R.drawable.ic_test;
        private boolean autoCancel = true;
        private boolean onGoing = false;
        private LedColor ledColor = null;
        private int flags = 0;
        private long when = 0;
        private int onMs = 1000;
        private int offMs = 750;
        private int ledBrightnessPercent = 100;
        private boolean vibration = true;
        private boolean sound = true;
        private boolean onlyOnce;


        /**
         * Instantiates a new Builder.
         *
         * @param context        the context
         * @param receiver       the receiver
         * @param notificationId the notification id
         */
        public Builder(Context context, Class<?> receiver, int notificationId) {
            this.context = context;
            this.receiver = receiver;
            this.notificationId = notificationId;
        }


        /**
         * Title builder.
         *
         * @param title the title
         * @return the builder
         */
        public Builder title(CharSequence title) {
            this.title = title;
            return this;
        }


        /**
         * Ticker builder.
         *
         * @param ticker the ticker
         * @return the builder
         */
        public Builder ticker(CharSequence ticker) {
            this.ticker = ticker;
            return this;
        }


        /**
         * Subtext builder.
         *
         * @param subtext the subtext
         * @return the builder
         */
        public Builder subtext(CharSequence subtext) {
            this.subtext = subtext;
            return this;
        }


        /**
         * Message builder.
         *
         * @param message the message
         * @return the builder
         */
        public Builder message(CharSequence message) {
            this.message = message;
            return this;
        }


        /**
         * Icon builder.
         *
         * @param iconId the icon id
         * @return the builder
         */
        public Builder icon(int iconId) {
            this.iconId = iconId;
            return this;
        }


        /**
         * Dismissable builder.
         *
         * @param dismissable the dismissable
         * @return the builder
         */
        public Builder dismissable(boolean dismissable) {
            this.onGoing = !dismissable;
            return this;
        }


        /**
         * Auto cancel builder.
         *
         * @param autoCancel the auto cancel
         * @return the builder
         */
        public Builder autoCancel(boolean autoCancel) {
            this.autoCancel = autoCancel;
            return this;
        }


        /**
         * Sound builder.
         *
         * @param sound the sound
         * @return the builder
         */
        public Builder sound(boolean sound) {
            if (sound) this.flags |= Notification.DEFAULT_SOUND;
            else this.flags ^= Notification.DEFAULT_SOUND;

            this.sound = sound;
            return this;
        }


        /**
         * Vibration builder.
         *
         * @param vibration the vibration
         * @return the builder
         */
        public Builder vibration(boolean vibration) {
            if (vibration) this.flags |= Notification.DEFAULT_VIBRATE;
            else this.flags ^= Notification.DEFAULT_VIBRATE;

            this.vibration = vibration;
            return this;
        }


        /**
         * Led color builder.
         *
         * @param ledColor the led color
         * @return the builder
         */
        public Builder ledColor(LedColor ledColor) {
            if (ledColor != null) this.flags |= Notification.FLAG_SHOW_LIGHTS;
            else this.flags ^= Notification.FLAG_SHOW_LIGHTS;
            this.ledColor = ledColor;
            return this;
        }


        /**
         * Led duration builder.
         *
         * @param onMs  the on ms
         * @param offMs the off ms
         * @return the builder
         */
        public Builder ledDuration(int onMs, int offMs) {
            this.onMs = onMs;
            this.offMs = offMs;
            return this;
        }


        /**
         * Led brightness builder.
         *
         * @param percent the percent
         * @return the builder
         */
        public Builder ledBrightness(int percent) {
            this.ledBrightnessPercent = percent;
            return this;
        }


        /**
         * When builder.
         *
         * @param when the when
         * @return the builder
         */
        public Builder when(long when) {
            this.when = when;
            return this;
        }


        /**
         * Sets only once.
         *
         * @param onlyOnce the only once
         * @return the only once
         */
        public Builder setOnlyOnce(boolean onlyOnce) {
            this.onlyOnce = onlyOnce;
            return this;
        }


        /**
         * Build builder.
         *
         * @return the builder
         */
        public Builder build() {
            return this;
        }


        /**
         * Fire.
         */
        public void fire() {
            new NotificationUtils(this);
        }
    }

}
