package org.example.behavioral;

/**
 * TEMPLATE METHOD (Behavioral)
 * ----------------------------
 * Intent: Define the skeleton of an algorithm in a base method, deferring some steps to
 *         subclasses. Subclasses redefine certain steps without changing the algorithm's structure.
 *
 * Use when:
 *   - Several classes share the same overall algorithm but differ in specific steps.
 *   - You want to avoid code duplication by pulling the invariant parts into a base class.
 *
 * Interview points:
 *   - The template method is usually `final` so subclasses can't change the algorithm's structure.
 *   - "Hook" methods provide optional override points with default behavior.
 */
public class TemplateMethodDemo {

    static abstract class DataExporter {
        /** The template method: fixed algorithm, variable steps. */
        public final void export() {
            openSource();
            String raw = readData();
            String formatted = format(raw);   // step varies per subclass
            write(formatted);
            closeSource();
        }

        private void openSource()  { System.out.println("Opening data source"); }
        private String readData()  { return "id=1;name=Arsh"; }
        private void closeSource() { System.out.println("Closing data source"); }

        protected abstract String format(String raw);   // subclasses fill this in

        protected void write(String formatted) {         // hook with default behavior
            System.out.println("Writing: " + formatted);
        }
    }

    static class JsonExporter extends DataExporter {
        protected String format(String raw) {
            String[] parts = raw.split(";");
            return "{ " + String.join(", ", parts).replace("=", ": ") + " }";
        }
    }

    static class CsvExporter extends DataExporter {
        protected String format(String raw) {
            return raw.replace(";", ",").replaceAll("\\w+=", "");
        }
    }

    public static void main(String[] args) {
        System.out.println("-- JSON --");
        new JsonExporter().export();
        System.out.println("-- CSV --");
        new CsvExporter().export();
    }
}
