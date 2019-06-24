package Logica;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class Regla {

    private Integer id;
    private ArrayList<Literal> antecedentePalabrasClaves;
    private ArrayList<String> respuestasDialogo;
    private Boolean yaEjecutada = false;
    private int prioridad;
    private int novedad=0;

    public Regla() {
    }

    public Regla(Integer id, ArrayList<Literal> antecedentePalabrasClaves, int prioridad, ArrayList<String> respuestasDialogo) {
        this.id = id;
        this.antecedentePalabrasClaves = antecedentePalabrasClaves;
        this.prioridad = prioridad;
        this.respuestasDialogo = respuestasDialogo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNovedad() {
        return novedad;
    }

    public void setNovedad(int novedad) {
        this.novedad = novedad;
    }

    public ArrayList<Literal> getAntecedentePalabrasClaves() {
        return antecedentePalabrasClaves;
    }

    public void setAntecedentePalabrasClaves(ArrayList<Literal> antecedentePalabrasClaves) {
        this.antecedentePalabrasClaves = antecedentePalabrasClaves;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }


    public ArrayList<String> getRespuestasDialogo() {
        return respuestasDialogo;
    }

    public void setRespuestasDialogo(ArrayList<String> respuestasDialogo) {
        this.respuestasDialogo = respuestasDialogo;
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
