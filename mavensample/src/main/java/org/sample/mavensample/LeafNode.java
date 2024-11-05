package org.sample.mavensample;

import java.util.ArrayList;

/*
 * Description: The LeafNode class extends the abstract Node class and represents a leaf node in a quadtree structure. 
 * It is responsible for storing rectangles and managing operations such as inserting, deleting, finding, and printing these rectangles. 
 * This class maintains a list of rectangles that fall within the node's defined boundary.
 */
class LeafNode extends Node {
    ArrayList<Rectangle> rectangles = new ArrayList<>();

    /**
     * Description: Initializes a new LeafNode with a specified boundary defined by its position (x, y) and its dimensions (width, height). 
     * 
     * @param x (double): The x-coordinate of the bottom-left corner of the node's boundary.
     * @param y (double): The y-coordinate of the bottom-left corner of the node's boundary.
     * @param width (double): The width of the node's boundary.
     * @param height (double): The height of the node's boundary.
     */
    public LeafNode(double x, double y, double width, double height) {
        super(new Rectangle((float) x, (float) y, (float) width, (float) height));
    }

    /**
     * Description: Inserts a rectangle into the leaf node if its bottom-left corner is contained within the node's boundary. 
     * 
     * @param r (Rectangle): The rectangle to be inserted into the leaf node.
     */
    @Override
    public void insert(Rectangle r) {
        if (boundary.contains(r.point.x, r.point.y)) {
            rectangles.add(r);
        }
    }

    /**
     * Description: Deletes a rectangle from the leaf node based on the coordinates of its bottom-left corner.
     * 
     * @param x (float): The x-coordinate of the rectangle's bottom-left corner to be deleted.
     * @param y (float): The y-coordinate of the rectangle's bottom-left corner to be deleted.
     */
    @Override
    public void delete(float x, float y) {
        rectangles.removeIf(r -> r.point.x == x && r.point.y == y);
    }

    /**
     * Description: Finds and returns the rectangle that matches the specified coordinates of its bottom-left corner.
     * 
     * @param x (float): The x-coordinate of the rectangle's bottom-left corner to find.
     * @param y (float): The y-coordinate of the rectangle's bottom-left corner to find.
     * 
     * @return Returns the Rectangle object if found; otherwise, returns null.
     */
    @Override
    public Rectangle find(float x, float y) {
        return rectangles.stream()
                .filter(r -> r.point.x == x && r.point.y == y)
                .findFirst()
                .orElse(null);
    }

    /**
     * Description: Prints the details of the leaf node and its stored rectangles, formatted by the depth in the quadtree.
     * 
     * @param depth (integer): The depth level of the node in the quadtree, used for indentation in the output.
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
     * Description: Returns the total number of rectangles currently stored in the leaf node.
     * 
     * @return Returns an integer representing the total count of rectangles.
     */
    public int getTotalRectangles() {
        return rectangles.size();
    }
}
