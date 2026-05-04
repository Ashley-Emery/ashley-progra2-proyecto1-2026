/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.progra2.proyecto1.pkg2026;

/**
 *
 * @author ashley
 */

public final class ValidadorXiangqi {

    private ValidadorXiangqi() {
    }

    public static final boolean dentroTablero(int fila, int columna) {
        return fila >= 0 && fila < 10 && columna >= 0 && columna < 9;
    }

    public static final boolean dentroPalacio(String color, int fila, int columna) {
        if (color.equalsIgnoreCase("Rojo")) {
            return fila >= 7 && fila <= 9 && columna >= 3 && columna <= 5;
        }

        return fila >= 0 && fila <= 2 && columna >= 3 && columna <= 5;
    }

    public static final boolean mismaLinea(int fila1, int columna1, int fila2, int columna2) {
        return fila1 == fila2 || columna1 == columna2;
    }

    public static final int contarPiezasEntre(Pieza[][] tablero, int fila1, int columna1, int fila2, int columna2) {
        int contador = 0;

        if (fila1 == fila2) {
            int inicio = Math.min(columna1, columna2) + 1;
            int fin = Math.max(columna1, columna2);

            for (int c = inicio; c < fin; c++) {
                if (tablero[fila1][c] != null) {
                    contador++;
                }
            }
        } else if (columna1 == columna2) {
            int inicio = Math.min(fila1, fila2) + 1;
            int fin = Math.max(fila1, fila2);

            for (int f = inicio; f < fin; f++) {
                if (tablero[f][columna1] != null) {
                    contador++;
                }
            }
        }

        return contador;
    }

    public static final boolean casillaVacia(Pieza[][] tablero, int fila, int columna) {
        return tablero[fila][columna] == null;
    }

    public static final boolean piezaEnemiga(Pieza pieza, Pieza destino) {
        return pieza != null && destino != null && !pieza.getColor().equalsIgnoreCase(destino.getColor());
    }

    public static final boolean generalesEnfrentados(Pieza[][] tablero) {
        Pieza generalRojo = null;
        Pieza generalNegro = null;

        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 9; c++) {
                Pieza pieza = tablero[f][c];

                if (pieza != null && pieza.getNombre().equalsIgnoreCase("General")) {
                    if (pieza.esRoja()) {
                        generalRojo = pieza;
                    } else {
                        generalNegro = pieza;
                    }
                }
            }
        }

        if (generalRojo == null || generalNegro == null) {
            return false;
        }

        if (generalRojo.getColumna() != generalNegro.getColumna()) {
            return false;
        }

        return contarPiezasEntre(
                tablero,
                generalRojo.getFila(),
                generalRojo.getColumna(),
                generalNegro.getFila(),
                generalNegro.getColumna()
        ) == 0;
    }
}
