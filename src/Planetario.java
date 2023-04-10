import it.kibo.fp.lib.*;

import java.util.ArrayList;

public class Planetario {
    private ArrayList<Corpo> lista_corpi = new ArrayList<Corpo>(10);
    private String nome_sistema;
    public Planetario(String nome_sistema){
    this.nome_sistema = nome_sistema;
    }

    public static Planetario crea_planetario(){
        System.out.println("Crea un nuovo planetario\n");
        String nome = InputData.readNonEmptyString("Inserire il nome: ", true);
        return new Planetario(nome);
    }

    public ArrayList<Corpo> getLista_corpi() {
        return lista_corpi;
    }

    public String getNome_sistema() {
        return nome_sistema;
    }
    
    public void stampa_lista_corpi() {
    	for(Corpo corpo: lista_corpi) {
    		System.out.print(corpo.toString());
    	}
    }
    
    public void creaStella(){
        String nome = InputData.readNonEmptyString("nome stella:", true);
        double massa = InputData.readDoubleWithMinimum("massa stella:", 0);
        double raggio_corpo = InputData.readDoubleWithMinimum("raggio stella: ", 0);
        Stella stella = new Stella(nome , massa, raggio_corpo);
	//lista_corpi = new ArrayList<Corpo>();
        lista_corpi.add(stella);
    }

    public void creaPianeta(){
        String nome = InputData.readNonEmptyString("nome pianeta:", true);
        Posizione posizione = new Posizione(InputData.readDouble("inserire x:"), InputData.readDouble("inserire y:"));
        double massa = InputData.readDoubleWithMinimum("massa pianeta:", 0);
        double raggio_corpo = InputData.readDoubleWithMinimum("raggio pianeta: ", 0);
        Corpo padre = lista_corpi.get(0);
        Pianeta pianeta = new Pianeta(nome, posizione, massa, raggio_corpo, padre);
        
        /*
         * Controllare che il pianeta non si sovrapponga ad altri corpi
         *  e, nel caso, chiedere l'inserimento di una posizione valida
         */
        while(sovrapposto(pianeta)) {
        	System.out.println(AnsiColors.RED + "Il pianeta si sovrappone con altri corpi, inserisci una posizione valida" + AnsiColors.RESET);
        	posizione = new Posizione(InputData.readDouble("inserire x:"), InputData.readDouble("inserire y:"));
        	pianeta.setPosizione(posizione);
        }
        
        lista_corpi.add(pianeta);
        lista_corpi.get(0).aggiungi_satellite(pianeta);
    }

    public void stampa_pianeti(){
        //serve per avere una lista dei pianeti per scegliere il corpo padre di una luna
        for (Corpo corpo : lista_corpi) {
            if (corpo.getClass() == Pianeta.class) {
                System.out.println(corpo.getNome() + "\n");
            }
        }
    }

    public Corpo toCorpo(String nome){
        //serve per avere subito il corpo corrispondente a un nome
        Corpo c=null;
        for (Corpo corpo : lista_corpi) {
            if (corpo.getNome().equals(nome)) {
                c = corpo;
            }
        }
        return c;
    }

    public void creaLuna(){
        String nome = InputData.readNonEmptyString("nome luna:", true);
        Posizione posizione = new Posizione(InputData.readDouble("inserire x:"), InputData.readDouble("inserire y:"));
        double massa = InputData.readDoubleWithMinimum("massa luna:", 0);
        double raggio_corpo = InputData.readDoubleWithMinimum("raggio luna: ", 0);
        stampa_pianeti();
        String nome_padre = InputData.readNonEmptyString("Scegli il pianeta padre dalla lista: ", true);
        Corpo padre = toCorpo(nome_padre);
        Luna luna = new Luna(nome, posizione, massa, raggio_corpo, padre);
        
        /*
         * Controllare che la luna non si sovrapponga ad altri corpi
         *  e, nel caso, chiedere l'inserimento di una posizione valida
         */
        while(sovrapposto(luna)) {
        	System.out.println(AnsiColors.RED + "La luna si sovrappone con altri corpi, inserisci una posizione valida" + AnsiColors.RESET);
        	posizione = new Posizione(InputData.readDouble("inserire x:"), InputData.readDouble("inserire y:"));
        	luna.setPosizione(posizione);
        }
        
        lista_corpi.add(luna);
        lista_corpi.get(lista_corpi.indexOf(padre)).aggiungi_satellite(luna);
    }

