package org.example.UI.Materia;

import org.example.dao.MateriaDao;
import org.example.daoImple.MateriaDaoImple;
import org.example.obj.Materia;

import javax.swing.*;
import java.awt.*;

public class InterfazAgregarMateria extends JFrame {
    private JTextField txtNombreMateria;
    private JTextField txtCreditos;

    public InterfazAgregarMateria() {
        setTitle("Agregar Materia");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        setVisible(true);

        JLabel lblNombreMateria = new JLabel("Nombre de la Materia:");
        txtNombreMateria = new JTextField();
        JLabel lblCreditos = new JLabel("Creditos:");
        txtCreditos = new JTextField();

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarMateria());

        add(lblNombreMateria);
        add(txtNombreMateria);
        add(lblCreditos);
        add(txtCreditos);
        add(btnAgregar);

        setLocationRelativeTo(null);


    }

    private void agregarMateria() {
        String nombreMateria = txtNombreMateria.getText();
        int creditos = Integer.parseInt(txtCreditos.getText());

        MateriaDao materiaDao = new MateriaDaoImple();
        Materia materia = new Materia();
        materia.setNombreMateria(nombreMateria);
        materia.setCreditos(creditos);

        try {
            materiaDao.insert(materia);
            JOptionPane.showMessageDialog(this, "Materia agregada exitosamente");
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar la materia");
        }
    }
}