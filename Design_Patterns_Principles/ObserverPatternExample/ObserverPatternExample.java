import java.util.ArrayList;
import java.util.List;

// Subject Interface
interface Stock {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// Observer Interface
interface Observer {
    void update(double price);
}

// Concrete Subject
class StockMarket implements Stock {
    private List<Observer> observers = new ArrayList<>();
    private double stockPrice;

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockPrice);
        }
    }

    public void setStockPrice(double price) {
        stockPrice = price;
        System.out.println("\nStock Price Updated to: " + stockPrice);
        notifyObservers();
    }
}

// Concrete Observer - Mobile App
class MobileApp implements Observer {
    public void update(double price) {
        System.out.println("Mobile App: Stock Price = " + price);
    }
}

// Concrete Observer - Web App
class WebApp implements Observer {
    public void update(double price) {
        System.out.println("Web App: Stock Price = " + price);
    }
}

// Test Class
public class ObserverPatternExample {
    public static void main(String[] args) {

        StockMarket stockMarket = new StockMarket();

        Observer mobile = new MobileApp();
        Observer web = new WebApp();

        stockMarket.registerObserver(mobile);
        stockMarket.registerObserver(web);

        stockMarket.setStockPrice(1500.50);
        stockMarket.setStockPrice(1550.75);

        stockMarket.removeObserver(web);

        stockMarket.setStockPrice(1600.00);
    }
}