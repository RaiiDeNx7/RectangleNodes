package org.sample.mavensample;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class NodeTest extends TestCase {

    private LeafNode leafNode;
    private InternalNode internalNode;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    protected void setUp() {
        // Initialize a LeafNode with a boundary of (0, 0) with width and height of 100
        leafNode = new LeafNode(0, 0, 100, 100);
        // Initialize an InternalNode with a boundary of (0, 0) with width and height of 100
        internalNode = new InternalNode(0, 0, 100, 100);
        System.setOut(new PrintStream(outContent));
    }

    protected void tearDown() {
        System.setOut(originalOut);
    }

    public void testLeafNodeInsertion() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        assertNotNull("Rectangle should be inserted into LeafNode", leafNode.find(15, 15));
    }

    public void testLeafNodeInsertionExceedsCapacity() {
        // Fill the LeafNode beyond its capacity
        for (int i = 0; i < 5; i++) {
            leafNode.insert(new Rectangle(i * 10, i * 10, 5, 5));
        }
        // Verify that the fifth rectangle is not inserted
        assertNull("Should not find the fifth rectangle after exceeding capacity", 
            leafNode.find(20, 20));
    }

    public void testInternalNodeInsertionAndSubdivide() {
        // Insert enough rectangles to force subdivision
        for (int i = 0; i < 5; i++) {
            internalNode.insert(new Rectangle(i * 10, i * 10, 10, 10));
        }
        // Ensure rectangles are distributed among child nodes
        assertNotNull("Should find rectangle in child node SW", 
            internalNode.children[0].find(5, 5));
        assertNotNull("Should find rectangle in child node NW", 
            internalNode.children[1].find(5, 15));
        assertNotNull("Should find rectangle in child node SE", 
            internalNode.children[2].find(15, 5));
        assertNotNull("Should find rectangle in child node NE", 
            internalNode.children[3].find(15, 15));
    }

    public void testFindRectangleInLeafNode() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        Rectangle found = leafNode.find(15, 15);
        assertNotNull("Should find rectangle in LeafNode", found);
        assertEquals("Found rectangle should match the inserted rectangle", rect, found);
    }

    public void testFindNonExistingRectangleInLeafNode() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        Rectangle found = leafNode.find(50, 50); // Outside the inserted rectangle
        assertNull("Should not find any rectangle at (50, 50)", found);
    }

    public void testDeleteRectangleInLeafNode() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        leafNode.delete(15, 15); // Delete by center point
        assertNull("Should not find rectangle after deletion", leafNode.find(15, 15));
    }

    public void testDeleteNonExistingRectangleInLeafNode() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        leafNode.delete(50, 50); // Attempt to delete a non-existing rectangle
        assertNotNull("Should still find the existing rectangle after non-existing delete", 
            leafNode.find(15, 15));
    }

    public void testPrintLeafNode() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        leafNode.print(0);
        String output = outContent.toString();
        assertTrue("Print output should contain the LeafNode boundary",
            output.contains("Leaf Node - Rectangle at (0.00, 0.00): 100.00x100.00"));
        assertTrue("Print output should contain the inserted rectangle",
            output.contains("    Rectangle at (10.00, 10.00): 20.00x20.00"));
    }

    public void testPrintInternalNode() {
        // Fill the InternalNode with rectangles to test print functionality
        for (int i = 0; i < 5; i++) {
            internalNode.insert(new Rectangle(i * 10, i * 10, 10, 10));
        }
        internalNode.print(0);
        String output = outContent.toString();
        assertTrue("Print output should contain the InternalNode boundary",
            output.contains("Internal Node - Rectangle at (0.00, 0.00): 100.00x100.00"));
    }

    public void testBoundaryInsertion() {
        // Test inserting rectangles that lie on the boundary of the node
        Rectangle rect1 = new Rectangle(0, 0, 50, 50);
        Rectangle rect2 = new Rectangle(100, 100, 50, 50);
        
        // Should insert rect1 as it lies within the boundary
        internalNode.insert(rect1);
        assertNotNull("Should find rectangle at (25, 25)", internalNode.find(25, 25));
        
        // Should not insert rect2 as it lies outside the boundary
        internalNode.insert(rect2);
        assertNull("Should not find rectangle at (125, 125)", internalNode.find(125, 125));
    }

    public void testEmptyLeafNode() {
        // Verify that a newly created LeafNode is empty
        assertTrue("New LeafNode should be empty", leafNode.rectangles.isEmpty());
    }

    public void testEmptyInternalNode() {
        // Verify that a newly created InternalNode has empty children
        assertTrue("New InternalNode should be empty", internalNode.isEmpty());
    }
    
    public void testInsertRectanglesAtBoundaries() {
        // Test inserting rectangles that are aligned with the boundaries
        Rectangle rect1 = new Rectangle(0, 0, 10, 10);
        Rectangle rect2 = new Rectangle(50, 50, 10, 10);
        Rectangle rect3 = new Rectangle(100, 100, 10, 10); // Should not be inserted
        
        internalNode.insert(rect1);
        internalNode.insert(rect2);
        internalNode.insert(rect3);
        
        assertNotNull("Should find rectangle at (5, 5)", internalNode.find(5, 5));
        assertNotNull("Should find rectangle at (55, 55)", internalNode.find(55, 55));
        assertNull("Should not find rectangle at (105, 105)", internalNode.find(105, 105));
    }
}
