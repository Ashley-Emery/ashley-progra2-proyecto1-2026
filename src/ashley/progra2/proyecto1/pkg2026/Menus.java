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

public class Menus {

    private ArrayList<Player> players;
    private Player loggedInPlayer;

    private static final String PLAYERS_FILE = "players.xia";

    public Menus() {
        players = new ArrayList<Player>();
        loggedInPlayer = null;

        resetearArchivoPlayers();
        cargarPlayers();
    }

    // =========================================================
    // MENU INICIO
    // =========================================================

    public String login(String username, String password) {
        Player player = buscarPlayerActivo(username);

        if (player == null) {
            return "El username no existe o la cuenta está inactiva.";
        }

        if (!player.getPassword().equals(password)) {
            return "Password incorrecto.";
        }

        loggedInPlayer = player;
        return "Login exitoso. Ir al MENU PRINCIPAL.";
    }

    public String crearPlayer(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "El username no puede estar vacío.";
        }

        if (existeUsername(username)) {
            return "El username ya existe. Debe ser único.";
        }

        if (!passwordValido(password)) {
            return "El password debe tener exactamente 5 caracteres.";
        }

        Player nuevo = new Player(username, password);
        players.add(nuevo);
        guardarPlayers();

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
        return "Logout exitoso. Ir al MENU INICIO.";
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
        partida.terminarPartida();
        guardarPlayers();

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
        partida.terminarPartida();
        guardarPlayers();

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
                + "\nActivo: " + loggedInPlayer.isActivo();
    }

    public String cambiarPassword(String passwordActual, String passwordNuevo) {
        if (loggedInPlayer == null) {
            return "No hay usuario logged in.";
        }

        if (!loggedInPlayer.getPassword().equals(passwordActual)) {
            return "El password actual es incorrecto.";
        }

        if (!passwordValido(passwordNuevo)) {
            return "El nuevo password debe tener exactamente 5 caracteres.";
        }

        loggedInPlayer.setPassword(passwordNuevo);
        guardarPlayers();

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

        loggedInPlayer.setActivo(false);
        borrarArchivoLogsUsuario(usernameEliminado);

        guardarPlayers();

        loggedInPlayer = null;

        return "Cuenta eliminada exitosamente.";
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

    public String exportarLogsMisUltimosJuegos(String rutaArchivo) {
        ArrayList<String> misLogs = logsMisUltimosJuegos();

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo));

            for (int i = 0; i < misLogs.size(); i++) {
                writer.println(misLogs.get(i));
            }

            writer.close();
            return "Logs exportados exitosamente.";
        } catch (IOException e) {
            return "No se pudieron exportar los logs.";
        }
    }

    // =========================================================
    // LOGS INDIVIDUALES POR USUARIO
    // =========================================================

    private void registrarLogAmbos(Partida partida, String accionRojo, String accionNegro) {
        registrarLog(partida.getJugadorRojo().getUsername(), accionRojo);
        registrarLog(partida.getJugadorNegro().getUsername(), accionNegro);
    }

    private void registrarLog(String username, String accion) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(obtenerArchivoLog(username), true));
            writer.println(LocalDateTime.now().toString() + " - " + accion);
            writer.close();
        } catch (IOException e) {
        }
    }

    private void cargarLogsUsuario(String username, ArrayList<String> misLogs) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(obtenerArchivoLog(username)));
            String linea;

            while ((linea = reader.readLine()) != null) {
                misLogs.add(linea);
            }

            reader.close();
        } catch (IOException e) {
        }
    }

    private String obtenerArchivoLog(String username) {
        return "logs_" + username + ".xia";
    }

    private void borrarArchivoLogsUsuario(String username) {
        File archivo = new File(obtenerArchivoLog(username));

        if (archivo.exists()) {
            archivo.delete();
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
        return password != null && password.length() == 5;
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

    // =========================================================
    // ARCHIVO DE PLAYERS
    // =========================================================

    private void resetearArchivoPlayers() {
        try {
            // Esto sobrescribe el archivo con nada, limpiándolo al inicio
            PrintWriter writer = new PrintWriter(new FileWriter(PLAYERS_FILE, false));
            writer.print("");
            writer.close();
        } catch (IOException e) {
        }
    }

    private void guardarPlayers() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(PLAYERS_FILE));

            for (int i = 0; i < players.size(); i++) {
                writer.println(players.get(i).toFileString());
            }

            writer.close();
        } catch (IOException e) {
        }
    }

    private void cargarPlayers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PLAYERS_FILE));
            String linea;

            while ((linea = reader.readLine()) != null) {
                Player player = Player.fromFileString(linea);

                if (player != null) {
                    players.add(player);
                }
            }

            reader.close();
        } catch (IOException e) {
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

        public Player(String username, String password) {
            this.username = username;
            this.password = password;
            this.puntos = 0;
            this.fechaIngreso = LocalDateTime.now().toString();
            this.activo = true;
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

        public String toFileString() {
            return username + "|" + password + "|" + puntos + "|" + fechaIngreso + "|" + activo;
        }

        public static Player fromFileString(String linea) {
            try {
                String[] partes = linea.split("\\|");

                String username = partes[0];
                String password = partes[1];
                int puntos = Integer.parseInt(partes[2]);
                String fechaIngreso = partes[3];
                boolean activo = Boolean.parseBoolean(partes[4]);

                return new Player(username, password, puntos, fechaIngreso, activo);
            } catch (Exception e) {
                return null;
            }
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