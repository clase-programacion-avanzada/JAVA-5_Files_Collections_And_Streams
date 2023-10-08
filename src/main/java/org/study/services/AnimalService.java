package org.study.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.study.exceptions.NotFoundException;
import org.study.model.Animal;
import org.study.model.Vaccine;


public class AnimalService {

    private List<Animal> animalList ;

    public AnimalService() {

        this.animalList = new ArrayList<>();

    }

    public List<Animal> getAnimalList() {
        return new ArrayList<>(animalList);
    }

    /**
     * Adds a vaccine to an animal with the specified name.
     *
     * @param nameOfAnimal The name of the animal to which the vaccine will be added.
     * @param volume       The volume of the vaccine.
     * @param brand        The brand of the vaccine.
     *
     * Note: This method assumes that the name of the animal is unique,
     * and it adds the vaccine to the first animal found with the matching name.
     *
     * The addVaccine method delegates the responsibility of finding the animal by name to the findAnimalByName method.
     * This separation of concerns and delegation of specific tasks is a good practice because it follows
     * the Single Responsibility Principle (SRP) in software design.
     * Each method has a clear and focused responsibility, making the code more maintainable and easier to understand.
     *
     * The chain of responsibilities is important in software development because it promotes modularity,
     * reusability, and easier testing.
     * If you need to change how animals are found or how vaccines are added in the future,
     * you can modify the respective methods without affecting the other parts of the code that use these methods.
     * This separation of concerns reduces code complexity and helps avoid unintended side effects when making changes.
     */
    public void addVaccineToAnimal(String nameOfAnimal, int volume, String brand)
        throws NotFoundException {
        // This is implying that the name of the animal is unique.

        // Step 1: Find the animal by name using the findAnimalByName method.

        Animal animalToAddVaccine = findAnimalByName(nameOfAnimal);

        if (animalToAddVaccine == null) {
            // The animal was not found.
            throw new NotFoundException(String.format("Animal with name %s not found", nameOfAnimal));
        }

        // Step 2: Add the vaccine to the found animal.
        animalToAddVaccine.addVaccine(volume, brand);
    }


    /**
     * Searches for an animal by its name.
     *
     * @param nameOfAnimal The name of the animal to search for.
     * @return The found animal or null if not found.
     *
     * Note: Returning null can lead to NullPointerExceptions if not handled properly.
     * Consider using Optional<Animal> to improve safety and handle absence gracefully.
     *
     * Reference: https://www.baeldung.com/java-optional
     */
    private Animal findAnimalByName(String nameOfAnimal) {
        for (Animal animal : animalList) {
            if (animal.getName().equals(nameOfAnimal)) {
                return animal;
            }
        }

        // This implementation could be improved using Optional.
        return null;
    }


    private Animal findAnimalByNameUsingStreams(String nameOfAnimal) throws NotFoundException {
        return animalList.stream()
            .filter(animal -> animal.getName().equals(nameOfAnimal))
            .findFirst()
            .orElseThrow(
                () -> new NotFoundException(
                    String.format("Animal with name %s not found", nameOfAnimal)));
    }

    private List<String> findAnimalsByName(String nameOfAnimal) {
        List<String> animalsFound = new ArrayList<>();

        for (Animal animal : animalList) {
            if (animal.getName().equalsIgnoreCase(nameOfAnimal)) {
                animalsFound.add(animal.getName());
            }
        }

        return animalsFound;
    }

    private List<String> findAnimalsByNameUsingStreams(String nameOfAnimal) {
        return animalList.stream()
            .filter(animal -> animal.getName().equalsIgnoreCase(nameOfAnimal))
            .map(Animal::getName)
            .toList();
    }

    /*This method returns a map with the animal ids and their owner ids set:
    * i.e {animalId1: {ownerId1, ownerId2}, animalId2: {ownerId1, ownerId3}}
    * */
    private Map<UUID, Set<UUID>> findAnimalOwnersByName(String nameOfAnimal) {
        return animalList.stream()
            .filter(animal -> animal.getName().equalsIgnoreCase(nameOfAnimal))
            .collect(Collectors.toMap(Animal::getId, Animal::getOwnerIds));
    }


    /**
     * Searches for an animal by its unique ID.
     *
     * @param id The ID to search for.
     * @return The found animal or null if not found.
     *
     * Note: Returning null can lead to NullPointerExceptions if not handled properly.
     * Consider using Optional<Animal> to improve safety and handle absence gracefully.
     *
     * Reference: https://www.baeldung.com/java-optional
     */
    public Animal findAnimalById(UUID id) {
        for (Animal animal : animalList) {
            if (animal.getId().equals(id)) {
                return animal;
            }
        }

        // This implementation could be improved using Optional.
        return null;
    }



