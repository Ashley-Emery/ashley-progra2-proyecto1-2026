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
import java.awt.*;

public class TableroGUI extends JFrame {

    private Menus menus;
    private Menus.Partida partidaMenus;
    private PartidaXiangqi partidaXiangqi;

    private MenusGUI menusGUI;

    private JButton[][] botonesTablero;
    private JLabel lblPartida;
    private JLabel lblTurno;
    private JLabel lblEstado;
    private JLabel lblUltimoMovimiento;
    private JLabel lblIdPartida;

    private JPanel panelCapturasRojo;
    private JPanel panelCapturasNegro;

    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;
    private int filaDestinoSeleccionada = -1;
    private int columnaDestinoSeleccionada = -1;

    private boolean piezaConfirmada = false;

    private static final Color COLOR_SELECCION = new Color(0x5170ff);
    private static final Color COLOR_DESTINO = new Color(0x4d992c);

    private static final int[][] GRID_IMAGENES = {
        {6, 2, 2, 13, 2, 14, 2, 2, 9},
        {4, 1, 1, 1, 10, 1, 1, 1, 5},
        {4, 1, 1, 12, 1, 11, 1, 1, 5},
        {4, 1, 1, 1, 1, 1, 1, 1, 5},
        {8, 3, 3, 3, 3, 3, 3, 3, 7},
        {6, 2, 2, 2, 2, 2, 2, 2, 9},
        {4, 1, 1, 1, 1, 1, 1, 1, 5},
        {4, 1, 1, 16, 1, 15, 1, 1, 5},
        {4, 1, 1, 1, 10, 1, 1, 1, 5},
        {8, 3, 3, 18, 3, 17, 3, 3, 7}
    };

    public TableroGUI(Menus menus, Menus.Partida partidaMenus, MenusGUI menusGUI) {
        this.menus = menus;
        this.partidaMenus = partidaMenus;
        this.menusGUI = menusGUI;

        try {
            this.partidaXiangqi = new PartidaXiangqi(menus, partidaMenus);
        } catch (XiangqiException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            dispose();
            return;
        }

        botonesTablero = new JButton[10][9];

        setTitle("Xiangqi - Tablero");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setMinimumSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setResizable(true);

        add(crearPanelPrincipal());

        actualizarInformacion("Sin movimientos todavía.");
        pintarPiezasIniciales();

        setVisible(true);
    }

    private JPanel crearPanelPrincipal() {
        FondoPanel panel = new FondoPanel("/ashley/progra2/proyecto1/pkg2026/assets/background_tablero.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panelTablero = crearPanelTablero();
        JPanel panelInfo = crearPanelInformacion();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 55, 30, 20);
        panel.add(panelTablero, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 20, 30, 55);
        panel.add(panelInfo, gbc);

        return panel;
    }

