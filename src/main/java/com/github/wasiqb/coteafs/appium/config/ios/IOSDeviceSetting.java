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
package com.github.wasiqb.coteafs.appium.config.ios;

import lombok.Getter;

/**
 * @author wasiq.bhamla
 * @since Jan 18, 2018 9:11:11 PM
 */
@Getter
public class IOSDeviceSetting {
    private       String  agentPath;
    private       String  appName;
    private       boolean autoAcceptAlerts;
    private       boolean autoDismissAlerts;
    private       String  bootstrapPath;
    private       String  bundleId;
    private       long    launchTimeout;
    private final boolean showXcodeLog;
    private       String  signingId;
    private       String  teamId;
    private       String  updatedWdaBundleId;
    private final boolean useNewWda;
    private final boolean usePrebuiltWda;
    private       long    wdaConnectionTimeout;
    private final int     wdaLocalPort;
    private       int     wdaStartupRetries;
    private       long    wdaStartupRetryInterval;

    /**
     * @author wasiq.bhamla
     * @since Jan 18, 2018 9:34:13 PM
     */
    public IOSDeviceSetting() {
        this.showXcodeLog = false;
        this.useNewWda = false;
        this.usePrebuiltWda = false;
        this.wdaLocalPort = 8100;
    }
}