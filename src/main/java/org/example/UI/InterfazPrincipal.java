package org.example.UI;

import org.example.UI.Estudiante.InterfazEstudiantes;
import org.example.UI.Materia.InterfazMaterias;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazPrincipal extends JFrame {
    private JButton btnEstudiantes;
    private JButton btnMaterias;

    public InterfazPrincipal() {
        setTitle("Interfaz Principal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.LIGHT_GRAY); // Cambia el color de fondo

        btnEstudiantes = new JButton("Estudiantes");
        btnEstudiantes.setFont(new Font("Arial", Font.BOLD, 14)); // Cambia la fuente y el tamaño de la fuente

        btnEstudiantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazEstudiantes interfazEstudiantes = new InterfazEstudiantes();
                interfazEstudiantes.actualizarTabla();
                interfazEstudiantes.setVisible(true);
            }
        });

        btnMaterias = new JButton("Materias");
        btnMaterias.setFont(new Font("Arial", Font.BOLD, 14)); // Cambia la fuente y el tamaño de la fuente
        btnMaterias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazMaterias interfazMaterias = new InterfazMaterias();
                interfazMaterias.loadMaterias();
                interfazMaterias.setVisible(true);
            }
        });

        setLayout(new GridBagLayout()); // Cambia el layout a GridBagLayout para centrar los botones
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // Agrega espacio entre los botones

        add(btnEstudiantes, gbc);
        add(btnMaterias, gbc);
    }
}