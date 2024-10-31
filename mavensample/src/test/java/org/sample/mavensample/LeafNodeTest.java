package org.sample.mavensample;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LeafNodeTest extends TestCase {

    private LeafNode leafNode;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    protected void setUp() {
        // Initialize a LeafNode with a boundary of (0, 0) with width and height of 100
        leafNode = new LeafNode(0, 0, 100, 100);
        System.setOut(new PrintStream(outContent));
    }

    protected void tearDown() {
        System.setOut(originalOut);
    }

    public void testInsertRectangleWithinBoundary() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        assertNotNull("Rectangle should be inserted into LeafNode", leafNode.find(15, 15));
    }

    public void testInsertRectangleOutsideBoundary() {
        Rectangle rect = new Rectangle(150, 150, 20, 20);
        leafNode.insert(rect);
        assertNull("Should not find rectangle outside the LeafNode boundary", leafNode.find(155, 155));
    }

    public void testInsertDuplicateRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        leafNode.insert(rect); // Try inserting the same rectangle again
        assertEquals("Should contain only one instance of the rectangle", 1, leafNode.rectangles.size());
    }

    public void testFindExistingRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        Rectangle found = leafNode.find(15, 15);
        assertNotNull("Should find the rectangle in LeafNode", found);
        assertEquals("Found rectangle should match the inserted rectangle", rect, found);
    }

    public void testFindNonExistingRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        Rectangle found = leafNode.find(50, 50); // Outside the inserted rectangle
        assertNull("Should not find any rectangle at (50, 50)", found);
    }

    public void testDeleteRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        leafNode.delete(15, 15); // Delete by center point
        assertNull("Should not find rectangle after deletion", leafNode.find(15, 15));
    }

    public void testDeleteNonExistingRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        leafNode.delete(50, 50); // Attempt to delete a non-existing rectangle
        assertNotNull("Should still find the existing rectangle after non-existing delete", leafNode.find(15, 15));
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

    public void testPrintEmptyLeafNode() {
        leafNode.print(0);
        String output = outContent.toString();
        assertTrue("Print output should indicate empty LeafNode",
            output.contains("Leaf Node - Rectangle at (0.00, 0.00): 100.00x100.00"));
        assertFalse("Print output should not contain any rectangles", output.contains("Rectangle"));
    }

    public void testInsertMultipleRectangles() {
        // Insert multiple rectangles
        for (int i = 0; i < 5; i++) {
            leafNode.insert(new Rectangle(i * 10, i * 10, 5, 5));
        }
        assertEquals("Should have 5 rectangles in the LeafNode", 5, leafNode.rectangles.size());
    }

    public void testDeleteAllRectangles() {
        // Insert multiple rectangles and delete them all
        for (int i = 0; i < 5; i++) {
            leafNode.insert(new Rectangle(i * 10, i * 10, 5, 5));
        }
        for (int i = 0; i < 5; i++) {
            leafNode.delete(i * 10 + 2.5, i * 10 + 2.5); // Delete by center point
        }
        assertTrue("LeafNode should be empty after deleting all rectangles", leafNode.rectangles.isEmpty());
    }

    public void testInsertAndDeleteRectangleOnBoundary() {
        Rectangle rect = new Rectangle(0, 0, 50, 50);
        leafNode.insert(rect);
        assertNotNull("Should find rectangle inserted at the boundary", leafNode.find(25, 25));
        leafNode.delete(25, 25); // Delete the rectangle
        assertNull("Should not find rectangle after deletion", leafNode.find(25, 25));
    }

    public void testBoundaryConditions() {
        // Test inserting rectangles that lie on the boundary of the node
        Rectangle rect1 = new Rectangle(0, 0, 10, 10); // On the boundary
        Rectangle rect2 = new Rectangle(100, 100, 10, 10); // Outside the boundary

        leafNode.insert(rect1);
        assertNotNull("Should find rectangle at (5, 5)", leafNode.find(5, 5));
        
        leafNode.insert(rect2);
        assertNull("Should not find rectangle at (105, 105)", leafNode.find(105, 105));
    }
}
