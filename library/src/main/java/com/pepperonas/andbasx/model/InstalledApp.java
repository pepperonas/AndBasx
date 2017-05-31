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

package com.pepperonas.andbasx.model;

import android.content.pm.ApplicationInfo;

/**
 * The type Installed app.
 */
public class InstalledApp {

    private ApplicationInfo applicationInfo;

    private String applicationName;


    /**
     * Instantiates a new Installed app.
     *
     * @param applicationInfo the application info
     * @param applicationName the application name
     */
    public InstalledApp(ApplicationInfo applicationInfo, String applicationName) {
        this.applicationInfo = applicationInfo;
        this.applicationName = applicationName;
    }


    /**
     * Gets application info.
     *
     * @return the application info
     */
    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }


    /**
     * Sets application info.
     *
     * @param applicationInfo the application info
     */
    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }


    /**
     * Gets application name.
     *
     * @return the application name
     */
    public String getApplicationName() {
        return applicationName;
    }


    /**
     * Sets application name.
     *
     * @param applicationName the application name
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
