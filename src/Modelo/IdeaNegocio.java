package Modelo;

public class IdeaNegocio {
    private int id;
    private int usuarioId;
    private int categoriaId;
    private String titulo;
    private String descripcion;
    private String estado;

    public IdeaNegocio() { }

    public IdeaNegocio(int id, int usuarioId, int categoriaId, String titulo, String descripcion, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public int getCategoriaId() { return categoriaId; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setId(int id) { this.id = id; }
}
