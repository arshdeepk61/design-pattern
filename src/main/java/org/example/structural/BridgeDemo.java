package org.example.structural;

/**
 * BRIDGE (Structural)
 * -------------------
 * Intent: Decouple an abstraction from its implementation so the two can vary independently.
 *
 * Use when:
 *   - You have a class that varies along TWO (or more) independent dimensions and want to avoid a
 *     combinatorial explosion of subclasses (e.g. ShapeXColor: RedCircle, BlueCircle, RedSquare...).
 *   - You want to switch implementations at runtime.
 *
 * Interview points:
 *   - Abstraction holds a reference to an Implementor (composition = the "bridge").
 *   - The two hierarchies (abstraction: Shape; implementor: Renderer) grow separately:
 *     N shapes + M renderers = N+M classes, not N*M.
 *   - Compare to Adapter: Adapter makes existing things fit AFTER the fact; Bridge is designed
 *     up front to keep abstraction and implementation apart.
 */
public class BridgeDemo {

    /** Implementor hierarchy — the "how it's drawn" dimension. */
    interface Renderer {
        void renderCircle(float radius);
        void renderSquare(float side);
    }

    static class VectorRenderer implements Renderer {
        public void renderCircle(float radius) { System.out.println("Vector circle r=" + radius); }
        public void renderSquare(float side)   { System.out.println("Vector square s=" + side); }
    }

    static class RasterRenderer implements Renderer {
        public void renderCircle(float radius) { System.out.println("Raster pixels for circle r=" + radius); }
        public void renderSquare(float side)   { System.out.println("Raster pixels for square s=" + side); }
    }

    /** Abstraction hierarchy — the "what shape" dimension. Holds a Renderer (the bridge). */
    static abstract class Shape {
        protected final Renderer renderer;   // bridge to the implementor
        Shape(Renderer renderer) { this.renderer = renderer; }
        abstract void draw();
    }

    static class Circle extends Shape {
        private final float radius;
        Circle(Renderer renderer, float radius) { super(renderer); this.radius = radius; }
        void draw() { renderer.renderCircle(radius); }
    }

    static class Square extends Shape {
        private final float side;
        Square(Renderer renderer, float side) { super(renderer); this.side = side; }
        void draw() { renderer.renderSquare(side); }
    }

    public static void main(String[] args) {
        // Any shape can be combined with any renderer at runtime — no Shape*Renderer subclasses.
        new Circle(new VectorRenderer(), 5).draw();
        new Circle(new RasterRenderer(), 5).draw();
        new Square(new VectorRenderer(), 3).draw();
        new Square(new RasterRenderer(), 3).draw();
    }
}
