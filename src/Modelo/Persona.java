package Modelo;

/**
 * Representa una persona con datos personales básicos.
 */
public class Persona {
    private int id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String direccion;

    /**
     * Constructor vacío. Útil para frameworks o cuando se asignarán valores más tarde.
     */
    public Persona() {}

    /**
     * Constructor con todos los campos excepto el ID.
     * @param nombres Nombres de la persona.
     * @param apellidos Apellidos de la persona.
     * @param correo Correo electrónico.
     * @param telefono Número de teléfono.
     * @param direccion Dirección de residencia.
     */
    public Persona(String nombres, String apellidos, String correo, String telefono, String direccion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /**
     * Obtiene el ID de la persona.
     * @return ID único de la persona.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la persona.
     * @param id ID único de la persona.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene los nombres de la persona.
     * @return Nombres.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece los nombres de la persona.
     * @param nombres Nombres.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos de la persona.
     * @return Apellidos.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos de la persona.
     * @param apellidos Apellidos.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el correo electrónico de la persona.
     * @return Correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico de la persona.
     * @param correo Correo electrónico.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el número de teléfono de la persona.
     * @return Teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono de la persona.
     * @param telefono Teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección de residencia de la persona.
     * @return Dirección.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de residencia de la persona.
     * @param direccion Dirección.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
