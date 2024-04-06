package org.example.com;

import org.example.com.BasketSplitter;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class BasketSplitterTest {
    // when testing be sure to fill absolute path to the location to config.json file
    // also when providing other config file than used in previous tests the reuslts may differ
    private string absolutePath = "";//???

    @Test
    public void testSplitBasket1() {
        BasketSplitter basketSplitter = new BasketSplitter(absolutePath);
        List<String> items = Arrays.asList("Cocoa Butter", "Tart - Raisin And Pecan", "Table Cloth 54x72 White", "Flower - Daisies", "Fond - Chocolate", "Cookies - Englishbay Wht");

        Map<String, List<String>> result = basketSplitter.split(items);

        // check result
        assertEquals(2, result.size()); // check if map has 2 baskets
        assertTrue(result.containsKey("Courier"));
        assertTrue(result.containsKey("Express Collection"));

        // check contents of baskets
        assertEquals(5, result.get("Courier").size());
        assertEquals(1, result.get("Express Collection").size());
    }

    @Test
    public void testSplitBasket2() {
        BasketSplitter basketSplitter = new BasketSplitter(absolutePath);
        List<String> items = Arrays.asList("Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole", "Haggis", "Mushroom - Porcini Frozen", "Cake - Miini Cheesecake Cherry", "Sauce - Mint", "Longan", "Bag Clear 10 Lb", "Nantucket - Pomegranate Pear", "Puree - Strawberry", "Numi - Assorted Teas", "Apples - Spartan", "Garlic - Peeled", "Cabbage - Nappa", "Bagel - Whole White Sesame", "Tea - Apple Green Tea");

        Map<String, List<String>> result = basketSplitter.split(items);

        // check result
        assertEquals(3, result.size()); // check if map has 3 baskets
        assertTrue(result.containsKey("Express Collection"));
        assertTrue(result.containsKey("Courier"));

        // check contents of baskets
        assertEquals(1, result.get("Courier").size());
        assertEquals(13, result.get("Express Collection").size());
    }
}
