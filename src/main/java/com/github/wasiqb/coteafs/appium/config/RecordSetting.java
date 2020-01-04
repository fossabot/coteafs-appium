/*
 * Copyright (c) 2017-2020, Wasiq Bhamla.
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

import io.appium.java_client.ios.IOSStartScreenRecordingOptions.VideoQuality;
import lombok.Getter;

/**
 * @author wasiqb
 * @since Oct 13, 2018
 */
@Getter
public class RecordSetting {
    private final int          bitRate;
    private final int          duration;
    private final boolean      enabled;
    private final String       path;
    private final String       prefix;
    private final VideoQuality quality;
    private       String       size;

    /**
     * @author wasiqb
     * @since Oct 13, 2018
     */
    public RecordSetting() {
        this.duration = 3;
        this.quality = VideoQuality.MEDIUM;
        this.enabled = false;
        this.path = format("%s/videos", System.getProperty("user.dir"));
        this.prefix = "VID";
        this.bitRate = 4;
    }
}