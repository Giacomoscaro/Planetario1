import java.util.ArrayList;

public class Corpo {

    private int id; //identificatore unico del corpo
    private String nome;
    private Posizione posizione;
    private double massa;
    private double raggio_orbita;
    private double raggio_corpo;
    private Corpo padre;
    private ArrayList<Corpo> satelliti;
    private Posizione posizione_relativa;
    
    
    private static int contatore=0;
    

    //costruttore per un pianeta o una luna:
    public Corpo(String nome, Posizione posizione, double massa, double raggio_corpo, Corpo padre) {
        this.nome = nome;
        this.posizione = posizione;
        this.massa = massa;
        this.raggio_corpo = raggio_corpo;
        this.padre = padre;
        
        /* ID del corpo
         * si usa il valore corrente del contatore che viene poi incrementato
         * in modo che i corpi creati successivamente abbiano sempre un id diverso
         */
        id = contatore;
        id++;
    }
    
    //costruttore per la stella:
    public Corpo(String nome, double massa, double raggio_corpo) {
        this.nome = nome;
        this.massa = massa;
        this.raggio_corpo = raggio_corpo;
        this.posizione = new Posizione(0,0);
        this.raggio_orbita = 0.0;
        this.padre = this;
        
        id = contatore;
        id++;
    }
    
    public int getId() {
    	return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    public double getMassa() {
        return massa;
    }

    public void setMassa(double massa) {
        this.massa = massa;
    }

    public double getRaggio_orbita() {
        return raggio_orbita;
    }

    public void setRaggio_orbita(double raggio_orbita) {
        this.raggio_orbita = raggio_orbita;
    }

    public double getRaggio_corpo() {
        return raggio_corpo;
    }

    public void setRaggio_corpo(double raggio_corpo) {
        this.raggio_corpo = raggio_corpo;
    }

    public Corpo getPadre() {
        return padre;
    }

    public void setPadre(Corpo padre) {
        this.padre = padre;
    }
    
    /**
     * Restituisce l'intera lista dei satelliti
     */
    public ArrayList<Corpo> getSatelliti() {
        return satelliti;
    }

    /**
     * Cambia la lista di satelliti con una data
     * @param satelliti la nuova lista di satelliti
     */
    public void setSatelliti(ArrayList<Corpo> satelliti) {
        this.satelliti = satelliti;
    }
    
    /** 
     * Ritorna un satellite che ha l'id specificato
     * 
     * @param _id l'id del corpo da trovare
     * @return	il satellite cercato
     */
    public Corpo getSatellite(int _id) {
    	for(Corpo corpo : satelliti) {
    		if(corpo.id ==_id)
    			return corpo;
    	}
    	/* Ritornare null nel caso non trovi un corpo corrispondente
    	 * Valutare se gestire questa cosa in modo diverso
    	 */
    	return null;
    }
    
    /**
     * Sostituisce un satellite con un corpo indicato
     * 
     * @param corpo	il corpo da inserire al posto di quello già presente
     * @param _id	l'indice del satellite da rimpiazzare
     */
    public void setSatellite(Corpo _corpo, int _id) {
    	for(Corpo corpo : satelliti)
    		if(corpo.id == _id) {
    			corpo = _corpo; // da controllare
    			System.out.println("Sostituzione con il corpo -> id: " + corpo.id);
    		}
    	
    }

    public static int getContatore() {
        return contatore;
    }

    public static void setContatore(int contatore) {
        Corpo.contatore = contatore;
    }

    public Posizione getPosizione_relativa() {
        return posizione_relativa;
    }

    public void setPosizione_relativa(Posizione posizione_relativa) {
        this.posizione_relativa = posizione_relativa;
    }

    public void aggiungi_satellite(Corpo satellite){
        satelliti.add(satellite);
    }
}
