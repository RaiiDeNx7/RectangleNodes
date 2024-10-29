package org.sample.mavensample;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InternalNodeTest extends TestCase {

    private InternalNode internalNode;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    protected void setUp() {
        // Initialize InternalNode with a boundary of (0, 0) with width and height of 100
        internalNode = new InternalNode(0, 0, 100, 100);
        System.setOut(new PrintStream(outContent));
    }

    protected void tearDown() {
        System.setOut(originalOut);
    }

    public void testInsertIntoEmptyNode() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        // Verify that the rectangle is inserted into the appropriate child node
        assertNotNull("Child node should not be null", internalNode.children[0]);
        assertTrue("Inserted rectangle should be in the SW child node", 
            internalNode.children[0].find(15, 15) != null);
    }

    public void testInsertAndSubdivide() {
        // Insert rectangles that will force the internal node to subdivide
        for (int i = 0; i < 5; i++) {
            internalNode.insert(new Rectangle(i * 10, i * 10, 10, 10));
        }
        
        // Verify that all rectangles are inserted into the appropriate child nodes
        assertNotNull("Child node SW should not be null after subdivision", internalNode.children[0]);
        assertNotNull("Child node NW should not be null after subdivision", internalNode.children[1]);
        assertNotNull("Child node SE should not be null after subdivision", internalNode.children[2]);
        assertNotNull("Child node NE should not be null after subdivision", internalNode.children[3]);
    }

    public void testFindExistingRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        Rectangle found = internalNode.find(15, 15);
        assertNotNull("Should find the rectangle within the InternalNode", found);
        assertEquals("Found rectangle should match the inserted rectangle", rect, found);
    }

    public void testFindNonExistingRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        Rectangle found = internalNode.find(50, 50);
        assertNull("Should not find any rectangle at (50, 50)", found);
    }

    public void testDelete() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        internalNode.delete(15, 15); // Deleting the rectangle by its center
        assertNull("Should not find the rectangle after deletion", internalNode.find(15, 15));
    }

    public void testDeleteNonExistingRectangle() {
        Rectangle rect1 = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect1);
        internalNode.delete(50, 50); // Attempt to delete a non-existing rectangle
        assertTrue("Should still find the existing rectangle after non-existing delete", 
            internalNode.find(15, 15) != null);
    }

    public void testPrint() {
        Rectangle rect1 = new Rectangle(10, 10, 20, 20);
        Rectangle rect2 = new Rectangle(30, 30, 10, 10);
        internalNode.insert(rect1);
        internalNode.insert(rect2);
        
        internalNode.print(0);
        String output = outContent.toString();
        assertTrue("Print output should contain the Internal Node boundary",
            output.contains("Internal Node - Rectangle at (0.00, 0.00): 100.00x100.00"));
        assertTrue("Print output should contain the child nodes",
            output.contains("Leaf Node - Rectangle at (0.00, 50.00): 50.00x50.00"));
        assertTrue("Print output should contain the inserted rectangle",
            output.contains("    Rectangle at (10.00, 10.00): 20.00x20.00"));
        assertTrue("Print output should contain the second rectangle",
            output.contains("    Rectangle at (30.00, 30.00): 10.00x10.00"));
    }

    public void testIsEmpty() {
        assertTrue("New InternalNode should be empty", internalNode.isEmpty());
        
        // Insert a rectangle to ensure the node is no longer empty
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        internalNode.insert(rect);
        assertFalse("InternalNode should not be empty after inserting a rectangle", internalNode.isEmpty());
        
        // Delete the rectangle to check if it returns to empty
        internalNode.delete(15, 15); // Delete the rectangle
        assertTrue("InternalNode should be empty after deleting the rectangle", internalNode.isEmpty());
    }
}
