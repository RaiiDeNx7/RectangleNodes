package org.sample.mavensample;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainTest extends TestCase {

    private Main quadtree;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    protected void setUp() {
        // Initialize Main with a specified boundary
        quadtree = new Main(-50, -50, 100, 100);
        System.setOut(new PrintStream(outContent));
    }

    protected void tearDown() {
        System.setOut(originalOut);
    }

    public void testInsertRectangle() {
        quadtree.insert(10, 10, 20, 20);
        assertNotNull("Should find inserted rectangle", quadtree.root.find(15, 15));
    }

    public void testInsertMultipleRectangles() {
        for (int i = 0; i < 5; i++) {
            quadtree.insert(i * 10, i * 10, 5, 5);
        }
        assertEquals("Should find 5 rectangles", 5, ((LeafNode) quadtree.root).rectangles.size());
    }

    public void testInsertRectangleExceedingLeafCapacity() {
        for (int i = 0; i < 6; i++) {
            quadtree.insert(i * 10, i * 10, 5, 5);
        }
        assertTrue("Should have created an InternalNode after exceeding capacity", quadtree.root instanceof InternalNode);
    }

    public void testDeleteRectangle() {
        quadtree.insert(10, 10, 20, 20);
        quadtree.delete(15, 15); // Delete by center
        assertNull("Should not find rectangle after deletion", quadtree.root.find(15, 15));
    }

    public void testDeleteNonExistingRectangle() {
        quadtree.insert(10, 10, 20, 20);
        quadtree.delete(50, 50); // Attempt to delete a non-existing rectangle
        assertNotNull("Should still find the existing rectangle after non-existing delete", quadtree.root.find(15, 15));
    }

    public void testUpdateRectangle() {
        quadtree.insert(10, 10, 20, 20);
        quadtree.update(10, 10, 30, 30, 10, 10); // Update the rectangle
        assertNull("Should not find the old rectangle after update", quadtree.root.find(15, 15));
        assertNotNull("Should find the new rectangle after update", quadtree.root.find(35, 35));
    }

    public void testUpdateRectangleNotFound() {
        quadtree.update(100, 100, 30, 30, 10, 10); // Attempt to update a non-existing rectangle
        assertTrue("Output should contain 'Rectangle not found at'", outContent.toString().contains("Rectangle not found at (100.0, 100.0)"));
    }

    public void testFindRectangle() {
        quadtree.insert(10, 10, 20, 20);
        quadtree.find(15, 15); // Find by center
        assertTrue("Output should contain the rectangle details", outContent.toString().contains("Rectangle at (10.00, 10.00): 20.00x20.00"));
    }

    public void testFindNonExistingRectangle() {
        quadtree.find(50, 50); // Find a non-existing rectangle
        assertTrue("Output should indicate rectangle not found", outContent.toString().contains("Rectangle not found"));
    }

    public void testDump() {
        quadtree.insert(10, 10, 20, 20);
        quadtree.dump();
        assertTrue("Output should contain the dumped details", outContent.toString().contains("Leaf Node -"));
    }

    public void testProcessCommands() throws IOException {
        // Create a temporary command file
        File tempFile = File.createTempFile("commands", ".cmmd");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("insert 10 10 20 20\n");
        writer.write("find 15 15\n");
        writer.write("update 10 10 30 30 10 10\n");
        writer.write("find 35 35\n");
        writer.write("delete 15 15\n");
        writer.write("find 15 15\n");
        writer.write("dump\n");
        writer.write("unknowncommand\n");
        writer.close();

        // Process the commands in the temporary file
        quadtree.processCommands(tempFile.getAbsolutePath());

        String output = outContent.toString();
        assertTrue("Output should confirm rectangle insertion", output.contains("Rectangle at (10.00, 10.00): 20.00x20.00"));
        assertTrue("Output should confirm rectangle update", output.contains("Rectangle at (30.00, 30.00): 10.00x10.00"));
        assertTrue("Output should confirm rectangle deletion", output.contains("Rectangle not found"));
        assertTrue("Output should show dumped details", output.contains("Leaf Node -"));
        assertTrue("Output should indicate unknown command", output.contains("Unknown command: unknowncommand"));

        // Cleanup the temporary file
        tempFile.delete();
    }

    public void testInvalidCommandFormat() throws IOException {
        // Create a temporary command file
        File tempFile = File.createTempFile("commands", ".cmmd");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("insert 10 10 20\n"); // Invalid insert command
        writer.write("delete 10\n"); // Invalid delete command
        writer.write("update 10 10\n"); // Invalid update command
        writer.write("find 10 10\n"); // Valid find command
        writer.write("dump\n");
        writer.close();

        // Process the commands in the temporary file
        quadtree.processCommands(tempFile.getAbsolutePath());

        String output = outContent.toString();
        assertTrue("Output should indicate invalid insert command format", output.contains("Invalid insert command format."));
        assertTrue("Output should indicate invalid delete command format", output.contains("Invalid delete command format."));
        assertTrue("Output should indicate invalid update command format", output.contains("Invalid update command format."));
        assertTrue("Output should confirm find command works", output.contains("Rectangle at (10.00, 10.00): 20.00x20.00"));
        assertTrue("Output should show dumped details", output.contains("Leaf Node -"));

        // Cleanup the temporary file
        tempFile.delete();
    }

    public void testInvalidNumberFormat() throws IOException {
        // Create a temporary command file
        File tempFile = File.createTempFile("commands", ".cmmd");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("insert 10 ten 20 20\n"); // Invalid number
        writer.close();

        // Process the commands in the temporary file
        quadtree.processCommands(tempFile.getAbsolutePath());

        String output = outContent.toString();
        assertTrue("Output should indicate invalid number format", output.contains("Invalid number format for command: insert"));

        // Cleanup the temporary file
        tempFile.delete();
    }
}
