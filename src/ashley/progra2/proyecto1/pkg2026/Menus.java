/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.progra2.proyecto1.pkg2026;

/**
 *
 * @author ashley
 */

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.time.format.DateTimeFormatter;

public class Menus {

    private ArrayList<Player> players;
    private Player loggedInPlayer;
    private ArrayList<LogUsuario> logsUsuarios;

    public Menus() {
        players = new ArrayList<Player>();
        loggedInPlayer = null;
        logsUsuarios = new ArrayList<LogUsuario>();
    }

    // =========================================================
    // MENU INICIO
    // =========================================================

    public String login(String username, String password) {
        Player player = buscarPlayerPorUsername(username);

        if (player == null) {
            return "El username no existe.";
        }

        if (!player.getPassword().equals(password)) {
            return "Password incorrecto.";
        }

        if (!player.isActivo()) {
            return "CUENTA_DESACTIVADA";
        }

        player.actualizarFechaIngreso();
        loggedInPlayer = player;
        return "Login exitoso.";
    }

    public String crearPlayer(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "El username no puede estar vacío.";
        }

        if (existeUsername(username)) {
            return "El username ya existe. Debe ser único.";
        }

        if (!passwordValido(password)) {
            return "El password debe tener exactamente 5 caracteres, contener mayusculas, minusculas, numero y al menos un caracter especial.";
        }

        Player nuevo = new Player(username, password);
        players.add(nuevo);

        loggedInPlayer = nuevo;

