package Logica;


import java.util.*;


public class MI {

    private ArrayList<Regla> MP = new ArrayList<>();
    private ArrayList<Literal> MT = new ArrayList<>();
    private ArrayList<Regla> reglasActivas = new ArrayList<>();
    private Integer NUMERO_DE_INFERENCIA = 0;
    private ArrayList<String> dialogosDefault = new ArrayList<>();
    private HashSet<Literal> BAG_KEYWORD = new HashSet<>();

    public interface PalabrasClavesMacheadasInterface {
        void palabrasMacheadas(ArrayList<Literal> palabraInferida);
    }

    public MI() {
        setDialogosDefault();
    }

    public MI(ArrayList<Regla> MP, ArrayList<Literal> MT) {
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

    public String responder(String textoUsuario, PalabrasClavesMacheadasInterface palabrasClavesMacheadasInterface) {
        NUMERO_DE_INFERENCIA++;

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
            if (!BAG_KEYWORD.contains(literalesDeUsuario.get(h))) literalesDeUsuario.remove(h);
        }

        //SE AGRUPAN LOS LITERALES DE LA MI Y LOS LITERALES ESCRITOS POR EL USURIO
        MT.addAll(literalesDeUsuario);

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
                    if (reglasCotejadas.get(i).getNovedad() > reglasCotejadas.get(i + 1).getNovedad()) {
                        Regla tmp = reglasCotejadas.get(i + 1);
                        reglasCotejadas.add(i + 1, reglasCotejadas.get(i));
                        reglasCotejadas.add(i, tmp);
                    }
                }
            }

            procesoDeInferir.add(reglasCotejadas.get(0));
            for (Regla regla : reglasCotejadas.subList(1, reglasCotejadas.size() - 1)) {
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
                if (reglasCotejadas.contains(reglaActiva)) {
                    reglasCotejadas.remove(reglaActiva);
                }
            }
        }
    }

    private void prioridad(ArrayList<Regla> reglasCotejadas) {
        if (reglasCotejadas.size() > 1) {
            ArrayList<Regla> procesoDeInferir = new ArrayList<>();
            for (int x = 0; x < reglasCotejadas.size(); x++) {
                for (int i = 0; i < reglasCotejadas.size() - x - 1; i++) {
                    if (reglasCotejadas.get(i).getPrioridad() > reglasCotejadas.get(i + 1).getPrioridad()) {
                        Regla tmp = reglasCotejadas.get(i + 1);
                        reglasCotejadas.add(i + 1, reglasCotejadas.get(i));
                        reglasCotejadas.add(i, tmp);
                    }
                }
            }

            procesoDeInferir.add(reglasCotejadas.get(0));
            for (Regla regla : reglasCotejadas.subList(1, reglasCotejadas.size() - 1)) {
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
        especificidad(reglasCotejadas);
        novedad(reglasCotejadas);
        prioridad(reglasCotejadas);
        aleatoriedad(reglasCotejadas);
    }

    private void faseCotejo(ArrayList<Regla> reglasCotejadas) {
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
                if (r.getNovedad() != 0) r.setNovedad(NUMERO_DE_INFERENCIA);
            }
        }
    }

    private String faseEjecucion(ArrayList<Regla> reglasCotejadas, PalabrasClavesMacheadasInterface palabrasClavesMacheadasInterface) {
        if (reglasCotejadas.size() == 1) {
            reglasActivas.add(reglasCotejadas.get(0));
            palabrasClavesMacheadasInterface.palabrasMacheadas(reglasCotejadas.get(0).getAntecedentePalabrasClaves());
            return reglasCotejadas.get(0).getRespuestaDialogo();
        } else {
            System.err.println("HUBO UN ERROR AL INFERIR 1 SOLA REGLA, cant. reglas inferidas: " + reglasCotejadas.size());
            Collections.shuffle(dialogosDefault);
            return dialogosDefault.get(0);
        }
    }

    private void setDialogosDefault() {
        dialogosDefault.add("¿No entiendo lo que quieres decir, por favor me lo puedes repetir?");
        dialogosDefault.add("No te estaria entendiendo");
        dialogosDefault.add("¿Podias explicarte un poco mejor?");
    }
}
