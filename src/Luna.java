
public class Luna extends Corpo {

    public Luna(String nome, Posizione posizione, double massa, double raggio_orbita, double raggio_corpo, Corpo padre) {
        super(nome, posizione, massa, raggio_orbita, raggio_corpo, padre);

        this.setRaggio_orbita(this.getPosizione().get_distanza(this.getPadre().getPosizione()));

    }
}
