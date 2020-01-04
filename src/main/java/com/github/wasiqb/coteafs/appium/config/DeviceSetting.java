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

import com.github.wasiqb.coteafs.appium.config.android.AndroidDeviceSetting;
import com.github.wasiqb.coteafs.appium.config.enums.AutomationType;
import com.github.wasiqb.coteafs.appium.config.enums.Browser;
import com.github.wasiqb.coteafs.appium.config.enums.DeviceType;
import com.github.wasiqb.coteafs.appium.config.ios.IOSDeviceSetting;
import com.github.wasiqb.coteafs.selenium.core.enums.ApplicationType;
import com.github.wasiqb.coteafs.selenium.core.enums.Platform;
import com.github.wasiqb.coteafs.selenium.core.enums.PlatformOs;
import lombok.Getter;

/**
 * @author wasiq.bhamla
 * @since 12-Apr-2017 8:34:28 PM
 */
@Getter
public class DeviceSetting {
    private       AndroidDeviceSetting android;
    private       String               appLocation;
    private final ApplicationType      appType;
    private final AutomationType       automationName;
    private       Browser              browser;
    private final boolean              clearSystemFiles;
    private final boolean              cloudApp;
    private       String               deviceName;
    private final DeviceType           deviceType;
    private       String               deviceVersion;
    private       boolean              eventTimings;
    private final boolean              externalApp;
    private final boolean              fullReset;
    private       IOSDeviceSetting     ios;
    private       long                 keyTypingDelay;
    private final boolean              noReset;
    private final Platform             platform;
    private final PlatformOs           platformOs;
    private final PlaybackSetting      playback;
    private final int                  sessionTimeout;
    private       String               udid;

    /**
     * @author wasiq.bhamla
     * @since 24-Apr-2017 9:21:26 PM
     */
    public DeviceSetting() {
        this.automationName = AutomationType.APPIUM;
        this.platformOs = PlatformOs.ANDROID;
        this.appType = ApplicationType.NATIVE;
        this.deviceType = DeviceType.REAL;
        this.platform = Platform.MOBILE;
        this.noReset = false;
        this.fullReset = false;
        this.externalApp = false;
        this.clearSystemFiles = false;
        this.sessionTimeout = 120;
        this.cloudApp = false;
        this.playback = new PlaybackSetting();
    }

    /**
     * @return the appLocation
     * @author wasiq.bhamla
     * @since 12-Apr-2017 8:42:48 PM
     */
    public String getAppLocation() {
        return replaceSystemProperty(this.appLocation);
    }
}