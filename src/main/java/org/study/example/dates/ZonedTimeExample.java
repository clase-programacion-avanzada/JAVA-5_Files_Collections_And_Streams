package org.study.example.dates;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase principal para demostrar el uso de ZonedDateTime, la clase de Java 8
 * para manejar fechas y horas con el contexto de una zona horaria específica.
 */
public class ZonedTimeExample {

    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println("  DEMOSTRACIÓN DE FECHAS Y HORAS CON CONTEXTO   ");
        System.out.println("==================================================\n");

        // --- Ejemplo 1: ZonedDateTime ---
        // Caso de uso: Agendar una reunión internacional entre equipos en diferentes países.
        // ZonedDateTime es esencial para asegurar que todos sepan la hora correcta en su ubicación.
        System.out.println("--- Caso de Uso: ZonedDateTime (Reunión Internacional) ---");

        // 1. Definir las zonas horarias de los participantes.
        // Se usan los identificadores estándar de la base de datos IANA.
        ZoneId madridZone = ZoneId.of("Europe/Madrid");
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");

        // 2. Se agenda la reunión para las 9:00 AM en Madrid.
        // Primero creamos un LocalDateTime porque es la hora "local" en Madrid.
        LocalDateTime meetingLocalTime = LocalDateTime.of(2025, 11, 20, 9, 0);

        // 3. Ahora, le damos contexto a esa hora local, creando un ZonedDateTime.
        // Esto representa el instante exacto de la reunión desde la perspectiva de Madrid.
        ZonedDateTime meetingInMadrid = ZonedDateTime.of(meetingLocalTime, madridZone);

        // Usamos un formateador para mostrar la información claramente.
        // 'VV' muestra el identificador de la zona horaria.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM, yyyy 'a las' HH:mm '['VV']'");
        System.out.println("Hora de la reunión agendada: " + meetingInMadrid.format(formatter));

        // 4. ¿A qué hora será la reunión para el equipo en Tokio?
        // Usamos withZoneSameInstant() para ver el MISMO instante de tiempo, pero desde otra zona horaria.
        // La API maneja toda la conversión de horas por nosotros.
        ZonedDateTime meetingInTokyo = meetingInMadrid.withZoneSameInstant(tokyoZone);

        System.out.println("=> Para el equipo de Tokio, la reunión será a las: " + meetingInTokyo.format(formatter));
        System.out.println("=> Nótese que el instante en el tiempo es el mismo, solo cambia la hora local y el offset.\n");


        System.out.println("--------------------------------------------------\n");


        // --- Ejemplo 2: ZonedDateTime y el Horario de Verano (DST) ---
        // Caso de uso: Demostrar cómo la API maneja automáticamente los cambios de horario.
        // Este era un gran problema con las clases antiguas (java.util.Calendar).
        System.out.println("--- Caso de Uso: ZonedDateTime (Manejo de Horario de Verano) ---");

        // En Nueva York, el horario de verano en 2025 comienza el 9 de marzo a las 2:00 AM,
        // momento en que el reloj salta a las 3:00 AM.
        ZoneId newYorkZone = ZoneId.of("America/New_York");
        System.out.println("Analizando el cambio de horario de verano en Nueva York para 2025...");

        // Creamos una fecha justo antes del cambio.
        ZonedDateTime timeBeforeDST = ZonedDateTime.of(
            LocalDateTime.of(2025, 3, 9, 1, 59), newYorkZone
        );
        System.out.println("Hora justo antes del cambio: " + timeBeforeDST.format(formatter));

        // Ahora, añadimos 2 minutos. Esperaríamos que fueran las 2:01 AM, pero...
        ZonedDateTime timeAfterDST = timeBeforeDST.plusMinutes(2);

        // La API sabe que la hora saltó de 1:59 AM a 3:00 AM.
        // Por lo tanto, el resultado es 3:01 AM.
        System.out.println("Hora después de sumar 2 minutos: " + timeAfterDST.format(formatter));
        System.out.println("=> ¡Correcto! La API manejó el salto de hora automáticamente. La hora de las 2:00 a 2:59 nunca existió ese día.");
    }
}