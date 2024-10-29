package org.sample.mavensample;
import java.util.List;
import java.util.ArrayList;

//LeafNode class representing a leaf node in the Quadtree
class LeafNode extends Node {
    List<Rectangle> rectangles;

    public LeafNode(double x, double y, double width, double height) {
        super(new Rectangle(x, y, width, height));
        rectangles = new ArrayList<>();
    }

    @Override
    public void insert(Rectangle r) {
        if (!boundary.contains(r.x, r.y)) return;

        // Insert rectangle if not already present
        if (!rectangles.contains(r)) {
            rectangles.add(r);
        }
    }

    @Override
    public void print(int depth) {
        String indent = "    ".repeat(depth);
        System.out.println(indent + "Leaf Node - " + boundary);
        for (Rectangle r : rectangles) {
            System.out.println(indent + "    " + r);
        }
    }

    @Override
    public void delete(double x, double y) {
        rectangles.removeIf(r -> r.contains(x, y));
    }

    @Override
    public Rectangle find(double x, double y) {
        for (Rectangle r : rectangles) {
            if (r.contains(x, y)) {
                return r;
            }
        }
        return null;
    }
}