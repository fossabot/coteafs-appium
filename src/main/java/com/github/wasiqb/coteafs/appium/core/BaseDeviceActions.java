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

import static java.time.Duration.ofSeconds;

import com.github.wasiqb.coteafs.appium.config.DeviceSetting;
import com.github.wasiqb.coteafs.selenium.core.driver.IScriptAction;
import com.github.wasiqb.coteafs.selenium.core.driver.IWaitAction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseDeviceActions<D extends AppiumDriver<MobileElement>, T extends Device<D>>
    implements IWaitAction<D>, IScriptAction {
    protected       D             driver;
    protected final DeviceSetting setting;
    private final   WebDriverWait wait;

    BaseDeviceActions(final T device) {
        this.driver = device.getDriver();
        this.setting = device.getSetting();
        this.wait = new WebDriverWait(driver(), ofSeconds(this.setting.getPlayback()
            .getWaitForElementUntil()).getSeconds());
    }

    @Override
    public D driver() {
        return this.driver;
    }

    @Override
    public WebDriverWait driverWait() {
        return this.wait;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E execute(final String script, final Object... args) {
        return (E) this.driver.executeScript(script, args);
    }
}