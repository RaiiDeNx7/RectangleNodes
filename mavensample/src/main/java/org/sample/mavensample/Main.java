package org.sample.mavensample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * The Main class serves as the entry point for the application, responsible for managing input processing and command handling 
 * for a quadtree data structure. It reads commands from specified input files, processes these commands to manipulate the quadtree, 
 * and outputs results based on user requests. The commands include operations for inserting, finding, deleting, updating, and dumping rectangles in the quadtree.
 */
public class Main {
	
	/**
     * Description: The main method executes the application, handling command line arguments, 
     * reading commands from specified files, and processing each command to perform operations on the quadtree.
     * 
     * @param args (String[]): An array of command-line arguments, typically representing file names containing quadtree command
     * 
     * @exception Catches IOException when reading files, printing an error message if an issue occurs.
     * @exception Catches Exception for invalid commands or errors during quadtree operations, printing the error message to the console.
     */
	public static void main(String[] args) {
     ArrayList<String> commands = new ArrayList<>();
     QuadTree quadtree = new QuadTree();

     try {
         for (String arg : args) {
             BufferedReader br = new BufferedReader(new FileReader(arg));
             String line;
             while ((line = br.readLine()) != null) {
                 commands.add(line.trim());
             }
             br.close();
         }
     } catch (IOException e) {
         System.out.println("Error reading the file: " + e.getMessage());
     }

     for (String cmd : commands) {
         try {
             if (cmd.toLowerCase().startsWith("insert")) {
                 String[] insertInput = parse_InsertInput(cmd);
                 float x = Float.parseFloat(insertInput[1]);
                 float y = Float.parseFloat(insertInput[2]);
                 float length = Float.parseFloat(insertInput[3]);
                 float width = Float.parseFloat(insertInput[4]);
                 quadtree.insert(new Rectangle(x, y, length, width));
             } else if (cmd.toLowerCase().startsWith("find")) {
                 String[] findInput = parse_FindInput(cmd);
                 float x = Float.parseFloat(findInput[1]);
                 float y = Float.parseFloat(findInput[2]);
                 Rectangle result = quadtree.find(x, y);
                 System.out.println(result != null ? result : "Rectangle not found");
             } else if (cmd.toLowerCase().startsWith("delete")) {
                 String[] deleteInput = parse_DeleteInput(cmd);
                 float x = Float.parseFloat(deleteInput[1]);
                 float y = Float.parseFloat(deleteInput[2]);
                 quadtree.delete(x, y);
             } else if (cmd.toLowerCase().startsWith("update")) {
                 String[] updateInput = parse_UpdateInput(cmd);
                 float x = Float.parseFloat(updateInput[1]);
                 float y = Float.parseFloat(updateInput[2]);
                 float length = Float.parseFloat(updateInput[3]);
                 float width = Float.parseFloat(updateInput[4]);
                 quadtree.update(x, y, length, width);
             } else if (cmd.toLowerCase().startsWith("dump")) {
                 quadtree.dump();
             } else {
                 throw new Exception("Invalid command: " + cmd);
             }
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }
     }
 }


	/**
     * Description: Parses an insert command and returns its components as an array of strings.
     * 
     * @param cmd (String): The command string to be parsed, expected to contain parameters for insertion.
     * 
     * @exception Throws an Exception if the command does not have the expected number of parts.
     * 
     * @return An array of strings representing the parsed command components.
     */
 public static String[] parse_InsertInput(String cmd) throws Exception {
     return parseCommand(cmd, 5);
 }

 /**
  * Description: Parses a find command and returns its components as an array of strings.
  * 
  * @param cmd (String): The command string to be parsed, expected to contain parameters for finding a rectangle.
  * 
  * @exception Throws an Exception if the command does not have the expected number of parts.
  * 
  * @return An array of strings representing the parsed command components.
  */
 public static String[] parse_FindInput(String cmd) throws Exception {
     return parseCommand(cmd, 3);
 }

 /**
  * Description: Parses a delete command and returns its components as an array of strings.
  * 
  * @param cmd (String): The command string to be parsed, expected to contain parameters for deletion.
  * 
  * @exception Throws an Exception if the command does not have the expected number of parts.
  * 
  * @return An array of strings representing the parsed command components.
  */
 public static String[] parse_DeleteInput(String cmd) throws Exception {
     return parseCommand(cmd, 3);
 }

 /**
  * Description: Parses an update command and returns its components as an array of strings.
  * 
  * @param cmd (String): The command string to be parsed, expected to contain parameters for updating a rectangle..
  * 
  * @exception Throws an Exception if the command does not have the expected number of parts.
  * 
  * @return An array of strings representing the parsed command components.
  */
 public static String[] parse_UpdateInput(String cmd) throws Exception {
     return parseCommand(cmd, 5);
 }

 /**
  * Description: General command parser that splits the command string into parts and checks the number of components.
  * 
  * @param cmd (String): The command string to be parsed.
  * @param expectedParts (integer): The expected number of parts the command should contain.
  * 
  * @exception Throws an Exception if the command does not have the expected number of parts.
  * 
  * @return An array of strings representing the parsed command components.
  */
 public static String[] parseCommand(String cmd, int expectedParts) throws Exception {
     String[] parts = cmd.replaceAll(";$", "").split("\\s+");
     if (parts.length != expectedParts) throw new Exception("Cannot parse command: " + cmd);
     return parts;
 }
}
