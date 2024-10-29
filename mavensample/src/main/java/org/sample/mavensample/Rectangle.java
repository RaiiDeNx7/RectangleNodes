package org.sample.mavensample;

/*
 * Description:
 * 		The Rectangle class represents a region in 2D space (defined by bottom-left corner). It also checks if a given point is inside the rectangle and return details about the rectangle.
 * 
 * Arguments:
 * 		double x - the x-coordinate of the bottom-left corner of rectangle
 * 		double y - the y-coordinate of the bottom-left corner of rectangle
 * 		double width - The width of rectangle, extending right from x
 * 		double height - the height of rectangle, extending up from y
 * 
 * Exceptions: None
 * 
 * Return Value:
 * 		contains(double px, double py) returns a boolean indicating whether the specified point is inside the rectangle
 * 		toString() returns a string that represents the rectangle's details.
 *
*/
class Rectangle {
	
    double x, y, width, height;
    
    /**
     * Default Rectangle Constructor
     * 
     * @param x The x-coordinate of the bottom-left corner
     * @param y The y-coordinate of the bottom-left corner
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     */

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(double px, double py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    @Override
    public String toString() {
        return String.format("Rectangle at (%.2f, %.2f): %.2fx%.2f", x, y, width, height);
    }
}