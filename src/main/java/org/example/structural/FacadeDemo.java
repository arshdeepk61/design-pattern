package org.example.structural;

/**
 * FACADE (Structural)
 * -------------------
 * Intent: Provide a unified, simplified interface to a set of interfaces in a subsystem.
 *         Defines a higher-level interface that makes the subsystem easier to use.
 *
 * Use when:
 *   - You want to hide the complexity of a subsystem behind one simple entry point.
 *   - You want to decouple clients from the many moving parts of a subsystem.
 *
 * Interview points:
 *   - Facade doesn't add behavior; it orchestrates existing subsystem classes.
 *   - Clients can still use the subsystem directly if they need fine-grained control.
 */
public class FacadeDemo {

    // --- Complex subsystem ---
    static class InventoryService {
        boolean inStock(String sku) { System.out.println("Check stock: " + sku); return true; }
    }
    static class PaymentService {
        boolean charge(String account, double amount) {
            System.out.println("Charge " + account + ": $" + amount); return true;
        }
    }
    static class ShippingService {
        void ship(String sku, String address) {
            System.out.println("Ship " + sku + " to " + address);
        }
    }

    // --- Facade: one simple method orchestrates the whole flow ---
    static class OrderFacade {
        private final InventoryService inventory = new InventoryService();
        private final PaymentService payment = new PaymentService();
        private final ShippingService shipping = new ShippingService();

        boolean placeOrder(String sku, String account, double price, String address) {
            if (!inventory.inStock(sku)) {
                System.out.println("Out of stock");
                return false;
            }
            if (!payment.charge(account, price)) {
                System.out.println("Payment failed");
                return false;
            }
            shipping.ship(sku, address);
            System.out.println("Order complete!");
            return true;
        }
    }

    public static void main(String[] args) {
        new OrderFacade().placeOrder("BOOK-42", "acct-007", 19.99, "221B Baker St");
    }
}
