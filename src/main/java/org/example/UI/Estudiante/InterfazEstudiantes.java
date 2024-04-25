package org.example.UI.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.example.UI.Notas.InterfazNotas;
import org.example.daoImple.EstudianteDaoImple;
import org.example.obj.Estudiante;
import org.example.dao.EstudianteDao;

public class InterfazEstudiantes extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JButton btnAgregarEstudiante;
    private JButton btnVerMaterias;
    private JButton btnVerNotas;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnInscribirse;
    public InterfazEstudiantes() {
        setTitle("Estudiantes");
        setSize(800, 600);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Nombre", "Apellido", "Fecha de Nacimiento", "Carrera"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        btnAgregarEstudiante = new JButton("Agregar Estudiante");

        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTabla();
            }
        });
        timer.start(); // Inicia el Timer

        btnAgregarEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazAgregarEstudiante interfazAgregarEstudiante = new InterfazAgregarEstudiante();
            }
        });

        btnVerMaterias = new JButton("Ver Materias");
        btnVerMaterias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int idEstudiante = (int) model.getValueAt(selectedRow, 0);
                    new InterfazVerMaterias(idEstudiante).setVisible(true);
                }
            }
        });

        btnVerNotas = new JButton("Ver Notas");
        btnVerNotas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int idEstudiante = (int) model.getValueAt(selectedRow, 0);
                    new InterfazNotas(idEstudiante);
                }
            }
        });

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    EstudianteDao estudianteDao = new EstudianteDaoImple();
                    int idEstudiante = (int) model.getValueAt(selectedRow, 0);
                    try {
                        Estudiante estudiante = estudianteDao.get(idEstudiante);
                        new InterfazActualizarEstudiante(estudiante).setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(InterfazEstudiantes.this, "Error al obtener el estudiante");
                    }
                }
            }
        });

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEstudiante();
                actualizarTabla();
            }
        });
        btnInscribirse = new JButton("Inscribirse");
        btnInscribirse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int idEstudiante = (int) model.getValueAt(selectedRow, 0);
                    new InterfazAgregarInscrito(idEstudiante).setVisible(true);
                }
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregarEstudiante);
        panelBotones.add(btnVerMaterias);
        panelBotones.add(btnVerNotas);
        panelBotones.add(btnInscribirse);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public void loadStudents() {
        EstudianteDao estudianteDao = new EstudianteDaoImple();
        try {
            ArrayList<Estudiante> estudiantes = estudianteDao.getList();
            for (Estudiante estudiante : estudiantes) {
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

    public void eliminarEstudiante() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idEstudiante = (int) model.getValueAt(selectedRow, 0);
            EstudianteDao estudianteDao = new EstudianteDaoImple();
            try {
                estudianteDao.delete(idEstudiante);
                model.removeRow(selectedRow);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(InterfazEstudiantes.this, "Error al eliminar el estudiante");
            }
        }
    }

    public void actualizarTabla() {
        model.setRowCount(0); // Borra todos los datos de la tabla
        loadStudents(); // Vuelve a cargar los datos desde la base de datos
    }
}