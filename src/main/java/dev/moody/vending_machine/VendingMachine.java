package dev.moody.vending_machine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class VendingMachine {
  public final int rows;
  public final int columns;
  public final LinkedHashMap<String, VendingItem> items;

  public VendingMachine(int rows, int columns, List<VendingItem> itemList) {
    this.rows = rows;
    this.columns = columns;

    // Initialize items.
    this.items = new LinkedHashMap<>();
    for (int i = 0; i < itemList.size(); i++) {
      // Generate item ID from index based on rows/columns (e.g. 0 -> "A1").
      int row = i / columns;
      int column = (i % columns) + 1;
      String id = String.format("%s%d", Character.toString('A' + row), column);
      // Map id to item at index i.
      this.items.put(id, itemList.get(i));
    }
  }

  /**
   * Parses a JSON <code>String</code>, and returns a new
   * <code>VendingMachine</code> instance.
   * 
   * @param json the JSON <code>String</code> to parse
   * @return a new <code>VendingMachine</code>
   * @throws JsonMappingException
   * @throws JsonProcessingException
   * @throws ParseException
   */
  public static VendingMachine fromJson(String json)
      throws JsonMappingException, JsonProcessingException, ParseException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode treeRoot = mapper.readTree(json);

    // Get rows and columns.
    JsonNode config = treeRoot.get("config");
    int rows = config.get("rows").asInt();
    int columns = config.get("columns").asInt();

    // Get items.
    JsonNode jsonItems = treeRoot.get("items");
    ArrayList<VendingItem> items = new ArrayList<>();
    for (int i = 0; i < jsonItems.size(); i++) {
      JsonNode jsonItem = jsonItems.get(i);

      // Get item name, amount, and price.
      String name = jsonItem.get("name").asText();
      int amount = jsonItem.get("amount").asInt();
      double price = CurrencyUtils.parse(jsonItem.get("price").asText());

      // Add to list.
      items.add(new VendingItem(name, amount, price));
    }

    return new VendingMachine(rows, columns, items);
  }

  /**
   * Returns the item mapped to the specified id.
   * 
   * @param id the id of the item to get
   * @return the <code>VendingItem</code>, or <code>null</node>
   */
  public VendingItem getItem(String id) {
    id = id.trim().toUpperCase();
    return items.containsKey(id) ? items.get(id) : null;
  }

  /**
   * Dispenses an item by id.
   *
   * @param id the id of the item to dispense
   * @return <code>true</code> if successful
   */
  public boolean dispense(String id) {
    var item = getItem(id);
    if (item != null && item.amount > 0) {
      item.amount--;
      return true;
    }
    return false;
  }
}
