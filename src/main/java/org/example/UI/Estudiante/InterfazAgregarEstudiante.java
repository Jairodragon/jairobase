package org.example.UI.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.example.daoImple.EstudianteDaoImple;
import org.example.obj.Estudiante;
import org.example.dao.EstudianteDao;

public class InterfazAgregarEstudiante extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtFechaNacimiento;
    private JTextField txtCarrera;
    private JButton btnAgregar;

    public InterfazAgregarEstudiante() {
        setTitle("Agregar Estudiante");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        setLayout(new GridLayout(5, 2));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        add(txtApellido);

        add(new JLabel("Fecha de Nacimiento (dd/MM/yyyy):"));
        txtFechaNacimiento = new JTextField();
        add(txtFechaNacimiento);

        add(new JLabel("Carrera:"));
        txtCarrera = new JTextField();
        add(txtCarrera);

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEstudiante();
            }
        });
        add(btnAgregar);
    }

    private void agregarEstudiante() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String fechaNacimientoStr = txtFechaNacimiento.getText();
        String carrera = txtCarrera.getText();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || fechaNacimientoStr.isEmpty() || carrera.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date fechaNacimientoUtil = null;
        try {
            fechaNacimientoUtil = formatter.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Por favor, use el formato yyyy/MM/dd");
            return;
        }

        // Convertir java.util.Date a java.sql.Date
        java.sql.Date fechaNacimiento = new java.sql.Date(fechaNacimientoUtil.getTime());

        Estudiante estudiante = new Estudiante();
        estudiante.setNombreCompleto(nombre);
        estudiante.setApellido(apellido);
        estudiante.setFechaNacimiento(fechaNacimiento);
        estudiante.setCarrera(carrera);

        EstudianteDao estudianteDao = new EstudianteDaoImple();
        try {
            estudianteDao.insert(estudiante);
            JOptionPane.showMessageDialog(this, "Estudiante agregado exitosamente");
            this.dispose(); // Cierra la ventana si el estudiante se agrega correctamente
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el estudiante", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}