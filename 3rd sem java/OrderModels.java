import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

class MenuItem {
    final String name;
    final String category;
    final String description;
    final int priceCents;
    final String shortMark;
    final Color accent;
    final List<Customization> options;

    MenuItem(String name, String category, String description, int priceCents, String shortMark, Color accent,
             Customization... options) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.priceCents = priceCents;
        this.shortMark = shortMark;
        this.accent = accent;
        this.options = java.util.Arrays.asList(options);
    }
}

class Customization {
    final String name;
    final int priceCents;

    Customization(String name, int priceCents) {
        this.name = name;
        this.priceCents = priceCents;
    }
}

class CartLine {
    final MenuItem item;
    final List<Customization> customizations;
    int quantity = 1;

    CartLine(MenuItem item, List<Customization> customizations) {
        this.item = item;
        this.customizations = new ArrayList<Customization>(customizations);
    }

    int unitPriceCents() {
        int price = item.priceCents;
        for (Customization option : customizations) price += option.priceCents;
        return price;
    }
}