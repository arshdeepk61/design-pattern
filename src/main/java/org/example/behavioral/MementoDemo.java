package org.example.behavioral;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * MEMENTO (Behavioral)
 * --------------------
 * Intent: Without violating encapsulation, capture and externalize an object's internal state so
 *         that the object can be restored to this state later (undo/rollback).
 *
 * Use when:
 *   - You need snapshots to support undo/redo or checkpoints.
 *   - You want to capture state without exposing the object's internals.
 *
 * Roles:
 *   - Originator: creates a memento of its state and can restore from one.
 *   - Memento: immutable snapshot (opaque to everyone except the originator).
 *   - Caretaker: stores mementos but never inspects/modifies them.
 *
 * Interview points:
 *   - Distinct from Command-based undo: Memento restores a whole saved state rather than
 *     replaying inverse operations.
 */
public class MementoDemo {

    /** Memento: immutable snapshot. Kept opaque to the caretaker. */
    static final class EditorMemento {
        private final String content;
        private EditorMemento(String content) { this.content = content; }
    }

    /** Originator. */
    static class TextEditor {
        private String content = "";

        void type(String text) { content += text; }
        String getContent() { return content; }

        EditorMemento save() { return new EditorMemento(content); }      // snapshot
        void restore(EditorMemento m) { this.content = m.content; }      // rollback
    }

    /** Caretaker: holds the history, never peeks inside a memento. */
    static class History {
        private final Deque<EditorMemento> stack = new ArrayDeque<>();
        void push(EditorMemento m) { stack.push(m); }
        EditorMemento pop() { return stack.pop(); }
        boolean isEmpty() { return stack.isEmpty(); }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        History history = new History();

        editor.type("Hello");
        history.push(editor.save());     // checkpoint 1

        editor.type(", World");
        history.push(editor.save());     // checkpoint 2

        editor.type("!!! oops");
        System.out.println("Current: " + editor.getContent());

        editor.restore(history.pop());   // undo to checkpoint 2
        System.out.println("Undo 1:  " + editor.getContent());

        editor.restore(history.pop());   // undo to checkpoint 1
        System.out.println("Undo 2:  " + editor.getContent());
    }
}
