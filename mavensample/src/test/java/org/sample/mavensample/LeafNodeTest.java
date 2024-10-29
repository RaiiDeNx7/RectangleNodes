package org.sample.mavensample;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LeafNodeTest extends TestCase {

    private LeafNode leafNode;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    protected void setUp() {
        // Initialize LeafNode with a boundary of (0, 0) with width and height of 100
        leafNode = new LeafNode(0, 0, 100, 100);
        System.setOut(new PrintStream(outContent));
    }

    protected void tearDown() {
        System.setOut(originalOut);
    }

    public void testInsert() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        assertTrue("Rectangles should contain the inserted rectangle", 
            leafNode.rectangles.contains(rect));
    }

    public void testInsertOutsideBoundary() {
        Rectangle rect = new Rectangle(200, 200, 20, 20);
        leafNode.insert(rect);
        assertFalse("Rectangles should not contain the rectangle outside the boundary", 
            leafNode.rectangles.contains(rect));
    }

    public void testFindExistingRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        Rectangle found = leafNode.find(15, 15);
        assertNotNull("Should find the rectangle within the LeafNode", found);
        assertEquals("Found rectangle should match the inserted rectangle", rect, found);
    }

    public void testFindNonExistingRectangle() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        Rectangle found = leafNode.find(50, 50);
        assertNull("Should not find any rectangle at (50, 50)", found);
    }

    public void testDelete() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        leafNode.insert(rect);
        leafNode.delete(15, 15); // Deleting the rectangle by its center
        assertFalse("Rectangles should not contain the deleted rectangle", 
            leafNode.rectangles.contains(rect));
    }

    public void testDeleteNonExistingRectangle() {
        Rectangle rect1 = new Rectangle(10, 10, 20, 20);
        Rectangle rect2 = new Rectangle(30, 30, 10, 10);
        leafNode.insert(rect1);
        leafNode.delete(50, 50); // Attempt to delete a non-existing rectangle
        assertTrue("Should still contain the existing rectangle after non-existing delete", 
            leafNode.rectangles.contains(rect1));
    }

    public void testPrint() {
        Rectangle rect1 = new Rectangle(10, 10, 20, 20);
        Rectangle rect2 = new Rectangle(30, 30, 10, 10);
        leafNode.insert(rect1);
        leafNode.insert(rect2);
        
        leafNode.print(0);
        String output = outContent.toString();
        assertTrue("Print output should contain the Leaf Node boundary",
            output.contains("Leaf Node - Rectangle at (0.00, 0.00): 100.00x100.00"));
        assertTrue("Print output should contain the first rectangle",
            output.contains("    Rectangle at (10.00, 10.00): 20.00x20.00"));
        assertTrue("Print output should contain the second rectangle",
            output.contains("    Rectangle at (30.00, 30.00): 10.00x10.00"));
    }
}
