package org.aytidA.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    public static void logError(String message, Throwable t) {
        logger.error(message, t);
    }

    public static void logInfo(String message) {
        logger.info(message);
    }
}