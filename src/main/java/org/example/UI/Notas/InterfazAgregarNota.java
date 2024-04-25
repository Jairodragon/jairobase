package org.example.UI.Notas;

import org.example.dao.InscritoDao;
import org.example.dao.MateriaDao;
import org.example.dao.NotaDao;
import org.example.daoImple.InscritoDaoImple;
import org.example.daoImple.MateriaDaoImple;
import org.example.daoImple.NotaDaoImple;
import org.example.obj.Inscrito;
import org.example.obj.Materia;
import org.example.obj.Nota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InterfazAgregarNota extends JFrame {
    private JComboBox<String> cmbMaterias;
    private JTextField txtNota;
    private JButton btnAgregar;
    private int idEstudiante;
    private Map<String, Integer> mapMaterias;

    public InterfazAgregarNota(int idEstudiante) {
        this.idEstudiante = idEstudiante;
        setTitle("Agregar Nota");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);
        setVisible(true);

        add(new JLabel("Materia:"));
        cmbMaterias = new JComboBox<>();
        mapMaterias = new HashMap<>();
        loadMaterias();
        add(cmbMaterias);

        add(new JLabel("Nota:"));
        txtNota = new JTextField();
        add(txtNota);

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarNota();
            }
        });
        add(btnAgregar);
    }

    public void loadMaterias() {
        InscritoDao inscritoDao = new InscritoDaoImple();
        MateriaDao materiaDao = new MateriaDaoImple();
        try {
            ArrayList<Inscrito> inscripciones = inscritoDao.getInscripcionesEstudiante(idEstudiante);
            for (Inscrito inscrito : inscripciones) {
                Materia materia = materiaDao.get(inscrito.getIdMateria());
                cmbMaterias.addItem(materia.getNombreMateria());
                mapMaterias.put(materia.getNombreMateria(), materia.getIdMateria());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las materias");
        }
    }

    public void agregarNota() {
        String nombreMateria = (String) cmbMaterias.getSelectedItem();
        int idMateria = mapMaterias.get(nombreMateria);
        double notaValor = Double.parseDouble(txtNota.getText());
        NotaDao notaDao = new NotaDaoImple();
        try {
            Nota nota = new Nota(idEstudiante, idMateria, (float) notaValor);
            notaDao.insert(nota);
            this.dispose();
            JOptionPane.showMessageDialog(this, "Nota agregada exitosamente. ID generado: " + nota.getId());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar la nota");
        }
    }
}