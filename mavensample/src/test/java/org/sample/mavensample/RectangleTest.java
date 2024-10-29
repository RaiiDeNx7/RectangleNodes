package org.sample.mavensample;

import junit.framework.TestCase;

public class RectangleTest extends TestCase {

    public void testRectangleCreation() {
        Rectangle rect = new Rectangle(0.0, 0.0, 10.0, 5.0);
        assertEquals("X-coordinate should be initialized to 0.0", 0.0, rect.x);
        assertEquals("Y-coordinate should be initialized to 0.0", 0.0, rect.y);
        assertEquals("Width should be initialized to 10.0", 10.0, rect.width);
        assertEquals("Height should be initialized to 5.0", 5.0, rect.height);
    }

    public void testContainsPointInside() {
        Rectangle rect = new Rectangle(1.0, 1.0, 4.0, 3.0);
        assertTrue("Point (2.0, 2.0) should be inside the rectangle", rect.contains(2.0, 2.0));
        assertTrue("Point (1.0, 1.0) should be inside the rectangle (bottom-left corner)", rect.contains(1.0, 1.0));
        assertTrue("Point (5.0, 4.0) should be inside the rectangle (top-right corner)", rect.contains(5.0, 4.0));
    }

    public void testContainsPointOutside() {
        Rectangle rect = new Rectangle(1.0, 1.0, 4.0, 3.0);
        assertFalse("Point (0.0, 0.0) should be outside the rectangle", rect.contains(0.0, 0.0));
        assertFalse("Point (6.0, 2.0) should be outside the rectangle", rect.contains(6.0, 2.0));
        assertFalse("Point (2.0, 5.0) should be outside the rectangle", rect.contains(2.0, 5.0));
    }

    public void testToString() {
        Rectangle rect = new Rectangle(1.5, 2.5, 3.0, 4.0);
        assertEquals("toString output should match the expected format", "Rectangle at (1.50, 2.50): 3.00x4.00", rect.toString());
    }
}
