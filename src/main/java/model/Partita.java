package Model;

import java.util.ArrayList;

public abstract class Partita {
    private String codicePartita;
    private String videogioco;
    private ArrayList<Giocatore> partecipantiPartita;

    public Partita(String codicePartita, String videogioco ){
        this.codicePartita = codicePartita;
        this.videogioco = videogioco;
        this.partecipantiPartita = new ArrayList<Giocatore>();
    }

    public Partita(String codicePartita, String videogioco, ArrayList<Giocatore> partecipantiPartita) {
        this.codicePartita = codicePartita;
        this.videogioco = videogioco;
        this.partecipantiPartita = partecipantiPartita;
    }

    public String getCodicePartita() {
        return codicePartita;
    }

    public void setCodicePartita(String codicePartita) {
        this.codicePartita = codicePartita;
    }

    public String getVideogioco() {
        return videogioco;
    }

    public void setVideogioco(String videogioco) {
        this.videogioco = videogioco;
    }

    public ArrayList<Giocatore> getPartecipantiPartita() {
        return partecipantiPartita;
    }

    public void setPartecipantiPartita(ArrayList<Giocatore> partecipantiPartita) {
        this.partecipantiPartita = partecipantiPartita;
    }
}
