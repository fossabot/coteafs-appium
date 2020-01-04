/*
 * Copyright (c) 2017, Wasiq Bhamla.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wasiqb.coteafs.appium.config.android;

import lombok.Getter;

/**
 * @author wasiq.bhamla
 * @since Jan 18, 2018 9:05:52 PM
 */
@Getter
public class AndroidDeviceSetting {
    private int     adbPort;
    private long    apkInstallTimeout;
    private String  appActivity;
    private String  appPackage;
    private String  appWaitActivity;
    private String  appWaitPackage;
    private long    appWaitTimeout;
    private boolean autoGrantPermissions;
    private String  avd;
    private       String avdArgs;
    private final long   avdLaunchTimeout;
    private final long   avdReadyTimeout;
    private       String chromeDriverPath;
    private int     deviceReadyTimeout;
    private int     systemPort;

    /**
     * @author wasiqb
     * @since Oct 30, 2018
     */
    public AndroidDeviceSetting() {
        this.avdLaunchTimeout = 60000;
        this.avdReadyTimeout = 60000;
    }
}