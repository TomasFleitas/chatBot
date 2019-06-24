package Logica;

import Logica.Literal;
import Logica.Regla;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CargadorReglas {

    private static ArrayList<Regla> reglas;
    private static ArrayList<Literal> palabrasClaves;
    private static HashMap<String,ArrayList<String>> preguntas;
    private static HashMap<String,ArrayList<Literal>> grupos;
    private static HashMap<Literal,Literal> palabrasRaices;
    private static HashMap<String,ArrayList<Pair<Literal,String>>> grafo;
    private static int pos;

    private static void inicializar(){
        reglas = new ArrayList<>();
        palabrasClaves = new ArrayList<>();
        String filePath = "./input.in";//TODO cargar path
        File file = new File(filePath);
        if(file.exists()) {
            String strData;
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                fis.close();
                strData = new String(data, "UTF-8");
                pos=0;
                preguntas = new HashMap<>();
                grupos = new HashMap<>();
                grafo = new HashMap<>();
                palabrasClaves = new ArrayList<>();
                reglas = new ArrayList<>();
                palabrasRaices = new HashMap<>();
                parsearPreguntas(strData);
                parsearGrupos(strData);
                parsearRelaciones(strData);
                buildReglas();
            } catch (IOException ex) {
                System.out.println("Error al leer el archivo: "+filePath);
            }
        }
        else System.out.println("No se pudo encontrar el archivo: "+filePath);
    }



    private static void parsearPreguntas(String str) {
        int nextPos = str.indexOf('\n',pos)-1;
        int cantGruposPreguntas = Integer.valueOf(str.substring(pos,nextPos));
        pos = nextPos+2;
        for(int i=0;i<cantGruposPreguntas;i++){
            nextPos = str.indexOf('\n',pos)-1;
            String nombreGrupo = str.substring(pos,nextPos);
            pos = nextPos+2;
            nextPos = str.indexOf('\n',pos)-1;
            int cantPregs = Integer.valueOf(str.substring(pos,nextPos));
            pos = nextPos+2;
            for(int j=0;j<cantPregs;j++) {
                nextPos = str.indexOf('\n', pos)-1;
                String pregunta = str.substring(pos, nextPos);
                pos = nextPos + 2;
                if(!preguntas.containsKey(nombreGrupo)) preguntas.put(nombreGrupo,new ArrayList<>());
                preguntas.get(nombreGrupo).add(pregunta);
            }
        }
    }
    private static void parsearGrupos(String str) {
        int nextPos = str.indexOf('\n',pos)-1;
        int cantGrupos = Integer.valueOf(str.substring(pos,nextPos));
        pos = nextPos+2;
        for(int i=0;i<cantGrupos;i++){
            //System.out.println(i+" "+cantGrupos);
            String nombreGrupo="";
            Literal palabraRaiz = new Literal();
            String acum="";
            while(str.charAt(pos)!='\n'){
                if(str.charAt(pos)==':'){
                    nombreGrupo=acum;
                    grupos.put(nombreGrupo,new ArrayList<>());
                    acum="";
                }
                else if(str.charAt(pos)==','){
                    Literal auxLiteral = new Literal(acum);
                    if(grupos.get(nombreGrupo).size()==0) {
                        palabraRaiz=auxLiteral;
                        grupos.get(nombreGrupo).add(auxLiteral);
                        palabrasRaices.put(palabraRaiz,palabraRaiz);
                        palabrasClaves.add(auxLiteral);
                    }
                    else palabrasRaices.put(auxLiteral,palabraRaiz);
                    //System.out.println(acum);
                    acum="";
                }
                else acum+=str.charAt(pos);
                pos++;
            }
            acum=acum.substring(0,acum.length()-1);
            Literal auxLiteral = new Literal(acum);
            if(grupos.get(nombreGrupo).size()==0) {
                palabraRaiz=auxLiteral;
                grupos.get(nombreGrupo).add(auxLiteral);
                palabrasRaices.put(palabraRaiz,palabraRaiz);
                palabrasClaves.add(auxLiteral);
            }
            else palabrasRaices.put(auxLiteral,palabraRaiz);
            //System.out.println(acum);
            pos++;
        }
    }
    private static void parsearRelaciones(String str) {
        int nextPos = str.indexOf('\n',pos)-1;
        int cantRelaciones = Integer.valueOf(str.substring(pos,nextPos));
        pos = nextPos+2;
        for(int i=0;i<cantRelaciones;i++) {
            nextPos = str.indexOf(' ',pos);
            String preg1 = str.substring(pos,nextPos);
            pos = nextPos+1;
            nextPos = str.indexOf(' ',pos);
            String grupo = str.substring(pos,nextPos);
            pos = nextPos+1;
            nextPos = str.indexOf('\n',pos)-1;
            String preg2 = str.substring(pos,nextPos);
            pos = nextPos+2;
            //System.out.println(preg1+" "+grupo+" "+preg2);
            //System.out.println(preguntas.get(preg1)+" "+grupos.get(grupo)+" "+preguntas.get(preg2));

            for(Literal literal : grupos.get(grupo)){
                if(!grafo.containsKey(preg1)) grafo.put(preg1,new ArrayList<>());
                grafo.get(preg1).add(new Pair<Literal,String>(literal,preg2));
                //System.out.println("arista: "+preg1+"->"+preg2+"("+literal+")");
            }
        }
    }

    private static void buildReglas() {
        String nodoInicial = "#Saludo";
        //System.out.println("Comenzo construccion de reglas");
        buildReglas(nodoInicial,new HashSet<String>(),new HashSet<>());
        //System.out.println("Finalizo construccion de reglas");
    }

    private static void buildReglas(String nodo, HashSet<String> usados, HashSet<Literal> antecedentes) {
        int cantNext=0;
        usados.add(nodo);
        //System.out.println(nodo);
        //System.out.println(grafo.get(nodo));
        //System.out.println(nodo+"> "+antecedentes);

        if(grafo.get(nodo)==null)
        {
           // System.out.println("entro al if");
            ArrayList<String> aux = new ArrayList<>();
            aux.add("He encontrado el producto perfecto para ti, un vendedor seguirá el proceso");
            ArrayList<Literal> auxAntecedentes= new ArrayList<>();
            auxAntecedentes.addAll(antecedentes);
            reglas.add(new Regla(reglas.size()+1,auxAntecedentes,1,aux));
            //System.out.println("REGLA "+reglas.size()+" "+auxAntecedentes+" "+aux);
            //System.out.println("Producto final"+reglas.size());
        }
        else for(Pair<Literal,String> next : grafo.get(nodo))if(!usados.contains(next.getValue())){
            //System.out.println(nodo+" "+next.getKey()+" "+next.getValue());
            cantNext++;
            antecedentes.add(next.getKey());
            ArrayList<Literal> auxAntecedentes= new ArrayList<>();
            auxAntecedentes.addAll(antecedentes);
            reglas.add(new Regla(reglas.size()+1,auxAntecedentes,1,preguntas.get(next.getValue())));//TODO falta ver tema de prioridad
            //System.out.println("REGLA "+reglas.size()+" "+auxAntecedentes+" "+preguntas.get(next.getValue()));
            //System.out.println(nodo+"->"+next.getValue()+"("+next.getKey()+")");
            buildReglas(next.getValue(),usados,antecedentes);
            antecedentes.remove(next.getKey());

        }
        /*if(cantNext==0)
        {
            ArrayList<String> aux = new ArrayList<>();
            aux.add("He encontrado el producto perfecto para ti, un vendedor seguirá el proceso");
            reglas.add(new Regla(reglas.size()+1,antecedentes,1,aux));
            System.out.println("Producto final"+reglas.size());
        }*/
        usados.remove(nodo);
        return;
    }

    public static ArrayList<Regla> getReglas() {
        if(reglas==null || reglas.isEmpty()) inicializar();
        return reglas;
    }

    public static ArrayList<Literal> getPalabrasClaves() {
        if(palabrasClaves==null || palabrasClaves.isEmpty()) inicializar();
        return palabrasClaves;
    }

    public static HashMap<Literal, Literal> getPalabrasRaices() {
        return palabrasRaices;
    }
}
