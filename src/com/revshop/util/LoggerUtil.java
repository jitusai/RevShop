package com.revshop.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {

    private static final Logger logger = Logger.getLogger("RevShopLogger");
    private static FileHandler fileHandler;

    static {
        try {
            // Log to revshop.log, append mode
            fileHandler = new FileHandler("revshop.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message, Throwable t) {
        logger.log(Level.SEVERE, message, t);
    }
    
    public static void logWarning(String message) {
        logger.warning(message);
    }
}
