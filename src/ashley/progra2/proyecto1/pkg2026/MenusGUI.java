/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ashley.progra2.proyecto1.pkg2026;

/**
 *
 * @author ashley
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class MenusGUI extends JFrame {

    private Menus menus;
    private CardLayout cardLayout;
    private JPanel contenedor;

    private static final String MENU_INICIO = "MENU_INICIO";
    private static final String CREAR_PLAYER = "CREAR_PLAYER";
    private static final String MENU_PRINCIPAL = "MENU_PRINCIPAL";
    private static final String LOG_IN = "LOG_IN";
    private static final String MI_CUENTA = "MI_CUENTA";
    private static final String CAMBIAR_PASSWORD = "CAMBIAR_PASSWORD";
    private static final String ELIMINAR_CUENTA = "ELIMINAR_CUENTA";
    private static final String REPORTES = "REPORTES";
    private static final String RANKING_JUGADORES = "RANKING_JUGADORES";
    private static final String LOG_ULTIMAS_PARTIDAS = "LOG_ULTIMAS_PARTIDAS";
    private static final String NUEVA_PARTIDA = "NUEVA_PARTIDA";

    public MenusGUI() {
        menus = new Menus();
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        setTitle("Xiangqui");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setMinimumSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setResizable(true);

        contenedor.add(crearMenuInicio(), MENU_INICIO);
        contenedor.add(crearPlayer(), CREAR_PLAYER);
        contenedor.add(crearMenuPrincipal(), MENU_PRINCIPAL);
        contenedor.add(crearLogin(), LOG_IN);
        contenedor.add(crearMiCuenta(), MI_CUENTA);
        contenedor.add(crearCambiarPassword(), CAMBIAR_PASSWORD);
        contenedor.add(crearEliminarCuenta(), ELIMINAR_CUENTA);
        contenedor.add(crearReportes(), REPORTES);
        contenedor.add(crearRankingJugadores(), RANKING_JUGADORES);
        contenedor.add(crearLogsUltimasPartidas(), LOG_ULTIMAS_PARTIDAS);
        contenedor.add(crearNuevaPartida(), NUEVA_PARTIDA);

        add(contenedor);
        cardLayout.show(contenedor, MENU_INICIO);

        setVisible(true);
    }

    // =========================================================
    // MENU INICIO - CARD LAYOUT
    // =========================================================

    private JPanel crearMenuInicio() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("Xiangqui");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Open Sans", Font.BOLD, 42));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(3, 1, 0, 18));

        JButton btnLogin = crearBotonMenu("Log in");
        JButton btnCrearJugador = crearBotonMenu("Crear jugador");
        JButton btnSalir = crearBotonMenu("Salir");

        btnLogin.addActionListener(e -> cardLayout.show(contenedor, LOG_IN));
        btnCrearJugador.addActionListener(e -> cardLayout.show(contenedor, CREAR_PLAYER));
        btnSalir.addActionListener(e -> {
            menus.salir();
            System.exit(0);
        });

        panelBotones.add(btnLogin);
        panelBotones.add(btnCrearJugador);
        panelBotones.add(btnSalir);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(titulo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelBotones, gbc);

        return panel;
    }

    // =========================================================
    // CREAR PLAYER - CARD LAYOUT
    // =========================================================

    private JPanel crearPlayer() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel tituloJuego = new JLabel("Xiangqui");
        tituloJuego.setForeground(Color.WHITE);
        tituloJuego.setFont(new Font("Open Sans", Font.BOLD, 42));

        JLabel tituloCrear = new JLabel("Crear usuario nuevo");
        tituloCrear.setForeground(new Color(0xffd600));
        tituloCrear.setFont(new Font("Open Sans", Font.BOLD, 30));

        JLabel lblUsuario = crearLabelFormulario("Usuario");
        JLabel lblPassword = crearLabelFormulario("Contraseña");

        JTextField txtUsuario = crearCampoTexto();
        JPasswordField txtPassword = crearCampoPassword();

        JButton btnVolver = crearBotonColor("Volver", new Color(0xec9c0d));
        JButton btnCrearUsuario = crearBotonColor("Crear usuario", new Color(0xa93407));

        btnVolver.addActionListener(e -> {
            txtUsuario.setText("");
            txtPassword.setText("");
            cardLayout.show(contenedor, MENU_INICIO);
        });

        btnCrearUsuario.addActionListener(e -> {
            String username = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());

            String respuesta = menus.crearPlayer(username, password);

            JOptionPane.showMessageDialog(this, respuesta);

            if (respuesta.equals("Usuario creado exitosamente.")) {
                txtUsuario.setText("");
                txtPassword.setText("");
                cardLayout.show(contenedor, MENU_PRINCIPAL);
            }
        });

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);

        GridBagConstraints fgbc = new GridBagConstraints();

        fgbc.gridx = 0;
        fgbc.gridy = 0;
        fgbc.anchor = GridBagConstraints.WEST;
        fgbc.insets = new Insets(0, 0, 25, 0);
        panelFormulario.add(tituloCrear, fgbc);

        fgbc.gridy = 1;
        fgbc.insets = new Insets(0, 35, 8, 0);
        panelFormulario.add(lblUsuario, fgbc);

        fgbc.gridy = 2;
        fgbc.insets = new Insets(0, 35, 25, 0);
        panelFormulario.add(txtUsuario, fgbc);

        fgbc.gridy = 3;
        fgbc.insets = new Insets(0, 35, 8, 0);
        panelFormulario.add(lblPassword, fgbc);

        fgbc.gridy = 4;
        fgbc.insets = new Insets(0, 35, 35, 0);
        panelFormulario.add(txtPassword, fgbc);

        fgbc.gridy = 5;
        fgbc.insets = new Insets(0, 35, 0, 0);
        panelFormulario.add(btnCrearUsuario, fgbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(tituloJuego, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelFormulario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.55;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 90, 70, 0);
        panel.add(btnVolver, gbc);

        return panel;
    }

    // =========================================================
    // LOG IN - CARD LAYOUT
    // =========================================================

    private JPanel crearLogin() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel tituloJuego = new JLabel("Xiangqui");
        tituloJuego.setForeground(Color.WHITE);
        tituloJuego.setFont(new Font("Open Sans", Font.BOLD, 42));

        JLabel tituloLogin = new JLabel("Inicio de sesion");
        tituloLogin.setForeground(new Color(0xffd600));
        tituloLogin.setFont(new Font("Open Sans", Font.BOLD, 30));

        JLabel lblUsuario = crearLabelFormulario("Usuario");
        JLabel lblPassword = crearLabelFormulario("Contraseña");

        JTextField txtUsuario = crearCampoTexto();
        JPasswordField txtPassword = crearCampoPassword();

        JButton btnVolver = crearBotonColor("Volver", new Color(0xec9c0d));
        JButton btnLogin = crearBotonColor("log in", new Color(0xa93407));

        btnVolver.addActionListener(e -> {
            txtUsuario.setText("");
            txtPassword.setText("");
            cardLayout.show(contenedor, MENU_INICIO);
        });

        btnLogin.addActionListener(e -> {
            String username = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());

            String respuesta = menus.login(username, password);

            if (respuesta.equals("Login exitoso. Ir al MENU PRINCIPAL.")) {
                txtUsuario.setText("");
                txtPassword.setText("");
                cardLayout.show(contenedor, MENU_PRINCIPAL);
            } else {
                JOptionPane.showMessageDialog(this, respuesta);
            }
        });

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);

        GridBagConstraints fgbc = new GridBagConstraints();

        fgbc.gridx = 0;
        fgbc.gridy = 0;
        fgbc.anchor = GridBagConstraints.WEST;
        fgbc.insets = new Insets(0, 0, 25, 0);
        panelFormulario.add(tituloLogin, fgbc);

        fgbc.gridy = 1;
        fgbc.insets = new Insets(0, 35, 8, 0);
        panelFormulario.add(lblUsuario, fgbc);

        fgbc.gridy = 2;
        fgbc.insets = new Insets(0, 35, 25, 0);
        panelFormulario.add(txtUsuario, fgbc);

        fgbc.gridy = 3;
        fgbc.insets = new Insets(0, 35, 8, 0);
        panelFormulario.add(lblPassword, fgbc);

        fgbc.gridy = 4;
        fgbc.insets = new Insets(0, 35, 35, 0);
        panelFormulario.add(txtPassword, fgbc);

        fgbc.gridy = 5;
        fgbc.insets = new Insets(0, 35, 0, 0);
        panelFormulario.add(btnLogin, fgbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(tituloJuego, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelFormulario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.55;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 90, 70, 0);
        panel.add(btnVolver, gbc);

        return panel;
    }

    // =========================================================
    // MENU PRINCIPAL - CARD LAYOUT
    // =========================================================

    private JPanel crearMenuPrincipal() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("Xiangqui");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Open Sans", Font.BOLD, 42));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(4, 1, 0, 18));

        JButton btnNuevaPartida = crearBotonMenu("Nueva partida");
        JButton btnMiCuenta = crearBotonMenu("Mi cuenta");
        JButton btnReportes = crearBotonMenu("Reportes");
        JButton btnLogout = crearBotonMenu("Log out");

        btnNuevaPartida.addActionListener(e -> cardLayout.show(contenedor, NUEVA_PARTIDA));
        btnMiCuenta.addActionListener(e -> cardLayout.show(contenedor, MI_CUENTA));
        btnReportes.addActionListener(e -> cardLayout.show(contenedor, REPORTES));

        btnLogout.addActionListener(e -> {
            menus.logout();
            cardLayout.show(contenedor, MENU_INICIO);
        });

        panelBotones.add(btnNuevaPartida);
        panelBotones.add(btnMiCuenta);
        panelBotones.add(btnReportes);
        panelBotones.add(btnLogout);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(titulo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelBotones, gbc);

        return panel;
    }

    // =========================================================
    // MI CUENTA - CARD LAYOUT
    // =========================================================

    private JPanel crearMiCuenta() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel tituloJuego = new JLabel("Xiangqui");
        tituloJuego.setForeground(Color.WHITE);
        tituloJuego.setFont(new Font("Open Sans", Font.BOLD, 42));

        JLabel titulo = new JLabel("Mi cuenta");
        titulo.setForeground(new Color(0xffd600));
        titulo.setFont(new Font("Open Sans", Font.BOLD, 30));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(3, 1, 0, 18));

        JButton btnCambiarPassword = crearBotonMenu("Cambiar password");
        JButton btnEliminarCuenta = crearBotonMenu("Eliminar cuenta");
        JButton btnVolver = crearBotonColor("Volver", new Color(0xec9c0d));

        btnCambiarPassword.addActionListener(e -> cardLayout.show(contenedor, CAMBIAR_PASSWORD));

        btnEliminarCuenta.addActionListener(e -> cardLayout.show(contenedor, ELIMINAR_CUENTA));

        btnVolver.addActionListener(e -> cardLayout.show(contenedor, MENU_PRINCIPAL));

        panelBotones.add(btnCambiarPassword);
        panelBotones.add(btnEliminarCuenta);
        panelBotones.add(btnVolver);

        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setOpaque(false);

        GridBagConstraints rGbc = new GridBagConstraints();

        rGbc.gridx = 0;
        rGbc.gridy = 0;
        rGbc.insets = new Insets(0, 0, 30, 0);
        rGbc.anchor = GridBagConstraints.CENTER;
        panelDerecho.add(titulo, rGbc);

        rGbc.gridy = 1;
        panelDerecho.add(panelBotones, rGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(tituloJuego, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelDerecho, gbc);

        return panel;
    }

    // =========================================================
    // CAMBIAR PASSWORD - CARD LAYOUT
    // =========================================================

    private JPanel crearCambiarPassword() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel tituloJuego = new JLabel("Xiangqui");
        tituloJuego.setForeground(Color.WHITE);
        tituloJuego.setFont(new Font("Open Sans", Font.BOLD, 42));

        JLabel titulo = new JLabel("Cambiar contraseña");
        titulo.setForeground(new Color(0xffd600));
        titulo.setFont(new Font("Open Sans", Font.BOLD, 30));

        JLabel lblPasswordActual = crearLabelFormulario("Contraseña actual");
        JLabel lblPasswordNueva = crearLabelFormulario("Contraseña nueva");

        JPasswordField txtPasswordActual = crearCampoPassword();
        JPasswordField txtPasswordNueva = crearCampoPassword();

        JButton btnVolver = crearBotonColor("Volver", new Color(0xec9c0d));
        JButton btnGuardar = crearBotonColor("Guardar", new Color(0xa93407));

        btnVolver.addActionListener(e -> {
            txtPasswordActual.setText("");
            txtPasswordNueva.setText("");
            cardLayout.show(contenedor, MI_CUENTA);
        });

        btnGuardar.addActionListener(e -> {
            String passwordActual = new String(txtPasswordActual.getPassword());
            String passwordNueva = new String(txtPasswordNueva.getPassword());

            String respuesta = menus.cambiarPassword(passwordActual, passwordNueva);

            JOptionPane.showMessageDialog(this, respuesta);

            if (respuesta.equals("Password cambiado exitosamente.")) {
                txtPasswordActual.setText("");
                txtPasswordNueva.setText("");
                cardLayout.show(contenedor, MI_CUENTA);
            }
        });

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);

        GridBagConstraints fgbc = new GridBagConstraints();

        fgbc.gridx = 0;
        fgbc.gridy = 0;
        fgbc.anchor = GridBagConstraints.WEST;
        fgbc.insets = new Insets(0, 0, 25, 0);
        panelFormulario.add(titulo, fgbc);

        fgbc.gridy = 1;
        fgbc.insets = new Insets(0, 35, 8, 0);
        panelFormulario.add(lblPasswordActual, fgbc);

        fgbc.gridy = 2;
        fgbc.insets = new Insets(0, 35, 25, 0);
        panelFormulario.add(txtPasswordActual, fgbc);

        fgbc.gridy = 3;
        fgbc.insets = new Insets(0, 35, 8, 0);
        panelFormulario.add(lblPasswordNueva, fgbc);

        fgbc.gridy = 4;
        fgbc.insets = new Insets(0, 35, 35, 0);
        panelFormulario.add(txtPasswordNueva, fgbc);

        fgbc.gridy = 5;
        fgbc.insets = new Insets(0, 35, 0, 0);
        panelFormulario.add(btnGuardar, fgbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(tituloJuego, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelFormulario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.55;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 90, 70, 0);
        panel.add(btnVolver, gbc);

        return panel;
    }

    // =========================================================
    // ELIMINAR CUENTA - CARD LAYOUT
    // =========================================================

    private JPanel crearEliminarCuenta() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel tituloJuego = new JLabel("Xiangqui");
        tituloJuego.setForeground(Color.WHITE);
        tituloJuego.setFont(new Font("Open Sans", Font.BOLD, 42));

        JLabel titulo = new JLabel("Eliminar cuenta");
        titulo.setForeground(new Color(0xffd600));
        titulo.setFont(new Font("Open Sans", Font.BOLD, 30));

        JLabel mensaje = new JLabel(
                "<html><div style='width:330px;'>"
                + "Esta acción es permanente. Tu cuenta y todos tus datos serán eliminados "
                + "y no podrán recuperarse.<br><br>"
                + "Eliminar tu cuenta borrará todo tu progreso de forma irreversible.<br><br>"
                + "¿Deseas continuar?"
                + "</div></html>"
        );
        mensaje.setForeground(Color.WHITE);
        mensaje.setFont(new Font("Open Sans", Font.BOLD, 18));

        JButton btnVolver = crearBotonColor("Volver", new Color(0xec9c0d));
        JButton btnAceptar = crearBotonColor("Aceptar", new Color(0xa93407));

        btnVolver.addActionListener(e -> cardLayout.show(contenedor, MI_CUENTA));

        btnAceptar.addActionListener(e -> {
            JPasswordField campoPassword = new JPasswordField();

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    campoPassword,
                    "Ingrese su contraseña actual",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (opcion == JOptionPane.OK_OPTION) {
                String passwordActual = new String(campoPassword.getPassword());
                String respuesta = menus.eliminarMiCuenta(passwordActual);

                JOptionPane.showMessageDialog(this, respuesta);

                if (respuesta.equals("Cuenta eliminada exitosamente.")) {
                    cardLayout.show(contenedor, MENU_INICIO);
                }
            }
        });

        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setOpaque(false);

        GridBagConstraints rGbc = new GridBagConstraints();

        rGbc.gridx = 0;
        rGbc.gridy = 0;
        rGbc.anchor = GridBagConstraints.WEST;
        rGbc.insets = new Insets(0, 0, 25, 0);
        panelDerecho.add(titulo, rGbc);

        rGbc.gridy = 1;
        rGbc.insets = new Insets(0, 0, 35, 0);
        panelDerecho.add(mensaje, rGbc);

        rGbc.gridy = 2;
        rGbc.anchor = GridBagConstraints.CENTER;
        rGbc.insets = new Insets(0, 0, 0, 0);
        panelDerecho.add(btnAceptar, rGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(tituloJuego, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelDerecho, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.55;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 90, 70, 0);
        panel.add(btnVolver, gbc);

        return panel;
    }

    // =========================================================
    // REPORTES - CARD LAYOUT
    // =========================================================

    private JPanel crearReportes() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel tituloJuego = new JLabel("Xiangqui");
        tituloJuego.setForeground(Color.WHITE);
        tituloJuego.setFont(new Font("Open Sans", Font.BOLD, 42));

        JLabel titulo = new JLabel("Reportes");
        titulo.setForeground(new Color(0xffd600));
        titulo.setFont(new Font("Open Sans", Font.BOLD, 30));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(3, 1, 0, 18));

        JButton btnRanking = crearBotonMenu("Ranking de jugadores");
        JButton btnLogs = crearBotonMenu("Logs ultimas partidas");
        JButton btnVolver = crearBotonColor("Volver", new Color(0xec9c0d));

        btnRanking.addActionListener(e -> cardLayout.show(contenedor, RANKING_JUGADORES));
        btnLogs.addActionListener(e -> cardLayout.show(contenedor, LOG_ULTIMAS_PARTIDAS));
        btnVolver.addActionListener(e -> cardLayout.show(contenedor, MENU_PRINCIPAL));

        panelBotones.add(btnRanking);
        panelBotones.add(btnLogs);
        panelBotones.add(btnVolver);

        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setOpaque(false);

        GridBagConstraints rGbc = new GridBagConstraints();

        rGbc.gridx = 0;
        rGbc.gridy = 0;
        rGbc.insets = new Insets(0, 0, 30, 0);
        rGbc.anchor = GridBagConstraints.CENTER;
        panelDerecho.add(titulo, rGbc);

        rGbc.gridy = 1;
        panelDerecho.add(panelBotones, rGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(tituloJuego, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelDerecho, gbc);

        return panel;
    }

    // =========================================================
    // RANKING JUGADORES - CARD LAYOUT
    // =========================================================

    private JPanel crearRankingJugadores() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel tituloJuego = new JLabel("Xiangqui");
        tituloJuego.setForeground(Color.WHITE);
        tituloJuego.setFont(new Font("Open Sans", Font.BOLD, 42));

        JLabel titulo = new JLabel("Ranking de jugadores");
        titulo.setForeground(new Color(0xffd600));
        titulo.setFont(new Font("Open Sans", Font.BOLD, 30));

        String[] columnas = {"Posicion", "Usuario", "Puntaje"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Open Sans", Font.PLAIN, 16));
        tabla.setRowHeight(42);
        tabla.getTableHeader().setFont(new Font("Open Sans", Font.BOLD, 16));
        tabla.getTableHeader().setBackground(new Color(0xa93407));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 42));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(430, 300));

        JButton btnVolver = crearBotonColor("Volver", new Color(0xec9c0d));

        btnVolver.addActionListener(e -> cardLayout.show(contenedor, REPORTES));

        tabla.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                modelo.setRowCount(0);

                java.util.ArrayList<String> ranking = menus.rankingJugadores();

                for (int i = 0; i < ranking.size(); i++) {
                    String[] partes = ranking.get(i).split(" - ");

                    if (partes.length == 3) {
                        modelo.addRow(new Object[]{partes[0], partes[1], partes[2]});
                    }
                }
            }

            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {
            }

            public void ancestorMoved(javax.swing.event.AncestorEvent event) {
            }
        });

        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setOpaque(false);

        GridBagConstraints rGbc = new GridBagConstraints();

        rGbc.gridx = 0;
        rGbc.gridy = 0;
        rGbc.anchor = GridBagConstraints.CENTER;
        rGbc.insets = new Insets(0, 0, 25, 0);
        panelDerecho.add(titulo, rGbc);

        rGbc.gridy = 1;
        rGbc.insets = new Insets(0, 0, 0, 0);
        panelDerecho.add(scrollTabla, rGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(tituloJuego, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelDerecho, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.55;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 90, 70, 0);
        panel.add(btnVolver, gbc);

        return panel;
    }

    // =========================================================
    // LOG ULTIMAS PARTIDAS - CARD LAYOUT
    // =========================================================

    private JPanel crearLogsUltimasPartidas() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel tituloJuego = new JLabel("Xiangqui");
        tituloJuego.setForeground(Color.WHITE);
        tituloJuego.setFont(new Font("Open Sans", Font.BOLD, 42));

        JLabel titulo = new JLabel("Descargar logs");
        titulo.setForeground(new Color(0xffd600));
        titulo.setFont(new Font("Open Sans", Font.BOLD, 30));

        String[] columnas = {"Fecha", "Log"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Open Sans", Font.PLAIN, 16));
        tabla.setRowHeight(42);
        tabla.getTableHeader().setFont(new Font("Open Sans", Font.BOLD, 16));
        tabla.getTableHeader().setBackground(new Color(0xa93407));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 42));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(430, 300));

        JButton btnVolver = crearBotonColor("Volver", new Color(0xec9c0d));
        JButton btnExportar = crearBotonColor("Exportar logs", new Color(0xa93407));

        btnVolver.addActionListener(e -> cardLayout.show(contenedor, REPORTES));

        btnExportar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar logs");

            int opcion = fileChooser.showSaveDialog(this);

            if (opcion == JFileChooser.APPROVE_OPTION) {
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                String respuesta = menus.exportarLogsMisUltimosJuegos(ruta);

                JOptionPane.showMessageDialog(this, respuesta);
            }
        });

        tabla.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                modelo.setRowCount(0);

                java.util.ArrayList<String> logs = menus.logsMisUltimosJuegos();

                for (int i = 0; i < logs.size(); i++) {
                    String linea = logs.get(i);
                    int separador = linea.indexOf(" - ");

                    if (separador != -1) {
                        String fecha = linea.substring(0, separador);
                        String log = linea.substring(separador + 3);
                        modelo.addRow(new Object[]{fecha, log});
                    } else {
                        modelo.addRow(new Object[]{"", linea});
                    }
                }
            }

            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {
            }

            public void ancestorMoved(javax.swing.event.AncestorEvent event) {
            }
        });

        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setOpaque(false);

        GridBagConstraints rGbc = new GridBagConstraints();

        rGbc.gridx = 0;
        rGbc.gridy = 0;
        rGbc.anchor = GridBagConstraints.CENTER;
        rGbc.insets = new Insets(0, 0, 25, 0);
        panelDerecho.add(titulo, rGbc);

        rGbc.gridy = 1;
        rGbc.insets = new Insets(0, 0, 25, 0);
        panelDerecho.add(scrollTabla, rGbc);

        rGbc.gridy = 2;
        rGbc.insets = new Insets(0, 0, 0, 0);
        panelDerecho.add(btnExportar, rGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(tituloJuego, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelDerecho, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.55;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 90, 70, 0);
        panel.add(btnVolver, gbc);

        return panel;
    }

    // =========================================================
    // NUEVA PARTIDA - CARD LAYOUT
    // =========================================================

    private JPanel crearNuevaPartida() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel tituloJuego = new JLabel("Xiangqui");
        tituloJuego.setForeground(Color.WHITE);
        tituloJuego.setFont(new Font("Open Sans", Font.BOLD, 42));

        JLabel titulo = new JLabel("Nueva partida");
        titulo.setForeground(new Color(0xffd600));
        titulo.setFont(new Font("Open Sans", Font.BOLD, 30));

        JLabel instruccion = new JLabel("Seleccione un usuario oponente.");
        instruccion.setForeground(Color.WHITE);
        instruccion.setFont(new Font("Open Sans", Font.BOLD, 16));

        JPanel panelLista = new JPanel();
        panelLista.setBackground(Color.WHITE);
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));

        JScrollPane scrollLista = new JScrollPane(panelLista);
        scrollLista.setPreferredSize(new Dimension(330, 210));
        scrollLista.setBorder(null);

        JButton btnVolver = crearBotonColor("Volver", new Color(0xec9c0d));
        JButton btnSeleccionar = crearBotonColor("Seleccionar", new Color(0xa93407));

        btnVolver.addActionListener(e -> cardLayout.show(contenedor, MENU_PRINCIPAL));

        btnSeleccionar.addActionListener(e -> {
            int seleccionados = 0;
            String usernameSeleccionado = "";

            for (int i = 0; i < panelLista.getComponentCount(); i++) {
                JCheckBox check = (JCheckBox) panelLista.getComponent(i);

                if (check.isSelected()) {
                    seleccionados++;
                    usernameSeleccionado = check.getText();
                }
            }

            if (seleccionados != 1) {
                JOptionPane.showMessageDialog(this, "Solo se permite seleccionar un usuario oponente.");
            } else {
                Menus.Partida partida = menus.nuevaPartida(usernameSeleccionado);

                if (partida == null) {
                    JOptionPane.showMessageDialog(this, "No se pudo iniciar la partida.");
                } else {
                    JOptionPane.showMessageDialog(this, "Partida iniciada: " + partida.getIdPartida());
                    
                    // Más adelante, cuando exista TableroGUI.java:
                    // new TableroGUI(menus, partida);
                    // this.dispose();
                }
            }
        });

        panelLista.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                panelLista.removeAll();

                java.util.ArrayList<Menus.Player> oponentes = menus.obtenerOponentesDisponibles();

                for (int i = 0; i < oponentes.size(); i++) {
                    JCheckBox check = new JCheckBox(oponentes.get(i).getUsername());
                    check.setFont(new Font("Open Sans", Font.PLAIN, 22));
                    check.setBackground(Color.WHITE);
                    check.setFocusPainted(false);
                    panelLista.add(check);
                }

                panelLista.revalidate();
                panelLista.repaint();
            }

            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {
            }

            public void ancestorMoved(javax.swing.event.AncestorEvent event) {
            }
        });

        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setOpaque(false);

        GridBagConstraints rGbc = new GridBagConstraints();

        rGbc.gridx = 0;
        rGbc.gridy = 0;
        rGbc.anchor = GridBagConstraints.CENTER;
        rGbc.insets = new Insets(0, 0, 25, 0);
        panelDerecho.add(titulo, rGbc);

        rGbc.gridy = 1;
        rGbc.anchor = GridBagConstraints.WEST;
        rGbc.insets = new Insets(0, 0, 8, 0);
        panelDerecho.add(instruccion, rGbc);

        rGbc.gridy = 2;
        rGbc.insets = new Insets(0, 0, 30, 0);
        panelDerecho.add(scrollLista, rGbc);

        rGbc.gridy = 3;
        rGbc.anchor = GridBagConstraints.CENTER;
        rGbc.insets = new Insets(0, 0, 0, 0);
        panelDerecho.add(btnSeleccionar, rGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 90, 0, 0);
        panel.add(tituloJuego, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 70);
        panel.add(panelDerecho, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.55;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 90, 70, 0);
        panel.add(btnVolver, gbc);

        return panel;
    }

    // =========================================================
    // COMPONENTES VISUALES
    // =========================================================

    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Open Sans", Font.BOLD, 18));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(0x5a3a2e));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setPreferredSize(new Dimension(230, 60));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JButton crearBotonColor(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Open Sans", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setPreferredSize(new Dimension(260, 55));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JLabel crearLabelFormulario(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Open Sans", Font.BOLD, 18));
        return label;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Open Sans", Font.PLAIN, 18));
        campo.setPreferredSize(new Dimension(270, 45));
        campo.setBorder(null);
        return campo;
    }

    private JPasswordField crearCampoPassword() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(new Font("Open Sans", Font.PLAIN, 18));
        campo.setPreferredSize(new Dimension(270, 45));
        campo.setBorder(null);
        return campo;
    }

    private static class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel(String rutaImagen) {
            try {
                ImageIcon icono = new ImageIcon(FondoPanel.class.getResource(rutaImagen));
                imagenFondo = icono.getImage();
            } catch (Exception e) {
                imagenFondo = null;
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}