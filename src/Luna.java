import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.PrettyStrings;

public class Luna extends Corpo {

    public Luna(String nome, Posizione posizione, double massa, double raggio_corpo, Corpo padre) {
        super(nome, posizione, massa, raggio_corpo, padre);

        this.setRaggio_orbita(Posizione.distanza(this.getPosizione(), this.getPadre().getPosizione()));

    }

	/**
	 * Compone uno StringBuffer con tutte le informazioni della Luna concatenate
	 * @return la String con tutte le informazioni della Luna
	 */
    public String toString() {
    	StringBuffer output= new StringBuffer();
    	output.append("\t" + AnsiColors.PURPLE + "LUNA" + AnsiColors.RESET + " \n\n");
    	output.append("ID :\t" + getId() + "\n");
    	output.append("Nome :\t" + getNome() + "\n");
    	output.append("Posizione :\t" + getPosizione().toString() + "\n");
    	output.append("Massa :\t\t" + getMassa() + "\n");
    	output.append("Raggio :\t\t" + getRaggio_corpo() + "\n");
    	output.append("Raggio orbitale :\t" + getRaggio_orbita() + "\n");
    	
    	if(getPadre() != null)
    		output.append("Satellite di :\t" + getPadre().getNome() + " : " + getPadre().getId() + "\n");

		output.append("Percorso per raggiungerla :\t" + AnsiColors.YELLOW + getPadre().getPadre().getNome() + AnsiColors.RESET + " > " + AnsiColors.GREEN + getPadre().getNome() + AnsiColors.RESET + " > " + AnsiColors.PURPLE + getNome() + AnsiColors.RESET + "\n");

    	output.append(PrettyStrings.repeatChar('-', 40) + "\n\n");
    	
    	return output.toString();
    }
}