    public void rimuovi_stella(){
        System.out.println("Hai deciso di rimuovere la stella dal tuo sistema solare, il che porterà ad una sua completa distruzione, addio " + this.getNome_sistema());
        lista_corpi.clear();
    }

    public void rimuovi_pianeta(Pianeta pianeta){
        for(int i=0; i<pianeta.getSatelliti().size(); i++){
            lista_corpi.remove(pianeta.getSatelliti().get(i));
        }
        pianeta.getSatelliti().clear();
        pianeta.getPadre().getSatelliti().remove(pianeta);
        lista_corpi.remove(pianeta);
    }

    public void rimuovi_luna(Luna luna){
        lista_corpi.remove(luna);
        luna.getPadre().getSatelliti().remove(luna);
    }

    public void stampa_lune(){
        //serve per avere una lista delle lune
        for (Corpo corpo : lista_corpi) {
            if (corpo.getClass() == Luna.class) {
                System.out.println(corpo.getNome() + "\n");
            }
        }
    }

    public void stampa_info_corpo(){
        System.out.println("Scegli di quale corpo vedere le caratteristiche: ");
        for(Corpo corpo : lista_corpi){
            System.out.println(corpo.getNome() + "\n");
        }
        String nome = InputData.readNonEmptyString("\n>", true);
        Corpo corpo = toCorpo(nome);
        System.out.print(corpo.toString());
    }
    
    /**
     * Restituisce vero se il corpo indicato si sovrappone con un altro corpo
     * della lista_corpi
     * @return TRUE il corpo si sovrappone; FALSE il corpo non si sovrappone
     */
    public boolean sovrapposto(Corpo c) {
    	boolean posizioneValida=false;
        for(Corpo corpo : lista_corpi) {
        	if( Corpo.sovrapposti(c, corpo) ) {
        		posizioneValida=true;
        		break;
        	}
        }
        return posizioneValida;
    }

    /*
    * Ti dice se un corpo è presente nel sistema solare
    * in base al nome
    * Se vero ti stampa le info, se falso ti avverte
     */

    public void appartiene(String nome_corpo){
        boolean appartiene = false;

        for (Corpo corpo : lista_corpi){
            if (nome_corpo.equals(corpo.getNome())) {
                appartiene = true;
                break;
            }
        }

        if (!appartiene)
            System.out.println(nome_corpo + " non appartiene a questo sistema\n");
        else{
            System.out.println(nome_corpo + " appartiene a questo sistema:\n");
            System.out.print(toCorpo(nome_corpo).toString());
        }
    }

    /*
    * La seguente funzione calcola il centro di massa nel sistema stellare
    * Prima trova i dati necessari e poi svolge i calcoli
    * Restituisce una Posizione con x e y del centro di massa
     */
    public Posizione CDM(){
        double somma_masse=0;
        double somma_x=0;
        double somma_y=0;
        for(Corpo corpo : lista_corpi){
            somma_masse += corpo.getMassa();
            somma_x += corpo.getMassa() * corpo.getPosizione().getX();
            somma_y += corpo.getMassa() * corpo.getPosizione().getY();
        }
        return new Posizione(somma_x/somma_masse, somma_y/somma_masse);
    }

