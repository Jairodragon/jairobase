package org.example.dao;

import org.example.obj.Estudiante;

import java.util.ArrayList;

public abstract class EstudianteDao {
    public abstract int insert(Estudiante estudiante) throws Exception;

    public abstract void update(Estudiante estudiante) throws Exception;

    public abstract void delete(int id) throws Exception;

    public abstract Estudiante get(int id) throws Exception;

    public abstract ArrayList<Estudiante> getList() throws Exception;
}
