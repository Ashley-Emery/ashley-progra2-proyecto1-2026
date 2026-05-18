/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.progra2.proyecto1.pkg2026;

/**
 *
 * @author ashley
 */

public class PartidaXiangqi {

    private Pieza[][] tablero;
    private Menus menus;
    private Menus.Partida partidaMenus;
    private String turnoColor;
    private String estado;
    private boolean activa;

    public PartidaXiangqi(Menus menus, Menus.Partida partidaMenus) throws XiangqiException {
        if (menus == null || partidaMenus == null) {
            throw new XiangqiException("No se puede iniciar partida sin datos válidos.");
        }

        this.menus = menus;
        this.partidaMenus = partidaMenus;
        this.tablero = new Pieza[10][9];
        this.turnoColor = "Rojo";
        this.estado = "En juego";
        this.activa = true;

        colocarPiezasIniciales();
    }

    private void colocarPiezasIniciales() {
        tablero[0][0] = new PiezasXiangqi.Carro("Negro", 0, 0);
        tablero[0][1] = new PiezasXiangqi.Caballo("Negro", 0, 1);
        tablero[0][2] = new PiezasXiangqi.Elefante("Negro", 0, 2);
        tablero[0][3] = new PiezasXiangqi.Oficial("Negro", 0, 3);
        tablero[0][4] = new PiezasXiangqi.General("Negro", 0, 4);
        tablero[0][5] = new PiezasXiangqi.Oficial("Negro", 0, 5);
        tablero[0][6] = new PiezasXiangqi.Elefante("Negro", 0, 6);
        tablero[0][7] = new PiezasXiangqi.Caballo("Negro", 0, 7);
        tablero[0][8] = new PiezasXiangqi.Carro("Negro", 0, 8);

        tablero[2][1] = new PiezasXiangqi.Canon("Negro", 2, 1);
        tablero[2][7] = new PiezasXiangqi.Canon("Negro", 2, 7);

        tablero[3][0] = new PiezasXiangqi.Soldado("Negro", 3, 0);
        tablero[3][2] = new PiezasXiangqi.Soldado("Negro", 3, 2);
        tablero[3][4] = new PiezasXiangqi.Soldado("Negro", 3, 4);
        tablero[3][6] = new PiezasXiangqi.Soldado("Negro", 3, 6);
        tablero[3][8] = new PiezasXiangqi.Soldado("Negro", 3, 8);

        tablero[9][0] = new PiezasXiangqi.Carro("Rojo", 9, 0);
        tablero[9][1] = new PiezasXiangqi.Caballo("Rojo", 9, 1);
        tablero[9][2] = new PiezasXiangqi.Elefante("Rojo", 9, 2);
        tablero[9][3] = new PiezasXiangqi.Oficial("Rojo", 9, 3);
        tablero[9][4] = new PiezasXiangqi.General("Rojo", 9, 4);
        tablero[9][5] = new PiezasXiangqi.Oficial("Rojo", 9, 5);
        tablero[9][6] = new PiezasXiangqi.Elefante("Rojo", 9, 6);
        tablero[9][7] = new PiezasXiangqi.Caballo("Rojo", 9, 7);
        tablero[9][8] = new PiezasXiangqi.Carro("Rojo", 9, 8);

        tablero[7][1] = new PiezasXiangqi.Canon("Rojo", 7, 1);
        tablero[7][7] = new PiezasXiangqi.Canon("Rojo", 7, 7);

        tablero[6][0] = new PiezasXiangqi.Soldado("Rojo", 6, 0);
        tablero[6][2] = new PiezasXiangqi.Soldado("Rojo", 6, 2);
        tablero[6][4] = new PiezasXiangqi.Soldado("Rojo", 6, 4);
        tablero[6][6] = new PiezasXiangqi.Soldado("Rojo", 6, 6);
        tablero[6][8] = new PiezasXiangqi.Soldado("Rojo", 6, 8);
    }

    public String mover(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) throws XiangqiException {
        validarMovimientoBasico(filaOrigen, columnaOrigen, filaDestino, columnaDestino);

        Pieza pieza = tablero[filaOrigen][columnaOrigen];
        Pieza destino = tablero[filaDestino][columnaDestino];

        if (!pieza.movimientoValido(tablero, filaDestino, columnaDestino)) {
            throw new XiangqiException("Movimiento invalido para la pieza seleccionada.");
        }

        tablero[filaDestino][columnaDestino] = pieza;
        tablero[filaOrigen][columnaOrigen] = null;
        pieza.setPosicion(filaDestino, columnaDestino);

        if (destino != null) {
            destino.capturar();
        }

        String movimiento = pieza.getNombre()
                + " " + filaOrigen + "," + columnaOrigen
                + " -> " + filaDestino + "," + columnaDestino;

        if (destino != null) {
            movimiento += " capturo " + destino.getNombre();
        }

        menus.moverFicha(partidaMenus, movimiento);

        String colorOponente = obtenerColorOponente(turnoColor);

        if (estaEnJaque(colorOponente)) {
            if (estaEnJaqueMate(colorOponente)) {
                estado = "Jaque mate";
                activa = false;

                if (turnoColor.equalsIgnoreCase("Rojo")) {
                    return menus.finalizarPartidaPorVictoria(
                            partidaMenus,
                            partidaMenus.getJugadorRojo(),
                            partidaMenus.getJugadorNegro()
                    );
                } else {
                    return menus.finalizarPartidaPorVictoria(
                            partidaMenus,
                            partidaMenus.getJugadorNegro(),
                            partidaMenus.getJugadorRojo()
                    );
                }
            } else {
                estado = "Jaque";
            }
        } else {
            estado = "En juego";
        }

        cambiarTurnoColor();

        return "Movimiento realizado. Estado: " + estado + ". Turno de " + turnoColor + ".";
    }

