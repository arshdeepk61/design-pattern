package org.example.structural;

/**
 * DECORATOR (Structural)
 * ----------------------
 * Intent: Attach additional responsibilities to an object DYNAMICALLY. Decorators provide a
 *         flexible alternative to subclassing for extending functionality.
 *
 * Use when:
 *   - You want to add behavior to individual objects without affecting others of the same class.
 *   - Subclassing would cause a combinatorial explosion of classes.
 *
 * Interview points:
 *   - A decorator implements the same interface as the component AND wraps an instance of it.
 *   - Real-world: java.io streams (new BufferedReader(new FileReader(...))).
 */
public class DecoratorDemo {

    interface Coffee {
        String description();
        double cost();
    }

    static class SimpleCoffee implements Coffee {
        public String description() { return "Coffee"; }
        public double cost() { return 2.00; }
    }

    /** Base decorator: wraps a Coffee and delegates by default. */
    static abstract class CoffeeDecorator implements Coffee {
        protected final Coffee inner;
        CoffeeDecorator(Coffee inner) { this.inner = inner; }
    }

    static class Milk extends CoffeeDecorator {
        Milk(Coffee inner) { super(inner); }
        public String description() { return inner.description() + " + Milk"; }
        public double cost() { return inner.cost() + 0.50; }
    }

    static class Sugar extends CoffeeDecorator {
        Sugar(Coffee inner) { super(inner); }
        public String description() { return inner.description() + " + Sugar"; }
        public double cost() { return inner.cost() + 0.25; }
    }

    static class WhippedCream extends CoffeeDecorator {
        WhippedCream(Coffee inner) { super(inner); }
        public String description() { return inner.description() + " + WhippedCream"; }
        public double cost() { return inner.cost() + 0.75; }
    }

    public static void main(String[] args) {
        // Stack decorators dynamically at runtime.
        Coffee order = new WhippedCream(new Milk(new Sugar(new SimpleCoffee())));
        System.out.println(order.description() + " = $" + order.cost());
    }
}