    private JPanel crearPanelTablero() {
        JPanel panelTablero = new JPanel(new GridLayout(10, 9, 0, 0));
        panelTablero.setOpaque(false);
        panelTablero.setPreferredSize(new Dimension(460, 560));

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                JButton boton = new JButton();
                boton.setFocusPainted(false);
                boton.setBorderPainted(false);
                boton.setContentAreaFilled(false);
                boton.setOpaque(false);
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                int numeroImagen = GRID_IMAGENES[fila][columna];
                boton.setIcon(cargarImagenGrid(numeroImagen, 52, 56));
                boton.setHorizontalTextPosition(SwingConstants.CENTER);
                
                
                final int filaActual = fila;
                final int columnaActual = columna;

                boton.addActionListener(e -> manejarClickTablero(filaActual, columnaActual));

                botonesTablero[fila][columna] = boton;
                panelTablero.add(boton);
            }
        }

        return panelTablero;
    }

    private JPanel crearPanelInformacion() {
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("Xiangqi", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(0xa93407));
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Open Sans", Font.BOLD, 16));
        titulo.setPreferredSize(new Dimension(430, 35));

        JPanel cajaInfo = new JPanel();
        cajaInfo.setLayout(new BoxLayout(cajaInfo, BoxLayout.Y_AXIS));
        cajaInfo.setBackground(new Color(0xa93407));
        cajaInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        cajaInfo.setPreferredSize(new Dimension(430, 175));

        lblPartida = crearLabelInfo();
        lblTurno = crearLabelInfo();
        lblEstado = crearLabelInfo();
        lblUltimoMovimiento = crearLabelInfo();

        cajaInfo.add(lblPartida);
        cajaInfo.add(Box.createVerticalStrut(18));
        cajaInfo.add(lblTurno);
        cajaInfo.add(lblEstado);
        cajaInfo.add(Box.createVerticalStrut(18));
        cajaInfo.add(lblUltimoMovimiento);

        JPanel cajaCapturas = new JPanel();
        cajaCapturas.setLayout(new BoxLayout(cajaCapturas, BoxLayout.Y_AXIS));
        cajaCapturas.setBackground(new Color(0x9ba38e));
        cajaCapturas.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        cajaCapturas.setPreferredSize(new Dimension(430, 125));

        JLabel tituloCapturas = crearLabelInfo();
        tituloCapturas.setText("[ Capturas ]");
        tituloCapturas.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelCapturasRojo = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelCapturasRojo.setOpaque(false);

        panelCapturasNegro = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelCapturasNegro.setOpaque(false);

        panelCapturasRojo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCapturasNegro.setAlignmentX(Component.LEFT_ALIGNMENT);

        cajaCapturas.add(tituloCapturas);
        cajaCapturas.add(panelCapturasRojo);
        cajaCapturas.add(panelCapturasNegro);

        lblIdPartida = new JLabel("", SwingConstants.RIGHT);
        lblIdPartida.setOpaque(true);
        lblIdPartida.setBackground(new Color(0x5a3a2e));
        lblIdPartida.setForeground(Color.WHITE);
        lblIdPartida.setFont(new Font("Open Sans", Font.BOLD, 14));
        lblIdPartida.setPreferredSize(new Dimension(430, 28));

        JButton btnAbandonar = crearBotonAbandonar();

        btnAbandonar.addActionListener(e -> abandonarPartida());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 8, 0);
        panelInfo.add(titulo, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 6, 0);
        panelInfo.add(cajaInfo, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 8, 0);
        panelInfo.add(cajaCapturas, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 55, 0);
        panelInfo.add(lblIdPartida, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelInfo.add(btnAbandonar, gbc);

        return panelInfo;
    }

    private JLabel crearLabelInfo() {
        JLabel label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Open Sans", Font.PLAIN, 14));
        return label;
    }

    private JButton crearBotonAbandonar() {
        JButton boton = new JButton("Abandonar partida");
        boton.setFont(new Font("Open Sans", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(0xec9c0d));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setPreferredSize(new Dimension(260, 55));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void actualizarInformacion(String ultimoMovimiento) {
        lblPartida.setText("Partida "
                + partidaMenus.getJugadorRojo().getUsername()
                + " vs "
                + partidaMenus.getJugadorNegro().getUsername());

        lblTurno.setText("Turno: " + obtenerUsernamePorColor(partidaXiangqi.getTurnoColor()));
        lblEstado.setText("Estado: " + partidaXiangqi.getEstado());

        lblUltimoMovimiento.setText("<html>[ Ultimo movimiento ]<br>" + ultimoMovimiento + "</html>");

        lblIdPartida.setText("[ ID: " + partidaMenus.getIdPartida() + " ]");

        actualizarCapturas();
    }

    private void abandonarPartida() {
        try {
            String respuesta = partidaXiangqi.retirar(partidaXiangqi.getTurnoColor());
            JOptionPane.showMessageDialog(this, respuesta);

            if (menusGUI != null) {
                menusGUI.mostrarRankingJugadores();
            }

            dispose();

        } catch (XiangqiException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void manejarClickTablero(int fila, int columna) {
        if (!piezaConfirmada) {
            manejarSeleccionDePieza(fila, columna);
        } else {
            seleccionarDestino(fila, columna);
        }
    }

    private void manejarSeleccionDePieza(int fila, int columna) {
        Pieza[][] tablero = partidaXiangqi.getTablero();
        Pieza pieza = tablero[fila][columna];

        if (pieza == null) {
            JOptionPane.showMessageDialog(this, "No hay pieza seleccionada.");
            limpiarSeleccion();
            return;
        }

        if (!pieza.getColor().equalsIgnoreCase(partidaXiangqi.getTurnoColor())) {
            JOptionPane.showMessageDialog(this, "No puedes mover una pieza del rival.");
            limpiarSeleccion();
            return;
        }

        if (filaSeleccionada == fila && columnaSeleccionada == columna) {
            piezaConfirmada = true;
            botonesTablero[fila][columna].setBorder(BorderFactory.createLineBorder(COLOR_SELECCION, 5));
            botonesTablero[fila][columna].setBorderPainted(true);
            return;
        }

        limpiarSeleccion();

        filaSeleccionada = fila;
        columnaSeleccionada = columna;

        botonesTablero[fila][columna].setBorder(BorderFactory.createLineBorder(COLOR_SELECCION, 4));
        botonesTablero[fila][columna].setBorderPainted(true);
    }

    private void seleccionarDestino(int fila, int columna) {
        if (fila == filaSeleccionada && columna == columnaSeleccionada) {
            piezaConfirmada = false;
            limpiarSeleccion();
            return;
        }

        if (filaDestinoSeleccionada == fila && columnaDestinoSeleccionada == columna) {
            moverPiezaSeleccionada(fila, columna);
            return;
        }

        limpiarDestino();

        filaDestinoSeleccionada = fila;
        columnaDestinoSeleccionada = columna;

        botonesTablero[fila][columna].setBorder(BorderFactory.createLineBorder(COLOR_DESTINO, 4));
        botonesTablero[fila][columna].setBorderPainted(true);
    }

    private void moverPiezaSeleccionada(int filaDestino, int columnaDestino) {
        try {
            Pieza pieza = partidaXiangqi.getTablero()[filaSeleccionada][columnaSeleccionada];

            String ultimoMovimiento = obtenerUsernamePorColor(pieza.getColor())
                    + " movio " + pieza.getNombre()
                    + " (" + filaSeleccionada + "," + columnaSeleccionada + ")"
                    + " → "
                    + "(" + filaDestino + "," + columnaDestino + ")";

            String respuesta = partidaXiangqi.mover(
                    filaSeleccionada,
                    columnaSeleccionada,
                    filaDestino,
                    columnaDestino
            );

            limpiarSeleccion();
            pintarPiezasIniciales();
            actualizarInformacion(ultimoMovimiento);

            if (respuesta.contains("venció") || respuesta.contains("retir")) {
                JOptionPane.showMessageDialog(this, respuesta);

                if (menusGUI != null) {
                    menusGUI.mostrarRankingJugadores();
                }

                dispose();
            }

        } catch (XiangqiException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            limpiarDestino();
        }
    }

    private void limpiarDestino() {
        if (filaDestinoSeleccionada != -1 && columnaDestinoSeleccionada != -1) {
            botonesTablero[filaDestinoSeleccionada][columnaDestinoSeleccionada].setBorder(null);
            botonesTablero[filaDestinoSeleccionada][columnaDestinoSeleccionada].setBorderPainted(false);
        }

        filaDestinoSeleccionada = -1;
        columnaDestinoSeleccionada = -1;
    }

    private void limpiarSeleccion() {
        if (filaSeleccionada != -1 && columnaSeleccionada != -1) {
            botonesTablero[filaSeleccionada][columnaSeleccionada].setBorder(null);
            botonesTablero[filaSeleccionada][columnaSeleccionada].setBorderPainted(false);
        }

        limpiarDestino();

        filaSeleccionada = -1;
        columnaSeleccionada = -1;
        piezaConfirmada = false;
    }

    private String obtenerUsernamePorColor(String color) {
        if (color.equalsIgnoreCase("Rojo")) {
            return partidaMenus.getJugadorRojo().getUsername();
        }

        return partidaMenus.getJugadorNegro().getUsername();
    }

    private void actualizarCapturas() {
        panelCapturasRojo.removeAll();
        panelCapturasNegro.removeAll();

        JLabel lblRojo = crearLabelInfo();
        lblRojo.setText("Rojo:");

        JLabel lblNegro = crearLabelInfo();
        lblNegro.setText("Negro:");

        panelCapturasRojo.add(lblRojo);
        agregarIconosCapturas(panelCapturasRojo, "Rojo");

        panelCapturasNegro.add(lblNegro);
        agregarIconosCapturas(panelCapturasNegro, "Negro");

        panelCapturasRojo.revalidate();
        panelCapturasRojo.repaint();
        panelCapturasNegro.revalidate();
        panelCapturasNegro.repaint();
    }

    private void agregarIconosCapturas(JPanel panel, String color) {
        String[] piezas = {"Canon", "Caballo", "Carro", "Elefante", "Oficial", "General", "Soldado"};

        for (int i = 0; i < piezas.length; i++) {
            String nombre = piezas[i];
            int total = obtenerTotalInicial(nombre);
            int actuales = contarPiezasActuales(color, nombre);
            int capturadas = total - actuales;

            JLabel lbl = new JLabel("(" + capturadas + "/" + total + ")");
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Open Sans", Font.PLAIN, 13));

            ImageIcon icono = cargarImagenPieza(obtenerNombreArchivoPieza(nombre, color), 22, 22);
            lbl.setIcon(icono);

            panel.add(lbl);
        }
    }

    private int contarPiezasActuales(String color, String nombre) {
        Pieza[][] tablero = partidaXiangqi.getTablero();
        int contador = 0;

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                Pieza pieza = tablero[fila][columna];

                if (pieza != null
                        && pieza.getColor().equalsIgnoreCase(color)
                        && pieza.getNombre().equalsIgnoreCase(nombre)) {
                    contador++;
                }
            }
        }

        return contador;
    }

    private int obtenerTotalInicial(String nombre) {
        if (nombre.equalsIgnoreCase("Soldado")) {
            return 5;
        }

        if (nombre.equalsIgnoreCase("General")) {
            return 1;
        }

        return 2;
    }

    private String obtenerNombreArchivoPieza(String nombre, String color) {
        String nombreArchivo = nombre.toLowerCase();

        if (nombreArchivo.equals("carro")) {
            nombreArchivo = "carroguerra";
        }

        return nombreArchivo + "_" + color.toLowerCase() + ".png";
    }

    private void pintarPiezasIniciales() {
        Pieza[][] tablero = partidaXiangqi.getTablero();

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                Pieza pieza = tablero[fila][columna];

                ImageIcon iconoGrid = cargarImagenGrid(GRID_IMAGENES[fila][columna], 52, 56);
                ImageIcon iconoPieza = null;

                if (pieza != null) {
                    iconoPieza = cargarImagenPieza(obtenerNombreImagenPieza(pieza), 42, 42);
                }

                botonesTablero[fila][columna].setIcon(new IconoCompuesto(iconoGrid, iconoPieza));
            }
        }
    }

    private ImageIcon cargarImagenPieza(String nombrePieza, int ancho, int alto) {
        String ruta = "/ashley/progra2/proyecto1/pkg2026/assets/" + nombrePieza;

        try {
            ImageIcon icono = new ImageIcon(TableroGUI.class.getResource(ruta));
            Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagen);
        } catch (Exception e) {
            return null;
        }
    }

    private String obtenerNombreImagenPieza(Pieza pieza) {
        String nombre = pieza.getNombre().toLowerCase();
        String color = pieza.getColor().toLowerCase();

        if (nombre.equals("carro")) {
            nombre = "carroguerra";
        }

        return nombre + "_" + color + ".png";
    }

    private ImageIcon cargarImagenGrid(int numero, int ancho, int alto) {
        String ruta = "/ashley/progra2/proyecto1/pkg2026/assets/grid_" + numero + ".png";

        try {
            ImageIcon icono = new ImageIcon(TableroGUI.class.getResource(ruta));
            Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagen);
        } catch (Exception e) {
            return null;
        }
    }

    private static class IconoCompuesto implements Icon {

        private ImageIcon fondo;
        private ImageIcon pieza;

        public IconoCompuesto(ImageIcon fondo, ImageIcon pieza) {
            this.fondo = fondo;
            this.pieza = pieza;
        }

        public int getIconWidth() {
            return fondo.getIconWidth();
        }

        public int getIconHeight() {
            return fondo.getIconHeight();
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            fondo.paintIcon(c, g, x, y);

            if (pieza != null) {
                int piezaX = x + (fondo.getIconWidth() - pieza.getIconWidth()) / 2;
                int piezaY = y + (fondo.getIconHeight() - pieza.getIconHeight()) / 2;
                pieza.paintIcon(c, g, piezaX, piezaY);
            }
        }
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
