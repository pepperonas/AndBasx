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

package com.pepperonas.andbasx.interfaces;

import com.pepperonas.andbasx.concurrency.LoaderTaskUtils;
import java.io.InputStream;

/**
 * The interface Loader task listener.
 */
public interface LoaderTaskListener {

    /**
     * On loader task success.
     *
     * @param action the action
     * @param msg    the msg
     */
    void onLoaderTaskSuccess(LoaderTaskUtils.Action action, String msg);

    /**
     * On loader task failed.
     *
     * @param action the action
     * @param msg    the msg
     */
    void onLoaderTaskFailed(LoaderTaskUtils.Action action, String msg);

    /**
     * On loader task success.
     *
     * @param action      the action
     * @param inputStream the input stream
     */
    void onLoaderTaskSuccess(LoaderTaskUtils.Action action, InputStream inputStream);

    /**
     * On loader task failed.
     *
     * @param action      the action
     * @param inputStream the input stream
     */
    void onLoaderTaskFailed(LoaderTaskUtils.Action action, InputStream inputStream);

}
