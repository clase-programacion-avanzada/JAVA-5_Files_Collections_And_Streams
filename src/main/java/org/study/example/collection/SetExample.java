package org.study.example.collection;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SetExample {

    // Definimos un enum para el ejemplo de EnumSet.
    // Representa los posibles roles de un usuario en un sistema.
    public enum UserRole {
        ADMIN, EDITOR, VIEWER
    }

    public static void main(String[] args) {
        /*
         * =================================================================
         *           DEMOSTRACIÓN DE LA INTERFAZ SET EN JAVA
         * =================================================================
         *
         * Un Set es una colección que NO PERMITE elementos duplicados.
         * Su principal propósito es modelar el concepto matemático de un conjunto.
         * La mayoría de las implementaciones (como HashSet) no garantizan ningún orden.
         *
         * La unicidad se determina usando los métodos equals() y hashCode() de los objetos.
         */

        //==================================================================//
        //    SECCIÓN 1: MÉTODOS COMUNES DE LA INTERFAZ SET                 //
        //==================================================================//
        System.out.println("--- SECCIÓN 1: MÉTODOS COMUNES DE SET ---");

        // Usamos HashSet para la demostración general.
        Set<String> methodSet = new HashSet<>();

        // 1. add(elemento): Añade un elemento. Devuelve 'true' si el elemento no existía,
        // y 'false' si ya estaba presente (y por lo tanto, no se añadió).
        System.out.println("1. Añadiendo 'Rojo': " + methodSet.add("Rojo"));
        System.out.println("   Añadiendo 'Verde': " + methodSet.add("Verde"));
        System.out.println("   Añadiendo 'Azul': " + methodSet.add("Azul"));
        System.out.println("   Set actual: " + methodSet);

        // Intentamos añadir un duplicado.
        System.out.println("   Intentando añadir 'Rojo' de nuevo: " + methodSet.add("Rojo"));
        System.out.println("   El Set no cambia: " + methodSet);

        // 2. remove(objeto): Elimina un elemento.
        methodSet.remove("Verde");
        System.out.println("2. Set después de eliminar 'Verde': " + methodSet);

        // 3. contains(objeto): Verifica si el Set contiene un elemento. Es una operación muy rápida.
        boolean hasBlue = methodSet.contains("Azul");
        System.out.println("3. ¿El Set contiene 'Azul'? " + hasBlue);

        // 4. size(): Devuelve el número de elementos.
        System.out.println("4. El tamaño actual del Set es: " + methodSet.size());

        // 5. isEmpty(): Verifica si el Set está vacío.
        System.out.println("5. ¿El Set está vacío? " + methodSet.isEmpty());

        // 6. clear(): Elimina todos los elementos.
        methodSet.clear();
        System.out.println("6. Set después de clear(): " + methodSet);
        System.out.println("   ¿El Set está vacío ahora? " + methodSet.isEmpty());


        //==================================================================//
        //    SECCIÓN 2: CASOS DE USO - HASHSET vs. LINKEDHASHSET vs. TREESET //
        //==================================================================//
        System.out.println("\n\n--- SECCIÓN 2: CASOS DE USO DE IMPLEMENTACIONES DE SET ---");

        //------------------------------------------------------------------//
        // CASO DE USO 1: HashSet - Máxima velocidad, sin orden.            //
        //------------------------------------------------------------------//
        /*
         * HashSet usa una tabla hash. Proporciona el mejor rendimiento (O(1) en promedio)
         * para añadir, eliminar y verificar la existencia de elementos.
         * NO garantiza ningún orden en la iteración.
         *
         * Caso de uso: Verificar rápidamente si un elemento ya ha sido procesado o visto.
         * Ejemplo: Almacenar los IDs de usuarios que han dado 'like' a una foto.
         * No importa el orden, solo si un ID ya existe para no contarlo dos veces.
         */
        System.out.println("\n-> Caso de Uso: HashSet para gestionar 'likes'");
        Set<Integer> userLikes = new HashSet<>();
        userLikes.add(101); // ID de usuario
        userLikes.add(205);
        userLikes.add(34);

        // Un usuario intenta dar like de nuevo. La operación es muy rápida.
        if (userLikes.contains(101)) {
            System.out.println("El usuario 101 ya ha dado like. No se hace nada.");
        } else {
            System.out.println("El usuario 101 da like por primera vez.");
        }
        System.out.println("IDs de usuarios que han dado like: " + userLikes);

        /*
         * Un uso extremadamente común de HashSet es eliminar duplicados de otra colección.
         */
        System.out.println("\n-> Uso extra de HashSet: Eliminar duplicados de una lista");
        List<String> emailsWithDuplicates = List.of("ana@mail.com", "juan@mail.com", "pedro@mail.com", "ana@mail.com");
        System.out.println("Lista original de emails: " + emailsWithDuplicates);
        Set<String> uniqueEmails = new HashSet<>(emailsWithDuplicates);
        System.out.println("Emails únicos (sin orden garantizado): " + uniqueEmails);


        //------------------------------------------------------------------//
        // CASO DE USO 2: LinkedHashSet - Unicidad y orden de inserción.    //
        //------------------------------------------------------------------//
        /*
         * LinkedHashSet es casi tan rápido como HashSet, pero mantiene una lista
         * enlazada interna para recordar el orden en que se insertaron los elementos.
         *
         * Caso de uso: Cuando necesitas elementos únicos Y te importa el orden de llegada.
         * Ejemplo: Una lista de reproducción de canciones donde no quieres duplicados,
         * pero quieres que se reproduzcan en el orden en que las añadiste.
         */
        System.out.println("\n-> Caso de Uso: LinkedHashSet para una playlist");
        Set<String> musicPlaylist = new LinkedHashSet<>();
        musicPlaylist.add("Bohemian Rhapsody - Queen");
        musicPlaylist.add("Stairway to Heaven - Led Zeppelin");
        musicPlaylist.add("Hotel California - Eagles");
        musicPlaylist.add("Bohemian Rhapsody - Queen"); // Este será ignorado

        System.out.println("Playlist (mantiene el orden de inserción y sin duplicados):");
        for (String song : musicPlaylist) {
            System.out.println("  - " + song);
        }

        //------------------------------------------------------------------//
        // CASO DE USO 3: TreeSet - Unicidad y orden natural (ordenado).    //
        //------------------------------------------------------------------//
        /*
         * TreeSet almacena los elementos en una estructura de árbol (Rojo-Negro).
         * Esto mantiene los elementos constantemente ordenados según su "orden natural"
         * (p. ej., alfabético para Strings, numérico para Integers) o un Comparator.
         * Su rendimiento es O(log n), más lento que HashSet.
         *
         * Caso de uso: Cuando necesitas un conjunto de elementos únicos que siempre esté ordenado.
         * Ejemplo: Un directorio de nombres de usuario que siempre debe mostrarse en orden alfabético.
         */
        System.out.println("\n-> Caso de Uso: TreeSet para un directorio de usuarios ordenado");
        Set<String> sortedUsernames = new TreeSet<>();
        sortedUsernames.add("charlie");
        sortedUsernames.add("alice");
        sortedUsernames.add("eve");
        sortedUsernames.add("bob");

        System.out.println("Directorio de usuarios (automáticamente ordenado alfabéticamente): " + sortedUsernames);

        //------------------------------------------------------------------//
        // CASO DE USO 4: EnumSet - Set especializado para tipos enum.      //
        //------------------------------------------------------------------//
        /*
         * EnumSet es una implementación de alto rendimiento diseñada exclusivamente para
         * trabajar con tipos `enum`. Internamente, usa un vector de bits, lo que lo hace
         * extremadamente rápido y eficiente en memoria.
         *
         * Caso de uso: Representar un conjunto de opciones o estados.
         * Ejemplo: Almacenar los roles o permisos de un usuario.
         */
        System.out.println("\n-> Caso de Uso: EnumSet para gestionar permisos de usuario");
        // Creamos un conjunto de permisos para un usuario.
        Set<UserRole> userPermissions = EnumSet.of(UserRole.VIEWER, UserRole.EDITOR);

        System.out.println("Permisos iniciales del usuario: " + userPermissions);

        // Verificamos si el usuario tiene un permiso específico (operación muy rápida).
        if (userPermissions.contains(UserRole.ADMIN)) {
            System.out.println("El usuario es Administrador.");
        } else {
            System.out.println("El usuario NO es Administrador.");
        }

        // Añadimos un nuevo permiso.
        userPermissions.add(UserRole.ADMIN);
        System.out.println("Permisos después de la promoción: " + userPermissions);
    }
}
