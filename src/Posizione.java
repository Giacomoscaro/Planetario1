public class Posizione {

    private double x;
    private double y;

    public Posizione(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double get_distanza(Posizione p2){
        return Math.sqrt(Math.pow(x-p2.getX(), 2) + Math.pow(y-p2.getY(), 2));
    }

    public Posizione posizione_relativa(Posizione riferimento){
        return new Posizione(x-riferimento.getX(), y-riferimento.getY());
    }
    
    public String toString() {
    	return "( " + x + " , " + y + " )";
    }

}
