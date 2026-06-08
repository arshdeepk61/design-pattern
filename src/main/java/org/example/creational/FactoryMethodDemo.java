package org.example.creational;

/**
 * FACTORY METHOD (Creational)
 * ---------------------------
 * Intent: Define an interface for creating an object, but let subclasses (or a factory method)
 *         decide which concrete class to instantiate. Defers instantiation to subclasses.
 *
 * Use when:
 *   - A class can't anticipate the type of objects it must create.
 *   - You want to localize the "new" keyword so client code depends on an interface, not concretes.
 *
 * Interview points:
 *   - Factory Method vs Abstract Factory: Factory Method creates ONE product via inheritance;
 *     Abstract Factory creates FAMILIES of related products via composition.
 */
public class FactoryMethodDemo {

    interface Notification {
        void send(String message);
    }

    static class EmailNotification implements Notification {
        public void send(String message) { System.out.println("Email: " + message); }
    }

    static class SmsNotification implements Notification {
        public void send(String message) { System.out.println("SMS: " + message); }
    }

    static class PushNotification implements Notification {
        public void send(String message) { System.out.println("Push: " + message); }
    }

    enum Channel { EMAIL, SMS, PUSH }

    /** The factory method encapsulates the object-creation decision. */
    static class NotificationFactory {
        static Notification create(Channel channel) {
            return switch (channel) {
                case EMAIL -> new EmailNotification();
                case SMS   -> new SmsNotification();
                case PUSH  -> new PushNotification();
            };
        }
    }

    public static void main(String[] args) {
        for (Channel c : Channel.values()) {
            Notification n = NotificationFactory.create(c);   // client never calls `new` on a concrete type
            n.send("Your order has shipped");
        }
    }
}
