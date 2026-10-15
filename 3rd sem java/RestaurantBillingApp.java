import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RestaurantBillingApp extends JFrame {
    private static final Color INK = new Color(35, 48, 43);
    private static final Color MUTED = new Color(116, 128, 120);
    private static final Color PAPER = new Color(248, 247, 241);
    private static final Color WHITE = Color.WHITE;
    private static final Color GREEN = new Color(41, 113, 82);
    private static final Color CORAL = new Color(226, 105, 78);
    private static final Color LINE = new Color(231, 232, 224);
    private static final double TAX_RATE = 0.08;
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
    private static final List<MenuItem> MENU = MenuCatalog.getItems();
    private static final String[] CATEGORIES = {
        "All items", "Starters", "Burgers", "Pizza", "Rice bowls", "Sides", "Drinks", "Desserts"
    };

    private final List<CartLine> cart = new ArrayList<CartLine>();
    private final JPanel pageHost = new JPanel(new BorderLayout());
    private String selectedCategory = "All items";
    private int nextOrderNumber = 1042;

    public RestaurantBillingApp() {
        super("Juniper & Grain | Restaurant Billing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 690));
        setSize(1260, 820);
        setLocationRelativeTo(null);
        getContentPane().setBackground(PAPER);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createHeader(), BorderLayout.NORTH);
        getContentPane().add(pageHost, BorderLayout.CENTER);
        showHome();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(INK);
        header.setBorder(BorderFactory.createEmptyBorder(15, 26, 15, 26));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        JLabel name = label("JUNIPER & GRAIN", 19, WHITE, Font.BOLD);
        JLabel tagline = label("GOOD FOOD, GOOD MOOD", 10, new Color(190, 207, 192), Font.BOLD);
        tagline.setBorder(BorderFactory.createEmptyBorder(3, 1, 0, 0));
        brand.add(name);
        brand.add(tagline);

        JLabel service = label("COUNTER SERVICE   /   OPEN TODAY", 11, new Color(224, 232, 222), Font.BOLD);
        header.add(brand, BorderLayout.WEST);
        header.add(service, BorderLayout.EAST);
        return header;
    }

    private JPanel createHome() {
        JPanel home = new JPanel(new BorderLayout());
        home.setBackground(PAPER);
        home.add(createCategoryRail(), BorderLayout.WEST);
        home.add(createMenuBrowser(), BorderLayout.CENTER);
        home.add(createOrderPanel(), BorderLayout.EAST);
        return home;
    }

    private JPanel createCategoryRail() {
        JPanel rail = new JPanel();
        rail.setBackground(new Color(242, 241, 233));
        rail.setPreferredSize(new Dimension(178, 0));
        rail.setBorder(BorderFactory.createEmptyBorder(26, 16, 18, 14));
        rail.setLayout(new BoxLayout(rail, BoxLayout.Y_AXIS));
        JLabel section = label("THE MENU", 11, MUTED, Font.BOLD);
        section.setBorder(BorderFactory.createEmptyBorder(0, 9, 16, 0));
        rail.add(section);

        for (String category : CATEGORIES) {
            final String choice = category;
            JButton button = new JButton(category);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setFont(new Font("Segoe UI", Font.BOLD, 13));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(11, 11, 11, 8));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setBackground(category.equals(selectedCategory) ? GREEN : new Color(242, 241, 233));
            button.setForeground(category.equals(selectedCategory) ? WHITE : INK);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.addActionListener((ActionEvent event) -> {
                selectedCategory = choice;
                showHome();
            });
            rail.add(button);
            rail.add(Box.createVerticalStrut(5));
        }

        rail.add(Box.createVerticalGlue());
        JPanel note = new RoundedPanel(16, new Color(255, 239, 207));
        note.setLayout(new BoxLayout(note, BoxLayout.Y_AXIS));
        note.setBorder(BorderFactory.createEmptyBorder(13, 13, 13, 13));
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        note.add(label("MADE FRESH", 10, new Color(132, 92, 26), Font.BOLD));
        JLabel noteText = label("Small-batch favorites, big-time flavor.", 12, INK, Font.PLAIN);
        noteText.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
        note.add(noteText);
        rail.add(note);
        return rail;
    }

    private JPanel createMenuBrowser() {
        JPanel browser = new JPanel(new BorderLayout(0, 16));
        browser.setBackground(PAPER);
        browser.setBorder(BorderFactory.createEmptyBorder(25, 24, 20, 20));

        JPanel heading = new JPanel(new BorderLayout(10, 8));
        heading.setOpaque(false);
        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.add(label("A little something delicious", 23, INK, Font.BOLD));
        JLabel subtitle = label("Pick a favorite. Make it yours.", 13, MUTED, Font.PLAIN);
        subtitle.setBorder(BorderFactory.createEmptyBorder(5, 1, 0, 0));
        titleBlock.add(subtitle);

        JTextField search = new JTextField();
        search.setToolTipText("Search the menu");
        search.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        search.setForeground(INK);
        search.setBackground(WHITE);
        search.setPreferredSize(new Dimension(185, 38));
        search.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE), BorderFactory.createEmptyBorder(7, 10, 7, 10)));
        heading.add(titleBlock, BorderLayout.WEST);
        heading.add(search, BorderLayout.EAST);
        browser.add(heading, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(0, 2, 13, 13));
        cards.setBackground(PAPER);
        cards.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2));
        Runnable populate = () -> {
            cards.removeAll();
            String query = search.getText().trim().toLowerCase(Locale.ROOT);
            List<MenuItem> visibleItems = new ArrayList<MenuItem>();
            for (MenuItem item : MENU) {
                boolean inCategory = selectedCategory.equals("All items") || item.category.equals(selectedCategory);
                boolean matchesSearch = query.isEmpty()
                        || item.name.toLowerCase(Locale.ROOT).contains(query)
                        || item.description.toLowerCase(Locale.ROOT).contains(query);
                if (inCategory && matchesSearch) visibleItems.add(item);
            }
            if (visibleItems.isEmpty()) {
                cards.setLayout(new BorderLayout());
                cards.add(label("No menu items match that search.", 14, MUTED, Font.PLAIN), BorderLayout.NORTH);
            } else {
                cards.setLayout(new GridLayout(0, 2, 13, 13));
                for (MenuItem item : visibleItems) cards.add(createMenuCard(item));
            }
            cards.revalidate();
            cards.repaint();
        };
        populate.run();
        search.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent event) { populate.run(); }
            public void removeUpdate(DocumentEvent event) { populate.run(); }
            public void changedUpdate(DocumentEvent event) { populate.run(); }
        });

        JScrollPane scroll = new JScrollPane(cards);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(PAPER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        browser.add(scroll, BorderLayout.CENTER);
        return browser;
    }

    private JPanel createMenuCard(MenuItem item) {
        RoundedPanel card = new RoundedPanel(18, WHITE);
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createEmptyBorder(14, 14, 13, 14));
        card.setPreferredSize(new Dimension(220, 168));

        JPanel top = new JPanel(new BorderLayout(9, 0));
        top.setOpaque(false);
        JPanel marker = new RoundedPanel(14, item.accent);
        marker.setPreferredSize(new Dimension(38, 38));
        marker.setLayout(new BorderLayout());
        JLabel initial = label(item.shortMark, 15, WHITE, Font.BOLD);
        initial.setHorizontalAlignment(SwingConstants.CENTER);
        marker.add(initial, BorderLayout.CENTER);
        JPanel names = new JPanel();
        names.setOpaque(false);
        names.setLayout(new BoxLayout(names, BoxLayout.Y_AXIS));
        names.add(label(item.category.toUpperCase(Locale.ROOT), 9, MUTED, Font.BOLD));
        JLabel itemName = label(item.name, 14, INK, Font.BOLD);
        itemName.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        names.add(itemName);
        top.add(marker, BorderLayout.WEST);
        top.add(names, BorderLayout.CENTER);

        JTextArea description = new JTextArea(item.description);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        description.setForeground(MUTED);
        description.setOpaque(false);
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBorder(null);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.add(label(money(item.priceCents), 15, INK, Font.BOLD), BorderLayout.WEST);
        JButton add = actionButton(item.options.isEmpty() ? "ADD +" : "CUSTOMIZE +", GREEN, WHITE);
        add.setFont(new Font("Segoe UI", Font.BOLD, 10));
        add.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        add.addActionListener(event -> addMenuItem(item));
        footer.add(add, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);
        card.add(description, BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(WHITE);
        panel.setPreferredSize(new Dimension(324, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, LINE),
                BorderFactory.createEmptyBorder(24, 19, 18, 19)));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        heading.add(label("Your order", 21, INK, Font.BOLD), BorderLayout.WEST);
        JLabel count = label(String.valueOf(itemCount()) + " ITEMS", 10, MUTED, Font.BOLD);
        heading.add(count, BorderLayout.EAST);
        panel.add(heading, BorderLayout.NORTH);

        JPanel rows = new JPanel();
        rows.setBackground(WHITE);
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));
        if (cart.isEmpty()) {
            JPanel empty = new JPanel();
            empty.setOpaque(false);
            empty.setLayout(new BoxLayout(empty, BoxLayout.Y_AXIS));
            empty.setBorder(BorderFactory.createEmptyBorder(44, 8, 0, 8));
            JLabel mark = label("+", 34, new Color(192, 201, 192), Font.PLAIN);
            mark.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel emptyTitle = label("Your basket is ready", 14, INK, Font.BOLD);
            emptyTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel emptyHint = label("Add something tasty to get started.", 11, MUTED, Font.PLAIN);
            emptyHint.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyHint.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
            empty.add(mark);
            empty.add(Box.createVerticalStrut(9));
            empty.add(emptyTitle);
            empty.add(emptyHint);
            rows.add(empty);
        } else {
            for (CartLine line : cart) {
                rows.add(createCartRow(line));
                rows.add(Box.createVerticalStrut(10));
            }
        }
        JScrollPane listScroll = new JScrollPane(rows);
        listScroll.setBorder(null);
        listScroll.getViewport().setBackground(WHITE);
        listScroll.getVerticalScrollBar().setUnitIncrement(14);
        panel.add(listScroll, BorderLayout.CENTER);

        JPanel totals = new JPanel();
        totals.setOpaque(false);
        totals.setLayout(new BoxLayout(totals, BoxLayout.Y_AXIS));
        totals.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, LINE));
        totals.add(Box.createVerticalStrut(13));
        totals.add(totalRow("Subtotal", money(subtotalCents()), false));
        totals.add(Box.createVerticalStrut(8));
        totals.add(totalRow("Tax (8%)", money(taxCents()), false));
        totals.add(Box.createVerticalStrut(11));
        totals.add(totalRow("Total", money(totalCents()), true));
        totals.add(Box.createVerticalStrut(14));
        JButton checkout = actionButton("PLACE ORDER", CORAL, WHITE);
        checkout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        checkout.setPreferredSize(new Dimension(100, 46));
        checkout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        checkout.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkout.addActionListener(event -> showReceipt());
        totals.add(checkout);
        panel.add(totals, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCartRow(CartLine line) {
        JPanel row = new JPanel(new BorderLayout(6, 6));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(7, 0, 8, 0));
        JPanel description = new JPanel();
        description.setOpaque(false);
        description.setLayout(new BoxLayout(description, BoxLayout.Y_AXIS));
        description.add(label(line.item.name, 13, INK, Font.BOLD));
        if (line.customizations.isEmpty()) {
            JLabel detail = label(money(line.unitPriceCents()), 11, MUTED, Font.PLAIN);
            detail.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
            description.add(detail);
        } else {
            StringBuilder choices = new StringBuilder();
            for (Customization option : line.customizations) {
                if (choices.length() > 0) choices.append(", ");
                choices.append(option.name);
            }
            JLabel detail = label(choices.toString(), 10, MUTED, Font.PLAIN);
            detail.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
            description.add(detail);
        }
        row.add(description, BorderLayout.CENTER);
        row.add(label(money(line.unitPriceCents() * line.quantity), 12, INK, Font.BOLD), BorderLayout.EAST);

        JPanel controls = new JPanel(new BorderLayout());
        controls.setOpaque(false);
        JPanel quantity = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));
        quantity.setOpaque(false);
        JButton minus = quantityButton("-");
        minus.addActionListener(event -> changeQuantity(line, -1));
        JLabel amount = label(String.valueOf(line.quantity), 12, INK, Font.BOLD);
        amount.setHorizontalAlignment(SwingConstants.CENTER);
        amount.setPreferredSize(new Dimension(22, 28));
        JButton plus = quantityButton("+");
        plus.addActionListener(event -> changeQuantity(line, 1));
        quantity.add(minus);
        quantity.add(amount);
        quantity.add(plus);
        controls.add(quantity, BorderLayout.WEST);
        row.add(controls, BorderLayout.SOUTH);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 82));
        return row;
    }

    private JPanel totalRow(String title, String amount, boolean grandTotal) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, grandTotal ? 27 : 21));
        row.add(label(title, grandTotal ? 15 : 12, grandTotal ? INK : MUTED,
                grandTotal ? Font.BOLD : Font.PLAIN), BorderLayout.WEST);
        row.add(label(amount, grandTotal ? 17 : 12, grandTotal ? GREEN : INK, Font.BOLD), BorderLayout.EAST);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        return row;
    }

    private void addMenuItem(MenuItem item) {
        List<Customization> selected = new ArrayList<Customization>();
        if (!item.options.isEmpty()) {
            JPanel choices = new JPanel();
            choices.setBackground(WHITE);
            choices.setLayout(new BoxLayout(choices, BoxLayout.Y_AXIS));
            choices.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            JCheckBox[] boxes = new JCheckBox[item.options.size()];
            for (int i = 0; i < item.options.size(); i++) {
                Customization option = item.options.get(i);
                boxes[i] = new JCheckBox(option.name + "  +" + money(option.priceCents));
                boxes[i].setBackground(WHITE);
                boxes[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
                boxes[i].setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2));
                choices.add(boxes[i]);
            }
            int result = JOptionPane.showConfirmDialog(this, choices, "Make it yours: " + item.name,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return;
            for (int i = 0; i < boxes.length; i++) {
                if (boxes[i].isSelected()) selected.add(item.options.get(i));
            }
        }

        for (CartLine line : cart) {
            if (line.item == item && sameCustomizations(line.customizations, selected)) {
                line.quantity++;
                refreshHome();
                return;
            }
        }
        cart.add(new CartLine(item, selected));
        refreshHome();
    }

    private boolean sameCustomizations(List<Customization> first, List<Customization> second) {
        if (first.size() != second.size()) return false;
        for (int i = 0; i < first.size(); i++) {
            if (!first.get(i).name.equals(second.get(i).name)) return false;
        }
        return true;
    }

    private void changeQuantity(CartLine line, int change) {
        line.quantity += change;
        if (line.quantity <= 0) cart.remove(line);
        refreshHome();
    }

    private void refreshHome() {
        pageHost.removeAll();
        pageHost.add(createHome(), BorderLayout.CENTER);
        pageHost.revalidate();
        pageHost.repaint();
    }

    private void showHome() {
        refreshHome();
    }

    private void showReceipt() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add an item to your order before checking out.",
                    "Your order is empty", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        final int orderNumber = nextOrderNumber++;
        JPanel receiptPage = new JPanel(new java.awt.GridBagLayout());
        receiptPage.setBackground(PAPER);
        RoundedPanel receipt = new RoundedPanel(22, WHITE);
        receipt.setLayout(new BoxLayout(receipt, BoxLayout.Y_AXIS));
        receipt.setBorder(BorderFactory.createEmptyBorder(27, 34, 27, 34));
        receipt.setPreferredSize(new Dimension(520, 620));

        JLabel stamp = label("ORDER CONFIRMED", 10, GREEN, Font.BOLD);
        stamp.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = label("That's a wrap!", 27, INK, Font.BOLD);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel thanks = label("Thanks for stopping by Juniper & Grain.", 13, MUTED, Font.PLAIN);
        thanks.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel orderId = label("ORDER  #" + String.format(Locale.ROOT, "%04d", orderNumber), 11, CORAL, Font.BOLD);
        orderId.setBorder(BorderFactory.createEmptyBorder(16, 0, 3, 0));
        orderId.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel date = label(new SimpleDateFormat("EEE, MMM d  |  h:mm a", Locale.US).format(new Date()),
                11, MUTED, Font.PLAIN);
        date.setAlignmentX(Component.CENTER_ALIGNMENT);

        receipt.add(stamp);
        receipt.add(Box.createVerticalStrut(7));
        receipt.add(title);
        receipt.add(Box.createVerticalStrut(4));
        receipt.add(thanks);
        receipt.add(orderId);
        receipt.add(date);
        receipt.add(Box.createVerticalStrut(20));
        JPanel divider = new JPanel();
        divider.setBackground(LINE);
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        receipt.add(divider);
        receipt.add(Box.createVerticalStrut(14));

        for (CartLine line : cart) {
            JPanel billRow = new JPanel(new BorderLayout(8, 3));
            billRow.setOpaque(false);
            billRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43));
            JPanel itemText = new JPanel();
            itemText.setOpaque(false);
            itemText.setLayout(new BoxLayout(itemText, BoxLayout.Y_AXIS));
            itemText.add(label(line.quantity + "  x  " + line.item.name, 12, INK, Font.BOLD));
            if (!line.customizations.isEmpty()) {
                StringBuilder names = new StringBuilder();
                for (Customization option : line.customizations) {
                    if (names.length() > 0) names.append(", ");
                    names.append(option.name);
                }
                itemText.add(label(names.toString(), 10, MUTED, Font.PLAIN));
            }
            billRow.add(itemText, BorderLayout.WEST);
            billRow.add(label(money(line.unitPriceCents() * line.quantity), 12, INK, Font.PLAIN), BorderLayout.EAST);
            receipt.add(billRow);
            receipt.add(Box.createVerticalStrut(7));
        }

        JPanel receiptDivider = new JPanel();
        receiptDivider.setBackground(LINE);
        receiptDivider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        receipt.add(Box.createVerticalStrut(7));
        receipt.add(receiptDivider);
        receipt.add(Box.createVerticalStrut(12));
        receipt.add(totalRow("Subtotal", money(subtotalCents()), false));
        receipt.add(Box.createVerticalStrut(7));
        receipt.add(totalRow("Tax (8%)", money(taxCents()), false));
        receipt.add(Box.createVerticalStrut(10));
        receipt.add(totalRow("Total paid", money(totalCents()), true));
        receipt.add(Box.createVerticalStrut(20));

        JButton newOrder = actionButton("START A NEW ORDER", GREEN, WHITE);
        newOrder.setFont(new Font("Segoe UI", Font.BOLD, 12));
        newOrder.setPreferredSize(new Dimension(200, 44));
        newOrder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        newOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
        newOrder.addActionListener(event -> {
            cart.clear();
            selectedCategory = "All items";
            showHome();
        });
        receipt.add(newOrder);

        JScrollPane receiptScroll = new JScrollPane(receipt);
        receiptScroll.setBorder(null);
        receiptScroll.getViewport().setBackground(PAPER);
        receiptPage.add(receiptScroll);
        pageHost.removeAll();
        pageHost.add(receiptPage, BorderLayout.CENTER);
        pageHost.revalidate();
        pageHost.repaint();
    }

    private int itemCount() {
        int count = 0;
        for (CartLine line : cart) count += line.quantity;
        return count;
    }

    private int subtotalCents() {
        int subtotal = 0;
        for (CartLine line : cart) subtotal += line.unitPriceCents() * line.quantity;
        return subtotal;
    }

    private int taxCents() {
        return (int) Math.round(subtotalCents() * TAX_RATE);
    }

    private int totalCents() {
        return subtotalCents() + taxCents();
    }

    private static String money(int cents) {
        return CURRENCY.format(cents / 100.0);
    }

    private static JLabel label(String text, int size, Color color, int style) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", style, size));
        label.setForeground(color);
        return label;
    }

    private static JButton actionButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        return button;
    }

    private static JButton quantityButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(GREEN);
        button.setBackground(new Color(237, 244, 237));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(27, 26));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new RestaurantBillingApp().setVisible(true);
        });
    }

}