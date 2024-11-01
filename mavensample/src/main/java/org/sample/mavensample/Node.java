package org.sample.mavensample;

/*
 * Description: The Node class is an abstract class that focusing on storing rectangles. It also provides the methods for 
 * inserting, printing, deleting, and finding Node details.
*/
abstract class Node {
    Rectangle boundary;

    /**
     * Default Node Constructor
     * Description: Constructor that initializes a Node with a specific boundary.
     * 
     * @param boundary (Rectangle): Defines the boundary of the node, typically representing the area of space this node covers.
     */
    public Node(Rectangle boundary) {
        this.boundary = boundary;
    }

    /**
     * Description: Abstract method intended to insert a rectangle r into the node. 
     * The implementation should handle cases such as inserting rectangles into a child node if the node has already been split.
     * 	
     * @param r (Rectangle): The rectangle to be inserted within this node's boundary.
    */
    abstract void insert(Rectangle r);
    
    /**
     * Description: Abstract method intended to print the contents or structure of the node. 
     * depth can be used to determine the indentation level, reflecting the depth of the node in the quadtree structure.
     * 	
     * @param depth (integer): The depth level of the node, typically used for formatting the output.
    */
    abstract void print(int depth);
    
    /**
     * Description: Abstract method intended to delete a rectangle located at the specified point (x,y). 
     * The implementation should find and remove the rectangle from the node, potentially updating the structure of the 
     * quadtree if a node becomes empty.
     * 	
     * @param x (double): The x-coordinate of the rectangle's reference point to delete.
     * @param y (double): The y-coordinate of the rectangle's reference point to delete.
     * 
    */
    abstract void delete(double x, double y);
    
    /**
     * Description: Abstract method intended to locate and return the rectangle containing the point (x,y) 
     * within the node's boundary.
     * 	
     * @param x (double): The x-coordinate of the point to find within the node's boundary.
     * @param y (double): The y-coordinate of the point to find within the node's boundary.
     * 
     * @return (Rectangle): The rectangle that contains the point (x,y), or null if no such rectangle is found.
    */
    abstract Rectangle find(double x, double y);
}
