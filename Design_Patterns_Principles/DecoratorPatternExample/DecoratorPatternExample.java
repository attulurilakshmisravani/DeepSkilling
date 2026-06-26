interface Notifier {
    void send(String message);
}

// Concrete Component
class EmailNotifier implements Notifier {
    public void send(String message) {
        System.out.println("Email Notification: " + message);
    }
}

// Abstract Decorator
abstract class NotifierDecorator implements Notifier {
    protected Notifier notifier;

    public NotifierDecorator(Notifier notifier) {
        this.notifier = notifier;
    }

    public void send(String message) {
        notifier.send(message);
    }
}

// Concrete Decorator - SMS
class SMSNotifierDecorator extends NotifierDecorator {

    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    public void send(String message) {
        super.send(message);
        System.out.println("SMS Notification: " + message);
    }
}

// Concrete Decorator - Slack
class SlackNotifierDecorator extends NotifierDecorator {

    public SlackNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    public void send(String message) {
        super.send(message);
        System.out.println("Slack Notification: " + message);
    }
}

public class DecoratorPatternExample {
    public static void main(String[] args) {

        Notifier notifier = new EmailNotifier();

        System.out.println("Email Only:");
        notifier.send("Your order has been placed.");

        System.out.println();

        System.out.println("Email + SMS:");
        Notifier smsNotifier = new SMSNotifierDecorator(notifier);
        smsNotifier.send("Your order has been placed.");

        System.out.println();

        System.out.println("Email + SMS + Slack:");
        Notifier allNotifier = new SlackNotifierDecorator(
                                    new SMSNotifierDecorator(
                                        new EmailNotifier()));
        allNotifier.send("Your order has been placed.");
    }
}