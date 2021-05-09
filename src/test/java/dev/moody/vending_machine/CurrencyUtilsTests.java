package dev.moody.vending_machine;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class CurrencyUtilsTests {
  @Test
  public void format() {
    Assert.assertEquals("$1.00", CurrencyUtils.format(1));
    Assert.assertEquals("$1.23", CurrencyUtils.format(1.23));
    Assert.assertEquals("$1,234,567.89", CurrencyUtils.format(1234567.89));
  }

  @Test
  public void parse() throws ParseException {
    Assert.assertEquals(1.23, CurrencyUtils.parse("$1.23"), 0.01);
    Assert.assertEquals(3.01, CurrencyUtils.parse("$3.01"), 0.01);
    Assert.assertEquals(1234567.89, CurrencyUtils.parse("$1,234,567.89"), 0.01);
  }
}
