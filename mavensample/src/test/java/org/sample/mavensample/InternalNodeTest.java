package org.sample.mavensample;

import junit.framework.TestCase;

public class InternalNodeTest extends TestCase {
    private InternalNode internalNode;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Create an InternalNode for testing with arbitrary dimensions
        internalNode = new InternalNode(0, 0, 100, 100);
    }

    public void testConstructor() {
        assertNotNull("InternalNode should be created", internalNode);
        assertNotNull("TopLeft should be initialized", internalNode.topLeft);
        assertNotNull("TopRight should be initialized", internalNode.topRight);
        assertNotNull("BottomLeft should be initialized", internalNode.bottomLeft);
        assertNotNull("BottomRight should be initialized", internalNode.bottomRight);
    }

    public void testUpdateExistingRectangle() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5);
        internalNode.insert(rect);
        internalNode.update(10, 10, 10, 10); // Update the rectangle
        Rectangle updatedRectangle = internalNode.find(10, 10);
        assertNotNull("Updated rectangle should exist", updatedRectangle);
        assertEquals("Updated rectangle should have the new length", 10.0f, updatedRectangle.length);
        assertEquals("Updated rectangle should have the new width", 10.0f, updatedRectangle.width);
    }

    public void testUpdateNonExistingRectangle() {
        try {
            internalNode.update(30, 30, 10, 10); // Try to update a non-existing rectangle
            fail("Should throw an exception for updating non-existing rectangle");
        } catch (Exception e) {
            assertEquals("Rectangle not found at specified location", e.getMessage());
        }
    }

    public void testSubdivide() {
        // Check if the subdivisions are correctly initialized
        assertNotNull("TopLeft should be a LeafNode", internalNode.topLeft);
        assertNotNull("TopRight should be a LeafNode", internalNode.topRight);
        assertNotNull("BottomLeft should be a LeafNode", internalNode.bottomLeft);
        assertNotNull("BottomRight should be a LeafNode", internalNode.bottomRight);
    }

    public void testGetTotalRectangles() throws Exception {
        internalNode.insert(new Rectangle(10, 10, 5, 5));
        internalNode.insert(new Rectangle(20, 20, 5, 5));
        assertEquals("Total rectangles should be 2 initially", 2, internalNode.getTotalRectangles());
        internalNode.insert(new Rectangle(15, 15, 5, 5));
        assertEquals("Total rectangles should be 3 after adding one", 3, internalNode.getTotalRectangles());
    }

    public void testGetChildren() {
        Node[] children = internalNode.getChildren();
        assertEquals("There should be 4 children in the internal node", 4, children.length);
        assertTrue("Top left child should be an instance of LeafNode", children[0] instanceof LeafNode);
        assertTrue("Top right child should be an instance of LeafNode", children[1] instanceof LeafNode);
        assertTrue("Bottom left child should be an instance of LeafNode", children[2] instanceof LeafNode);
        assertTrue("Bottom right child should be an instance of LeafNode", children[3] instanceof LeafNode);
    }

    public void testInsertValidRectangle() throws Exception {
        Rectangle newRectangle = new Rectangle(15, 15, 5, 5);
        internalNode.insert(newRectangle); // Should succeed
        assertEquals("Total rectangles should be 1 after insertion", 1, internalNode.getTotalRectangles());
    }

    public void testInsertRectangleOutOfBoundary() {
        Rectangle outOfBoundaryRectangle = new Rectangle(200, 200, 5, 5);
        try {
            internalNode.insert(outOfBoundaryRectangle);
            fail("Should throw an exception for inserting out of boundary");
        } catch (Exception e) {
            assertEquals("Rectangle is out of the internal node's boundary.", e.getMessage());
        }
    }

    public void testInsertDuplicateRectangle() throws Exception {
        Rectangle duplicateRectangle = new Rectangle(10, 10, 5, 5);
        internalNode.insert(duplicateRectangle); // Should succeed first
        try {
            internalNode.insert(duplicateRectangle); // This should throw an exception
            fail("Should throw an exception for double insert");
        } catch (Exception e) {
            assertEquals("You can not double insert at this position.", e.getMessage());
        }
    }

    public void testDeleteExistingRectangle() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5);
        internalNode.insert(rect);
        internalNode.delete(10, 10);
        assertEquals("Total rectangles should be 0 after deletion", 0, internalNode.getTotalRectangles());
        try {
            internalNode.find(10, 10); // This should now throw an exception
            fail("Should have thrown an exception since rectangle was deleted");
        } catch (Exception e) {
            assertEquals("Nothing is at 10.0, 10.0", e.getMessage());
        }
    }

    public void testDeleteNonExistingRectangle() {
        try {
            internalNode.delete(30, 30);
            fail("Should throw an exception when trying to delete a non-existing rectangle");
        } catch (Exception e) {
            assertEquals("Nothing to delete at 30.0, 30.0", e.getMessage());
        }
    }

    public void testFindExistingRectangle() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5);
        internalNode.insert(rect);
        Rectangle found = internalNode.find(10, 10);
        assertNotNull("Should find rectangle at (10, 10)", found);
        assertEquals("Found rectangle should have the correct dimensions", 5.0f, found.length);
    }

    public void testFindNonExistingRectangle() {
        try {
            internalNode.find(30, 30);
            fail("Should throw an exception for not found rectangle");
        } catch (Exception e) {
            assertEquals("Nothing is at 30.0, 30.0", e.getMessage());
        }
    }

    public void testPrint() {
        // Capture the output of the print method
        internalNode.print(0);
        // This test might be expanded with an output capturing mechanism if needed.
    }
    public void testInsertIntoTopLeftChild() throws Exception {
        Rectangle rect = new Rectangle(10, 60, 5, 5); // Should go to top left
        internalNode.insert(rect);
        assertEquals("Total rectangles should be 1 after insertion", 1, internalNode.getTotalRectangles());
    }

    public void testInsertIntoTopRightChild() throws Exception {
        Rectangle rect = new Rectangle(60, 60, 5, 5); // Should go to top right
        internalNode.insert(rect);
        assertEquals("Total rectangles should be 1 after insertion", 1, internalNode.getTotalRectangles());
    }

    public void testInsertIntoBottomLeftChild() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5); // Should go to bottom left
        internalNode.insert(rect);
        assertEquals("Total rectangles should be 1 after insertion", 1, internalNode.getTotalRectangles());
    }

    public void testInsertIntoBottomRightChild() throws Exception {
        Rectangle rect = new Rectangle(60, 10, 5, 5); // Should go to bottom right
        internalNode.insert(rect);
        assertEquals("Total rectangles should be 1 after insertion", 1, internalNode.getTotalRectangles());
    }

    public void testInsertIntoInvalidPosition() {
        Rectangle invalidRectangle = new Rectangle(150, 150, 5, 5); // Out of bounds
        try {
            internalNode.insert(invalidRectangle);
            fail("Should throw an exception for inserting out of boundary");
        } catch (Exception e) {
            assertEquals("Rectangle is out of the internal node's boundary.", e.getMessage());
        }
    }

    public void testDeleteFromTopLeftChild() throws Exception {
        Rectangle rect = new Rectangle(10, 60, 5, 5); // Should go to top left
        internalNode.insert(rect);
        internalNode.delete(10, 60);
        assertEquals("Total rectangles should be 0 after deletion", 0, internalNode.getTotalRectangles());
    }

    public void testDeleteFromTopRightChild() throws Exception {
        Rectangle rect = new Rectangle(60, 60, 5, 5); // Should go to top right
        internalNode.insert(rect);
        internalNode.delete(60, 60);
        assertEquals("Total rectangles should be 0 after deletion", 0, internalNode.getTotalRectangles());
    }

    public void testDeleteFromBottomLeftChild() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5); // Should go to bottom left
        internalNode.insert(rect);
        internalNode.delete(10, 10);
        assertEquals("Total rectangles should be 0 after deletion", 0, internalNode.getTotalRectangles());
    }

    public void testDeleteFromBottomRightChild() throws Exception {
        Rectangle rect = new Rectangle(60, 10, 5, 5); // Should go to bottom right
        internalNode.insert(rect);
        internalNode.delete(60, 10);
        assertEquals("Total rectangles should be 0 after deletion", 0, internalNode.getTotalRectangles());
    }

    public void testFindInTopLeftChild() throws Exception {
        Rectangle rect = new Rectangle(10, 60, 5, 5); // Should go to top left
        internalNode.insert(rect);
        Rectangle found = internalNode.find(10, 60);
        assertNotNull("Should find rectangle in top left child", found);
        assertEquals("Found rectangle should have the correct dimensions", 5.0f, found.length);
    }

    public void testFindInTopRightChild() throws Exception {
        Rectangle rect = new Rectangle(60, 60, 5, 5); // Should go to top right
        internalNode.insert(rect);
        Rectangle found = internalNode.find(60, 60);
        assertNotNull("Should find rectangle in top right child", found);
        assertEquals("Found rectangle should have the correct dimensions", 5.0f, found.length);
    }

    public void testFindInBottomLeftChild() throws Exception {
        Rectangle rect = new Rectangle(10, 10, 5, 5); // Should go to bottom left
        internalNode.insert(rect);
        Rectangle found = internalNode.find(10, 10);
        assertNotNull("Should find rectangle in bottom left child", found);
        assertEquals("Found rectangle should have the correct dimensions", 5.0f, found.length);
    }

    public void testFindInBottomRightChild() throws Exception {
        Rectangle rect = new Rectangle(60, 10, 5, 5); // Should go to bottom right
        internalNode.insert(rect);
        Rectangle found = internalNode.find(60, 10);
        assertNotNull("Should find rectangle in bottom right child", found);
        assertEquals("Found rectangle should have the correct dimensions", 5.0f, found.length);
    }
    
}
