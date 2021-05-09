package dev.moody.vending_machine;

import java.nio.file.Files;
import java.nio.file.Path;

public class Program {
  private VendingMachine machine;
  private boolean isRunning;

  public static void main(String[] args) {
    Program program = new Program();
    program.run();
  }

  public void run() {
    AuditLogger.start();
    System.out.println("\n-- Vending Machine\n");
    displayHelp();

    // Main loop. Process input until user quits.
    isRunning = true;
    while (isRunning) {
      System.out.println();
      String input = ConsoleUtils.getInput("Enter a command: ");
      System.out.println();
      processInput(input);
    }

    System.out.println("Shutting down...\n");
    AuditLogger.stop();
  }

  private void processInput(String input) {
    String[] args = input.split(" ");
    switch (args[0].toLowerCase()) {
      // Load items.
      case "load":
      case "l":
        loadData(args);
        break;
      // View items.
      case "view":
      case "v":
        displayItems();
        break;
      // Buy item.
      case "buy":
      case "b":
        buyItem(args);
        break;
      // Help.
      case "help":
      case "h":
        displayHelp();
        break;
      // Quit.
      case "quit":
      case "q":
        isRunning = false;
        break;
      default:
        System.out.println("Unrecognized command.");
        break;
    }
  }

  private void loadData(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: `load [path]`");
      return;
    }

    try {
      Path filePath = Path.of(args[1]);
      if (!filePath.toFile().exists()) {
        System.out.println("File does not exist.");
        return;
      }

      machine = VendingMachine.fromJson(Files.readString(filePath));
      AuditLogger.jsonDataLoaded(machine);

      System.out.println("Data loaded successfully.\n");
      displayItems();
    } catch (Exception e) {
      System.out.println("Failed to load data.");
    }
  }

  private void displayItems() {
    if (machine == null) {
      System.out.println("Item data has not been loaded.");
    } else if (machine.items.size() == 0) {
      System.out.println("No items.");
    } else {
      System.out.println(TableUtils.getItemsTable(machine));
    }
  }

  private void buyItem(String[] args) {
    if (machine == null) {
      System.out.println("Item data has not been loaded.");
      return;
    }

    if (args.length < 2) {
      System.out.println("Usage: `buy [id]`");
      return;
    }

    // Get item.
    String id = args[1];
    VendingItem item = machine.getItem(id);

    if (item == null) {
      System.out.println("Item not found.");
      return;
    }

    // Display selected item.
    System.out.println(TableUtils.getItemTable(id, item) + "\n");

    // Ensure item is in stock.
    if (item.amount > 0) {
      // Confirm the user's selection.
      boolean purchase = ConsoleUtils.getYesNo("Continue buying this item?", false);
      if (!purchase)
        return;

      // Log selection.
      AuditLogger.itemSelected(item);

      // Get payment amount from user and calculate change.
      double payment = ConsoleUtils.getUsd("Enter payment (USD): ");
      double change = payment - item.price;

      // Log payment.
      AuditLogger.paymentReceived(payment);

      // Ensure payment amount covers cost of item.
      if (change < 0) {
        // Log refund.
        System.out.format("\nInsufficient funds. Refunding %s...\n", CurrencyUtils.format(payment));
        AuditLogger.paymentRefunded(payment);
      } else {
        // Dispense the item.
        if (machine.dispense(id)) {
          // Display transaction details.
          System.out.println("\nThank you for your purchase.\n");
          System.out.println(TableUtils.getTransactionTable(item.price, payment, change));

          // Log dispensed item.
          System.out.format("\nDispensing %s...\n", item.name);
          AuditLogger.itemDispensed(item);

          // Dispense change if necessary.
          if (change > 0) {
            System.out.format("Dispensing %s change...\n", CurrencyUtils.format(change));
            AuditLogger.changeDispensed(change);
          }
        } else {
          // Log refund.
          System.out.format("Failed to dispense item. Refunding %s...\n", CurrencyUtils.format(payment));
          AuditLogger.paymentRefunded(payment);
        }
      }
    } else {
      System.out.println("Item out of stock.");
    }
  }

  private static void displayHelp() {
    System.out.println(TableUtils.getHelpTable());
  }
}
