
public class Main {
    public static void main(String[] args) {
        Planetario p1 = Planetario.crea_planetario();
        p1.creaStella();
        p1.creaPianeta();
        p1.creaLuna();
        
        p1.stampa_lista_corpi();
    }
}