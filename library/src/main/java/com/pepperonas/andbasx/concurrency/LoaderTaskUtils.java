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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.pepperonas.andbasx.AndBasx;
import com.pepperonas.andbasx.interfaces.LoaderTaskListener;
import com.pepperonas.jbasx.io.IoUtils;
import com.pepperonas.jbasx.log.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Loader task utils.
 */
public class LoaderTaskUtils extends AsyncTask<String, String, String> {

    private static final String TAG = "LoaderTaskUtils";


    /**
     * The enum Action.
     */
    public enum Action {
        /**
         * Read action.
         */
        READ(0),
        /**
         * Resolve action.
         */
        RESOLVE(5),
        /**
         * Store file action.
         */
        STORE_FILE(10);

        /**
         * The .
         */
        int i;


        Action(int i) {
            this.i = i;
        }
    }


    private Builder builder;


    /**
     * Instantiates a new Loader task utils.
     *
     * @param builder the builder
     */
    public LoaderTaskUtils(Builder builder) {
        this.builder = builder;
        if (builder.params == null) {
            this.execute(this.builder.url);
        } else {
            String[] args = new String[this.builder.params.size() + 1];
            //            args[0] = builder.url;
            int i = 0;
            for (String s : this.builder.params) {
                args[i++] = s;
            }
            this.execute(args);
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (this.builder.progressDialog != null) {
            if (builder.showProgress) {
                this.builder.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                this.builder.progressDialog.setIndeterminate(true);
                this.builder.progressDialog.show();
            }
        }
    }


    @Override
    protected String doInBackground(String... args) {
        URL url;
        OutputStream output = null;
        InputStream is = null;

        try {
            int count;

            url = new URL(builder.url);

            Log.d(TAG, "doInBackground URL:  " + "");

            if (builder.action == Action.READ || builder.action == Action.STORE_FILE) {

                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                urlConnection.setReadTimeout(builder.readTimeout);
                urlConnection.setConnectTimeout(builder.connectionTimeout);

                is = new BufferedInputStream(url.openStream(), 8192);

                if (builder.action == Action.STORE_FILE) {

                    int length = urlConnection.getContentLength();
                    output = new FileOutputStream(
                            new File(builder.dirPath, builder.fileName + builder.extension));
                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = is.read(data)) != -1) {
                        total += count;
                        publishProgress("" + (int) ((total * 100) / length));
                        output.write(data, 0, count);
                    }

                    builder.loaderTaskListener
                            .onLoaderTaskSuccess(builder.action, "File successfully stored.");
                    return "";

                } else if (builder.action == Action.READ) {

                    if (builder.loaderTaskListener != null) {
                        builder.loaderTaskListener
                                .onLoaderTaskSuccess(builder.action, is);
                    }
                    String text = IoUtils.convertStreamToString(is);
                    builder.loaderTaskListener.onLoaderTaskSuccess(builder.action, text);
                    return "";

                }

            } else if (builder.action == Action.RESOLVE) {
                Log.i(TAG, "doInBackground " + url);
                Log.d(TAG, "doInBackground  ", args);

                int i = 0;
                String key = "";
                Map<String, Object> params = new LinkedHashMap<>();
                for (String param : args) {
                    if (i % 2 == 0) {
                        key = param;
                    } else if (i % 2 == 1) {
                        params.put(key, param);
                    }
                    i++;
                }

                Log.logHashMap(TAG, 0, params);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) {
                        postData.append('&');
                    }
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);

                StringBuilder result = new StringBuilder();
                Reader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8"));
                for (int c = in.read(); c != -1; c = in.read()) {
                    result.append((char) c);
                }

                builder.loaderTaskListener.onLoaderTaskSuccess(builder.action, result.toString());

