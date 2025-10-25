package org.study.example.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.study.exception.NotFoundException;
import org.study.common.model.animal.Animal;

public class StreamsExample {

    public static void main(String[] args) {
        /* Functional programming concepts */

        //Reference: https://www.turing.com/kb/introduction-to-functional-programming

        /*We are going to talk about Java Streams*/

        //Reference: https://www.baeldung.com/java-streams
        //Reference: https://www.baeldung.com/java-8-streams-introduction

        /*
        Java Streams are used to process collections of objects
        A stream is a sequence of objects that supports various methods which can be pipelined to produce the desired result
        Streams are lazy, meaning that they don't execute until we need them
        Streams are functional in nature,
         meaning that they don't change the original data structure,
         they only provide the result as per the pipelined methods
        A stream is not a data structure instead it takes input from the Collections, Arrays or I/O channels
        */

        //We can create a stream from a list, set, or map
        List<String> names = List.of("John", "Mary", "Bob", "Alice");
        Stream<String> namesStream = names.stream();

        Set<String> namesSet = Set.of("John", "Mary", "Bob", "Alice");
        Stream<String> namesSetStream = namesSet.stream();

        Map<String, String> namesMap =
            Map.of("John", "Doe", "Mary", "Smith", "Bob", "Brown", "Alice", "White");

        Stream<String> namesMapStream = namesMap.keySet().stream();
        Stream<String> namesMapValuesStream = namesMap.values().stream();
        Stream<Map.Entry<String, String>> namesMapValuesStreamEntry =
            namesMap.entrySet().stream();

        //We can create a stream from an array
        String[] namesArray = {"John", "Mary", "Bob", "Alice"};
        Stream<String> namesArrayStream = Stream.of(namesArray);

        //We can create a stream from a string
        String namesString = "John,Mary,Bob,Alice";
        Stream<String> namesStringStream = Stream.of(namesString);

        //We can create a stream from a file
        //Reference: https://www.baeldung.com/java-read-lines-large-file
        //Reference: https://www.baeldung.com/java-8-streams
        try {
            Stream<String> namesFileStream = Files.lines(Paths.get("src/main/resources/animals.csv"));

        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        //We can do operations on a stream
        //But first let's talk about lambda expressions
        //Reference: https://www.baeldung.com/java-8-lambda-expressions-tips
        //Reference: https://www.baeldung.com/java-8-functional-interfaces

        //A lambda expression is an anonymous function that means we can create a function without a name
        //a lambda expression has three parts:
        //1. A comma-separated list of formal parameters enclosed in parentheses (this may be empty if the function has no parameters)
        //2. The arrow token, -> (read as "goes to")
        //3. A body, which consists of a single expression or a statement block
        // i.e () -> System.out.println("Hello World");
        // i.e (int x, int y) -> x + y;
        // i.e (String name) -> { System.out.println(name); }

        //If a lambda expression returns a boolean we call it a predicate
        //Predicate is a functional interface that represents a predicate (boolean-valued function) of one argument
        //Reference: https://www.baeldung.com/java-predicate-chain

        Predicate<String> isLongerThan5 = s -> s.length() > 5;

        //If a lambda expression returns a void we call it a consumer
        //Consumer is a functional interface that represents an operation that accepts a single input argument and returns no result
        //Reference: https://www.baeldung.com/java-8-consumer-interface
        //i.e
        Consumer<String> print = s -> System.out.println(s);

        //If a lambda expression returns a value we call it a function
        //Function is a functional interface that represents a function (method) that takes a single parameter and returns a single value
        //Reference: https://www.baeldung.com/java-8-functional-interfaces
        //i.e
        Function<String, Integer> getLength = s -> s.length();

        //Now that we know that we can do operations on a stream, we will treat lambda expressions like any other variable
        //We can pass them as arguments to methods, we can return them from methods, we can store them in variables

        Animal animal1 = new Animal("Miguel", 4);
        animal1.addVaccine(120, "Pfizer");
        animal1.addVaccine(120, "Moderna");

        Animal animal2 = new Animal("Miauricio", 8);
        animal2.addVaccine(20, "Pfizer");
        animal2.addVaccine(15, "Moderna");

        Animal animal3 = new Animal("Antonella", 5);
        animal3.addVaccine(12, "Sinovac");
        animal3.addVaccine(10, "Moderna");
        List<Animal> animalList  = List.of(animal1, animal2, animal3);
        //Let's see some examples of operations we can do on a stream
        //We can use the forEach method to iterate over a stream
        //Foreach is expecting a lambda expression that takes an animal as an argument and returns void
        System.out.println("All animals: ");
        animalList.stream()
            .forEach(animal -> System.out.println(animal));

        //we could also write it like this
        animalList.stream()
            //We can use method references to pass a method as an argument to another method
            //Reference: https://www.baeldung.com/java-method-references
            .forEach(System.out::println);

        //We can use the filter method to filter a stream
        //Filter is expecting a lambda expression that takes an object as an argument and returns a boolean
        //i.e if we have a list of animals (🐶, 🐱, 🐶) we are expecting to filter it using function
        // (🐶) -> is a dog?
        // (🐶, 🐱, 🐶).stream().filter((🐶) -> is a dog?) result will be (🐶, 🐶)
        //We can chain stream operations
        System.out.println("Animals older than 5: ");
        animalList.stream()
            .filter(animal -> animal.getAge() > 5)
            .forEach(System.out::println);

        //We could even store the filtering lambda expression in a variable
        Predicate<Animal> isOlderThan5 = animal -> animal.getAge() > 5;
        Predicate<Animal> isYoungerThan5 = animal -> animal.getAge() < 5;

        System.out.println("Animals older than 5: ");
        animalList.stream()
            .filter(isOlderThan5)
            .forEach(System.out::println);

        System.out.println("Animals younger than 5: ");
        animalList.stream()
            .filter(isYoungerThan5)
            .forEach(System.out::println);

        //We also would like to know if all elements of a stream match a condition
        //We can use the allMatch method to check if all elements of a stream match a condition
        //It expects a lambda expression that takes an object as an argument and returns a boolean
        // i.e if we have a list of animals (🐶, 🐱, 🐶) we are expecting to check if all animals are dogs using function
        // (🐶) -> is a dog?
        // (🐶, 🐱, 🐶).stream().allMatch((🐶) -> is a dog?) result will be false
        //We can use the allMatch method to check if all animals are older than 5
        boolean allAnimalsOlderThan5 = animalList.stream()
            .allMatch(isOlderThan5);

        System.out.println("All animals older than 5: " + allAnimalsOlderThan5);

        //We can use the noneMatch method to check if none of the elements of a stream match a condition
        //It expects a lambda expression that takes an object as an argument and returns a boolean
        // i.e if we have a list of animals (🐶, 🐱, 🐶) we are expecting to check if none of the animals are dogs using function
        // (🐶) -> is a dog?
        // (🐶, 🐱, 🐶).stream().noneMatch((🐶) -> is a dog?) result will be false
        //We can use the noneMatch method to check if none of the animals are older than 5

        boolean noneAnimalsOlderThan5 = animalList.stream()
            .noneMatch(isOlderThan5);

        System.out.println("None animals older than 5: " + noneAnimalsOlderThan5);

        //We can use the anyMatch method to check if any of the elements of a stream match a condition
        //It expects a lambda expression that takes an object as an argument and returns a boolean
        //i.e if we have a list of animals (🐶, 🐱, 🐶) we are expecting to check if any of the animals are dogs using function
        // (🐶) -> is a dog?
        // (🐶, 🐱, 🐶).stream().anyMatch((🐶) -> is a dog?) result will be true
        //We can use the anyMatch method to check if any of the animals are older than 5

        boolean anyAnimalsOlderThan5 = animalList.stream()
            .anyMatch(isOlderThan5);

        System.out.println("Any animals older than 5: " + anyAnimalsOlderThan5);

        //Let's use filter and anyMatch to check if there are animals with Vaccine brand Pfizer and retrieve them

        animalList.stream()
            .filter(animal -> animal.getVaccines().stream()
                .anyMatch(
                    vaccine -> vaccine.getBrand().equalsIgnoreCase("Pfizer")
                )
            )
            .forEach(System.out::println);


        //We can also sort a stream
        //We can use the sorted method to sort a stream
        //It expects a comparator
        //A comparator is a functional interface that compares two objects
        //It means that it is expecting to be a lambda expression that takes two objects as arguments and returns an int
        //i.e if we have a list of animals (🐶, 🐱, 🐭) we are expecting to sort it using function
        // (🐶, 🐱) -> 🐶.age - 🐱.age
        //if 🐱.age = 5 , 🐶.age = 10 and 🐭.age = 2
        // (🐶, 🐱, 🐶).stream().sorted((🐶, 🐱) -> 🐶.age - 🐱.age) result will be (🐭,🐱, 🐶)
        //Reference: https://www.baeldung.com/java-8-sort-lambda
        //Reference: https://www.baeldung.com/java-comparator-comparable
        //We can send a lambda expression directly
        System.out.println("Animals sorted by name: ");
        animalList.stream()
            .sorted((animalA, animalB) -> animalA.getName().compareTo(animalB.getName()))
            .forEach(System.out::println);

        //Or we can store the lambda expression in a variable

        Comparator<Animal> compareByAge = (animalA, animalB) -> animalA.getAge() - animalB.getAge();

        System.out.println("Animals sorted by age: ");
        animalList.stream()
            .sorted(compareByAge)
            .forEach(System.out::println);

        //But the preferred option is to use method references
        Comparator<Animal> compareByName = Comparator.comparing(Animal::getName);

        System.out.println("Animals sorted by name using Comparator.comparing: ");
        animalList.stream()
            .sorted(compareByName)
            .forEach(System.out::println);

        //We could even chain the stream operations filter and sorted
        System.out.println("Animals older than 5 sorted by name: ");
        animalList.stream()
            .filter(isOlderThan5)
            .sorted(compareByName)
            .forEach(System.out::println);

        //Sometimes we want to transform a stream into another stream
        //We can use the map method to transform a stream
        //The map operation expects a lambda expression that takes an animal as an argument and returns another object
        //i.e if we have a list of squares (🟥, 🟧, 🟨) we are expecting to transform it using function
        // (🟥) -> 🔴
        // (🟥, 🟧, 🟨).stream().map((🟥) -> 🔴) result will be (🔴, 🟠, 🟡)

        System.out.println("Animals names: ");
        animalList.stream()
            .map(animal -> animal.getName())
            .forEach(System.out::println);



        //Sometimes we need to transform the stream back to a list, set, or map, to do that we have following options.
        //We can use the collect method to transform a stream into a list, set, or map
        List <String> animalNamesList = animalList.stream()
            .map(animal -> animal.getName())
            .toList();

        Set <String> animalNamesSet = animalList.stream()
            .map(animal -> animal.getName())
            .collect(Collectors.toSet());

        Map <UUID, Animal> animalsById = animalList.stream()
            .collect(Collectors.toMap(
                animal -> animal.getId(),
                animal -> animal));

        //These are the basic operations, but what happens if we want just one element of the stream (like the max or the min)
        //To answer this question we need to talk about Optional
        //Optional is a container object that may or may not contain a non-null value
        // i.e if we have an optional of an animal (🐶) we are expecting to check if it has a value using function
        // (🐶) -> 🐶.isPresent()
        // (🐶).isPresent() result will be true
        // we could have an optional of an animal (🐶) that doesn't have a value in our representation it would be ()
        // ().isPresent() result will be false
        //Reference: https://www.baeldung.com/java-optional
        //Reference: https://www.baeldung.com/java-optional-return

        Optional<Animal> myAnimalOptional = Optional.of(animal1);

        System.out.println("Optional of animal1: " + myAnimalOptional);

        if(myAnimalOptional.isPresent()) {
            System.out.println(myAnimalOptional.get());
        } else {
            System.out.println("Animal is not present");
        }

        //we can create optionals coming from a method that could return null
        Optional<Animal> myAnimalOptional2 = Optional.ofNullable(getNull());

        System.out.println("Optional of null: " + myAnimalOptional2);
        if(myAnimalOptional2.isPresent()) {
            System.out.println(myAnimalOptional2.get());
        } else {
            System.out.println("Animal is not present");
        }

        //We can create an empty optional
        Optional<Animal> myAnimalOptional3 = Optional.empty();

        System.out.println("Optional empty: " + myAnimalOptional3);

        if(myAnimalOptional3.isPresent()) {
            System.out.println(myAnimalOptional3.get());
        } else {
            System.out.println("Animal is not present");
        }

        //findFirst returns an optional because the stream could be empty

        Optional<Animal> firstAnimal = animalList.stream()
            .findFirst();

        //We can check if the optional has a value using the isPresent method
        if (firstAnimal.isPresent()) {
            //We use get method to get the value of the optional
            System.out.println(firstAnimal.get());
        }

        //We can use the ifPresent method to check if the optional has a value
        //ifPresent expects a lambda expression that takes an object as an argument and returns void
        firstAnimal.ifPresent(animal -> System.out.println(animal));


        //We can use the orElse method to return the value if the optional has a value or a default value if it doesn't
        Animal firstAnimalOrDog = animalList.stream()
            .filter(isOlderThan5)
            .findFirst()
            .orElse(new Animal("Dog", 4));

        //We can use the orElseThrow method to return the value if the optional has a value or throw an exception if it doesn't
        try {
            Animal firstAnimalOrThrow = animalList.stream()
                .filter(isOlderThan5)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No animals older than 5"));

        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

        //What if I want all distinct animal names older than 5
        //we could use the toSet method to get the distinct elements of a stream
        System.out.println("Distinct animal names older than 5 sorted by name: ");

        animalList.stream()
            .filter(isOlderThan5)
            .map(animal -> animal.getName())
            .collect(Collectors.toSet())
            .forEach(System.out::println);

        //In case that we want all distinct elements but sorted we could use the distinct method
        //See that we are not sending any argument to the distinct method
        //it is because we are using the equals method of the object to compare the elements
        //in this case we are using the equals method of the String class
        //Also, sorted method expects a comparator,
        // but we can use the natural order of the object, in this case the natural order of the String class
        System.out.println("Distinct animal names older than 5 sorted by name: ");
        animalList.stream()
            .filter(isOlderThan5)
            .map(animal -> animal.getName())
            .distinct()
            .sorted()
            .forEach(System.out::println);



        //Let's see how to use optionals to get the maximum age of animalList through a method
        Optional<Animal> optionalAnimal = getMaxAge(animalList);

        optionalAnimal.ifPresent(animal -> System.out.println("Max age: " + animal.getAge()));

        //now let's see what happens with an empty list
        List<Animal> emptyAnimalList = List.of();
        Optional<Animal> optionalEmptyAnimal = getMaxAge(emptyAnimalList);

        optionalEmptyAnimal.ifPresent(animal -> System.out.println("Max age: " + animal.getAge()));

        //Optionals are intended to be used as return types, not parameters

        //Now let's chain the stream operations to get the first name of animals older than 5 ordered by name
        Optional<String> firstName = animalList.stream()
            .filter(isOlderThan5)
            .sorted(compareByName)
            .map(animal -> animal.getName())
            .findFirst();

        firstName.ifPresent(name -> System.out.println("First name: " + name));

        //Now let's chain the stream operations to get the first name of animals older than 15 ordered by name
        //in this case we will get empty because there are no animals older than 15
        Optional<String> firstName2 = animalList.stream()
            .filter(animal -> animal.getAge() > 15)
            .sorted(compareByName)
            .map(Animal::getName)
            .findFirst();

        if(firstName2.isPresent()) {
            System.out.println("First name: " + firstName2.get());
        } else {
            System.out.println("No animals older than 15");
        }

    }

    private static Animal getNull() {
        return null;
    }

    private static Optional<Animal> getMaxAge(List<Animal> animals) {
        return animals.stream()
            .max(Comparator.comparing(Animal::getAge));
    }

}    