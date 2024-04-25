package org.example.UI.Estudiante;

import org.example.dao.EstudianteDao;
import org.example.daoImple.EstudianteDaoImple;
import org.example.obj.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class InterfazActualizarEstudiante extends JFrame {
    private JTextField txtIdEstudiante;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtFechaNacimiento;
    private JTextField txtCarrera;
    private Estudiante estudiante;

    public InterfazActualizarEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        setTitle("Actualizar Estudiante");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));
        setVisible(true);

        JLabel lblIdEstudiante = new JLabel("ID del Estudiante:");
        txtIdEstudiante = new JTextField(String.valueOf(estudiante.getIdEstudiante()));
        txtIdEstudiante.setEditable(false);
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(estudiante.getNombreCompleto());
        JLabel lblApellido = new JLabel("Apellido:");
        txtApellido = new JTextField(estudiante.getApellido());
        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
        txtFechaNacimiento = new JTextField(estudiante.getFechaNacimiento().toString());
        JLabel lblCarrera = new JLabel("Carrera:");
        txtCarrera = new JTextField(estudiante.getCarrera());

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarEstudiante());

        add(lblIdEstudiante);
        add(txtIdEstudiante);
        add(lblNombre);
        add(txtNombre);
        add(lblApellido);
        add(txtApellido);
        add(lblFechaNacimiento);
        add(txtFechaNacimiento);
        add(lblCarrera);
        add(txtCarrera);
        add(btnActualizar);

        setLocationRelativeTo(null);
    }

    private void actualizarEstudiante() {
        int idEstudiante = Integer.parseInt(txtIdEstudiante.getText());
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String fechaNacimiento = txtFechaNacimiento.getText();
        String carrera = txtCarrera.getText();

        EstudianteDao estudianteDao = new EstudianteDaoImple();
        Estudiante estudiante = new Estudiante();
        estudiante.setIdEstudiante(idEstudiante);
        estudiante.setNombreCompleto(nombre);
        estudiante.setApellido(apellido);
        estudiante.setFechaNacimiento(Date.valueOf(fechaNacimiento));
        estudiante.setCarrera(carrera);

        try {
            estudianteDao.update(estudiante);
            JOptionPane.showMessageDialog(this, "Estudiante actualizado exitosamente");
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el estudiante");
        }
    }
}