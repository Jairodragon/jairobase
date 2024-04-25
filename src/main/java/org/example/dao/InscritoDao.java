package org.example.dao;

import org.example.obj.Inscrito;

import java.util.ArrayList;

public abstract class InscritoDao {
    public abstract void insert(Inscrito inscrito) throws Exception;

    public abstract void delete(int idEstudiante, int idMateria) throws Exception;

    public abstract ArrayList<Inscrito> getInscripcionesEstudiante(int idEstudiante) throws Exception;

    public abstract ArrayList<Inscrito> getInscripcionesMateria(int idMateria) throws Exception;
}
