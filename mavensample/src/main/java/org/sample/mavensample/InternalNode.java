package org.sample.mavensample;



//InternalNode class which extends Node
//InternalNode class representing an internal node in the Quadtree
class InternalNode extends Node {
    Node[] children;

    public InternalNode(double x, double y, double width, double height) {
        super(new Rectangle(x, y, width, height));
        children = new Node[4];
    }

    // Subdivide the current node into four quadrants
    public void subdivide() {
        double x = boundary.x;
        double y = boundary.y;
        double w = boundary.width / 2;
        double h = boundary.height / 2;

        // Create four children: SW, NW, SE, NE
        children[0] = new LeafNode(x, y + h, w, h);       // SW (South-West)
        children[1] = new LeafNode(x, y, w, h);           // NW (North-West)
        children[2] = new LeafNode(x + w, y + h, w, h);   // SE (South-East)
        children[3] = new LeafNode(x + w, y, w, h);       // NE (North-East)
    }

    @Override
    public void insert(Rectangle r) {
        if (!boundary.contains(r.x, r.y)) return;

        // Subdivide if children do not exist
        if (children[0] == null) {
            subdivide();
        }

        // Insert into the appropriate child node
        for (Node child : children) {
            if (child.boundary.contains(r.x, r.y)) {
                child.insert(r);
                return; // Insert into only one child
            }
        }
    }

    @Override
    public void print(int depth) {
        String indent = "    ".repeat(depth);
        System.out.println(indent + "Internal Node - " + boundary);

        // Print the children in the correct order: SW, NW, SE, NE
        if (children[0] != null) {  // Ensure children have been subdivided
            children[0].print(depth + 1);  // SW
            children[1].print(depth + 1);  // NW
            children[2].print(depth + 1);  // SE
            children[3].print(depth + 1);  // NE
        }
    }

    @Override
    public void delete(double x, double y) {
        if (children[0] == null) return; // No children means nothing to delete

        // Delegate delete to the appropriate child node
        for (Node child : children) {
            if (child.boundary.contains(x, y)) {
                child.delete(x, y);
                return; // Stop after deleting from the correct child
            }
        }
    }

    @Override
    public Rectangle find(double x, double y) {
        if (children[0] == null) return null; // No children means no rectangles to find

        // Search for the rectangle in the appropriate child node
        for (Node child : children) {
            if (child.boundary.contains(x, y)) {
                return child.find(x, y); // Return the found rectangle
            }
        }
        return null; // Rectangle not found
    }
    
    public boolean isEmpty() {
        for (Node child : children) {
            if (child instanceof LeafNode && !((LeafNode) child).rectangles.isEmpty()) {
                return false; // Found a non-empty leaf node
            }
        }
        return true; // All child nodes are empty
    }
}
