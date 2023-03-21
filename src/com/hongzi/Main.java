import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static ProductList productList = new ProductList();

    public static void main(String[] args) {
        Product bread = new Product("bread", 1.99, 100);
        Product orange = new Product("orange", 3.99, 200);
        Product cake = new Product("cake", 6.50, 15);
        Product juice = new Product("juice", 4.50, 50);
        productList.addProduct(bread);
        productList.addProduct(orange);
        productList.addProduct(cake);
        productList.addProduct(juice);
        System.out.println(productList);

        Scanner input = new Scanner(System.in);
        String cartName = null;
        String checkingOut = null;
        String addItem = null;

        System.out.println("Please enter your cart name:");
        cartName = input.next(); 
        Cart myCart = new Cart(cartName);

       do{
            do{
                try {
                    System.out.println("Do you want to add or remove item from the cart (a/r):");
                    addItem = input.next();
                    if (!addItem.equals("a") && !addItem.equals("r")) {
                        addItem = null;
                        throw new InputMismatchException();
                    }
                } catch (InputMismatchException e) {
                    addItem = null;
                    input.nextLine();
                }
            } while (addItem == null);
      
            System.out.println("Enter the item name:");
            String item = input.next();
            System.out.println("Enter the item quantity:");
            int quantity = input.nextInt();
            if (addItem.equals("a")) {
                sellItem(myCart, item, quantity);
                System.out.println(myCart);
            } else if (addItem.equals("r")) {
                removeItem(myCart, item, quantity);
                System.out.println(myCart);
            }
            input.nextLine();
                      
            do{
                try {
                    System.out.println("Do you want to check out? (y/n)");
                    checkingOut = input.nextLine();
                    if (!checkingOut.equals("y") && !checkingOut.equals("n")) {
                        checkingOut = null;
                        throw new InputMismatchException();
                    }
                } catch (InputMismatchException e) {
                    checkingOut = null;
                    input.nextLine();
                }
            } while (checkingOut == null);
            if (checkingOut.equals("y")) {
                System.out.println("Checking out for:");
                System.out.println(myCart);
                checkOut(myCart);
                System.out.println("You have successfully checked out. Thank you for shopping with us!");

            }
        } while (!checkingOut.equals("y"));

        input.close();
    }

    public static int sellItem(Cart cart, String item, int quantity) {
        // retrieve the item from stock list
        Product p = productList.get(item);
        if(p == null) {
            System.out.println("We don't sell " + item);
            return 0;
        }
        if(productList.reserveStock(item, quantity) != 0) {
            return cart.addToCart(p, quantity);
        } 
        return 0;
    }

    public static int removeItem(Cart cart, String item, int quantity) {
        // retrieve the item from stock list
        Product p = productList.get(item);
        if(p == null) {
            System.out.println("We don't sell " + item);
            return 0;
        }
        if(cart.removeFromCart(p, quantity) == quantity) {
            return productList.unreserveStock(item, quantity);
        } else {
            System.out.println("You don't have this amount of " + p.getName() + " in your cart.");
        }
        return 0;
    }

    public static void checkOut(Cart cart) {
        for (Map.Entry<Product, Integer> item : cart.items().entrySet()) {
            productList.sellProduct(item.getKey().getName(), item.getValue());
        }
        cart.clearCart();
    }
}


// Scenarios:
// 1. Add valid item, remove valid item and amount, check out.
// 2. Add invalid item that we don't sell / remove invalid item that isn't in the cart.
// 3. Add valid items but the amount is invalid / remove valid item but the amount is more than what the cart has.
// 4. Invalid inputs.