                return "";
            }

        } catch (IOException e) {
            builder.loaderTaskListener.onLoaderTaskFailed(builder.action, "An error occurred.");
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    @Override
    protected void onProgressUpdate(String... values) {

        if (this.builder.progressDialog != null && values[0] != null) {
            this.builder.progressDialog.setProgress(Integer.parseInt(values[0]));
        }

        super.onProgressUpdate(values);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (builder.progressDialog != null) {
            builder.progressDialog.dismiss();
        }
    }


    /**
     * The type Builder.
     */
    public static class Builder {

        private final Context ctx;
        private final LoaderTaskListener loaderTaskListener;
        //        private LoaderTaskListenerInputStream loaderTaskListenerInputStream;
        private final String url;
        private int connectionTimeout = 15000;
        private int readTimeout = 10000;
        private Action action;
        private String dirPath;
        private String fileName;
        private String extension;
        private List<String> params;
        private ProgressDialog progressDialog;
        private boolean showProgress;


        /**
         * Instantiates a new Builder.
         *
         * @param context            the context
         * @param loaderTaskListener the loader task listener
         * @param url                the url
         */
        public Builder(Context context, LoaderTaskListener loaderTaskListener, String url) {
            action = Action.READ;

            this.ctx = context;
            this.loaderTaskListener = loaderTaskListener;
            this.url = url;
        }

        /**
         * Instantiates a new Builder.
         *
         * @param context                       the context
         * @param loaderTaskListener            the loader task listener
         * @param loaderTaskListenerInputStream the loader task listener input stream
         * @param url                           the url
         */
        public Builder(Context context, LoaderTaskListener loaderTaskListener,
                       LoaderTaskListener loaderTaskListenerInputStream, String url) {
            action = Action.READ;

            this.ctx = context;
            this.loaderTaskListener = loaderTaskListener;
            this.url = url;
        }


        /**
         * Instantiates a new Builder.
         *
         * @param context            the context
         * @param loaderTaskListener the loader task listener
         * @param url                the url
         * @param params             the params
         */
        public Builder(Context context, LoaderTaskListener loaderTaskListener, String url,
                       String... params) {
            action = Action.RESOLVE;
            this.params = new ArrayList<>();

            this.ctx = context;
            this.loaderTaskListener = loaderTaskListener;
            this.url = url;
            Collections.addAll(this.params, params);
        }


        /**
         * Add param builder.
         *
         * @param key   the key
         * @param value the value
         * @return the builder
         */
        public Builder addParam(String key, String value) {
            action = Action.RESOLVE;
            if (this.params == null) {
                this.params = new ArrayList<>();
            }

            params.add(key);
            params.add(value);
            return this;
        }


        /**
         * Store content builder.
         *
         * @param dirPath   the dir path
         * @param fileName  the file name
         * @param extension the extension
         * @return the builder
         */
        public Builder storeContent(String dirPath, String fileName, String extension) {
            this.action = Action.STORE_FILE;

            this.dirPath = dirPath;
            this.fileName = fileName;
            if (!extension.contains(".")) {
                extension = "." + extension;
            }
            this.extension = extension;
            return this;
        }


        /**
         * Show dialog builder.
         *
         * @param stringIdTitle   the string id title
         * @param stringIdMessage the string id message
         * @return the builder
         */
        public Builder showDialog(int stringIdTitle, int stringIdMessage) {
            return showDialog(AndBasx.getContext().getString(stringIdTitle),
                    AndBasx.getContext().getString(stringIdMessage));
        }


        /**
         * Show dialog builder.
         *
         * @param title   the title
         * @param message the message
         * @return the builder
         */
        public Builder showDialog(String title, String message) {
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            showProgress = false;
            return this;
        }


        /**
         * Show progress dialog builder.
         *
         * @param stringIdTitle   the string id title
         * @param stringIdMessage the string id message
         * @return the builder
         */
        public Builder showProgressDialog(int stringIdTitle, int stringIdMessage) {
            return showProgressDialog(AndBasx.getContext().getString(stringIdTitle),
                    AndBasx.getContext().getString(stringIdMessage));
        }


        /**
         * Show progress dialog builder.
         *
         * @param title   the title
         * @param message the message
         * @return the builder
         */
        public Builder showProgressDialog(String title, String message) {
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            showProgress = true;
            return this;
        }


        /**
         * Sets connection timeout.
         *
         * @param timeout the timeout
         * @return the connection timeout
         */
        public Builder setConnectionTimeout(int timeout) {
            connectionTimeout = timeout;
            return this;
        }


        /**
         * Sets read timeout.
         *
         * @param timeout the timeout
         * @return the read timeout
         */
        public Builder setReadTimeout(int timeout) {
            readTimeout = timeout;
            return this;
        }


        /**
         * Launch.
         */
        public void launch() {
            new LoaderTaskUtils(this);
        }

    }

}
