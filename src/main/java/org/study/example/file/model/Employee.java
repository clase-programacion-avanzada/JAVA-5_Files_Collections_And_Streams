package org.study.example.file.model;

import java.io.Serializable;

/**
 * Clase que representa a un empleado.
 * Ha sido renombrada a 'Employee' según la solicitud.
 * Debe implementar 'Serializable' para que sus instancias puedan ser guardadas.
 */
public class Employee implements Serializable {

    // Buena práctica: definir un ID de versión para la clase serializable.
    private static final long serialVersionUID = 2L; // Incrementado para reflejar el cambio de nombre

    private String fullName;
    private int employeeId;
    private double salary;

    // --- USO DE TRANSIENT ---
    // La palabra clave 'transient' marca un campo para que NO sea serializado.
    // Esto es crucial para datos sensibles (contraseñas, tokens) o datos
    // que no necesitan ser guardados (pueden ser calculados o son temporales).
    private transient String accessCode;

    public Employee(String fullName, int employeeId, double salary, String accessCode) {
        this.fullName = fullName;
        this.employeeId = employeeId;
        this.salary = salary;
        this.accessCode = accessCode;
    }

    @Override
    public String toString() {
        // La lógica aquí muestra claramente si el campo 'accessCode' se ha perdido.
        return "Employee [ID=" + employeeId + ", Nombre=" + fullName + ", Salario=" + salary + ", Código de Acceso=" + (accessCode == null ? "NO GUARDADO (transient)" : accessCode) + "]";
    }
}