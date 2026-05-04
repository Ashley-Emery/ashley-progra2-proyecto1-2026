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

    private JButton[][] botonesTablero;
    private JLabel lblPartida;
    private JLabel lblTurno;
    private JLabel lblEstado;
    private JLabel lblUltimoMovimiento;
    private JLabel lblCapturas;
    private JLabel lblIdPartida;

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

    public TableroGUI(Menus menus, Menus.Partida partidaMenus) {
        this.menus = menus;
        this.partidaMenus = partidaMenus;

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
        cajaInfo.setPreferredSize(new Dimension(430, 210));

        lblPartida = crearLabelInfo();
        lblTurno = crearLabelInfo();
        lblEstado = crearLabelInfo();
        lblUltimoMovimiento = crearLabelInfo();
        lblCapturas = crearLabelInfo();

        cajaInfo.add(lblPartida);
        cajaInfo.add(Box.createVerticalStrut(12));
        cajaInfo.add(lblTurno);
        cajaInfo.add(lblEstado);
        cajaInfo.add(Box.createVerticalStrut(12));
        cajaInfo.add(lblUltimoMovimiento);
        cajaInfo.add(Box.createVerticalStrut(12));
        cajaInfo.add(lblCapturas);

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
        gbc.insets = new Insets(0, 0, 12, 0);
        panelInfo.add(cajaInfo, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 70, 0);
        panelInfo.add(lblIdPartida, gbc);

        gbc.gridy = 3;
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

        lblTurno.setText("Turno: " + partidaXiangqi.getTurnoColor());
        lblEstado.setText("Estado: " + partidaXiangqi.getEstado());

        lblUltimoMovimiento.setText("<html>[ Ultimo movimiento ]<br>" + ultimoMovimiento + "</html>");

        lblCapturas.setText("<html>[ Capturas ]<br>Rojo: pendiente<br>Negro: pendiente</html>");

        lblIdPartida.setText("[ ID: " + partidaMenus.getIdPartida() + " ]");
    }

    private void abandonarPartida() {
        try {
            String respuesta = partidaXiangqi.retirar(partidaXiangqi.getTurnoColor());
            JOptionPane.showMessageDialog(this, respuesta);
            actualizarInformacion("Jugador " + partidaXiangqi.getTurnoColor() + " abandonó la partida.");
        } catch (XiangqiException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
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
