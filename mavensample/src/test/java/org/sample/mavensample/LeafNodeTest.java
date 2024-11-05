package org.sample.mavensample;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;  
import java.io.PrintStream;

public class LeafNodeTest extends TestCase {

    private LeafNode leafNode;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Create a LeafNode with boundary defined by a rectangle at (0, 0) with width and height of 100
        leafNode = new LeafNode(0, 0, 100, 100);
    }

    public void testInsert() {
        Rectangle rect1 = new Rectangle(10, 10, 5, 5);
        leafNode.insert(rect1);
        
        // Check if the rectangle has been added
        assertEquals("Should contain one rectangle", 1, leafNode.rectangles.size());
        assertEquals("First rectangle should be (10, 10)", rect1, leafNode.rectangles.get(0));
    }

    public void testInsertOutsideBoundary() {
        Rectangle rect2 = new Rectangle(150, 150, 5, 5); // Outside the boundary
        leafNode.insert(rect2);
        
        // Check that it has not been added
        assertEquals("Should still contain no rectangles", 0, leafNode.rectangles.size());
    }

    public void testDelete() {
        Rectangle rect1 = new Rectangle(10, 10, 5, 5);
        leafNode.insert(rect1);
        
        leafNode.delete(10, 10); // Should remove rect1
        
        // Check if the rectangle has been removed
        assertEquals("Should contain no rectangles", 0, leafNode.rectangles.size());
    }

    public void testDeleteNonExistent() {
        Rectangle rect1 = new Rectangle(10, 10, 5, 5);
        leafNode.insert(rect1);
        
        leafNode.delete(20, 20); // Attempting to delete a rectangle that does not exist
        
        // Check that the original rectangle still exists
        assertEquals("Should still contain one rectangle", 1, leafNode.rectangles.size());
    }

    public void testDeleteMultipleRectangles() {
        // Insert multiple rectangles
        Rectangle rect1 = new Rectangle(10, 10, 5, 5);
        Rectangle rect2 = new Rectangle(20, 20, 5, 5);
        Rectangle rect3 = new Rectangle(30, 30, 5, 5);
        leafNode.insert(rect1);
        leafNode.insert(rect2);
        leafNode.insert(rect3);
        
        // Delete one rectangle
        leafNode.delete(20, 20);
        
        // Check the size after deletion
        assertEquals("Should contain two rectangles", 2, leafNode.rectangles.size());
        // Check that the deleted rectangle is not present
        assertNull("Should not find the deleted rectangle", leafNode.find(20, 20));
    }

    public void testDeleteEdgeRectangle() {
        Rectangle rect1 = new Rectangle(10, 10, 5, 5);
        leafNode.insert(rect1);
        
        // Delete the rectangle at the edge
        leafNode.delete(10, 10);
        
        // Verify the rectangle was deleted
        assertEquals("Should contain no rectangles", 0, leafNode.rectangles.size());
    }

    public void testFind() {
        Rectangle rect1 = new Rectangle(10, 10, 5, 5);
        leafNode.insert(rect1);
        
        // Try to find the rectangle
        Rectangle found = leafNode.find(10, 10);
        assertNotNull("Should find the rectangle", found);
        assertEquals("Found rectangle should match", rect1, found);
    }

    public void testFindNotFound() {
        // Attempt to find a rectangle that doesn't exist
        Rectangle found = leafNode.find(20, 20);
        assertNull("Should not find the rectangle", found);
    }

    public void testFindAfterDeletion() {
        Rectangle rect1 = new Rectangle(10, 10, 5, 5);
        leafNode.insert(rect1);
        
        // Delete the rectangle
        leafNode.delete(10, 10);
        
        // Try to find the deleted rectangle
        Rectangle found = leafNode.find(10, 10);
        assertNull("Should not find the deleted rectangle", found);
    }

    public void testInsertMultipleRectangles() {
        // Insert rectangles to the maximum capacity of the LeafNode
        for (int i = 0; i < 5; i++) {
            leafNode.insert(new Rectangle(i * 10, i * 10, 5, 5));
        }
        
        // Check if the number of rectangles is correct
        assertEquals("Should contain five rectangles", 5, leafNode.rectangles.size());
    }

    public void testInsertBeyondCapacity() {
        // Fill the LeafNode to its capacity
        for (int i = 0; i < 5; i++) {
            leafNode.insert(new Rectangle(i * 10, i * 10, 5, 5));
        }
        
        // Attempt to insert a sixth rectangle
        Rectangle rect6 = new Rectangle(50, 50, 5, 5);
        leafNode.insert(rect6);
        
        // Check that the sixth rectangle was not added
        assertEquals("Should still contain five rectangles", 5, leafNode.rectangles.size());
    }

    public void testPrint() {
        Rectangle rect1 = new Rectangle(10, 10, 5, 5);
        leafNode.insert(rect1);
        
        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        leafNode.print(0);
        
        // Reset System.out
        System.setOut(originalOut);
        
        String output = outputStream.toString();
        assertTrue("Output should contain Leaf Node", output.contains("Leaf Node"));
        assertTrue("Output should contain rectangle info", output.contains("Rectangle at (10.00, 10.00): 5.00x5.00"));
    }

    public void testBoundaryConditions() {
        // Check inserting at the edges of the boundary
        Rectangle rect1 = new Rectangle(0, 0, 5, 5); // At the corner
        leafNode.insert(rect1);
        assertEquals("Should contain one rectangle", 1, leafNode.rectangles.size());

        Rectangle rect2 = new Rectangle(95, 95, 10, 10); // Just touching the boundary
        leafNode.insert(rect2); 
        assertEquals("Should still contain one rectangle", 1, leafNode.rectangles.size()); // Should not be added

        Rectangle rect3 = new Rectangle(100, 100, 5, 5); // Outside boundary
        leafNode.insert(rect3);
        assertEquals("Should still contain one rectangle", 1, leafNode.rectangles.size()); // Should not be added
    }
}
