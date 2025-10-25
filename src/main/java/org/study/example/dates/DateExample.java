package org.study.example.dates;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase principal para demostrar el uso de las clases de fecha y hora locales
 * introducidas en Java 8 (java.time).
 */
public class DateExample {

    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println("  DEMOSTRACIÓN DE CLASES LOCALES DE FECHA Y HORA  ");
        System.out.println("==================================================\n");

        // --- Ejemplo 1: LocalDate ---
        // Caso de uso: Gestionar la fecha de devolución de un libro en una biblioteca.
        // LocalDate es perfecto porque la fecha de devolución no depende de la hora ni de la zona horaria.
        System.out.println("--- Caso de Uso: LocalDate (Préstamo en Biblioteca) ---");

        LocalDate checkoutDate = LocalDate.now();
        System.out.println("Fecha de préstamo del libro: " + checkoutDate);

        // El libro debe ser devuelto en 2 semanas. La API es inmutable,
        // por lo que 'plusWeeks' devuelve una nueva instancia de LocalDate.
        LocalDate dueDate = checkoutDate.plusWeeks(2);
        System.out.println("Fecha de devolución programada: " + dueDate);

        // Simulemos que revisamos el estado del libro en una fecha posterior.
        LocalDate checkDate = dueDate.plusDays(1); // Un día después de la fecha de devolución.
        System.out.println("Revisando estado en la fecha: " + checkDate);

        if (checkDate.isAfter(dueDate)) {
            System.out.println("=> ¡El libro está vencido!");
        } else {
            System.out.println("=> El libro está dentro del plazo.");
        }

        System.out.println("\n--------------------------------------------------\n");


        // --- Ejemplo 2: LocalTime ---
        // Caso de uso: Comprobar si una tienda está abierta.
        // El horario de una tienda es un ejemplo ideal para LocalTime, ya que es una hora del día fija.
        System.out.println("--- Caso de Uso: LocalTime (Horario de Tienda) ---");

        LocalTime openingTime = LocalTime.of(9, 0);
        LocalTime closingTime = LocalTime.of(20, 30);
        System.out.println("Horario de la tienda: de " + openingTime + " a " + closingTime);

        // Obtenemos la hora actual para la comprobación.
        // Para que el ejemplo funcione a cualquier hora, simularemos una hora.
        LocalTime currentTime = LocalTime.of(15, 45);
        System.out.println("Hora actual para la comprobación: " + currentTime);

        // Verificamos si la hora actual está dentro del horario de apertura.
        // La condición es: currentTime NO es anterior a openingTime Y ES anterior a closingTime.
        if (!currentTime.isBefore(openingTime) && currentTime.isBefore(closingTime)) {
            System.out.println("=> Resultado: La tienda está abierta ahora mismo.");
        } else {
            System.out.println("=> Resultado: La tienda está cerrada en este momento.");
        }

        System.out.println("\n--------------------------------------------------\n");


        // --- Ejemplo 3: LocalDateTime ---
        // Caso de uso: Agendar y reagendar una cita médica.
        // LocalDateTime es ideal aquí porque una cita tiene una fecha Y una hora específicas.
        System.out.println("--- Caso de Uso: LocalDateTime (Cita Médica) ---");

        // Definimos la fecha y hora de la cita original.
        LocalDateTime originalAppointment = LocalDateTime.of(2025, 12, 10, 11, 30);

        // Usamos un formateador para mostrar la fecha de una manera más amigable para el usuario.
        DateTimeFormatter friendlyFormatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'a las' HH:mm");
        String formattedAppointment = originalAppointment.format(friendlyFormatter);
        System.out.println("Cita original agendada para: " + formattedAppointment);

        // El paciente llama para posponer la cita 24 horas.
        // Como LocalDateTime es inmutable, 'plusHours' devuelve un nuevo objeto.
        LocalDateTime rescheduledAppointment = originalAppointment.plusHours(24);
        String formattedRescheduled = rescheduledAppointment.format(friendlyFormatter);
        System.out.println("La cita ha sido reagendada para: " + formattedRescheduled);

        // Verificamos si la cita reagendada es en el futuro respecto a la fecha original.
        if (rescheduledAppointment.isAfter(originalAppointment)) {
            System.out.println("=> Confirmación: La nueva fecha de la cita es posterior a la original.");
        }
    }
}