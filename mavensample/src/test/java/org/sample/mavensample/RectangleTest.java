package org.sample.mavensample;

import junit.framework.TestCase;

public class RectangleTest extends TestCase {
    
    private Rectangle rectangle;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Create a rectangle at position (0, 0) with a length of 10 and width of 5
        rectangle = new Rectangle(0, 0, 10, 5);
    }

    public void testContains() {
        // Test a point inside the rectangle
        assertTrue("Point (5, 2) should be inside the rectangle", rectangle.contains(5, 2));
        
        // Test a point on the edge of the rectangle
        assertTrue("Point (10, 5) should be on the edge and inside the rectangle", rectangle.contains(10, 5));
        
        // Test points outside the rectangle
        assertFalse("Point (11, 5) should be outside the rectangle", rectangle.contains(11, 5));
        assertFalse("Point (5, 6) should be outside the rectangle", rectangle.contains(5, 6));
        assertFalse("Point (-1, -1) should be outside the rectangle", rectangle.contains(-1, -1));
    }

    public void testToString() {
        String expected = "Rectangle at (0.00, 0.00): 10.00x5.00";
        assertEquals("toString should return the correct format", expected, rectangle.toString());
    }

    @Override
    protected void tearDown() throws Exception {
        rectangle = null;
        super.tearDown();
    }
}


