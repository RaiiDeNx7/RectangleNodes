package org.sample.mavensample;

public class ContainsRectangle {
    private double x1, y1; // Bottom-left corner
    private double x2, y2; // Top-right corner

    public ContainsRectangle(double x1, double y1, double x2, double y2) {
        if (x1 >= x2 || y1 >= y2) {
            throw new IllegalArgumentException("Invalid rectangle coordinates.");
        }
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    // Getters for the coordinates
    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    // Method to determine if this rectangle contains another rectangle
    public boolean contains(ContainsRectangle other) {
        // Check if the bottom-left corner of the other rectangle is within this rectangle
        // and if the top-right corner of the other rectangle is within this rectangle
        return this.x1 <= other.getX1() && this.y1 <= other.getY1()
            && this.x2 >= other.getX2() && this.y2 >= other.getY2();
    }

    // For better readability, let's add a method to print the rectangle's details
    @Override
    public String toString() {
        return "Rectangle[(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + ")]";
    }
}
