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

package com.pepperonas.andbasx.format;

import com.pepperonas.andbasx.system.DeviceUtils;
import com.pepperonas.jbasx.format.TimeFormatUtils;

import java.util.Date;

/**
 * The type Time format utils localized.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class TimeFormatUtilsLocalized {

    /**
     * The constant DEFAULT_FORMAT.
     */
    public static final String DEFAULT_FORMAT = TimeFormatUtils.DEFAULT_FORMAT;
    /**
     * The constant DEFAULT_FORMAT_YMD_HMS.
     */
    public static final String DEFAULT_FORMAT_YMD_HMS = TimeFormatUtils.DEFAULT_FORMAT_YMD_HMS;
    /**
     * The constant DEFAULT_FORMAT_YMD_HM.
     */
    public static final String DEFAULT_FORMAT_YMD_HM = TimeFormatUtils.DEFAULT_FORMAT_YMD_HM;
    /**
     * The constant DEFAULT_FORMAT_MD_HM.
     */
    public static final String DEFAULT_FORMAT_MD_HM = TimeFormatUtils.DEFAULT_FORMAT_MD_HM;
    /**
     * The constant DEFAULT_FORMAT_DMY_HM.
     */
    public static final String DEFAULT_FORMAT_DMY_HM = TimeFormatUtils.DEFAULT_FORMAT_DMY_HM;
    /**
     * The constant DEFAULT_FORMAT_DMY_HMS.
     */
    public static final String DEFAULT_FORMAT_DMY_HMS = TimeFormatUtils.DEFAULT_FORMAT_DMY_HMS;

    /**
     * The constant LOG_FORMAT.
     */
    public static final String LOG_FORMAT = TimeFormatUtils.LOG_FORMAT;

    /**
     * The constant UTC_FORMAT.
     */
    public static final String UTC_FORMAT = TimeFormatUtils.UTC_FORMAT;


    /**
     * To time string force show hours string.
     *
     * @param millis      the millis
     * @param showSeconds the show seconds
     * @param showUnits   the show units
     * @return the string
     */
    public static String toTimeStringForceShowHours(long millis, boolean showSeconds, boolean showUnits) {
        return TimeFormatUtils.toTimeString(millis, showSeconds, showUnits);
    }


    /**
     * To time string string.
     *
     * @param millis      the millis
     * @param showSeconds the show seconds
     * @param showUnits   the show units
     * @return the string
     */
    public static String toTimeString(long millis, boolean showSeconds, boolean showUnits) {
        return TimeFormatUtils.toTimeString(millis, showSeconds, showUnits);
    }


    /**
     * To time string string.
     *
     * @param millis         the millis
     * @param showSeconds    the show seconds
     * @param separatorHours the separator hours
     * @param separatorMin   the separator min
     * @param separatorSec   the separator sec
     * @return the string
     */
    public static String toTimeString(long millis, boolean showSeconds, String separatorHours, String separatorMin, String separatorSec) {
        return TimeFormatUtils.toTimeString(millis, showSeconds, separatorHours, separatorMin, separatorSec);
    }


    /**
     * Utc to local string.
     *
     * @param date the date
     * @return the string
     */
    public static String utcToLocal(Date date) {
        return TimeFormatUtils.utcToLocal(date, DeviceUtils.getLocale());
    }


    /**
     * Format time string.
     *
     * @param time   the time
     * @param format the format
     * @return the string
     */
    public static String formatTime(long time, String format) {
        return TimeFormatUtils.formatTime(time, format, DeviceUtils.getLocale());
    }


    /**
     * Format time string.
     *
     * @param date   the date
     * @param format the format
     * @return the string
     */
    public static String formatTime(Date date, String format) {
        return TimeFormatUtils.formatTime(date, format, DeviceUtils.getLocale());
    }


    /**
     * Format time string.
     *
     * @param timeStr   the time str
     * @param srcFormat the src format
     * @param dstFormat the dst format
     * @return the string
     */
    public static String formatTime(String timeStr, String srcFormat, String dstFormat) {
        return TimeFormatUtils.formatTime(timeStr, srcFormat, dstFormat, DeviceUtils.getLocale());
    }


    /**
     * Format time long.
     *
     * @param time   the time
     * @param format the format
     * @return the long
     */
    public static long formatTime(String time, String format) {
        return TimeFormatUtils.formatTime(time, format, DeviceUtils.getLocale());
    }


    /**
     * Use {@link TimeFormatUtils.Format}.
     *
     * @param format The stamp's format.
     * @return The formatted timestamp.
     */
    public static String getTimestamp(TimeFormatUtils.Format format) {
        return TimeFormatUtils.getTimestamp(format);
    }


    /**
     * Gets timestamp lexical.
     *
     * @param showSeconds the show seconds
     * @return the timestamp lexical
     */
    public static String getTimestampLexical(boolean showSeconds) {
        return TimeFormatUtils.getTimestampLexical(showSeconds, DeviceUtils.getLocale());
    }


    /**
     * Gets timestamp millis.
     *
     * @return the timestamp millis
     */
    public static String getTimestampMillis() {
        return TimeFormatUtils.getTimestampMillis(DeviceUtils.getLocale());
    }


    /**
     * Gets stamp.
     *
     * @return the stamp
     */
    public static String getStamp() {
        return TimeFormatUtils.getStamp();
    }


    /**
     * Use {@link TimeFormatUtils.Daytime}.
     *
     * @return The actual daytime.
     */
    public static TimeFormatUtils.Daytime getDaytime() {
        return TimeFormatUtils.getDaytime();
    }

}
