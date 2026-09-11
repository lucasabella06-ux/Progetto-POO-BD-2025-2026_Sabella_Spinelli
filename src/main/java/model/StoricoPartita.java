package model;

import java.util.ArrayList;

/**
 * La classe StoricoPartita rappresenta una raccolta dei risultati delle partite pubbliche giocate da un {@link Giocatore}
 * nel corso di una sessione di gioco (una sessione di gioco inizia all'apertura dell'applicazione e finisce
 * con la chiusura della stessa). In particolare vengono memorizzati il vincitore, la durata e l'elenco dei
 * partecipanti di ogni partita pubblica.
 */
public class StoricoPartita {
    /**
     * Il {@link Giocatore} vincitore della {@link PartitaPubblica} a cui il giocatore ha partecipato.
     */
    private Giocatore vincitorePartita;
    /**
     * La durata della {@link PartitaPubblica} a cui il giocatore ha partecipato.
     */
    private String durataPartita;
    /**
     * La {@link PartitaPubblica} a cui il giocatore ha partecipato.
     */
    private PartitaPubblica partita;
    /**
     * L'elenco dei {@link Giocatore} partecipanti della {@link PartitaPubblica} a cui il giocatore ha partecipato.
     */
    private ArrayList<Giocatore> partecipantiPartita;

    /**
     * Istanzia un oggetto StoricoPartita.
     *
     * @param vincitorePartita    Il {@link Giocatore} vincitore della {@link PartitaPubblica}.
     * @param durataPartita       La durata della {@link PartitaPubblica}.
     * @param partita             La {@link PartitaPubblica} il cui risultato viene memorizzato.
     * @param partecipantiPartita L'elenco dei {@link Giocatore} partecipanti della {@link PartitaPubblica}.
     */
    public StoricoPartita(Giocatore vincitorePartita, String durataPartita, PartitaPubblica partita, ArrayList<Giocatore> partecipantiPartita) {
        this.vincitorePartita = vincitorePartita;
        this.durataPartita = durataPartita;
        this.partita = partita;
        this.partecipantiPartita = partecipantiPartita;
    }

    /**
     * Restituisce il {@link Giocatore} vincitore della {@link PartitaPubblica}.
     *
     * @return il giocatore vincitore della partita pubblica.
     */
    public Giocatore getVincitorePartita() {
        return vincitorePartita;
    }

    /**
     * Restituisce la durata della {@link PartitaPubblica}.
     *
     * @return La stringa contenente la durata della partita pubblica.
     */
    public String getDurataPartita() {
        return durataPartita;
    }

    /**
     * Genera e restituisce una rappresentazione testuale delle informazioni di base dell'
     * oggetto StoricoPartita, separando le informazioni con un ";" e raccogliendole
     * nel seguente ordine: videogioco, vincitore, durata.
     * @return Una stringa contenente le informazioni di base dell'oggetto StoricoPartita.
     */
    public String toString(){
        return "Videogioco: "+partita.getVideogioco()+";Vincitore: "+vincitorePartita.getNomeUtente()+";Durata: "+this.durataPartita;
    }

    /**
     * Restituisce l'elenco dei {@link Giocatore} partecipanti della {@link PartitaPubblica}
     * a cui fa riferimento lo storico
     *
     * @return Un ArrayList contenente i partecipanti della partita pubblica a cui fa riferimento
     * lo storico
     */
    public ArrayList<Giocatore> getPartecipantiPartita() {
        return partecipantiPartita;
    }
}
