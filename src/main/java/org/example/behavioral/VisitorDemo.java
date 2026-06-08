package org.example.behavioral;

import java.util.List;

/**
 * VISITOR (Behavioral)
 * --------------------
 * Intent: Represent an operation to be performed on the elements of an object structure. Visitor
 *         lets you define a new operation without changing the classes of the elements it operates on.
 *
 * Use when:
 *   - An object structure has many element types and you want to add operations over them
 *     without polluting the element classes (keeps elements stable, operations pluggable).
 *
 * Interview points:
 *   - "Double dispatch": element.accept(visitor) calls back visitor.visitX(this), so the right
 *     method is chosen by BOTH the element type and the visitor type.
 *   - Trade-off: easy to add new operations (new visitors), hard to add new element types
 *     (must touch every visitor). Opposite trade-off to plain polymorphism.
 */
public class VisitorDemo {

    interface Visitor {
        double visit(Book book);
        double visit(Electronic electronic);
    }

    interface Item {
        double accept(Visitor visitor);   // double dispatch entry point
    }

    static class Book implements Item {
        final double price;
        Book(double price) { this.price = price; }
        public double accept(Visitor v) { return v.visit(this); }
    }

    static class Electronic implements Item {
        final double price;
        Electronic(double price) { this.price = price; }
        public double accept(Visitor v) { return v.visit(this); }
    }

    /** A new operation = a new visitor, with NO change to Book/Electronic. */
    static class TaxVisitor implements Visitor {
        public double visit(Book book) { return book.price * 0.00; }            // books tax-free
        public double visit(Electronic e) { return e.price * 0.18; }            // 18% on electronics
    }

    static class ShippingVisitor implements Visitor {
        public double visit(Book book) { return 2.0; }
        public double visit(Electronic e) { return 5.0; }
    }

    public static void main(String[] args) {
        List<Item> cart = List.of(new Book(30), new Electronic(100), new Book(20));

        double tax = cart.stream().mapToDouble(i -> i.accept(new TaxVisitor())).sum();
        double shipping = cart.stream().mapToDouble(i -> i.accept(new ShippingVisitor())).sum();

        System.out.println("Total tax:      $" + tax);
        System.out.println("Total shipping: $" + shipping);
    }
}
