package org.sample.mavensample;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainTest extends TestCase {

    private Main quadtree;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    protected void setUp() {
        quadtree = new Main(-50, -50, 100, 100);
        System.setOut(new PrintStream(outContent));
    }

    protected void tearDown() {
        System.setOut(originalOut);
    }

    public void testInsert() {
        quadtree.insert(0, 0, 10, 10);
        quadtree.find(0, 0);
        assertTrue("Output should contain the rectangle details",
            outContent.toString().contains("Rectangle at (0.00, 0.00): 10.00x10.00"));
    }

    public void testDelete() {
        quadtree.insert(0, 0, 10, 10);
        quadtree.delete(0, 0);
        quadtree.find(0, 0);
        assertTrue("Output should indicate rectangle not found after deletion",
            outContent.toString().contains("Rectangle not found"));
    }

    public void testUpdate() {
        quadtree.insert(0, 0, 10, 10);
        quadtree.update(0, 0, 5, 5, 15, 15);
        quadtree.find(5, 5);
        assertTrue("Output should contain updated rectangle details",
            outContent.toString().contains("Rectangle at (5.00, 5.00): 15.00x15.00"));
    }

    public void testFind() {
        quadtree.insert(0, 0, 10, 10);
        quadtree.find(0, 0);
        assertTrue("Output should contain the rectangle details for find",
            outContent.toString().contains("Rectangle at (0.00, 0.00): 10.00x10.00"));

        quadtree.find(20, 20);
        assertTrue("Output should indicate rectangle not found for out-of-bound point",
            outContent.toString().contains("Rectangle not found"));
    }

    public void testDump() {
        quadtree.insert(0, 0, 10, 10);
        quadtree.insert(20, 20, 5, 5);
        quadtree.dump();
        assertTrue("Dump should output rectangles and hierarchy details",
            outContent.toString().contains("Rectangle at (0.00, 0.00): 10.00x10.00") &&
            outContent.toString().contains("Rectangle at (20.00, 20.00): 5.00x5.00"));
    }
}