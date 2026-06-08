package org.example.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * COMPOSITE (Structural)
 * ----------------------
 * Intent: Compose objects into TREE structures to represent part-whole hierarchies.
 *         Lets clients treat individual objects (leaves) and compositions uniformly.
 *
 * Use when:
 *   - You have a tree of objects and want to apply an operation over the whole tree the same way
 *     you'd apply it to a single element (e.g. file/folder sizes, UI component trees).
 *
 * Interview points:
 *   - Both leaf and composite implement the same Component interface -> uniform treatment.
 */
public class CompositeDemo {

    interface FileSystemNode {
        int sizeKb();                 // common operation
        void print(String indent);
    }

    /** Leaf. */
    static class FileNode implements FileSystemNode {
        private final String name;
        private final int sizeKb;

        FileNode(String name, int sizeKb) { this.name = name; this.sizeKb = sizeKb; }

        public int sizeKb() { return sizeKb; }
        public void print(String indent) { System.out.println(indent + "- " + name + " (" + sizeKb + "kb)"); }
    }

    /** Composite: holds children, which may be files or more folders. */
    static class FolderNode implements FileSystemNode {
        private final String name;
        private final List<FileSystemNode> children = new ArrayList<>();

        FolderNode(String name) { this.name = name; }

        FolderNode add(FileSystemNode node) { children.add(node); return this; }

        public int sizeKb() {
            return children.stream().mapToInt(FileSystemNode::sizeKb).sum();   // recurse over tree
        }

        public void print(String indent) {
            System.out.println(indent + "+ " + name + "/ (" + sizeKb() + "kb total)");
            children.forEach(c -> c.print(indent + "   "));
        }
    }

    public static void main(String[] args) {
        FolderNode root = new FolderNode("root")
                .add(new FileNode("readme.md", 2))
                .add(new FolderNode("src")
                        .add(new FileNode("Main.java", 10))
                        .add(new FileNode("Util.java", 5)));

        root.print("");
        System.out.println("Total size: " + root.sizeKb() + "kb");
    }
}
