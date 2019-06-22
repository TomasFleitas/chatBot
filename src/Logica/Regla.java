package Logica;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class Regla {

    private Integer id;
    private ArrayList<Literal> antecedentePalabrasClaves;
    private String respuestaDialogo;
    private Boolean yaEjecutada = false;
    private int prioridad;
    private int novedad=0;

    public Regla() {
    }

    public Regla(Integer id, ArrayList<Literal> antecedentePalabrasClaves, int prioridad, String respuestaDialogo) {
        this.id = id;
        this.antecedentePalabrasClaves = antecedentePalabrasClaves;
        this.prioridad = prioridad;
        this.respuestaDialogo = respuestaDialogo;
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
