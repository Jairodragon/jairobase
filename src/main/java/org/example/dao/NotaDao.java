package org.example.dao;

import org.example.obj.Nota;

import java.util.ArrayList;

public abstract class NotaDao {
    public abstract void insert(Nota nota) throws Exception;

    public abstract void update(Nota nota) throws Exception;

    public abstract void delete(int id) throws Exception;

    public abstract Nota getNotas(int id) throws Exception;

    public abstract ArrayList<Nota> getNotasEstudiante(int idEstudiante) throws Exception;

    public abstract double promedioNotasMateriaEstudiante(int idMateria, int idEstudiante) throws Exception;

    public abstract boolean existenNotasParaMateria(int idMateria) throws Exception;
}
