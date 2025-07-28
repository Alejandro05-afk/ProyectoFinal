package Modelo;

/**
 * Representa un usuario completo con datos personales y rol.
 */
public class UsuarioCompleto {
    private int id;
    private int personaId;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String direccion;
    private String rol;

    /**
     * Constructor que inicializa todos los campos de UsuarioCompleto.
     *
     * @param id ID del usuario.
     * @param personaId ID de la persona asociada.
     * @param nombres Nombres del usuario.
     * @param apellidos Apellidos del usuario.
     * @param correo Correo electrónico.
     * @param telefono Número telefónico.
     * @param direccion Dirección del usuario.
     * @param rol Rol del usuario (ej. administrador, mentor, emprendedor).
     */
    public UsuarioCompleto(int id, int personaId, String nombres, String apellidos,
                           String correo, String telefono, String direccion, String rol) {
        this.id = id;
        this.personaId = personaId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
    }

    /** @return ID del usuario. */
    public int getId() { return id; }

    /** @param id Establece el ID del usuario. */
    public void setId(int id) { this.id = id; }

    /** @return ID de la persona asociada. */
    public int getPersonaId() { return personaId; }

    /** @param personaId Establece el ID de la persona asociada. */
    public void setPersonaId(int personaId) { this.personaId = personaId; }

    /** @return Nombres del usuario. */
    public String getNombres() { return nombres; }

    /** @param nombres Establece los nombres del usuario. */
    public void setNombres(String nombres) { this.nombres = nombres; }

    /** @return Apellidos del usuario. */
    public String getApellidos() { return apellidos; }

    /** @param apellidos Establece los apellidos del usuario. */
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    /** @return Correo electrónico del usuario. */
    public String getCorreo() { return correo; }

    /** @param correo Establece el correo electrónico del usuario. */
    public void setCorreo(String correo) { this.correo = correo; }

    /** @return Teléfono del usuario. */
    public String getTelefono() { return telefono; }

    /** @param telefono Establece el teléfono del usuario. */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /** @return Dirección del usuario. */
    public String getDireccion() { return direccion; }

    /** @param direccion Establece la dirección del usuario. */
    public void setDireccion(String direccion) { this.direccion = direccion; }

    /** @return Rol del usuario. */
    public String getRol() { return rol; }

    /** @param rol Establece el rol del usuario. */
    public void setRol(String rol) { this.rol = rol; }
}
