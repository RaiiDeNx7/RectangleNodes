package org.sample.mavensample;
import java.util.List;
import java.util.ArrayList;

class LeafNode extends Node {
    private List<Rectangle> rectangles;

    public LeafNode() {
        this.rectangles = new ArrayList<>();
    }

    @Override
    public void insert(Rectangle data) {
        // Inserting data directly into the leaf node
        rectangles.add(data);
    }

    @Override
    public void delete(Rectangle data) {
        rectangles.remove(data);
    }

    @Override
    public void update(Rectangle oldData, Rectangle newData) {
        if (rectangles.contains(oldData)) {
            rectangles.remove(oldData);
            rectangles.add(newData);
        }
    }

    @Override
    public void dump(String indent) {
        System.out.println(indent + "LeafNode with " + rectangles.size() + " rectangles:");
        for (Rectangle rect : rectangles) {
            System.out.println(indent + "    " + rect);
        }
    }

    @Override
    public Rectangle find(double x, double y) {
        for (Rectangle rect : rectangles) {
            if (rect.x == x && rect.y == y) {
                return rect;
            }
        }
        return null;
    }
}