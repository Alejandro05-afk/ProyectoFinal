package Modelo;

/**
 * Representa a un usuario del sistema, vinculado a una persona y con rol y credenciales.
 */
public class Usuario {
    private int id;
    private int personaId;
    private String contraseña;
    private String rol;

    // NUEVOS CAMPOS (datos de la persona asociados al usuario)
    private String nombres;
    private String apellidos;
    private String correo;

    // Relación con objeto Persona
    private Persona persona;

    /**
     * Constructor vacío. Útil para frameworks o cuando los valores se asignan más adelante.
     */
    public Usuario() {}

    /**
     * Constructor principal para crear un usuario.
     * @param personaId ID de la persona asociada.
     * @param contraseña Contraseña del usuario.
     * @param rol Rol del usuario (Administrador, Mentor, Emprendedor, etc.).
     */
    public Usuario(int personaId, String contraseña, String rol) {
        this.personaId = personaId;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    /**
     * Obtiene el ID del usuario.
     * @return ID único.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     * @param id ID único.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el ID de la persona asociada.
     * @return ID de persona.
     */
    public int getPersonaId() {
        return personaId;
    }

    /**
     * Establece el ID de la persona asociada.
     * @param personaId ID de persona.
     */
    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return Contraseña.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece la contraseña del usuario.
     * @param contraseña Contraseña.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Obtiene el rol del usuario.
     * @return Rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol Rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene los nombres de la persona asociada.
     * @return Nombres.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece los nombres de la persona asociada.
     * @param nombres Nombres.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos de la persona asociada.
     * @return Apellidos.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos de la persona asociada.
     * @param apellidos Apellidos.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el correo de la persona asociada.
     * @return Correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo de la persona asociada.
     * @param correo Correo electrónico.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el objeto Persona asociado a este usuario.
     * @return Objeto Persona.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Establece el objeto Persona asociado a este usuario.
     * @param persona Objeto Persona.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
