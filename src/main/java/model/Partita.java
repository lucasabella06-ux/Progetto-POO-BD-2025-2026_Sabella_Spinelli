package model;

import java.util.ArrayList;

/**
 * La classe astratta Partita rappresenta una partita generica all'interno del sistema.
 * Definisce le informazioni di base comuni a tutti i tipi di partite nel sistema ({@link PartitaAmichevole}, {@link PartitaPubblica}.
 */
public abstract class Partita {
    /**
     * Il codice univoco identificativo della partita.
     */
    protected String codicePartita;
    /**
     * Il videogioco su cui la partita viene giocata.
     */
    protected String videogioco;
    /**
     * L'elenco dei {@link Giocatore} partecipanti della partita.
     */
    protected ArrayList<Giocatore> partecipantiPartita;
    /**
     * Il numero di partecipanti consentiti e necessari per lo svolgimento della partita.
     */
    protected int capienza;

    /**
     * Istanzia un nuovo oggetto della classe Partita, creando una lista vuota per i partecipanti.
     *
     * @param codicePartita Il codice univoco identificativo della partita.
     * @param videogioco    Il videogioco su cui si giocherà la partita.
     * @param capienza      Il numero di partecipanti consentiti e necessari per lo svolgimento della partita.
     */
    public Partita(String codicePartita, String videogioco, int capienza ){
        this.codicePartita = codicePartita;
        this.videogioco = videogioco;
        this.partecipantiPartita = new ArrayList<Giocatore>();
        this.capienza = capienza;
    }

    /**
     * Restituisce il codice identificativo della partita
     *
     * @return Una stringa contenente il codice identificativo della partita
     */
    public String getCodicePartita() {
        return codicePartita;
    }

    /**
     * Restituisce il videogioco su cui si gioca la partita.
     *
     * @return Una stringa contenente il titolo del videogioco su cui si gioca la partita.
     */
    public String getVideogioco() {
        return videogioco;
    }

    /**
     * Restituisce l'elenco dei {@link Giocatore} partecipanti della partita.
     *
     * @return L'ArrayList contenente i partecipanti della partita.
     */
    public ArrayList<Giocatore> getPartecipantiPartita() {
        return partecipantiPartita;
    }

    /**
     * Restituisce la capienza della partita.
     *
     * @return Il valore numerico della capienza della partita.
     */
    public int getCapienza(){ return capienza;}
}
