interface PaymentProcessor {
    void processPayment(double amount);
}

// Adaptee 1
class PayPalGateway {
    public void sendPayment(double amount) {
        System.out.println("Payment of Rs." + amount + " processed using PayPal.");
    }
}

// Adaptee 2
class StripeGateway {
    public void makePayment(double amount) {
        System.out.println("Payment of Rs." + amount + " processed using Stripe.");
    }
}

// Adapter for PayPal
class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway paypal = new PayPalGateway();

    public void processPayment(double amount) {
        paypal.sendPayment(amount);
    }
}

// Adapter for Stripe
class StripeAdapter implements PaymentProcessor {
    private StripeGateway stripe = new StripeGateway();

    public void processPayment(double amount) {
        stripe.makePayment(amount);
    }
}

public class AdapterPatternExample {
    public static void main(String[] args) {

        PaymentProcessor payment1 = new PayPalAdapter();
        payment1.processPayment(1000);

        PaymentProcessor payment2 = new StripeAdapter();
        payment2.processPayment(2500);
    }
}