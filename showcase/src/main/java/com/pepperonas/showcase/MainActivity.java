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

package com.pepperonas.showcase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.pepperonas.andbasx.concurrency.LoaderTaskUtils.Action;
import com.pepperonas.andbasx.concurrency.LoaderTaskUtils.Builder;
import com.pepperonas.andbasx.interfaces.LoaderTaskListener;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView iv = (ImageView) findViewById(R.id.imageView);

        new Builder(this, new LoaderTaskListener() {
            @Override
            public void onLoaderTaskSuccess(Action action, String msg) {

            }

            @Override
            public void onLoaderTaskFailed(Action action, String msg) {

            }

            @Override
            public void onLoaderTaskSuccess(Action action, final InputStream inputStream) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                        iv.setImageBitmap(bmp);
                    }
                });
            }

            @Override
            public void onLoaderTaskFailed(Action action, InputStream inputStream) {

            }
        }, "https://celox.io/file/ic_launcher-1.png")
            .launch();
    }
}
