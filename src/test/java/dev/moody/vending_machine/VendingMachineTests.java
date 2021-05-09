package dev.moody.vending_machine;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class VendingMachineTests {
  @Test
  public void fromJson() {
    try {
      VendingMachine machine = VendingMachine.fromJson(
          "{\"config\": {\"rows\": 6, \"columns\": \"10\"}, \"items\": [{\"name\": \"Test Item\", \"amount\": 10, \"price\": \"$1.50\"}]}");

      Assert.assertEquals(6, machine.rows);
      Assert.assertEquals(10, machine.columns);

      VendingItem item = machine.items.get("A1");
      Assert.assertEquals("Test Item", item.name);
      Assert.assertEquals(10, item.amount);
      Assert.assertEquals(1.50, item.price, 0.01);
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
  }

  @Test
  public void getItem() {
    VendingItem a1 = new VendingItem("Item 1", 10, 1.50);
    VendingItem a2 = new VendingItem("Item 2", 9, 1.40);
    VendingItem b1 = new VendingItem("Item 3", 8, 1.30);
    VendingItem b2 = new VendingItem("Item 4", 7, 1.20);
    VendingItem c1 = new VendingItem("Item 5", 6, 1.10);

    VendingMachine machine = new VendingMachine(2, 2, Arrays.asList(a1, a2, b1, b2, c1));

    Assert.assertSame(a1, machine.getItem("A1"));
    Assert.assertSame(a2, machine.getItem("A2"));
    Assert.assertSame(b1, machine.getItem("B1"));
    Assert.assertSame(b2, machine.getItem("B2"));
    Assert.assertSame(c1, machine.getItem("C1"));

    Assert.assertNull(machine.getItem(""));
    Assert.assertNull(machine.getItem("A"));
    Assert.assertNull(machine.getItem("1A"));
    Assert.assertNull(machine.getItem("A3"));
    Assert.assertNull(machine.getItem("ABC"));
  }

  @Test
  public void dispense() {
    VendingItem a1 = new VendingItem("Item 1", 1, 1.50);
    VendingMachine machine = new VendingMachine(1, 1, Arrays.asList(a1));
    Assert.assertEquals(1, a1.amount);

    Assert.assertTrue(machine.dispense("A1"));
    Assert.assertEquals(0, a1.amount);

    Assert.assertFalse(machine.dispense("A1"));
    Assert.assertEquals(0, a1.amount);
  }
}
