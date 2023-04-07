import it.kibo.fp.lib.PrettyStrings;

public class Main {
    public static void main(String[] args){
        Planetario p1 = Planetario.crea_planetario();
        System.out.println(PrettyStrings.isolatedLine("Crea la tua prima stella"));
        p1.creaStella();
        System.out.println(PrettyStrings.isolatedLine("Crea il tuo primo pianeta"));
        p1.creaPianeta();
        MenuPrincipale m1 = new MenuPrincipale();
        m1.interazione(p1);
    }
}