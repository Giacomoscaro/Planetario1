
public class Corpo {

    private String nome;
    private Posizione posizione;
    private double massa;
    private double raggio_orbita;
    private double raggio_corpo;
    private Corpo padre;
    private Corpo[] satelliti;
    private static int contatore;
    private Posizione posizione_relativa;

    //costruttore per un pianeta o una luna:
    public Corpo(String nome, Posizione posizione, double massa, double raggio_corpo, Corpo padre) {
        this.nome = nome;
        this.posizione = posizione;
        this.massa = massa;
        this.raggio_corpo = raggio_corpo;
        this.padre = padre;
    }
    //costruttore per la stella:
    public Corpo(String nome, double massa, double raggio_corpo) {
        this.nome = nome;
        this.massa = massa;
        this.raggio_corpo = raggio_corpo;
        this.posizione = new Posizione(0,0);
        this.raggio_orbita = 0.0;
        this.padre = this;
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

    public Corpo[] getSatelliti() {
        return satelliti;
    }

    public void setSatelliti(Corpo[] satelliti) {
        this.satelliti = satelliti;
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
        satelliti[satelliti.length] = satellite;
    }
}
