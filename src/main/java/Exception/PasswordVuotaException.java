package exception;

/**
 * L'eccezione PasswordVuotaException viene lanciata quando un utente inserisce la propria password
 * +per accedere al sistema o per creare un nuovo account, ma la password inserita nel campo testuale risulta vuoto.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * all'utente di inserire un'altra password.
 */
public class PasswordVuotaException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public PasswordVuotaException(String message) {
        super(message);
    }
}
