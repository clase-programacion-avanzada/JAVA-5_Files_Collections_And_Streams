package org.study.example.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.study.example.file.model.Product;

/**
 * Clase que demuestra la lectura y escritura de archivos de texto en Java
 * usando el paquete java.nio.file (NIO.2).
 */
public class TextFileExample {

    /**
     * El método main ejecuta todos los ejemplos.
     * Declara 'throws IOException' para simplificar el código de ejemplo,
     * ya que muchas operaciones de archivo pueden lanzar esta excepción.
     */
    public static void main(String[] args) throws IOException {

        // --- Ejemplo 1: Escribir y leer un String completo en un archivo ---
        System.out.println("--- Ejemplo 1: Escribir y Leer un String Completo ---");
        String prefix = "src/main/resources/";
        // Definimos el nombre del archivo y el contenido a escribir.
        String simpleFileName = "ejemplo_simple.txt";
        String contentToWrite = "Hola, mundo desde Java NIO.\nEsta es una segunda línea.";

        // Obtenemos un objeto Path que representa la ruta al archivo.
        Path simpleFilePath = Paths.get(prefix + simpleFileName);

        // Escribimos el String en el archivo. Si el archivo ya existe, se sobreescribe.
        // Files.writeString es un método muy conveniente introducido en Java 11.
        Files.writeString(simpleFilePath, contentToWrite);
        System.out.println("Archivo '" + simpleFileName + "' escrito exitosamente.");

        // Leemos todo el contenido del archivo a un solo String.
        String readContent = Files.readString(simpleFilePath);
        System.out.println("\nContenido leído del archivo:");
        System.out.println(readContent);


        // --- Ejemplo 2: Escribir y leer una lista de líneas ---
        System.out.println("\n--- Ejemplo 2: Escribir y Leer una Lista de Líneas ---");

        String linesFileName = "ejemplo_lineas.txt";
        Path linesFilePath = Paths.get(prefix + linesFileName);
        List<String> linesToWrite = Arrays.asList(
            "Primera línea de la lista.",
            "Segunda línea.",
            "Tercera y última línea."
        );

        // Escribimos una colección de líneas en el archivo. Cada elemento de la lista será una línea.
        Files.write(linesFilePath, linesToWrite);
        System.out.println("Archivo '" + linesFileName + "' escrito con una lista de líneas.");

        // Leemos todas las líneas del archivo en una List<String>.
        List<String> readLines = Files.readAllLines(linesFilePath);
        System.out.println("\nContenido leído del archivo línea por línea:");
        // Iteramos sobre la lista usando un bucle for-each tradicional (sin API de Streams).
        for (String currentLine : readLines) {
            System.out.println(currentLine);
        }


        // --- Ejemplo 3: Parsear un archivo de texto para crear objetos ---
        System.out.println("\n--- Ejemplo 3: Parsear un Archivo para Crear Objetos ---");

        String csvFileName = "productos.csv";
        Path csvFilePath = Paths.get(prefix + csvFileName);

        // Primero, creamos un archivo de ejemplo con formato CSV (valores separados por comas).
        List<String> csvLines = Arrays.asList(
            "Laptop,999.99,50",
            "Teclado,75.50,200",
            "Mouse,25.00,500",
            "Monitor", // Línea mal formada
            "Webcam,45.99,150"
        );
        Files.write(csvFilePath, csvLines);
        System.out.println("Archivo de datos '" + csvFileName + "' creado para el ejemplo de parseo.");

        // Ahora, leemos el archivo y lo convertimos en una lista de objetos 'Producto'.
        List<Product> productList = new ArrayList<>();
        List<String> linesFromCsv = Files.readAllLines(csvFilePath);

        // Procesamos cada línea para crear un objeto.
        for (String currentLine : linesFromCsv) {
            // Dividimos la línea por la coma para obtener los campos.
            String[] parts = currentLine.split(",");

            // Verificamos que la línea tenga el número correcto de partes para evitar errores.
            if (parts.length == 3) {
                try {
                    // Extraemos y convertimos cada parte al tipo de dato correcto.
                    // .trim() elimina espacios en blanco al inicio y al final.
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    int stock = Integer.parseInt(parts[2].trim());

                    // Creamos el objeto Producto y lo añadimos a nuestra lista.
                    productList.add(new Product(name, price, stock));
                } catch (NumberFormatException e) {
                    // Manejamos el caso en que un número no pueda ser parseado.
                    System.err.println("ADVERTENCIA: Se omitió una línea con formato de número incorrecto: " + currentLine);
                }
            } else {
                System.err.println("ADVERTENCIA: Se omitió una línea mal formada (no tiene 3 campos): " + currentLine);
            }
        }

        // Finalmente, imprimimos la lista de objetos que hemos creado.
        System.out.println("\nObjetos 'Producto' creados a partir del archivo:");
        for (Product product : productList) {
            System.out.println(product);
        }
    }
}