package org.study.example.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.study.example.file.model.Employee;

/**
 * Clase que demuestra la serialización y deserialización de objetos,
 * destacando el uso de la palabra clave 'transient'.
 */
public class BinaryFileExample {




    /**
     * El método main ejecuta el ciclo completo de serialización y deserialización.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        System.out.println("--- Ejemplo de Serialización con 'transient' ---");

        String objectFileName = "employees.ser"; // Archivo binario de salida

        // 1. Creamos una lista de objetos 'Employee' con todos sus datos.
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Carlos Santana", 101, 75000.0, "cs_pass_123"));
        employeeList.add(new Employee("Beatriz Luna", 102, 82000.0, "bl_secret_456"));

        System.out.println("Paso 1: Objetos originales en memoria (con código de acceso):");
        for(Employee currentEmployee : employeeList) {
            System.out.println(currentEmployee);
        }

        // 2. Proceso de Serialización (Escribir objetos en un archivo)
        // El campo 'accessCode' será ignorado durante este proceso.
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFileName))) {
            objectOutputStream.writeObject(employeeList);
        }
        System.out.println("\nPaso 2: Lista de objetos serializada en '" + objectFileName + "'. El campo 'accessCode' fue omitido.");


        // 3. Proceso de Deserialización (Leer objetos desde un archivo)
        System.out.println("\n--- Ejemplo de Deserialización ---");

        List<Employee> deserializedList;

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(objectFileName))) {
            // Leemos el objeto desde el archivo y lo casteamos de vuelta a List<Employee>.
            deserializedList = (List<Employee>) objectInputStream.readObject();
        }
        System.out.println("Paso 3: Lista de objetos deserializada desde '" + objectFileName + "'.");

        // 4. Verificación del efecto de 'transient'
        System.out.println("\nPaso 4: Objetos recuperados del archivo:");
        for (Employee currentEmployee : deserializedList) {
            System.out.println(currentEmployee);
        }

        System.out.println("\n--- CONCLUSIÓN ---");
        System.out.println("Como se puede observar, el campo 'accessCode' es 'null' en los objetos recuperados.");
        System.out.println("Esto demuestra que la palabra clave 'transient' previno exitosamente que ese dato se guardara en el archivo binario.");
    }
}