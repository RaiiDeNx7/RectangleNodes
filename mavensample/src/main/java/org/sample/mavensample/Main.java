package org.sample.mavensample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    Node root;

    /**
     * Default Main Constructor
     * Description: Constructor that initializes the Main class, creating a root node for the quadtree with a specified boundary. 
     * The root starts as a LeafNode.
     * 
     * @param x (double): The x-coordinate of the bottom-left corner
     * @param y (double): The y-coordinate of the bottom-left corner
     * @param width (double): The width of the rectangle
     * @param height (double): The height of the rectangle
     */
    public Main(double x, double y, double width, double height) {
        root = new LeafNode(x, y, width, height);
    }
    

    /**
     * 
     * Description: Inserts a rectangle defined by its position (x,y) and dimensions (width,height) into the quadtree. 
     * If the root is a LeafNode that exceeds the rectangle capacity (more than 4 rectangles), 
     * it’s converted to an InternalNode, and existing rectangles are re-inserted.
     * 
     * @param x (double): The x-coordinate of the bottom-left corner
     * @param y (double): The y-coordinate of the bottom-left corner
     * @param width (double): The width of the rectangle
     * @param height (double): The height of the rectangle
     */
    public void insert(double x, double y, double width, double height) {
        Rectangle r = new Rectangle(x, y, width, height);
        root.insert(r);

        if (root instanceof LeafNode && ((LeafNode) root).rectangles.size() > 4) {
            InternalNode newRoot = new InternalNode(root.boundary.x, root.boundary.y, root.boundary.width, root.boundary.height);
            for (Rectangle rect : ((LeafNode) root).rectangles) {
                newRoot.insert(rect);
            }
            root = newRoot;
        }
    }

    /**
     * Description: Deletes a rectangle containing the specified point (x,y) from the quadtree. 
     * Delegates deletion to the root node.
     * 
     * @param x (double): The x-coordinate of the point within the rectangle to delete.
     * @param y (double): The y-coordinate of the point within the rectangle to delete.
     */
    public void delete(double x, double y) {
        root.delete(x, y);
    }

    /**
     * Description: Updates a rectangle at the specified point (x,y) by removing it 
     * and inserting a new rectangle with updated coordinates and dimensions.
     * 
     * @param x (double): The x-coordinate of the point within the existing rectangle.
     * @param y (double): The y-coordinate of the point within the existing rectangle.
     * @param newX (double): The x-coordinate of the new rectangle’s bottom-left corner.
     * @param newY (double): The y-coordinate of the new rectangle’s bottom-left corner.
     * @param newWidth (double): The width of the new rectangle.
     * @param newHeight (double): The height of the new rectangle.
     */
    public void update(double x, double y, double newX, double newY, double newWidth, double newHeight) {
        Rectangle r = root.find(x, y);
        if (r != null) {
            delete(x, y); // Remove the old rectangle
            insert(newX, newY, newWidth, newHeight); // Insert the updated rectangle
        } else {
            System.out.println("Rectangle not found at (" + x + ", " + y + ")");
        }
    }

    /**
     * Description: Searches for a rectangle containing the point (x,y) and prints it if found. 
     * If not found, outputs an appropriate message.
     * 
     * @param x (double): The x-coordinate of the point within the rectangle.
     * @param y (double): The y-coordinate of the point within the rectangle.
     */
    public void find(double x, double y) {
        Rectangle r = root.find(x, y);
        if (r != null) {
            System.out.println(r);
        } else {
            System.out.println("Rectangle not found");
        }
    }

    /**
     * Description: Prints the entire structure of the quadtree, 
     * including each node and its contents, starting from the root.
     * 
     */
    public void dump() {
        root.print(0);
    }

    
    /**
     * Description: Reads commands from a .cmmd file and processes each command to manipulate the quadtree. 
     * Recognized commands are insert, delete, update, find, and dump. 
     * Handles exceptions for invalid formats and unrecognized commands.
     * 
     * @param filePath (String): Path to the .cmmd file containing commands.
     * 
     * @exception IOException: If an error occurs while reading the file.
     * @exception NumberFormatException: For invalid number format within commands. 
     */
    public void processCommands(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Trim whitespace and remove trailing semicolons or unwanted characters
                line = line.trim().replaceAll(";$", "");

                if (line.isEmpty()) continue; // Skip empty lines

                String[] parts = line.split("\\s+");  // Split by whitespace
                String command = parts[0].toLowerCase();  // Normalize to lowercase

                try {
                    switch (command) {
                        case "insert":
                            if (parts.length == 5) {
                                double x = Double.parseDouble(parts[1]);
                                double y = Double.parseDouble(parts[2]);
                                double width = Double.parseDouble(parts[3]);
                                double height = Double.parseDouble(parts[4]);
                                insert(x, y, width, height);
                            } else {
                                System.out.println("Invalid insert command format.");
                            }
                            break;

                        case "delete":
                            if (parts.length == 3) {
                                double x = Double.parseDouble(parts[1]);
                                double y = Double.parseDouble(parts[2]);
                                delete(x, y);
                            } else {
                                System.out.println("Invalid delete command format.");
                            }
                            break;

                        // Updated update command to handle six arguments
                        case "update":
                            if (parts.length == 7) {
                                double x = Double.parseDouble(parts[1]);
                                double y = Double.parseDouble(parts[2]);
                                double newX = Double.parseDouble(parts[3]);
                                double newY = Double.parseDouble(parts[4]);
                                double newWidth = Double.parseDouble(parts[5]);
                                double newHeight = Double.parseDouble(parts[6]);
                                update(x, y, newX, newY, newWidth, newHeight);
                            } else {
                                System.out.println("Invalid update command format.");
                            }
                            break;

                        case "find":
                            if (parts.length == 3) {
                                double x = Double.parseDouble(parts[1]);
                                double y = Double.parseDouble(parts[2]);
                                find(x, y);
                            } else {
                                System.out.println("Invalid find command format.");
                            }
                            break;

                        case "dump":
                            dump();
                            break;

                        default:
                            System.out.println("Unknown command: " + command);
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format for command: " + command);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Description: Entry point for the application. Expects a .cmmd file path as an argument. 
     * Initializes a quadtree with a default boundary and processes commands from the file.
     * 
     * @param args (String[]): Command-line arguments. The first argument should be the file path for the .cmmd file.
     * 
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <path-to-cmmd-file>");
            return;
        }

        Main quadtree = new Main(-50, -50, 100, 100);
        quadtree.processCommands(args[0]);
    }
}
  