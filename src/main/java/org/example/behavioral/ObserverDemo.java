package org.example.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER (Behavioral)
 * ---------------------
 * Intent: Define a one-to-many dependency so that when one object (subject) changes state,
 *         all its dependents (observers) are notified and updated automatically.
 *
 * Use when:
 *   - A change to one object requires changing others, and you don't know how many or which ones.
 *   - You want loose coupling between the publisher and its subscribers.
 *
 * Interview points:
 *   - Push vs pull model (here we push the new state to observers).
 *   - Real-world: event listeners, pub/sub, Spring ApplicationEvent, RxJava.
 */
public class ObserverDemo {

    interface Observer {
        void update(int temperature);
    }

    /** Subject: maintains observers and notifies them on state change. */
    static class WeatherStation {
        private final List<Observer> observers = new ArrayList<>();
        private int temperature;

        void subscribe(Observer o)   { observers.add(o); }
        void unsubscribe(Observer o) { observers.remove(o); }

        void setTemperature(int temperature) {
            this.temperature = temperature;
            notifyObservers();
        }

        private void notifyObservers() {
            for (Observer o : observers) o.update(temperature);
        }
    }

    static class PhoneDisplay implements Observer {
        private final String name;
        PhoneDisplay(String name) { this.name = name; }
        public void update(int temperature) {
            System.out.println(name + " shows: " + temperature + "°C");
        }
    }

    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        Observer alice = new PhoneDisplay("Alice");
        Observer bob   = new PhoneDisplay("Bob");

        station.subscribe(alice);
        station.subscribe(bob);
        station.setTemperature(22);   // both notified

        station.unsubscribe(bob);
        station.setTemperature(25);   // only Alice notified
    }
}
