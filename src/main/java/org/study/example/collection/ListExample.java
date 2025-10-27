package org.study.example.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListExample {

    public static void main(String[] args) {
        /*
         * =================================================================
         *           DEMOSTRACIÓN DE LA INTERFAZ LIST EN JAVA
         * =================================================================
         *
         * Una List es una colección ORDENADA que PERMITE elementos duplicados.
         * "Ordenada" significa que mantiene el orden de inserción y podemos
         * acceder a los elementos por su posición (índice).
         *
         * La mejor práctica es declarar la variable con el tipo de la interfaz (List)
         * y la inicialización con la implementación concreta (new ArrayList<>()).
         * Esto hace que nuestro código sea más flexible.
         */

        //==================================================================//
        //    SECCIÓN 1: MÉTODOS COMUNES DE LA INTERFAZ LIST                 //
        //==================================================================//
        System.out.println("--- SECCIÓN 1: MÉTODOS COMUNES DE LIST ---");

        // Usamos ArrayList para esta demostración general.
        List<String> methodList =
            new ArrayList<>();

        // 1. add(elemento): Añade un elemento al final de la lista.
        methodList.add("Manzana");
        methodList.add("Banana");
        methodList.add("Naranja");
        System.out.println("1. Lista después de añadir elementos: " + methodList);

        // 2. add(índice, elemento): Inserta un elemento en una posición específica.
        methodList.add(1, "Fresa");
        System.out.println("2. Lista después de insertar 'Fresa' en el índice 1: " + methodList);

        // 3. get(índice): Obtiene el elemento en una posición específica.
        String fruit = methodList.get(2);
        System.out.println("3. El elemento en el índice 2 es: " + fruit);

        // 4. set(índice, elemento): Reemplaza un elemento en una posición específica.
        methodList.set(3, "Piña");
        System.out.println("4. Lista después de reemplazar el elemento en el índice 3: " + methodList);

        // 5. remove(índice): Elimina un elemento por su índice.
        methodList.remove(0);
        System.out.println("5. Lista después de eliminar el elemento en el índice 0: " + methodList);

        // 6. remove(objeto): Elimina la primera ocurrencia de un objeto.
        methodList.remove("Fresa");
        System.out.println("6. Lista después de eliminar el objeto 'Fresa': " + methodList);

        // 7. size(): Devuelve el número de elementos en la lista.
        System.out.println("7. El tamaño actual de la lista es: " + methodList.size());

        // 8. contains(objeto): Verifica si la lista contiene un elemento.
        boolean hasBanana = methodList.contains("Banana");
        System.out.println("8. ¿La lista contiene 'Banana'? " + hasBanana);

        // 9. indexOf(objeto): Devuelve el índice de la primera ocurrencia de un elemento (-1 si no existe).
        int bananaIndex = methodList.indexOf("Banana");
        System.out.println("9. El índice de 'Banana' es: " + bananaIndex);

        // 10. isEmpty(): Verifica si la lista está vacía.
        System.out.println("10. ¿La lista está vacía? " + methodList.isEmpty());

        // 11. clear(): Elimina todos los elementos de la lista.
        methodList.clear();
        System.out.println("11. Lista después de clear(): " + methodList);
        System.out.println("    ¿La lista está vacía ahora? " + methodList.isEmpty());


        //==================================================================//
        //    SECCIÓN 2: CASOS DE USO - ARRAYLIST vs. LINKEDLIST            //
        //==================================================================//
        System.out.println("\n\n--- SECCIÓN 2: CASOS DE USO DE IMPLEMENTACIONES DE LIST ---");

        //------------------------------------------------------------------//
        // CASO DE USO 1: ArrayList - Ideal para acceso rápido por índice.  //
        //------------------------------------------------------------------//
        /*
         * ArrayList utiliza un array interno. Esto hace que sea extremadamente rápido
         * para leer elementos en cualquier posición (acceso aleatorio), ya que puede
         * calcular la dirección de memoria directamente.
         *
         * Es la elección perfecta cuando la tarea principal es leer datos o iterar
         * sobre la lista, y las inserciones/eliminaciones son poco frecuentes o
         * se realizan principalmente al final.
         *
         * Ejemplo: Un catálogo de productos que se carga una vez y luego se muestra al usuario.
         */
        System.out.println("\n-> Caso de Uso: ArrayList para un catálogo de productos");
        List<String> productCatalog = new ArrayList<>();
        // Simulamos la carga de muchos productos en el catálogo.
        for (int i = 1; i <= 20; i++) {
            productCatalog.add("Producto #" + i);
        }

        // ACCESO RÁPIDO (O(1)): Obtener un producto por su posición es instantáneo.
        System.out.println("Acceso rápido: El producto en la posición 15 es: " + productCatalog.get(15));

        // INSERCIÓN LENTA (O(n)): Añadir un producto al principio es lento,
        // porque necesita desplazar todos los demás elementos una posición a la derecha.
        System.out.println("El tamaño antes de la inserción al principio es: " + productCatalog.size());
        productCatalog.add(0, "¡NUEVO PRODUCTO ESTRELLA!");
        System.out.println("El tamaño después de la inserción es: " + productCatalog.size());
        System.out.println("Catálogo después de una inserción costosa al principio: " + productCatalog.get(0));


        //------------------------------------------------------------------//
        // CASO DE USO 2: LinkedList - Ideal para inserciones y eliminaciones frecuentes. //
        //------------------------------------------------------------------//
        /*
         * LinkedList utiliza una lista doblemente enlazada. Cada elemento (nodo)
         * conoce al elemento anterior y al siguiente.
         *
         * Esto la hace muy eficiente para añadir o quitar elementos en cualquier parte
         * de la lista, ya que solo necesita actualizar las referencias de los nodos vecinos.
         *
         * Es la elección ideal para listas que cambian constantemente de tamaño.
         *
         * Ejemplo: Una lista de reproducción de música donde el usuario puede añadir,
         * eliminar y reordenar canciones constantemente.
         */
        System.out.println("\n-> Caso de Uso: LinkedList para una lista de reproducción de música");
        List<String> musicPlaylist = new LinkedList<>();
        musicPlaylist.add("Canción A");
        musicPlaylist.add("Canción B");
        musicPlaylist.add("Canción D");
        System.out.println("Playlist original: " + musicPlaylist);

        // INSERCIÓN RÁPIDA (O(1) si se tiene la posición): Añadir una canción en medio es muy eficiente.
        musicPlaylist.add(2, "Canción C"); // Insertamos la canción que faltaba.
        System.out.println("Playlist después de insertar 'Canción C': " + musicPlaylist);

        // ELIMINACIÓN RÁPIDA (O(1) si se tiene la posición):
        musicPlaylist.remove(1);
        System.out.println("Playlist después de eliminar la 'Canción B': " + musicPlaylist);

        // ACCESO LENTO (O(n)): Obtener una canción por su índice es lento.
        // Debe recorrer la lista desde el principio (o el final) hasta encontrar la posición.
        System.out.println("Acceso lento: Para obtener '" + musicPlaylist.get(1) + "', tuvo que recorrer los nodos.");
    }
}