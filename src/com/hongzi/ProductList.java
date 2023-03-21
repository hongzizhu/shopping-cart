import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductList {
    private final Map<String, Product> list;

    public ProductList() {
        this.list = new LinkedHashMap<>(); // Keep products in order as they are added.
    }
    
    // Add product to the product list and adjust its quantity.
    public int addProduct(Product p) {
        if (p != null) {
            Product instock = list.getOrDefault(p.getName(), p);
            if (instock != p) {
                p.adjustStock(instock.availableQuantity());
            }
            list.put(p.getName(), p);
            return p.availableQuantity();
        }
        return 0;
    }

    // Sell product from the product list and adjust its quantity.
    public int sellProduct(String item, int quantity) {
        Product inStock = list.get(item);
        if((inStock != null) && (quantity > 0)) {
            return inStock.finaliseStock(quantity);
        }
        return 0;
    }

    public int reserveStock(String item, int quantity) {
        Product inStock = list.get(item);
        if((inStock != null) && (quantity > 0)) {
            try {
                return inStock.reserveStock(quantity);
            } catch (InvalidAmoutException e) {
                System.out.println(e.getMessage());
            }
        }
        return 0;
    }

    public int unreserveStock(String item, int quantity) {
        Product inStock = list.get(item);

        if((inStock != null) && (quantity > 0)) {
            return inStock.unreserveStock(quantity);
        }
        return 0;
    }

    public Product get(String name) {
        return list.get(name);
    }

    public Map<String, Product> products() {
        return Collections.unmodifiableMap(list); //read only
    }

    // Output all products in stock and their quantities and prices.
    @Override
    public String toString() {
        String s = "\nProduct List\n";
        for (Map.Entry<String, Product> item : list.entrySet()) {
            Product p = item.getValue();
            s = s + p.getName() + ": " + p.availableQuantity() + " in stock, " + p.getPrice() + " each.\n";
        }
        return s;
    }
}
