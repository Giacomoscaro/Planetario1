import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;

public class MenuPrincipale {
    private final String[] scelte = {"Aggiungi un pianeta", "Aggiungi una luna", "Visualizza tutti i corpi", "Resetta il sistema", "Rimuovi un pianeta", "Rimuovi una luna", "Visualizza un corpo a scelta", "Controlla se un corpo appartiene al sistema", "Visualizza tutti i satelliti di un corpo", "Calcola il centro di massa del sistema", "Calcola la rotta migliore tra due corpi", "Controllo collisioni"};
    private final String[] editor = {"Aggiungi un Pianeta", "Aggiungi una Luna", "Rimuovi un Pianeta", "Rimuovi una Luna"};
    private final String[] info = {"Visualizza tutti i Corpi", "Visualizza un Corpo a scelta", "Visualizza i Satelliti di un corpo", "Controllo che un corpo appartiente al Sistema"};
    private final String[] funzionalita = {"Calcola il centro di massa del Sistema", "Controlla le Collisioni", "Calcola le rotta migliore tra due corpi", "Resetta il Sistema"};
    private final Menu menu = new Menu("QUESTO É IL MENU PRINCIPALE", scelte, true, true, true);
    private final Menu m1 = new Menu("Editor", editor, true, true, true);
    private final Menu m2 = new Menu("InfoCorpi", info, true, true, true);
    private final Menu m3 = new Menu("Funzionalità", funzionalita, true, true, true);

    public MenuPrincipale() {

    }
    public void editor(Planetario p1) {
        int scelta = m1.choose();
        switch (scelta) {
            case 1 -> p1.creaPianeta();
            case 2 -> p1.creaLuna();
            case 3 -> {
                p1.stampa_pianeti();
                String nome_r = InputData.readNonEmptyString("Inserisci il nome del pianeta da rimuovere: ", true);
                Pianeta pianeta_r = (Pianeta) p1.toCorpo(nome_r);
                p1.rimuovi_pianeta(pianeta_r);
            }
            case 4 -> {
                p1.stampa_lune();
                String nome_r = InputData.readNonEmptyString("Inserisci il nome della luna da rimuovere: ", true);
                Luna luna_r = (Luna) p1.toCorpo(nome_r);
                p1.rimuovi_luna(luna_r);
            }
        }
    }

    public void info(Planetario p1) {
        int scelta = m2.choose();
        switch (scelta) {
            case 1 -> p1.stampa_lista_corpi();
            case 2 -> p1.stampa_info_corpo();
            case 3 -> {
                System.out.println(p1.getLista_corpi().get(0).getNome() + "\n");
                p1.stampa_pianeti();
                String nome_corpo = InputData.readNonEmptyString("Inserisci il corpo di cui vuoi vedere i satelliti :\t", true);
                p1.toCorpo(nome_corpo).visualizza_satelliti();
            }
            case 4 -> {
                String nome_corpo = InputData.readNonEmptyString("Quale corpo vuoi controllare che sia nel sistema?\n>", true);
                p1.appartiene(nome_corpo);
            }
        }
    }

    public void funzionalita(Planetario p1) {
        int scelta = m3.choose();
        switch (scelta) {
            case 1 ->
                    System.out.println("Il centro di massa del sistema si trova nel punto C = (" + p1.CDM().getX() + " , " + p1.CDM().getY() + ")\n");

            case 2 -> {
                //con un doppio ciclo for controlla che ciascun corpo possa collidere con un altro e stampa un avviso in caso affermativo
                Corpo c1, c2;
                for (int i = 0; i < p1.getLista_corpi().size(); i++) {
                    c1 = p1.getLista_corpi().get(i);
                    for (int j = i; j < p1.getLista_corpi().size(); j++) {
                        c2 = p1.getLista_corpi().get(j);
                        if (!c1.equals(c2) && Corpo.collidono(c1, c2))
                            System.out.println(AnsiColors.RED + "ATTENZIONE\ti corpi " + c1.getNome() + " e " + c2.getNome() + " potrebbero COLLIDERE in futuro\t\t" + AnsiColors.WHITE + "è consigliabile prendere dei provvedimenti a riguardo\n" + AnsiColors.RESET);
                    }
                }


            }
            case 3 -> p1.calcolo_rotta();
            case 4 -> p1.rimuovi_stella();
        }
    }

    public void interazione(Planetario p1) {
        boolean continuare;
        do {
            int scelt = menu.choose();
            switch (scelt) {
                case 1 -> editor(p1);
                case 2 -> info(p1);
                case 3 -> funzionalita(p1);
                default -> {
                }
            }
            if (p1.getLista_corpi().size() == 0) {
                break;
            }

            continuare = InputData.readYesOrNo("Vuoi fare altro?");
        } while (continuare);
    }


    /*public void interazione(Planetario p1){
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
                case 9 -> {
                    System.out.println(p1.getLista_corpi().get(0).getNome() + "\n");
                    p1.stampa_pianeti();
                    String nome_corpo = InputData.readNonEmptyString("Inserisci il corpo di cui vuoi vedere i satelliti :\t", true);
                    p1.toCorpo(nome_corpo).visualizza_satelliti();
                }
                case 10 ->
                    System.out.println("Il centro di massa del sistema si trova nel punto C = (" + p1.CDM().getX() + " , " + p1.CDM().getY() + ")\n");
                case 11 ->
                    p1.calcolo_rotta();
                case 12 -> {
                    //con un doppio ciclo for controlla che ciascun corpo possa collidere con un altro e stampa un avviso in caso affermativo
                    Corpo c1, c2;
                    for(int i=0; i<p1.getLista_corpi().size(); i++){
                        c1= p1.getLista_corpi().get(i);
                        for(int j=i; j<p1.getLista_corpi().size(); j++){
                            c2= p1.getLista_corpi().get(j);
                            if(!c1.equals(c2) && Corpo.collidono(c1, c2))
                                System.out.println(AnsiColors.RED + "ATTENZIONE\ti corpi " + c1.getNome() + " e " + c2.getNome() + " potrebbero COLLIDERE in futuro\t\t" + AnsiColors.WHITE + "è consigliabile prendere dei provvedimenti a riguardo\n" + AnsiColors.RESET);
                        }
                    }
                }
                default -> {
                }
            }

            if(p1.getLista_corpi().size()==0){break;}

            continuare = InputData.readYesOrNo("Vuoi fare altro?");

        }while(continuare);
    } */

}
