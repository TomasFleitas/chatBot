package Logica;

import java.util.*;


public class MI {

    private ArrayList<Regla> MP = new ArrayList<>();
    private HashSet<Literal> MT = new HashSet<>();
    private ArrayList<Regla> reglasActivas = new ArrayList<>();
    private Integer NUMERO_DE_INFERENCIA = 0;
    private ArrayList<String> dialogosDefault = new ArrayList<>();
    private HashSet<Literal> BAG_KEYWORD = new HashSet<>();
    private HashMap<Literal,Literal> palabrasRaices = new HashMap<>();

    public interface PalabrasClavesMacheadasInterface {
        void palabrasMacheadas(ArrayList<Literal> palabraInferida);
    }

    public MI() {
        setDialogosDefault();
        MP = CargadorReglas.getReglas();
        palabrasRaices = CargadorReglas.getPalabrasRaices();
        BAG_KEYWORD.addAll(CargadorReglas.getPalabrasClaves());
        //for(Regla regla : MP) System.out.println(regla.getAntecedentePalabrasClaves()+": ");//+regla.getRespuestasDialogo());
    }

    public MI(ArrayList<Regla> MP, HashSet<Literal> MT) {
        this.MP = MP;
        this.MT = MT;
        setDialogosDefault();
    }

    public ArrayList<Regla> getMP() {
        return MP;
    }

    public void setMP(ArrayList<Regla> MP) {
        this.MP = MP;
    }

    public HashSet<Literal> getMT() {
        return MT;
    }

    public void setMT(HashSet<Literal> MT) {
        this.MT = MT;
    }

    public ArrayList<Regla> getReglasActivas() {
        return reglasActivas;
    }

    public void setReglasActivas(ArrayList<Regla> reglasActivas) {
        this.reglasActivas = reglasActivas;
    }

    public String responder(String textoUsuario, PalabrasClavesMacheadasInterface palabrasClavesMacheadasInterface) {
        NUMERO_DE_INFERENCIA++;

        System.out.println("Usuario dice: "+textoUsuario);

        //TOKENIZAR
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(textoUsuario, " ");
        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }

        //SE LLEVA TODAS LAS PALABRAS CLAVES ESCRITAS POR EL USUARIO A LITERALES PARA PODER COMPARAR CON LOS LITERALES DE LA MI
        ArrayList<Literal> literalesDeUsuario = new ArrayList<>();
        for (String s : tokens) literalesDeUsuario.add(new Literal(s));

        //FILTRAR LOS LITERALES ESCRITOS POR EL USUARIO CON LA BAG DE KEYWORD
        int longitudPalabrasUsuario = literalesDeUsuario.size();
        for (int h = 0; h < longitudPalabrasUsuario; h++) {
            if (palabrasRaices.containsKey(literalesDeUsuario.get(h)) && BAG_KEYWORD.contains(palabrasRaices.get(literalesDeUsuario.get(h))))
                //System.out.println("agregar a MT "+palabrasRaices.get(literalesDeUsuario.get(h)));
                MT.add(palabrasRaices.get(literalesDeUsuario.get(h)));
        }

        System.out.println("MT: ");
        for(Literal literal : MT) System.out.println(literal);
        //SE REALIZA LA INFERENCIA

