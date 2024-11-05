package org.sample.mavensample;

import junit.framework.TestCase;

public class NodeTest extends TestCase {

	private class TestNode extends Node {
        public TestNode(Rectangle boundary) {
            super(boundary);
        }

        @Override
        void insert(Rectangle r) {
            // No-op for testing, or you could implement simple storage logic
        }

        @Override
        void delete(float x, float y) {
            // No-op for testing
        }

        @Override
        Rectangle find(float x, float y) {
            // For testing, return null to simulate not found
            return null;
        }

        @Override
        void print(int depth) {
            // For testing, simply print a placeholder
            System.out.println("TestNode at depth " + depth);
        }
    }

    public void testNodeInitialization() {
        Rectangle rect = new Rectangle(0, 0, 10, 10);
        Node node = new TestNode(rect);
        
        // Verify that the boundary is set correctly
        assertNotNull("Node boundary should not be null", node.boundary);
        assertEquals("Node boundary should match the initialized rectangle", rect, node.boundary);
    }

    public void testPrint() {
        Rectangle rect = new Rectangle(0, 0, 10, 10);
        Node node = new TestNode(rect);

        // Capture the output of print method
        // This requires redirection of the output stream if you want to check the printed text.
        // For now, we'll just call it to ensure it executes without exception.
        node.print(1);
    }
}
