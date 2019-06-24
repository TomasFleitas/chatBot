package ChatFrame;

import Logica.Literal;
import Logica.MI;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * @author tomas
 */
public class chatBot extends javax.swing.JFrame {
    private static MI maquinaDeInferencia = new MI();
    private javax.swing.JButton botonEnviar;
    private javax.swing.JTextPane contenedorChat;
    private javax.swing.JTextField textoEnviar;

    public chatBot() {
        initComponents();
        setLocationRelativeTo(null);

    }

    private void initComponents() {

        botonEnviar = new javax.swing.JButton();
        textoEnviar = new javax.swing.JTextField();
        JLabel jLabel1 = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        contenedorChat = new javax.swing.JTextPane();
        JLabel jLabel2 = new JLabel();
        JScrollPane jScrollPane2 = new JScrollPane();
        JTextPane contenedorAgente = new JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compras inteligentes");
        setResizable(false);

        botonEnviar.setText("Enviar");
        botonEnviar.addActionListener(evt -> botonEnviarActionPerformed());
        botonEnviar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                botonEnviarKeyTyped(evt);
            }
        });

        jLabel1.setText("Chat");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        contenedorChat.setEditable(false);
        contenedorChat.setAutoscrolls(false);
        jScrollPane1.setViewportView(contenedorChat);

        jLabel2.setText("KewWord y Resultados");

        contenedorAgente.setEditable(false);
        jScrollPane2.setViewportView(contenedorAgente);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(textoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(botonEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(botonEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        pack();
    }

    private void botonEnviarActionPerformed() {
        if (textoEnviar.getText().length() > 0) enviarDatosMaquinaDeInferencias(textoEnviar.getText()); textoEnviar.setText("");
    }

    private void botonEnviarKeyTyped(java.awt.event.KeyEvent evt) {
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            botonEnviar.doClick();
        }
    }


    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chatBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(() -> new chatBot().setVisible(true));
    }

    private void enviarDatosMaquinaDeInferencias(String texto) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM HH:mm");
        String auxUsuario = "Usuario: \n      " + texto+"\n   " +
                "                                  "+format.format(new Date());
        addText(auxUsuario, Color.BLACK);
        String dialogoRobot = maquinaDeInferencia.responder(texto, palabraInferida -> {
            StringBuilder palabras = new StringBuilder("KeyWords: ");
            int i = 1;
            for (Literal l : palabraInferida) {
                if (i % 4 == 0) {
                    palabras.append("\n");
                }
                palabras.append(l);
                i++;
            }
            String auxVendedor = "InfoParaElVendedor: \n      " + palabras;
            addText(auxVendedor, Color.MAGENTA);
        });
        String auxAgente = "EwentsCorp: \n      " + dialogoRobot+"\n   " +
                "                                  "+format.format(new Date());
        addText(auxAgente, Color.ORANGE);
    }

    private void addText(String text, Color color) {
        text += "\n \n";
        StyledDocument doc = contenedorChat.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len = doc.getLength();
        contenedorChat.setCaretPosition(len);
        contenedorChat.setCharacterAttributes(aset, false);
        try {
            doc.insertString(len, text, aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        pack();
        this.validate();
    }
}
