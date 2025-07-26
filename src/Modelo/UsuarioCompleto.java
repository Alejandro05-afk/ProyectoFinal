package Modelo;

public class UsuarioCompleto {
    private int personaId;
    private String nombres;
    private String apellidos;
    private String correo;
    private String rol;

    // Constructor, getters y setters
    public UsuarioCompleto(int personaId, String nombres, String apellidos, String correo, String rol) {
        this.personaId = personaId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.rol = rol;
    }

    public int getPersonaId() { return personaId; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }
}
