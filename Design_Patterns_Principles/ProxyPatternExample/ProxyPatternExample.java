interface Image {
    void display();
}

// Real Subject
class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromServer();
    }

    private void loadFromServer() {
        System.out.println("Loading " + fileName + " from remote server...");
    }

    public void display() {
        System.out.println("Displaying " + fileName);
    }
}

// Proxy
class ProxyImage implements Image {
    private String fileName;
    private RealImage realImage;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName); // Lazy initialization
        }
        realImage.display(); // Cached object reused
    }
}

public class ProxyPatternExample {
    public static void main(String[] args) {

        Image image = new ProxyImage("sample.jpg");

        System.out.println("First Call:");
        image.display();

        System.out.println();

        System.out.println("Second Call:");
        image.display();
    }
}