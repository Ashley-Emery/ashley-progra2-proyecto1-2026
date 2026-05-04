/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.progra2.proyecto1.pkg2026;

/**
 *
 * @author ashley
 */

public class PiezasXiangqi {

    public static class General extends Pieza {

        public General(String color, int fila, int columna) {
            super(color, "General", fila, columna);
        }

        public boolean movimientoValido(Pieza[][] tablero, int filaDestino, int columnaDestino) {
            int df = Math.abs(filaDestino - fila);
            int dc = Math.abs(columnaDestino - columna);

            return df + dc == 1
                    && ValidadorXiangqi.dentroPalacio(color, filaDestino, columnaDestino);
        }
    }

    public static class Elefante extends Pieza {

        public Elefante(String color, int fila, int columna) {
            super(color, "Elefante", fila, columna);
        }

        public boolean movimientoValido(Pieza[][] tablero, int filaDestino, int columnaDestino) {
            int df = filaDestino - fila;
            int dc = columnaDestino - columna;

            if (Math.abs(df) != 2 || Math.abs(dc) != 2) {
                return false;
            }

            if (color.equalsIgnoreCase("Rojo") && filaDestino < 5) {
                return false;
            }

            if (color.equalsIgnoreCase("Negro") && filaDestino > 4) {
                return false;
            }

            int ojoFila = fila + df / 2;
            int ojoColumna = columna + dc / 2;

            return tablero[ojoFila][ojoColumna] == null;
        }
    }

    public static class Oficial extends Pieza {

        public Oficial(String color, int fila, int columna) {
            super(color, "Oficial", fila, columna);
        }

        public boolean movimientoValido(Pieza[][] tablero, int filaDestino, int columnaDestino) {
            int df = Math.abs(filaDestino - fila);
            int dc = Math.abs(columnaDestino - columna);

            return df == 1
                    && dc == 1
                    && ValidadorXiangqi.dentroPalacio(color, filaDestino, columnaDestino);
        }
    }

    public static class Caballo extends Pieza {

        public Caballo(String color, int fila, int columna) {
            super(color, "Caballo", fila, columna);
        }

        public boolean movimientoValido(Pieza[][] tablero, int filaDestino, int columnaDestino) {
            int df = filaDestino - fila;
            int dc = columnaDestino - columna;

            if (!((Math.abs(df) == 2 && Math.abs(dc) == 1) || (Math.abs(df) == 1 && Math.abs(dc) == 2))) {
                return false;
            }

            int bloqueoFila = fila;
            int bloqueoColumna = columna;

            if (Math.abs(df) == 2) {
                bloqueoFila = fila + df / 2;
            } else {
                bloqueoColumna = columna + dc / 2;
            }

            return tablero[bloqueoFila][bloqueoColumna] == null;
        }
    }

    public static class Carro extends Pieza {

        public Carro(String color, int fila, int columna) {
            super(color, "Carro", fila, columna);
        }

        public boolean movimientoValido(Pieza[][] tablero, int filaDestino, int columnaDestino) {
            if (!ValidadorXiangqi.mismaLinea(fila, columna, filaDestino, columnaDestino)) {
                return false;
            }

            return ValidadorXiangqi.contarPiezasEntre(tablero, fila, columna, filaDestino, columnaDestino) == 0;
        }
    }

    public static class Canon extends Pieza {

        public Canon(String color, int fila, int columna) {
            super(color, "Canon", fila, columna);
        }

        public boolean movimientoValido(Pieza[][] tablero, int filaDestino, int columnaDestino) {
            if (!ValidadorXiangqi.mismaLinea(fila, columna, filaDestino, columnaDestino)) {
                return false;
            }

            int piezasEntre = ValidadorXiangqi.contarPiezasEntre(tablero, fila, columna, filaDestino, columnaDestino);
            Pieza destino = tablero[filaDestino][columnaDestino];

            if (destino == null) {
                return piezasEntre == 0;
            }

            return piezasEntre == 1 && ValidadorXiangqi.piezaEnemiga(this, destino);
        }
    }

    public static class Soldado extends Pieza {

        public Soldado(String color, int fila, int columna) {
            super(color, "Soldado", fila, columna);
        }

        public boolean movimientoValido(Pieza[][] tablero, int filaDestino, int columnaDestino) {
            int df = filaDestino - fila;
            int dc = columnaDestino - columna;

            if (color.equalsIgnoreCase("Rojo")) {
                if (fila >= 5) {
                    return df == -1 && dc == 0;
                }

                return (df == -1 && dc == 0) || (df == 0 && Math.abs(dc) == 1);
            }

            if (fila <= 4) {
                return df == 1 && dc == 0;
            }

            return (df == 1 && dc == 0) || (df == 0 && Math.abs(dc) == 1);
        }
    }
}
