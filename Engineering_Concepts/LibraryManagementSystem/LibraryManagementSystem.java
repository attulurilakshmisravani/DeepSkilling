import java.util.Arrays;
import java.util.Comparator;

public class LibraryManagementSystem {

    // Linear Search
    public static Book linearSearch(Book[] books, String title) {
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Binary Search
    public static Book binarySearch(Book[] books, String title) {

        int low = 0;
        int high = books.length - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            int result =
                books[mid].title.compareToIgnoreCase(title);

            if (result == 0)
                return books[mid];
            else if (result < 0)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return null;
    }

    public static void main(String[] args) {

        Book[] books = {
            new Book(101, "Java", "James Gosling"),
            new Book(102, "Python", "Guido van Rossum"),
            new Book(103, "C Programming", "Dennis Ritchie"),
            new Book(104, "Data Structures", "Mark Allen")
        };

        // Linear Search
        Book result1 = linearSearch(books, "Python");

        System.out.println("Linear Search Result:");
        System.out.println(result1);

        // Sort books by title for Binary Search
        Arrays.sort(books,
            Comparator.comparing(b -> b.title.toLowerCase()));

        // Binary Search
        Book result2 = binarySearch(books, "Python");

        System.out.println("\nBinary Search Result:");
        System.out.println(result2);
    }
}