public class Pais {
    private String nombre;
    private Jugador jugador;
    private boolean tieneCostas;
    private int nivelDesarrollo;
    private int porcentajeDestruccion;
    private int tanques;
    private int aviones;
    private int submarinos;
    private int armasNucleares;

    // Constructor actualizado
    public Pais(String nombre, boolean tieneCostas, int nivelDesarrollo, int tanques, int aviones, int submarinos, int armasNucleares) {
        this.nombre = nombre;
        this.tieneCostas = tieneCostas;
        this.nivelDesarrollo = nivelDesarrollo;
        this.porcentajeDestruccion = 0;
        this.tanques = tanques;
        this.aviones = aviones;
        this.submarinos = submarinos;
        this.armasNucleares = armasNucleares;
    }

    public String getNombre() {
        return nombre;
    }
    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }
    public boolean tieneCostas() {
        return tieneCostas;
    }

    public int getNivelDesarrollo() {
        return nivelDesarrollo;
    }

    public int getPorcentajeDestruccion() {
        return porcentajeDestruccion;
    }

    public void aumentarDestruccion(int porcentaje) {
        this.porcentajeDestruccion += porcentaje;
        if (this.porcentajeDestruccion > 100) {
            this.porcentajeDestruccion = 100; // Limitar el porcentaje de destrucción a un máximo de 100%
        }
    }

    // Getters y setters para los armamentos
    public int getTanques() {
        return tanques;
    }
    public void setTanques(int suma) {
        tanques += suma;
    }

    public int getAviones() {
        return aviones;
    }
    public void setAviones(int suma) {
        aviones += suma;
    }

    public int getSubmarinos() {
        return submarinos;
    }
    public void setSubmarinos(int suma) {
        submarinos += suma;
    }

    public int getArmasNucleares() {
        return armasNucleares;
    }
    public void setArmasNucleares(int suma) {
        this.armasNucleares += suma;
    }
    // Método para obtener una descripción de los armamentos
    public String getDescripcionArmamentos() {
        return "Tanques: " + tanques + ", Aviones: " + aviones + ", Submarinos: " + submarinos + "Armas nucleares:" + armasNucleares;
    }
}
