package org.example.UI.Materia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.example.dao.InscritoDao;
import org.example.dao.NotaDao;
import org.example.daoImple.InscritoDaoImple;
import org.example.daoImple.MateriaDaoImple;
import org.example.daoImple.NotaDaoImple;
import org.example.obj.Inscrito;
import org.example.obj.Materia;
import org.example.dao.MateriaDao;

public class InterfazMaterias extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JButton btnAgregarMateria;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVerEstudiantes;

    public InterfazMaterias() {
        setTitle("Materias");
        setSize(800, 600);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Nombre", "Créditos"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        btnAgregarMateria = new JButton("Agregar Materia");

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTabla();
            }
        });
        timer.start(); // Inicia el Timer

        btnAgregarMateria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazAgregarMateria interfazAgregarMateria = new InterfazAgregarMateria();
            }
        });

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int idMateria = (int) model.getValueAt(selectedRow, 0);
                    MateriaDao materiaDao = new MateriaDaoImple();
                    try {
                        Materia materia = materiaDao.get(idMateria);
                        new InterfazActualizarMateria(materia);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(InterfazMaterias.this, "Error al obtener la materia");
                    }
                }
            }
        });

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminarMateria();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnVerEstudiantes = new JButton("Ver Estudiantes Inscritos");
        btnVerEstudiantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int idEstudiante = (int) model.getValueAt(selectedRow, 0);
                    new InterfazVerEstudiantes(idEstudiante);
                }
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregarMateria);
        panelBotones.add(btnVerEstudiantes);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public void loadMaterias() {
        MateriaDao materiaDao = new MateriaDaoImple();
        try {
            ArrayList<Materia> materias = materiaDao.getList();
            for (Materia materia : materias) {
                Object[] row = new Object[3];
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

    public void eliminarMateria() throws Exception {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idMateria = (int) model.getValueAt(selectedRow, 0);
            NotaDao notaDao = new NotaDaoImple();
            if (notaDao.existenNotasParaMateria(idMateria)) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar la materia porque existen notas asociadas a ella");
                return;
            }
            MateriaDao materiaDao = new MateriaDaoImple();
            try {
                materiaDao.delete(idMateria);
                model.removeRow(selectedRow);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar la materia");
            }
        }
    }

    public void actualizarTabla() {
        model.setRowCount(0); // Borra todos los datos de la tabla
        loadMaterias(); // Vuelve a cargar los datos desde la base de datos
    }
}