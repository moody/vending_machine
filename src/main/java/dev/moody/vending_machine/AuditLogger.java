package dev.moody.vending_machine;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class AuditLogger {
  private static final Logger logger;

  static {
    logger = Logger.getLogger("VendingMachine");

    try {
      FileHandler handler = new FileHandler("audit.log", true);
      SimpleFormatter formatter = new SimpleFormatter();
      handler.setFormatter(formatter);
      logger.addHandler(handler);
      logger.setUseParentHandlers(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void start() {
    logger.info("Started.");
  }

  public static void stop() {
    logger.info("Stopped.");
  }

  public static void jsonDataLoaded(VendingMachine machine) {
    StringBuilder builder = new StringBuilder("JSON data loaded.");
    builder.append("\n  rows: " + Integer.toString(machine.rows));
    builder.append("\n  columns: " + Integer.toString(machine.columns));
    builder.append("\n  items:");
    for (String key : machine.items.keySet()) {
      VendingItem item = machine.items.get(key);
      builder.append(String.format("\n    %s: %s", key, item));
    }

    logger.info(builder.toString());
  }

  public static void changeDispensed(double change) {
    logger.info("Change dispensed: " + CurrencyUtils.format(change));
  }

  public static void itemDispensed(VendingItem item) {
    logger.info("Item dispensed: " + item);
  }

  public static void itemSelected(VendingItem item) {
    logger.info("Item selected: " + item);
  }

  public static void paymentReceived(double payment) {
    logger.info("Payment received: " + CurrencyUtils.format(payment));
  }

  public static void paymentRefunded(double payment) {
    logger.info("Payment refunded: " + CurrencyUtils.format(payment));
  }
}
