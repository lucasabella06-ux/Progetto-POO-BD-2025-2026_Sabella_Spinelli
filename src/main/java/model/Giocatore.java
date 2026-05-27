package Model;

import java.util.Date;

import java.util.ArrayList;

public class Giocatore extends Utente implements Comparable<Giocatore>{
    String idGiocatore;
    private int livello;
    private final int livelloMax = 100;
    public enum gradoGiocatore{
        BRONZO, ARGENTO, ORO, PLATINO, DIAMANTE;
    }
    private gradoGiocatore grado;
    private boolean statoGiocatore = true;
    private boolean isAttivo = true;
    private int partiteVinte;
    private int partitePerse;
    private ArrayList<Segnalazione> segnalazioniEffettuate = new ArrayList<Segnalazione>();
    private ArrayList<Segnalazione> segnalazioniRicevute = new ArrayList<Segnalazione>();
    private ArrayList<String> messaggiInviati = new ArrayList<String>();
    private ArrayList<String> messaggiRicevuti = new ArrayList<String>();
    private Lobby lobbyCreata = null;
    private Lobby lobbyAttuale = null;
    private Partita partitaAttuale = null;
    private ArrayList<StoricoPartita> storico = new ArrayList<StoricoPartita>();

    public Giocatore(String nomeUtente, String emailUtente, String passwordUtente, String idGiocatore, int livello, int partiteVinte, int partitePerse) {
        super(nomeUtente, emailUtente, passwordUtente);
        this.idGiocatore = idGiocatore;
        this.livello = livello;
        this.grado = assegnaGrado(livello);
        this.partiteVinte = partiteVinte;
        this.partitePerse = partitePerse;
    }

    public Giocatore(String nomeUtente, String emailUtente, String passwordUtente, String idGiocatore) {
        super(nomeUtente, emailUtente, passwordUtente);
        this.idGiocatore = idGiocatore;
        this.livello = 1;
        this.grado = gradoGiocatore.BRONZO;
        this.partiteVinte = 0;
        this.partitePerse = 0;
    }

    public gradoGiocatore assegnaGrado(int livello)
    {
        if(livello>=1 && livello <= 25) return gradoGiocatore.BRONZO;
        else if(livello >= 26 && livello <= 50) return gradoGiocatore.ARGENTO;
        else if(livello >= 51 && livello <= 75) return gradoGiocatore.ORO;
        else if(livello >= 76 && livello <= 90) return gradoGiocatore.PLATINO;
        else if(livello >= 91 && livello <= 100) return gradoGiocatore.DIAMANTE;
        else return null;
    }

    public void creaLobby(String codiceLobby, int capienzaMax)
    {
        if(this.lobbyAttuale == null && this.lobbyCreata == null) {
            this.lobbyCreata = new Lobby(codiceLobby, capienzaMax, this);
            this.lobbyAttuale = lobbyCreata;
        }
    }

    public void eliminaLobby(){
        for(Giocatore g: lobbyCreata.visualizzaGiocatori())
        {
            lobbyCreata.rimuoviGiocatori(g);
        }
        this.lobbyCreata = null;
    }

    public void entraInLobby(Lobby lobby, String codiceInserito){
        if(this.lobbyAttuale == null && this.lobbyCreata == null)
        {
            if(lobby.getCodiceLobby().equals(codiceInserito))
            {
                this.lobbyAttuale = lobby;
                lobby.aggiungiGiocatori(this);
            }
        }
    }

    public void esciDallaLobby(){
        if(lobbyAttuale != null && lobbyCreata == null) {
            lobbyAttuale.rimuoviGiocatori(this);
            this.lobbyAttuale = null;
        }
    }

    public void mandaMessaggio(Giocatore destinatario, String messaggio)
    {
        this.messaggiInviati.add(messaggio);
        destinatario.messaggiRicevuti.add(messaggio);
    }
    public void effettuaSegnalazione(Giocatore giocatoreSegnalato, Date dataSegnalazione, Segnalazione.Motivo motivoSegnalazione) {
       Segnalazione nuovaSegnalazione = new Segnalazione(this, giocatoreSegnalato, dataSegnalazione, motivoSegnalazione);
    }

    public void modificaStatoGiocatore(){
        if (this.statoGiocatore) this.statoGiocatore = false;
        else this.statoGiocatore = true;
    }

    public ArrayList<Segnalazione> getSegnalazioniEffettuate() {
        return segnalazioniEffettuate;
    }

    public ArrayList<Segnalazione> getSegnalazioniRicevute() {
        return segnalazioniRicevute;
    }

    public ArrayList<String> getMessaggiInviati() {
        return messaggiInviati;
    }

    public ArrayList<String> getMessaggiRicevuti() {
        return messaggiRicevuti;
    }

    public boolean entraInPartita(Partita partita)
    {
        if(partitaAttuale == null) {
            partitaAttuale = partita;
            partita.getPartecipantiPartita().add(this);
            return true;
        }
        else return false;
    }

    public void esciDallaPartita()
    {
       if (partitaAttuale != null){
           partitaAttuale.getPartecipantiPartita().remove(this);
           this.partitaAttuale = null;
       }
    }

    public Partita getPartitaAttuale() {
        return partitaAttuale;
    }

    public ArrayList<StoricoPartita> getStorico() {
        return storico;
    }

    @Override
    public int compareTo(Giocatore g) {

            int compVinte = Integer.compare(g.partiteVinte, this.partiteVinte);
            if (compVinte != 0) {
                return compVinte;
            }

            int compPerse = Integer.compare(this.partitePerse, g.partitePerse);
            if (compPerse != 0) {
                return compPerse;
            }

            return Integer.compare(g.livello, this.livello);
    }

    public boolean getIsAttivo() {
        return isAttivo;
    }

    public void cambiaIsAttivo() {
        if(isAttivo) isAttivo = false;
        else isAttivo = true;
    }

    public Lobby getLobbyAttuale() {
        return lobbyAttuale;
    }

    public int getPartiteVinte() {
        return partiteVinte;
    }

    public int getPartitePerse() {
        return partitePerse;
    }

    public void aggiungiPartitaVinta() {
        partiteVinte++;
    }

    public void aggiungiPartitaPersa() {
        partitePerse++;
    }

    public void verificaSalitaDiLivello (){
        if((partiteVinte / livello) >= 10 )
        {
            livello++;
            this.assegnaGrado(livello);
        }
    }

    public String getIdGiocatore() {
        return idGiocatore;
    }

    public int getLivello() {
        return livello;
    }

    public String toString(){
        return "Nome Giocatore: "+this.getNomeUtente()+"\n ID giocatore: "+this.getIdGiocatore()+"\n livello: "+this.getLivello()+"\n Partite Vinte: "+this.getPartiteVinte()+"\n Partite Perse: "+this.getPartitePerse();
    }
}
