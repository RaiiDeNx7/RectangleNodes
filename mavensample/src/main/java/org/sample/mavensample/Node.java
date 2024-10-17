package org.sample.mavensample;

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
