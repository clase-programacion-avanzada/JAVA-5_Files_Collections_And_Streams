package org.study;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionsExample {
    public static void main(String[] args) {

        /*This is an example on how Collections works*/

        /* Let's talk about collections:
             You already know about arrays, but there are other types of collections
             Collections are used to store multiple values in a single variable
             Collections are more flexible than arrays because they can grow and shrink dynamically
             There are 4 types of collections in Java: List, Set, Map, Queue
             We will talk about List and Map
             List is an ordered collection of elements*/
        //Reference: https://www.baeldung.com/java-collections

        //===================LIST===================//


        List<String> names = new ArrayList<>(List.of("John", "Mary", "Bob", "Alice"));
        List<String> anotherList = new ArrayList<>();
        //We can iterate over a list using a for loop
        for(String name : names) {
            System.out.println(name);
        }

        //We can get the element at a specific index
        System.out.println(names.get(0));

        //We can add elements to a list
        names.add("John");

        //We can remove elements from a list
        names.remove("John");

        //We can check if a list contains a specific element
        System.out.println(names.contains("John"));

        //We can check the size of a list
        System.out.println(names.size());

        //We can check if a list is empty
        System.out.println(names.isEmpty());

        //We can clear a list
        names.clear();


        //===================SET===================//
        //Set is a collection of unique elements
        //We can use a set to store a list of unique names
        Set<String> uniqueNames = Set.of("John", "Mary", "Bob", "Alice");
        Set<String> anotherSet = new HashSet<>();
        //We can iterate over a set using a for loop
        for( String name : uniqueNames) {
            System.out.println(name);
        }

        //We can use a set to remove duplicates from a list
        List<String> namesWithDuplicates = List.of("John", "Mary", "Bob", "Alice", "John", "Mary", "Bob", "Alice");
        Set<String> uniqueNames3 = new HashSet<>(namesWithDuplicates);
        //We can iterate over a set using a for loop
        for( String name : uniqueNames3) {
            System.out.println(name);
        }

        //We can add elements to a set
        uniqueNames3.add("John");
        //if we try to add an element that already exists in the set, it will not be added
        uniqueNames3.add("John");

        //We can remove elements from a set
        uniqueNames3.remove("John");

        //We can check if a set contains a specific element
        System.out.println(uniqueNames3.contains("John"));

        //We can check the size of a set
        System.out.println(uniqueNames3.size());

        //We can check if a set is empty
        System.out.println(uniqueNames3.isEmpty());

        //We can clear a set
        uniqueNames3.clear();

        //===================MAP===================//

        //Map is a collection of key-value pairs
        //We can use a map to store information about a person
        Map<String, Integer> ages =
            new HashMap<>(Map.of("John", 30, "Mary", 25, "Bob", 40, "Alice", 35));
        Map<String, Integer> anotherMap  = new HashMap<>();
        //We can iterate over a map using a for loop
        //We can use the entrySet() method to get a set of all the entries in the map
        //An entry is a key-value pair
        for(Map.Entry<String, Integer> entry : ages.entrySet()) {
            System.out.println(entry.getKey() + " is " + entry.getValue() + " years old");
        }

        //We can get the value of a key
        //If the key does not exist, it will return null
        System.out.println(ages.get("John"));

        //We can check if a map contains a specific key
        //If the key does not exist, it will return false
        System.out.println(ages.containsKey("John"));


        //We can check if a map contains a specific value
        //If the value does not exist, it will return false
        System.out.println(ages.containsValue(30));

        //We can check the size of a map
        System.out.println(ages.size());

        //We can check if a map is empty
        System.out.println(ages.isEmpty());

        //We can add a new key-value pair to a map
        //If the key already exists, it will replace the old value with the new value
        //If the key does not exist, it will add the new key-value pair
        //It returns the old value if the key already exists, otherwise it returns null
        System.out.println(ages.put("John", 30));

        //We can remove a key-value pair from a map
        //It returns the value of the removed key-value pair
        ages.remove("John");

        //We can get a set of all the keys in a map
        Set<String> keys = ages.keySet();
        for(String key : keys) {
            System.out.println(key);
        }


        //We can get a list of all the values in a map
        //The values in a map can be duplicates, so we can use a list to store them
        //We can use the values() method to get a collection of all the values in the map
        List<Integer> values = new ArrayList<>(ages.values());
        for(Integer value : values) {
            System.out.println(value);
        }

        //We can clear a map
        ages.clear();


        //===================DEQUE===================//

        //Deque is a collection of elements that can be added or removed from both ends
        //We can use a deque to store a list of names
        //We can add elements to the front or back of the deque
        //This means that a deque can be used as a stack or a queue

        //Deque as a queue:
        //We can use a deque as a queue by adding elements to the back of the deque
        //and removing elements from the front of the deque
        Deque<String> queue = new ArrayDeque<>();

        //We can add elements to the back of the deque
        queue.add("John");
        queue.add("Mary");
        queue.add("Bob");
        queue.add("Alice");

        //We can use peek() to get the element at the front of the deque without removing it
        System.out.println(queue.peek());

        //We can use poll() to get the element at the front of the deque and remove it
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        //We can use check the size of a deque
        System.out.println(queue.size());

        //We can check if a deque is empty
        System.out.println(queue.isEmpty());

        //We can check if a deque contains a specific element
        System.out.println(queue.contains("John"));

        //We can clear a deque
        queue.clear();

        //Deque as a stack:
        //We can use a deque as a stack by adding elements to the front of the deque
        //and removing elements from the front of the deque

        Deque<String> stack = new ArrayDeque<>();

        //We can add elements to the front of the deque this is the main difference between a stack and a queue
        stack.push("John");
        stack.push("Mary");
        stack.push("Bob");
        stack.push("Alice");

        //We can use peek() to get the element at the front of the deque without removing it
        System.out.println(stack.peek());

        //We can use poll() to get the element at the front of the deque and remove it
        System.out.println(stack.poll());
        System.out.println(stack.poll());

        //We can use check the size of a deque
        System.out.println(stack.size());

        //We can check if a deque is empty
        System.out.println(stack.isEmpty());

        //We can check if a deque contains a specific element
        System.out.println(stack.contains("John"));

        //We can clear a deque
        stack.clear();







    }
}