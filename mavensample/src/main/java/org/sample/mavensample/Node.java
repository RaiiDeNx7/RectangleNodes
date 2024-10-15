package org.sample.mavensample;

public abstract class Node {

    // Abstract method for inserting data into the node
    public abstract void insert(Object data);

    // Abstract method for deleting data from the node
    public abstract void delete(Object data);

    // Abstract method for updating data in the node
    public abstract void update(Object oldData, Object newData);

    // Abstract method for dumping (displaying) the contents of the node
    public abstract void dump();

    // Abstract method for finding data in the node
    public abstract Object find(Object data);
}

