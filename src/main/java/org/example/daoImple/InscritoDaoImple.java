package org.example.daoImple;

import org.example.Conexion;
import org.example.dao.InscritoDao;
import org.example.obj.Inscrito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InscritoDaoImple extends InscritoDao {
    @Override
    public void insert(Inscrito inscrito) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "INSERT INTO Inscritos (id_estudiante, id_materia) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, inscrito.getIdEstudiante());
            stmt.setInt(2, inscrito.getIdMateria());

            stmt.executeUpdate();

            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar el inscrito en la base de datos");
        }
    }

    @Override
    public void delete(int idEstudiante, int idMateria) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "DELETE FROM Inscritos WHERE id_estudiante=? AND id_materia=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idMateria);

            stmt.executeUpdate();

            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al eliminar el inscrito de la base de datos");
        }
    }

    @Override
    public ArrayList<Inscrito> getInscripcionesEstudiante(int idEstudiante) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT * FROM Inscritos WHERE id_estudiante=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            ArrayList<Inscrito> inscripciones = new ArrayList<>();
            while (rs.next()) {
                Inscrito inscrito = new Inscrito();
                inscrito.setIdEstudiante(rs.getInt("id_estudiante"));
                inscrito.setIdMateria(rs.getInt("id_materia"));
                inscripciones.add(inscrito);
            }

            rs.close();
            stmt.close();
            objConexion.desconectar();
            return inscripciones;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al obtener las inscripciones del estudiante de la base de datos");
        }
    }

    @Override
    public ArrayList<Inscrito> getInscripcionesMateria(int idMateria) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT * FROM Inscritos WHERE id_materia=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            ArrayList<Inscrito> inscripciones = new ArrayList<>();
            while (rs.next()) {
                Inscrito inscrito = new Inscrito();
                inscrito.setIdEstudiante(rs.getInt("id_estudiante"));
                inscrito.setIdMateria(rs.getInt("id_materia"));
                inscripciones.add(inscrito);
            }

            rs.close();
            stmt.close();
            objConexion.desconectar();
            return inscripciones;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al obtener las inscripciones de la materia de la base de datos");
        }
    }
}
