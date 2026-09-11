package exception;

/**
 * L'eccezione GiocatoreGiaInPartitaException viene lanciata quando un {@link model.Giocatore} prova ad entrarein
 * una {@link model.PartitaPubblica}, ma risulta già all'interno di un'altra {@link model.Partita}.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e mostrare al giocatore
 * la finestra informativa col messaggio d'errore.
 */
public class GiocatoreGiaInPartitaException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public GiocatoreGiaInPartitaException(String message) {
        super(message);
    }
}
