package org.sample.mavensample;
import java.util.List;
import java.util.ArrayList;

//LeafNode class representing a leaf node in the Quadtree
class LeafNode extends Node {
    List<Rectangle> rectangles;

    /**
     * Default LeafNode Constructor
     * Constructor that initializes a LeafNode with a specific boundary and an empty list of rectangles. 
     * The boundary is defined as a rectangle positioned at (x,y) with the given width and height.
     * 
     * @param x (double): The x-coordinate of the bottom-left corner
     * @param y (double): The y-coordinate of the bottom-left corner
     * @param width (double): The width of the rectangle
     * @param height (double): The height of the rectangle
     */
    public LeafNode(double x, double y, double width, double height) {
        super(new Rectangle(x, y, width, height));
        rectangles = new ArrayList<>();
    }

    /**
     * Description: Inserts a rectangle r into the LeafNode if r lies within the node’s boundary. 
     * If the rectangle is already present, it will not be added again.
     * 
     * @param r (Rectangle): The rectangle to be inserted within this node’s boundary.
    */
    @Override
    public void insert(Rectangle r) {
        if (!boundary.contains(r.x, r.y)) return;

        // Insert rectangle if not already present
        if (!rectangles.contains(r)) {
            rectangles.add(r);
        }
    }

    /**
     * Description: Prints the structure of the LeafNode, showing the boundary of the node and any rectangles it contains. 
     * The depth parameter determines the level of indentation for formatting the printed output.
     * 
     * @param depth (integer): The depth level of the node, which is used to indent the output accordingly.
    */
    @Override
    public void print(int depth) {
        String indent = "    ".repeat(depth);
        System.out.println(indent + "Leaf Node - " + boundary);
        for (Rectangle r : rectangles) {
            System.out.println(indent + "    " + r);
        }
    }

    /**
     * Description: Deletes any rectangle within the LeafNode that contains the specified point (x,y). 
     * This uses a filter to remove all rectangles where (x,y) is inside the rectangle's boundary.
     * 
     * @param x (double): The x-coordinate of the point for locating the rectangle to delete.
     * @param y (double): The y-coordinate of the point for locating the rectangle to delete.
    */
    @Override
    public void delete(double x, double y) {
        rectangles.removeIf(r -> r.contains(x, y));
    }

    /**
     * Description: Searches for a rectangle within the LeafNode that contains the specified point (x,y) and 
     * returns it if found.
     * 
     * @param x (double): The x-coordinate of the point to locate within the node.
     * @param y (double): The y-coordinate of the point to locate within the node.
     * 
     * @return Rectangle: Returns the rectangle containing the point (x,y) if found, otherwise returns null.
    */
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