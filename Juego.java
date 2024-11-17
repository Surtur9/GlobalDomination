import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Juego {
    private List<Jugador> jugadores;
    private List<Pais> paises;
    private int turnoActual;
    private TurnoListener ventanaJuegoListener;

    public Juego(int numeroJugadores) {
        jugadores = new ArrayList<>();
        paises = generarPaises();
        asignarPaisesJugadores(numeroJugadores);
        turnoActual = 0; // Inicia con el primer jugador
    }

    private List<Pais> generarPaises() {
        List<Pais> paises = new ArrayList<>();

        // Tier 4 - SUPERPOTENCIAS
        paises.add(new Pais("EEUU", true, 4, 50, 50, 50, 5));
        paises.add(new Pais("China", true, 4, 50, 50, 50, 5));
        paises.add(new Pais("Rusia", true, 4, 50, 50, 50, 5));
        paises.add(new Pais("Reino Unido", true, 4, 50, 50, 50, 5));
        paises.add(new Pais("Francia", true, 4, 50, 50, 50, 5));
        paises.add(new Pais("Pakistán", false, 4, 50, 50, 50, 5));
        paises.add(new Pais("Israel", false, 4, 50, 50, 50, 5));
        paises.add(new Pais("India", true, 4, 50, 50, 50, 5));

        // Tier 3 - POTENCIAS
        paises.add(new Pais("España", true, 3, 40, 40, 40, 0));
        paises.add(new Pais("Alemania", false, 3, 40, 40, 40, 0));
        paises.add(new Pais("Marruecos", true, 3, 40, 40, 40, 0));
        paises.add(new Pais("Japón", true, 3, 40, 40, 40, 0));
        paises.add(new Pais("Ucrania", false, 3, 40, 40, 40, 0));
        paises.add(new Pais("Australia", true, 3, 40, 40, 40, 0));
        paises.add(new Pais("Canadá", true, 3, 40, 40, 40, 0));
        paises.add(new Pais("Corea del Norte", false, 3, 40, 40, 40, 0));

        // Tier 2 - FORTALEZAS
        paises.add(new Pais("Siria", false, 2, 30, 30, 0, 0));
        paises.add(new Pais("Turquía", true, 2, 30, 30, 0, 0));
        paises.add(new Pais("Corea del Sur", false, 2, 30, 30, 0, 0));
        paises.add(new Pais("Egipto", true, 2, 30, 30, 0, 0));
        paises.add(new Pais("Grecia", true, 2, 30, 30, 0, 0));
        paises.add(new Pais("Arabia Saudita", true, 2, 30, 30, 0, 0));
        paises.add(new Pais("Bélgica", false, 2, 30, 30, 0, 0));
        paises.add(new Pais("Argentina", true, 2, 30, 30, 0, 0));
        paises.add(new Pais("Brasil", true, 2, 30, 30, 0, 0));

        // Tier 1 - ASPIRANTES
        //paises.add(new Pais("África del Este", false, 1, 20, 0, 0, 0));
        //paises.add(new Pais("Sudáfrica", true, 1, 20, 0, 0, 0));
        //paises.add(new Pais("África del Oeste", false, 1, 20, 0, 0, 0));
        //paises.add(new Pais("África del Norte", true, 1, 20, 0, 0, 0));

        return paises;
    }

    private void asignarPaisesJugadores(int numeroJugadores) {
        Random rand = new Random();
        for (int i = 0; i < numeroJugadores; i++) {
            jugadores.add(new Jugador("Jugador " + (i + 1)));
        }

        // Separar los países por tier
        List<Pais> tier4 = new ArrayList<>();
        List<Pais> tier3 = new ArrayList<>();
        List<Pais> tier2 = new ArrayList<>();
        List<Pais> tier1 = new ArrayList<>();

        for (Pais pais : paises) {
            switch (pais.getNivelDesarrollo()) {
                case 4 -> tier4.add(pais);
                case 3 -> tier3.add(pais);
                case 2 -> tier2.add(pais);
                case 1 -> tier1.add(pais);
            }
        }

        // Distribuir los países de manera equilibrada por tier
        distribuirPaisesPorTier(tier4, numeroJugadores);
        distribuirPaisesPorTier(tier3, numeroJugadores);
        distribuirPaisesPorTier(tier2, numeroJugadores);
        distribuirPaisesPorTier(tier1, numeroJugadores);
    }

    private void distribuirPaisesPorTier(List<Pais> paisesPorTier, int numeroJugadores) {
        int indexJugador = 0;
        for (Pais pais : paisesPorTier) {
            Jugador jugador = jugadores.get(indexJugador);
            jugador.añadirPais(pais);
            pais.setJugador(jugador); // Asociar el país con el jugador
            indexJugador = (indexJugador + 1) % numeroJugadores; // Circular para asegurar distribución equitativa
        }
    }

    public void iniciar() {
        System.out.println("¡El juego ha comenzado!");
        mostrarEstadoJugadores();
        // Aquí puedes agregar lógica adicional para iniciar el flujo del juego (turnos, etc.)
    }

    private void mostrarEstadoJugadores() {
        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + " posee los siguientes países:");
            for (Pais pais : jugador.getPaises()) {
                System.out.println(" - " + pais.getNombre() + " (Tier " + pais.getNivelDesarrollo() + ")");
            }
        }
    }

    //  Metodo para verificar si un jugador tiene países
    private boolean jugadorTienePaises(Jugador jugador) {
        return !jugador.getPaises().isEmpty();
    }

    // Modificación del metodo pasarTurno
    public void pasarTurno() {
        int turnoAnterior = turnoActual;

        do {
            // Actualizar turno actual
            turnoActual = (turnoActual + 1) % jugadores.size();
            Jugador jugadorActual = jugadores.get(turnoActual);

            // Si el jugador actual no tiene países, eliminar al jugador
            if (!jugadorTienePaises(jugadorActual)) {
                eliminarJugador(jugadorActual);

                // Ajustar el índice turnoActual ya que se ha eliminado un jugador
                if (turnoActual >= jugadores.size()) {
                    turnoActual = 0;
                }
            }

            // Verificar si queda solo un jugador después de la eliminación
            if (jugadores.size() == 1) {
                if (ventanaJuegoListener != null) {
                    ventanaJuegoListener.jugadorGano(jugadores.get(0).getNombre() + " ha ganado el juego.");
                }
                return; // Finalizar el metodo, ya que el juego ha terminado
            }

        } while (!jugadorTienePaises(jugadores.get(turnoActual)) && !jugadores.isEmpty());

        // Verificar si solo queda un jugador después del ciclo
        if (jugadores.size() == 1) {
            if (ventanaJuegoListener != null) {
                ventanaJuegoListener.jugadorGano(jugadores.get(0).getNombre() + " ha ganado el juego.");
            }
            return; // Finalizar el método
        }

        // Imprimir el turno actual si hay más de un jugador restante
        if (!jugadores.isEmpty()) {
            System.out.println("Es el turno de " + getJugadorActual().getNombre());
        }
    }

    public void eliminarJugador(Jugador jugador) {
        if (jugador != null && jugadores.contains(jugador)) {
            if (ventanaJuegoListener != null) {
                //ventanaJuegoListener.jugadorPerdio(jugador.getNombre() + " se ha quedado sin países y ha perdido.");
            }

            jugadores.remove(jugador);

            // Ajustar el turno actual si el jugador eliminado era el turno actual
            if (turnoActual >= jugadores.size()) {
                turnoActual = 0;
            }
        }
    }
    public int getTurnoActual() {
        return turnoActual;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public Jugador getJugadorDePais(Pais pais) {
        return pais.getJugador();
    }

    // Nuevo metodo para obtener la lista de jugadores
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    // Nuevo metodo para obtener la lista de países
    public List<Pais> getPaises() {
        return paises.stream()
                .filter(pais -> pais.getJugador() != null)
                .collect(Collectors.toList());
    }

    // Metodo para asignar el listener de la ventanaJuego para notificar eventos
    public void setVentanaJuegoListener(TurnoListener listener) {
        this.ventanaJuegoListener = listener;
    }
}
