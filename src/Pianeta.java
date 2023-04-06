import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.PrettyStrings;

public class Pianeta extends Corpo {

    public Pianeta(String nome, Posizione posizione, double massa, double raggio_corpo, Corpo padre) {
        super(nome, posizione, massa, raggio_corpo, padre);

        this.setRaggio_orbita(this.getPosizione().get_distanza(this.getPadre().getPosizione()));

    }
    
    public String toString() {
    	StringBuffer output= new StringBuffer();
    	output.append("\t" + AnsiColors.GREEN + "PIANETA" + AnsiColors.RESET + " \n\n");
    	output.append("ID :\t" + getId() + "\n");
    	output.append("Nome :\t" + getNome() + "\n");
    	output.append("Posizione :\t" + getPosizione().toString() + "\n");
    	output.append("Massa :\t\t" + getMassa() + "\n");
    	output.append("Raggio :\t\t" + getRaggio_corpo() + "\n");
    	output.append("Raggio orbitale :\t" + getRaggio_orbita() + "\n");
    	
    	if(getSatelliti().isEmpty() == false) {
    		output.append("Satelliti :\t" + "\n");
    		for(Corpo satellite: getSatelliti())
    			output.append("\t⊢ " + satellite.getNome() + " : " + satellite.getId() + "\n");
    	
    		output.append("\n");
    	}
    	
    	if(getPadre() != null)
    		output.append("Satellite di :\t" + getPadre().getNome() + " : " + getPadre().getId() + "\n");
    	
    	output.append(PrettyStrings.repeatChar('-', 40) + "\n\n");
    	
    	return output.toString();
    }
}
