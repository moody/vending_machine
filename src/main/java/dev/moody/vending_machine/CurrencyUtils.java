package dev.moody.vending_machine;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class CurrencyUtils {
  private static final NumberFormat usdFormat = NumberFormat.getCurrencyInstance(Locale.US);

  public static String format(double value) {
    return usdFormat.format(value);
  }

  public static double parse(String value) throws ParseException {
    return usdFormat.parse(value).doubleValue();
  }
}
