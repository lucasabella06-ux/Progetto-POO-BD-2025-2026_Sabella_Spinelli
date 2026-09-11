package dao;

import java.util.ArrayList;

/**
 * L'interfaccia PartitePubblicheDAO gestisce la persistenza dei dati relativi alle {@link model.PartitaPubblica},
 * la partecipazione dei {@link model.Giocatore} ad esse e la loro gestione da parte dei {@link model.Moderatore}
 * nel sistema, definendo metodi per la gestione e l'aggiornamento di tali dati.
 */
public interface PartitePubblicheDAO {
    /**
     * Riempie le liste fornite come parametri con i dati delle {@link model.PartitaPubblica} presenti nel sistema.
     *
     * @param listaCodiciPartitePubbliche Lista in cui inserire i codici identificativi delle
     *                                    partite pubbliche recuperate.
     * @param listaVideogiochi            Lista in cui inserire videogiochi dove sono giocate
     *                                    le partite pubbliche recuperate.
     * @param listaCapienze               Lista in cui inserire le capienze delle
     *                                    partite pubbliche recuperate.
     * @param listaModeratoriPartite      Lista in cui inserire i nomi utente dei moderatori
     *                                    che gestiscono le partite pubbliche recuperate.
     */
    public void generaPartitePubbliche(ArrayList<String> listaCodiciPartitePubbliche, ArrayList<String> listaVideogiochi, ArrayList<Integer> listaCapienze, ArrayList<String> listaModeratoriPartite);

    /**
     * Riempie le liste fornite come parametri con i dati dei {@link model.Moderatore} che gestiscono
     * le {@link model.PartitaPubblica} oltre a quelli già presentati nei dati delle partite pubbliche
     * presenti nel sistema.
     *
     * @param listaNomi                   Lista in cui inserire i nomi utente dei {@link model.Moderatore}
     *                                    che gestiscono le partite pubbliche recuperate.
     * @param listaCodiciPartitePubbliche Lista in cui inserire i codici identificativi delle
     *                                    partite pubbliche recuperate.
     */
    public void generaGestioniExtraPartitePubbliche(ArrayList<String> listaNomi, ArrayList<String> listaCodiciPartitePubbliche);

    /**
     * Riempie le liste fornite come parametri con i dati dei {@link model.Giocatore} partecipanti
     * delle {@link model.PartitaPubblica} presenti nel sistema.
     *
     * @param listaNomi                   Lista in cui inserire i nomi utente dei partecipanti alle
     *                                    partite pubbliche recuperate.
     * @param listaCodiciPartitePubbliche Lista in cui inserire i codici identificativi delle
     *                                    partite pubbliche recuperate.
     */
    public void generaPartecipantiPartitePubbliche(ArrayList<String> listaNomi, ArrayList<String> listaCodiciPartitePubbliche);

    /**
     * Registra l'eliminazione di una {@link model.PartitaPubblica} presente nel sistema.
     *
     * @param codicePartitaPubblica Il codice della partita pubblica da rimuovere.
     */
    public void rimuoviPartitaPubblica(String codicePartitaPubblica);

    /**
     * Rimuove i dati relativi alla partecipazione dei {@link model.Giocatore} ad
     * una {@link model.PartitaPubblica} che è stata eliminata dal sistema.
     *
     * @param codicePartitaPubblica Il codice della partita pubblica che è stata eliminata.
     */
    public void rimuoviPartecipantiPartitaPubblicaEliminata(String codicePartitaPubblica);

    /**
     * Registra l'ingresso di un nuovo {@link model.Giocatore} nella
     * {@link model.PartitaPubblica}.
     *
     * @param nomeUtente            Il nome utente del {@link model.Giocatore} che è entrato nella
     *                              partita pubblica.
     * @param codicePartitaPubblica Il codice della {@link model.PartitaPubblica} in cui è entrato
     *                              il giocatore
     */
    public void aggiungiGiocatoreAllaPartitaPubblica(String nomeUtente, String codicePartitaPubblica);

    /**
     * Registra l'uscita di un {@link model.Giocatore} partecipante dalla {@link model.PartitaPubblica}.
     *
     * @param nomeUtente Il nome utente del {@link model.Giocatore} che è uscito
     * dalla {@link model.PartitaPubblica}.
     */
    public void rimuoviGiocatoreDallaPartitaPubblica(String nomeUtente);

    /**
     * Registra la gestione da parte di un ulteriore {@link model.Moderatore} della
     * {@link model.PartitaPubblica}.
     *
     * @param nomeUtente            Il nome utente del {@link model.Moderatore} che sta gestendo
     *                              la partita pubblica.
     * @param codicePartitaPubblica Il codice della {@link model.PartitaPubblica} che è gestita
     *                              dal moderatore.
     */
    public void aggiungiGestioneExtraPartitaPubblica(String nomeUtente, String codicePartitaPubblica);

    /**
     * Registra la fine della gestione di una {@link model.PartitaPubblica}
     * da parte di un {@link model.Moderatore} non incluso nel costruttore
     * della partita pubblica.
     *
     * @param nomeUtente Il nome utente del {@link model.Moderatore} che ha terminato la gestione
     *                   della {@link model.PartitaPubblica}.
     */
    public void rimuoviGestioneExtraPartitaPubblica(String nomeUtente);

    /**
     * Aggiorna il {@link model.Moderatore} collegato al costruttore della
     * {@link model.PartitaPubblica} nel caso in cui il moderatore precedente
     * termini la sua gestione della partita.
     *
     * @param nomeUtente            Il nome utente del nuovo {@link model.Moderatore}
     *                              da aggiungere al campo moderatore legato al
     *                              costruttore della {@link model.PartitaPubblica}
     * @param codicePartitaPubblica Il codice della partita pubblica il cui moderatore
     *                              "principale" viene aggiornato.
     */
    public void aggiornaModeratorePartitaPubblica(String nomeUtente, String codicePartitaPubblica);
}
