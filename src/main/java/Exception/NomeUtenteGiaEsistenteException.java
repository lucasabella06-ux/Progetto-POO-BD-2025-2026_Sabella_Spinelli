package exception;

/**
 * L'eccezione NomeUtenteGiaEsistenteException viene lanciata quando un utente inserisce un nome utente per creare
 * un nuovo account, ma il nome utente inserito appartiene già ad un altro {@link model.Utente} presente
 * nel sistema.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * all'utente di inserire un altro nome utente.
 */
public class NomeUtenteGiaEsistenteException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public NomeUtenteGiaEsistenteException(String message) {
        super(message);
    }
}
