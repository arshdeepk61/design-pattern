package org.example.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * MEDIATOR (Behavioral)
 * ---------------------
 * Intent: Define an object that encapsulates how a set of objects interact. Mediator promotes
 *         loose coupling by keeping objects from referring to each other explicitly, letting you
 *         vary their interaction independently.
 *
 * Use when:
 *   - Many objects communicate in complex, tangled ways (many-to-many references).
 *   - You want to centralize control of how a set of components collaborate.
 *
 * Interview points:
 *   - Turns a many-to-many web of dependencies into a many-to-one (each colleague knows only
 *     the mediator). Real-world: chat rooms, air-traffic control, UI dialogs coordinating widgets.
 */
public class MediatorDemo {

    interface ChatMediator {
        void register(User user);
        void send(String message, User from);
    }

    /** Concrete mediator: knows all participants and routes messages between them. */
    static class ChatRoom implements ChatMediator {
        private final List<User> users = new ArrayList<>();

        public void register(User user) { users.add(user); }

        public void send(String message, User from) {
            for (User u : users) {
                if (u != from) u.receive(message, from.name);   // sender doesn't get its own message
            }
        }
    }

    /** Colleague: talks only to the mediator, never directly to other colleagues. */
    static class User {
        private final String name;
        private final ChatMediator mediator;

        User(String name, ChatMediator mediator) {
            this.name = name;
            this.mediator = mediator;
            mediator.register(this);
        }

        void send(String message) {
            System.out.println(name + " sends: " + message);
            mediator.send(message, this);
        }

        void receive(String message, String from) {
            System.out.println("  " + name + " got from " + from + ": " + message);
        }
    }

    public static void main(String[] args) {
        ChatMediator room = new ChatRoom();
        User alice = new User("Alice", room);
        User bob   = new User("Bob", room);
        User carol = new User("Carol", room);

        alice.send("Hi everyone!");
        bob.send("Hey Alice!");
    }
}
