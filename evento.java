public class evento {
    private String tipo;
    private int porcentajeDestruccion;

    public evento(String tipo, int porcentajeDestruccion) {
        this.tipo = tipo;
        this.porcentajeDestruccion = porcentajeDestruccion;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPorcentajeDestruccion() {
        return porcentajeDestruccion;
    }

    public void aplicarEvento(Pais pais) {
        pais.recibirAtaque(porcentajeDestruccion);
        System.out.println(tipo + " destruyó " + porcentajeDestruccion + "% de " + pais.getNombre());
    }
}

