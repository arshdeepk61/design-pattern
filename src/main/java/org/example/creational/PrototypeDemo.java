package org.example.creational;

import java.util.ArrayList;
import java.util.List;

/**
 * PROTOTYPE (Creational)
 * ----------------------
 * Intent: Create new objects by COPYING an existing object (the prototype) rather than
 *         constructing from scratch.
 *
 * Use when:
 *   - Object creation is expensive (e.g. heavy initialization, DB/network load) and you
 *     already have a configured instance to clone.
 *   - You want to avoid a parallel hierarchy of factory classes.
 *
 * Interview points:
 *   - Shallow vs deep copy: a deep copy must clone nested mutable objects too, otherwise
 *     clones share references (mutating one affects the other).
 */
public class PrototypeDemo {

    interface Prototype<T> {
        T cloneDeep();
    }

    static class Document implements Prototype<Document> {
        private String title;
        private final List<String> sections;   // mutable -> needs deep copy

        Document(String title, List<String> sections) {
            this.title = title;
            this.sections = sections;
        }

        @Override
        public Document cloneDeep() {
            // Deep copy: new list so the clone doesn't share the original's sections.
            return new Document(this.title, new ArrayList<>(this.sections));
        }

        void setTitle(String title) { this.title = title; }
        void addSection(String s)   { sections.add(s); }

        @Override
        public String toString() { return title + " " + sections; }
    }

    public static void main(String[] args) {
        Document template = new Document("Template", new ArrayList<>(List.of("Intro", "Body")));

        Document copy = template.cloneDeep();
        copy.setTitle("Report 2026");
        copy.addSection("Conclusion");   // mutating the copy must NOT affect the template

        System.out.println("Original: " + template);  // sections unchanged -> proves deep copy
        System.out.println("Copy:     " + copy);
    }
}
