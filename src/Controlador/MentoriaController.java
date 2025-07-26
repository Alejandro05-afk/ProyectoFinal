package Controlador;

import DAO.MentoriaDAO;
import Modelo.Mentor;

import java.util.ArrayList;
import java.util.List;

public class MentoriaController {

    // Obtener lista de mentores en formato "ID - NOMBRE (ESPECIALIDAD)"
    public static List<String> obtenerMentores() {
        List<Mentor> listaMentores = MentoriaDAO.obtenerMentores();
        List<String> resultado = new ArrayList<>();

        for (Mentor m : listaMentores) {
            resultado.add(m.getId() + " - " + m.getNombres() + " (" + m.getEspecialidad() + ")");
        }
        return resultado;
    }

    // Asignar mentor a una idea (crear entrada en la tabla mentorias)
    public static void asignar(int ideaId, int mentorId) {
        MentoriaDAO.asignarMentoria(ideaId, mentorId);
    }
}
