package org.sample.mavensample;

import junit.framework.TestCase;

public class NodeTest extends TestCase {

    // Concrete subclass for testing purposes
    class TestNode extends Node {

        public TestNode(Rectangle boundary) {
            super(boundary);
        }

        @Override
        void insert(Rectangle r) {
            // Mock implementation for testing purposes
        }

        @Override
        void print(int depth) {
            // Mock implementation for testing purposes
        }

        @Override
        void delete(double x, double y) {
            // Mock implementation for testing purposes
        }

        @Override
        Rectangle find(double x, double y) {
            // Check if the given point (x, y) is within the boundary rectangle
            return boundary.contains(x, y) ? boundary : null;
        }
    }

    public void testNodeCreation() {
        Rectangle boundary = new Rectangle(0.0, 0.0, 10.0, 10.0);
        TestNode node = new TestNode(boundary);
        assertEquals("Boundary should be initialized correctly", boundary, node.boundary);
    }

    public void testFindWithinBoundary() {
        Rectangle boundary = new Rectangle(0.0, 0.0, 10.0, 10.0);
        TestNode node = new TestNode(boundary);
        assertNotNull("Find should return the boundary when point is within it", node.find(5.0, 5.0));
        assertEquals("Returned rectangle should match the boundary", boundary, node.find(5.0, 5.0));
    }

    public void testFindOutsideBoundary() {
        Rectangle boundary = new Rectangle(0.0, 0.0, 10.0, 10.0);
        TestNode node = new TestNode(boundary);
        assertNull("Find should return null when point is outside the boundary", node.find(15.0, 15.0));
    }
}
