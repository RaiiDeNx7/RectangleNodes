package org.sample.mavensample;

/*
 * Description:
 * 		The Node class is an abstract class that focusing on storing rectangles. It also provides the methods for inserting, printing, deleting, and finding Node details.
 * 
 * Arguments:
 * 		Rectangle Boundary - Rectangle object which represents the boundary of the node's space
 * 
 * Exceptions: None
 * 
 * Return Value:
 * 		find(double x, double y) returns a Rectangle if found, or null if not found)
 *
*/
abstract class Node {
    Rectangle boundary;

    public Node(Rectangle boundary) {
        this.boundary = boundary;
    }

    abstract void insert(Rectangle r);
    abstract void print(int depth);
    abstract void delete(double x, double y);
    abstract Rectangle find(double x, double y);
}
