# Design Patterns in Java

Runnable, heavily-commented examples of the most important **Gang of Four** design patterns,
organized by category. Built for interview prep — each pattern is self-contained with its own
`main()` method so you can run and study any one in isolation.

- **Java 21**, Maven, no external dependencies.
- Every file has a header comment: **intent**, **when to use**, and **interview points**.

## Run

Run everything at once:

```bash
mvn -q compile exec:java -Dexec.mainClass=org.example.Main
```

Or run any single pattern directly from your IDE (each `*Demo` class has a `main()`), e.g. `StrategyDemo`.

> No `exec` plugin configured? You can also compile and run with plain `javac`/`java`, or just
> hit ▶ next to any `main()` in IntelliJ.

## Patterns covered

### Creational — *how objects get created*
| Pattern | One-liner | File |
|---|---|---|
| Singleton | One instance, global access (holder idiom + double-checked locking) | `creational/SingletonDemo.java` |
| Factory Method | Defer instantiation to a creation method | `creational/FactoryMethodDemo.java` |
| Abstract Factory | Create families of related objects | `creational/AbstractFactoryDemo.java` |
| Builder | Step-by-step construction of complex/immutable objects | `creational/BuilderDemo.java` |
| Prototype | Create new objects by cloning existing ones | `creational/PrototypeDemo.java` |

### Structural — *how objects are composed*
| Pattern | One-liner | File |
|---|---|---|
| Adapter | Make incompatible interfaces work together | `structural/AdapterDemo.java` |
| Decorator | Add behavior dynamically by wrapping | `structural/DecoratorDemo.java` |
| Facade | One simple interface over a complex subsystem | `structural/FacadeDemo.java` |
| Proxy | A stand-in that controls access (lazy/virtual proxy) | `structural/ProxyDemo.java` |
| Composite | Treat part-whole tree hierarchies uniformly | `structural/CompositeDemo.java` |

### Behavioral — *how objects interact*
| Pattern | One-liner | File |
|---|---|---|
| Strategy | Interchangeable algorithms chosen at runtime | `behavioral/StrategyDemo.java` |
| Observer | One-to-many change notification (pub/sub) | `behavioral/ObserverDemo.java` |
| Command | Encapsulate a request as an object (with undo) | `behavioral/CommandDemo.java` |
| Template Method | Fixed algorithm skeleton, subclass fills steps | `behavioral/TemplateMethodDemo.java` |
| State | Behavior changes with internal state | `behavioral/StateDemo.java` |
| Chain of Responsibility | Pass a request along a chain of handlers | `behavioral/ChainOfResponsibilityDemo.java` |
| Iterator | Sequential access without exposing internals | `behavioral/IteratorDemo.java` |

## Interview cheatsheet

- **Factory Method vs Abstract Factory** — Factory Method creates *one* product via inheritance;
  Abstract Factory creates *families* of products via composition.
- **Strategy vs State** — same structure; Strategy swaps an algorithm, State models transitions
  between states (states can change the context's current state).
- **Decorator vs Proxy** — both wrap and share the subject's interface; Decorator *adds behavior*,
  Proxy *controls access* (lazy load, permissions, remoting).
- **Decorator vs Inheritance** — Decorator adds responsibilities at runtime to individual objects,
  avoiding a class explosion from static subclassing.
- **Adapter vs Facade** — Adapter changes one interface to another expected one; Facade invents a
  new simpler interface over many classes.
- **Where you've already seen them** — `java.io` streams (Decorator), `Iterator/Iterable` (Iterator),
  Spring AOP `@Transactional` (Proxy), Spring Security filter chain (Chain of Responsibility),
  `ApplicationEvent` (Observer), `StringBuilder`/Lombok `@Builder` (Builder).
