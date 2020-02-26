/*
 * Copyright 2019-2019 Gryphon Zone
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zone.gryphon.canary;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class LoggingProviderTest {

    /**
     * setup.
     */
    @BeforeClass
    public static void setUp() {
        // note: this must be run only once for *all* JUnit tests in the project, not just the ones in this class
        // (i.e. only once per JVM)
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Slf4j
    private static class Slf4jLogger {

    }

    @Log
    private static class JulLogger {

    }

    @Log4j
    private static class Log4jLogger {

    }

    @CommonsLog
    private static class CommonsLogger {

    }


    @Test
    public void testLogging() {
        Slf4jLogger.log.info("SLF4J");
        JulLogger.log.info("JUL");
        Log4jLogger.log.info("LOG4J");
        CommonsLogger.log.info("COMMONS");
    }
}
