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

package com.pepperonas.andbasx.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pepperonas.andbasx.AndBasx;
import com.pepperonas.jbasx.log.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The type Bitmap utils.
 *
 * @author Martin Pfeffer (pepperonas)
 */
public class BitmapUtils {

    private static final String TAG = "BitmapUtils";


    /**
     * Save a {@link Bitmap} in the app's data directory.
     * NOTE: This directory is private to the user.
     *
     * @param bitmap   The {@link Bitmap} to store.
     * @param dirName  The name of the directory.
     * @param fileName The name of the file.
     * @return The created {@link File}.
     */
    public static File storeInDataDirectory(Bitmap bitmap, String dirName, String fileName) {
        if (fileName.contains(".")) {
            fileName = fileName.split("\\.")[0];
            Log.d(TAG, "storeInDataDirectory - Replaced file extension: " + fileName);
        }

        String dirPath = AndBasx.getContext().getFilesDir().getAbsolutePath() + File.separator + dirName;
        File path = new File(dirPath);
        if (!path.exists()) {
            boolean success = path.mkdirs();
            if (AndBasx.mLog == AndBasx.LogMode.ALL || AndBasx.mLog == AndBasx.LogMode.DEFAULT) {
                Log.d(TAG, "storeInDataDirectory - " + (success ? "File created." : "File not created."));
            }
        }

        File file = new File(path.getPath(), fileName + ".png");
        if (!file.exists()) {
            try {
                file.createNewFile();
                if (AndBasx.mLog == AndBasx.LogMode.ALL || AndBasx.mLog == AndBasx.LogMode.DEFAULT) {
                    Log.d(TAG, "saveInAppsDir - New file '" + file.getPath() + "' created.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * Load a {@link Bitmap} from the filesystem.
     *
     * @param dirPath  The path of the directory, where the {@link Bitmap} is stored.
     * @param fileName The name of the file.
     * @return The {@link Bitmap} which should be loaded.
     */
    public static Bitmap loadFromStorage(String dirPath, String fileName) {
        try {
            File f = new File(dirPath, fileName + ".png");
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
