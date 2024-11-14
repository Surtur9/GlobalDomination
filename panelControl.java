import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class panelControl extends JPanel {
    private Pais pais;

    public panelControl(Pais pais, List<Pais> todosLosPaises) {
        this.pais = pais;

        setLayout(new GridLayout(12, 2, 10, 10)); // Ajustar la cuadrícula para mostrar la información correctamente, con espacio entre componentes

        // Información del país y del jugador
        JLabel lblNombrePais = new JLabel("País: " + pais.getNombre());
        lblNombrePais.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel lblJugador = new JLabel(pais.getJugador().getNombre() + " - Puntos: " + pais.getJugador().getPuntos());
        lblJugador.setHorizontalAlignment(SwingConstants.CENTER);

        // Añadir etiquetas para el nombre del país y el jugador
        add(lblNombrePais);
        add(lblJugador);

        // Información sobre armamento y botones para añadir armamento
        // Tanques
        JLabel lblTanques = new JLabel("Tanques: " + pais.getTanques());
        lblTanques.setHorizontalAlignment(SwingConstants.LEFT);
        JButton btnAñadirTanques = crearBotonRedondeado("Añadir Tanque (-1 punto)");

        // Aviones
        JLabel lblAviones = new JLabel("Aviones: " + pais.getAviones());
        lblAviones.setHorizontalAlignment(SwingConstants.LEFT);
        JButton btnAñadirAviones = crearBotonRedondeado("Añadir Avion (-1 punto)");

        // Submarinos
        JLabel lblSubmarinos = new JLabel("Submarinos: " + pais.getSubmarinos());
        lblSubmarinos.setHorizontalAlignment(SwingConstants.LEFT);
        JButton btnAñadirSubmarinos = crearBotonRedondeado("Añadir Submarino (-1 punto)");

        // Bombas Nucleares
        JLabel lblBombas = new JLabel("Armas Nucleares: " + pais.getArmasNucleares());
        lblBombas.setHorizontalAlignment(SwingConstants.LEFT);
        JButton btnAñadirArmasNucleares = crearBotonRedondeado("Añadir Bomba Nuclear (-10 puntos)");

        // Nivel de destrucción
        JLabel lblNivelDestruccion = new JLabel("Nivel de Destrucción: " + pais.getPorcentajeDestruccion() + "%");
        lblNivelDestruccion.setHorizontalAlignment(SwingConstants.LEFT);
        JButton btnReparar = crearBotonRedondeado("Reparar (-1 punto por 10%)");

        // ComboBox para seleccionar países enemigos con la información del jugador al que pertenecen
        JComboBox<String> comboBoxPaisesEnemigos = new JComboBox<>();
        List<String> paisesEnemigos = todosLosPaises.stream()
                .filter(p -> !p.getNombre().equals(pais.getNombre())) // Filtrar para que no aparezca el país del jugador actual
                .filter(p -> !p.getJugador().equals(pais.getJugador())) // Filtrar para que no aparezcan los países del mismo jugador
                .map(p -> p.getNombre() + " (" + p.getJugador().getNombre() + ")")
                .collect(Collectors.toList());
        for (String nombrePais : paisesEnemigos) {
            comboBoxPaisesEnemigos.addItem(nombrePais);
        }

        // Botón de acción para atacar
        JButton btnAtacar = crearBotonRedondeado("Atacar");

        // Añadir componentes al panel
        add(lblTanques);
        add(btnAñadirTanques);
        add(lblAviones);
        add(btnAñadirAviones);
        add(lblSubmarinos);
        add(btnAñadirSubmarinos);
        add(lblBombas);
        add(btnAñadirArmasNucleares);
        add(lblNivelDestruccion);
        add(btnReparar);
        add(comboBoxPaisesEnemigos);
        add(btnAtacar);

        // Listeners para las acciones de los botones
        btnAtacar.addActionListener(e -> {
            String paisObjetivo = (String) comboBoxPaisesEnemigos.getSelectedItem();
            System.out.println("Atacar presionado para el país: " + pais.getNombre() + " contra " + paisObjetivo);
        });

        // Listeners para añadir armamentos
        btnAñadirTanques.addActionListener(e -> {
            if (pais.getJugador().getPuntos() >= 1) {
                pais.setTanques(1);
                pais.getJugador().gastarPuntos(1);
                actualizarArmamentos(lblTanques, "Tanques: " + pais.getTanques());
                actualizarJugadorInfo(lblJugador);
            } else {
                JOptionPane.showMessageDialog(this, "No tienes suficientes puntos para añadir un tanque.");
            }
        });

        btnAñadirAviones.addActionListener(e -> {
            if (pais.getJugador().getPuntos() >= 1) {
                pais.setAviones(1);
                pais.getJugador().gastarPuntos(1);
                actualizarArmamentos(lblAviones, "Aviones: " + pais.getAviones());
                actualizarJugadorInfo(lblJugador);
            } else {
                JOptionPane.showMessageDialog(this, "No tienes suficientes puntos para añadir un avión.");
            }
        });

        btnAñadirSubmarinos.addActionListener(e -> {
            if (pais.getJugador().getPuntos() >= 1) {
                pais.setSubmarinos(1);
                pais.getJugador().gastarPuntos(1);
                actualizarArmamentos(lblSubmarinos, "Submarinos: " + pais.getSubmarinos());
                actualizarJugadorInfo(lblJugador);
            } else {
                JOptionPane.showMessageDialog(this, "No tienes suficientes puntos para añadir un submarino.");
            }
        });

        btnAñadirArmasNucleares.addActionListener(e -> {
            if (pais.getJugador().getPuntos() >= 10) {
                pais.setArmasNucleares(1);
                pais.getJugador().gastarPuntos(10);
                actualizarArmamentos(lblBombas, "Armas Nucleares: " + pais.getArmasNucleares());
                actualizarJugadorInfo(lblJugador);
            } else {
                JOptionPane.showMessageDialog(this, "No tienes suficientes puntos para añadir una bomba nuclear.");
            }
        });

        // Listener para reparar el país
        btnReparar.addActionListener(e -> {
            if (pais.getJugador().getPuntos() >= 1 && pais.getPorcentajeDestruccion() > 0) {
                int puntosReparar = Math.min(10, pais.getPorcentajeDestruccion());
                pais.repararDestruccion(puntosReparar);
                pais.getJugador().gastarPuntos(1);
                actualizarArmamentos(lblNivelDestruccion, "Nivel de Destrucción: " + pais.getPorcentajeDestruccion() + "%");
                actualizarJugadorInfo(lblJugador);
            } else if (pais.getPorcentajeDestruccion() == 0) {
                JOptionPane.showMessageDialog(this, "El país no necesita reparaciones.");
            } else {
                JOptionPane.showMessageDialog(this, "No tienes suficientes puntos para reparar el país.");
            }
        });
    }

    // Método para crear un botón redondeado
    private JButton crearBotonRedondeado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(Color.LIGHT_GRAY);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false); // Quitar el borde negro
        boton.setUI(new javax.swing.plaf.metal.MetalButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.DARK_GRAY);
                g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), 30, 30);
                g2.dispose();
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(boton.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 30, 30);
                super.paint(g2, c);
                g2.dispose();
            }
        });

        boton.setMinimumSize(new Dimension(150, 40));
        boton.setMaximumSize(new Dimension(150, 40));
        boton.setPreferredSize(new Dimension(150, 40));
        return boton;
    }

    // Método para actualizar la información de armamentos en la etiqueta correspondiente
    private void actualizarArmamentos(JLabel label, String text) {
        label.setText(text);
    }

    // Método para actualizar la información del jugador en la etiqueta correspondiente
    private void actualizarJugadorInfo(JLabel lblJugador) {
        lblJugador.setText(pais.getJugador().getNombre() + " - Puntos: " + pais.getJugador().getPuntos());
    }
}
