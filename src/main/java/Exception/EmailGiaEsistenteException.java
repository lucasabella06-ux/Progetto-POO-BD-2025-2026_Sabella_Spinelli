package exception;

/**
 * L'eccezione EmailGiaEsistenteException viene lanciata quando un utente inserisce il proprio indirizzo email
 * per creare un nuovo account, ma l'indirizzo inserito appartiene già ad un altro {@link model.Utente} presente
 * nel sistema.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * all'utente di inserire un altro indirizzo.
 */
public class EmailGiaEsistenteException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public EmailGiaEsistenteException(String message) {
        super(message);
    }
}
