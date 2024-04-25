package org.example.dao;

import org.example.obj.Materia;

import java.util.ArrayList;

public abstract class MateriaDao {
    public abstract int insert(Materia materia) throws Exception;

    public abstract void update(Materia materia) throws Exception;

    public abstract void delete(int id) throws Exception;

    public abstract Materia get(int id) throws Exception;

    public abstract ArrayList<Materia> getList() throws Exception;
}
