package Model;

import java.util.ArrayList;

public class PartitaAmichevole extends Partita{
    public PartitaAmichevole(String codicePartita, String videogioco) {
        super(codicePartita, videogioco);
    }

    public PartitaAmichevole(String codicePartita, String videogioco, ArrayList<Giocatore> partecipantiPartita) {
        super(codicePartita, videogioco, partecipantiPartita);
    }
}
