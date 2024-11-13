import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego {
    private List<Jugador> jugadores;
    private List<Pais> paises;

    public Juego(int numeroJugadores) {
        jugadores = new ArrayList<>();
        paises = generarPaises();
        asignarPaisesJugadores(numeroJugadores);
    }

    private List<Pais> generarPaises() {
        List<Pais> paises = new ArrayList<>();

        // Tier 4 - SUPERPOTENCIAS
        paises.add(new Pais("EEUU", true, true, 4));
        paises.add(new Pais("China", true, true, 4));
        paises.add(new Pais("Rusia", true, true, 4));
        paises.add(new Pais("Reino Unido", true, true, 4));
        paises.add(new Pais("Francia", true, true, 4));
        paises.add(new Pais("Pakistán", false, true, 4));
        paises.add(new Pais("Israel", false, true, 4));
        paises.add(new Pais("India", true, true, 4));

        // Tier 3 - POTENCIAS
        paises.add(new Pais("España", true, false, 3));
        paises.add(new Pais("Alemania", false, false, 3));
        paises.add(new Pais("Marruecos", true, false, 3));
        paises.add(new Pais("Japón", true, false, 3));
        paises.add(new Pais("Ucrania", false, false, 3));
        paises.add(new Pais("Australia", true, false, 3));
        paises.add(new Pais("Canadá", true, false, 3));
        paises.add(new Pais("Corea del Norte", false, true, 3));

        // Tier 2 - FORTALEZAS
        paises.add(new Pais("Siria", false, false, 2));
        paises.add(new Pais("Turquía", true, false, 2));
        paises.add(new Pais("Corea del Sur", false, false, 2));
        paises.add(new Pais("Egipto", true, false, 2));
        paises.add(new Pais("Grecia", true, false, 2));
        paises.add(new Pais("Arabia Saudita", true, false, 2));
        paises.add(new Pais("Bélgica", false, false, 2));
        paises.add(new Pais("Japón", true, false, 2));
        paises.add(new Pais("Argentina", true, false, 2));
        paises.add(new Pais("Brasil", true, false, 2));

        // Tier 1 - ASPIRANTES
        paises.add(new Pais("África del Este", false, false, 1));
        paises.add(new Pais("Sudáfrica", true, false, 1));
        paises.add(new Pais("África del Oeste", false, false, 1));
        paises.add(new Pais("África del Norte", true, false, 1));

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
            jugadores.get(indexJugador).añadirPais(pais);
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

    // Nuevo método para obtener la lista de jugadores
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    // Nuevo método para obtener la lista de países
    public List<Pais> getPaises() {
        return paises;
    }
}
