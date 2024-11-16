import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class panelControl extends JPanel {
    private Pais pais;
    private TurnoListener turnoListener;

    public panelControl(Pais pais, List<Pais> todosLosPaises, TurnoListener turnoListener) {
        this.pais = pais;
        this.turnoListener = turnoListener; // Asignar referencia al listener

        setLayout(new GridBagLayout()); // Cambiar a GridBagLayout para un mejor control sobre la disposición
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Información del país y del jugador
        JLabel lblNombrePais = new JLabel("País: " + pais.getNombre());
        lblNombrePais.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombrePais.setFont(new Font("Arial", Font.BOLD, 20)); // Texto más grande
        JLabel lblJugador = new JLabel(pais.getJugador().getNombre() + " - Puntos: " + pais.getJugador().getPuntos());
        lblJugador.setHorizontalAlignment(SwingConstants.CENTER);
        lblJugador.setFont(new Font("Arial", Font.BOLD, 20)); // Texto más grande

        // Añadir etiquetas para el nombre del país y el jugador
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Se extiende por dos columnas
        add(lblNombrePais, gbc);

        gbc.gridy = 1;
        add(lblJugador, gbc);

        gbc.gridwidth = 1; // Restaura gridwidth para siguientes componentes

        // Información sobre armamento y botones para añadir armamento
        int row = 2; // Para mantener la fila actual

        // Tanques
        JLabel lblTanques = new JLabel("Tanques: " + pais.getTanques());
        lblTanques.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
        lblTanques.setFont(new Font("Arial", Font.BOLD, 18)); // Texto más grande
        JButton btnAñadirTanques = crearBotonRedondeado("Añadir Tanque (-1 punto)");

        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblTanques, gbc);

        gbc.gridx = 1;
        add(btnAñadirTanques, gbc);
        row++;

        // Aviones
        JLabel lblAviones = new JLabel("Aviones: " + pais.getAviones());
        lblAviones.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
        lblAviones.setFont(new Font("Arial", Font.BOLD, 18)); // Texto más grande
        JButton btnAñadirAviones = crearBotonRedondeado("Añadir Avion (-1 punto)");

        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblAviones, gbc);

        gbc.gridx = 1;
        add(btnAñadirAviones, gbc);
        row++;

        // Submarinos (solo si nivel de desarrollo es 3 o superior)
        if (pais.getNivelDesarrollo() >= 3) {
            JLabel lblSubmarinos = new JLabel("Submarinos: " + pais.getSubmarinos());
            lblSubmarinos.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
            lblSubmarinos.setFont(new Font("Arial", Font.BOLD, 18)); // Texto más grande
            JButton btnAñadirSubmarinos = crearBotonRedondeado("Añadir Submarino (-1 punto)");

            gbc.gridx = 0;
            gbc.gridy = row;
            add(lblSubmarinos, gbc);

            gbc.gridx = 1;
            add(btnAñadirSubmarinos, gbc);
            row++;

            // Listener para añadir submarinos
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
        }

        // Bombas Nucleares (solo si nivel de desarrollo es 4)
        if (pais.getNivelDesarrollo() == 4) {
            JLabel lblBombas = new JLabel("Armas Nucleares: " + pais.getArmasNucleares());
            lblBombas.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
            lblBombas.setFont(new Font("Arial", Font.BOLD, 18)); // Texto más grande
            JButton btnAñadirArmasNucleares = crearBotonRedondeado("Añadir Bomba Nuclear (-10 puntos)");

            gbc.gridx = 0;
            gbc.gridy = row;
            add(lblBombas, gbc);

            gbc.gridx = 1;
            add(btnAñadirArmasNucleares, gbc);
            row++;

            // Listener para añadir bombas nucleares
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
        }

        // Nivel de destrucción
        JLabel lblNivelDestruccion = new JLabel("Nivel de Destrucción: " + pais.getPorcentajeDestruccion() + "%");
        lblNivelDestruccion.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
        lblNivelDestruccion.setFont(new Font("Arial", Font.BOLD, 18)); // Texto más grande
        JButton btnReparar = crearBotonRedondeado("Reparar (-1 punto por 2%)");

        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblNivelDestruccion, gbc);

        gbc.gridx = 1;
        add(btnReparar, gbc);
        row++;

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

        comboBoxPaisesEnemigos.setFont(new Font("Arial", Font.PLAIN, 18));
        comboBoxPaisesEnemigos.setBackground(Color.LIGHT_GRAY);
        comboBoxPaisesEnemigos.setPreferredSize(new Dimension(300, 40));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1; // ComboBox de países ocupa una columna
        add(comboBoxPaisesEnemigos, gbc);

        // ComboBox para seleccionar las armas disponibles
        JComboBox<String> comboBoxArmasDisponibles = new JComboBox<>();
        if (pais.getTanques() > 0) {
            comboBoxArmasDisponibles.addItem("Tanques");
        }
        if (pais.getNivelDesarrollo() >= 2 && pais.getAviones() > 0) {
            comboBoxArmasDisponibles.addItem("Aviones");
        }
        if (pais.getNivelDesarrollo() >= 3 && pais.getSubmarinos() > 0) {
            comboBoxArmasDisponibles.addItem("Submarinos");
        }
        if (pais.getNivelDesarrollo() == 4 && pais.getArmasNucleares() > 0) {
            comboBoxArmasDisponibles.addItem("Bombas Nucleares");
        }

        comboBoxArmasDisponibles.setFont(new Font("Arial", Font.PLAIN, 18));
        comboBoxArmasDisponibles.setBackground(Color.LIGHT_GRAY);
        comboBoxArmasDisponibles.setPreferredSize(new Dimension(300, 40));

        gbc.gridx = 1;
        add(comboBoxArmasDisponibles, gbc);
        row++;

        // Botón de acción para atacar
        JButton btnAtacar = crearBotonRedondeado("Atacar");
        btnAtacar.setPreferredSize(new Dimension(620, 50)); // Botón ocupa todo el ancho del panel
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2; // Botón de atacar ocupa dos columnas
        add(btnAtacar, gbc);

        // Listeners para las acciones de los botones
        btnAtacar.addActionListener(e -> {
            String paisObjetivoString = (String) comboBoxPaisesEnemigos.getSelectedItem();
            String armaSeleccionada = (String) comboBoxArmasDisponibles.getSelectedItem();

            if (paisObjetivoString != null && armaSeleccionada != null) {
                // Obtener país objetivo
                Pais paisObjetivo = todosLosPaises.stream()
                        .filter(p -> paisObjetivoString.contains(p.getNombre()))
                        .findFirst()
                        .orElse(null);

                if (paisObjetivo != null) {
                    int daño = 0;
                    double porcentajePerdida = 0.0;

                    switch (paisObjetivo.getNivelDesarrollo()) {
                        case 4:
                            porcentajePerdida = 0.50; // Nivel 4, perder 50%
                            break;
                        case 3:
                            porcentajePerdida = 0.25; // Nivel 3, perder 25%
                            break;
                        case 2:
                            porcentajePerdida = 0.10; // Nivel 2, perder 10%
                            break;
                        case 1:
                            porcentajePerdida = 0.0;  // Nivel 1, no se pierde nada
                            break;
                        default:
                            // Si hay algún nivel inesperado, podrías asignar un valor por defecto
                            porcentajePerdida = 0.0;
                            break;
                    }


                    switch (armaSeleccionada) {
                        case "Tanques": {
                            int tanquesIniciales = pais.getTanques();
                            int tanquesUtilizados = (int) Math.ceil(tanquesIniciales * (1 - porcentajePerdida));
                            daño = tanquesUtilizados;
                            pais.setTanques(-tanquesIniciales + tanquesUtilizados); // Actualizar la cantidad de tanques restantes
                            break;
                        }
                        case "Aviones": {
                            int avionesIniciales = pais.getAviones();
                            int avionesUtilizados = (int) Math.ceil(avionesIniciales * (1 - porcentajePerdida));
                            daño = avionesUtilizados;
                            pais.setAviones(-avionesIniciales + avionesUtilizados); // Actualizar la cantidad de aviones restantes
                            break;
                        }
                        case "Submarinos": {
                            int submarinosIniciales = pais.getSubmarinos();
                            int submarinosUtilizados = (int) Math.ceil(submarinosIniciales * (1 - porcentajePerdida));
                            daño = submarinosUtilizados;
                            pais.setSubmarinos(-submarinosIniciales + submarinosUtilizados); // Actualizar la cantidad de submarinos restantes
                            break;
                        }
                        case "Bombas Nucleares": {
                            daño = 20;
                            pais.setArmasNucleares(-1); // Usar una bomba nuclear
                            break;
                        }
                        default: {
                            // Puedes agregar algún comportamiento predeterminado aquí si es necesario.
                            break;
                        }
                    }


                    // Aplicar el daño al país objetivo
                    paisObjetivo.recibirAtaque(daño);
                    actualizarArmamentos(lblNivelDestruccion, "Nivel de Destrucción: " + paisObjetivo.getPorcentajeDestruccion() + "%");

                    // Mensaje de ataque inicial
                    String mensajeAtaque = pais.getNombre() + " ha atacado a " + paisObjetivo.getNombre() + " con " + armaSeleccionada + ". Nivel de destrucción: " + paisObjetivo.getPorcentajeDestruccion() + "%";

                    // Si el país ha sido destruido
                    if (paisObjetivo.getPorcentajeDestruccion() >= 100) {
                        mensajeAtaque += " y " + paisObjetivo.getNombre() + " ha quedado destruido. ";
                        Jugador jugadorDerrotado = paisObjetivo.getJugador();
                        actualizarEstadoPaisDestruido(paisObjetivo);

                        // Comprobar si el jugador del país objetivo ya no tiene más países
                        if (jugadorDerrotado != null && jugadorDerrotado.getPaises().isEmpty()) {
                            mensajeAtaque = "El "+ jugadorDerrotado.getNombre() + " ha perdido todos sus países y ha sido eliminado del juego.";
                        }
                    }

                    // Mostrar el banner de ataque
                    turnoListener.mostrarBannerAtaque(mensajeAtaque);

                    // Pasar el turno automáticamente después del ataque
                    turnoListener.pasarTurno();
                }
            }
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

        // Listener para reparar el país
        btnReparar.addActionListener(e -> {
            if (pais.getJugador().getPuntos() >= 1 && pais.getPorcentajeDestruccion() > 0) {
                int porcentajeReparar = Math.min(2, pais.getPorcentajeDestruccion()); // Reparar solo un 2% por punto
                pais.repararDestruccion(porcentajeReparar);
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

    // Método para crear un botón redondeado y más alargado para acomodar el texto
    private JButton crearBotonRedondeado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(Color.LIGHT_GRAY);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setPreferredSize(new Dimension(300, 50)); // Botones más alargados
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

        boton.setMinimumSize(new Dimension(300, 50));
        boton.setMaximumSize(new Dimension(300, 50));
        boton.setPreferredSize(new Dimension(300, 50));
        return boton;
    }

    // Método para actualizar la información de armamentos en la etiqueta correspondiente
    private void actualizarArmamentos(JLabel label, String text) {
        label.setText(text);
    }

    private void actualizarEstadoPaisDestruido(Pais paisDestruido) {
        // Quitar el país de la lista del jugador
        Jugador jugador = paisDestruido.getJugador();
        if (jugador != null) {
            jugador.getPaises().remove(paisDestruido);
        }

        // Quitar la relación de pertenencia del país
        paisDestruido.setJugador(null);

        // Cambiar el color del país en la interfaz
        // Aquí asumimos que existe un método para actualizar la interfaz del país
        turnoListener.actualizarColorPais(paisDestruido);
    }
    // Método para actualizar la información del jugador en la etiqueta correspondiente
    private void actualizarJugadorInfo(JLabel lblJugador) {
        lblJugador.setText(pais.getJugador().getNombre() + " - Puntos: " + pais.getJugador().getPuntos());
    }
}
