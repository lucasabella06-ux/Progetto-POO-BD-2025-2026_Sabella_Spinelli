package exception;

/**
 * L'eccezione CodiceLobbyVuotoException viene lanciata quando il {@link model.Giocatore} loggato inserisce
 * un codice per creare una {@link model.Lobby}, ma il codice inserito nel campo testuale risulta vuoto.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * al giocatore di inserire un altro codice.
 */
public class CodiceLobbyVuotoException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public CodiceLobbyVuotoException(String message) {
        super(message);
    }
}
