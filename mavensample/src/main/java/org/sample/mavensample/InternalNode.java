package org.sample.mavensample;



//InternalNode class which extends Node
//InternalNode class representing an internal node in the Quadtree
class InternalNode extends Node {
    Node[] children;

    public InternalNode(double x, double y, double width, double height) {
        super(new Rectangle(x, y, width, height));
        children = new Node[4];
    }

    public void subdivide() {
        double x = boundary.x;
        double y = boundary.y;
        double w = boundary.width / 2;
        double h = boundary.height / 2;

        // SW, NW, SE, NE
        children[0] = new LeafNode(x, y + h, w, h);       // SW
        children[1] = new LeafNode(x, y, w, h);           // NW
        children[2] = new LeafNode(x + w, y + h, w, h);   // SE
        children[3] = new LeafNode(x + w, y, w, h);       // NE
    }

    @Override
    public void insert(Rectangle r) {
        if (!boundary.contains(r.x, r.y)) return;

        // Subdivide if needed
        if (children[0] == null) {
            subdivide();
        }

        // Insert into the appropriate child node
        for (Node child : children) {
            if (child.boundary.contains(r.x, r.y)) {
                child.insert(r);
                break; // Insert into only one child
            }
        }
    }

    @Override
    public void print(int depth) {
        String indent = "    ".repeat(depth);
        System.out.println(indent + "Internal Node - " + boundary);

        // Correct print order: SW, NW, SE, NE
        children[0].print(depth + 1);  // SW
        children[1].print(depth + 1);  // NW
        children[2].print(depth + 1);  // SE
        children[3].print(depth + 1);  // NE
    }

    @Override
    public void delete(double x, double y) {
        for (Node child : children) {
            if (child.boundary.contains(x, y)) {
                child.delete(x, y);
                break;
            }
        }
    }

    @Override
    public Rectangle find(double x, double y) {
        for (Node child : children) {
            if (child.boundary.contains(x, y)) {
                return child.find(x, y);
            }
        }
        return null;
    }
}