public class Posizione {

    private final double x;
    private final double y;

    public Posizione(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    /**
     * Ritorna la distanza tra due posizioni
     */
    public static  double distanza(Posizione p1, Posizione p2){
    	return Math.sqrt( Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2) );
    }
    
    public String toString() {
    	return "( " + x + " , " + y + " )";
    }

}
