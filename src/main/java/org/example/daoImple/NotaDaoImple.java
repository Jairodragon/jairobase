package org.example.daoImple;

import org.example.Conexion;
import org.example.dao.NotaDao;
import org.example.obj.Nota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotaDaoImple extends NotaDao {
    @Override
    public void insert(Nota nota) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "INSERT INTO Notas (id_estudiante, id_materia, nota) VALUES (?, ?, ?) RETURNING id_nota";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, nota.getIdEstudiante());
            stmt.setInt(2, nota.getIdMateria());
            stmt.setFloat(3, nota.getNota());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nota.setId(rs.getInt("id_nota"));
            }

            rs.close();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar la nota en la base de datos", e);
        }
    }

    @Override
    public void update(Nota nota) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "UPDATE Notas SET nota=? WHERE id_nota=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setFloat(1, nota.getNota());
            stmt.setInt(2, nota.getId());

            stmt.executeUpdate();

            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar la nota en la base de datos", e);
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "DELETE FROM Notas WHERE id_nota=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            stmt.executeUpdate();

            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al eliminar la nota de la base de datos", e);
        }
    }

    @Override
    public Nota getNotas(int id) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT * FROM Notas WHERE id_nota=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));
                nota.setIdEstudiante(rs.getInt("id_estudiante"));
                nota.setIdMateria(rs.getInt("id_materia"));
                nota.setNota(rs.getFloat("nota"));
                rs.close();
                stmt.close();
                objConexion.desconectar();
                return nota;
            } else {
                rs.close();
                stmt.close();
                objConexion.desconectar();
                throw new Exception("No se encontró la nota en la base de datos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al obtener la nota de la base de datos", e);
        }
    }
    @Override
    public ArrayList<Nota> getNotasEstudiante(int idEstudiante) throws Exception {
        ArrayList<Nota> notas = new ArrayList<>();
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT id_nota, id_estudiante, id_materia, nota FROM Notas WHERE id_estudiante=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id_nota"));
                nota.setIdEstudiante(rs.getInt("id_estudiante"));
                nota.setIdMateria(rs.getInt("id_materia"));
                nota.setNota(rs.getFloat("nota"));
                notas.add(nota);
            }

            rs.close();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al obtener las notas del estudiante de la base de datos", e);
        }
        return notas;
    }
    @Override
    public double promedioNotasMateriaEstudiante(int idMateria, int idEstudiante) throws Exception {
        double promedio = 0;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT AVG(nota) FROM Notas WHERE id_materia=? AND id_estudiante=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idMateria);
            stmt.setInt(2, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                promedio = rs.getDouble(1);
            }

            rs.close();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al calcular el promedio de las notas de la materia para el estudiante", e);
        }
        return promedio;
    }
    @Override
    public boolean existenNotasParaMateria(int idMateria) throws Exception {
        boolean existenNotas = false;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarPostgreSQL();
            String query = "SELECT COUNT(*) FROM Notas WHERE id_materia=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                existenNotas = count > 0;
            }

            rs.close();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al verificar si existen notas para la materia", e);
        }
        return existenNotas;
    }
}
