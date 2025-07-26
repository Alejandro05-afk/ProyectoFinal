package Modelo;

public class Mentor {
    private int id;
    private String nombres;
    private String apellidos;
    private String especialidad;

    public Mentor(int id, String nombres, String apellidos, String especialidad) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
    }

    public int getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    @Override
    public String toString() {
        return id + " - " + getNombreCompleto() + " (" + especialidad + ")";
    }
}
