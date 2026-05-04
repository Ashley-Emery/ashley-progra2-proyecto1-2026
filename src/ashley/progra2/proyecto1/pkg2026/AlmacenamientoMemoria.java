/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.progra2.proyecto1.pkg2026;

/**
 *
 * @author ashley
 */

import java.util.ArrayList;

public class AlmacenamientoMemoria implements Almacenamiento {
    
    private ArrayList<Menus.Player> players;
    private ArrayList<Menus.LogUsuario> logsUsuarios;
    private ArrayList<Menus.Partida> partidas;

    public AlmacenamientoMemoria() {
        players = new ArrayList<Menus.Player>();
        logsUsuarios = new ArrayList<Menus.LogUsuario>();
        partidas = new ArrayList<Menus.Partida>();
    }

    public void agregarPlayer(Menus.Player player) {
        players.add(player);
    }

    public void eliminarPlayer(Menus.Player player) {
        players.remove(player);
    }

    public ArrayList<Menus.Player> getPlayers() {
        return players;
    }

    public void agregarLog(Menus.LogUsuario log) {
        logsUsuarios.add(log);
    }

    public void eliminarLogsUsuario(String username) {
        for (int i = logsUsuarios.size() - 1; i >= 0; i--) {
            if (logsUsuarios.get(i).getUsername().equalsIgnoreCase(username)) {
                logsUsuarios.remove(i);
            }
        }
    }

    public ArrayList<Menus.LogUsuario> getLogsUsuarios() {
        return logsUsuarios;
    }

    public void agregarPartida(Menus.Partida partida) {
        partidas.add(partida);
    }

    public ArrayList<Menus.Partida> getPartidas() {
        return partidas;
    }
}
