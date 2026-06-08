package org.example.creational;

/**
 * BUILDER (Creational)
 * --------------------
 * Intent: Separate the construction of a complex object from its representation, so the same
 *         construction process can build different representations. Lets you build step by step.
 *
 * Use when:
 *   - An object has many optional parameters (avoids the "telescoping constructor" anti-pattern).
 *   - You want immutable objects with readable, fluent construction.
 *
 * Interview points:
 *   - Fluent builder returns `this` for chaining; the final build() validates and constructs.
 *   - The product can be immutable (final fields, no setters).
 */
public class BuilderDemo {

    /** Immutable product built via a fluent builder. */
    static final class HttpRequest {
        private final String url;          // required
        private final String method;       // optional (default GET)
        private final String body;         // optional
        private final int timeoutMs;       // optional

        private HttpRequest(Builder b) {
            this.url = b.url;
            this.method = b.method;
            this.body = b.body;
            this.timeoutMs = b.timeoutMs;
        }

        @Override
        public String toString() {
            return method + " " + url + " (timeout=" + timeoutMs + "ms, body=" + body + ")";
        }

        static Builder builder(String url) {
            return new Builder(url);
        }

        static final class Builder {
            private final String url;            // required -> constructor arg
            private String method = "GET";
            private String body = null;
            private int timeoutMs = 30_000;

            private Builder(String url) {
                if (url == null || url.isBlank()) {
                    throw new IllegalArgumentException("url is required");
                }
                this.url = url;
            }

            Builder method(String method) { this.method = method; return this; }
            Builder body(String body)     { this.body = body; return this; }
            Builder timeoutMs(int ms)     { this.timeoutMs = ms; return this; }

            HttpRequest build() {
                return new HttpRequest(this);
            }
        }
    }

    public static void main(String[] args) {
        HttpRequest get = HttpRequest.builder("https://api.example.com/users").build();
        HttpRequest post = HttpRequest.builder("https://api.example.com/users")
                .method("POST")
                .body("{\"name\":\"Arsh\"}")
                .timeoutMs(5_000)
                .build();

        System.out.println(get);
        System.out.println(post);
    }
}
