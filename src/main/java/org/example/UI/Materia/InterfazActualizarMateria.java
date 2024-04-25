package org.example.UI.Materia;

import org.example.dao.MateriaDao;
import org.example.daoImple.MateriaDaoImple;
import org.example.obj.Materia;

import javax.swing.*;
import java.awt.*;

public class InterfazActualizarMateria extends JFrame {
    private JTextField txtIdMateria;
    private JTextField txtNombreMateria;
    private JTextField txtCreditos;
    private Materia materia;

    public InterfazActualizarMateria(Materia materia) {
        this.materia = materia;
        setTitle("Actualizar Materia");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));
        setVisible(true);

        JLabel lblIdMateria = new JLabel("ID de la Materia:");
        txtIdMateria = new JTextField(String.valueOf(materia.getIdMateria()));
        txtIdMateria.setEditable(false);
        JLabel lblNombreMateria = new JLabel("Nombre de la Materia:");
        txtNombreMateria = new JTextField(materia.getNombreMateria());
        JLabel lblCreditos = new JLabel("Creditos:");
        txtCreditos = new JTextField(String.valueOf(materia.getCreditos()));

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarMateria());

        add(lblIdMateria);
        add(txtIdMateria);
        add(lblNombreMateria);
        add(txtNombreMateria);
        add(lblCreditos);
        add(txtCreditos);
        add(btnActualizar);

        setLocationRelativeTo(null);
    }

    private void actualizarMateria() {
        int idMateria = Integer.parseInt(txtIdMateria.getText());
        String nombreMateria = txtNombreMateria.getText();
        int creditos = Integer.parseInt(txtCreditos.getText());

        MateriaDao materiaDao = new MateriaDaoImple();
        Materia materia = new Materia();
        materia.setIdMateria(idMateria);
        materia.setNombreMateria(nombreMateria);
        materia.setCreditos(creditos);

        try {
            materiaDao.update(materia);
            JOptionPane.showMessageDialog(this, "Materia actualizada exitosamente");
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la materia");
        }
    }
}