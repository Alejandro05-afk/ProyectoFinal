package Controlador;

import DAO.UsuarioDAO;
import Modelo.UsuarioCompleto;
import Modelo.Persona;

import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Listar usuarios con rol (para mostrar en tabla con rol)
    public List<UsuarioCompleto> listarUsuariosConRol() {
        return usuarioDAO.listarUsuariosConRol();
    }

    // Buscar usuario con rol por nombre
    public UsuarioCompleto buscarPorNombreConRol(String nombre) {
        return usuarioDAO.buscarPorNombreConRol(nombre);
    }

    // Listar solo personas (sin rol)
    public List<Persona> listar() {
        return usuarioDAO.listarUsuarios();
    }

    // Buscar persona por nombre (sin rol)
    public Persona buscarPorNombre(String nombre) {
        return usuarioDAO.buscarPorNombre(nombre);
    }

    // Editar un campo específico de una persona o su usuario
    public void editarCampo(int personaId, String campo, String nuevoValor) {
        usuarioDAO.editarCampo(personaId, campo, nuevoValor);
    }

    // Eliminar un usuario completo (persona + usuario)
    public void eliminar(int personaId) {
        usuarioDAO.eliminarUsuario(personaId);
    }
}
