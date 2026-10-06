package com.bank.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BankingLogger {

    private static final Logger logger =
            Logger.getLogger("BankingSystem");

    static {

        try {

            FileHandler fileHandler =
                    new FileHandler("banking.log", true);

            fileHandler.setFormatter(
                    new SimpleFormatter());

            logger.addHandler(fileHandler);

            logger.setLevel(Level.INFO);

        } catch (IOException e) {

            System.out.println(
                    "Logger setup failed: " + e.getMessage());
        }
    }

    private BankingLogger() {
    }

    public static void info(String message) {

        logger.info(message);
    }

    public static void warning(String message) {

        logger.warning(message);
    }

    public static void error(String message) {

        logger.severe(message);
    }
}