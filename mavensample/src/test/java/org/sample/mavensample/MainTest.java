package org.sample.mavensample;

import junit.framework.TestCase;

import java.io.*;

public class MainTest extends TestCase {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Set up to capture console output
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // Restore original output
        System.setOut(originalOut);
    }

    public void testInsertCommand() throws Exception {
        String command = "insert 10 10 5 5;";
        String[] args = { "dummy.txt" };
        
        // Create a temporary file with the command
        createTempFile(args[0], command);

        Main.main(args);

        // Verify the inserted rectangle
        QuadTree quadTree = new QuadTree();
        Rectangle expectedRectangle = new Rectangle(10, 10, 5, 5);
        Rectangle found = quadTree.find(10, 10);
        assertNotNull("Rectangle should be found", found);
        assertEquals("Inserted rectangle should match", expectedRectangle, found);
    }

    public void testFindCommand() throws Exception {
        // Prepare a file with commands
        String commandInsert = "insert 20 20 5 5;";
        String commandFind = "find 20 20;";
        String[] args = { "dummy.txt" };

        createTempFile(args[0], commandInsert + "\n" + commandFind);

        Main.main(args);

        // Check output for found rectangle
        String output = outputStream.toString();
        assertTrue("Output should contain rectangle info", output.contains("Rectangle at (20.00, 20.00): 5.00x5.00"));
    }

    public void testDeleteCommand() throws Exception {
        String commandInsert = "insert 30 30 5 5;";
        String commandDelete = "delete 30 30;";
        String[] args = { "dummy.txt" };

        createTempFile(args[0], commandInsert + "\n" + commandDelete);

        Main.main(args);

        // Try to find the deleted rectangle
        QuadTree quadTree = new QuadTree();
        Rectangle found = quadTree.find(30, 30);
        assertNull("Rectangle should be deleted", found);
    }

    public void testUpdateCommand() throws Exception {
        String commandInsert = "insert 40 40 5 5;";
        String commandUpdate = "update 40 40 10 10;";
        String[] args = { "dummy.txt" };

        createTempFile(args[0], commandInsert + "\n" + commandUpdate);

        Main.main(args);

        // Verify that the rectangle has been updated
        QuadTree quadTree = new QuadTree();
        Rectangle found = quadTree.find(40, 40);
        assertNull("Old rectangle should be deleted", found);

        Rectangle expectedUpdatedRectangle = new Rectangle(40, 40, 10, 10);
        Rectangle updatedFound = quadTree.find(40, 40);
        assertNotNull("Updated rectangle should be found", updatedFound);
        assertEquals("Updated rectangle should match", expectedUpdatedRectangle, updatedFound);
    }

    public void testInvalidCommand() throws Exception {
        String commandInvalid = "invalidCommand;";
        String[] args = { "dummy.txt" };

        createTempFile(args[0], commandInvalid);

        Main.main(args);

        // Check output for invalid command message
        String output = outputStream.toString();
        assertTrue("Output should indicate invalid command", output.contains("Invalid command"));
    }

    public void testFileReadError() throws Exception {
        String[] args = { "nonexistent.txt" };
        
        // Running main with a non-existent file should print an error
        Main.main(args);

        String output = outputStream.toString();
        assertTrue("Output should indicate file read error", output.contains("Error reading the file"));
    }

    public void testDumpCommand() throws Exception {
        String commandInsert = "insert 50 50 5 5;";
        String commandDump = "dump;";
        String[] args = { "dummy.txt" };

        createTempFile(args[0], commandInsert + "\n" + commandDump);

        Main.main(args);

        // Verify that dump outputs the correct structure
        String output = outputStream.toString();
        assertTrue("Output should contain dump info", output.contains("Leaf Node"));
    }

    // Helper method to create a temporary file with the given content
    private void createTempFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }
    public void testFindRectangle() throws Exception {
        // Setup a temporary file with commands
        String fileName = "test_commands.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("insert 10 10 5 5;\n");
            writer.write("find 10 10;\n");
            writer.write("find 20 20;\n");
        }

        // Execute the main method
        String[] args = {fileName};
        Main.main(args);

        // Check the output
        String output = outputStream.toString();
        assertTrue("Output should contain rectangle info", output.contains("Rectangle at (10.00, 10.00): 5.00x5.00"));
        assertTrue("Output should indicate rectangle not found", output.contains("Rectangle not found"));
    }

    public void testParseCommandSuccess() throws Exception {
        String command = "insert 10 10 5 5;";
        String[] result = Main.parse_InsertInput(command);
        assertEquals("Should parse correctly", "insert", result[0]);
        assertEquals("Should parse correctly", "10", result[1]);
        assertEquals("Should parse correctly", "10", result[2]);
        assertEquals("Should parse correctly", "5", result[3]);
        assertEquals("Should parse correctly", "5", result[4]);
    }

    public void testParseCommandFailure() {
        String command = "insert 10 10 5;"; // Invalid number of parts
        try {
            Main.parse_InsertInput(command);
            fail("Should throw an exception for invalid command");
        } catch (Exception e) {
            assertEquals("Cannot parse command: " + command, e.getMessage());
        }
    }
    public void testMainClassExists() {
        // Ensure the Main class can be instantiated
        assertNotNull("Main class should exist", new Main());
    }

    public void testMainMethodRuns() {
        // Run the main method without any arguments
        try {
            String[] args = {};
            Main.main(args);
            // If no exceptions are thrown, the test passes
        } catch (Exception e) {
            fail("Main method should run without exceptions: " + e.getMessage());
        }
    }
}
