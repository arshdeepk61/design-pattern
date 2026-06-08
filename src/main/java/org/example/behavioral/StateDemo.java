package org.example.behavioral;

/**
 * STATE (Behavioral)
 * ------------------
 * Intent: Allow an object to alter its behavior when its internal state changes.
 *         The object will appear to change its class.
 *
 * Use when:
 *   - An object's behavior depends on its state and must change at runtime.
 *   - You have large conditionals that select behavior based on the object's current state.
 *
 * Interview points:
 *   - Each state is a class implementing the same interface; the context delegates to the
 *     current state object, which can transition the context to another state.
 *   - Compare to Strategy: same structure, but State models transitions between states.
 */
public class StateDemo {

    interface State {
        void insertCoin(VendingMachine m);
        void dispense(VendingMachine m);
    }

    static class VendingMachine {
        private State state = new NoCoinState();
        void setState(State state) { this.state = state; }
        void insertCoin() { state.insertCoin(this); }
        void dispense()   { state.dispense(this); }
    }

    static class NoCoinState implements State {
        public void insertCoin(VendingMachine m) {
            System.out.println("Coin accepted");
            m.setState(new HasCoinState());
        }
        public void dispense(VendingMachine m) {
            System.out.println("Insert a coin first");
        }
    }

    static class HasCoinState implements State {
        public void insertCoin(VendingMachine m) {
            System.out.println("Coin already inserted");
        }
        public void dispense(VendingMachine m) {
            System.out.println("Dispensing product");
            m.setState(new NoCoinState());
        }
    }

    public static void main(String[] args) {
        VendingMachine m = new VendingMachine();
        m.dispense();      // No coin -> rejected
        m.insertCoin();    // -> HasCoin
        m.insertCoin();    // already inserted
        m.dispense();      // dispense -> back to NoCoin
    }
}
