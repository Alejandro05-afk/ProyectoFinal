package Modelo;

/**
 * Representa a un mentor del sistema, incluyendo información personal y su especialidad.
 */
public class Mentor {
    private int mentorId;      // ID en la tabla 'mentores'
    private int personaId;     // ID en la tabla 'personas'
    private String nombres;
    private String apellidos;
    private String especialidad;

    /**
     * Constructor para inicializar un mentor.
     * @param mentorId ID del mentor en la tabla 'mentores'.
     * @param personaId ID asociado en la tabla 'personas'.
     * @param nombres Nombres del mentor.
     * @param apellidos Apellidos del mentor.
     * @param especialidad Especialidad del mentor.
     */
    public Mentor(int mentorId, int personaId, String nombres, String apellidos, String especialidad) {
        this.mentorId = mentorId;
        this.personaId = personaId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
    }

    /**
     * Obtiene el ID del mentor.
     * @return ID del mentor.
     */
    public int getId() {
        return mentorId;
    }

    /**
     * Obtiene el ID de la persona asociada.
     * @return ID de la persona.
     */
    public int getPersonaId() {
        return personaId;
    }

    /**
     * Obtiene los nombres del mentor.
     * @return nombres del mentor.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Obtiene los apellidos del mentor.
     * @return apellidos del mentor.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Obtiene la especialidad del mentor.
     * @return especialidad del mentor.
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Devuelve el nombre completo del mentor.
     * @return nombre completo en formato "nombres apellidos".
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    /**
     * Representación en texto del mentor.
     * @return cadena con ID, nombre completo y especialidad.
     */
    @Override
    public String toString() {
        return mentorId + " - " + getNombreCompleto() + " (" + especialidad + ")";
    }
}