        return "Usuario creado exitosamente.";
    }

    public String salir() {
        loggedInPlayer = null;
        return "Programa terminado.";
    }

    // =========================================================
    // MENU PRINCIPAL
    // =========================================================

    public String logout() {
        loggedInPlayer = null;
        return "Logout exitoso.";
    }

    public boolean haySesionActiva() {
        return loggedInPlayer != null;
    }

    public Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    // =========================================================
    // JUGAR XIANGQI
    // =========================================================

    public ArrayList<Player> obtenerOponentesDisponibles() {
        ArrayList<Player> oponentes = new ArrayList<Player>();

        if (loggedInPlayer == null) {
            return oponentes;
        }

        for (int i = 0; i < players.size(); i++) {
            Player actual = players.get(i);

            if (actual.isActivo() && !actual.getUsername().equalsIgnoreCase(loggedInPlayer.getUsername())) {
                oponentes.add(actual);
            }
        }

        return oponentes;
    }

    public Partida nuevaPartida(String usernameOponente) {
        if (loggedInPlayer == null) {
            return null;
        }

        Player oponente = buscarPlayerActivo(usernameOponente);

        if (oponente == null) {
            return null;
        }

        if (oponente.getUsername().equalsIgnoreCase(loggedInPlayer.getUsername())) {
            return null;
        }

        Partida partida = new Partida(loggedInPlayer, oponente);

        registrarLogAmbos(
                partida,
                partida.getIdPartida() + " - Inicio partida contra " + oponente.getUsername(),
                partida.getIdPartida() + " - Inicio partida contra " + loggedInPlayer.getUsername()
        );

        return partida;
    }

    public String moverFicha(Partida partida, String movimiento) {
        if (partida == null) {
            return "No existe partida.";
        }

        if (!partida.isActiva()) {
            return "La partida ya terminó.";
        }

        Player jugadorQueMueve = partida.getTurnoActual();

        String accion = "[" + partida.getIdPartida() + "] " + jugadorQueMueve.getUsername() + " movió ficha: " + movimiento;

        registrarLogAmbos(partida, accion, accion);

        partida.cambiarTurno();

        return "Movimiento registrado: " + movimiento;
    }

    public String finalizarPartidaPorVictoria(Partida partida, Player ganador, Player perdedor) {
        if (partida == null || ganador == null || perdedor == null) {
            return "No se pudo finalizar la partida.";
        }

        ganador.sumarPuntos(3);
        ganador.sumarVictoria();
        ganador.sumarPartidaJugada();
        perdedor.sumarPartidaJugada();

        partida.terminarPartida();

        String mensaje = "[" + partida.getIdPartida() + "] " + ganador.getUsername() + " venció a " + perdedor.getUsername()
                + ", felicidades has ganado 3 puntos.";

        registrarLog(ganador.getUsername(), mensaje);
        registrarLog(perdedor.getUsername(), mensaje);

        return mensaje;
    }

    public String finalizarPartidaPorRetiro(Partida partida, Player jugadorRetirado) {
        if (partida == null || jugadorRetirado == null) {
            return "No se pudo finalizar la partida.";
        }

        Player ganador;

        if (partida.getJugadorRojo().getUsername().equalsIgnoreCase(jugadorRetirado.getUsername())) {
            ganador = partida.getJugadorNegro();
        } else {
            ganador = partida.getJugadorRojo();
        }

        ganador.sumarPuntos(3);
        ganador.sumarVictoria();
        ganador.sumarPartidaJugada();
        jugadorRetirado.sumarPartidaJugada();

        partida.terminarPartida();

        String mensaje = "[" + partida.getIdPartida() + "] " + jugadorRetirado.getUsername() + " se ha retirado, felicidades "
                + ganador.getUsername() + ", has ganado 3 puntos.";

        registrarLog(ganador.getUsername(), mensaje);
        registrarLog(jugadorRetirado.getUsername(), mensaje);

        return mensaje;
    }

    // =========================================================
    // MI CUENTA
    // =========================================================

    public String verMiCuenta() {
        if (loggedInPlayer == null) {
            return "No hay usuario logged in.";
        }

        return "Username: " + loggedInPlayer.getUsername()
                + "\nPuntos: " + loggedInPlayer.getPuntos()
                + "\nFecha de ingreso: " + loggedInPlayer.getFechaIngreso()
                + "\nEstado: " + loggedInPlayer.getEstadoTexto();
    }

    public String cambiarPassword(String passwordActual, String passwordNuevo) {
        if (loggedInPlayer == null) {
            return "No hay usuario logged in.";
        }

        if (!loggedInPlayer.getPassword().equals(passwordActual)) {
            return "El password actual es incorrecto.";
        }

        if (!passwordValido(passwordNuevo)) {
            return "El password debe tener exactamente 5 caracteres, contener mayusculas, minusculas, numero y al menos un caracter especial.";
        }

        loggedInPlayer.setPassword(passwordNuevo);

        return "Password cambiado exitosamente.";
    }

    public String eliminarMiCuenta(String passwordActual) {
        if (loggedInPlayer == null) {
            return "No hay usuario logged in.";
        }

        if (!loggedInPlayer.getPassword().equals(passwordActual)) {
            return "Password incorrecto. No se eliminó la cuenta.";
        }

        String usernameEliminado = loggedInPlayer.getUsername();

        players.remove(loggedInPlayer);
        borrarLogsUsuario(usernameEliminado);

        loggedInPlayer = null;

        return "Cuenta eliminada exitosamente.";
    }

    public String desactivarMiCuenta(String passwordActual) {
        if (loggedInPlayer == null) {
            return "No hay usuario logged in.";
        }

        if (!loggedInPlayer.getPassword().equals(passwordActual)) {
            return "Password incorrecto. No se desactivo la cuenta.";
        }

        loggedInPlayer.setActivo(false);
        loggedInPlayer = null;

        return "Cuenta desactivada exitosamente.";
    }

    public String reactivarCuenta(String username, String password) {
        Player player = buscarPlayerPorUsername(username);

        if (player == null) {
            return "El username no existe.";
        }

        if (!player.getPassword().equals(password)) {
            return "Password incorrecto.";
        }

        player.setActivo(true);
        player.actualizarFechaIngreso();
        loggedInPlayer = player;

        return "Cuenta reactivada exitosamente.";
    }

    // =========================================================
    // REPORTES
    // =========================================================

    public ArrayList<String> rankingJugadores() {
        ArrayList<Player> activos = obtenerPlayersActivos();

        ordenarRankingDown(activos, 0);

        ArrayList<String> reporte = new ArrayList<String>();

        for (int i = 0; i < activos.size(); i++) {
            Player actual = activos.get(i);
            reporte.add((i + 1) + " - " + actual.getUsername() + " - " + actual.getPuntos());
        }

        return reporte;
    }

    public ArrayList<String> logsMisUltimosJuegos() {
        ArrayList<String> misLogs = new ArrayList<String>();

        if (loggedInPlayer == null) {
            return misLogs;
        }

        cargarLogsUsuario(loggedInPlayer.getUsername(), misLogs);

        ArrayList<String> resultado = new ArrayList<String>();

        // Recursividad UP: muestra del más reciente al más viejo
        agregarLogsRecientesUp(misLogs, misLogs.size() - 1, resultado);

        return resultado;
    }

    // =========================================================
    // LOGS INDIVIDUALES POR USUARIO
    // =========================================================

    private void registrarLogAmbos(Partida partida, String accionRojo, String accionNegro) {
        registrarLog(partida.getJugadorRojo().getUsername(), accionRojo);
        registrarLog(partida.getJugadorNegro().getUsername(), accionNegro);
    }

    private void registrarLog(String username, String accion) {
        LogUsuario nuevoLog = new LogUsuario(username, LocalDateTime.now().toString(), accion);
        logsUsuarios.add(nuevoLog);
    }

    private void cargarLogsUsuario(String username, ArrayList<String> misLogs) {
        for (int i = 0; i < logsUsuarios.size(); i++) {
            LogUsuario log = logsUsuarios.get(i);

            if (log.getUsername().equalsIgnoreCase(username)) {
                misLogs.add(log.getFecha() + " - " + log.getAccion());
            }
        }
    }

    private void borrarLogsUsuario(String username) {
        for (int i = logsUsuarios.size() - 1; i >= 0; i--) {
            if (logsUsuarios.get(i).getUsername().equalsIgnoreCase(username)) {
                logsUsuarios.remove(i);
            }
        }
    }

    // =========================================================
    // RECURSIVIDAD UP
    // =========================================================

    private void agregarLogsRecientesUp(ArrayList<String> lista, int posicion, ArrayList<String> resultado) {
        if (posicion >= 0) {
            resultado.add(lista.get(posicion));
            agregarLogsRecientesUp(lista, posicion - 1, resultado);
        }
    }

    // =========================================================
    // RECURSIVIDAD DOWN
    // =========================================================

    private void ordenarRankingDown(ArrayList<Player> lista, int posicion) {
        if (posicion < lista.size() - 1) {
            int mayor = buscarMayorDown(lista, posicion, posicion + 1);

            Player temporal = lista.get(posicion);
            lista.set(posicion, lista.get(mayor));
            lista.set(mayor, temporal);

            ordenarRankingDown(lista, posicion + 1);
        }
    }

    private int buscarMayorDown(ArrayList<Player> lista, int mayorActual, int posicion) {
        if (posicion < lista.size()) {
            if (lista.get(posicion).getPuntos() > lista.get(mayorActual).getPuntos()) {
                mayorActual = posicion;
            }

            return buscarMayorDown(lista, mayorActual, posicion + 1);
        }

        return mayorActual;
    }

    // =========================================================
    // METODOS AUXILIARES
    // =========================================================

    private boolean passwordValido(String password) {
        if (password == null || password.length() != 5) {
            return false;
        }

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (int i = 0; i < password.length(); i++) {

            char caracter = password.charAt(i);

            if (Character.isUpperCase(caracter)) {
                tieneMayuscula = true;
            } else if (Character.isLowerCase(caracter)) {
                tieneMinuscula = true;
            } else if (Character.isDigit(caracter)) {
                tieneNumero = true;
            } else {
                tieneEspecial = true;
            }
        }

        return tieneMayuscula && tieneMinuscula && tieneNumero && tieneEspecial;
    }

    private boolean existeUsername(String username) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }

    private Player buscarPlayerActivo(String username) {
        for (int i = 0; i < players.size(); i++) {
            Player actual = players.get(i);

            if (actual.getUsername().equalsIgnoreCase(username) && actual.isActivo()) {
                return actual;
            }
        }

        return null;
    }

    private ArrayList<Player> obtenerPlayersActivos() {
        ArrayList<Player> activos = new ArrayList<Player>();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isActivo()) {
                activos.add(players.get(i));
            }
        }

        return activos;
    }

    private Player buscarPlayerPorUsername(String username) {
        for (int i = 0; i < players.size(); i++) {
            Player actual = players.get(i);

            if (actual.getUsername().equalsIgnoreCase(username)) {
                return actual;
            }
        }

        return null;
    }

    // =========================================================
    // CLASE LOG USUARIO
    // =========================================================

    public static class LogUsuario {
        private String username;
        private String fecha;
        private String accion;

        public LogUsuario(String username, String fecha, String accion) {
            this.username = username;
            this.fecha = fecha;
            this.accion = accion;
        }

        public String getUsername() {
            return username;
        }

        public String getFecha() {
            return fecha;
        }

        public String getAccion() {
            return accion;
        }
    }

    // =========================================================
    // CLASE PLAYER
    // =========================================================

    public static class Player {
        private String username;
        private String password;
        private int puntos;
        private String fechaIngreso;
        private boolean activo;
        private int partidasJugadas;
        private int victorias;

        public Player(String username, String password) {
            this.username = username;
            this.password = password;
            this.puntos = 0;
            this.fechaIngreso = LocalDateTime.now().toString();
            this.activo = true;
            this.partidasJugadas = 0;
            this.victorias = 0;
        }

        public Player(String username, String password, int puntos, String fechaIngreso, boolean activo) {
            this.username = username;
            this.password = password;
            this.puntos = puntos;
            this.fechaIngreso = fechaIngreso;
            this.activo = activo;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getPuntos() {
            return puntos;
        }

        public String getFechaIngreso() {
            return fechaIngreso;
        }

        public String getFechaIngresoFormateada() {
            LocalDateTime fecha = LocalDateTime.parse(fechaIngreso);
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return fecha.format(formato);
        }

        public String getEstadoTexto() {
            return activo ? "Activo" : "Inactivo";
        }

        public int getPartidasJugadas() {
            return partidasJugadas;
        }

        public int getVictorias() {
            return victorias;
        }

        public boolean isActivo() {
            return activo;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setActivo(boolean activo) {
            this.activo = activo;
        }

        public void sumarPuntos(int puntosGanados) {
            puntos += puntosGanados;
        }

        public void actualizarFechaIngreso() {
            this.fechaIngreso = LocalDateTime.now().toString();
        }

        public void sumarPartidaJugada() {
            partidasJugadas++;
        }

        public void sumarVictoria() {
            victorias++;
        }

    }

    // =========================================================
    // CLASE PARTIDA
    // =========================================================

    public static class Partida {
        private String idPartida;
        private Player jugadorRojo;
        private Player jugadorNegro;
        private Player turnoActual;
        private boolean activa;

        public Partida(Player jugadorRojo, Player jugadorNegro) {
            this.idPartida = generarIdPartida(jugadorRojo, jugadorNegro);
            this.jugadorRojo = jugadorRojo;
            this.jugadorNegro = jugadorNegro;
            this.turnoActual = jugadorRojo;
            this.activa = true;
        }

        public String getIdPartida() {
            return idPartida;
        }

        public Player getJugadorRojo() {
            return jugadorRojo;
        }

        public Player getJugadorNegro() {
            return jugadorNegro;
        }

        public Player getTurnoActual() {
            return turnoActual;
        }

        public boolean isActiva() {
            return activa;
        }

        public void terminarPartida() {
            activa = false;
        }

        private static String generarIdPartida(Player jugadorRojo, Player jugadorNegro) {
            String fecha = LocalDateTime.now().toString();

            fecha = fecha.replace("-", "");
            fecha = fecha.replace(":", "");
            fecha = fecha.replace(".", "");

            return "PX-" + jugadorRojo.getUsername() + "-" + jugadorNegro.getUsername() + "-" + fecha;
        }

        public String cambiarTurno() {
            if (turnoActual.getUsername().equalsIgnoreCase(jugadorRojo.getUsername())) {
                turnoActual = jugadorNegro;
            } else {
                turnoActual = jugadorRojo;
            }

            return "Turno de " + turnoActual.getUsername();
        }

        public String retirar(Player jugador) {
            if (!activa) {
                return "La partida ya terminó.";
            }

            activa = false;
            return jugador.getUsername() + " solicitó retirarse.";
        }
    }
}