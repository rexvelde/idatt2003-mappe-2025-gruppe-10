package edu.ntnu.idi.idatt.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.*;

/**
 * LoggerToFile is a utility class for logging messages to a file.
 * It creates a separate log file for each class that uses it.
 * The log files are stored in the "target/logs" directory.
 * This class was created by ChatGPT.
 */
public class LoggerToFile {

  // Logger cache per class
  private static final ConcurrentHashMap<String, Logger> loggerCache = new ConcurrentHashMap<>();

  /**
   * Logs to file in target based on the class name.
   * Created by ChatGPT.
   *
   * @param clazz Klassen som logger
   * @param level Loggnivå
   * @param message Meldingen som skal logges
   */
  public static void log(Level level, String message, Class<?> clazz) {
    Logger logger = getOrCreateLogger(clazz);
    logger.log(level, message);
  }

  private static Logger getOrCreateLogger(Class<?> clazz) {
    return loggerCache.computeIfAbsent(clazz.getName(), name -> {
      try {
        String logDir = "target/logs";
        Files.createDirectories(Paths.get(logDir));

        String logFilePath = logDir + "/" + name + ".log";
        FileHandler fileHandler = new FileHandler(logFilePath, true);
        fileHandler.setFormatter(new SimpleFormatter());

        Logger logger = Logger.getLogger(name);
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);
        return logger;
      } catch (IOException e) {
        throw new RuntimeException("Could not set up logger for " + clazz.getName(), e);
      }
    });
  }
}
