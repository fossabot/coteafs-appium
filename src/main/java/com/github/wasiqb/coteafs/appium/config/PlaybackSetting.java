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

import static java.lang.String.format;

import lombok.Getter;

/**
 * @author wasiq.bhamla
 * @since Jan 18, 2018 9:32:14 PM
 */
@Getter
public class PlaybackSetting {
    private final int           defaultWait;
    private final int           delayAfterSwipe;
    private final int           delayAfterTap;
    private final long          delayBeforeSwipe;
    private final int           delayBeforeTap;
    private final RecordSetting record;
    private final String        screenShotPath;
    private final String        screenShotPrefix;
    private final boolean       screenshotOnError;
    private final int           waitForElementUntil;

    /**
     * @author wasiq.bhamla
     * @since Jan 18, 2018 9:34:51 PM
     */
    public PlaybackSetting() {
        this.defaultWait = 30;
        this.waitForElementUntil = 30;
        this.delayBeforeTap = 0;
        this.delayAfterTap = 0;
        this.delayBeforeSwipe = 0;
        this.delayAfterSwipe = 0;
        this.screenShotPath = format("%s/screenshots", System.getProperty("user.dir"));
        this.screenShotPrefix = "SCR";
        this.record = new RecordSetting();
        this.screenshotOnError = false;
    }
}