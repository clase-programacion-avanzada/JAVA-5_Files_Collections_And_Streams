package org.study.example.dates;


import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

/**
 * Clase principal para demostrar el uso de Period y Duration, las clases de Java 8
 * para medir cantidades de tiempo.
 */
public class MeasureTimExample {

    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println("  DEMOSTRACIÓN DE CLASES PARA MEDIR TIEMPO      ");
        System.out.println("==================================================\n");

        // --- Ejemplo 1: Period ---
        // Caso de uso: Calcular la edad de una persona o el tiempo de servicio en una empresa.
        // Period es ideal para cantidades de tiempo en términos humanos: años, meses y días.
        // Se usa con clases basadas en fechas, como LocalDate.
        System.out.println("--- Caso de Uso: Period (Calcular la Edad de una Persona) ---");

        LocalDate birthDate = LocalDate.of(1992, 8, 24);
        LocalDate currentDate = LocalDate.now();

        System.out.println("Fecha de nacimiento: " + birthDate);
        System.out.println("Fecha actual: " + currentDate);

        // Calculamos el periodo entre la fecha de nacimiento y la fecha actual.
        // La API maneja correctamente los años bisiestos y la duración de cada mes.
        Period age = Period.between(birthDate, currentDate);

        // Extraemos los componentes del periodo para mostrarlos.
        int years = age.getYears();
        int months = age.getMonths();
        int days = age.getDays();

        // Usamos printf para un formato de salida limpio.
        System.out.printf("=> Edad exacta: %d años, %d meses y %d días.%n", years, months, days);
        System.out.println("=> 'Period' es perfecto para conceptos de calendario que las personas entienden.");


        System.out.println("\n--------------------------------------------------\n");


        // --- Ejemplo 2: Duration ---
        // Caso de uso: Medir cuánto tiempo tarda en ejecutarse una tarea o un algoritmo.
        // Duration mide el tiempo en unidades precisas (segundos y nanosegundos),
        // por lo que es la elección correcta para mediciones de rendimiento. Se usa con Instants.
        System.out.println("--- Caso de Uso: Duration (Medir la Duración de una Tarea) ---");

        System.out.println("Iniciando una tarea simulada (ej. descarga de archivo)...");

        // Obtenemos el instante de inicio.
        Instant startTime = Instant.now();

        // Simulamos una tarea que dura 1.5 segundos.
        try {
            Thread.sleep(1500); // El método sleep espera una cantidad de milisegundos.
        } catch (InterruptedException e) {
            // Manejo de la excepción si el hilo es interrumpido.
            Thread.currentThread().interrupt();
            System.err.println("La simulación de la tarea fue interrumpida.");
        }

        // Obtenemos el instante de finalización.
        Instant endTime = Instant.now();
        System.out.println("Tarea finalizada.");

        // Calculamos la duración entre el inicio y el fin.
        Duration taskDuration = Duration.between(startTime, endTime);

        // Duration nos ofrece métodos para obtener la duración total en la unidad que necesitemos.
        System.out.println("=> La tarea tardó un total de " + taskDuration.toMillis() + " milisegundos.");

        // También podemos obtener los segundos y la parte restante de nanosegundos.
        long durationInSeconds = taskDuration.getSeconds();
        int nanoPart = taskDuration.getNano(); // La parte de nanosegundos que sobra después de los segundos.
        System.out.printf("=> O de forma más precisa: %d segundos y %d nanosegundos.%n", durationInSeconds, nanoPart);
        System.out.println("=> 'Duration' es para tiempo de máquina, preciso y sin ambigüedades.");
    }
}
