package org.example.UI.Materia;

import org.example.dao.EstudianteDao;
import org.example.dao.InscritoDao;
import org.example.daoImple.EstudianteDaoImple;
import org.example.daoImple.InscritoDaoImple;
import org.example.obj.Estudiante;
import org.example.obj.Inscrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class InterfazVerEstudiantes extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private int idMateria;

    public InterfazVerEstudiantes(int idMateria) {
        this.idMateria = idMateria;
        setTitle("Estudiantes inscritos en la materia");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setVisible(true);

        String[] columnNames = {"ID Estudiante", "Nombre Completo", "Apellido", "Fecha de Nacimiento", "Carrera"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadInscripcionesEstudiante(idMateria);
    }

    public void loadInscripcionesEstudiante(int idMateria) {
        InscritoDao inscritoDao = new InscritoDaoImple();
        EstudianteDao estudianteDao = new EstudianteDaoImple();
        try {
            ArrayList<Inscrito> inscripciones = inscritoDao.getInscripcionesMateria(idMateria);
            for (Inscrito inscrito : inscripciones) {
                Estudiante estudiante = estudianteDao.get(inscrito.getIdEstudiante());
                Object[] row = new Object[5];
                row[0] = estudiante.getIdEstudiante();
                row[1] = estudiante.getNombreCompleto();
                row[2] = estudiante.getApellido();
                row[3] = estudiante.getFechaNacimiento();
                row[4] = estudiante.getCarrera();
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los estudiantes");
        }
    }
}