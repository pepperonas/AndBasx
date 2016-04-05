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

package com.pepperonas.andbasx;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.BuildConfig;
import android.util.Log;

import com.pepperonas.jbasx.Jbasx;

import java.io.File;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class AndBasx {

    private static final String TAG = "AndBasx";

    private static String mLogFileName = "andbasx.log";

    private static Context mCtx;


    public enum LogMode {
        NONE(-1), DEFAULT(0), ALL(3);

        private final int mode;


        LogMode(int i) {this.mode = i;}
    }


    public static LogMode mLog = LogMode.DEFAULT;


    /**
     * Initializes the library with the app's {@link Context}.
     */
    public static void init(Context context) {
        mCtx = context;
    }


    /**
     * Initializes the library with the app's {@link Context}.
     *
     * @see LogMode
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
     * Set the log behaviour.
     *
     * @see LogMode
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
     * Writes a log-file in the app's data-directory.
     *
     * @param fileName     Name of the log-file.
     * @param setTimeStamp Whenever a timestamp should be set or not.
     */
    public static void storeLogFileInAppsCacheDir(String fileName, boolean setTimeStamp) {
        AndBasx.setLog(LogMode.ALL);

        File logFile = AndBasx.getContext().getExternalCacheDir();
        if (logFile != null) {
            Jbasx.setLogWriter(logFile.getAbsolutePath(), fileName, setTimeStamp);
        } else {
            if (AndBasx.mLog == LogMode.ALL || AndBasx.mLog == LogMode.DEFAULT) {
                com.pepperonas.jbasx.log.Log.e(TAG, "storeLogFileInAppsDataDir - failed (LogFile does not exist).");
            }
        }
    }



    /**
     * Writes a log-file in the app's data-directory.
     */
    public static void storeLogFileInAppsCacheDir() {
        AndBasx.setLog(LogMode.ALL);

        File logFile = AndBasx.getContext().getExternalCacheDir();
        if (logFile != null) {
            Jbasx.setLogWriter(logFile.getAbsolutePath(), mLogFileName, false);
        } else {
            if (AndBasx.mLog == LogMode.ALL || AndBasx.mLog == LogMode.DEFAULT) {
                com.pepperonas.jbasx.log.Log.e(TAG, "storeLogFileInAppsDataDir - failed (LogFile does not exist).");
            }
        }
    }


    public static Context getContext() {
        return mCtx;
    }


    public static class Version {

        /**
         * Show the current version of the library.
         */
        public static void showVersionInfo() {
            Log.i(TAG, versionInfo());
        }


        /**
         * @return <p>---ANDBASX---<br>
         * andbasx-{@value BuildConfig#VERSION_NAME}</p>
         */
        public static String getVersionInfo() {
            return "---ANDBASX---\n" +
                   versionInfo();
        }


        /**
         * @return '{@value BuildConfig#VERSION_CODE}'
         */
        public static int versionCode() { return BuildConfig.VERSION_CODE; }


        /**
         * @return '{@value BuildConfig#VERSION_NAME}'
         */
        public static String versionName() { return BuildConfig.VERSION_NAME; }


        /**
         * @return 'andbasx-{@value BuildConfig#VERSION_NAME}'
         */
        public static String versionInfo() {
            return "andbasx-" + versionName() + ".aar";
        }


        /**
         * @return The license text.
         */
        public static String getLicense() {
            return "Copyright (c) 2016 Martin Pfeffer\n" +
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
