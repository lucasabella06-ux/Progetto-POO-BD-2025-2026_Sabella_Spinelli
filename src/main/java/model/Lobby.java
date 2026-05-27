package Model;

import java.util.ArrayList;

public class Lobby {

    private String codiceLobby;
    private int capienzaGiocatoriMax;
    private int capienzaEffettiva;
    private Giocatore host;
    private ArrayList<Giocatore> partecipantiLobby = new ArrayList<Giocatore>();
    private PartitaAmichevole partitaCreata = null;

    public Lobby(String codiceLobby, int capienzaGiocatoriMax, Giocatore host) {

        this.codiceLobby = codiceLobby;
        this.capienzaGiocatoriMax = capienzaGiocatoriMax;
        this.host = host;
        partecipantiLobby.add(host);
    }

    public String getCodiceLobby() {
        return codiceLobby;
    }

    public void setCodiceLobby(String codiceLobby) {
        this.codiceLobby = codiceLobby;
    }

    public int getCapienzaGiocatoriMax() {
        return capienzaGiocatoriMax;
    }

    public void setCapienzaGiocatoriMax(int capienzaGiocatoriMax) {
        this.capienzaGiocatoriMax = capienzaGiocatoriMax;
    }

    public ArrayList<Giocatore> visualizzaGiocatori(){
        return partecipantiLobby;
    }

    public void aggiungiGiocatori(Giocatore g)
    {
        if (capienzaEffettiva < capienzaGiocatoriMax){
            this.partecipantiLobby.add(g);
        }
    }

    public void rimuoviGiocatori(Giocatore g)
    {
        this.partecipantiLobby.remove(g);
    }

    public boolean creaPartitaAmichevole(String codicePartita, String videogioco)
    {
        this.partitaCreata = new PartitaAmichevole(codicePartita, videogioco);
        for(Giocatore g: partecipantiLobby)
        {
            if(!g.entraInPartita(partitaCreata)) return false;
        }
        return true;
    }

    public void eliminaPartitaAmichevole()
    {
        for(Giocatore g: partecipantiLobby)
        {
            g.esciDallaPartita();
        }
        this.partitaCreata = null;
    }
}
