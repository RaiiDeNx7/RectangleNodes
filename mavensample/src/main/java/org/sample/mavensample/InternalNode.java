package org.sample.mavensample;
import java.util.ArrayList;
import java.util.List;

public class InternalNode extends Node {
    private List<Node> children;

    public InternalNode() {
        this.children = new ArrayList<>();
    }

    @Override
    public void insert(Object data) {
        // Base case: Insert into an appropriate child
        if (children.isEmpty()) {
            System.out.println("Cannot insert into an empty InternalNode, adding a new LeafNode.");
            children.add(new LeafNode(data));  // Adding the first LeafNode
        } else {
            // Recursively pass the insert down to the first child for simplicity
            children.get(0).insert(data);
        }
    }

    @Override
    public void delete(Object data) {
        // Recursively attempt to delete the data from all children
        for (Node child : children) {
            child.delete(data);  // Delegate delete to child nodes
        }
    }

    @Override
    public void update(Object oldData, Object newData) {
        // Recursively update the data in the children nodes
        for (Node child : children) {
            child.update(oldData, newData);
        }
    }

    @Override
    public void dump() {
        System.out.println("Dumping InternalNode contents:");
        for (Node child : children) {
            child.dump();  // Recursively dump the children
        }
    }

    @Override
    public Object find(Object data) {
        // Recursively search in the child nodes
        for (Node child : children) {
            Object found = child.find(data);
            if (found != null) {
                return found;  // Return if found
            }
        }
        return null;  // Data not found in any child
    }
}
