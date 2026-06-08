package org.example.structural;

/**
 * ADAPTER (Structural)
 * --------------------
 * Intent: Convert the interface of a class into another interface clients expect.
 *         Lets classes work together that couldn't otherwise because of incompatible interfaces.
 *
 * Use when:
 *   - You want to reuse an existing (often third-party / legacy) class whose interface
 *     doesn't match what your code needs.
 *
 * Interview points:
 *   - Object adapter (composition, shown here) vs class adapter (inheritance).
 *   - Real-world: java.util.Arrays.asList, InputStreamReader (bytes -> chars).
 */
public class AdapterDemo {

    // Target: the interface our application expects.
    interface PaymentProcessor {
        void pay(int amountCents);
    }

    // Adaptee: an existing class with an incompatible interface (e.g. a third-party SDK).
    static class LegacyStripeApi {
        void makePayment(double dollars) {
            System.out.println("Stripe charged $" + dollars);
        }
    }

    // Adapter: makes the adaptee conform to the target interface.
    static class StripeAdapter implements PaymentProcessor {
        private final LegacyStripeApi stripe;

        StripeAdapter(LegacyStripeApi stripe) { this.stripe = stripe; }

        @Override
        public void pay(int amountCents) {
            stripe.makePayment(amountCents / 100.0);   // translate the call
        }
    }

    static void checkout(PaymentProcessor processor) {
        processor.pay(2599);   // client speaks only "PaymentProcessor"
    }

    public static void main(String[] args) {
        checkout(new StripeAdapter(new LegacyStripeApi()));
    }
}
