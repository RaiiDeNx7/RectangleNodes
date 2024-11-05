package org.sample.mavensample;

/**
 * The Node class serves as an abstract base class for handling nodes within a quadtree data structure. 
 * It provides functionality for storing rectangles within a defined boundary, managing parent-child relationships, 
 * and implementing essential methods for inserting, deleting, finding, and printing rectangles. Derived classes must provide specific implementations for these methods.
 */
abstract class Node {
    Rectangle boundary;

    /**
     * Description: Initializes a new instance of the Node class with a specified boundary defined by a rectangle. 
     * @param boundary (Rectangle): The rectangle that defines the spatial boundary of this node. This boundary determines the area within which rectangles can be stored or manipulated.
     */
    Node(Rectangle boundary) {
        this.boundary = boundary;
    }

    /**
     * Description:  Inserts a rectangle into the node. 
     * 
     * @param r (Rectangle): The rectangle to be inserted.
     * 
     * @exception Throws an Exception if the insertion fails (e.g., if the rectangle is out of the node's boundary).
     */
    abstract void insert(Rectangle r) throws Exception;
    
    /**
     * Description:  Inserts a rectangle into the node. 
     * 
     * @param x (float): The x-coordinate of the rectangle to be deleted.
     * @param y (float): The y-coordinate of the rectangle to be deleted.
     * 
     * @exception Throws an Exception if no rectangle is found at the specified coordinates.
     */
    abstract void delete(float x, float y) throws Exception;
    
    /**
     * Description: Finds and returns the rectangle located at the specified coordinates.. 
     * 
     * @param x (float): The x-coordinate where the rectangle is searched.
     * @param y (float): The y-coordinate where the rectangle is searched.
     * 
     * @exception Throws an Exception if no rectangle is found at the specified coordinates.
     * 
     * @return Returns the Rectangle located at the specified coordinates, or throws an exception if not found.
     */
    abstract Rectangle find(float x, float y) throws Exception;
    
    /**
     * Description: Prints the details of the node and its child nodes, formatted according to the specified depth.
     * 
     * @param depth (integer): The current depth in the quadtree structure, used for formatting output.
     * 
     * @exception This method does not throw exceptions.
     * 
     */
    abstract void print(int depth);
}