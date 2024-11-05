package org.sample.mavensample;

import junit.framework.TestCase;

public class QuadTreeTest extends TestCase {
    private QuadTree quadTree;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        quadTree = new QuadTree(); // Initialize a new QuadTree before each test
    }

    public void testInsertRectangle() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5);
        quadTree.insert(rect);
        assertEquals("Should contain one rectangle after insertion", 1, ((LeafNode) quadTree.getRoot()).rectangles.size());
    }

    public void testInsertMultipleRectangles() throws Exception {
        for (int i = 0; i < 5; i++) {
            quadTree.insert(new Rectangle(i * 10, i * 10, 5, 5));
        }
        assertEquals("Should contain five rectangles", 5, ((LeafNode) quadTree.getRoot()).rectangles.size());
    }

    public void testInsertExceedingCapacity() throws Exception {
        QuadTree quadTree = new QuadTree();
        
        // Insert 5 rectangles to reach capacity
        for (int i = 0; i < 5; i++) {
            quadTree.insert(new Rectangle(0, 0, 10, 10)); // Adjust coordinates as necessary
        }
        
        // Now insert the sixth rectangle to trigger a split
        quadTree.insert(new Rectangle(0, 0, 10, 10));
        
        // Assert the expected state
        InternalNode root = (InternalNode) quadTree.getRoot();
        assertEquals("Top left child should have no rectangles", 0, ((LeafNode) root.topLeft).rectangles.size());
        // Assert other child nodes as necessary
    }



    public void testFindExistingRectangle() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5);
        quadTree.insert(rect);
        Rectangle found = quadTree.find(10, 10);
        assertNotNull("Should find the inserted rectangle", found);
        assertEquals("Found rectangle should match the inserted rectangle", rect, found);
    }

    public void testFindNonExistentRectangle() {
        try {
            quadTree.find(20, 20);
            fail("Finding a non-existent rectangle should throw an exception");
        } catch (Exception e) {
            assertEquals("Expected exception message not received", "Nothing is at 20.0, 20.0", e.getMessage());
        }
    }

    public void testDeleteRectangle() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5);
        quadTree.insert(rect);
        quadTree.delete(10, 10);
        assertEquals("Should be empty after deletion", 0, ((LeafNode) quadTree.getRoot()).rectangles.size());
    }

    public void testDeleteNonExistentRectangle() {
        try {
            quadTree.delete(20, 20);
            fail("Deleting a non-existent rectangle should throw an exception");
        } catch (Exception e) {
            assertEquals("Expected exception message not received", "Nothing to delete at 20.0, 20.0", e.getMessage());
        }
    }

    public void testUpdateExistingRectangle() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5);
        quadTree.insert(rect);
        quadTree.update(10, 10, 10, 10);
        Rectangle updated = quadTree.find(10, 10);
        assertNotNull("Should find the updated rectangle", updated);
        assertEquals("Updated rectangle dimensions should match", 10, updated.length);
        assertEquals("Updated rectangle dimensions should match", 10, updated.width);
    }

    public void testUpdateNonExistentRectangle() {
        try {
            quadTree.update(20, 20, 10, 10);
            fail("Updating a non-existent rectangle should throw an exception");
        } catch (Exception e) {
            assertEquals("Expected exception message not received", "No rectangle found at 20.0, 20.0", e.getMessage());
        }
    }

    public void testDumpTree() {
        // Redirecting output for testing dump functionality is more complex
        // Usually done by asserting printed output or capturing it in a mock
        try {
            quadTree.dump(); // Test if this executes without throwing an exception
        } catch (Exception e) {
            fail("Dumping the QuadTree should not throw an exception: " + e.getMessage());
        }
    }
}
