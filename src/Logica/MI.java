package Logica;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class MI {

    private ArrayList<Regla> MP = new ArrayList<>();
    private ArrayList<Literal> MT = new ArrayList<>();

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

    public String responder(ArrayList<String> listaPalabrasClavesUsuario, PalabraInferidaInterface palabraInferidaInterface){

        //SE LLEVA TODAS LAS PALABRAS CLAVES ESCRITAS POR EL USUARIO A LITERALES PARA PODER COMPARAR CON LOS LITERALES DE LA MI
        ArrayList<Literal> literalesDeUsuario = new ArrayList<>();
        for (String s: listaPalabrasClavesUsuario) {
            literalesDeUsuario.add(new Literal(s));
        }

        //SE AGRUPAN LOS LITERALES DE LA MI Y LOS LITERALES ESCRITOS POR EL USUARIO SOLO PARA COMPROBAR LAS REGLAS
        HashSet<Literal> literalesAux = new HashSet<>();
        literalesAux.addAll(MT);
        literalesAux.addAll(literalesDeUsuario);

        //SE REALIZA EL COTEJO DE LAS REGLAS
        HashMap<Integer,Regla> reglasCotejadas = new HashMap<>();
        int i = 1;
        for (Regla r : MP){
            int literalesDelAntecedente = r.getAntecedentePalabrasClaves().size();
            int countLiterals = 0;
            for (Literal l : r.getAntecedentePalabrasClaves()){
                if (literalesAux.contains(l)){
                    countLiterals++;
                }
            }

            if (countLiterals == literalesDelAntecedente){
                reglasCotejadas.put(i,r);
                i++;
            }
        }


        //TODO: APLICAR LA FASE DE RESOLUCION EN EL SIGUIENTE ORDEN >>>ESPECIFICIDAD, NO-DIPLICIDAD,PRIORIDAD, NOVEDAD, ALEATORIO<<<

        //TODO: AGREGAR EL CONSECUENTE A LA MT Y OBTENER LA RESPUESTA ASOCIADA A LA REGLA ELEGIDA PARA QUE EL USUARIO TENGA FEEDBACK

        palabraInferidaInterface.palabraMacheada("ACA HAY QUE PONER EL CONSECUENTE");


        return "FALTA HACER QUE RESPONDA ALGO EL TIPO ESTE";
    }
}
