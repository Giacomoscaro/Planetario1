import it.unibs.fp.mylib.InputDati;

import java.util.ArrayList;

public class Planetario {
    private ArrayList<Corpo> lista_corpi;
    private String nome_sistema;
    public Planetario(){
        System.out.println("Crea nuovo it.unibs.fp.mylib.Planetario");
        creaStella();
    }
    public void creaStella(){
        String nome = InputDati.leggiStringaNonVuota("nome:");
        Double massa = InputDati.leggiDouble("massa:");
        Double raggio_corpo = InputDati.leggiDouble("raggio corpo: ");
        Stella stella = new Stella(nome , massa, raggio_corpo);
	lista_corpi = new ArrayList<Corpo>();
        lista_corpi.add(stella);
    }
    public String getNome_sistema() {
        return nome_sistema;
    }

    public void setNome_sistema(String nome_sistema) {
        this.nome_sistema = nome_sistema;
    }
}
