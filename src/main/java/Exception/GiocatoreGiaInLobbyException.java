package exception;

/**
 * L'eccezione GiocatoreGiaInLobbyException viene lanciata quando un {@link model.Giocatore} prova ad entrare o
 * a creare una {@link model.Lobby}, ma risulta già all'interno di un'altra lobby.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e mostrare al giocatore
 * la finestra informativa col messaggio d'errore.
 */
public class GiocatoreGiaInLobbyException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public GiocatoreGiaInLobbyException(String message) {
        super(message);
    }
}
