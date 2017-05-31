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

package com.pepperonas.andbasx;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pepperonas.jbasx.Jbasx;

import java.io.File;

/**
 * The type And basx.
 */
public class AndBasx {

    private static final String TAG = "AndBasx";

    private static String mLogFileName = "andbasx.log";

    private static Context mCtx;


    /**
     * The enum Log mode.
     */
    public enum LogMode {
        /**
         * None log mode.
         */
        NONE(-1),
        /**
         * Default log mode.
         */
        DEFAULT(0),
        /**
         * All log mode.
         */
        ALL(3);

        private final int mode;


        LogMode(int i) {
            this.mode = i;
        }
    }


    /**
     * The constant mLog.
     */
    public static LogMode mLog = LogMode.DEFAULT;


    /**
     * Init.
     *
     * @param context the context
     */
    public static void init(Context context) {
        mCtx = context;
    }


    /**
     * Init.
     *
     * @param context the context
     * @param logMode the log mode
     */
    public static void init(Context context, @Nullable LogMode logMode) {
        mCtx = context;
        if (mLog == LogMode.ALL) {
            Jbasx.setLog(Jbasx.LogMode.ALL);
        }
        if (mLog == LogMode.DEFAULT) {
            Jbasx.setLog(Jbasx.LogMode.DEFAULT);
        }
        if (mLog == LogMode.NONE) {
            Jbasx.setLog(Jbasx.LogMode.NONE);
        }
        mLog = logMode;
    }


    /**
     * Sets log.
     *
     * @param logMode the log mode
     */
    public static void setLog(LogMode logMode) {
        if (mLog == LogMode.ALL) {
            Jbasx.setLog(Jbasx.LogMode.ALL);
        }
        if (mLog == LogMode.DEFAULT) {
            Jbasx.setLog(Jbasx.LogMode.DEFAULT);
        }
        if (mLog == LogMode.NONE) {
            Jbasx.setLog(Jbasx.LogMode.NONE);
        }
        mLog = logMode;
    }


    /**
     * Store log file in apps cache dir.
     *
     * @param fileName     the file name
     * @param setTimeStamp the set time stamp
     */
    public static void storeLogFileInAppsCacheDir(String fileName, boolean setTimeStamp) {
        AndBasx.setLog(LogMode.ALL);

        File logFile = AndBasx.getContext().getExternalCacheDir();
        if (logFile != null) {
            Jbasx.setLogWriter(logFile.getAbsolutePath(), fileName, setTimeStamp);
        } else {
            if (AndBasx.mLog == LogMode.ALL || AndBasx.mLog == LogMode.DEFAULT) {
                Log.e(TAG, "storeLogFileInAppsDataDir - failed (LogFile does not exist).");
            }
        }
    }


    /**
     * Store log file in apps cache dir.
     */
    public static void storeLogFileInAppsCacheDir() {
        AndBasx.setLog(LogMode.ALL);

        File logFile = AndBasx.getContext().getExternalCacheDir();
        if (logFile != null) {
            Jbasx.setLogWriter(logFile.getAbsolutePath(), mLogFileName, false);
        } else {
            if (AndBasx.mLog == LogMode.ALL || AndBasx.mLog == LogMode.DEFAULT) {
                Log.e(TAG, "storeLogFileInAppsDataDir - failed (LogFile does not exist).");
            }
        }
    }


    /**
     * Gets context.
     *
     * @return the context
     */
    public static Context getContext() {
        return mCtx;
    }


    /**
     * The type Version.
     */
    public static class Version {


        /**
         * Gets version name.
         *
         * @return the version name
         */
        public static String getVersionName() {
            return BuildConfig.VERSION_NAME;
        }


        /**
         * Gets version info.
         *
         * @return the version info
         */
        public static String getVersionInfo() {
            return "andbasx-" + BuildConfig.VERSION_NAME;
        }


        /**
         * Gets license.
         *
         * @return the license
         */
        public static String getLicense() {
            return "Copyright (c) 2017 Martin Pfeffer\n" +
                    " \n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    "you may not use this file except in compliance with the License.\n" +
                    "You may obtain a copy of the License at\n" +
                    " \n" +
                    "     http://www.apache.org/licenses/LICENSE-2.0\n" +
                    " \n" +
                    "Unless required by applicable law or agreed to in writing, software\n" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "See the License for the specific language governing permissions and\n" +
                    "limitations under the License.";
        }
    }

}
