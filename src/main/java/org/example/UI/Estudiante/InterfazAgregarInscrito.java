package org.example.UI.Estudiante;

import org.example.dao.InscritoDao;
import org.example.daoImple.InscritoDaoImple;
import org.example.obj.Estudiante;
import org.example.obj.Inscrito;

import javax.swing.*;
import java.awt.*;

public class InterfazAgregarInscrito extends JFrame {
    private JTextField txtIdEstudiante;
    private JTextField txtIdMateria;
    private int idEstudiante;

    public InterfazAgregarInscrito(int idEstudiante) {
        this.idEstudiante = idEstudiante;
        setTitle("Agregar Inscrito");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        setVisible(true);

        JLabel lblIdEstudiante = new JLabel("ID del Estudiante:");
        txtIdEstudiante = new JTextField(String.valueOf(idEstudiante));
        txtIdEstudiante.setEditable(false); // Hacer que txtIdEstudiante no sea editable
        JLabel lblIdMateria = new JLabel("ID de la Materia:");
        txtIdMateria = new JTextField();

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarInscrito());

        add(lblIdEstudiante);
        add(txtIdEstudiante);
        add(lblIdMateria);
        add(txtIdMateria);
        add(btnAgregar);

        setLocationRelativeTo(null);
    }

    private void agregarInscrito() {
        String idEstudianteStr = txtIdEstudiante.getText();
        String idMateriaStr = txtIdMateria.getText();

        if (idEstudianteStr.isEmpty() || idMateriaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: Todos los campos deben estar llenos");
            return;
        }

        int idEstudiante = Integer.parseInt(idEstudianteStr);
        int idMateria = Integer.parseInt(idMateriaStr);

        InscritoDao inscritoDao = new InscritoDaoImple();
        Inscrito inscrito = new Inscrito();
        inscrito.setIdEstudiante(idEstudiante);
        inscrito.setIdMateria(idMateria);

        try {
            inscritoDao.insert(inscrito);
            JOptionPane.showMessageDialog(this, "Inscripción agregada exitosamente");
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar la inscripción o estudiante ya inscrito");
        }
    }
}