package Controlador;

import DAO.AvanceFaseDAO;

/**
 * Controlador para gestionar el registro de avances en las fases de una idea de negocio.
 */
public class AvanceFaseController {

    /**
     * Registra un avance de fase en la base de datos.
     *
     * @param ideaId ID de la idea de negocio.
     * @param faseId ID de la fase del proyecto.
     * @param porcentajeAvance Porcentaje de avance registrado.
     * @return Número de filas afectadas en la base de datos.
     */
    public static int registrarAvanceFase(int ideaId, int faseId, int porcentajeAvance) {
        return AvanceFaseDAO.insertarAvanceFase(ideaId, faseId, porcentajeAvance);
    }
}
