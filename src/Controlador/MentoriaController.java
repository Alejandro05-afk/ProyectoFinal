package Controlador;

import DAO.MentoriaDAO;
import Modelo.Mentor;
import Modelo.Mentoria;

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
    // Crear una nueva mentoría
    public static void crearMentoria(int ideaId, int mentorId, String fecha) {
        MentoriaDAO.insertarMentoria(ideaId, mentorId, fecha);
    }

    // Listar todas las mentorías asignadas a un mentor
    public static List<Mentoria> obtenerMentoriasPorMentor(int mentorId) {
        return MentoriaDAO.obtenerMentoriasPorMentor(mentorId);
    }
    public static int obtenerMentoriaIdPorIdea(int ideaId) {
        return MentoriaDAO.obtenerMentoriaIdPorIdea(ideaId);
    }

}
