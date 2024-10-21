package org.sample.mavensample;

class Rectangle {
    double x, y, width, height;

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