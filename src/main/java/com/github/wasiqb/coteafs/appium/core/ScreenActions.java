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
package com.github.wasiqb.coteafs.appium.core;

import static com.github.wasiqb.coteafs.appium.constants.ErrorMessage.SERVER_STOPPED;
import static com.github.wasiqb.coteafs.appium.utils.ScreenRecorder.saveRecording;
import static com.github.wasiqb.coteafs.error.util.ErrorUtil.fail;
import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import com.github.wasiqb.coteafs.appium.config.PlaybackSetting;
import com.github.wasiqb.coteafs.appium.config.RecordSetting;
import com.github.wasiqb.coteafs.appium.error.AppiumServerStoppedError;
import com.github.wasiqb.coteafs.logger.Loggy;
import com.github.wasiqb.coteafs.selenium.core.driver.IDriverActions;
import com.google.common.truth.StringSubject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStopScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;

public abstract class ScreenActions<D extends AppiumDriver<MobileElement>, T extends Device<D>>
    extends BaseDeviceActions<D, T> implements IDriverActions<D> {
    private static final Loggy LOG = Loggy.init();

    private static void copyFile(final File source, final String destination) {
        try {
            FileUtils.copyFile(source, new File(destination));
        } catch (final IOException e) {
            LOG.e("Error occurred while capturing screensshot...");
            LOG.c(e);
        }
    }

    ScreenActions(final T device) {
        super(device);
    }

    @Override
    public byte[] attachScreenshot() {
        return new byte[0];
    }

    @Override
    public File saveScreenshot() {
        final PlaybackSetting playbackSetting = this.setting.getPlayback();
        final String path = playbackSetting.getScreenShotPath();
        final String prefix = playbackSetting.getScreenShotPrefix();
        final SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd-HHmmss");
        final String timeStamp = date.format(Calendar.getInstance()
            .getTime());
        final String fileName = "%s/%s-%s.%s";
        return saveScreenshot(format(fileName, path, prefix, timeStamp, "jpeg"));
    }

    @Override
    public File saveScreenshot(final String path) {
        LOG.i("Capturing screenshot and saving at [{}]...", path);
        try {
            final File srcFiler = this.driver.getScreenshotAs(OutputType.FILE);
            copyFile(srcFiler, path);
            return new File(path);
        } catch (final NoSuchSessionException e) {
            fail(AppiumServerStoppedError.class, SERVER_STOPPED, e);
        }
        return null;
    }

    @Override
    public void startRecording() {
        startRecord((CanRecordScreen) driver());
    }

    @Override
    public void stopRecording() {
        stopRecord((CanRecordScreen) driver());
    }

    @Override
    public String title() {
        return null;
    }

    @Override
    public StringSubject verifyTitle() {
        return null;
    }

    protected abstract <X extends BaseStartScreenRecordingOptions<X>> X startRecordSetting();

    protected abstract <Y extends BaseStopScreenRecordingOptions<Y>> Y stopRecordSetting();

    private <X extends BaseStartScreenRecordingOptions<X>> void startRecord(
        final CanRecordScreen screen) {
        final RecordSetting record = this.setting.getPlayback()
            .getRecord();
        if (record.isEnabled() && !this.setting.isCloudApp()) {
            LOG.i("Starting video recording...");
            final X option = startRecordSetting();
            option.withTimeLimit(Duration.ofMinutes(record.getDuration()));
            screen.startRecordingScreen(option);
        }
    }

    private <X extends BaseStopScreenRecordingOptions<X>> void stopRecord(
        final CanRecordScreen screen) {
        final RecordSetting record = this.setting.getPlayback()
            .getRecord();
        if (record.isEnabled() && !this.setting.isCloudApp()) {
            LOG.i("Stopping video recording...");
            final String content = screen.<X>stopRecordingScreen(stopRecordSetting());
            saveRecording(content, record);
        }
    }
}