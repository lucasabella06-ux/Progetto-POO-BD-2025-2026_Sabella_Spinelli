package exception;

/**
 * L'eccezione GiocatoreOfflineException viene lanciata quando un {@link model.Giocatore} prova ad entrare o
 * a creare una {@link model.Lobby}, ma il suo profilo risulta offline a seguito di un cambio del selettore di stato
 * nella {@link gui.HomeGiocatore}.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e mostrare al giocatore
 * la finestra informativa col messaggio d'errore.
 */
public class GiocatoreOfflineException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public GiocatoreOfflineException(String message) {
        super(message);
    }
}
