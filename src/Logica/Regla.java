package Logica;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class Regla {

    private Integer id;
    private ArrayList<Literal> antecedentePalabrasClaves;
    private Literal consecuente;
    private String respuestaDialogo;
    private Boolean yaEjecutada = false;

    public Regla() {
    }

    public Regla(Integer id, ArrayList<Literal> antecedentePalabrasClaves, Literal consecuente, String respuestaDialogo) {
        this.id = id;
        this.antecedentePalabrasClaves = antecedentePalabrasClaves;
        this.consecuente = consecuente;
        this.respuestaDialogo = respuestaDialogo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getYaEjecutada() {
        return yaEjecutada;
    }

    public void ejecutar() {
        yaEjecutada = true;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Regla) obj).getId() == id;
    }
}
