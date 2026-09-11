package exception;

/**
 * L'eccezione GiocatoreBannatoException viene lanciata quando un {@link model.Giocatore}accede al sistema
 * con le sue credenziali ma il suo account risulta esser stato bandito da un moderatore.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e mostrare al giocatore
 * la finestra informativa col messaggio d'errore.
 */
public class GiocatoreBannatoException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public GiocatoreBannatoException(String message) {
        super(message);
    }
}
