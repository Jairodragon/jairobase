package org.example.UI.Estudiante;

import org.example.dao.InscritoDao;
import org.example.dao.MateriaDao;
import org.example.daoImple.InscritoDaoImple;
import org.example.daoImple.MateriaDaoImple;
import org.example.obj.Inscrito;
import org.example.obj.Materia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfazVerMaterias extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private int idEstudiante;
    private JButton btnEliminarInscripcion;

    public InterfazVerMaterias(int idEstudiante) {
        this.idEstudiante = idEstudiante;
        setTitle("Materias del estudiante");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        String[] columnNames = {"ID Materia", "Nombre Materia", "Creditos"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnEliminarInscripcion = new JButton("Eliminar Inscripción");
        btnEliminarInscripcion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarInscripcion();
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnEliminarInscripcion);
        add(panelBotones, BorderLayout.SOUTH);

        loadMaterias();
    }

    public void loadMaterias() {
        InscritoDao inscritoDao = new InscritoDaoImple();
        MateriaDao materiaDao = new MateriaDaoImple();
        try {
            ArrayList<Inscrito> inscripciones = inscritoDao.getInscripcionesEstudiante(idEstudiante);
            for (Inscrito inscrito : inscripciones) {
                Materia materia = materiaDao.get(inscrito.getIdMateria());
                Object[] row = new Object[4];
                row[0] = materia.getIdMateria();
                row[1] = materia.getNombreMateria();
                row[2] = materia.getCreditos();
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las materias");
        }
    }

    public void eliminarInscripcion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idMateria = (int) model.getValueAt(selectedRow, 0);
            InscritoDao inscritoDao = new InscritoDaoImple();
            try {
                inscritoDao.delete(idEstudiante, idMateria);
                model.removeRow(selectedRow);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar la inscripción");
            }
        }
    }
}