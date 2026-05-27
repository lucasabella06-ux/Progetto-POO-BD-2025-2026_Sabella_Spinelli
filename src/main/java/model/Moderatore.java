package Model;

import java.util.ArrayList;

public class Moderatore extends Utente{
    private String codiceAccessoModeratore;
    private int orarioInizioAttivita;
    private int orarioFineAttivita;
    private ArrayList<PartitaPubblica> partiteGestite = new ArrayList<PartitaPubblica>();

    public Moderatore(String nomeUtente, String emailUtente, String passwordUtente, String codiceAccessoModeratore, int orarioInizioAttivita, int orarioFineAttivita) {
        super(nomeUtente, emailUtente, passwordUtente);
        this.codiceAccessoModeratore = codiceAccessoModeratore;
        this.orarioInizioAttivita = orarioInizioAttivita;
        this.orarioFineAttivita = orarioFineAttivita;
    }

    public Moderatore(String nomeUtente, String emailUtente, String passwordUtente, String codiceAccessoModeratore) {
        super(nomeUtente, emailUtente, passwordUtente);
        this.codiceAccessoModeratore = codiceAccessoModeratore;
    }

    public String getCodiceAccessoModeratore() {
        return codiceAccessoModeratore;
    }

    public void setCodiceAccessoModeratore(String codiceAccessoModeratore) {
        this.codiceAccessoModeratore = codiceAccessoModeratore;
    }

    public int getOrarioInizioAttivita() {
        return orarioInizioAttivita;
    }

    public void setOrarioInizioAttivita(int orarioInizioAttivita) {
        this.orarioInizioAttivita = orarioInizioAttivita;
    }

    public int getOrarioFineAttivita() {
        return orarioFineAttivita;
    }

    public void setOrarioFineAttivita(int orarioFineAttivita) {
        this.orarioFineAttivita = orarioFineAttivita;
    }

    public boolean loginModeratore(String nomeUtenteInserito, String emailInserita, String passwordInserita, String codiceModeratore) {
        return (super.login(nomeUtenteInserito, emailInserita, passwordInserita) && this.codiceAccessoModeratore.equals(codiceModeratore));
    }

    public ArrayList<Segnalazione> visualizzaSegnalazioni(Giocatore giocatoreSegnalato) {

        return giocatoreSegnalato.getSegnalazioniRicevute();

    }

    public void bandisciGiocatore(Giocatore giocatoreDaBandire) {
        if (giocatoreDaBandire.getIsAttivo()) {
            giocatoreDaBandire.cambiaIsAttivo();
            if (giocatoreDaBandire.getPartitaAttuale() != null) {
                giocatoreDaBandire.esciDallaPartita();
            }
            if (giocatoreDaBandire.getLobbyAttuale() != null) {
                giocatoreDaBandire.esciDallaLobby();
            }
        }
    }

    public void riattivaGiocatore(Giocatore giocatoreDaRiattivare) {
        if(!giocatoreDaRiattivare.getIsAttivo()){
            giocatoreDaRiattivare.cambiaIsAttivo();
        }
    }

    public void aggiungiPartitaGestita(PartitaPubblica p)
    {
        partiteGestite.add(p);
    }

    public void rimuoviPartitaGestita(PartitaPubblica p)
    {
        partiteGestite.remove(p);
    }
}
