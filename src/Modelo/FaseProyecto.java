package Modelo;

/**
 * Representa una fase dentro del desarrollo de un proyecto o idea de negocio.
 */
public class FaseProyecto {
    private int id;
    private String nombre;

    /**
     * Constructor para crear una fase de proyecto.
     * @param id identificador único de la fase.
     * @param nombre nombre descriptivo de la fase.
     */
    public FaseProyecto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID de la fase del proyecto.
     * @return ID de la fase.
     */
    public int getId() { return id; }

    /**
     * Obtiene el nombre de la fase del proyecto.
     * @return nombre de la fase.
     */
    public String getNombre() { return nombre; }
}
