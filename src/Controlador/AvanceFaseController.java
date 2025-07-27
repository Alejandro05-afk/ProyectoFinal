package Controlador;

import DAO.AvanceFaseDAO;

public class AvanceFaseController {

    public static int registrarAvanceFase(int ideaId, int faseId, int porcentajeAvance) {
        return AvanceFaseDAO.insertarAvanceFase(ideaId, faseId, porcentajeAvance);
    }
}
