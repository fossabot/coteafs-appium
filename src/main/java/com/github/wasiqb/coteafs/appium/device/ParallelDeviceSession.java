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
package com.github.wasiqb.coteafs.appium.device;

/**
 * @author Wasiq Bhamla
 * @since 25-Dec-2019
 */
public class ParallelDeviceSession {
    private static final ThreadLocal<DeviceSession> SESSION = new ThreadLocal<>();

    /**
     * @author Wasiq Bhamla
     * @since 25-Dec-2019
     */
    public static void close() {
        SESSION.remove();
    }

    /**
     * @return current session
     * @author Wasiq Bhamla
     * @since 25-Dec-2019
     */
    public static DeviceSession getSession() {
        return SESSION.get();
    }

    /**
     * @param session new session
     * @author Wasiq Bhamla
     * @since 25-Dec-2019
     */
    public static void setSession(final DeviceSession session) {
        SESSION.set(session);
    }

    private ParallelDeviceSession() {
        // Utility class.
    }
}