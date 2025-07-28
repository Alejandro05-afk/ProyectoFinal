package Controlador;

import DAO.UsuarioDAO;
import Modelo.UsuarioCompleto;
import Modelo.Persona;

import java.util.List;

/**
 * Controlador encargado de gestionar operaciones relacionadas con usuarios y personas.
 */
public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    /**
     * Constructor que inicializa el DAO de usuario.
     */
    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Lista todos los usuarios junto con su rol, para mostrar en tablas.
     *
     * @return Lista de objetos {@link UsuarioCompleto} con datos y rol.
     */
    public List<UsuarioCompleto> listarUsuariosConRol() {
        return usuarioDAO.listarUsuariosConRol();
    }

    /**
     * Busca usuarios con rol por nombre.
     *
     * @param nombre Nombre para la búsqueda.
     * @return Lista de usuarios con rol que coinciden con el nombre.
     */
    public List<UsuarioCompleto> buscarPorNombreConRol(String nombre) {
        return usuarioDAO.buscarPorNombreConRol(nombre);
    }

    /**
     * Lista solo personas sin incluir rol.
     *
     * @return Lista de objetos {@link Persona}.
     */
    public List<Persona> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    /**
     * Busca una persona por nombre sin incluir rol.
     *
     * @param nombre Nombre para la búsqueda.
     * @return Objeto {@link Persona} encontrado.
     */
    public Persona buscarPorNombre(String nombre) {
        return usuarioDAO.buscarPorNombre(nombre);
    }

    /**
     * Edita un campo específico de una persona o su usuario asociado.
     *
     * @param personaId ID de la persona a editar.
     * @param campo Nombre del campo a modificar.
     * @param nuevoValor Nuevo valor a asignar.
     */
    public void editarCampo(int personaId, String campo, String nuevoValor) {
        usuarioDAO.editarCampo(personaId, campo, nuevoValor);
    }

    /**
     * Elimina un usuario completo, incluyendo la persona y el usuario.
     *
     * @param personaId ID de la persona a eliminar.
     */
    public void eliminar(int personaId) {
        usuarioDAO.eliminarUsuario(personaId);
    }

    /**
     * Método estático para eliminar un usuario completo dado el ID de la persona.
     *
     * @param personaId ID de la persona a eliminar.
     */
    public static void eliminarUsuario(int personaId) {
        new UsuarioDAO().eliminarUsuario(personaId);
    }
}
