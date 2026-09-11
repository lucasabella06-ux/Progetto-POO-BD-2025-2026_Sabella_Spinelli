package dao;

import java.util.ArrayList;

/**
 * L'interfaccia LobbyDAO gestisce la persistenza dei dati relativi alle {@link model.Lobby} e
 * la partecipazione dei {@link model.Giocatore} ad esse nel sistema, definendo metodi per
 * la gestione e l'aggiornamento di tali dati.
 */
public interface LobbyDAO {
    /**
     * Riempie le liste fornite come parametri con i dati delle {@link model.Lobby} presenti nel sistema.
     *
     * @param listaCodiciLobby      Lista in cui inserire i codici delle lobby recuperati.
     * @param listaCapienzeMaxLobby Lista in cui inserire le capienze massime delle lobby
     *                              recuperate.
     * @param listaHost             Lista in cui inserire i nomi utente degli host delle
     *                              lobby recuperati.
     */
    public void generaLobby(ArrayList<String> listaCodiciLobby, ArrayList<Integer> listaCapienzeMaxLobby, ArrayList<String> listaHost);

    /**
     * Riempie le liste fornite come parametri con i dati dei {@link model.Giocatore} partecipanti
     * delle {@link model.Lobby} presenti nel sistema (i nomi utenti forniti non includono quelli
     * dell'host).
     *
     * @param listaNomi        Lista in cui inserire i nomi utenti dei partecipanti recuperati.
     * @param listaCodiciLobby Lista in cui inserire i codici delle lobby recuperati.
     */
    public void generaPartecipantiLobby(ArrayList<String> listaNomi, ArrayList<String> listaCodiciLobby);

    /**
     * Registra l'aggiunta di una nuova {@link model.Lobby} nel sistema.
     *
     * @param codiceLobby Il codice univoco della {@link model.Lobby}.
     * @param CapienzaMax La capienza massima della {@link model.Lobby}.
     * @param host        Il nome utente del {@link model.Giocatore} host della lobby.
     */
    public void aggiungiLobby(String codiceLobby, int CapienzaMax, String host);

    /**
     * Registra l'eliminazione di una {@link model.Lobby} presente nel sistema.
     *
     * @param codiceLobby Il codice della {@link model.Lobby} da rimuovere.
     */
    public void rimuoviLobby(String codiceLobby);

    /**
     * Registra l'aggiunta di un nuovo {@link model.Giocatore} partecipante alla
     * {@link model.Lobby}
     *
     * @param nomeUtente  Il nome utente del {@link model.Giocatore} che si è unito alla lobby.
     * @param codiceLobby Il codice della {@link model.Lobby} a cui si è unito il giocatore.
     */
    public void aggiungiGiocatoreAllaLobby(String nomeUtente, String codiceLobby);

    /**
     * Registra l'uscita di un {@link model.Giocatore} partecipante dalla lobby.
     *
     * @param nomeUtente Il nome utente del {@link model.Giocatore} che è uscito
     * dalla lobby.
     */
    public void rimuoviGiocatoreDallaLobby(String nomeUtente);
}
