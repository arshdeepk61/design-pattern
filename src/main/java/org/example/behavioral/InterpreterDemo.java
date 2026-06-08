package org.example.behavioral;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * INTERPRETER (Behavioral)
 * ------------------------
 * Intent: Given a language, define a representation for its grammar along with an interpreter that
 *         uses the representation to interpret sentences in the language.
 *
 * Use when:
 *   - You have a simple, well-defined grammar to evaluate (config DSLs, rule engines, search
 *     filters, math/boolean expressions) and the grammar is stable and not too complex.
 *
 * Interview points:
 *   - Each grammar rule becomes a class implementing a common Expression interface; the parse tree
 *     (Abstract Syntax Tree) is a Composite of these expression nodes.
 *   - Terminal expressions = leaves (numbers); Non-terminal expressions = operations (add, sub).
 *   - For anything beyond a toy grammar, prefer a real parser/AST tool — Interpreter doesn't scale.
 */
public class InterpreterDemo {

    /** Abstract expression — every node in the AST can interpret() itself. */
    interface Expression {
        int interpret();
    }

    /** Terminal expression: a literal number (a leaf). */
    static class Number implements Expression {
        private final int value;
        Number(int value) { this.value = value; }
        public int interpret() { return value; }
    }

    /** Non-terminal expression: addition of two sub-expressions. */
    static class Add implements Expression {
        private final Expression left, right;
        Add(Expression left, Expression right) { this.left = left; this.right = right; }
        public int interpret() { return left.interpret() + right.interpret(); }
    }

    /** Non-terminal expression: subtraction of two sub-expressions. */
    static class Subtract implements Expression {
        private final Expression left, right;
        Subtract(Expression left, Expression right) { this.left = left; this.right = right; }
        public int interpret() { return left.interpret() - right.interpret(); }
    }

    /**
     * A tiny parser that builds the AST from a postfix (Reverse Polish) expression.
     * Postfix avoids precedence/parentheses handling so we can focus on the pattern itself.
     * Example: "5 3 + 2 -"  means  (5 + 3) - 2.
     */
    static Expression parse(String postfix) {
        Deque<Expression> stack = new ArrayDeque<>();
        for (String token : postfix.trim().split("\\s+")) {
            switch (token) {
                case "+" -> { Expression r = stack.pop(), l = stack.pop(); stack.push(new Add(l, r)); }
                case "-" -> { Expression r = stack.pop(), l = stack.pop(); stack.push(new Subtract(l, r)); }
                default  -> stack.push(new Number(Integer.parseInt(token)));
            }
        }
        return stack.pop();   // root of the AST
    }

    public static void main(String[] args) {
        String expr = "5 3 + 2 -";                 // (5 + 3) - 2
        Expression ast = parse(expr);
        System.out.println(expr + "  =>  " + ast.interpret());

        // Or build the AST directly: (10 - 4) + 7 = 13
        Expression manual = new Add(new Subtract(new Number(10), new Number(4)), new Number(7));
        System.out.println("(10 - 4) + 7  =>  " + manual.interpret());
    }
}
