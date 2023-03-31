import it.kibo.fp.lib.*;

import java.util.ArrayList;

public class Planetario {
    private ArrayList<Corpo> lista_corpi;
    private String nome_sistema;
    public Planetario(){
        System.out.println("Crea nuovo it.unibs.fp.mylib.Planetario");
        creaStella();
    }
    public void creaStella(){
        String nome = InputData.readNonEmptyString("nome:", true);
        Double massa = InputData.readDouble("massa:");
        Double raggio_corpo = InputData.readDouble("raggio corpo: ");
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
