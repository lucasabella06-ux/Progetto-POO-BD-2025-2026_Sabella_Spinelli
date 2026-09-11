package exception;

/**
 * L'eccezione CapienzaMassimaLobbyRaggiuntaException viene lanciata quando il {@link model.Giocatore} loggato inserisce
 * il codice giusto per entrare in una {@link model.Lobby}, ma la lobby a cui intende unirsi ha già il numero massimo di
 * partecipanti consentiti.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * al giocatore di inserire un altro codice oppure di scegliere un'altra modalità di gioco.
 */
public class CapienzaMassimaLobbyRaggiuntaException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public CapienzaMassimaLobbyRaggiuntaException(String message) {
        super(message);
    }
}
