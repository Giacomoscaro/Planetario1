
import it.unibs.fp.mylib.InputDati;

public class MyMenu {
    //metodo crea Corpi
    public Corpo creaCorpo(){
        String nome = InputDati.leggiStringaNonVuota("Inserire un nome per il corpo: ");
        Posizione pos = new Posizione( InputDati.leggiDouble("Inserire x:"), InputDati.leggiDouble("Inserisci y: "));
        Double massa = InputDati.leggiDouble("Inserire massa: ");
        Double raggio_orbita = InputDati.leggiDouble("Raggio orbita: ");
        Double raggio_corpo = InputDati.leggiDouble("Raggio del corpo: ");
         //InputDati.leggiStringaNonVuota("Inserisci it.unibs.fp.mylib.Pianeta madre: ");
        return new Corpo(nome, massa,raggio_corpo);
    }
}
