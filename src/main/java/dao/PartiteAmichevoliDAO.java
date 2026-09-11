package dao;

import java.util.ArrayList;

/**
 * L'interfaccia PartiteAmichevoliDAO gestisce la persistenza dei dati relativi alle {@link model.PartitaPubblica}
 * generate dalle {@link model.Lobby} presenti nel sistema, definendo metodi per la gestione di tali dati e il
 * loro aggiornamento.
 */
public interface PartiteAmichevoliDAO {
    /**
     * Riempie le liste fornite come parametri con i dati delle {@link model.PartitaAmichevole} presenti nel
     * sistema, considerando anche le {@link model.Lobby} che hanno creato tali partite.
     *
     * @param listaCodiciLobby      Lista in cui inserire i codici delle lobby recuperati.
     * @param listaCodiciPartiteAmichevoli Lista in cui inserire i codici delle partite amichevoli
     *                                     recuperate.
     * @param listaVideogiochi             Lista in cui inserire i videogiochi su cui si giocano
     *                                     le partite amichevoli recuperate.
     * @param listaCapienze                Lista in cui inserire le capienze delle partite amichevoli
     *                                     recuperate.
     */
    public void generaPartiteAmichevoli(ArrayList<String> listaCodiciLobby, ArrayList<String> listaCodiciPartiteAmichevoli, ArrayList<String> listaVideogiochi, ArrayList<Integer> listaCapienze);

    /**
     * Registra la creazione di una nuova {@link model.PartitaAmichevole} da parte di
     * una {@link model.Lobby} nel sistema.
     *
     * @param codiceLobby             Il codice identificativo della {@link model.Lobby} che ha generato
     *                                la {@link model.PartitaAmichevole}.
     * @param codicePartitaAmichevole Il codice identificativo della {@link model.PartitaAmichevole}.
     * @param videogioco              Il videogioco su cui si gioca la {@link model.PartitaAmichevole}
     * @param capienza                La capienza della {@link model.PartitaAmichevole}.
     */
    public void aggiungiPartitaAmichevole(String codiceLobby, String codicePartitaAmichevole, String videogioco, int capienza);

    /**
     * Registra l'eliminazione di una {@link model.PartitaAmichevole} presente nel sistema.
     *
     * @param codiceLobby Il codice della {@link model.Lobby} a cui appartiene la
     *                    partita amichevole da rimuovere.
     */
    public void rimuoviPartitaAmichevole(String codiceLobby);
}
