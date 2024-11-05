package org.sample.mavensample;

/*
 * Description: The Rectangle class represents a region in 2D space (defined by bottom-left corner). It also checks if a given point is inside the rectangle and return details about the rectangle.
*/
class Rectangle {
    Point point;
    float length, width;
   
    /**
     * Description:  Initializes a new instance of the Rectangle class with specified coordinates and dimensions.
     * @param x (float): The x-coordinate of the bottom-left corner of the rectangle.
     * @param y (float): The y-coordinate of the bottom-left corner of the rectangle.
     * @param length (float): The horizontal dimension (length) of the rectangle.
     * @param width (float): The vertical dimension (width) of the rectangle.
     */
    Rectangle(float x, float y, float length, float width) {
        this.point = new Point(x, y);
        this.length = length;
        this.width = width;
    }

    /**
     * Description:  Checks if the specified point (x, y) lies within the boundaries of the rectangle.
     * @param x (float): The x-coordinate of the point to check.
     * @param y (float): The y-coordinate of the point to check.
     * 
     * @return (boolean): Returns true if the point (x, y) is inside the rectangle; otherwise, returns false.
     */
    public boolean contains(float x, float y) {
        return x >= point.x && x <= point.x + length && y >= point.y && y <= point.y + width;
    }

    /**
     * Description:  Returns a string representation of the rectangle, including its location and dimensions.
     * 
     * @String: A formatted string that describes the rectangle's bottom-left corner and its dimensions, e.g., "Rectangle at (x, y): length x width"
     */
    @Override
    public String toString() {
        return String.format("Rectangle at (%.2f, %.2f): %.2fx%.2f", point.x, point.y, length, width);
    }
}