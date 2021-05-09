package dev.moody.vending_machine;

public class VendingItem {
  public final String name;
  public final double price;
  public int amount;

  public VendingItem(String name, int amount, double price) {
    this.name = name;
    this.amount = amount;
    this.price = price;
  }

  @Override
  public String toString() {
    return String.format("{ name: \"%s\", amount: %d, price: \"%s\" }", name, amount, CurrencyUtils.format(price));
  }
}
