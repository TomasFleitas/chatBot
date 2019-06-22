package ChatFrame;

import Logica.Literal;
import Logica.MI;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class chat {
    private static MI maquinaDeInferencia;
    private JButton botonEnviar;
    private JTextField textoEnviar;
    private JList contenedorChat;
    private JPanel panelMain;
    private JList palabrasInferidas;
    private DefaultListModel<String> modeloChat;
    private DefaultListModel<String> modeloPalabrasInferidas;

    public chat() {

        modeloChat = new DefaultListModel<>();
        modeloPalabrasInferidas = new DefaultListModel<>();
        contenedorChat.setModel(modeloChat);
        palabrasInferidas.setModel(modeloPalabrasInferidas);

        botonEnviar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (textoEnviar.getText().length() > 0) enviarDatosMaquinaDeInferencias(textoEnviar.getText());
            }
        });
    }
    public static void main(String[] args) {
        maquinaDeInferencia = new MI();
        JFrame frame = new JFrame("Compras Inteligentes");
        frame.setContentPane(new chat().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void enviarDatosMaquinaDeInferencias(String texto) {

        modeloChat.addElement("Usuario: " + texto);

        String dialogoRobot = maquinaDeInferencia.responder(texto, palabraInferida -> {
            String palabras = "KeyWords: ";
            int i= 1;
            for (Literal l:palabraInferida){
                if (i%4 == 0){
                    palabras += "\n";
                }
                palabras+= l;
                i++;
            }
            modeloPalabrasInferidas.addElement(palabras);
        });

        modeloChat.addElement("Robot: " + dialogoRobot);
    }
}
