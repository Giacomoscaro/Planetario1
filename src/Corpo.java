import java.util.ArrayList;

import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.PrettyStrings;

public class Corpo {

    private final int id; //identificatore unico del corpo
    private final String nome;
    private Posizione posizione;
    private final double massa;
    private double raggio_orbita;
    private final double raggio_corpo;
    private final Corpo padre;
    private ArrayList<Corpo> satelliti;
    
    
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
        contatore++;
        
        //inizializzazione dell'arraylist dei satelliti
        satelliti = new ArrayList<Corpo>();
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
        contatore++;
        
        satelliti = new ArrayList<Corpo>();
    }
    
    public int getId() {
    	return id;
    }
    
    public String getNome() {
        return nome;
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

    public double getRaggio_orbita() {
        return raggio_orbita;
    }

    public void setRaggio_orbita(double raggio_orbita) {
        this.raggio_orbita = raggio_orbita;
    }

    public double getRaggio_corpo() {
        return raggio_corpo;
    }

    public Corpo getPadre() {
        return padre;
    }
    
    /**
     * Restituisce l'intera lista dei satelliti
     */
    public ArrayList<Corpo> getSatelliti() {
        return satelliti;
    }

    public void aggiungi_satellite(Corpo satellite){
        satelliti.add(satellite);
    }

    /**
     * Visualizza i dettagli di tutti i satelliti del corpo da cui viene chiamato il metodo
     */
    public void visualizza_satelliti(){
        for(Corpo corpo: satelliti){
            System.out.println(corpo.toString());
        }
    }

    /**
     * Restituisce le informazioni di un corpo generico, l'elenco
     * dei suoi satelliti (se ne ha) e il corpo padre
     */
    public String toString() {
    	StringBuffer output= new StringBuffer();
        output.append("\t" + AnsiColors.WHITE + "CORPO" + AnsiColors.RESET + " \n\n");
        output.append("ID :\t" + id + "\n");
    	output.append("Nome :\t" + nome + "\n");
    	output.append("Posizione :\t" + posizione.toString() + "\n");
    	output.append("Massa :\t\t" + massa + "\n");
    	output.append("Raggio :\t\t" + raggio_corpo + "\n");
    	output.append("Raggio orbitale :\t" + raggio_orbita + "\n");
    	
    	if(!satelliti.isEmpty()) {
    		output.append("Satelliti :\t" + "\n");
    		for(Corpo satellite: satelliti)
    			output.append("\t" + satellite.getNome() + " : " + satellite.getId() + "\n");
    	
    		output.append("\n");
    	}
    	
    	if(padre != null)
    		output.append("Satellite di :\t" + padre.getNome() + " : " + padre.getId() + "\n");
    	
    	output.append(PrettyStrings.repeatChar('-', 40) + "\n\n");
    	
    	return output.toString();
    }
    
    /**
     *  Restituisce vero se i corpi in ingresso possono collidere
     * @param c1 primo corpo
     * @param c2 secondo corpo
     * @return vero se i corpi possono collidere
     */
    public static boolean collidono(Corpo c1, Corpo c2) {
    	/*
    	 * Due corpi possono collidere se
    	 * la distanza trai loro centri è minore o uguale alla somma dei raggi orbitali
    	 * (e della somma dei raggi dei corpi)
    	 */
        boolean collisione = false;
        if(c1.getPadre().equals(c2) || c2.getPadre().equals(c1) ||  c1.getPadre().equals(c2.getPadre())){
            //se i corpi sono adiacenti o hanno lo stesso padre collidono solo se la distanza tra loro è minore della somma dei raggi
            if( Posizione.distanza(c1.getPosizione(), c2.getPosizione()) <= c1.getRaggio_corpo() + c2.getRaggio_corpo())
                collisione = true;
        }
        else if( Posizione.distanza(c1.getPadre().getPosizione(), c2.getPadre().getPosizione()) <= c1.getRaggio_orbita() + c2.getRaggio_orbita() + c1.getRaggio_corpo() + c2.getRaggio_corpo() )
        collisione = true;
        return collisione;
    }
    
    /**
     * Ritorna vero se i due corpi si sovrappongono
     * @param c1 il primo corpo
     * @param c2 il secondo corpo
     * @return vero se i corpi si sovrappongono
     */
    public static boolean sovrapposti(Corpo c1, Corpo c2) {
    	/*
    	 * Due corpi si sovrappongono se la distanza tra loro è
    	 * minore della somma dei loro raggi
    	 */
        return Posizione.distanza(c1.getPosizione(), c2.getPosizione()) < c1.getRaggio_corpo() + c2.getRaggio_corpo();
    }
}
