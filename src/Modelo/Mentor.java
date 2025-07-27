package Modelo;

public class Mentor {
    private int mentorId;      // ID en la tabla 'mentores'
    private int personaId;     // ID en la tabla 'personas'
    private String nombres;
    private String apellidos;
    private String especialidad;

    // Constructor
    public Mentor(int mentorId, int personaId, String nombres, String apellidos, String especialidad) {
        this.mentorId = mentorId;
        this.personaId = personaId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
    }

    // Devuelve el ID de mentor
    public int getId() {
        return mentorId;
    }

    // Devuelve el ID de persona
    public int getPersonaId() {
        return personaId;
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
        return mentorId + " - " + getNombreCompleto() + " (" + especialidad + ")";
    }
}
