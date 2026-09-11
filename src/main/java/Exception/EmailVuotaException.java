package exception;

/**
 * L'eccezione EmailVuotaException viene lanciata quando un utente inserisce il proprio indirizzo email
 * per accedere al sistema o per creare un nuovo account, ma l'indirizzo inserito nel campo testuale risulta vuoto.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * all'utente di inserire un altro indirizzo.
 */
public class EmailVuotaException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public EmailVuotaException(String message) {
        super(message);
    }
}
