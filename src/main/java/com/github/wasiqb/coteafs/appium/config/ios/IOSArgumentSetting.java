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
 * @since Jan 18, 2018 8:57:52 PM
 */
@Getter
public class IOSArgumentSetting {
    private final int     backendRetries;
    private       String  ipaAbsolutePath;
    private       boolean safari;
    private final int     wdaPort;
    private       int     webkitDebugProxyPort;

    /**
     * @author wasiq.bhamla
     * @since Jan 18, 2018 8:59:34 PM
     */
    public IOSArgumentSetting() {
        this.backendRetries = 3;
        this.wdaPort = 8100;
    }
}