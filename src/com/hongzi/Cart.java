import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Cart {
    private final String name;
    private final Map<Product, Integer> list;

    public Cart(String name) {
        this.name = name;
        this.list = new TreeMap<>();
    }

    public int addToCart(Product p, int quantity) {
        if ((p != null) && (quantity > 0)) {
            int inCart = list.getOrDefault(p, 0);
            list.put(p, inCart + quantity);
            return inCart;
        }
        return 0;
    }

    public int removeFromCart(Product item, int quantity) {
        if((item != null) && (quantity > 0)) {
            // check if we already have the item in the cart
            int inCart = list.getOrDefault(item, 0);
            int newQuantity = inCart - quantity;

            if(newQuantity > 0) {
                list.put(item, newQuantity);
                return quantity;
            } else if(newQuantity == 0) {
                list.remove(item);
                return quantity;
            }
        }
        return 0;
    }

    public void clearCart() {
        this.list.clear();
    }

    public Map<Product, Integer> items() {
        return Collections.unmodifiableMap(list); //read only
    }

    // Output all items in the cart, their amount and total cost.
    @Override
    public String toString() {
        String s = "\nShopping cart " + name + " has " + list.size() + " items: \n";
        double totalCost = 0.0;
        for (Map.Entry<Product, Integer> item : list.entrySet()) {
            Product p = item.getKey();
            int quant = item.getValue();
            Double price = p.getPrice();
            s = s + p.getName() + ": " + quant + " in cart, " + price + " each.\n";
            totalCost += price * quant;
        }
        return s  = s + "Total cost: " + String.format("%.2f",totalCost) + "\n";
    }
     
}
