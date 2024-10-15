package org.sample.mavensample;
import java.util.ArrayList;
import java.util.List;



//InternalNode class which extends Node
class InternalNode extends Node {
 private List<Node> children;

 public InternalNode() {
     this.children = new ArrayList<>();
 }

 @Override
 public void insert(Rectangle data) {
     if (children.isEmpty()) {
         children.add(new LeafNode()); // Adding the first LeafNode
     }
     children.get(0).insert(data);  // Inserting into the first child (for simplicity)
 }

 @Override
 public void delete(Rectangle data) {
     for (Node child : children) {
         child.delete(data);  // Delegate delete to children
     }
 }

 @Override
 public void update(Rectangle oldData, Rectangle newData) {
     for (Node child : children) {
         child.update(oldData, newData);  // Recursively update in children
     }
 }

 @Override
 public void dump(String indent) {
     System.out.println(indent + "InternalNode with " + children.size() + " children:");
     for (Node child : children) {
         child.dump(indent + "    ");  // Recursively dump child nodes
     }
 }

 @Override
 public Rectangle find(double x, double y) {
     for (Node child : children) {
         Rectangle found = child.find(x, y);
         if (found != null) {
             return found;  // Return found rectangle if it exists in any child
         }
     }
     return null;
 }
}