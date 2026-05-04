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

public interface Almacenamiento {
    
    void agregarPlayer(Menus.Player player);
    void eliminarPlayer(Menus.Player player);
    ArrayList<Menus.Player> getPlayers();

    void agregarLog(Menus.LogUsuario log);
    void eliminarLogsUsuario(String username);
    ArrayList<Menus.LogUsuario> getLogsUsuarios();

    void agregarPartida(Menus.Partida partida);
    ArrayList<Menus.Partida> getPartidas();
    
}
