package org.study.example.file.model;

/**
 * Clase interna  para representar un producto.
 * Usada en el ejemplo de parseo.
 */
public class Product {
    String name;
    double price;
    int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Sobreescribimos el método toString para una impresión clara del objeto.
    @Override
    public String toString() {
        return "Producto [Nombre=" + name + ", Precio=" + price + ", Stock=" + stock + "]";
    }
}