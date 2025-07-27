package Modelo;

public class FaseProyecto {
    private int id;
    private String nombre;

    public FaseProyecto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
}
