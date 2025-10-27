package org.study.example.collection;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapExample {

    // Definimos un enum para el ejemplo de EnumMap.
    // Representa los niveles de severidad de un log.
    public enum LogLevel {
        INFO, WARNING, ERROR, DEBUG
    }

    public static void main(String[] args) {
        /*
         * =================================================================
         *           DEMOSTRACIÓN DE LA INTERFAZ MAP EN JAVA
         * =================================================================
         *
         * Un Map es una colección de pares clave-valor (key-value).
         * Cada clave debe ser ÚNICA. Si se intenta insertar una clave que ya existe,
         * su valor asociado se sobrescribe.
         *
         * Es como un diccionario: usas una clave para buscar su valor correspondiente.
         */

        //==================================================================//
        //    SECCIÓN 1: MÉTODOS COMUNES DE LA INTERFAZ MAP                 //
        //==================================================================//
        System.out.println("--- SECCIÓN 1: MÉTODOS COMUNES DE MAP ---");

        // Usamos HashMap para la demostración general.
        Map<String, String> capitalCities = new HashMap<>();

        // 1. put(clave, valor): Asocia un valor a una clave.
        System.out.println("1. Añadiendo 'España' -> 'Madrid'.");
        capitalCities.put("España", "Madrid");
        capitalCities.put("Francia", "París");
        capitalCities.put("Japón", "Tokio");
        System.out.println("   Mapa actual: " + capitalCities);

        // 2. get(clave): Obtiene el valor asociado a una clave. Devuelve null si la clave no existe.
        String capitalOfSpain = capitalCities.get("España");
        System.out.println("2. La capital de España es: " + capitalOfSpain);

        // 3. remove(clave): Elimina la entrada para una clave. Devuelve el valor que fue eliminado.
        String removedCapital = capitalCities.remove("Francia");
        System.out.println("3. Se eliminó la entrada de Francia. Su capital era: " + removedCapital);

        // 4. containsKey(clave): Verifica si el mapa contiene una clave específica.
        boolean hasJapan = capitalCities.containsKey("Japón");
        System.out.println("4. ¿El mapa contiene la clave 'Japón'? " + hasJapan);

        // 5. containsValue(valor): Verifica si el mapa contiene un valor específico.
        boolean hasMadrid = capitalCities.containsValue("Tokyo");
        System.out.println("5. ¿El mapa contiene el valor 'Tokyo'? " + hasMadrid);

        // 6. size(): Devuelve el número de pares clave-valor.
        System.out.println("6. El tamaño actual del mapa es: " + capitalCities.size());

        // 7. entrySet(): Devuelve un Set que contiene todos los pares clave-valor (Map.Entry).
        // Esta es la forma más eficiente de iterar sobre un mapa si necesitas tanto la clave como el valor.
        Set<Map.Entry<String, String>> entries = capitalCities.entrySet();
        System.out.println("7. El Set de entradas (entrySet) es: " + entries);

        // 8. Iteración sobre el mapa usando el entrySet() obtenido previamente.
        System.out.println("8. Iterando sobre el mapa con entrySet():");
        for (Map.Entry<String, String> entry : entries) {
            System.out.println("   - Clave: " + entry.getKey() + ", Valor: " + entry.getValue());
        }

        // 9. keySet() y values(): Obtener solo las claves o solo los valores.
        System.out.println("9. Conjunto de claves (keySet): " + capitalCities.keySet());
        System.out.println("   Colección de valores (values): " + capitalCities.values());

        // 10. clear(): Elimina todos los elementos.
        capitalCities.clear();
        System.out.println("10. ¿El mapa está vacío después de clear()? " + capitalCities.isEmpty());


        //==================================================================//
        //    SECCIÓN 2: CASOS DE USO - HASHMAP vs. LINKEDHASHMAP vs. TREEMAP //
        //==================================================================//
        System.out.println("\n\n--- SECCIÓN 2: CASOS DE USO DE IMPLEMENTACIONES DE MAP ---");

        //------------------------------------------------------------------//
        // CASO DE USO 1: HashMap - Máxima velocidad, sin orden.            //
        //------------------------------------------------------------------//
        /*
         * HashMap es la implementación más rápida (O(1) en promedio). No garantiza ningún orden.
         *
         * Caso de uso: Cuando necesitas almacenar y recuperar datos rápidamente y el orden no importa.
         * Ejemplo: Una caché para guardar datos de usuarios. Se busca por ID de usuario (clave)
         * para obtener el objeto Usuario (valor) de forma casi instantánea.
         */
        System.out.println("\n-> Caso de Uso: HashMap para una caché de datos de usuario");
        Map<Integer, String> userDataCache = new HashMap<>();
        userDataCache.put(101, "Datos del usuario Alice");
        userDataCache.put(205, "Datos del usuario Bob");

        // Simulamos una petición para obtener los datos de un usuario.
        int userIdToFetch = 101;
        System.out.println("Buscando datos para el ID de usuario " + userIdToFetch + "...");
        String userData = userDataCache.get(userIdToFetch);
        System.out.println("Datos encontrados (acceso rápido): " + userData);

        //------------------------------------------------------------------//
        // CASO DE USO 2: LinkedHashMap - Unicidad y orden de inserción.    //
        //------------------------------------------------------------------//
        /*
         * LinkedHashMap es casi tan rápido como HashMap, pero mantiene el orden en que se
         * insertaron las entradas.
         *
         * Caso de uso: Cuando el orden de inserción es importante.
         * Ejemplo: Propiedades de configuración que deben procesarse en el orden en que
         * fueron leídas de un archivo.
         */
        System.out.println("\n-> Caso de Uso: LinkedHashMap para propiedades de configuración");
        Map<String, String> configuration = new LinkedHashMap<>();
        configuration.put("database.url", "jdbc:mysql://localhost:3306/db");
        configuration.put("database.user", "admin");
        configuration.put("database.password", "secret");
        configuration.put("server.port", "8080");

        System.out.println("Procesando configuración (se mantiene el orden de inserción):");
        for (Map.Entry<String, String> configEntry : configuration.entrySet()) {
            System.out.println("  - " + configEntry.getKey() + " = " + configEntry.getValue());
        }

        //------------------------------------------------------------------//
        // CASO DE USO 3: TreeMap - Unicidad y claves ordenadas.            //
        //------------------------------------------------------------------//
        /*
         * TreeMap almacena las entradas ordenadas por clave (orden natural o un Comparator).
         * Su rendimiento es más lento (O(log n)).
         *
         * Caso de uso: Cuando necesitas que el mapa esté siempre ordenado por sus claves.
         * Ejemplo: Un glosario o diccionario que siempre debe mostrarse en orden alfabético.
         */
        System.out.println("\n-> Caso de Uso: TreeMap para un glosario ordenado");
        Map<String, String> glossary = new TreeMap<>();
        glossary.put("API", "Application Programming Interface");
        glossary.put("JVM", "Java Virtual Machine");
        glossary.put("SDK", "Software Development Kit");
        glossary.put("IDE", "Integrated Development Environment");

        System.out.println("Glosario (automáticamente ordenado por clave):");
        for (Map.Entry<String, String> glossaryEntry : glossary.entrySet()) {
            System.out.println("  - " + glossaryEntry.getKey() + ": " + glossaryEntry.getValue());
        }

        //------------------------------------------------------------------//
        // CASO DE USO 4: EnumMap - Mapa especializado para claves de tipo enum. //
        //------------------------------------------------------------------//
        /*
         * EnumMap es una implementación de alto rendimiento diseñada para usar claves de tipo `enum`.
         * Es extremadamente rápido y eficiente en memoria.
         *
         * Caso de uso: Contar o asociar valores a cada constante de un enum.
         * Ejemplo: Contar cuántos mensajes de log de cada nivel de severidad han ocurrido.
         */
        System.out.println("\n-> Caso de Uso: EnumMap para contar mensajes de log");
        Map<LogLevel, Integer> logCounts = new EnumMap<>(LogLevel.class);

        // Inicializamos los contadores para todos los niveles de log.
        for (LogLevel level : LogLevel.values()) {
            logCounts.put(level, 0);
        }

        // Simulamos la llegada de nuevos mensajes de log.
        logCounts.put(LogLevel.INFO, logCounts.get(LogLevel.INFO) + 1);
        logCounts.put(LogLevel.WARNING, logCounts.get(LogLevel.WARNING) + 1);
        logCounts.put(LogLevel.INFO, logCounts.get(LogLevel.INFO) + 1);
        logCounts.put(LogLevel.ERROR, logCounts.get(LogLevel.ERROR) + 1);

        System.out.println("Contadores de mensajes de log (ordenados por definición del enum):");
        System.out.println(logCounts);
    }
}