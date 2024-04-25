package org.example.UI.Notas;

import org.example.dao.NotaDao;
import org.example.daoImple.NotaDaoImple;
import org.example.obj.Nota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfazNotas extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private int idEstudiante;
    private JButton btnAgregarNota;
    private JButton btnPromedio;
    private NotaDao notaDao;
    public InterfazNotas(int idEstudiante) {
        this.idEstudiante = idEstudiante;
        notaDao = new NotaDaoImple();
        setTitle("Notas del estudiante");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setVisible(true);

        String[] columnNames = {"ID Materia", "Nota"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnAgregarNota = new JButton("Agregar nota");
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTabla();
            }
        });
        timer.start(); // Inicia el Timer
        btnAgregarNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InterfazAgregarNota(idEstudiante);

            }
        });
        btnPromedio = new JButton("Promedio");
        btnPromedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularPromedio();
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregarNota);
        panelBotones.add(btnPromedio);
        add(panelBotones, BorderLayout.SOUTH);

        loadNotas();
    }

    public void loadNotas() {
        NotaDao notaDao = new NotaDaoImple();
        try {
            ArrayList<Nota> notas = notaDao.getNotasEstudiante(idEstudiante);
            for (Nota nota : notas) {
                Object[] row = new Object[2];
                row[0] = nota.getIdMateria();
                row[1] = nota.getNota();
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las notas");
        }
    }

    public void actualizarTabla() {
        model.setRowCount(0); // Borra todos los datos de la tabla
        loadNotas(); // Vuelve a cargar los datos desde la base de datos
    }
    public void calcularPromedio() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idMateria = (int) model.getValueAt(selectedRow, 0);
            try {
                double promedio = notaDao.promedioNotasMateriaEstudiante(idMateria, idEstudiante);
                JOptionPane.showMessageDialog(this, "El promedio de las notas es: " + promedio);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al calcular el promedio de las notas");
            }
        }
    }
}