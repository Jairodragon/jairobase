package org.example.daoImple;

import org.example.Conexion;
import org.example.dao.MateriaDao;
import org.example.obj.Materia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MateriaDaoImple extends MateriaDao {
    @Override
    public int insert(Materia materia) throws Exception {
        int id = 0;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "INSERT INTO Materias (nombre_materia, creditos) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, materia.getNombreMateria());
            stmt.setInt(2, materia.getCreditos());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar la materia en la base de datos");
        }

        return id;
    }

    @Override
    public void update(Materia materia) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "UPDATE Materias SET nombre_materia=?, creditos=? WHERE id_materia=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, materia.getNombreMateria());
            stmt.setInt(2, materia.getCreditos());
            stmt.setInt(3, materia.getIdMateria());

            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar la materia en la base de datos");
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "DELETE FROM Materias WHERE id_materia=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al eliminar la materia de la base de datos");
        }
    }

    @Override
    public Materia get(int id) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT * FROM Materias WHERE id_materia=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("id_materia"));
                materia.setNombreMateria(rs.getString("nombre_materia"));
                materia.setCreditos(rs.getInt("creditos"));
                rs.close();
                stmt.close();
                objConexion.desconectar();
                return materia;
            } else {
                rs.close();
                stmt.close();
                objConexion.desconectar();
                throw new Exception("No se encontró la materia en la base de datos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al obtener la materia de la base de datos");
        }
    }

    @Override
    public ArrayList<Materia> getList() throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT * FROM Materias";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<Materia> materias = new ArrayList<>();
            while (rs.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("id_materia"));
                materia.setNombreMateria(rs.getString("nombre_materia"));
                materia.setCreditos(rs.getInt("creditos"));
                materias.add(materia);
            }

            rs.close();
            stmt.close();
            objConexion.desconectar();
            return materias;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al obtener la lista de materias de la base de datos");
        }
    }
}
