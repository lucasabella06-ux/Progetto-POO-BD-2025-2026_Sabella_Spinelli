package Model;

import java.util.ArrayList;

public class PartitaPubblica extends Partita {
    private ArrayList<Moderatore> moderatori = new ArrayList<Moderatore>();
    public PartitaPubblica(String codicePartita, String videogioco, Moderatore m) {
        super(codicePartita, videogioco);
        moderatori.add(m);
    }

    public PartitaPubblica(String codicePartita, String videogioco, ArrayList<Giocatore> partecipantiPartita, Moderatore m) {
        super(codicePartita, videogioco, partecipantiPartita);
        moderatori.add(m);
    }

    public void aggiungiRisultato(Giocatore vincitore, String durata)
    {
        StoricoPartita risultato = new StoricoPartita(vincitore, durata, this, getPartecipantiPartita());
        for (Giocatore g : getPartecipantiPartita())
        {
            g.getStorico().add(risultato);
            g.verificaSalitaDiLivello();
            if(g == vincitore) {
                g.aggiungiPartitaVinta();
            }
            else{
                g.aggiungiPartitaPersa();
            }
        }
        getPartecipantiPartita().removeAll(getPartecipantiPartita());
    }
}
