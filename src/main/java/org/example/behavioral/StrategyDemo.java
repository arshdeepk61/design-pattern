package org.example.behavioral;

/**
 * STRATEGY (Behavioral)
 * ---------------------
 * Intent: Define a family of algorithms, encapsulate each one, and make them interchangeable.
 *         Strategy lets the algorithm vary independently from clients that use it.
 *
 * Use when:
 *   - You have several variants of an algorithm and want to switch between them at runtime.
 *   - You want to replace big if/else or switch blocks on "type" with polymorphism.
 *
 * Interview points:
 *   - Favor composition over inheritance: the context HOLDS a strategy rather than subclassing.
 *   - In Java a strategy is often just a lambda / functional interface.
 */
public class StrategyDemo {

    @FunctionalInterface
    interface ShippingStrategy {
        double cost(double weightKg);
    }

    static class ShippingCalculator {
        private ShippingStrategy strategy;

        ShippingCalculator(ShippingStrategy strategy) { this.strategy = strategy; }

        void setStrategy(ShippingStrategy strategy) { this.strategy = strategy; }

        double calculate(double weightKg) { return strategy.cost(weightKg); }
    }

    public static void main(String[] args) {
        ShippingStrategy standard = w -> 5.0 + w * 1.0;
        ShippingStrategy express  = w -> 10.0 + w * 2.5;
        ShippingStrategy free     = w -> 0.0;

        ShippingCalculator calc = new ShippingCalculator(standard);
        System.out.println("Standard: $" + calc.calculate(3));

        calc.setStrategy(express);     // swap algorithm at runtime
        System.out.println("Express:  $" + calc.calculate(3));

        calc.setStrategy(free);
        System.out.println("Free:     $" + calc.calculate(3));
    }
}
