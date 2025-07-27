package Modelo;

public class Usuario {
    private int id;
    private int personaId;
    private String contraseña;
    private String rol;

    // NUEVOS CAMPOS
    private String nombres;
    private String apellidos;
    private String correo;

    public Usuario() {}

    public Usuario(int personaId, String contraseña, String rol) {
        this.personaId = personaId;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPersonaId() { return personaId; }
    public void setPersonaId(int personaId) { this.personaId = personaId; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Getters y Setters para los nuevos campos
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    private Persona persona;

    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }


}