    /**
     * Loads vaccine data from a CSV file with the specified format.
     *
     * @param path      The file path to the CSV file containing vaccine data.
     * @param delimiter The delimiter used in the CSV file to separate values.
     * @param fileService The FileService object used to read the file.
     * @return True if vaccines were loaded successfully, false otherwise.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public boolean loadVaccinesFromCSVFile(String path,
                                           String delimiter,
                                           FileService fileService)
        throws IOException, NotFoundException {

        Map<UUID, List<Vaccine>> vaccinesByAnimalId =
            fileService.loadVaccinesFromCSVFile(path, delimiter);

        // search animal by id and add vaccines to the animal
        for (Map.Entry<UUID, List<Vaccine>> vaccineEntry : vaccinesByAnimalId.entrySet()) {
            boolean vaccineAdded = addVaccinesToAnimal(vaccineEntry);

            if (!vaccineAdded) {
                return false;
            }
        }

        return true;
    }

    private boolean addVaccinesToAnimal(Map.Entry<UUID, List<Vaccine>> entry) throws NotFoundException {
        //An entry is a key-value pair in a map.
        // In this case, the key is the animal id and the value is the list of vaccines.
        // The entrySet() method returns a set view of the mappings contained in this map.
        // The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
        // Graphically you can see it as follows:
        // Map: {animalId1: [vaccine1, vaccine2], animalId2: [vaccine3, vaccine4]}

        // entry.getKey() is the animal id
        Animal animal = findAnimalById(entry.getKey());

        if (animal == null) {
            throw new NotFoundException(String.format("Error while assigning vaccines to animal: " +
                "Animal with id %s not found", entry.getKey()));
        }

        //entry.getValue() is the list of vaccines
        boolean vaccineAdded = animal.addVaccines(entry.getValue());
        return vaccineAdded;
    }


    /**
     * Loads animals data from a CSV file with the specified format.
     *  THIS METHOD DOES NOT OVERWRITE THE ANIMAL LIST. IT ADDS TO THE EXISTING LIST.
     * @param path      The file path to the CSV file containing animals data.
     * @param delimiter The delimiter used in the CSV file to separate values.
     * @param fileService The FileService object used to read the file.
     * @return True if vaccines were loaded successfully, false otherwise.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public boolean loadAnimalsFromCSVFile(String path,
                                          String delimiter,
                                          FileService fileService)
        throws IOException, NotFoundException {

        List<Animal> animals = fileService.loadAnimalsFromCSVFile(path, delimiter);

        return animalList.addAll(animals);
    }



    /**
     * Loads animals from a binary file by deserializing objects.
     *
     * @param filePath The path to the binary file containing serialized animal objects.
     * @param fileService The FileService object used to read the file.
     * @throws IOException            If an I/O error occurs while reading the file.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    public void loadAnimalsFromBinaryFileUsingTheEntireList(String filePath, FileService fileService) throws IOException, ClassNotFoundException {

        List<Animal> animals =
            fileService.loadAnimalsFromBinaryFileUsingTheEntireList(filePath);
        clearAnimalList();
        animalList.addAll(animals);

    }

    private void clearAnimalList() {
        animalList.clear();
    }


    /**
     * Saves the list of animals to a binary file.
     *
     * @param filePath The path to the binary file where the list of animals will be saved.
     * @param fileService The FileService object used to write the file.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public void saveAnimalsToBinaryFileUsingTheEntireList(String filePath, FileService fileService) throws IOException {

        fileService.saveAnimalsToBinaryFileUsingTheEntireList(filePath, animalList);

    }

    /**
     * Saves the list of animals to a binary file.
     *
     * @param filePath The path to the binary file where the list of animals will be saved.
     * @param fileService The FileService object used to write the file.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public void saveAnimalsToCSVFile(String filePath, FileService fileService) throws IOException {

        List<String> animalsListToCSV = this.animalList.stream()
            .map(animal -> animal.toCSV(";"))
            .toList();

        fileService.writeTextFile(filePath, animalsListToCSV);

    }

    public UUID addOwnerToAnimal(int animalNumber,
                                 String userName,
                                 OwnerService ownerService)
        throws NotFoundException {


        Animal animal = this.animalList.get(animalNumber);
        UUID ownerId = ownerService.getOwnerByUsername(userName).getId();
        animal.addOwnerId(ownerId);

        return animal.getId();

    }



    public void writeFileWithAnimalsAndNextVaccineApplication(String path, FileService fileService) throws IOException {

            fileService.writeTextFile(path, getAnimalsPendingOnNextApplicationReport());

    }

    /**
     * Generates a report of animals with expired vaccines.
     *
     * @return A list of strings describing animals with expired vaccines.
     */
    public List<String> getAnimalsPendingOnNextApplicationReport() {
        List<String> reportOfAnimalsPendingOnNextApplication = new ArrayList<>();

        // Iterate through each animal in the animalList.
        for (Animal animal : animalList) {
            // Iterate through the vaccines of the current animal.
            for (Vaccine vaccine : animal.getVaccines()) {
                // Check if the vaccine is expired using the Vaccine.isVaccineExpired method.
                if (Vaccine.isVaccineExpired(vaccine)) {
                    // Create a report string describing the expired vaccine for the current animal.
                    String animalReportValue = animal.getName()
                        + " has "
                        + vaccine.getBrand()
                        + " of "
                        + vaccine.getVolumeInMl()
                        + " ml "
                        + " expired on "
                        + vaccine.getDateOfNextApplication();

                    // Add the report string to the list.
                    reportOfAnimalsPendingOnNextApplication.add(animalReportValue);
                }
            }
        }

        return reportOfAnimalsPendingOnNextApplication;
    }




}
