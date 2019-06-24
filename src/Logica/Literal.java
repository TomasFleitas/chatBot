package Logica;

public class Literal {
    private String palabraClave;

    public Literal() {
    }

    public Literal(String palabraClave) {

        this.palabraClave = palabraClave.toLowerCase();
    }

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    @Override
    public boolean equals(Object obj){
    return palabraClave.equalsIgnoreCase(((Literal) obj).getPalabraClave());
    }

    @Override
    public String toString(){
        return palabraClave;
    }

    @Override
    public int hashCode() {
        return palabraClave.hashCode();
    }
}
