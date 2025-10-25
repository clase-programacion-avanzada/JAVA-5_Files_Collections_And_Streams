package org.study.example.dates;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Clase principal para demostrar el uso de las clases de "tiempo de máquina"
 * introducidas en Java 8 (java.time). Estas clases son ideales para logs,
 * marcas de tiempo y mediciones de rendimiento.
 */
public class MachineTimeExample {

    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println("  DEMOSTRACIÓN DE CLASES DE TIEMPO DE MÁQUINA   ");
        System.out.println("==================================================\n");

        // --- Ejemplo 1: Instant ---
        // Caso de uso: Registrar un evento en un sistema de auditoría (log).
        // Instant representa un punto único en la línea de tiempo universal (UTC),
        // ideal para saber "cuándo" ocurrió algo de forma inequívoca.
        System.out.println("--- Caso de Uso: Instant (Marca de Tiempo para un Log) ---");

        // Obtenemos el punto exacto en el tiempo en que ocurre el evento.
        Instant eventTimestamp = Instant.now();

        // El formato por defecto de Instant es ISO-8601, que es el estándar para
        // representar fechas y horas. La 'Z' al final significa 'Zulu', que es UTC.
        System.out.println("Evento registrado en el instante (UTC): " + eventTimestamp);

        // Para compatibilidad con sistemas antiguos o librerías (como JavaScript's Date.now()),
        // a menudo necesitamos los milisegundos desde la Época Unix (1 de enero de 1970).
        long epochMillis = eventTimestamp.toEpochMilli();
        System.out.println("El mismo instante en milisegundos desde la Época: " + epochMillis);

        // Los objetos Instant son inmutables. Si queremos calcular un instante futuro,
        // como la expiración de un token, se crea un nuevo objeto.
        Instant tokenExpiration = eventTimestamp.plus(1, ChronoUnit.HOURS);
        System.out.println("Un token generado en este instante expiraría a las: " + tokenExpiration);


        System.out.println("\n--------------------------------------------------\n");


        // --- Ejemplo 2: Duration ---
        // Caso de uso: Medir el tiempo de ejecución de un proceso (profiling).
        // Duration mide una cantidad de tiempo en segundos y nanosegundos,
        // por lo que es perfecto para mediciones de rendimiento.
        System.out.println("--- Caso de Uso: Duration (Medir Rendimiento de un Proceso) ---");

        System.out.println("Iniciando un proceso simulado...");
        Instant startTime = Instant.now();

        // Simulamos una tarea que toma algo de tiempo, como una llamada a una API o una consulta a base de datos.
        try {
            // Dormimos el hilo por 150 milisegundos para simular trabajo.
            Thread.sleep(150);
        } catch (InterruptedException e) {
            // Es buena práctica manejar la excepción de interrupción.
            Thread.currentThread().interrupt();
            System.err.println("El hilo fue interrumpido.");
        }

        Instant endTime = Instant.now();
        System.out.println("Proceso simulado finalizado.");

        // Calculamos la duración entre el inicio y el fin.
        // El método 'between' es la forma estándar de hacerlo.
        Duration processDuration = Duration.between(startTime, endTime);

        // Duration tiene métodos muy útiles para obtener la duración en diferentes unidades.
        System.out.println("=> El proceso tardó " + processDuration.toMillis() + " milisegundos.");
        System.out.println("=> O en nanosegundos: " + processDuration.toNanos() + " nanos.");

        // El formato por defecto de Duration es ISO-8601 para duraciones (ej. "PT0.15S").
        // 'P' es el designador de periodo, 'T' separa la parte de tiempo.
        System.out.println("=> Representación estándar de la duración: " + processDuration);
    }
}