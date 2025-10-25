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

public class FilesClassExample {

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



        //Let's create a binary file to store the students list
        System.out.println("Writing " + students.size() + " students to binary file...");

        String binaryFilePath = "students.dat";
        File binaryFile = new File(binaryFilePath);

        try {
            FileOutputStream fileOut = new FileOutputStream(binaryFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            //We can use the writeObject method to write an object to a file
            for (Student student : students) {
                out.writeObject(student);
            }
            //We need to close the streams
            out.close();
            fileOut.close();
            System.out.println("Binary file created successfully!");
            //See that we need catch the FileNotFoundException and IOException exceptions
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the file");
            System.out.println(e.getMessage());
        }

        //Read the binary file and print the students

        try {
            FileInputStream fileIn = new FileInputStream(binaryFilePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
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

            System.out.println("Students read from binary file:");
            for (Student student : studentsFromFile) {
                System.out.println(student);
            }

            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong when reading the file");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }

    }

}
