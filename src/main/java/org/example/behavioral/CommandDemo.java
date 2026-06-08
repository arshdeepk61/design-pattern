package org.example.behavioral;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * COMMAND (Behavioral)
 * --------------------
 * Intent: Encapsulate a request as an object, thereby letting you parameterize clients with
 *         different requests, queue or log requests, and support undoable operations.
 *
 * Use when:
 *   - You want to decouple the object that invokes an operation from the one that performs it.
 *   - You need undo/redo, queuing, or logging of operations.
 *
 * Interview points:
 *   - Key roles: Command, ConcreteCommand, Receiver (does the work), Invoker (triggers it).
 *   - Undo is supported by giving each command an inverse operation.
 */
public class CommandDemo {

    interface Command {
        void execute();
        void undo();
    }

    /** Receiver: knows how to perform the actual work. */
    static class TextEditor {
        private final StringBuilder text = new StringBuilder();

        void append(String s) { text.append(s); }
        void deleteLast(int n) { text.delete(text.length() - n, text.length()); }
        public String toString() { return text.toString(); }
    }

    static class AppendCommand implements Command {
        private final TextEditor editor;
        private final String value;

        AppendCommand(TextEditor editor, String value) { this.editor = editor; this.value = value; }

        public void execute() { editor.append(value); }
        public void undo()    { editor.deleteLast(value.length()); }
    }

    /** Invoker: runs commands and keeps a history for undo. */
    static class Invoker {
        private final Deque<Command> history = new ArrayDeque<>();

        void run(Command cmd) { cmd.execute(); history.push(cmd); }

        void undoLast() {
            if (!history.isEmpty()) history.pop().undo();
        }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        Invoker invoker = new Invoker();

        invoker.run(new AppendCommand(editor, "Hello"));
        invoker.run(new AppendCommand(editor, " World"));
        System.out.println("After typing: '" + editor + "'");

        invoker.undoLast();
        System.out.println("After undo:   '" + editor + "'");
    }
}
