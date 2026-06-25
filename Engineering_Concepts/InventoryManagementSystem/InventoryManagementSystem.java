import java.util.HashMap;

public class InventoryManagementSystem {

    HashMap<Integer, Product> inventory = new HashMap<>();

    public void addProduct(Product product) {
        inventory.put(product.productId, product);
    }

    public void updateProduct(int productId, int quantity, double price) {
        if (inventory.containsKey(productId)) {
            Product p = inventory.get(productId);
            p.quantity = quantity;
            p.price = price;
        }
    }

    public void deleteProduct(int productId) {
        inventory.remove(productId);
    }

    public void displayProducts() {
        for (Product p : inventory.values()) {
            System.out.println(p);
        }
    }

    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();

        ims.addProduct(new Product(101, "Laptop", 20, 50000));
        ims.addProduct(new Product(102, "Mouse", 100, 500));

        ims.displayProducts();
    }
}