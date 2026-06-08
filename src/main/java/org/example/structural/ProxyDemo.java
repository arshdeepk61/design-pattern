package org.example.structural;

/**
 * PROXY (Structural)
 * ------------------
 * Intent: Provide a SURROGATE or placeholder for another object to control access to it.
 *
 * Common flavors:
 *   - Virtual proxy: lazy-loads an expensive object on first use (shown here).
 *   - Protection proxy: enforces access control.
 *   - Remote proxy: represents an object in a different address space (RPC stubs).
 *
 * Interview points:
 *   - Proxy implements the SAME interface as the real subject, so it's transparent to clients.
 *   - Spring AOP creates dynamic proxies for @Transactional, @Cacheable, etc.
 */
public class ProxyDemo {

    interface Image {
        void display();
    }

    /** Real subject: expensive to create (simulates loading from disk). */
    static class HighResImage implements Image {
        private final String filename;

        HighResImage(String filename) {
            this.filename = filename;
            loadFromDisk();   // expensive
        }

        private void loadFromDisk() {
            System.out.println("Loading large image from disk: " + filename);
        }

        public void display() { System.out.println("Displaying " + filename); }
    }

    /** Virtual proxy: defers the expensive load until display() is actually called. */
    static class ImageProxy implements Image {
        private final String filename;
        private HighResImage real;   // created lazily

        ImageProxy(String filename) { this.filename = filename; }

        public void display() {
            if (real == null) {               // load only on first real use
                real = new HighResImage(filename);
            }
            real.display();
        }
    }

    public static void main(String[] args) {
        Image image = new ImageProxy("photo.png");
        System.out.println("Proxy created (no disk load yet)");
        image.display();   // triggers the load here
        image.display();   // already loaded -> no second load
    }
}
