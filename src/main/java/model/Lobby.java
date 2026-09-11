package model;

import java.util.ArrayList;

/**
 * La classe Lobby rappresenta il gruppo di gioco privato creato da un {@link Giocatore},
 * che ne diventa l'host. Alla lobby possono unirsi altri giocatori per giocare assieme
 * una {@link PartitaAmichevole}
 */
public class Lobby {
    /**
     * Il codice identificativo della lobby scelto dal {@link Giocatore} host.
     * Ha funzione anche di codice d'accesso che i giocatori devono inserire per
     * parteciparvi.
     */
    private String codiceLobby;
    /**
     * La capienza massima di {@link Giocatore} della lobby scelta dall'host.
     */
    private int capienzaGiocatoriMax;
    /**
     * Il {@link Giocatore} creatore della lobby.
     */
    private Giocatore host;
    /**
     * L'elenco dei {@link Giocatore} partecipanti della lobby.
     */
    private ArrayList<Giocatore> partecipantiLobby = new ArrayList<Giocatore>();
    /**
     * La {@link PartitaAmichevole} che è possibile creare all'interno della lobby.
     */
    private PartitaAmichevole partitaCreata = null;

    /**
     * Istanzia un nuovo oggetto Lobby, assegnando il {@link Giocatore} passato al metodo
     * sia come host della lobby che come primo partecipante della stessa.
     *
     * @param codiceLobby          Il codice identificativo della lobby.
     * @param capienzaGiocatoriMax La capienza massima di partecipanti della lobby.
     * @param host                 Il {@link Giocatore} creatore della lobby.
     */
    public Lobby(String codiceLobby, int capienzaGiocatoriMax, Giocatore host) {
        this.codiceLobby = codiceLobby;
        this.capienzaGiocatoriMax = capienzaGiocatoriMax;
        this.host = host;
        partecipantiLobby.add(host);
    }

    /**
     * Restituisce il codice identificativo della lobby.
     *
     * @return Una stringa contenente il codice identificativo della lobby.
     */
    public String getCodiceLobby() {
        return codiceLobby;
    }

    /**
     * Restituisce la capienza massima della lobby.
     *
     * @return L'intero rappresentante la capienza massima della lobby.
     */
    public int getCapienzaGiocatoriMax() {
        return capienzaGiocatoriMax;
    }

    /**
     * Restituisce l'elenco dei {@link Giocatore} partecipanti alla lobby.
     *
     * @return L'ArrayList contenente i partecipanti della lobby.
     */
    public ArrayList<Giocatore> visualizzaGiocatori(){
        return partecipantiLobby;
    }

    /**
     * Aggiunge un nuovo {@link Giocatore} partecipante alla lobby, assicurando che non venga sforata
     * la capienza massima di partecipanti scelta dall'host.
     *
     * @param g Il {@link Giocatore} che si è unito con successo alla lobby.
     */
    public void aggiungiGiocatori(Giocatore g)
    {
        if (partecipantiLobby.size() < capienzaGiocatoriMax){
            this.partecipantiLobby.add(g);
        }
    }

    /**
     * Rimuove un {@link Giocatore} che è uscito dalla lobby.
     *
     * @param g Il giocatore che è uscito dalla lobby.
     */
    public void rimuoviGiocatori(Giocatore g)
    {
        this.partecipantiLobby.remove(g);
    }

    /**
     * Istanzia la {@link PartitaAmichevole} creata dalla lobby mediante il costruttore della stessa classe,
     * prendendo come videogioco quello scelto dall'host e come capienza la quantità effettiva di
     * {@link Giocatore} presenti nella lobby, provvedendo a far entrare ciclicamente i giocatori
     * in partita col metodo entraInPartita.
     *
     * @param codicePartita Il codice identificativo univoco della {@link PartitaAmichevole}.
     * @param videogioco    Il videogioco a cui si gioca la partita amichevole.
     * @param capienza      La capienza della partita amichevole.
     * @return la {@link PartitaAmichevole} istanziata all'interno del metodo
     */
    public PartitaAmichevole creaPartitaAmichevole(String codicePartita, String videogioco, int capienza)
    {
        this.partitaCreata = new PartitaAmichevole(codicePartita, videogioco, capienza);
        for(Giocatore g: partecipantiLobby)
        {
            g.entraInPartita(partitaCreata);
        }
        return partitaCreata;
    }

    /**
     * Termina ed elimina la {@link PartitaAmichevole} dietro richiesta del {@link Giocatore} host della
     * lobby, provvedendo a far uscire ciclicamente tutti i giocatori dalla partita e a riportare il
     * parametro partitaCreata a null.
     */
    public void eliminaPartitaAmichevole()
    {
        for(Giocatore g: partecipantiLobby)
        {
            g.esciDallaPartita();
        }
        this.partitaCreata = null;
    }

    /**
     * Restituisce l'elenco dei partecipanti della lobby.
     *
     * @return Un ArrayList contenente i {@link Giocatore} partecipanti alla lobby.
     */
    public ArrayList<Giocatore> getPartecipantiLobby() {
        return partecipantiLobby;
    }

    /**
     * Restituisce il {@link Giocatore} host della lobby.
     *
     * @return Il giocatore host della lobby.
     */
    public Giocatore getHost() {
        return host;
    }

    /**
     * Restituisce la {@link PartitaAmichevole} creata dalla lobby.
     *
     * @return se non è null, la partita amichevole creata dalla lobby.
     */
    public PartitaAmichevole getPartitaCreata() {
        return partitaCreata;
    }
}
