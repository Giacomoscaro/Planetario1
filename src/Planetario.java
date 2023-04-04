import it.kibo.fp.lib.*;

import java.util.ArrayList;

public class Planetario {
    private ArrayList<Corpo> lista_corpi = new ArrayList<Corpo>(10);;
    private String nome_sistema;
    public Planetario(){
        System.out.println("Crea nuovo it.unibs.fp.mylib.Planetario");

    }
    public void creaStella(){
        String nome = InputData.readNonEmptyString("nome:", true);
        Double massa = InputData.readDoubleWithMinimum("massa:", 0);
        Double raggio_corpo = InputData.readDoubleWithMinimum("raggio stella: ", 0);
        Stella stella = new Stella(nome , massa, raggio_corpo);
	//lista_corpi = new ArrayList<Corpo>();
        lista_corpi.add(stella);
    }

    public void creaPianeta(){
        String nome = InputData.readNonEmptyString("nome:", true);
        Posizione posizione = new Posizione(InputData.readDouble("inserire x:"), InputData.readDouble("inserire y:"));
        Double massa = InputData.readDoubleWithMinimum("massa:", 0);
        Double raggio_corpo = InputData.readDoubleWithMinimum("raggio pianeta: ", 0);
        Corpo padre = lista_corpi.get(0);
        Pianeta pianeta = new Pianeta(nome, posizione, massa, raggio_corpo, padre);
    }



    public String getNome_sistema() {
        return nome_sistema;
    }

    public void setNome_sistema(String nome_sistema) {
        this.nome_sistema = nome_sistema;
    }
}
