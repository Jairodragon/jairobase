package org.example.daoImple;

import org.example.Conexion;
import org.example.dao.EstudianteDao;
import org.example.obj.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EstudianteDaoImple extends EstudianteDao {
    @Override
    public int insert(Estudiante estudiante) throws Exception {
        int id = 0;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "INSERT INTO Estudiantes (nombre_completo, apellido, fecha_nacimiento, carrera) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, estudiante.getNombreCompleto());
            stmt.setString(2, estudiante.getApellido());
            stmt.setDate(3, estudiante.getFechaNacimiento());
            stmt.setString(4, estudiante.getCarrera());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar el estudiante en la base de datos");
        }

        return id;
    }

    @Override
    public void update(Estudiante estudiante) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "UPDATE Estudiantes SET nombre_completo=?, apellido=?, fecha_nacimiento=?, carrera=? WHERE id_estudiante=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, estudiante.getNombreCompleto());
            stmt.setString(2, estudiante.getApellido());
            stmt.setDate(3, estudiante.getFechaNacimiento());
            stmt.setString(4, estudiante.getCarrera());
            stmt.setInt(5, estudiante.getIdEstudiante());

            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar el estudiante en la base de datos");
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "DELETE FROM Estudiantes WHERE id_estudiante=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al eliminar el estudiante de la base de datos");
        }
    }

    @Override
    public Estudiante get(int id) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT * FROM Estudiantes WHERE id_estudiante=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombreCompleto(rs.getString("nombre_completo"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                estudiante.setCarrera(rs.getString("carrera"));
                rs.close();
                stmt.close();
                objConexion.desconectar();
                return estudiante;
            } else {
                rs.close();
                stmt.close();
                objConexion.desconectar();
                throw new Exception("No se encontró al estudiante en la base de datos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al obtener al estudiante de la base de datos");
        }
    }

    @Override
    public ArrayList<Estudiante> getList() throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT * FROM Estudiantes";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<Estudiante> estudiantes = new ArrayList<>();
            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombreCompleto(rs.getString("nombre_completo"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                estudiante.setCarrera(rs.getString("carrera"));
                estudiantes.add(estudiante);
            }

            rs.close();
            stmt.close();
            objConexion.desconectar();
            return estudiantes;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al obtener la lista de estudiantes de la base de datos");
        }
    }
}
