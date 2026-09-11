package dao;

import java.util.ArrayList;

/**
 * L'interfaccia GiocatoriDAO gestisce la persistenza dei dati relativi ai {@link model.Giocatore} del sistema,
 * definendo metodi per la gestione di tali dati e il loro aggiornamento.
 */
public interface GiocatoriDAO {
    /**
     * Riempie le liste fornite come parametri con i dati dei {@link model.Giocatore} presenti nel sistema.
     *
     * @param listaNomi         Lista in cui inserire i nomi utente recuperati.
     * @param listaEmail        Lista in cui inserire le mail recuperate.
     * @param listaPassword     Lista in cui inserire le password recuperate.
     * @param listaIdGiocatori  Lista in cui inserire gli identificativi dei giocatori recuperati.
     * @param listaLivelli      Lista in cui inserire i livelli recuperati.
     * @param listaPartiteVinte Lista in cui inserire i valori delle partite vinte recuperati.
     * @param listaPartitePerse Lista in cui inserire i valori delle partite perse recuperati.
     * @param listaIsAttivo     Lista in cui inserire i valori isAttivo recuperati.
     */
    public void generaGiocatori(ArrayList<String> listaNomi, ArrayList<String> listaEmail, ArrayList<String> listaPassword,
                                ArrayList<String> listaIdGiocatori, ArrayList<Integer> listaLivelli, ArrayList<Integer> listaPartiteVinte,
                                ArrayList<Integer> listaPartitePerse, ArrayList<String> listaIsAttivo);

    /**
     * Registra l'aggiunta di un nuovo {@link model.Giocatore} nel sistema con parametri standard relativi al livello,
     * alle partite vinte e alle partite perse.
     *
     * @param nomeUtente  Il nome utente del {@link model.Giocatore}
     * @param email       L'email del {@link model.Giocatore}
     * @param password    La password del {@link model.Giocatore}
     * @param idGiocatore Il codice identificativo univoco del {@link model.Giocatore}
     */
    public void aggiungiGiocatore(String nomeUtente, String email, String password, String idGiocatore );

    /**
     * Registra il ban di un {@link model.Giocatore} dal sistema, procedendo alla sua rimozione dallo stesso.
     *
     * @param nomeUtente Il nome utente del giocatore da rimuovere.
     */
    public void rimuoviGiocatore(String nomeUtente);

    /**
     * Registra la riattivazione di un {@link model.Giocatore} nel sistema.
     *
     * @param nomeUtente Il nome utente del giocatore da riattivare.
     */
    public void riattivaGiocatore(String nomeUtente);

    /**
     * Aggiorna il livello di un {@link model.Giocatore} nel sistema.
     *
     * @param nomeUtente Il nome utente del giocatore di cui aggiornare il livello.
     */
    public void aggiornaLivello(String nomeUtente);

    /**
     * Aggiorna il valore delle partite vinte da un {@link model.Giocatore} nel sistema.
     *
     * @param nomeUtente Il nome utente del giocatore di cui aggiornare le partite vinte.
     */
    public void aggiornaPartiteVinte(String nomeUtente);

    /**
     * Aggiorna il valore delle partite perse da un {@link model.Giocatore} nel sistema.
     *
     * @param nomeUtente Il nome utente del giocatore di cui aggiornare le partite perse.
     */
    public void aggiornaPartitePerse(String nomeUtente);
}
