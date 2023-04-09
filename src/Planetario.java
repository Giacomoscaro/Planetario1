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

    public void setNome_sistema(String nome_sistema) {
        this.nome_sistema = nome_sistema;
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
}
