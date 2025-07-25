package Modelo;

public class Usuario {
    private int id;
    private int personaId;
    private String contraseña;
    private String rol;

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
}
