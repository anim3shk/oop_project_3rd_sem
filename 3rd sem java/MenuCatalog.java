import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

class MenuCatalog {
    private MenuCatalog() {
    }

    static List<MenuItem> getItems() {
        List<MenuItem> items = new ArrayList<MenuItem>();
        items.add(new MenuItem("Paneer tikka bites", "Starters", "Char-grilled paneer with mint chutney.", 12900, "P", new Color(205, 125, 62),
                option("Extra mint chutney", 1500), option("Add salad", 2000)));
        items.add(new MenuItem("Crispy corn", "Starters", "Golden corn tossed with spices and fresh lime.", 8900, "C", new Color(205, 148, 51),
                option("Extra spice", 500), option("Add cheese", 2000)));
        items.add(new MenuItem("Classic cheeseburger", "Burgers", "Smash patty, sharp cheddar, house pickles.", 10900, "B", new Color(226, 105, 78),
                option("Extra cheese", 3000), option("Crispy bacon", 4000), option("Jalapenos", 7500)));
        items.add(new MenuItem("Crispy chicken", "Burgers", "Golden chicken, crunchy slaw, tangy mayo.", 9900, "C", new Color(226, 154, 59),
                option("Extra cheese", 3000), option("Spicy mayo", 5000)));
         items.add(new MenuItem("Cheese cheeseburger", "Burgers", "Smash patty, sharp cheddar, house pickles.", 12900, "B", new Color(226, 105, 78),
                option("Extra cheese", 3000), option("Crispy bacon", 4000), option("Jalapenos", 7500)));
        items.add(new MenuItem("Garden burger", "Burgers", "Grilled black bean patty with green goddess.", 9900, "G", new Color(41, 113, 82),
                option("Avocado", 15000), option("Vegan cheese", 12500)));
        items.add(new MenuItem("Margherita", "Pizza", "Tomato, fresh mozzarella, basil. Personal 10 inch.", 12000, "M", new Color(205, 87, 67),
                option("Extra cheese", 15000), option("Olives", 7500)));
        items.add(new MenuItem("Pepperoni", "Pizza", "Cup-and-char pepperoni with our slow tomato sauce.", 14000, "P", new Color(193, 76, 69),
                option("Extra cheese", 15000), option("Mushrooms", 8500)));
        items.add(new MenuItem("Paneer tikka bowl", "Rice bowls", "Basmati rice, grilled paneer, and cooling raita.", 17900, "R", new Color(41, 113, 82),
                option("Extra paneer", 4500), option("Extra raita", 1500)));
        items.add(new MenuItem("Spiced chicken bowl", "Rice bowls", "Seasoned chicken, basmati rice, and fresh herbs.", 19900, "S", new Color(193, 76, 69),
                option("Extra chicken", 5000), option("Add raita", 1500)));
        items.add(new MenuItem("Loaded fries", "Sides", "Skin-on fries, scallion, house seasoning.", 6200, "F", new Color(205, 148, 51),
                option("Extra cheese", 3000), option("Bacon bits", 4000)));
        items.add(new MenuItem("Garden lemonade", "Drinks", "Fresh-squeezed lemon with a hint of mint.", 3900, "L", new Color(111, 155, 91)));
        items.add(new MenuItem("Vanilla bean shake", "Drinks", "Thick vanilla bean shake, whipped cream on top.", 5900, "S", new Color(157, 123, 99)));
        items.add(new MenuItem("Chocolate brownie", "Desserts", "Warm chocolate brownie with a soft, fudgy center.", 8900, "B", new Color(130, 82, 61),
                option("Add vanilla ice cream", 3500)));
        items.add(new MenuItem("Gulab jamun", "Desserts", "Two soft milk dumplings in fragrant sugar syrup.", 6900, "G", new Color(183, 111, 70),
                option("Add one more", 3000)));
        
        return items;
    }

    private static Customization option(String name, int priceCents) {
        return new Customization(name, priceCents);
    }
}