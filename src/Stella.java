public class Stella extends Corpo{

    public Stella(String nome, Posizione posizione, double massa, double raggio_orbita, double raggio_corpo, Corpo padre) {
        super(nome, posizione, massa, raggio_orbita, raggio_corpo, padre);
        this.setPosizione(new Posizione(0,0));
        this.setRaggio_orbita(0);
        this.setPadre(this);
    }
}
