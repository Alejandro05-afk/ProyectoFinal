package Controlador;

import DAO.ObservacionDAO;

public class ObservacionController {

    // Agrega una observación a una mentoría
    public static void agregarObservacion(int mentoriaId, String comentario) {
        ObservacionDAO.insertarObservacion(mentoriaId, comentario);
    }
}
