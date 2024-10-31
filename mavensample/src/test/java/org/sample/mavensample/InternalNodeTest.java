package org.sample.mavensample;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InternalNodeTest extends TestCase {

    private InternalNode internalNode;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    protected void setUp() {
        // Initialize an InternalNode with a boundary of (0, 0) with width and height of 100
        internalNode = new InternalNode(0, 0, 100, 100);
        System.setOut(new PrintStream(outContent));
    }

    protected void tearDown() {
        System.setOut(originalOut);
    }

    public void testSubdivide() {
        // Subdivide the internal node
        internalNode.subdivide();
        assertNotNull("SW child should not be null", internalNode.children[0]);
        assertNotNull("NW child should not be null", internalNode.children[1]);
        assertNotNull("SE child should not be null", internalNode.children[2]);
        assertNotNull("NE child should not be null", internalNode.children[3]);
    }

    public void testInsertRectangleWithinBoundary() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        assertNotNull("Should find rectangle after insertion", internalNode.find(15, 15));
    }

    public void testInsertRectangleOutsideBoundary() {
        Rectangle rect = new Rectangle(150, 150, 20, 20);
        internalNode.insert(rect);
        assertNull("Should not find rectangle outside the InternalNode boundary", internalNode.find(155, 155));
    }

    public void testInsertIntoCorrectChild() {
        Rectangle rect = new Rectangle(25, 25, 10, 10);
        internalNode.insert(rect);
        // Ensure the rectangle is found in the appropriate child
        assertNotNull("Should find rectangle in the appropriate child", internalNode.children[1].find(30, 30));
    }

    public void testInsertMultipleRectangles() {
        for (int i = 0; i < 10; i++) {
            Rectangle rect = new Rectangle(i * 10, i * 10, 5, 5);
            internalNode.insert(rect);
        }
        // Verify that rectangles are inserted into appropriate children
        assertNotNull("Should find rectangle in the appropriate child", internalNode.children[0].find(5, 5));
        assertNotNull("Should find rectangle in the appropriate child", internalNode.children[1].find(15, 15));
        assertNotNull("Should find rectangle in the appropriate child", internalNode.children[2].find(25, 25));
        assertNotNull("Should find rectangle in the appropriate child", internalNode.children[3].find(35, 35));
    }

    public void testDeleteRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        internalNode.delete(15, 15); // Delete the rectangle by center point
        assertNull("Should not find rectangle after deletion", internalNode.find(15, 15));
    }

    public void testDeleteNonExistingRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        internalNode.delete(50, 50); // Attempt to delete a non-existing rectangle
        assertNotNull("Should still find the existing rectangle after non-existing delete", internalNode.find(15, 15));
    }

    public void testPrintInternalNode() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        internalNode.subdivide(); // Ensure the node is subdivided before printing
        internalNode.print(0);
        String output = outContent.toString();
        assertTrue("Print output should contain the InternalNode boundary",
            output.contains("Internal Node - Rectangle at (0.00, 0.00): 100.00x100.00"));
        assertTrue("Print output should show child nodes", output.contains("Leaf Node -"));
    }

    public void testIsEmpty() {
        assertTrue("Newly created InternalNode should be empty", internalNode.isEmpty());
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        internalNode.subdivide(); // Ensure the node is subdivided
        assertFalse("InternalNode should not be empty after inserting a rectangle", internalNode.isEmpty());
        internalNode.delete(15, 15); // Delete the rectangle
        assertTrue("InternalNode should be empty after deleting the rectangle", internalNode.isEmpty());
    }

    public void testFindRectangleInCorrectChild() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        assertNotNull("Should find rectangle in the appropriate child", internalNode.find(15, 15));
    }

    public void testFindRectangleInUnsubdividedNode() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        assertNotNull("Should find rectangle before subdivision", internalNode.find(15, 15));
    }

    public void testBoundaryConditions() {
        Rectangle rect1 = new Rectangle(0, 0, 10, 10); // On the boundary
        Rectangle rect2 = new Rectangle(100, 100, 10, 10); // Outside the boundary

        internalNode.insert(rect1);
        assertNotNull("Should find rectangle at (5, 5)", internalNode.find(5, 5));
        
        internalNode.insert(rect2);
        assertNull("Should not find rectangle at (105, 105)", internalNode.find(105, 105));
    }
}
