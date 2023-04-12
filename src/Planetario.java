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
        String nome = InputData.readNonEmptyString("Inserire il nome del tuo sistema: ", true);
        return new Planetario(nome);
    }

    public ArrayList<Corpo> getLista_corpi() {
        return lista_corpi;
    }

    public String getNome_sistema() {
        return nome_sistema;
    }

    /**
     * Stampa in successione tutte le informazioni dettagliate
     * dei corpi registrati nella lista_corpi in ordine
     */
    public void stampa_lista_corpi() {
    	for(Corpo corpo: lista_corpi) {
    		System.out.print(corpo.toString());
    	}
    }

    /**
     * Crea una stella chiedendo i valori d'ingresso
     * all'utente e la aggiunge alla lista_corpi
     */
    public void creaStella(){
        String nome = InputData.readNonEmptyString("nome stella:", true);
        double massa = InputData.readDoubleWithMinimum("massa stella:", 0);
        double raggio_corpo = InputData.readDoubleWithMinimum("raggio stella: ", 0);
        Stella stella = new Stella(nome , massa, raggio_corpo);
        lista_corpi.add(stella);
    }

    /**
     * Crea un pianeta chiedendo i valori d'ingresso
     * all'utente e la aggiunge alla lista_corpi e
     * ai satelliti della stella
     */
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

    /**
     * Stampa una lista dei nomi di tutti i pianeti del sistema
     */
    public void stampa_pianeti(){
        //serve per avere una lista da cui scegliere il corpo padre di una luna
        for (Corpo corpo : lista_corpi) {
            if (corpo.getClass() == Pianeta.class) {
                System.out.println(corpo.getNome() + "\n");
            }
        }
    }

    /**
     * Cerca attraverso tutti i corpi della lista_corpi e una volta
     * trovato ritorna il corpo che corrisponde al nome inserito
     * @param nome è il nome del corpo da trovare
     * @return il corpo con il nome passato al metodo
     */
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

    /**
     * Crea una luna chiedendo i valori d'ingresso
     * all'utente e la aggiunge alla lista_corpi e
     * ai satelliti del pianeta scelto come padre
     */
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

    /**
     * Rimuove la stella dal sistema, causando un completo collasso di tutti
     * i pianeti che ne facevano parte, distruggendo totalmente l'intero sistema
     */
    public void rimuovi_stella(){
        System.out.println("Hai deciso di eliminare il tuo sistema stellare\n");
        boolean sicuro = InputData.readYesOrNo(AnsiColors.PURPLE + "Ne sei davvero sicuro");
        if(sicuro) {
            lista_corpi.clear();
            System.out.println(AnsiColors.RESET + "\nAddio " + getNome_sistema());
        }
    }

    /**
     * Rimuove il pianeta dalla lista_corpi e dai satelliti della stella
     * @param pianeta quale pianeta va rimosso
     */
    public void rimuovi_pianeta(Pianeta pianeta){
        /*
         * Non ha senso che nel sistema rimanga solo la stella, altrimenti
         * la stella vagherebbe nell'universo e non sarebbe più un sistema
         * quindi se si prova a rimuovere l'ultimo pianeta chiediamo
         * all'utente di sostituirlo con uno nuovo invece
         * */
        int n_pianeti = 0;
        for(Corpo corpo : lista_corpi){
            if(corpo.getClass().equals(Pianeta.class))
                n_pianeti++;
        }
        if(n_pianeti == 1){

           boolean sostituire = InputData.readYesOrNo("Non è consigliato rimuovere l'ultimo pianeta dal tuo sistema, vuoi invece sostituirlo con uno nuovo");
           if(sostituire){
               for(int i=0; i<pianeta.getSatelliti().size(); i++){
                   lista_corpi.remove(pianeta.getSatelliti().get(i));
               }
               pianeta.getSatelliti().clear();
               pianeta.getPadre().getSatelliti().remove(pianeta);
               lista_corpi.remove(pianeta);
               creaPianeta();
           }
           else return;
        }
        for(int i=0; i<pianeta.getSatelliti().size(); i++){
            lista_corpi.remove(pianeta.getSatelliti().get(i));
        }
        pianeta.getSatelliti().clear();
        pianeta.getPadre().getSatelliti().remove(pianeta);
        lista_corpi.remove(pianeta);
    }

    /**
     * Rimuove una luna dalla lista_corpi e dai satelliti del pianeta padre
     * @param luna quale luna va rimossa
     */
    public void rimuovi_luna(Luna luna){
        lista_corpi.remove(luna);
        luna.getPadre().getSatelliti().remove(luna);
    }

    /**
     * Stampa i nomi delle lune per sceglierne una da rimuovere in un altro metodo
     */
    public void stampa_lune(){
        //serve per avere una lista delle lune
        for (Corpo corpo : lista_corpi) {
            if (corpo.getClass() == Luna.class) {
                System.out.println(corpo.getNome() + "\n");
            }
        }
    }

    /**
     * Permette all'utente di scegliere un corpo in particolare di cui stampare
     * tutti i dettagli e utilizza il metodo toString() per stamparli
     */
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

    /**
     * Ti dice se il corpo di cui inserisci il nome come attributo appartiene al
     * sistema o no. Stampa anche un avviso per farti sapere l'esito di questo controllo
     * @param nome_corpo il corpo di cui vuoi controllare l'appartenenza
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

    /**
     * Calcola la sommatoria delle masse del sistema e le due sommatorie dei prodotti per le masse e
     * le coordinate x e y rispettivamente dei corpi del sistema, poi calcola il centro di massa
     * @return la posizione che avrà come parametri x e y le coordinate del centro di massa
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

    /**
     * Compone due liste di corpi sotto forma di ArrayList, che, corpo per corpo, risalgono ai corpi padre
     * dei due corpi inseriti come parametri finché le due liste non arrivano allo stesso corpo e quindi
     * si possono unire l'una all'altra per avere un'ArrayList di tutti i corpi che si trovano tra quello
     * di partenza e quello d'arrivo (compresi). La seconda lista è composta al contrario per facilitare
     * l'unione. Il metodo è usato per il metodo del calcolo della rotta.
     * @param c1 pianeta di partenza
     * @param c2 pianeta d'arrivo
     * @return un'ArrayList contenente in ordine tutti i pianeti tra c1 e c2 (compresi)
     */
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

        //rimuovo il corpo padre in comune (che è presente in due copie)
        listadef.remove(listadef.lastIndexOf(c2));

        return listadef;

    }

    /**
     * Stampa in ordine tutti i pianeti sul percorso da un pianeta di partenza a uno d'arrivo (inseriti
     * entrambi dall'utente) e calcola la lunghezza del percorso per poi stampare anche quella. Se
     * vengono inseriti due pianeti uguali l'utente viene avvertito e viene richiesto di reinserirli
     */
    public void calcolo_rotta() {
        ArrayList<Corpo> percorso;
        Corpo c1;
        Corpo c2;
        do {
            System.out.println("Scegli il corpo di partenza e il corpo d'arrivo dalla lista:\n");
            for (Corpo corpo : lista_corpi)
                System.out.println(corpo.getNome() + "\n"); //lista corpi tra cui scegliere
            c1 = toCorpo(InputData.readNonEmptyString("partenza >\t", true));
            c2 = toCorpo(InputData.readNonEmptyString("arrivo >\t", true));
            percorso = percorso(c1, c2);
            if(percorso.isEmpty()){
                System.out.println("Sei già sul Corpo di arrivo");
            }
        }while (percorso.isEmpty());
        StringBuffer rotta = new StringBuffer();

        double lunghezza_rotta = 0;

        //calcolo lunghezza rotta
        for (int i = 0; i < percorso.size() - 1; i++) {
            lunghezza_rotta += Posizione.distanza(percorso.get(i).getPosizione(), percorso.get(i + 1).getPosizione());
        }

        //creazione StringBuffer rotta
        for (int i = 0; i < percorso.size() - 1; i++) {
            rotta.append(percorso.get(i).getNome() + " > ");
        }
        rotta.append(percorso.get(percorso.size() - 1).getNome());

        System.out.println("La rotta consigliata per viaggiare da " + c1.getNome() + " a " + c2.getNome() + " è : \n" + rotta + "\nE la lunghezza del viaggio sarà di " + AnsiColors.CYAN + lunghezza_rotta + AnsiColors.RESET);

    }
}
