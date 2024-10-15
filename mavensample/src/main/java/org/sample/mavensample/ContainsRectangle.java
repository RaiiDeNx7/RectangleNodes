package org.sample.mavensample;

class Rectangle {
    double x, y, width, height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Rectangle other) {
        return other.x >= this.x && other.x + other.width <= this.x + this.width &&
               other.y >= this.y && other.y + other.height <= this.y + this.height;
    }

    public boolean intersects(Rectangle other) {
        return !(other.x > this.x + this.width || other.x + other.width < this.x ||
                other.y > this.y + this.height || other.y + other.height < this.y);
    }

    @Override
    public String toString() {
        return String.format("Rectangle at (%.2f, %.2f): %.2fx%.2f", x, y, width, height);
    }
}