    /*
    * Fa selezionare due corpi all'utente, calcola la rotta ottimale
    * secondo le regole stabilite e stampa le tappe della rotta e
    * la sua lunghezza
    * Non è il metodo più efficiente, ma fino a prova contraria funziona
     */
    public void calcolo_rotta() {
        System.out.println("Scegli il corpo di partenza e il corpo d'arrivo dalla lista:\n");
        for (Corpo corpo : lista_corpi)
            System.out.println(corpo.getNome() + "\n"); //lista corpi tra cui scegliere
        Corpo c1 = toCorpo(InputData.readNonEmptyString("partenza >\t", true));
        Corpo c2 = toCorpo(InputData.readNonEmptyString("arrivo >\t", true));
        StringBuffer rotta = new StringBuffer();
        double lunghezza_rotta = 0;
        if(c1.getPadre().equals(c2) || c2.getPadre().equals(c1)) {//se i due corpi sono adiacenti
            rotta.append(c1.getNome() + " > " + c2.getNome());
            lunghezza_rotta += c1.getPosizione().get_distanza(c2.getPosizione());
        }
        else if (c1.getClass().equals(Luna.class) && c2.getClass().equals(Luna.class)) {//se sono due lune ci sono solo due casi
            rotta.append(c1.getNome());
            if (c1.getPadre().equals(c2.getPadre())) {//stesso padre
                rotta.append(" > " + c1.getPadre().getNome());
                lunghezza_rotta += c1.getRaggio_orbita() + c2.getRaggio_orbita();
            }
            else {//padre diverso
                rotta.append(" > " + c1.getPadre().getPadre().getNome());
                lunghezza_rotta += c1.getPosizione().get_distanza(c1.getPadre().getPadre().getPosizione()) + c2.getPosizione().get_distanza(c2.getPadre().getPadre().getPosizione());
            }
            rotta.append(" > " + c2.getNome());
        }
        else if (c1.getClass().equals(Pianeta.class) && c2.getClass().equals(Pianeta.class)) {//due pianeti
            rotta.append(c1.getNome() + " > " + c1.getPadre().getNome() + " > " + c2.getNome());
            lunghezza_rotta += c1.getRaggio_orbita() + c2.getRaggio_orbita();
        }
        else {//i corpi non sono dello stesso tipo e non sono adiacenti
            rotta.append(c1.getNome());

            if (!c1.getClass().equals(Stella.class)) {//il corpo c1 non è la stella
                rotta.append(" > " + c1.getPadre().getNome());
                lunghezza_rotta += c1.getRaggio_orbita();
                if (!c1.getPadre().getClass().equals(Stella.class)) {//c1 non è un pianeta
                    rotta.append(" > " + c1.getPadre().getPadre().getNome());
                    lunghezza_rotta += c1.getPadre().getRaggio_orbita();
                    //c2 non può essere una luna, ma può essere un pianeta o una stella
                    if (!c2.getClass().equals(Stella.class)) {//c2 è un pianeta
                        rotta.append(" > " + c2.getNome());
                        lunghezza_rotta += c2.getRaggio_orbita();
                    }//se c2 è una stella è già tutto calcolato
                }
                else{//c1 è un pianeta e c2 non può essere un pianeta nè una stella, quindi è una luna
                    rotta.append(" > " + c2.getPadre().getNome() + " > " + c2.getNome());
                    lunghezza_rotta += c2.getPadre().getRaggio_orbita() + c2.getRaggio_orbita();
                }
            } else {//l'unica opzione rimasta è che c1 sia la stella
                //c2 non può essere un pianeta, quindi è una luna
                rotta.append(" > " + c2.getPadre().getNome());
                rotta.append(" > " + c2.getNome());
                lunghezza_rotta += c2.getPadre().getRaggio_orbita() + c2.getRaggio_orbita();
            }
        }
        System.out.println("La rotta consigliata per viaggiare da " + c1.getNome() + " a " + c2.getNome() + " è : \n" + rotta + "\nE la lunghezza del viaggio sarà di " + AnsiColors.CYAN + lunghezza_rotta + AnsiColors.RESET);
    }
}
