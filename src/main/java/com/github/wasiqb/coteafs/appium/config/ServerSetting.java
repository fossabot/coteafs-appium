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

import static com.github.wasiqb.coteafs.appium.utils.StringUtil.replaceSystemProperty;

import java.util.HashMap;
import java.util.Map;

import com.github.wasiqb.coteafs.appium.config.enums.Protocol;
import lombok.Getter;

/**
 * @author wasiq.bhamla
 * @since 12-Apr-2017 8:43:22 PM
 */
@Getter
public class ServerSetting {
    private static int countInstance = 0;

    private       String                appiumJsPath;
    private final ServerArgumentSetting arguments;
    private final boolean               cloud;
    private final Map<String, String>   environmentVariables;
    private final boolean               external;
    private final boolean               fullReset;
    private       String                host;
    private final String                logFilePath;
    private final boolean               noReset;
    private       String                nodePath;
    private       String                password;
    private       int                   port;
    private final Protocol              protocol;
    private final int                   sessionTimeout;
    private final long                  startUpTimeOutSeconds;
    private       String                userName;

    /**
     * @author wasiq.bhamla
     * @since 08-May-2017 7:49:49 PM
     */
    public ServerSetting() {
        countInstance++;
        this.cloud = false;
        this.external = false;
        this.noReset = false;
        this.fullReset = false;
        this.sessionTimeout = 120;
        this.startUpTimeOutSeconds = 60;
        this.environmentVariables = new HashMap<>();
        this.protocol = Protocol.HTTP;
        this.logFilePath = String.format("%s/logs/server-%d.log", System.getProperty("user.dir"),
            countInstance);
        this.arguments = new ServerArgumentSetting();
    }

    /**
     * @return the password
     * @author wasiqb
     * @since Sep 29, 2018
     */
    public String getPassword() {
        return replaceSystemProperty(this.password);
    }

    /**
     * @return the userName
     * @author wasiqb
     * @since Sep 29, 2018
     */
    public String getUserName() {
        return replaceSystemProperty(this.userName);
    }
}