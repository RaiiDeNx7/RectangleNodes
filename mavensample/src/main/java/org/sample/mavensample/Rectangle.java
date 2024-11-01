package org.sample.mavensample;

/*
 * Description: The Rectangle class represents a region in 2D space (defined by bottom-left corner). It also checks if a given point is inside the rectangle and return details about the rectangle.
*/
class Rectangle {
	
    double x, y, width, height;
    
    /**
     * Default Rectangle Constructor
     * 
     * @param x (double): The x-coordinate of the bottom-left corner
     * @param y (double): The y-coordinate of the bottom-left corner
     * @param width (double): The width of the rectangle
     * @param height (double): The height of the rectangle
     */
    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Description: Checks if a given point (px,py) lies within the rectangle’s boundaries. 
     * A point is considered within the rectangle if it’s greater than or equal to the rectangle’s left and bottom edges and 
     * less than or equal to its right and top edges.
     * 
     * @param px (double): The x-coordinate of the point to check.
     * @param py (double): The y-coordinate of the point to check.
     * 
     * @return (boolean): Returns true if the point is inside or on the boundary of the rectangle, and false otherwise.
     */
    public boolean contains(double px, double py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    /**
     * Description: Returns a string representation of the rectangle, detailing its position and size with two decimal places for each value.
     * 
     * @return (boolean): Returns true if the point is inside or on the boundary of the rectangle, and false otherwise.
     */
    @Override
    public String toString() {
        return String.format("Rectangle at (%.2f, %.2f): %.2fx%.2f", x, y, width, height);
    }
}