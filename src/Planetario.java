import it.kibo.fp.lib.*;

import java.util.ArrayList;

public class Planetario {
    private ArrayList<Corpo> lista_corpi = new ArrayList<Corpo>(10);;
    private String nome_sistema;
    public Planetario(){
        System.out.println("Crea nuovo it.unibs.fp.mylib.Planetario");

    }

    public String getNome_sistema() {
        return nome_sistema;
    }

    public void setNome_sistema(String nome_sistema) {
        this.nome_sistema = nome_sistema;
    }
    
    public void stampa_lista_corpi() {
    	for(Corpo corpo: lista_corpi) {
    		System.out.print(corpo.toString());
    	}
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
        lista_corpi.add(pianeta);
        lista_corpi.get(0).aggiungi_satellite(pianeta);
    }

    public void stampa_pianeti(){
        //serve per avere una lista dei pianeti per scegliere il corpo padre di una luna
        for(int i = 0; i< lista_corpi.size(); i++){
            if(lista_corpi.get(i).getClass() == Pianeta.class){
                System.out.println(lista_corpi.get(i).getNome() + "\n");
            }
        }
    }

    public Corpo toCorpo(String nome){
        //serve per avere subito il corpo corrispondente a un nome
        Corpo c=null;
        for(int i=0; i<lista_corpi.size(); i++){
            if(lista_corpi.get(i).getNome().equals(nome)){
                c=lista_corpi.get(i);
            }
        }
        return c;
    }

    public void creaLuna(){
        String nome = InputData.readNonEmptyString("nome:", true);
        Posizione posizione = new Posizione(InputData.readDouble("inserire x:"), InputData.readDouble("inserire y:"));
        Double massa = InputData.readDoubleWithMinimum("massa:", 0);
        Double raggio_corpo = InputData.readDoubleWithMinimum("raggio luna: ", 0);
        stampa_pianeti();
        String nome_padre = InputData.readNonEmptyString("Scegli il pianeta padre dalla lista:", true);
        Corpo padre = toCorpo(nome_padre);
        Luna luna = new Luna(nome, posizione, massa, raggio_corpo, padre);
        lista_corpi.add(luna);
        lista_corpi.get(lista_corpi.indexOf(padre)).aggiungi_satellite(luna);
    }


    public void rimuovi_stella(){
        System.out.println("Hai deciso di rimuovere la stella dal tuo sistema solare, il che porterà ad una sua completa distruzione, addio " + this.getNome_sistema());
        lista_corpi.clear();
    }

    public void rimuovi_pianeta(Pianeta pianeta){
        for(int i=0; i<pianeta.getSatelliti().size(); i++){
            lista_corpi.remove(pianeta.getSatelliti().get(i));
        }
        pianeta.getSatelliti().clear();
        lista_corpi.remove(pianeta);
    }

    public void rimuovi_luna(Luna luna){
        lista_corpi.remove(luna);
        luna.getPadre().getSatelliti().remove(luna);
    }

}
