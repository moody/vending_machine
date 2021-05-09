package dev.moody.vending_machine;

import java.util.Arrays;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;

public final class TableUtils {
  /**
   * Pretty prints help info.
   * 
   * @return <code>String</code>
   */
  public static String getHelpTable() {
    return AsciiTable.getTable(
      AsciiTable.BASIC_ASCII_NO_DATA_SEPARATORS,
      new Column[] {
        new Column().header("Command"),
        new Column().header("Description").dataAlign(HorizontalAlign.LEFT)
      },
      new String[][] {
        { "load, l", "load data from a JSON file" },
        { "view, v", "view available items" },
        { "buy, b", "buy an item" },
        { "help, h", "display this message" },
        { "quit, q", "exit the program" }
      }
    );
  }

  /**
   * Pretty prints <code>VendingMachine</code> items.
   * 
   * @param machine
   * @return <code>String</code>
   */
  public static String getItemsTable(VendingMachine machine) {
    return AsciiTable.getTable(
      AsciiTable.BASIC_ASCII_NO_DATA_SEPARATORS,
      machine.items.keySet(),
      Arrays.asList(
        new Column().header("ID").with(key -> key),
        new Column().header("Name").dataAlign(HorizontalAlign.LEFT).with(key -> machine.items.get(key).name),
        new Column().header("In Stock").dataAlign(HorizontalAlign.CENTER).with(key -> Integer.toString(machine.items.get(key).amount)),
        new Column().header("Price").with(key -> CurrencyUtils.format(machine.items.get(key).price))
      )
    );
  }

  /**
   * Pretty prints a <code>VendingItem</code>.
   * 
   * @param id
   * @param item
   * @return <code>String</code>
   */
  public static String getItemTable(String id, VendingItem item) {
    return AsciiTable.getTable(
      new String[] { "ID", "Name", "Amount", "Price" },
      new String[][] {
        {
          id.toUpperCase(),
          item.name,
          Integer.toString(item.amount),
          CurrencyUtils.format(item.price)
        }
      }
    );
  }

  /**
   * Pretty prints transaction info.
   * 
   * @param total
   * @param amountPaid
   * @param changeDue
   * @return <code>String</code>
   */
  public static String getTransactionTable(double total, double amountPaid, double changeDue) {
    return AsciiTable.getTable(
      new String[] { "Total", "Amount Paid", "Change Due" },
      new String[][] {
        {
          CurrencyUtils.format(total),
          CurrencyUtils.format(amountPaid),
          CurrencyUtils.format(changeDue)
        }
      }
    );
  }
}
