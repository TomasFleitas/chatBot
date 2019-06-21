package Logica;

import java.util.ArrayList;

public class Regla {

    private ArrayList<Literal> antecedentePalabrasClaves;
    private Literal consecuente;
    private String respuestaDialogo;

    public Regla() {
    }

    public Regla(ArrayList<Literal> antecedentePalabrasClaves, Literal consecuente, String respuestaDialogo) {
        this.antecedentePalabrasClaves = antecedentePalabrasClaves;
        this.consecuente = consecuente;
        this.respuestaDialogo = respuestaDialogo;
    }

    public ArrayList<Literal> getAntecedentePalabrasClaves() {
        return antecedentePalabrasClaves;
    }

    public void setAntecedentePalabrasClaves(ArrayList<Literal> antecedentePalabrasClaves) {
        this.antecedentePalabrasClaves = antecedentePalabrasClaves;
    }

    public Literal getConsecuente() {
        return consecuente;
    }

    public void setConsecuente(Literal consecuente) {
        this.consecuente = consecuente;
    }

    public String getRespuestaDialogo() {
        return respuestaDialogo;
    }

    public void setRespuestaDialogo(String respuestaDialogo) {
        this.respuestaDialogo = respuestaDialogo;
    }

}
