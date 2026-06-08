package org.example.behavioral;

/**
 * CHAIN OF RESPONSIBILITY (Behavioral)
 * ------------------------------------
 * Intent: Avoid coupling the sender of a request to its receiver by giving more than one object
 *         a chance to handle it. Chain the receivers and pass the request along until one handles it.
 *
 * Use when:
 *   - More than one object may handle a request and the handler isn't known in advance.
 *   - You want to issue a request to one of several handlers without specifying which explicitly.
 *
 * Interview points:
 *   - Each handler decides to process and/or forward to the next link.
 *   - Real-world: servlet filters, Spring Security filter chain, logging levels, middleware.
 */
public class ChainOfResponsibilityDemo {

    static abstract class Approver {
        protected Approver next;

        Approver linkTo(Approver next) { this.next = next; return next; }   // returns next for chaining

        void handle(double amount) {
            if (canApprove(amount)) {
                approve(amount);
            } else if (next != null) {
                next.handle(amount);          // pass it along
            } else {
                System.out.println("No one can approve $" + amount);
            }
        }

        protected abstract boolean canApprove(double amount);
        protected abstract void approve(double amount);
    }

    static class TeamLead extends Approver {
        protected boolean canApprove(double amount) { return amount <= 1_000; }
        protected void approve(double amount) { System.out.println("TeamLead approved $" + amount); }
    }

    static class Manager extends Approver {
        protected boolean canApprove(double amount) { return amount <= 10_000; }
        protected void approve(double amount) { System.out.println("Manager approved $" + amount); }
    }

    static class Director extends Approver {
        protected boolean canApprove(double amount) { return amount <= 100_000; }
        protected void approve(double amount) { System.out.println("Director approved $" + amount); }
    }

    public static void main(String[] args) {
        Approver lead = new TeamLead();
        lead.linkTo(new Manager()).linkTo(new Director());   // build the chain

        for (double amount : new double[]{500, 5_000, 50_000, 500_000}) {
            lead.handle(amount);
        }
    }
}
