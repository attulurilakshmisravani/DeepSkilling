import java.util.Arrays;
import java.util.Comparator;

public class EcommerceSearch {

    // Linear Search
    public static Product linearSearch(Product[] products, String name) {
        for (Product p : products) {
            if (p.productName.equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    // Binary Search
    public static Product binarySearch(Product[] products, String name) {

        int low = 0;
        int high = products.length - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            int result = products[mid].productName.compareToIgnoreCase(name);

            if (result == 0) {
                return products[mid];
            } else if (result < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return null;
    }

    public static void main(String[] args) {

        Product[] products = {
                new Product(101, "Laptop", "Electronics"),
                new Product(102, "Mouse", "Accessories"),
                new Product(103, "Keyboard", "Accessories"),
                new Product(104, "Monitor", "Electronics")
        };

        // Linear Search
        Product found1 = linearSearch(products, "Mouse");

        if (found1 != null)
            System.out.println("Linear Search Found: " + found1);
        else
            System.out.println("Product Not Found");

        // Sort array for Binary Search
        Arrays.sort(products,
                Comparator.comparing(p -> p.productName.toLowerCase()));

        Product found2 = binarySearch(products, "Mouse");

        if (found2 != null)
            System.out.println("Binary Search Found: " + found2);
        else
            System.out.println("Product Not Found");
    }
}