package model;

import java.util.ArrayList;

/**
 * La classe Moderatore rappresenta un moderatore all'interno del sistema.
 * Estende la classe astratta {@link Utente}.
 */
public class Moderatore extends Utente{
    /**
     * Il codice segreto univoco utilizzato dal moderatore sia per accedere al sistema che per svolgere
     * le proprie azioni.
     */
    private String codiceAccessoModeratore;
    /**
     * L'ora di inizio dell'attività giornaliera nel sistema del moderatore in formato di intero
     * (assumerà sempre valore da 1 a 12).
     */
    private int orarioInizioAttivita;
    /**
     * L'ora di fine dell'attività giornaliera nel sistema del moderatore in formato di intero
     * (assumerà sempre valore da 13 a 24).
     */
    private int orarioFineAttivita;
    /**
     * Elenco di {@link PartitaPubblica} gestite dal moderatore attualmente.
     */
    private ArrayList<PartitaPubblica> partiteGestite = new ArrayList<PartitaPubblica>();

    /**
     * Istanzia un nuovo oggetto Moderatore
     *
     * @param nomeUtente     Il nome identificativo passato alla superclasse {@link Utente}.
     * @param emailUtente    L'indirizzo email passato alla superclasse {@link Utente}.
     * @param passwordUtente La password passata alla superclasse {@link Utente}.
     * @param codiceAccessoModeratore Il codice segreto univoco del moderatore, generato automaticamente dal sistema.
     * @param orarioInizioAttivita    L'ora di inizio dell'attività giornaliera del moderatore, dichiarata da egli stesso.
     * @param orarioFineAttivita      L'ora di fine dell'attività giornaliera del moderatore, dichiarata da egli stesso.
     */
    public Moderatore(String nomeUtente, String emailUtente, String passwordUtente, String codiceAccessoModeratore, int orarioInizioAttivita, int orarioFineAttivita) {
        super(nomeUtente, emailUtente, passwordUtente);
        this.codiceAccessoModeratore = codiceAccessoModeratore;
        this.orarioInizioAttivita = orarioInizioAttivita;
        this.orarioFineAttivita = orarioFineAttivita;
    }

    /**
     * Restituisce il codice operativo univoco del moderatore.
     *
     * @return Una stringa contenente il codice del moderatore.
     */
    public String getCodiceAccessoModeratore() {
        return codiceAccessoModeratore;
    }

    /**
     * Restituisce l'ora d'inizio dell'attività del moderatore
     *
     * @return valore intero dell'ora d'inizio dell'attività del moderatore
     */
    public int getOrarioInizioAttivita() {
        return orarioInizioAttivita;
    }

    /**
     * Aggiorna l'ora d'inizio dell'attività del moderatore
     *
     * @param orarioInizioAttivita La nuova ora d'inizio dell'attività del moderatore
     */
    public void setOrarioInizioAttivita(int orarioInizioAttivita) {
        this.orarioInizioAttivita = orarioInizioAttivita;
    }

    /**
     * Restituisce l'ora di fine dell'attività del moderatore
     *
     * @return valore intero dell'ora di fine dell'attività del moderatore
     */
    public int getOrarioFineAttivita() {
        return orarioFineAttivita;
    }

    /**
     * Aggiorna l'ora di fine dell'attività del moderatore
     *
     * @param orarioFineAttivita La nuova ora di fine dell'attività del moderatore
     */
    public void setOrarioFineAttivita(int orarioFineAttivita) {
        this.orarioFineAttivita = orarioFineAttivita;
    }

    /**
     * Restituisce l'elenco di {@link Segnalazione} ricevute dal {@link Giocatore} specificato nel parametro,
     * chiamandone il metodo getSegnalazioniRicevute().
     *
     * @param giocatoreSegnalato Il giocatore di cui si vogliono analizzare le segnalazioni ricevute.
     * @return L'ArrayList contenente le segnalazioni ricevute dal giocatore specificato.
     */
    public ArrayList<Segnalazione> visualizzaSegnalazioni(Giocatore giocatoreSegnalato) {

        return giocatoreSegnalato.getSegnalazioniRicevute();

    }

    /**
     * Bandisce un {@link Giocatore} dal sistema, cambiando il suo parametro isAttivo
     * in modo tale che in fase di login del giocatore il profilo non venga trovato.
     * Elimina inoltre il giocatore dalla {@link PartitaPubblica} a cui sta partecipando.
     *
     * @param giocatoreDaBandire Il giocatore bandito dal moderatore.
     */
    public void bandisciGiocatore(Giocatore giocatoreDaBandire) {
        if (giocatoreDaBandire.getIsAttivo()) {
            giocatoreDaBandire.cambiaIsAttivo();
            if (giocatoreDaBandire.getPartitaAttuale() != null) {
                giocatoreDaBandire.esciDallaPartita();
            }
        }
    }

    /**
     * Riattiva un {@link Giocatore} nel sistema, ripristinando il suo parametro isAttivo.
     *
     * @param giocatoreDaRiattivare Il giocatore il cui profilo è stato riattivato dal moderatore.
     */
    public void riattivaGiocatore(Giocatore giocatoreDaRiattivare) {
        if(!giocatoreDaRiattivare.getIsAttivo()){
            giocatoreDaRiattivare.cambiaIsAttivo();
        }
    }

    /**
     * Aggiunge una {@link PartitaPubblica} all'elenco delle partite gestite dal moderatore.
     *
     * @param p la partita pubblica di cui il moderatore assume la gestione.
     */
    public void aggiungiPartitaGestita(PartitaPubblica p)
    {
        partiteGestite.add(p);
    }

    /**
     * Rimuove una {@link PartitaPubblica} dall'elenco delle partite gestite dal moderatore.
     *
     * @param p la partita pubblica di cui il moderatore termina la gestione.
     */
    public void rimuoviPartitaGestita(PartitaPubblica p)
    {
        partiteGestite.remove(p);
    }

    /**
     * Restituisce l'elenco delle {@link PartitaPubblica} gestite dal moderatore.
     *
     * @return ArrayList contenente le partite pubbliche gestite dal moderatore.
     */
    public ArrayList<PartitaPubblica> getPartiteGestite() {
        return partiteGestite;
    }
}
