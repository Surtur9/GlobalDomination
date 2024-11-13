import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private List<Pais> paises;
    private int puntos;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.paises = new ArrayList<>();
        this.puntos = 0;
    }

    public void añadirPais(Pais pais) {
        paises.add(pais);
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public int getPuntos() {
        return puntos;
    }

    public void añadirPuntos(int puntos) {
        this.puntos += puntos;
    }

    public void gastarPuntos(int cantidad) {
        if (cantidad <= puntos) {
            puntos -= cantidad;
        } else {
            System.out.println("No tienes suficientes puntos.");
        }
    }

    // Otros métodos necesarios
}
