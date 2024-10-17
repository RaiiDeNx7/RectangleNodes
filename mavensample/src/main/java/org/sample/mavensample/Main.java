package org.sample.mavensample;


public class Main {
    Node root;

    public Main(double x, double y, double width, double height) {
        root = new LeafNode(x, y, width, height);
    }

    public void insert(double x, double y, double width, double height) {
        Rectangle r = new Rectangle(x, y, width, height);
        root.insert(r);

        // If the root node contains more than 4 rectangles, it must be subdivided into an InternalNode
        if (root instanceof LeafNode && ((LeafNode) root).rectangles.size() > 4) {
            InternalNode newRoot = new InternalNode(root.boundary.x, root.boundary.y, root.boundary.width, root.boundary.height);
            for (Rectangle rect : ((LeafNode) root).rectangles) {
                newRoot.insert(rect);
            }
            root = newRoot;
        }
    }

    public void delete(double x, double y) {
        root.delete(x, y);
    }

    public void update(double x, double y, double newX, double newY, double newWidth, double newHeight) {
        Rectangle r = root.find(x, y);
        if (r != null) {
            delete(x, y);
            insert(newX, newY, newWidth, newHeight);
        }
    }

    public void find(double x, double y) {
        Rectangle r = root.find(x, y);
        if (r != null) {
            System.out.println(r);
        } else {
            System.out.println("Rectangle not found");
        }
    }

    public void dump() {
        root.print(0);
    }

    public static void main(String[] args) {
        Main quadtree = new Main(-50, -50, 100, 100);
        quadtree.dump();
        quadtree.insert(-40, -40, 10, 10);
        quadtree.dump();
        quadtree.insert(40, -40, 10, 10);
        quadtree.insert(-40, 40, 10, 10);
        quadtree.insert(-30, 40, 10, 10);
        quadtree.insert(-35, 40, 10, 10);
        quadtree.insert(-40, 30, 10, 10);
        quadtree.insert(40, 40, 10, 10);
        quadtree.dump();
        quadtree.update(40, 40, 40, 40, 100, 100);
        quadtree.dump();
        quadtree.find(40, 40);
        quadtree.insert(40, 40, 10, 10);
        quadtree.insert(39, 40, 10, 10);
        quadtree.insert(40, 39, 10, 10);
        quadtree.insert(39, 39, 10, 10);
        quadtree.dump();
        
        quadtree.delete(-40, -40);
        quadtree.delete(40, 10);
        quadtree.delete(-40, 40);
        quadtree.delete(-30, 40);
        quadtree.delete(-35, 40);
        quadtree.delete(-40, 30);
        quadtree.delete(40, 40);
        quadtree.delete(40, 40);
        quadtree.delete(39, 40);
        quadtree.delete(40, 39);
        quadtree.delete(39, 39);
        quadtree.dump();
    }
}
