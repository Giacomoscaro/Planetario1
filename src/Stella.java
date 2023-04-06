import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.PrettyStrings;

public class Stella extends Corpo {

    public Stella(String nome, double massa, double raggio_corpo) {
        super(nome, massa, raggio_corpo);
    }
    
    public String toString() {
    	StringBuffer output= new StringBuffer();
    	output.append("\t" + AnsiColors.YELLOW + "STELLA" + AnsiColors.RESET + " \n\n");
    	output.append("ID :\t" + getId() + "\n");
    	output.append("Nome :\t" + getNome() + "\n");
    	output.append("Massa :\t" + getMassa() + "\n");
    	output.append("Raggio :\t" + getRaggio_corpo() + "\n");
    	
    	if(getSatelliti().isEmpty() == false) {
    		output.append("Pianeti :\t" + "\n");
    		for(Corpo satellite: getSatelliti())
    			output.append("\t⊢ " + satellite.getNome() + " : " + satellite.getId() + "\n");
    	
    		output.append("\n");
    	}
    	
    	output.append(PrettyStrings.repeatChar('-', 40) + "\n\n");
    	
    	return output.toString();
    }
}
