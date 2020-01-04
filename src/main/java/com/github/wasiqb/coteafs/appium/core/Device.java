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

import static com.github.wasiqb.coteafs.appium.config.ConfigUtil.getDevice;
import static com.github.wasiqb.coteafs.appium.constants.ErrorMessage.SERVER_STOPPED;
import static com.github.wasiqb.coteafs.appium.device.ParallelDeviceSession.close;
import static com.github.wasiqb.coteafs.appium.device.ParallelDeviceSession.getSession;
import static com.github.wasiqb.coteafs.appium.device.ParallelDeviceSession.setSession;
import static com.github.wasiqb.coteafs.appium.utils.CapabilityUtils.setCapability;
import static com.github.wasiqb.coteafs.error.util.ErrorUtil.fail;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.ADB_PORT;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.ANDROID_INSTALL_TIMEOUT;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_WAIT_ACTIVITY;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_WAIT_DURATION;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_WAIT_PACKAGE;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AVD;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AVD_ARGS;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AVD_READY_TIMEOUT;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.SYSTEM_PORT;
import static io.appium.java_client.remote.IOSMobileCapabilityType.APP_NAME;
import static io.appium.java_client.remote.IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS;
import static io.appium.java_client.remote.IOSMobileCapabilityType.AUTO_DISMISS_ALERTS;
import static io.appium.java_client.remote.IOSMobileCapabilityType.BUNDLE_ID;
import static io.appium.java_client.remote.IOSMobileCapabilityType.LAUNCH_TIMEOUT;
import static io.appium.java_client.remote.IOSMobileCapabilityType.SHOW_XCODE_LOG;
import static io.appium.java_client.remote.IOSMobileCapabilityType.UPDATE_WDA_BUNDLEID;
import static io.appium.java_client.remote.IOSMobileCapabilityType.USE_NEW_WDA;
import static io.appium.java_client.remote.IOSMobileCapabilityType.USE_PREBUILT_WDA;
import static io.appium.java_client.remote.IOSMobileCapabilityType.WDA_CONNECTION_TIMEOUT;
import static io.appium.java_client.remote.IOSMobileCapabilityType.WDA_LOCAL_PORT;
import static io.appium.java_client.remote.IOSMobileCapabilityType.WDA_STARTUP_RETRIES;
import static io.appium.java_client.remote.IOSMobileCapabilityType.WDA_STARTUP_RETRY_INTERVAL;
import static io.appium.java_client.remote.IOSMobileCapabilityType.XCODE_ORG_ID;
import static io.appium.java_client.remote.IOSMobileCapabilityType.XCODE_SIGNING_ID;
import static io.appium.java_client.remote.MobileCapabilityType.APP;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.CLEAR_SYSTEM_FILES;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.EVENT_TIMINGS;
import static io.appium.java_client.remote.MobileCapabilityType.FULL_RESET;
import static io.appium.java_client.remote.MobileCapabilityType.NEW_COMMAND_TIMEOUT;
import static io.appium.java_client.remote.MobileCapabilityType.NO_RESET;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;
import static io.appium.java_client.remote.MobileCapabilityType.UDID;
import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import com.github.wasiqb.coteafs.appium.config.DeviceSetting;
import com.github.wasiqb.coteafs.appium.config.android.AndroidDeviceSetting;
import com.github.wasiqb.coteafs.appium.config.enums.DeviceType;
import com.github.wasiqb.coteafs.appium.config.ios.IOSDeviceSetting;
import com.github.wasiqb.coteafs.appium.device.DeviceSession;
import com.github.wasiqb.coteafs.appium.error.AppiumServerStoppedError;
import com.github.wasiqb.coteafs.appium.error.DeviceAppNotFoundError;
import com.github.wasiqb.coteafs.appium.error.DeviceDesiredCapabilitiesNotSetError;
import com.github.wasiqb.coteafs.appium.error.DeviceDriverDefaultWaitError;
import com.github.wasiqb.coteafs.appium.error.DeviceDriverInitializationFailedError;
import com.github.wasiqb.coteafs.appium.error.DeviceDriverNotStartingError;
import com.github.wasiqb.coteafs.appium.error.DeviceDriverNotStoppingError;
import com.github.wasiqb.coteafs.appium.service.AppiumServer;
import com.github.wasiqb.coteafs.logger.Loggy;
import com.github.wasiqb.coteafs.selenium.core.driver.IDriver;
import com.github.wasiqb.coteafs.selenium.core.driver.IDriverActions;
import com.github.wasiqb.coteafs.selenium.core.driver.IPlatformAction;
import com.github.wasiqb.coteafs.selenium.core.enums.ApplicationType;
import com.github.wasiqb.coteafs.selenium.core.enums.Platform;
import com.github.wasiqb.coteafs.selenium.core.enums.PlatformOs;
import com.google.common.reflect.TypeToken;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class Device<D extends AppiumDriver<MobileElement>>
    implements IDriver<D>, IPlatformAction {
    private static final Loggy               LOG = Loggy.init();
    private              DesiredCapabilities capabilities;
    private final        AppiumServer        server;
    private final        DeviceSetting       setting;

    public Device(final AppiumServer server, final String name) {
        this.server = server;
        this.setting = getDevice(name);
        buildCapabilities();
    }

    @Override
    public ApplicationType getApplicationType() {
        return this.setting.getAppType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public D getDriver() {
        return (D) getSession().getDriver();
    }

    @Override
    public Platform getPlatform() {
        return this.setting.getPlatform();
    }

    @Override
    public PlatformOs getPlatformOs() {
        return this.setting.getPlatformOs();
    }

    public DeviceSetting getSetting() {
        return this.setting;
    }

    @Override
    public boolean isRunning() {
        return getDriver().getSessionId() != null;
    }

    // TODO
    @Override
    public <T extends IDriverActions<D>> T perform() {
        return null;
    }

    @Override
    public void start() {
        startDriver();
        setImplicitWait();
    }

    @Override
    public void stop() {
        if (getDriver() != null) {
            quitApp();
            getSession().close();
            close();
        } else {
            LOG.t("[{}] device driver already stopped...", getPlatform());
        }
    }

    private void buildCapabilities() {
        LOG.t("Building Device capabilities...");
        this.capabilities = new DesiredCapabilities();

        setCommonCapabilities();
        setDeviceSpecificCapabilities();

        if (this.setting.getAppType() == ApplicationType.WEB) {
            setCapability(BROWSER_NAME, this.setting.getBrowser(), this.capabilities, true);
        } else {
            String appPath = this.setting.getAppLocation();
            if (appPath != null && !this.setting.isCloudApp()) {
                String path = "%s/src/test/resources/%s";
                path = format(path, getProperty("user.dir"), appPath);

                if (this.setting.isExternalApp()) {
                    path = appPath;
                }
                final File file = new File(path);
                if (!file.exists()) {
                    final String msg = format("App not found on mentioned location [%s]...", path);
                    LOG.e(msg);
                    fail(DeviceAppNotFoundError.class, msg);
                }
                appPath = path;
            }
            if (this.setting.isCloudApp() || appPath != null) {
                setCapability(APP, appPath, this.capabilities, true);
            }
        }
        LOG.t("Building Device capabilities completed...");
    }

    @SuppressWarnings( { "UnstableApiUsage", "unchecked", "rawtypes" })
    private D init(final URL url, final Capabilities capability) {
        final TypeToken<? extends Device> token = TypeToken.of(getClass());
        final Class<D> cls = (Class<D>) token.getRawType();
        final Class<?>[] argTypes = new Class<?>[] { URL.class, Capabilities.class };
        try {
            final Constructor<D> ctor = cls.getDeclaredConstructor(argTypes);
            return ctor.newInstance(url, capability);
        } catch (final NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail(DeviceDriverInitializationFailedError.class,
                "Error occured while initializing device driver.", e);
        }
        return null;
    }

    private void quitApp() {
        LOG.t("Closing & Quitting [{}] device driver...", getPlatform());
        try {
            getDriver().closeApp();
        } catch (final NoSuchSessionException e) {
            fail(AppiumServerStoppedError.class, SERVER_STOPPED, e);
        } catch (final Exception e) {
            fail(DeviceDriverNotStoppingError.class, "Error occured while stopping device driver.",
                e);
        }
    }

    private void setAndroidCapabilities() {
        final AndroidDeviceSetting android = this.setting.getAndroid();
        if (android != null) {
            if (this.setting.getDeviceType() == DeviceType.SIMULATOR) {
                setCapability(AVD, android.getAvd(), this.capabilities, true);
                setCapability(AVD_READY_TIMEOUT, SECONDS.toMillis(android.getAvdReadyTimeout()),
                    this.capabilities);
                setCapability(AVD_LAUNCH_TIMEOUT, SECONDS.toMillis(android.getAvdLaunchTimeout()),
                    this.capabilities);
                setCapability(AVD_ARGS, android.getAvdArgs(), this.capabilities);
            }
            if (this.setting.getAppType() != ApplicationType.WEB) {
                final String packageName = android.getAppPackage();
                final String app = this.setting.getAppLocation();
                if (packageName == null && app == null) {
                    fail(DeviceDesiredCapabilitiesNotSetError.class,
                        "Either App or Package name is mandatory...");
                }
                setCapability(APP_ACTIVITY, android.getAppActivity(), this.capabilities);
                setCapability(APP_PACKAGE, android.getAppPackage(), this.capabilities);
                setCapability(APP_WAIT_ACTIVITY, android.getAppWaitActivity(), this.capabilities);
                setCapability(APP_WAIT_DURATION, android.getAppWaitTimeout(), this.capabilities);
                setCapability(APP_WAIT_PACKAGE, android.getAppWaitPackage(), this.capabilities);
                setCapability(ANDROID_INSTALL_TIMEOUT, android.getApkInstallTimeout(),
                    this.capabilities);
                setCapability(AUTO_GRANT_PERMISSIONS, android.isAutoGrantPermissions(),
                    this.capabilities);
            } else {
                setCapability(CHROMEDRIVER_EXECUTABLE, android.getChromeDriverPath(),
                    this.capabilities);
            }
            setCapability(SYSTEM_PORT, android.getSystemPort(), this.capabilities);
            setCapability(ADB_PORT, android.getAdbPort(), this.capabilities);
            setCapability(DEVICE_READY_TIMEOUT, android.getDeviceReadyTimeout(), this.capabilities);
        }
    }

    private void setCommonCapabilities() {
        setCapability(DEVICE_NAME, this.setting.getDeviceName(), this.capabilities, true);
        setCapability(PLATFORM_NAME, this.setting.getPlatformOs(), this.capabilities, true);
        setCapability(PLATFORM_VERSION, this.setting.getDeviceVersion(), this.capabilities);
        setCapability(NO_RESET, this.setting.isNoReset(), this.capabilities);
        setCapability(FULL_RESET, this.setting.isFullReset(), this.capabilities);
        setCapability(NEW_COMMAND_TIMEOUT, this.setting.getSessionTimeout(), this.capabilities);
        setCapability(CLEAR_SYSTEM_FILES, this.setting.isClearSystemFiles(), this.capabilities);
        setCapability(AUTOMATION_NAME, this.setting.getAutomationName(), this.capabilities, true);
        setCapability(UDID, this.setting.getUdid(), this.capabilities);
        setCapability(EVENT_TIMINGS, this.setting.isEventTimings(), this.capabilities);
    }

    private void setDeviceSpecificCapabilities() {
        switch (this.setting.getPlatformOs()) {
            case IOS:
                setIOSCapabilities();
                break;
            case ANDROID:
                setAndroidCapabilities();
                break;
            case WINDOWS:
            default:
                break;
        }
    }

    private void setIOSCapabilities() {
        final IOSDeviceSetting ios = this.setting.getIos();
        if (ios != null) {
            if (this.setting.getAppType() != ApplicationType.WEB) {
                setCapability(BUNDLE_ID, ios.getBundleId(), this.capabilities, true);
            }
            setCapability(XCODE_ORG_ID, ios.getTeamId(), this.capabilities, true);
            setCapability(XCODE_SIGNING_ID, ios.getSigningId(), this.capabilities, true);
            setCapability(APP_NAME, ios.getAppName(), this.capabilities, true);
            setCapability(WDA_CONNECTION_TIMEOUT, ios.getWdaConnectionTimeout(), this.capabilities,
                true);
            setCapability("bootstrapPath", ios.getBootstrapPath(), this.capabilities);
            setCapability("agentPath", ios.getAgentPath(), this.capabilities);
            setCapability(UPDATE_WDA_BUNDLEID, ios.getUpdatedWdaBundleId(), this.capabilities);
            setCapability(USE_NEW_WDA, ios.isUseNewWda(), this.capabilities);
            setCapability(USE_PREBUILT_WDA, ios.isUsePrebuiltWda(), this.capabilities);
            setCapability(SHOW_XCODE_LOG, ios.isShowXcodeLog(), this.capabilities);
            setCapability(WDA_STARTUP_RETRIES, ios.getWdaStartupRetries(), this.capabilities);
            setCapability(WDA_STARTUP_RETRY_INTERVAL, ios.getWdaStartupRetryInterval(),
                this.capabilities);
            setCapability(AUTO_ACCEPT_ALERTS, ios.isAutoAcceptAlerts(), this.capabilities);
            setCapability(AUTO_DISMISS_ALERTS, ios.isAutoDismissAlerts(), this.capabilities);
            setCapability(WDA_LOCAL_PORT, ios.getWdaLocalPort(), this.capabilities);
            setCapability(LAUNCH_TIMEOUT, ios.getLaunchTimeout(), this.capabilities);
        }
    }

    private void setImplicitWait() {
        try {
            getDriver().manage()
                .timeouts()
                .implicitlyWait(this.setting.getPlayback()
                    .getDefaultWait(), SECONDS);
        } catch (final NoSuchSessionException e) {
            fail(AppiumServerStoppedError.class, SERVER_STOPPED, e);
        } catch (final Exception e) {
            fail(DeviceDriverDefaultWaitError.class,
                "Error occured while setting device driver default wait.", e);
        }
    }

    private void startDriver() {
        LOG.t("Starting [{}] device driver...", getPlatform());
        try {
            final D driver = init(this.server.getServiceUrl(), this.capabilities);
            setSession(new DeviceSession(driver));
        } catch (final Exception e) {
            fail(DeviceDriverNotStartingError.class, "Error occured starting device driver", e);
        }
    }
}