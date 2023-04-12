import it.kibo.fp.lib.*;

import java.util.ArrayList;

public class Planetario {
    private ArrayList<Corpo> lista_corpi = new ArrayList<Corpo>(10);
    private final String nome_sistema;
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
        System.out.println("Hai deciso di eliminare il tuo sistema stellare\n");
        boolean sicuro = InputData.readYesOrNo(AnsiColors.PURPLE + "Ne sei davvero sicuro");
        if(sicuro) {
            lista_corpi.clear();
            System.out.println(AnsiColors.RESET + "\nAddio " + getNome_sistema());
        }
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
            System.out.println(nome_corpo + " appartiene a questo sistema\nEd ecco le sue informazioni:\n");
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

    public ArrayList<Corpo> percorso(Corpo c1, Corpo c2){
    	ArrayList<Corpo> lista1 = new ArrayList<Corpo>();
    	ArrayList<Corpo> lista2 = new ArrayList<Corpo>();
    	ArrayList<Corpo> listadef = new ArrayList<Corpo>();
    	lista1.add(c1);
    	lista2.add(c2);
    	
    	if(c1==c2)
    		return listadef;
    	
    	while(c1 != c2) {
    		if(c1.getPadre()!=c1) {
    			c1=c1.getPadre();
    			lista1.add(c1);
    		}
    		
		if(c1==c2)
			break;

    		if(c2.getPadre()!=c2) {
    			c2=c2.getPadre();
    			lista2.add(0,c2);
    		}
    	}
    	
    	//aggiungo tutti gli elementi di lista1
    	for(Corpo corpo : lista1)
    		listadef.add(corpo);
    	
    	//aggiungo tutti gli elementi di lista2
    	for(Corpo corpo : lista2)
    		listadef.add(corpo);
    	
    	//rimuovo il corpo padre in comune (che è presente in 2 copie)
    	listadef.remove(listadef.lastIndexOf(c2));
    	
    	return listadef;

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
        ArrayList<Corpo> percorso = percorso(c1,c2);
        
        //calcolo lunghezza rotta
        for(int i=0; i<percorso.size()-1; i++) {
        	lunghezza_rotta+= Posizione.distanza( percorso.get(i).getPosizione(), percorso.get(i+1).getPosizione() );
        }
        
        //creazione StringBuffer rotta
        for(int i=0; i<percorso.size()-1; i++) {
        	rotta.append(percorso.get(i).getNome() + " > ");
        }
        rotta.append(percorso.get(percorso.size()-1).getNome() );
        
        System.out.println("La rotta consigliata per viaggiare da " + c1.getNome() + " a " + c2.getNome() + " è : \n" + rotta + "\nE la lunghezza del viaggio sarà di " + AnsiColors.CYAN + lunghezza_rotta + AnsiColors.RESET);
    }
}
