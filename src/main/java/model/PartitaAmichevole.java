package model;

import java.util.ArrayList;

/**
 * La classe PartitaAmichevole rappresenta una partita amichevole all'interno del sistema,
 * estende la classe {@link Partita}.Le partite amichevoli possono essere create solo
 * all'interno di {@link Lobby}.
 */
public class PartitaAmichevole extends Partita{
    /**
     * Istanzia un nuovo oggetto PartitaAmichevole, chiamando il costruttore della classe padre {@link Partita}.
     *
     * @param codicePartita Il codice univoco identificativo della partita passato alla superclasse {@link Partita}.
     * @param videogioco    Il videogioco su cui si gioca la partita passato alla superclasse {@link Partita}.
     * @param capienza      La capienza della partita passata alla superclasse {@link Partita}.
     */
    public PartitaAmichevole(String codicePartita, String videogioco, int capienza) {
        super(codicePartita, videogioco, capienza);
    }

}
