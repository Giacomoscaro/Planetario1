import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;

public class MenuPrincipale {
    private final String[] scelte = {"Editor Corpi", "Centro Informazioni", "Strumenti e Funzionalità"};
    private final String[] editor = {"Aggiungi un Pianeta", "Aggiungi una Luna", "Rimuovi un Pianeta", "Rimuovi una Luna"};
    private final String[] info = {"Visualizza tutti i Corpi", "Visualizza un Corpo a scelta", "Visualizza i Satelliti di un corpo", "Controllo che un corpo appartiente al Sistema"};
    private final String[] funzionalita = {"Calcola il centro di massa del Sistema", "Controlla le Collisioni", "Calcola le rotta migliore tra due corpi", "Resetta il Sistema"};
    private final Menu menu = new Menu(AnsiColors.BLUE + "MENU PRINCIPALE" + AnsiColors.RESET, scelte, true, true, true);
    private final Menu m1 = new Menu(AnsiColors.YELLOW + "EDITOR CORPI" + AnsiColors.RESET, editor, true, true, true);
    private final Menu m2 = new Menu(AnsiColors.CYAN + "CENTRO INFORMAZIONI" + AnsiColors.RESET, info, true, true, true);
    private final Menu m3 = new Menu(AnsiColors.GREEN + "STRUMENTI E FUNZIONALITÀ" + AnsiColors.RESET, funzionalita, true, true, true);

    public MenuPrincipale() {

    }

    /**
     * Primo dei menu secondari. Permette di creare o distruggere corpi nel sistema.
     * @param p1
     */
    public void editor(Planetario p1) {
        System.out.println("\n\n\n\n\n");
        int scelta = m1.choose();
        boolean principale;
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
                default -> {
                }
            }
            principale = InputData.readYesOrNo("Vuoi tornare al menu principale?");
            //TRUE riapre il menu principale; FALSE ti riapre l'editor
            if(principale)
                interazione(p1);
            else editor(p1);
    }

    /**
     * Secondo dei menu secondari. Non modifica nulla nel sistema ma contiene tutte le informazioni utili
     * @param p1
     */
    public void info(Planetario p1) {
        System.out.println("\n\n\n\n\n");
        int scelta = m2.choose();
        boolean principale;
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
            default -> {}
        }
        principale = InputData.readYesOrNo("Vuoi tornare al menu principale?");
        if(principale)
            interazione(p1);
        else info(p1);
    }

    /**
     * Terzo dei menu secondari. Permette di svolgere le funzioni più complesse del programma o di resettarlo
     * @param p1
     */
    public void funzionalita(Planetario p1) {
        System.out.println("\n\n\n\n\n");
        int scelta = m3.choose();
        boolean principale;
        switch (scelta) {
            case 1 ->
                System.out.println("Il centro di massa del sistema si trova nel punto C = (" + String.format("%.3f", p1.CDM().getX())  + " , " + String.format("%.3f", p1.CDM().getY()) + ")\n");
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
            default ->{}
        }
        if (p1.getLista_corpi().size() == 0)
            return;//se il sistema viene resettato cessa di esistere, quindi il programma va terminato

        principale = InputData.readYesOrNo("Vuoi tornare al menu principale?");
        if(principale)
            interazione(p1);
        else funzionalita(p1);
    }

    /**
     * Menu principale da cui scegliere a quale dei tre menu secondari accedere
     * @param p1 planetario su cui agirai tramite il menu
     */
    public void interazione(Planetario p1) {
        System.out.println("\n\n\n\n\n");
            int scelt = menu.choose();
            switch (scelt) {
                case 1 -> editor(p1);
                case 2 -> info(p1);
                case 3 -> funzionalita(p1);
                default -> {
                }
            }
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
