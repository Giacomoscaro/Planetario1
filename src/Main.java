
public class Main {
    public static void main(String[] args) {
        Planetario p1 = Planetario.crea_planetario();
        p1.creaStella();
        p1.creaPianeta();
        p1.creaLuna();
        //p1.rimuovi_luna((Luna)p1.getLista_corpi().get(2));
        //p1.rimuovi_pianeta((Pianeta)p1.getLista_corpi().get(1));
        //p1.rimuovi_stella();
        //p1.stampa_lune();

        p1.stampa_lista_corpi();
    }
}