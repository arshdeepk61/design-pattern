package org.example.structural;

import java.util.HashMap;
import java.util.Map;

/**
 * FLYWEIGHT (Structural)
 * ----------------------
 * Intent: Use sharing to support large numbers of fine-grained objects efficiently by separating
 *         intrinsic (shared, state-independent) data from extrinsic (per-use, context) data.
 *
 * Use when:
 *   - An application uses a huge number of objects that would be costly in memory if each were
 *     distinct, and most of their state can be made extrinsic and shared.
 *
 * Interview points:
 *   - Intrinsic state lives inside the flyweight and is shared; extrinsic state is passed in by
 *     the client at call time. A factory caches and reuses flyweights.
 *   - Real-world: Java's Integer.valueOf cache (-128..127), String pool, glyphs in a text editor.
 */
public class FlyweightDemo {

    /** Flyweight: stores only INTRINSIC (shared) state — the visual style of a tree. */
    static final class TreeType {
        private final String name;
        private final String color;
        private final String texture;

        TreeType(String name, String color, String texture) {
            this.name = name;
            this.color = color;
            this.texture = texture;
            System.out.println("  (created new TreeType: " + name + "/" + color + ")");
        }

        // x, y are EXTRINSIC state — passed in, not stored, so the type can be shared.
        void draw(int x, int y) {
            System.out.println("Draw " + name + " (" + color + ") at (" + x + "," + y + ")");
        }
    }

    /** Factory: caches and reuses flyweights so identical types share one instance. */
    static class TreeFactory {
        private final Map<String, TreeType> cache = new HashMap<>();

        TreeType getTreeType(String name, String color, String texture) {
            String key = name + "|" + color + "|" + texture;
            return cache.computeIfAbsent(key, k -> new TreeType(name, color, texture));
        }

        int distinctTypes() { return cache.size(); }
    }

    public static void main(String[] args) {
        TreeFactory factory = new TreeFactory();

        // Plant 5 trees but only 2 distinct visual types -> only 2 flyweights created.
        int[][] positions = {{1, 1}, {2, 5}, {9, 3}, {4, 8}, {7, 2}};
        String[] kinds = {"Oak", "Pine", "Oak", "Oak", "Pine"};

        for (int i = 0; i < positions.length; i++) {
            String kind = kinds[i];
            TreeType type = factory.getTreeType(kind, kind.equals("Oak") ? "green" : "dark-green", "tex");
            type.draw(positions[i][0], positions[i][1]);
        }

        System.out.println("Trees planted: " + positions.length +
                ", TreeType objects in memory: " + factory.distinctTypes());
    }
}
