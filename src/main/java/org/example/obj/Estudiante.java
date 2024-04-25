package org.example.obj;

import java.sql.Date;

public class Estudiante {
    private int idEstudiante;
    private String nombreCompleto;
    private String apellido;
    private Date fechaNacimiento;
    private String carrera;

    // Constructor
    public Estudiante() {
    }

    public Estudiante(int idEstudiante, String nombreCompleto, String apellido, Date fechaNacimiento, String carrera) {
        this.idEstudiante = idEstudiante;
        this.nombreCompleto = nombreCompleto;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.carrera = carrera;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}
