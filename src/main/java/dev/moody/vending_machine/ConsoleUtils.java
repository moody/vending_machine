package dev.moody.vending_machine;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public final class ConsoleUtils {
  private static final Scanner scanner = new Scanner(System.in);

  /**
   * Prompts the user for input.
   * 
   * @param prompt the message to display
   * @return the input <code>String</code> after trimming
   */
  public static String getInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  /**
   * Prompts the user to input a value in USD format.
   * 
   * @param prompt the message to display
   * @return the input value after conversion to type <code>double</code>
   */
  public static double getUsd(String prompt) {
    Number usd = null;
    while (usd == null) {
      String value = getInput(prompt);
      if (!value.startsWith("$")) {
        value = "$" + value;
      }
      try {
        usd = NumberFormat.getCurrencyInstance(Locale.US).parse(value);
      } catch (Exception e) {
        System.out.println("\nEnter an amount in USD.\n");
      }
    }
    return usd.doubleValue();
  }

  /**
   * Prompts the user with a yes-no question.
   * 
   * @param question     the message to display
   * @param defaultValue the value to return on empty input
   * @return a <code>boolean</code> based on user input
   */
  public static boolean getYesNo(String question, boolean defaultValue) {
    String y = defaultValue ? "Y" : "y";
    String n = !defaultValue ? "N" : "n";
    question = String.format("%s (%s/%s): ", question, y, n);

    Boolean result = null;
    while (result == null) {
      String input = getInput(question).toLowerCase();
      switch (input) {
        case "":
          result = defaultValue;
          break;
        case "yes":
        case "y":
          result = true;
          break;
        case "no":
        case "n":
          result = false;
          break;
        default:
          System.out.println("\nEnter 'y' or 'n'.\n");
          break;
      }
    }
    return result.booleanValue();
  }
}
