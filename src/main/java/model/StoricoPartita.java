package Model;

import java.sql.Array;
import java.util.ArrayList;

public class StoricoPartita {
    private Giocatore vincitorePartita;
    private String durataPartita;
    private PartitaPubblica partita;
    private ArrayList<Giocatore> partecipantiPartita;

    public StoricoPartita(Giocatore vincitorePartita, String durataPartita, PartitaPubblica partita, ArrayList<Giocatore> partecipantiPartita) {
        this.vincitorePartita = vincitorePartita;
        this.durataPartita = durataPartita;
    }

    public Giocatore getVincitorePartita() {
        return vincitorePartita;
    }

    public void setVincitorePartita(Giocatore vincitorePartita) {
        this.vincitorePartita = vincitorePartita;
    }

    public String getDurataPartita() {
        return durataPartita;
    }

    public void setDurataPartita(String durataPartita) {
        this.durataPartita = durataPartita;
    }
}
