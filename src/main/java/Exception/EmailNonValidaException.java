package exception;

/**
 * L'eccezione EmailNonValidaException viene lanciata quando un utente inserisce il proprio indirizzo email
 * per accedere al sistema o per creare un nuovo account, ma l'indirizzo inserito non contiene i caratteri necessari
 * per essere considerato un indirizzo valido.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * all'utente di inserire un altro indirizzo.
 */
public class EmailNonValidaException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public EmailNonValidaException(String message) {
        super(message);
    }
}