    private void validarMovimientoBasico(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) throws XiangqiException {
        if (!activa) {
            throw new XiangqiException("La partida ya termino.");
        }

        if (!ValidadorXiangqi.dentroTablero(filaOrigen, columnaOrigen)
                || !ValidadorXiangqi.dentroTablero(filaDestino, columnaDestino)) {
            throw new XiangqiException("Movimiento fuera del tablero.");
        }

        Pieza pieza = tablero[filaOrigen][columnaOrigen];

        if (pieza == null) {
            throw new XiangqiException("No hay pieza seleccionada.");
        }

        if (!pieza.getColor().equalsIgnoreCase(turnoColor)) {
            throw new XiangqiException("No puedes mover una pieza del rival.");
        }

        Pieza destino = tablero[filaDestino][columnaDestino];

        if (destino != null && destino.getColor().equalsIgnoreCase(pieza.getColor())) {
            throw new XiangqiException("No puedes capturar una pieza de tu mismo color.");
        }
    }

    private boolean estaEnJaque(String colorGeneral) {
        Pieza general = buscarGeneral(colorGeneral);

        if (general == null) {
            return true;
        }

        String colorRival = obtenerColorOponente(colorGeneral);

        if (ValidadorXiangqi.generalesEnfrentados(tablero)) {
            return true;
        }

        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 9; c++) {
                Pieza pieza = tablero[f][c];

                if (pieza != null && pieza.getColor().equalsIgnoreCase(colorRival)) {
                    if (pieza.movimientoValido(tablero, general.getFila(), general.getColumna())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean estaEnJaqueMate(String colorGeneral) {
        for (int fo = 0; fo < 10; fo++) {
            for (int co = 0; co < 9; co++) {
                Pieza pieza = tablero[fo][co];

                if (pieza != null && pieza.getColor().equalsIgnoreCase(colorGeneral)) {
                    for (int fd = 0; fd < 10; fd++) {
                        for (int cd = 0; cd < 9; cd++) {
                            if (puedeEscapar(pieza, fo, co, fd, cd, colorGeneral)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean puedeEscapar(Pieza pieza, int fo, int co, int fd, int cd, String colorGeneral) {
        if (!ValidadorXiangqi.dentroTablero(fd, cd)) {
            return false;
        }

        Pieza destino = tablero[fd][cd];

        if (destino != null && destino.getColor().equalsIgnoreCase(pieza.getColor())) {
            return false;
        }

        if (!pieza.movimientoValido(tablero, fd, cd)) {
            return false;
        }

        tablero[fd][cd] = pieza;
        tablero[fo][co] = null;
        pieza.setPosicion(fd, cd);

        if (destino != null) {
            destino.capturar();
        }

        boolean sigueEnJaque = estaEnJaque(colorGeneral);

        tablero[fo][co] = pieza;
        tablero[fd][cd] = destino;
        pieza.setPosicion(fo, co);

        if (destino != null) {
            destino.reactivar();
        }

        return !sigueEnJaque;
    }

    public String retirar(String colorJugador) throws XiangqiException {
        if (!activa) {
            throw new XiangqiException("La partida ya termino.");
        }

        activa = false;
        estado = "Jugador " + colorJugador + " se retiro";

        if (colorJugador.equalsIgnoreCase("Rojo")) {
            return menus.finalizarPartidaPorRetiro(partidaMenus, partidaMenus.getJugadorRojo());
        }

        return menus.finalizarPartidaPorRetiro(partidaMenus, partidaMenus.getJugadorNegro());
    }

    private Pieza buscarGeneral(String color) {
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 9; c++) {
                Pieza pieza = tablero[f][c];

                if (pieza != null
                        && pieza.getNombre().equalsIgnoreCase("General")
                        && pieza.getColor().equalsIgnoreCase(color)) {
                    return pieza;
                }
            }
        }

        return null;
    }

    private String obtenerColorOponente(String color) {
        if (color.equalsIgnoreCase("Rojo")) {
            return "Negro";
        }

        return "Rojo";
    }

    private void cambiarTurnoColor() {
        turnoColor = obtenerColorOponente(turnoColor);
    }

    public Pieza[][] getTablero() {
        return tablero;
    }

    public String getTurnoColor() {
        return turnoColor;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isActiva() {
        return activa;
    }

    public Menus.Partida getPartidaMenus() {
        return partidaMenus;
    }
}