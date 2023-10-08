package org.study;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.study.exceptions.NotFoundException;
import org.study.model.Animal;
import org.study.services.AnimalService;
import org.study.services.FileService;

public class FilesExample {
    public static void main(String[] args) {

        //Now let's talk about files, run this code as debug to see the files created
        //We can use the File class to create, delete, and read files
        Path path = Paths.get("test.txt");
        try {
            //creates file will throw an exception if the file already exists
            //createFile returns a Path object representing the file
            Path file = Files.createFile(path);

            //We can check if a file exists
            System.out.println(Files.exists(path));


            //We can write to a file
            //writeString method returns a Path object representing the file
            Path file2 = Files.writeString(path, "Hello World");

            //We can also use write method to write to a file, we can write either a string or a list of strings
            Files.write(path, "Hello World".getBytes());

            Files.write(path, List.of("1;ana", "2;maria"));

            //We can read a file
            //readString method returns a String
            String content = Files.readString(path);
            System.out.println(content);

            //readAllLines method returns a List<String>
            //We can use this method to read a file line by line
            List<String> lines = Files.readAllLines(path);
            for(String line : lines) {
                System.out.println(line);
            }

            //We can delete a file
            //delete method returns a boolean
            //We can also use deleteIfExists to delete a file if it exists
            //It will throw an DirectoryNotEmptyException if the file is a directory and not empty
            Files.deleteIfExists(path);

        } catch (IOException e) {
            System.out.println("An error occurred.");
        }


        //If we want to write an object that implements the Serializable interface to a file we can use the ObjectOutputStream class
        //If you go to the Animal class you will see that it implements the Serializable interface
        Animal animal = new Animal("Dog", 4);
        animal.addVaccine(120, "Pfizer");
        animal.addVaccine(120, "Moderna");

        //We can write an object to a file

        try {
            FileOutputStream fileOut = new FileOutputStream("animal.bin");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            //We can use the writeObject method to write an object to a file
            out.writeObject(animal);
            //We need to close the streams
            out.close();
            fileOut.close();
            //See that we need catch the FileNotFoundException and IOException exceptions
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the file");
        }

        //We could use the try-with-resources statement to automatically close the streams
        try (FileOutputStream fileOut = new FileOutputStream("animal.bin");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(animal);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the file");
        }

        //Now suppose that we have more than one animal
        Animal animal2 = new Animal("Cat", 4);
        animal2.addVaccine(120, "Pfizer");
        animal2.addVaccine(120, "Moderna");

        Animal animal3 = new Animal("Horse", 4);
        animal3.addVaccine(120, "Pfizer");
        animal3.addVaccine(120, "Moderna");

        //We can't use directly List.of because it returns an immutable list and immutable lists can't be written to a file
        //We need to use ArrayList instead
        List<Animal> animals = new ArrayList<>(
            List.of(animal, animal2, animal3)
        );

        //We can write a list of objects to a file
        try(FileOutputStream fileOut = new FileOutputStream("animals.bin");
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(animals);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the file");
        }

        //Now let's read the objects from the file
        //In a similar way as we have ObjectOutputStream we have ObjectInputStream
        //We can use the ObjectInputStream class to read an object from a file
        //We need to use the FileInputStream class to read the file
        try(FileInputStream fileIn = new FileInputStream("animals.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn)) {

            //We can use the readObject method to read an object from a file
            //We need to cast the object to the correct type
            //We will always use ArrayList to store the objects because it implements the Serializable interface
            List<Animal> animalsFromFile = (ArrayList<Animal>) in.readObject();

            //We can print the animals
            for(Animal animalFromFile : animalsFromFile) {
                System.out.println(animalFromFile);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong when reading from the file");

        } /*ClassNotFoundException is thrown when the class of the object to be deserialized cannot be found*/
          catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }


        //We will always follow the chain of responsibilities so there is a service that will handle the file operations
        //In our case we are hidding the file operations in the AnimalService class
        AnimalService animalService = new AnimalService();
        FileService fileService = new FileService();

        //Let's see how we can read a csv file using the AnimalService class
        //We can use the readAnimalsFromCsv method to read a csv file
        //We are injecting the FileService class to the AnimalService class
        //Reference: https://www.youtube.com/watch?v=J1f5b4vcxCQ
        //Reference: https://www.freecodecamp.org/news/a-quick-intro-to-dependency-injection-what-it-is-and-when-to-use-it-7578c84fa88f/

        try {

            boolean animalsLoaded =
                animalService.loadAnimalsFromCSVFile("src/main/resources/animals.csv", ";" ,fileService);

            if (animalsLoaded) {
                System.out.println("Animals loaded successfully");
            } else {
                System.out.println("Animals not loaded");
            }

            //We can print the animals
            for(Animal animalFromFile : animalService.getAnimalList()) {
                System.out.println(animalFromFile);
            }


        } catch (IOException e) {
            System.out.println("Something went wrong when reading from the file");
        }/*NotFoundException is an exception created to show that an animal was not found */
        catch (NotFoundException e) {
            System.out.println("Animal not found");
        }

        //We can write current state of list in a bin file
        try {
            animalService.saveAnimalsToBinaryFileUsingTheEntireList("src/main/resources/animals.bin", fileService);

        } catch (IOException e) {
            System.out.println("Something went wrong when writing to the file");
        }

        //And then we can read the animals from the bin file
        try {
            //Note that we are using the same fileService
            //Note that this method will overwrite the current list of animals
            animalService.loadAnimalsFromBinaryFileUsingTheEntireList("src/main/resources/animals.bin", fileService);

            //We can print the animals
            for(Animal animalFromFile : animalService.getAnimalList()) {
                System.out.println(animalFromFile);
            }

        } catch (IOException e) {
            System.out.println("Something went wrong when reading from the file");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }


    }
}
