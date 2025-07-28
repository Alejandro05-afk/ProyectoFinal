package Modelo;

/**
 * Representa una categoría en el sistema.
 * Contiene un identificador único y un nombre.
 */
public class Categoria {
    private int id;
    private String nombre;

    /**
     * Constructor para crear una categoría con ID y nombre.
     * @param id identificador único de la categoría.
     * @param nombre nombre descriptivo de la categoría.
     */
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID de la categoría.
     * @return ID de la categoría.
     */
    public int getId() { return id; }

    /**
     * Obtiene el nombre de la categoría.
     * @return nombre de la categoría.
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve el nombre de la categoría como representación de texto.
     * Esto es útil para mostrar en componentes como JComboBox.
     * @return nombre de la categoría.
     */
    @Override
    public String toString() {
        return nombre;
    }
}
