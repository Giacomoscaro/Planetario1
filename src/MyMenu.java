
import it.kibo.fp.lib.*;

public class MyMenu {
    //metodo crea Corpi
    public Corpo creaCorpo(){
        String nome = InputData.readNonEmptyString("Inserire un nome per il corpo: ", true);
        Posizione pos = new Posizione( InputData.readDouble("Inserire x:"), InputData.readDouble("Inserisci y: "));
        Double massa = InputData.readDouble("Inserire massa: ");
        Double raggio_orbita = InputData.readDouble("Raggio orbita: ");
        Double raggio_corpo = InputData.readDouble("Raggio del corpo: ");
        InputData.readNonEmptyString("Inserisci it.unibs.fp.mylib.Pianeta madre: ", true);
        return new Corpo(nome, massa,raggio_corpo);
    }
}
