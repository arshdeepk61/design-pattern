package org.example.creational;

/**
 * ABSTRACT FACTORY (Creational)
 * -----------------------------
 * Intent: Provide an interface for creating FAMILIES of related objects without specifying
 *         their concrete classes.
 *
 * Use when:
 *   - Your system must be independent of how its products are created and composed.
 *   - You need to enforce that products from the same family are used together
 *     (e.g. all "dark theme" widgets, or all "Windows" UI controls).
 *
 * Interview points:
 *   - Each factory produces a consistent set of products; swapping the factory swaps the whole family.
 */
public class AbstractFactoryDemo {

    // --- Product interfaces ---
    interface Button { void render(); }
    interface Checkbox { void render(); }

    // --- "Light" family ---
    static class LightButton implements Button {
        public void render() { System.out.println("Render LIGHT button"); }
    }
    static class LightCheckbox implements Checkbox {
        public void render() { System.out.println("Render LIGHT checkbox"); }
    }

    // --- "Dark" family ---
    static class DarkButton implements Button {
        public void render() { System.out.println("Render DARK button"); }
    }
    static class DarkCheckbox implements Checkbox {
        public void render() { System.out.println("Render DARK checkbox"); }
    }

    // --- Abstract factory ---
    interface ThemeFactory {
        Button createButton();
        Checkbox createCheckbox();
    }

    static class LightThemeFactory implements ThemeFactory {
        public Button createButton() { return new LightButton(); }
        public Checkbox createCheckbox() { return new LightCheckbox(); }
    }

    static class DarkThemeFactory implements ThemeFactory {
        public Button createButton() { return new DarkButton(); }
        public Checkbox createCheckbox() { return new DarkCheckbox(); }
    }

    /** Client works only with abstract factory + abstract products -> family stays consistent. */
    static void renderUI(ThemeFactory factory) {
        factory.createButton().render();
        factory.createCheckbox().render();
    }

    public static void main(String[] args) {
        System.out.println("-- Light theme --");
        renderUI(new LightThemeFactory());
        System.out.println("-- Dark theme --");
        renderUI(new DarkThemeFactory());
    }
}
