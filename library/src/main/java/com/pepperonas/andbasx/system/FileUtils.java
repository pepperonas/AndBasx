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

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.pepperonas.andbasx.base.Utils;
import com.pepperonas.jbasx.compressor.FileCompressor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

/**
 * The type File utils.
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    /**
     * Backup database.
     *
     * @param context             the context
     * @param override            the override
     * @param compress            the compress
     * @param databaseName        the database name
     * @param backupDirectoryName the backup directory name
     * @throws IOException the io exception
     */
    public static void backupDatabase(Context context, boolean override, boolean compress, String databaseName, String backupDirectoryName) throws IOException {
        long start = System.currentTimeMillis();

        File backupDb = null;
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDbPath = "//data//" + context.getPackageName() + "//databases//" + databaseName;
                File currentDb = new File(data, currentDbPath);
                File backupDbDir = new File(sd, backupDirectoryName);

                if (!backupDbDir.exists()) {
                    boolean created = backupDbDir.mkdirs();
                    Log.i(TAG, "backupDatabase: new directory " + (created ? "created." : "not created."));
                }

                if (currentDb.exists()) {
                    backupDb = new File(backupDbDir + "/" + (override ? "" : Utils.getReadableTimeStamp()) + databaseName);
                    if (!backupDb.exists()) {
                        boolean created = backupDb.createNewFile();
                        Log.i(TAG, "backupDatabase: new file " + (created ? "created." : "not created."));
                    }

                    FileChannel src = new FileInputStream(currentDb).getChannel();
                    FileChannel dst = new FileOutputStream(backupDb).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                Log.d(TAG, "backupDatabase: Database copied.");
            }
        } catch (Exception e) {
            Log.e(TAG, "backupDatabase: Error while copying database.", e);
        }

        if (!compress) return;

        if (backupDb != null) {
            File dir = new File(Environment.getExternalStorageDirectory() + "/" + backupDirectoryName);
            String fileName = "database";

            if (!dir.exists()) {
                Log.i(TAG, "backupDatabase: new dir created=" + (dir.mkdirs()));
            }

            File zipped = new File(dir, fileName + ".zip");
            if (!zipped.exists()) {
                Log.i(TAG, "backupDatabase: new archive created=" + (zipped.createNewFile()));
            }
            FileCompressor compressor = new FileCompressor(new String[]{backupDb.getPath()},
                    zipped.getPath());

            if (compressor.zip()) {
                Log.i(TAG, "backupDatabase: compressed=true | deleted=" + backupDb.delete());
            } else {
                Log.w(TAG, "backupDatabase: Compression failed.");
            }

        } else {
            Log.w(TAG, "backupDatabase: Can't compress database - no backup found.");
        }

        Log.i(TAG, "backupDatabase: took " + (System.currentTimeMillis() - start) + " ms.");
    }


    /**
     * Restore database.
     *
     * @param context             the context
     * @param databaseName        the database name
     * @param backupDirectoryName the backup directory name
     * @param backupDatabaseName  the backup database name
     */
    public static void restoreDatabase(Context context, String databaseName, String backupDirectoryName, String backupDatabaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String backupDbPath = backupDirectoryName + "/" + backupDatabaseName;
                File backupDbDir = new File(sd, backupDbPath);
                String appsDbPath = "//data//" + context.getPackageName() + "//databases//" + databaseName;
                File appsDb = new File(data, appsDbPath);

                FileChannel src = new FileInputStream(backupDbDir).getChannel();
                FileChannel dst = new FileOutputStream(appsDb).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Log.d(TAG, "restoreDatabase: Database restored.");
            }
        } catch (IOException e) {
            Log.e(TAG, "restoreDatabase: Error while restoring database.");
        }
    }

    /**
     * Write text file boolean.
     *
     * @param dirName  the dir name
     * @param fileName the file name
     * @param override the override
     * @param content  the content
     * @return the boolean
     */
    public static boolean writeTextFile(String dirName, String fileName, boolean override, String content) {
        String ts = Utils.getReadableTimeStamp();

        File dir = new File(Environment.getExternalStorageDirectory() + "/" + dirName);
        Log.i(TAG, "writeTextFile: new directory " + (dir.mkdirs() ? "created." : "not created."));

        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(new File(dir, (override ? "" : ts) + fileName + ".txt"), true));
            printWriter.print(content);
            printWriter.flush();
            printWriter.close();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "writeTextFile: ", e);
            return false;
        }
    }

    /**
     * Write text file boolean.
     *
     * @param dirName  the dir name
     * @param fileName the file name
     * @param override the override
     * @param content  the content
     * @return the boolean
     */
    public static boolean writeTextFile(String dirName, String fileName, boolean override, byte[] content) {
        String ts = Utils.getReadableTimeStamp();

        File dir = new File(Environment.getExternalStorageDirectory() + "/" + dirName);
        Log.i(TAG, "writeTextFile: new directory " + (dir.mkdirs() ? "created." : "not created."));

        FileOutputStream stream;
        try {
            stream = new FileOutputStream(new File(dir, (override ? "" : ts) + fileName + ".txt"), true);
            stream.write(content);
            stream.close();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "writeTextFile: ", e);
            return false;
        }
    }


    /**
     * Read text file string.
     *
     * @param dirName  the dir name
     * @param fileName the file name
     * @return the string
     */
    public static String readTextFile(String dirName, String fileName) {
        String content = "";
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + dirName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dir.getPath() + "/" + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = null;
            if (br != null) {
                line = br.readLine();
            }
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            content = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

}
