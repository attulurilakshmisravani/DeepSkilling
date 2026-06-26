// Strategy Interface
interface PaymentStrategy {
    void pay(double amount);
}

// Concrete Strategy - Credit Card
class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid Rs." + amount + " using Credit Card.");
    }
}

// Concrete Strategy - PayPal
class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid Rs." + amount + " using PayPal.");
    }
}

// Context Class
class PaymentContext {
    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executePayment(double amount) {
        strategy.pay(amount);
    }
}

// Test Class
public class StrategyPatternExample {
    public static void main(String[] args) {

        PaymentContext payment = new PaymentContext(new CreditCardPayment());
        payment.executePayment(1000);

        payment.setPaymentStrategy(new PayPalPayment());
        payment.executePayment(2500);
    }
}