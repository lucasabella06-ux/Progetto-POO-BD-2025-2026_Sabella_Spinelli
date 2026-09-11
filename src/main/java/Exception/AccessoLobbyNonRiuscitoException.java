package exception;

/**
 * L'eccezione AccessoLobbyNonRiuscitoException viene lanciata quando il {@link model.Giocatore} loggato, nel tentare
 * l'accesso ad una {@link model.Lobby}, inserisce un codice d'accesso che non appartiene a nessuna delle lobby
 * presenti nel sistema.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * al giocatore di reinserire il codice oppure di scegliere un'altra modalità di gioco.
 */
public class AccessoLobbyNonRiuscitoException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public AccessoLobbyNonRiuscitoException(String message) {
        super(message);
    }
}
