package org.study.example.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.study.example.file.model.Student;

public class FilesClassExampleTryWithResources {

    public static void main(String[] args) {

        String path = "src/main/resources/first_file.txt";
        File file = new File(path);
        List<Student> students = new ArrayList<>();
        try {
            Path filePath = file.toPath();
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                String[] tokens = line.split(";");
                Student student = new Student(
                    Integer.parseInt(tokens[0]),
                    tokens[1],
                    Integer.parseInt(tokens[2]));
                students.add(student);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



        //Let's create a binary file to store the students list using try-with-resources
        System.out.println("Writing " + students.size() + " students to binary file...");

        String binaryFilePath = "students.dat";
        File binaryFile = new File(binaryFilePath);

        // Try-with-resources automatically closes the streams
        try (FileOutputStream fileOut = new FileOutputStream(binaryFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            //We can use the writeObject method to write an object to a file
            for (Student student : students) {
                out.writeObject(student);
            }
            System.out.println("Binary file created successfully!");
            //Streams are automatically closed by try-with-resources
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the file");
            System.out.println(e.getMessage());
        }

        //Read the binary file and print the students using try-with-resources

        // Approach 1: Using EOFException (current approach, improved with try-with-resources)
        try (FileInputStream fileIn = new FileInputStream(binaryFilePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            List<Student> studentsFromFile = new ArrayList<>();

            // Read objects until we reach the end of the file
            try {
                while (true) {
                    Student student = (Student) in.readObject();
                    studentsFromFile.add(student);
                }
            } catch (java.io.EOFException e) {
                // End of file reached, this is expected
            }

            System.out.println("Students read from binary file (Approach 1 - EOFException):");
            for (Student student : studentsFromFile) {
                System.out.println(student);
            }
            // Streams are automatically closed by try-with-resources
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong when reading the file");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }

        // Demonstrate alternative approaches for serialization
        demonstrateAlternativeApproaches(students);
    }

    // Alternative Approach 2: Write count first, then read that exact number of objects
    private static void demonstrateCountBasedSerialization(List<Student> students) {
        String countBasedFilePath = "students_count_based.dat";
        
        System.out.println("\n--- Approach 2: Count-based serialization ---");
        
        // Write: Store count first, then objects
        try (FileOutputStream fileOut = new FileOutputStream(countBasedFilePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            // Write the count first
            out.writeInt(students.size());
            
            // Then write each student
            for (Student student : students) {
                out.writeObject(student);
            }
            System.out.println("Count-based binary file created successfully!");
            
        } catch (IOException e) {
            System.out.println("Error writing count-based file: " + e.getMessage());
            return;
        }
        
        // Read: Read count first, then read that exact number of objects
        try (FileInputStream fileIn = new FileInputStream(countBasedFilePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            // Read the count first
            int studentCount = in.readInt();
            List<Student> studentsFromFile = new ArrayList<>();
            
            // Read exactly that many objects
            for (int i = 0; i < studentCount; i++) {
                Student student = (Student) in.readObject();
                studentsFromFile.add(student);
            }
            
            System.out.println("Students read from count-based file:");
            for (Student student : studentsFromFile) {
                System.out.println(student);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading count-based file: " + e.getMessage());
        }
    }

    // Alternative Approach 3: Serialize the entire collection as one object
    private static void demonstrateCollectionSerialization(List<Student> students) {
        String collectionFilePath = "students_collection.dat";
        
        System.out.println("\n--- Approach 3: Collection serialization ---");
        
        // Write: Serialize the entire ArrayList as one object
        try (FileOutputStream fileOut = new FileOutputStream(collectionFilePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            
            // Write the entire list as a single object
            out.writeObject(students);
            System.out.println("Collection-based binary file created successfully!");
            
        } catch (IOException e) {
            System.out.println("Error writing collection file: " + e.getMessage());
            return;
        }
        
        // Read: Read the entire collection as one object
        try (FileInputStream fileIn = new FileInputStream(collectionFilePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            
            // Read the entire list as a single object
            List<Student> studentsFromFile = (List<Student>) in.readObject();
            
            System.out.println("Students read from collection file:");
            for (Student student : studentsFromFile) {
                System.out.println(student);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading collection file: " + e.getMessage());
        }
    }

    private static void demonstrateAlternativeApproaches(List<Student> students) {
        demonstrateCountBasedSerialization(students);
        demonstrateCollectionSerialization(students);
    }

}
