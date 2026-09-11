package exception;

/**
 * L'eccezione CodiceModeratoreErratoException viene lanciata quando il {@link model.Moderatore} loggato inserisce
 * il suo codice segreto univoco per entrare nel sistema oppure eseguire una sua operazione all'interno del sistema
 * (come gestire una {@link model.PartitaPubblica} oppure bandire un {@link model.Giocatore}), ma il codice inserito
 * nel campo testuale non corrisponde con il codice del moderatore.
 * È un'eccezione controllata per richiedere esplicitamente al sistema di gestire l'errore e permettere
 * al moderatore di reinserire il codice.
 */
public class CodiceModeratoreErratoException extends Exception {
    /**
     * Istanzia una nuova eccezione con il messaggio di errore specificato.
     *
     * @param message La descrizione testuale dell'errore rilevato.
     */
    public CodiceModeratoreErratoException(String message) {
        super(message);
    }
}
