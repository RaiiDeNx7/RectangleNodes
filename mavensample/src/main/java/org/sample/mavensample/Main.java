package org.sample.mavensample;


public class Main {
    Node root;

    public Main() {
        this.root = new InternalNode();  // Start with an internal node for the root
    }

    public void processCommand(String command) {
        String[] tokens = command.split(" ");
        switch (tokens[0]) {
            case "dump":
                root.dump("");
                break;
            case "insert":
                double x = Double.parseDouble(tokens[1]);
                double y = Double.parseDouble(tokens[2]);
                double width = Double.parseDouble(tokens[3]);
                double height = Double.parseDouble(tokens[4]);
                Rectangle rect = new Rectangle(x, y, width, height);
                root.insert(rect);
                break;
            case "delete":
                double dx = Double.parseDouble(tokens[1]);
                double dy = Double.parseDouble(tokens[2]);
                Rectangle toRemove = new Rectangle(dx, dy, 10, 10);
                root.delete(toRemove);
                break;
            case "update":
                double ux = Double.parseDouble(tokens[1]);
                double uy = Double.parseDouble(tokens[2]);
                double newWidth = Double.parseDouble(tokens[3]);
                double newHeight = Double.parseDouble(tokens[4]);
                Rectangle toUpdate = root.find(ux, uy);
                if (toUpdate != null) {
                    Rectangle newRect = new Rectangle(ux, uy, newWidth, newHeight);
                    root.update(toUpdate, newRect);
                }
                break;
            case "find":
                double fx = Double.parseDouble(tokens[1]);
                double fy = Double.parseDouble(tokens[2]);
                Rectangle found = root.find(fx, fy);
                if (found != null) {
                    System.out.println(found);
                } else {
                    System.out.println("Rectangle not found at (" + fx + ", " + fy + ")");
                }
                break;
            default:
                System.out.println("Unknown command: " + tokens[0]);
        }
    }

    public static void main(String[] args) {
        Main manager = new Main();
        String[] commands = {
            "dump",
            "insert -40 -40 10 10",
            "dump",
            "insert 40 -40 10 10",
            "insert -40 40 10 10",
            "insert -30 40 10 10",
            "insert -35 40 10 10",
            "insert -40 30 10 10",
            "insert 40 40 10 10",
            "dump",
            "update 40 40 100 100",
            "dump",
            "find 40 40",
            "insert 40 40 10 10",
            "insert 39 40 10 10",
            "insert 40 39 10 10",
            "insert 39 39 10 10",
            "dump",
            "delete -40 -40",
            "delete 40 10",
            "delete -40 40",
            "delete -30 40",
            "delete -35 40",
            "delete -40 30",
            "delete 40 40",
            "delete 40 40",
            "delete 39 40",
            "delete 40 39",
            "delete 39 39",
            "dump"
        };
        for (String command : commands) {
            manager.processCommand(command);
        }
    }
}