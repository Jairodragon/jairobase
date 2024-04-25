package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private String host = "localhost";
    private String port = "5432";
    private String database = "examenBase"; // Nombre de tu base de datos
    private String user = "postgres";
    private String pass = "root";
    private Connection objConnection;

    private static Conexion instance;

    private Conexion() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized Conexion getOrCreate() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public Connection conectarPostgreSQL() {
        try {
            Class.forName("org.postgresql.Driver");
            String connectionString = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            objConnection = DriverManager.getConnection(connectionString, user, pass);

            // Configurar el esquema
            objConnection.createStatement().execute("SET search_path TO public");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return objConnection;
    }

    public void ejecutarSQL(String sql) {
        try (Connection con = conectarPostgreSQL()) {
            Statement consulta = con.createStatement();
            consulta.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void desconectar() {
        try {
            if (estaConectado()) {
                objConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean estaConectado() {
        if (objConnection == null) {
            return false;
        }
        try {
            if (objConnection.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            objConnection = null;
            return false;
        }
        return true;
    }
}
