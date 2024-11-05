package org.sample.mavensample;

/**
 * The InternalNode class represents a node in a quadtree that can contain child nodes.
 * It extends the Node class and provides methods for inserting, deleting, finding, 
 * and printing rectangles, as well as updating rectangle dimensions and managing 
 * child node relationships.
 */
class InternalNode extends Node {
    Node topLeft, topRight, bottomLeft, bottomRight;

    /**
     * Description:  Initializes a new InternalNode with a specified boundary defined by its position (x, y) and its dimensions 
     * (width, height). This constructor also calls the subdivide() method to create the four child nodes.
     * 
     * @param x (double): The x-coordinate of the bottom-left corner of the node's boundary.
     * @param y (double): The y-coordinate of the bottom-left corner of the node's boundary.
     * @param width (double): The width of the node's boundary.
     * @param height (double): The height of the node's boundary.
     */
    public InternalNode(double x, double y, double width, double height) {
        super(new Rectangle((float) x, (float) y, (float) width, (float) height));
        subdivide();
    }
    
    /**
     * Description: Updates the dimensions of a rectangle located at the specified coordinates within the node. 
     * If the rectangle is found, it modifies its length and width; otherwise, it throws an exception.
     * 
     * @param x (float): The x-coordinate of the rectangle's bottom-left corner to be updated.
     * @param y (float): The y-coordinate of the rectangle's bottom-left corner to be updated.
     * @param newLength (float): The new length for the rectangle.
     * @param newWidth (float): The new width for the rectangle.
     */
    public void update(float x, float y, float newLength, float newWidth) throws Exception {
        // Find the rectangle in the appropriate child node
        Rectangle rectangleToUpdate = find(x, y); // Assuming `find` method locates the rectangle

        // Update the rectangle's dimensions
        if (rectangleToUpdate != null) {
            rectangleToUpdate.length = newLength;
            rectangleToUpdate.width = newWidth;
        } else {
            throw new Exception("Rectangle not found at specified location");
        }
    }

    /**
     * Description: Divides the current internal node's boundary into four smaller leaf nodes (topLeft, topRight, bottomLeft, bottomRight) by calculating the midpoints of the boundary.
     */
    private void subdivide() {
        float x = boundary.point.x, y = boundary.point.y, w = boundary.length / 2, h = boundary.width / 2;
        topLeft = new LeafNode(x, y + h, w, h);
        topRight = new LeafNode(x + w, y + h, w, h);
        bottomLeft = new LeafNode(x, y, w, h);
        bottomRight = new LeafNode(x + w, y, w, h);
    }

    /**
     * Description: Returns the total number of rectangles stored in this internal node and its child nodes.
     * 
     * @return An integer representing the total count of rectangles in the internal node and its children.
     */
    public int getTotalRectangles() {
        int total = 0;
        total += ((LeafNode) topLeft).getTotalRectangles();
        total += ((LeafNode) topRight).getTotalRectangles();
        total += ((LeafNode) bottomLeft).getTotalRectangles();
        total += ((LeafNode) bottomRight).getTotalRectangles();
        return total;
    }

    /**
     * Description: Returns an array of the child nodes (topLeft, topRight, bottomLeft, bottomRight) of this internal node.
     * 
     * @return An array of Node objects representing the children of this internal node.
     */
    // Add getChildren method
    public Node[] getChildren() {
        return new Node[]{topLeft, topRight, bottomLeft, bottomRight};
    }

    /**
     * Description: Inserts a rectangle into one of the child nodes if it lies within the internal node's boundary. 
     * If the rectangle's position is outside the boundary, it throws an exception.
     * 
     * @param r (Rectangle): The rectangle to be inserted.
     * 
     * @exception Throws an Exception if the rectangle is outside the internal node's boundary or if it cannot be inserted into any of the child nodes.
     */
    @Override
    public void insert(Rectangle r) throws Exception {
        // Check if the rectangle is within the boundary of this internal node
        if (!boundary.contains(r.point.x, r.point.y)) {
            throw new Exception("Rectangle is out of the internal node's boundary.");
        }

        // Attempt to insert the rectangle into one of the child nodes
        if (topLeft.boundary.contains(r.point.x, r.point.y)) {
            topLeft.insert(r);
        } else if (topRight.boundary.contains(r.point.x, r.point.y)) {
            topRight.insert(r);
        } else if (bottomLeft.boundary.contains(r.point.x, r.point.y)) {
            bottomLeft.insert(r);
        } else if (bottomRight.boundary.contains(r.point.x, r.point.y)) {
            bottomRight.insert(r);
        } else {
            throw new Exception("You can not double insert at this position.");
        }
    }

    /**
     * Description: Deletes a rectangle located at the specified coordinates from the appropriate child node.
     * 
     * @param x (float): The x-coordinate of the rectangle's bottom-left corner to be deleted.
     * @param y (float): The y-coordinate of the rectangle's bottom-left corner to be deleted.
     * 
     * @exception Throws an Exception if no rectangle exists at the specified coordinates.
     */
    @Override
    public void delete(float x, float y) throws Exception {
        if (topLeft.boundary.contains(x, y)) topLeft.delete(x, y);
        else if (topRight.boundary.contains(x, y)) topRight.delete(x, y);
        else if (bottomLeft.boundary.contains(x, y)) bottomLeft.delete(x, y);
        else if (bottomRight.boundary.contains(x, y)) bottomRight.delete(x, y);
        else throw new Exception("Nothing to delete at " + x + ", " + y);
    }

    /**
     * Description: Deletes a rectangle located at the specified coordinates from the appropriate child node.
     * 
     * @param x (float): The x-coordinate of the rectangle's bottom-left corner to find.
     * @param y (float): The y-coordinate of the rectangle's bottom-left corner to find.
     * 
     * @exception Throws an Exception if no rectangle exists at the specified coordinates.
     */
    @Override
    public Rectangle find(float x, float y) throws Exception {
        if (topLeft.boundary.contains(x, y)) return topLeft.find(x, y);
        else if (topRight.boundary.contains(x, y)) return topRight.find(x, y);
        else if (bottomLeft.boundary.contains(x, y)) return bottomLeft.find(x, y);
        else if (bottomRight.boundary.contains(x, y)) return bottomRight.find(x, y);
        else throw new Exception("Nothing is at " + x + ", " + y);
    }

    /**
     * Description: Prints the details of the internal node and its child nodes, formatted by the depth in the quadtree.
     * 
     * @param depth (integer): The depth level of the node in the quadtree, used for indentation in the output.
     */
    @Override
    public void print(int depth) {
        String indent = "    ".repeat(depth);
        System.out.println(indent + "Internal Node - " + boundary);
        topLeft.print(depth + 1);
        topRight.print(depth + 1);
        bottomLeft.print(depth + 1);
        bottomRight.print(depth + 1);
    }
}
