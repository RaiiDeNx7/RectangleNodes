package org.sample.mavensample;

public class LeafNode extends Node {
    private Object data;

    public LeafNode(Object data) {
        this.data = data;
    }

    @Override
    public void insert(Object data) {
        // LeafNodes should not have children, so we handle insertion directly here
        System.out.println("Inserting into LeafNode");
        this.data = data;  // Simple insert logic for leaf
    }

    @Override
    public void delete(Object data) {
        // Check if the data matches this LeafNode's data, if so, delete it (nullify)
        if (this.data.equals(data)) {
            System.out.println("Deleting data from LeafNode");
            this.data = null;  // Remove data by setting it to null
        }
    }

    @Override
    public void update(Object oldData, Object newData) {
        // If old data matches, update with new data
        if (this.data.equals(oldData)) {
            System.out.println("Updating data in LeafNode");
            this.data = newData;
        }
    }

    @Override
    public void dump() {
        System.out.println("LeafNode data: " + data);
    }

    @Override
    public Object find(Object data) {
        // Check if the current node contains the data
        if (this.data != null && this.data.equals(data)) {
            System.out.println("Data found in LeafNode");
            return data;
        }
        return null;  // Data not found
    }
}
