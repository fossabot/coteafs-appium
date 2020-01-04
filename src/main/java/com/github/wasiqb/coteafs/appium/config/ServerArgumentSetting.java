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
package com.github.wasiqb.coteafs.appium.config;

import com.github.wasiqb.coteafs.appium.config.android.AndroidArgumentSetting;
import com.github.wasiqb.coteafs.appium.config.enums.LogLevel;
import com.github.wasiqb.coteafs.appium.config.ios.IOSArgumentSetting;
import lombok.Getter;

/**
 * @author wasiq.bhamla
 * @since Oct 27, 2017 1:26:27 PM
 */
@Getter
public class ServerArgumentSetting {
    private final AndroidArgumentSetting android;
    private       String                 callbackIp;
    private       int                    callbackPort;
    private final IOSArgumentSetting     ios;
    private       boolean                localTimeZone;
    private final LogLevel               logLevel;
    private       boolean                logTimeStamp;
    private       String                 nodeConfigFile;
    private       boolean                sessionOverride;

    /**
     * @author wasiq.bhamla
     * @since Oct 27, 2017 1:26:27 PM
     */
    public ServerArgumentSetting() {
        this.logLevel = LogLevel.ERROR;
        this.android = new AndroidArgumentSetting();
        this.ios = new IOSArgumentSetting();
    }
}