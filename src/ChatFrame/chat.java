package ChatFrame;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class chat {
    private JButton botonEnviar;
    private JTextField textoEnviar;
    private JList contenedorChat;
    private JPanel panelMain;
    private DefaultListModel<String> modelo;

    public chat() {

        modelo = new DefaultListModel<>();
        contenedorChat.setModel(modelo);

        botonEnviar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (textoEnviar.getText().length() > 0) enviarDatosMaquinaDeInferencias(textoEnviar.getText());
            }
        });

        botonEnviar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (textoEnviar.getText().length() > 0) enviarDatosMaquinaDeInferencias(textoEnviar.getText());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Compras Inteligentes");
        frame.setContentPane(new chat().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void enviarDatosMaquinaDeInferencias(String texto) {
        //LLAMAR A MAQUINA DE INFERENCIA PARA QUE EL AGENTE DEVUELVA UNA PREGUNTA (ESTO HACERLO SEGUN LA KEYWORD QUE COINCIDEN)

        String pregunta = "¡pregunta que deberia retornar el agente!";
        modelo.addElement(pregunta);


    }
}
