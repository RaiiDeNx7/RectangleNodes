package org.sample.mavensample;

import junit.framework.TestCase;

public class PointTest extends TestCase {

    public void testPointInitialization() {
        // Initialize a point at (3.5, -2.1)
        Point point = new Point(3.5f, -2.1f);

        // Verify that x and y coordinates are set correctly
        assertEquals("X coordinate should be 3.5", 3.5f, point.x);
        assertEquals("Y coordinate should be -2.1", -2.1f, point.y);
    }
}
