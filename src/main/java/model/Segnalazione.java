package Model;

import java.util.Date;

public class Segnalazione {
    private Giocatore giocatoreSegnalante;
    private Giocatore giocatoreSegnalato;
    private Date dataSegnalazione;
    public enum Motivo{
        COMPORTAMENTO_SCORRETTO, TRUCCHI, INATTIVITA_GIOCATORE, SPAM, ALTRO;
    }
    private Motivo motivoSegnalazione;

    public Segnalazione(Giocatore giocatoreSegnalante, Giocatore giocatoreSegnalato, Date dataSegnalazione, Motivo motivoSegnalazione) {
        this.giocatoreSegnalante = giocatoreSegnalante;
        this.giocatoreSegnalato = giocatoreSegnalato;
        this.dataSegnalazione = dataSegnalazione;
        this.motivoSegnalazione = motivoSegnalazione;
        giocatoreSegnalato.getSegnalazioniRicevute().add(this);
    }
}
