package org.sample.mavensample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    Node root;

    /**
     * Default Main Constructor
     * 
     * @param x The x-coordinate of the bottom-left corner
     * @param y The y-coordinate of the bottom-left corner
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     */
    public Main(double x, double y, double width, double height) {
        root = new LeafNode(x, y, width, height);
    }

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

    public void delete(double x, double y) {
        root.delete(x, y);
    }

    // Updated method with six parameters
    public void update(double x, double y, double newX, double newY, double newWidth, double newHeight) {
        Rectangle r = root.find(x, y);
        if (r != null) {
            delete(x, y); // Remove the old rectangle
            insert(newX, newY, newWidth, newHeight); // Insert the updated rectangle
        } else {
            System.out.println("Rectangle not found at (" + x + ", " + y + ")");
        }
    }

    public void find(double x, double y) {
        Rectangle r = root.find(x, y);
        if (r != null) {
            System.out.println(r);
        } else {
            System.out.println("Rectangle not found");
        }
    }

    public void dump() {
        root.print(0);
    }

    // Method to process commands from the .cmmd file
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

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <path-to-cmmd-file>");
            return;
        }

        Main quadtree = new Main(-50, -50, 100, 100);
        quadtree.processCommands(args[0]);
    }
}

    
    /*
    public class Main {
    Node root;

    public Main(double x, double y, double width, double height) {
        root = new LeafNode(x, y, width, height);
    }

    public void insert(double x, double y, double width, double height) {
        Rectangle r = new Rectangle(x, y, width, height);
        root.insert(r);

        // If the root node contains more than 4 rectangles, it must be subdivided into an InternalNode
        if (root instanceof LeafNode && ((LeafNode) root).rectangles.size() > 4) {
            InternalNode newRoot = new InternalNode(root.boundary.x, root.boundary.y, root.boundary.width, root.boundary.height);
            for (Rectangle rect : ((LeafNode) root).rectangles) {
                newRoot.insert(rect);
            }
            root = newRoot;
        }
    }

    public void delete(double x, double y) {
        root.delete(x, y);
    }

    public void update(double x, double y, double newX, double newY, double newWidth, double newHeight) {
        Rectangle r = root.find(x, y);
        if (r != null) {
            delete(x, y);
            insert(newX, newY, newWidth, newHeight);
        }
    }

    public void find(double x, double y) {
        Rectangle r = root.find(x, y);
        if (r != null) {
            System.out.println(r);
        } else {
            System.out.println("Rectangle not found");
        }
    }

    public void dump() {
        root.print(0);
    }

    public static void main(String[] args) {
        Main quadtree = new Main(-50, -50, 100, 100);
        quadtree.dump();
        quadtree.insert(-40, -40, 10, 10);
        quadtree.dump();
        quadtree.insert(40, -40, 10, 10);
        quadtree.insert(-40, 40, 10, 10);
        quadtree.insert(-30, 40, 10, 10);
        quadtree.insert(-35, 40, 10, 10);
        quadtree.insert(-40, 30, 10, 10);
        quadtree.insert(40, 40, 10, 10);
        quadtree.dump();
        quadtree.update(40, 40, 40, 40, 100, 100);
        quadtree.dump();
        quadtree.find(40, 40);
        quadtree.insert(40, 40, 10, 10);
        quadtree.insert(39, 40, 10, 10);
        quadtree.insert(40, 39, 10, 10);
        quadtree.insert(39, 39, 10, 10);
        quadtree.dump();
        
        quadtree.delete(-40, -40);
        quadtree.delete(40, 10);
        quadtree.delete(-40, 40);
        quadtree.delete(-30, 40);
        quadtree.delete(-35, 40);
        quadtree.delete(-40, 30);
        quadtree.delete(40, 40);
        quadtree.delete(40, 40);
        quadtree.delete(39, 40);
        quadtree.delete(40, 39);
        quadtree.delete(39, 39);
        quadtree.dump();
    }
}
    */