        ArrayList<Regla> reglasCotejadas = new ArrayList<>();
        faseCotejo(reglasCotejadas);
        faseResolucion(reglasCotejadas);
        return faseEjecucion(reglasCotejadas, palabrasClavesMacheadasInterface);
    }


    private void especificidad(ArrayList<Regla> reglasCotejadas) {
        if (reglasCotejadas.size() > 1) {
            ArrayList<Regla> procesoDeInferir = new ArrayList<>();
            for (int x = 0; x < reglasCotejadas.size(); x++) {
                for (int i = 0; i < reglasCotejadas.size() - x - 1; i++) {
                    if (reglasCotejadas.get(i).getAntecedentePalabrasClaves().size() < reglasCotejadas.get(i + 1).getAntecedentePalabrasClaves().size()) {
                        Regla tmp = reglasCotejadas.get(i + 1);
                        reglasCotejadas.set(i + 1, reglasCotejadas.get(i));
                        reglasCotejadas.set(i, tmp);
                    }
                }
            }

            procesoDeInferir.add(reglasCotejadas.get(0));
            for (Regla regla : reglasCotejadas.subList(1, reglasCotejadas.size())) {
                if (reglasCotejadas.get(0).getAntecedentePalabrasClaves().size() == regla.getAntecedentePalabrasClaves().size()) {
                    procesoDeInferir.add(regla);
                } else {
                    break;
                }
            }

            if (procesoDeInferir.size() > 0) {
                reglasCotejadas.clear();
                reglasCotejadas.addAll(procesoDeInferir);
            }
        }
    }

    private void novedad(ArrayList<Regla> reglasCotejadas) {
        if (reglasCotejadas.size() > 1) {
            ArrayList<Regla> procesoDeInferir = new ArrayList<>();
            for (int x = 0; x < reglasCotejadas.size(); x++) {
                for (int i = 0; i < reglasCotejadas.size() - x - 1; i++) {
                    if (reglasCotejadas.get(i).getNovedad() < reglasCotejadas.get(i + 1).getNovedad()) {
                        Regla tmp = reglasCotejadas.get(i + 1);
                        reglasCotejadas.set(i + 1, reglasCotejadas.get(i));
                        reglasCotejadas.set(i, tmp);
                    }
                }
            }

            procesoDeInferir.add(reglasCotejadas.get(0));
            for (Regla regla : reglasCotejadas.subList(1, reglasCotejadas.size())) {
                if (reglasCotejadas.get(0).getNovedad() == regla.getNovedad()) {
                    procesoDeInferir.add(regla);
                } else {
                    break;
                }
            }

            if (procesoDeInferir.size() > 0) {
                reglasCotejadas.clear();
                reglasCotejadas.addAll(procesoDeInferir);
            }
        }
    }

    private void noDuplicidad(ArrayList<Regla> reglasCotejadas) {
        if (reglasCotejadas.size() > 1) {
            for (Regla reglaActiva : reglasActivas) {
                reglasCotejadas.remove(reglaActiva);
            }
        }
    }

    private void prioridad(ArrayList<Regla> reglasCotejadas) {
        if (reglasCotejadas.size() > 1) {
            ArrayList<Regla> procesoDeInferir = new ArrayList<>();
            for (int x = 0; x < reglasCotejadas.size(); x++) {
                for (int i = 0; i < reglasCotejadas.size() - x - 1; i++) {
                    if (reglasCotejadas.get(i).getPrioridad() < reglasCotejadas.get(i + 1).getPrioridad()) {
                        Regla tmp = reglasCotejadas.get(i + 1);
                        reglasCotejadas.set(i + 1, reglasCotejadas.get(i));
                        reglasCotejadas.set(i, tmp);
                    }
                }
            }

            procesoDeInferir.add(reglasCotejadas.get(0));
            for (Regla regla : reglasCotejadas.subList(1, reglasCotejadas.size())) {
                if (reglasCotejadas.get(0).getPrioridad() == regla.getPrioridad()) {
                    procesoDeInferir.add(regla);
                } else {
                    break;
                }
            }

            if (procesoDeInferir.size() > 0) {
                reglasCotejadas.clear();
                reglasCotejadas.addAll(procesoDeInferir);
            }
        }
    }

    private void aleatoriedad(ArrayList<Regla> reglasCotejadas) {
        if (reglasCotejadas.size() > 1) {
            Collections.shuffle(reglasCotejadas);
        }
    }

    private void faseResolucion(ArrayList<Regla> reglasCotejadas) {
        noDuplicidad(reglasCotejadas);
        System.out.println("Luego de filtrar por no duplicidad queda/n:");
        for(Regla r : reglasCotejadas) System.out.println("Regla "+r.getId()+": antecedentes="+r.getAntecedentePalabrasClaves());
        if(reglasCotejadas.size()<=1) return;
        especificidad(reglasCotejadas);
        System.out.println("Luego de filtrar por especificidad queda/n:");
        for(Regla r : reglasCotejadas) System.out.println("Regla "+r.getId()+": antecedentes="+r.getAntecedentePalabrasClaves());
        if(reglasCotejadas.size()<=1) return;
        novedad(reglasCotejadas);
        System.out.println("Luego de filtrar por novedad queda/n:");
        for(Regla r : reglasCotejadas) System.out.println("Regla "+r.getId()+": antecedentes="+r.getAntecedentePalabrasClaves());
        if(reglasCotejadas.size()<=1) return;
        prioridad(reglasCotejadas);
        System.out.println("Luego de filtrar por prioridad queda/n:");
        for(Regla r : reglasCotejadas) System.out.println("Regla "+r.getId()+": antecedentes="+r.getAntecedentePalabrasClaves());
        if(reglasCotejadas.size()<=1) return;
        aleatoriedad(reglasCotejadas);
        System.out.println("Luego de seleccionar aleatoriamente queda:");
        for(Regla r : reglasCotejadas) System.out.println("Regla "+r.getId()+": antecedentes="+r.getAntecedentePalabrasClaves());
    }

    private void faseCotejo(ArrayList<Regla> reglasCotejadas) {
        System.out.println("COTEJO");
        for (Regla r : MP) {
            int literalesDelAntecedente = r.getAntecedentePalabrasClaves().size();
            int countLiterals = 0;
            for (Literal l : r.getAntecedentePalabrasClaves()) {
                if (MT.contains(l)) {
                    countLiterals++;
                }
            }
            if (countLiterals == literalesDelAntecedente) {
                reglasCotejadas.add(r);
                if (r.getNovedad() == 0) r.setNovedad(NUMERO_DE_INFERENCIA);
                System.out.println("Regla "+r.getId()+": antecedentes="+r.getAntecedentePalabrasClaves());
            }

        }
    }

    private String faseEjecucion(ArrayList<Regla> reglasCotejadas, PalabrasClavesMacheadasInterface palabrasClavesMacheadasInterface) {
        if (reglasCotejadas.size() >= 1) {
            reglasActivas.add(reglasCotejadas.get(0));
            palabrasClavesMacheadasInterface.palabrasMacheadas(reglasCotejadas.get(0).getAntecedentePalabrasClaves());
            reglasCotejadas.get(0).ejecutar();
            Collections.shuffle(reglasCotejadas.get(0).getRespuestasDialogo());
            System.out.println("Agente dice: "+reglasCotejadas.get(0).getRespuestasDialogo().get(0));
            return reglasCotejadas.get(0).getRespuestasDialogo().get(0);
        } else {
            System.err.println("HUBO UN ERROR AL INFERIR 1 SOLA REGLA, cant. reglas inferidas: " + reglasCotejadas.size());
            Collections.shuffle(dialogosDefault);
            return dialogosDefault.get(0);
        }
    }

    private void setDialogosDefault() {
        dialogosDefault.add("No entiendo lo que quieres decir, ¿por favor me lo puedes repetir?");
        dialogosDefault.add("No te estaria entendiendo");
        dialogosDefault.add("¿Podrias explicarte un poco mejor?");
        dialogosDefault.add("¿Podrias explicarte con otras palabras?");
        dialogosDefault.add("Hablando de esa manera no llegaremos a un acuerdo");
        dialogosDefault.add("Debes responderme lo que te estoy preguntando");
        dialogosDefault.add("Yo pregunto, tú respondes, simple no ?");
        dialogosDefault.add("Ayudame a ayudarte");
    }
}
