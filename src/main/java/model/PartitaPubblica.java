package model;

import java.sql.Array;
import java.util.ArrayList;

/**
 * La classe PartitaPubblica rappresenta una partita pubblica all'interno del sistema,
 * estende la classe {@link Partita}. Tutte le partite pubbliche per essere istanziate
 * hanno obbligatoriamente bisogno di un moderatore che le gestisca, una volta istanziate
 * possono avere più di un moderatore.
 */
public class PartitaPubblica extends Partita {
    /**
     * L'elenco dei {@link Moderatore} che gestiscono la partita pubblica.
     */
    private ArrayList<Moderatore> moderatori = new ArrayList<Moderatore>();

    /**
     * Istanzia un nuovo oggetto PartitaPubblica
     *
     * @param codicePartita Il codice univoco identificativo della partita passato alla superclasse {@link Partita}.
     * @param videogioco    Il videogioco su cui si gioca la partita passato alla superclasse {@link Partita}.
     * @param capienza      La capienza della partita passata alla superclasse {@link Partita}.
     * @param m             Il {@link Moderatore} che gestisce la partita nel momento della sua creazione.
     */
    public PartitaPubblica(String codicePartita, String videogioco, int capienza, Moderatore m) {
        super(codicePartita, videogioco, capienza);
        moderatori.add(m);
        m.aggiungiPartitaGestita(this);
    }

    /**
     * Conclude la partita pubblica registrandone il risultato per tutti i {@link Giocatore} partecipanti,
     * istanziando un oggetto {@link StoricoPartita} da aggiungere agli storici di tutti i giocatori
     * e aggiornando il valore delle partite vinte del vincitore e delle partite perse dei perdenti.
     * Infine svuota ciclicamente l'elenco dei partecipanti alla partita.
     *
     * @param vincitore Il {@link Giocatore} vincitore della partita pubblica.
     * @param durata    La durata temporale della partita pubblica espressa sottoforma di stringa.
     */
    public void aggiungiRisultato(Giocatore vincitore, String durata)
    {
        ArrayList<Giocatore> partecipanti = new ArrayList<Giocatore>();
        for (Giocatore g : getPartecipantiPartita())
        {
            partecipanti.add(g);
        }

        StoricoPartita risultato = new StoricoPartita(vincitore, durata, this, partecipanti );
        for(Giocatore g: partecipanti)
        {
            g.getStorico().add(risultato);
            if(g == vincitore) {
                g.aggiungiPartitaVinta();
            }
            else{
                g.aggiungiPartitaPersa();
            }
            g.esciDallaPartita();
        }
    }

    /**
     * Restituisce l'elenco dei {@link Moderatore} responsabili della gestione della partita pubblica.
     *
     * @return L'ArrayList contenente i moderatori responsabili della gestione della partita pubblica.
     */
    public ArrayList<Moderatore> getModeratori() {
        return moderatori;
    }

    /**
     * Genera e restituisce una rappresentazione testuale formattata delle informazioni di base della
     * partita pubblica. Tali informazioni sono separate da un ";" e presentate secondo la seguente
     * divisione: codice della partita, videogioco, la quantità attuale di partecipanti, capienza,
     * numero di moderatori presenti.
     * @return Una stringa contenente le informazioni di base della partita pubblica.
     */
    public String toString(){
        return this.getCodicePartita()+";"+this.getVideogioco()+";"+this.getPartecipantiPartita().size()+";"+this.getCapienza()+";"+this.getModeratori().size();
    }
}
