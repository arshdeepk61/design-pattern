package org.example;

import org.example.behavioral.*;
import org.example.creational.*;
import org.example.structural.*;

/**
 * Entry point that runs every design-pattern demo in sequence.
 *
 * Each pattern also has its own main() method, so you can run any single demo directly
 * (e.g. run StrategyDemo) for focused study. This class just chains them all together.
 */
public class Main {

    public static void main(String[] args) {
        section("CREATIONAL PATTERNS");
        run("Singleton",        SingletonDemo::main);
        run("Factory Method",   FactoryMethodDemo::main);
        run("Abstract Factory", AbstractFactoryDemo::main);
        run("Builder",          BuilderDemo::main);
        run("Prototype",        PrototypeDemo::main);

        section("STRUCTURAL PATTERNS");
        run("Adapter",   AdapterDemo::main);
        run("Decorator", DecoratorDemo::main);
        run("Facade",    FacadeDemo::main);
        run("Proxy",     ProxyDemo::main);
        run("Composite", CompositeDemo::main);
        run("Flyweight", FlyweightDemo::main);
        run("Bridge",    BridgeDemo::main);

        section("BEHAVIORAL PATTERNS");
        run("Strategy",                StrategyDemo::main);
        run("Observer",                ObserverDemo::main);
        run("Command",                 CommandDemo::main);
        run("Template Method",         TemplateMethodDemo::main);
        run("State",                   StateDemo::main);
        run("Chain of Responsibility", ChainOfResponsibilityDemo::main);
        run("Iterator",                IteratorDemo::main);
        run("Mediator",                MediatorDemo::main);
        run("Memento",                 MementoDemo::main);
        run("Visitor",                 VisitorDemo::main);
    }

    @FunctionalInterface
    private interface Demo { void run(String[] args); }

    private static void run(String name, Demo demo) {
        System.out.println("\n### " + name + " ###");
        demo.run(new String[0]);
    }

    private static void section(String title) {
        System.out.println("\n==================== " + title + " ====================");
    }
}
