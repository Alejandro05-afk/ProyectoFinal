package Modelo;

/**
 * Representa una idea de negocio registrada por un usuario.
 */
public class IdeaNegocio {
    private int id;
    private int usuarioId;
    private int categoriaId;
    private String titulo;
    private String descripcion;
    private String estado;

    /**
     * Constructor vacío para uso general o frameworks.
     */
    public IdeaNegocio() { }

    /**
     * Constructor completo para inicializar una idea de negocio.
     * @param id ID único de la idea.
     * @param usuarioId ID del usuario que propuso la idea.
     * @param categoriaId ID de la categoría asignada.
     * @param titulo Título de la idea.
     * @param descripcion Descripción de la idea.
     * @param estado Estado actual de la idea (ej. pendiente, aprobado, etc.).
     */
    public IdeaNegocio(int id, int usuarioId, int categoriaId, String titulo, String descripcion, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Getters

    /**
     * Obtiene el ID de la idea.
     * @return ID de la idea.
     */
    public int getId() { return id; }

    /**
     * Obtiene el ID del usuario autor de la idea.
     * @return ID del usuario.
     */
    public int getUsuarioId() { return usuarioId; }

    /**
     * Obtiene el ID de la categoría de la idea.
     * @return ID de la categoría.
     */
    public int getCategoriaId() { return categoriaId; }

    /**
     * Obtiene el título de la idea.
     * @return título de la idea.
     */
    public String getTitulo() { return titulo; }

    /**
     * Obtiene la descripción de la idea.
     * @return descripción.
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Obtiene el estado actual de la idea.
     * @return estado de la idea.
     */
    public String getEstado() { return estado; }

    // Setters

    public void setId(int id) { this.id = id; }

    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public void setEstado(String estado) { this.estado = estado; }
}
