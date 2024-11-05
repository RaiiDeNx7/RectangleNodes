package org.sample.mavensample;

/*
 * The QuadTree class represents a spatial data structure used to partition a two-dimensional space 
 * by recursively subdividing it into four quadrants or regions. 
 * It allows for efficient insertion, searching, deletion, and updating of rectangles within a defined boundary.
 */
public class QuadTree {
    private Node root;
    private final float x;  // Default x-coordinate
    private final float y;  // Default y-coordinate
    private final float width;  // Default width of the root boundary
    private final float height; // Default height of the root boundary

    /**
     * Description:  Initializes a new instance of the QuadTree class with default boundary values and an initial root node as a LeafNode.
     * 
     */
    public QuadTree() {
        this.x = -50;  // Set default x-coordinate
        this.y = -50;  // Set default y-coordinate
        this.width = 100;  // Set default width of the root boundary
        this.height = 100; // Set default height of the root boundary
        this.root = new LeafNode(x, y, width, height); // Initialize root with default dimensions
    }

    /**
     * Description: Inserts a rectangle into the quadtree. If the root node is a LeafNode and it contains five or more rectangles, it will subdivide into an InternalNode.
     * @param r (Rectangle): The rectangle to be inserted into the quadtree.
     * 
     * @exception: Throws Exception if there is an error during the insertion process, such as if the rectangle is out of bounds or cannot be inserted for any reason.
     */
    public void insert(Rectangle r) throws Exception {
        if (root instanceof LeafNode && ((LeafNode) root).rectangles.size() >= 5) {
            InternalNode newRoot = new InternalNode(x, y, width, height);
            for (Rectangle rect : ((LeafNode) root).rectangles) {
                newRoot.insert(rect);
            }
            newRoot.insert(r);
            root = newRoot; // Assign the new root
        } else {
            root.insert(r); // Insert into the current root
        }
    }

    /**
     * Description: Searches for a rectangle at the specified coordinates (x, y).
     * @param x (float): The x-coordinate of the point to search for.
     * @param y (float): The y-coordinate of the point to search for.
     * 
     * @exception Throws Exception if no rectangle is found at the specified location.
     * 
     * @return (Rectangle): Returns the rectangle found at the specified coordinates, or throws an exception if not found.
     */
    public Rectangle find(float x, float y) throws Exception {
        return root.find(x, y);
    }

    /**
     * Description: Deletes the rectangle located at the specified coordinates (x, y) from the quadtree.
     * @param x (float): The x-coordinate of the rectangle to be deleted.
     * @param y (float): The y-coordinate of the rectangle to be deleted.
     * 
     * @exception Throws Exception if no rectangle is found at the specified location.
     * 
     */
    public void delete(float x, float y) throws Exception {
        root.delete(x, y);
    }

    /**
     * Description: Updates the dimensions of a rectangle located at the specified coordinates (x, y). 
     * If found, the rectangle is deleted and a new rectangle with updated dimensions is inserted.
     * 
     * @param x (float): The x-coordinate of the rectangle to be updated.
     * @param y (float): The y-coordinate of the rectangle to be updated.
     * 
     * @exception Throws Exception if no rectangle is found at the specified location for the update operation.
     * 
     */
    public void update(float x, float y, float newLength, float newWidth) throws Exception {
        Rectangle found = root.find(x, y);
        if (found != null) {
            delete(x, y);
            insert(new Rectangle(x, y, newLength, newWidth));
        } else {
            throw new Exception("No rectangle found at " + x + ", " + y);
        }
    }

    /**
     * Description: Prints the structure of the quadtree, showing all rectangles and their relationships.
     * 
     */
    public void dump() {
        root.print(0);
    }
    
    /**
     * Description:  Returns the root node of the quadtree.
     * 
     * @return Node: The root node of the quadtree.
     */
    // Add a method to access the root node
    public Node getRoot() {
        return root;
    }

}
