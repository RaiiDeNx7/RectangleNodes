package org.sample.mavensample;



//InternalNode class which extends Node
//InternalNode class representing an internal node in the Quadtree
class InternalNode extends Node {
    Node[] children;

    /**
     * Default InternalNode Constructor
     * Description: Constructor that initializes an InternalNode with a specific boundary and an array of four null child nodes.
     * 
     * @param x (double): The x-coordinate of the bottom-left corner
     * @param y (double): The y-coordinate of the bottom-left corner
     * @param width (double): The width of the rectangle
     * @param height (double): The height of the rectangle
     */
    public InternalNode(double x, double y, double width, double height) {
        super(new Rectangle(x, y, width, height));
        children = new Node[4];
    }

    /**
     * Description: Divides the current InternalNode into four LeafNode children, 
     * each representing one quadrant (SW, NW, SE, NE) of the current node’s boundary. 
     * Each child node is assigned half the width and height of the current node’s boundary.
     */
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

    /**
     * Description: Inserts a rectangle r into the appropriate child node. 
     * If the InternalNode has no child nodes, it first calls subdivide() to create child nodes.
     * The rectangle is only inserted into one child node, based on its location.
     * 
     * @param r (Rectangle): The rectangle to be inserted.
     */
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

    /**
     * Description: Prints the structure of the InternalNode, showing the boundary of the node and each child node, 
     * with the specified depth determining the level of indentation for output formatting.
     * 
     * @param  depth (integer): The depth level of the node, used for indentation.
     */
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

    /**
     * Description: Deletes a rectangle located at the specified point (x,y) 
     * by delegating the deletion to the appropriate child node. If no children exist, the method performs no action.
     * 
     * @param  x (double): The x-coordinate of the point for locating the rectangle to delete.
     * @param  y (double): The y-coordinate of the point for locating the rectangle to delete.
     */
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

    /**
     * Description: Searches for a rectangle containing the point (x,y) 
     * within the child nodes and returns it if found. If no children exist, the method returns null.
     * 
     * @param  x (double): The x-coordinate of the point to find within the node.
     * @param  y (double): The y-coordinate of the point to find within the node.
     * 
     * @return Rectangle: Returns the rectangle containing the point (x,y) if found, or null otherwise.
     */
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
    
    /**
     * Description: Checks if all child nodes of the InternalNode are empty. 
     * It does so by iterating through each child and verifying if they are LeafNode instances without rectangles.
     * 
     * @return boolean: Returns true if all child nodes are empty, and false if any child node contains rectangles.
     */
    public boolean isEmpty() {
        for (Node child : children) {
            if (child instanceof LeafNode && !((LeafNode) child).rectangles.isEmpty()) {
                return false; // Found a non-empty leaf node
            }
        }
        return true; // All child nodes are empty
    }
}
