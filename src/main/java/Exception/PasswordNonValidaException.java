package exception;

/**
 * L'eccezione PasswordNonValidaException viene lanciata quando un utente inserisce la propria password
 * per accedere al sistema o per creare un nuovo account, ma la password inserita non contiene il numero
 * di caratteri necessario per essere considerato una password valida.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * all'utente di inserire un'altra password.
 */
public class PasswordNonValidaException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public PasswordNonValidaException(String message) {
        super(message);
    }
}
