import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;

public class MenuPrincipale {
    private final String[] scelte = {"Aggiungi un pianeta", "Aggiungi una luna", "Visualizza tutti i corpi", "Rimuovi la stella", "Rimuovi un pianeta", "Rimuovi una luna", "Visualizza un corpo a scelta", "Controlla se un corpo appartiene al sistema"};
    private final Menu menu = new Menu("QUESTO É IL MENU PRINCIPALE", scelte, true, true, true);



    public MenuPrincipale(){

    }

    public void interazione(Planetario p1){
        boolean continuare;
        do{
            int scelta = menu.choose();
            switch (scelta) {
                case 1 ->
                    p1.creaPianeta();
                case 2 ->
                    p1.creaLuna();
                case 3 ->
                    p1.stampa_lista_corpi();
                case 4 ->
                    p1.rimuovi_stella();
                case 5 -> {
                    p1.stampa_pianeti();
                    String nome_r = InputData.readNonEmptyString("Inserisci il nome del pianeta da rimuovere: ", true);
                    Pianeta pianeta_r = (Pianeta) p1.toCorpo(nome_r);
                    p1.rimuovi_pianeta(pianeta_r);
                }
                case 6 -> {
                    p1.stampa_lune();
                    String nome_r = InputData.readNonEmptyString("Inserisci il nome della luna da rimuovere: ", true);
                    Luna luna_r = (Luna) p1.toCorpo(nome_r);
                    p1.rimuovi_luna(luna_r);
                }
                case 7 ->
                    p1.stampa_info_corpo();
                case 8 -> {
                    String nome_corpo = InputData.readNonEmptyString("Quale corpo vuoi controllare che sia nel sistema?\n>", true);
                    p1.appartiene(nome_corpo);
                }
                default -> {
                }
            }

            if(p1.getLista_corpi().size()==0){break;}

            continuare = InputData.readYesOrNo("Vuoi fare altro?");

        }while(continuare);
    }



}
