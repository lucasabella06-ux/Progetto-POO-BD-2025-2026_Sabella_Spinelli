package Model;

import java.util.ArrayList;
import java.util.Comparator;

public class Classifica {
    private final int numeroPosizioni = 25;
    private ArrayList<Giocatore> giocatoriInClassifica = new ArrayList<Giocatore>();

    public Classifica(ArrayList<Giocatore> giocatoriInClassifica) {
        this.giocatoriInClassifica = giocatoriInClassifica;
    }

    public Classifica(Giocatore g) {
        giocatoriInClassifica.add(g);
    }

    public void aggiungiGiocatore(Giocatore giocatoreDaAggiungere)
    {
        giocatoriInClassifica.add(giocatoreDaAggiungere);
    }

    public void rimuoviGiocatore(Giocatore giocatoreDaRimuovere)
    {
        giocatoriInClassifica.remove(giocatoreDaRimuovere);
    }

    public void ordinaGiocatori(){
        giocatoriInClassifica.sort(Giocatore::compareTo);
    }


    public int getNumeroPosizioni() {
        return numeroPosizioni;
    }


    public ArrayList<Giocatore> getGiocatoriInClassifica() {
        return giocatoriInClassifica;
    }

    public void setGiocatoriInClassifica(ArrayList<Giocatore> giocatoriInClassifica) {
        this.giocatoriInClassifica = giocatoriInClassifica;
    }


}
