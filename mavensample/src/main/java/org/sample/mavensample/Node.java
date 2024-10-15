package org.sample.mavensample;

abstract class Node {

    // Abstract method for inserting data into the node
    public abstract void insert(Rectangle data);

    // Abstract method for deleting data from the node
    public abstract void delete(Rectangle data);

    // Abstract method for updating data in the node
    public abstract void update(Rectangle oldData, Rectangle newData);

    // Abstract method for dumping (displaying) the contents of the node
    public abstract void dump(String indent);

    // Abstract method for finding data in the node
    public abstract Rectangle find(double x, double y);
}

