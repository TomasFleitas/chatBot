package Logica;


import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.ArrayList;
import java.util.HashSet;


public class MI {

    private ArrayList<Regla> MP = new ArrayList<>();
    private ArrayList<Literal> MT = new ArrayList<>();
    private ArrayList<Regla> reglasActivas = new ArrayList<>();

    public interface PalabraInferidaInterface {
        void palabraMacheada(String palabraInferida);
    }

    public MI() {
    }

    public MI(ArrayList<Regla> MP, ArrayList<Literal> MT) {
        this.MP = MP;
        this.MT = MT;
    }

    public ArrayList<Regla> getMP() {
        return MP;
    }

    public void setMP(ArrayList<Regla> MP) {
        this.MP = MP;
    }

    public ArrayList<Literal> getMT() {
        return MT;
    }

    public void setMT(ArrayList<Literal> MT) {
        this.MT = MT;
    }

    public ArrayList<Regla> getReglasActivas() {
        return reglasActivas;
    }

    public void setReglasActivas(ArrayList<Regla> reglasActivas) {
        this.reglasActivas = reglasActivas;
    }

    public String responder(ArrayList<String> listaPalabrasClavesUsuario, PalabraInferidaInterface palabraInferidaInterface) {

        //SE LLEVA TODAS LAS PALABRAS CLAVES ESCRITAS POR EL USUARIO A LITERALES PARA PODER COMPARAR CON LOS LITERALES DE LA MI
        ArrayList<Literal> literalesDeUsuario = new ArrayList<>();
        for (String s : listaPalabrasClavesUsuario) {
            literalesDeUsuario.add(new Literal(s));
        }

        //SE AGRUPAN LOS LITERALES DE LA MI Y LOS LITERALES ESCRITOS POR EL USUARIO SOLO PARA COMPROBAR LAS REGLAS
        HashSet<Literal> literalesAux = new HashSet<>();
        literalesAux.addAll(MT);
        literalesAux.addAll(literalesDeUsuario);

        //SE REALIZA EL COTEJO DE LAS REGLAS
        ArrayList<Regla> reglasCotejadas = new ArrayList<>();
        for (Regla r : MP) {
            int literalesDelAntecedente = r.getAntecedentePalabrasClaves().size();
            int countLiterals = 0;
            for (Literal l : r.getAntecedentePalabrasClaves()) {
                if (literalesAux.contains(l)) {
                    countLiterals++;
                }
            }

            if (countLiterals == literalesDelAntecedente) {
                reglasCotejadas.add(r);
            }
        }

        //TODO: APLICAR LA FASE DE RESOLUCION EN EL SIGUIENTE ORDEN >>>ESPECIFICIDAD, NO-DIPLICIDAD,NOVEDAD,PRIORIDAD,ALEATORIO<<
        ArrayList<Regla> procesoDeInferir = new ArrayList<>();
        //>>>>>>>>>ESPECIFICIDAD<<<<<<<<<<<<<
        for (int x = 0; x < reglasCotejadas.size(); x++) {
            for (int i = 0; i < reglasCotejadas.size() - x - 1; i++) {
                if (reglasCotejadas.get(i).getAntecedentePalabrasClaves().size() > reglasCotejadas.get(i + 1).getAntecedentePalabrasClaves().size()) {
                    Regla tmp = reglasCotejadas.get(i + 1);
                    reglasCotejadas.add(i + 1, reglasCotejadas.get(i));
                    reglasCotejadas.add(i, tmp);
                }
            }
        }
        procesoDeInferir.add(reglasCotejadas.get(0));
        for (Regla regla : reglasCotejadas.subList(1, reglasCotejadas.size() - 1)) {
            if (reglasCotejadas.get(0).getAntecedentePalabrasClaves().size() == regla.getAntecedentePalabrasClaves().size()) {
                procesoDeInferir.add(regla);
            }
        }

        if (procesoDeInferir.size() > 0) {
            if (procesoDeInferir.size() == 1) {
                reglasActivas.add(procesoDeInferir.get(0));
                //TODO: REGLA A EJECUTAR!!!!!
                ///////////////////////////////////////FIN ESPECIFICIDAD////////////////////////////////////////////
            } else {
                //>>>>>>>>>>>>>>>>>>>>NO DUPLICIDAD<<<<<<<<<<<<<<<<<<<<<<
                for (Regla reglaActiva : reglasActivas) {
                    if (procesoDeInferir.contains(reglaActiva)) {
                        procesoDeInferir.remove(reglaActiva);
                    }
                }
            }
        }

        //TODO: AGREGAR EL CONSECUENTE A LA MT Y OBTENER LA RESPUESTA ASOCIADA A LA REGLA ELEGIDA PARA QUE EL USUARIO TENGA FEEDBACK

        palabraInferidaInterface.palabraMacheada("ACA HAY QUE PONER EL CONSECUENTE");


        return "FALTA HACER QUE RESPONDA ALGO EL TIPO ESTE";
    }
}
