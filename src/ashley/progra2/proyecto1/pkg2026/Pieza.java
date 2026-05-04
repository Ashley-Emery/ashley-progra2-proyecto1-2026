/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.progra2.proyecto1.pkg2026;

/**
 *
 * @author ashley
 */

public abstract class Pieza {

    protected String color;
    protected String nombre;
    protected int fila;
    protected int columna;
    protected boolean activa;

    public Pieza(String color, String nombre, int fila, int columna) {
        this.color = color;
        this.nombre = nombre;
        this.fila = fila;
        this.columna = columna;
        this.activa = true;
    }

    public abstract boolean movimientoValido(Pieza[][] tablero, int filaDestino, int columnaDestino);

    public String getColor() {
        return color;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public void capturar() {
        activa = false;
    }

    public void reactivar() {
        activa = true;
    }

    public boolean esRoja() {
        return color.equalsIgnoreCase("Rojo");
    }

    public boolean esNegra() {
        return color.equalsIgnoreCase("Negro");
    }
}
