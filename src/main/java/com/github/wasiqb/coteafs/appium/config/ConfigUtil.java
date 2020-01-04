/*
 *
 *  Copyright (c) 2019, Wasiq Bhamla.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.github.wasiqb.coteafs.appium.config;

import static com.github.wasiqb.coteafs.appium.constants.ConfigKeys.COTEAFS_CONFIG_DEFAULT_FILE;
import static com.github.wasiqb.coteafs.appium.constants.ConfigKeys.COTEAFS_CONFIG_KEY;
import static com.github.wasiqb.coteafs.config.loader.ConfigLoader.settings;

public final class ConfigUtil {
    private static final AppiumSetting SETTING;

    static {
        SETTING = settings().withKey(COTEAFS_CONFIG_KEY)
            .withDefault(COTEAFS_CONFIG_DEFAULT_FILE)
            .load(AppiumSetting.class);
    }

    public static DeviceSetting getDevice(final String name) {
        return SETTING.getDevice(name);
    }

    public static ServerSetting getServer(final String name) {
        return SETTING.getServer(name);
    }

    private ConfigUtil() {
        // Utility class.
    }
}