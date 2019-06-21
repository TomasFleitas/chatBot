package ChatFrame;

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


        //TOKENIZAR Y ENVIAR A LA MI PARA QUE RETORNE UNA PREGUNTA Y ADEMAS ACTUALICE LA MT
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(texto, " ");
        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }

        String pregunta = maquinaDeInferencia.responder(tokens, palabraInferida -> {
            System.out.println("Palabra que fue inferida: " + palabraInferida);
            modeloPalabrasInferidas.addElement(palabraInferida);
        });

        modeloChat.addElement("Robot: " + pregunta);
    }
}
