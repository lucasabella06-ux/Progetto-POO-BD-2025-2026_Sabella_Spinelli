package model;

import java.util.Date;

import java.util.ArrayList;

/**
 * La classe Giocatore rappresenta un giocatore all'interno del sistema.
 * Estende la classe astratta {@link Utente} e implementa l'interfaccia Comparable per consentire l'override del metodo
 * compareTo, il quale permette di ordinare i giocatori in base alle loro statistiche.
 */
public class Giocatore extends Utente implements Comparable<Giocatore>{
    /**
     * L'identificativo univoco del giocatore.
     */
    private String idGiocatore;
    /**
     * Il livello attuale del giocatore.
     */
    private int livello;
    /**
     * Il livello massimo raggiungibile da un giocatore.
     */
    private final int livelloMax = 100;

    /**
     * Un'enumerazione che definisce i gradi competitivi associati al giocatore in base al livello.
     */
    public enum GradoGiocatore {
        /**
         * Il grado per i giocatori di livello da 1 a 25.
         */
        BRONZO,
        /**
         * Il grado per i giocatori di livello da 26 a 50.
         */
        ARGENTO,
        /**
         * Il grado per i giocatori di livello da 51 a 75.
         */
        ORO,
        /**
         * Il grado per i giocatori di livello da 76 a 90.
         */
        PLATINO,
        /**
         * Il grado per i giocatori di livello da 91 a 100.
         */
        DIAMANTE;
    }
    private GradoGiocatore grado;
    /**
     * Lo stato online/offline del giocatore. {@code true} se il giocatore è online, {@code false} se è offline.
     */
    private boolean statoGiocatore = true;
    /**
     * Lo stato di attività del giocatore all'interno del sistema, cioè se il profilo del giocatore è attivo oppure è stato
     * bannato da un moderatore, per questo tutti i giocatori sono istanziati con {@code true}.
     * Il parametro isAttivo verrà cambiato a {@code false} solo in caso di ban del profilo del giocatore da parte di un moderatore.
     */
    private boolean isAttivo = true;
    /**
     * Il numero di partite vinte dal giocatore.
     */
    private int partiteVinte;
    /**
     * Il numero di partite perse dal giocatore.
     */
    private int partitePerse;
    /**
     * Elenco delle segnalazioni effettuate dal giocatore verso altri giocatori.
     */
    private ArrayList<Segnalazione> segnalazioniEffettuate = new ArrayList<Segnalazione>();
    /**
     * Elenco delle segnalazioni ricevute dal giocatore da parte di altri giocatori.
     */
    private ArrayList<Segnalazione> segnalazioniRicevute = new ArrayList<Segnalazione>();
    /**
     * La lobby creata e gestita dal giocatore come host.
     */
    private Lobby lobbyCreata = null;
    /**
     * La lobby in cui il giocatore si trova come partecipante o come host.
     * Se il giocatore è host della lobby questo parametro coincide con lobbyCreata.
     */
    private Lobby lobbyAttuale = null;
    /**
     * La partita a cui il giocatore sta partecipando. Può essere una {@link PartitaAmichevole} oppure {@link PartitaPubblica}
     */
    private Partita partitaAttuale = null;
    /**
     * Elenco dei risultati delle partite disputate dal giocatore durante una sessione di gioco
     * (con sessione di gioco si intende il periodo tra l'apertura dell'applicazione e la sua chiusura).
     */
    private ArrayList<StoricoPartita> storico = new ArrayList<StoricoPartita>();

    /**
     * Istanzia un nuovo oggetto Giocatore partendo da parametri non standard.
     *
     * @param nomeUtente     Il nome identificativo ereditato da {@link Utente}.
     * @param emailUtente    L'indirizzo email ereditato da {@link Utente}.
     * @param passwordUtente La password ereditata da {@link Utente}.
     * @param idGiocatore    Il codice univoco per identificare il giocatore.
     * @param livello        Il livello del giocatore .
     * @param partiteVinte   Il numero di partite vinte dal giocatore.
     * @param partitePerse   Il numero di partite perse dal giocatore.
     */
    public Giocatore(String nomeUtente, String emailUtente, String passwordUtente, String idGiocatore, int livello, int partiteVinte, int partitePerse) {
        super(nomeUtente, emailUtente, passwordUtente);
        this.idGiocatore = idGiocatore;
        this.livello = livello;
        this.grado = assegnaGrado(livello);
        this.partiteVinte = partiteVinte;
        this.partitePerse = partitePerse;
    }

    /**
     * Istanzia un nuovo oggetto Giocatore con parametri statistici standard (livello settato a 1, partite vinte e perse a 0).
     * @param nomeUtente     Il nome identificativo passato alla superclasse {@link Utente}.
     * @param emailUtente    L'indirizzo email passato alla superclasse {@link Utente}.
     * @param passwordUtente La password passata alla superclasse {@link Utente}.
     * @param idGiocatore    Il codice univoco per identificare il giocatore, generato automaticamente dal sistema.
     */
    public Giocatore(String nomeUtente, String emailUtente, String passwordUtente, String idGiocatore) {
        super(nomeUtente, emailUtente, passwordUtente);
        this.idGiocatore = idGiocatore;
        this.livello = 1;
        this.grado = GradoGiocatore.BRONZO;
        this.partiteVinte = 0;
        this.partitePerse = 0;
    }

    /**
     * Assegna il grado del giocatore in base al valore numerico del livello.
     * I gradi sono assegnati nei seguenti intervalli di livello:
     * Da 1 a 25: BRONZO
     * Da 26 a 50: ARGENTO
     * Da 51 a 75: ORO
     * Da 76 a 90: PLATINO
     * Da 91 a 100: DIAMANTE
     *
     * @param livello Il livello attuale del giocatore.
     * @return Il corrispondente grado del giocatore oppure null se il valore del livello non è valido.
     */
    public GradoGiocatore assegnaGrado(int livello) {
        if(livello >= 1 && livello <= 25) return GradoGiocatore.BRONZO;
        else if(livello >= 26 && livello <= 50) return GradoGiocatore.ARGENTO;
        else if(livello >= 51 && livello <= 75) return GradoGiocatore.ORO;
        else if(livello >= 76 && livello <= 90) return GradoGiocatore.PLATINO;
        else if(livello >= 91 && livello <= 100) return GradoGiocatore.DIAMANTE;
        else return null;
    }

    /**
     * Istanzia una nuova {@link Lobby} di cui il giocatore diventa l'host.
     * La creazione avviene con successo e modifica il parametro lobbyCreata solo se il giocatore
     * non si trova o non ha creato già un'altra lobby (ovvero se lobbyAttuale è null).
     *
     * @param codiceLobby Il codice identificativo della nuova lobby inserito dal giocatore.
     * @param capienzaMax Il numero massimo di partecipanti ammessi nella lobby selezionato dall'utente.
     */
    public void creaLobby(String codiceLobby, int capienzaMax) {
        if(this.lobbyAttuale == null && this.lobbyCreata == null) {
            this.lobbyCreata = new Lobby(codiceLobby, capienzaMax, this);
            this.lobbyAttuale = lobbyCreata;
        }
    }

    /**
     * Elimina definitivamente la {@link Lobby} creata dal giocatore.
     * Prima dell'eliminazione, il metodo espelle ciclicamente tutti i partecipanti
     * finchè l'elenco dei partecipanti della lobby non risulta vuoto
     * e infine riporta i valori di lobbyAttuale e lobbyCreata a null.
     */
    public void eliminaLobby(){
        while (!lobbyCreata.visualizzaGiocatori().isEmpty()) {
            lobbyCreata.rimuoviGiocatori(lobbyCreata.getPartecipantiLobby().getFirst());
        }
        this.lobbyAttuale = null;
        this.lobbyCreata = null;
    }

    /**
     * Consente al giocatore di partecipare a una {@link Lobby} creata da un altro Giocatore.
     * L'operazione fallisce se il giocatore si trova o ha già creato un'altra lobby.
     *
     * @param lobby La lobby a cui il giocatore vuole unirsi.
     * @return {@code true} se l'operazione di ingresso nella lobby riesce, {@code false} altrimenti..
     */
    public boolean entraInLobby(Lobby lobby){
        if(this.lobbyAttuale == null && this.lobbyCreata == null) {
            this.lobbyAttuale = lobby;
            lobby.aggiungiGiocatori(this);
            return true;
        }
        else return false;
    }

    /**
     * Permette al giocatore di uscire dalla {@link Lobby} in cui si trova come partecipante.
     * Rimuove il giocatore dall'elenco dei partecipanti alla lobby e setta il parametro lobbyAttuale a null.
     */
    public void esciDallaLobby(){
        lobbyAttuale.rimuoviGiocatori(this);
        this.lobbyAttuale = null;
    }

    /**
     * Istanzia una {@link Segnalazione} fatta dal giocatore contro un altro giocatore.
     * La segnalazione viene salvata nella lista delle segnalazioni effettuate dal giocatore.
     *
     * @param giocatoreSegnalato Il {@link Giocatore} verso cui è rivolta la segnalazione.
     * @param dataSegnalazione   La data in cui è stata inviata la segnalazione.
     * @param motivoSegnalazione La motivazione specifica della segnalazione descritta dal giocatore, ricavata dall'enumerazione {@link Segnalazione.Motivo}.
     */
    public void effettuaSegnalazione(Giocatore giocatoreSegnalato, Date dataSegnalazione, Segnalazione.Motivo motivoSegnalazione) {
        Segnalazione nuovaSegnalazione = new Segnalazione(this, giocatoreSegnalato, dataSegnalazione, motivoSegnalazione);
        segnalazioniEffettuate.add(nuovaSegnalazione);
    }

    /**
     * Aggiorna lo stato online/offline del giocatore.
     *
     * @param statoGiocatore Il nuovo valore da assegnare allo stato.
     */
    public void setStatoGiocatore(boolean statoGiocatore) {
        this.statoGiocatore = statoGiocatore;
    }

    /**
     * Restituisce l'elenco di tutte le {@link Segnalazione} ricevute dal giocatore da parte di altri giocatori.
     *
     * @return Un ArrayList contenente gli oggetti istanza di {@link Segnalazione} ricevuti dal giocatore.
     */
    public ArrayList<Segnalazione> getSegnalazioniRicevute() {
        return segnalazioniRicevute;
    }

    /**
     * Permette al giocatore l'ingresso in una {@link PartitaAmichevole} o {@link PartitaPubblica}
     * L'ingresso viene bloccato se il giocatore sta già partecipando ad un'altra partita in contemporanea.
     *
     * @param partita La partita a cui il giocatore intende partecipare.
     * @return {@code true} se l'ingresso riesce, {@code false} altrimenti.
     */
    public boolean entraInPartita(Partita partita) {
        if(partitaAttuale == null) {
            partitaAttuale = partita;
            partita.getPartecipantiPartita().add(this);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Permette al giocatore di uscire dalla partita in cui si trova,
     * riportando il parametro partitaAttuale a null.
     */
    public void esciDallaPartita() {
        if (partitaAttuale != null){
            partitaAttuale.getPartecipantiPartita().remove(this);
            this.partitaAttuale = null;
        }
    }

    /**
     * Restituisce la sessione di gioco attiva in cui l'utente è attualmente impegnato.
     *
     * @return L'oggetto {@link Partita} corrente, oppure {@code null} se il giocatore è libero.
     */
    public Partita getPartitaAttuale() {
        return partitaAttuale;
    }

    /**
     * Restituisce l'elenco cronologico dei risultati delle {@link PartitaPubblica} a cui il giocatore ha partecipato
     * durante la sessione di gioco corrente.
     *
     * @return L'elenco contenente gli oggetti istanza di {@link StoricoPartita}.
     */
    public ArrayList<StoricoPartita> getStorico() {
        return storico;
    }

    /**
     * Confronta questo giocatore con un altro per stabilire qual è il migliore.
     * L'ordinamento tiene conto dei seguenti criteri in ordine di importanza:
     * Maggior numero di partiteVinte (ordine decrescente).
     * Minor numero di partitePerse (ordine crescente).
     * Livello più alto (ordine decrescente).
     *
     * @param g Il secondo giocatore con cui il giocatore corrente viene comparato.
     * @return Un intero negativo se il giocatore corrente è migliore di g, positivo se non lo è, zero se hanno pari statistiche.
     */
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

    /**
     * Verifica se il giocatore risulta attualmente abilitato e attivo nel sistema.
     *
     * @return {@code true} se il giocatore è attivo, {@code false} se è stato bannato.
     */
    public boolean getIsAttivo() {
        return isAttivo;
    }

    /**
     * Inverte lo stato del giocatore nel sistema in seguito ad un ban o ad una riattivazione.
     */
    public void cambiaIsAttivo() {
        if(isAttivo) isAttivo = false;
        else isAttivo = true;
    }

    /**
     * Restituisce la {@link Lobby} in cui il giocatore è attualmente presente come partecipante o come host.
     *
     * @return La lobby corrente, oppure null se il giocatore non si trova in nessuna lobby.
     */
    public Lobby getLobbyAttuale() {
        return lobbyAttuale;
    }

    /**
     * Restituisce il numero di vittorie ottenute dal giocatore.
     *
     * @return Il numero delle partite vinte.
     */
    public int getPartiteVinte() {
        return partiteVinte;
    }

    /**
     * Restituisce il numero di sconfitte subite dal giocatore.
     *
     * @return Il numero delle partite perse.
     */
    public int getPartitePerse() {
        return partitePerse;
    }

    /**
     * Incrementa il contatore delle partite vinte (Riguarda solo {@link PartitaPubblica}.
     */
    public void aggiungiPartitaVinta() {
        partiteVinte++;
    }

    /**
     * Incrementa il contatore delle partite perse (Riguarda solo {@link PartitaPubblica}.
     */
    public void aggiungiPartitaPersa() {
        partitePerse++;
    }

    /**
     * Verifica se il numero di partite vinte sono abbastanza per far avanzare il giocatore di livello.
     * In caso positivo, incrementa il livello di gioco e riassegna il {@link GradoGiocatore}.
     */
    public void verificaSalitaDiLivello (){
        if((partiteVinte / livello) >= 10 )
        {
            livello++;
            this.assegnaGrado(livello);
        }
    }

    /**
     * Restituisce il codice identificativo univoco del giocatore.
     *
     * @return L'ID del giocatore.
     */
    public String getIdGiocatore() {
        return idGiocatore;
    }

    /**
     * Restituisce il livello attuale del giocatore.
     *
     * @return Il livello del giocatore.
     */
    public int getLivello() {
        return livello;
    }

    /**
     * Genera una rappresentazione testuale formattata del profilo del giocatore.
     * I dati sono concatenati e separati dal carattere ";" secondo la sequenza:
     * nomeUtente, livello, grado, partiteVinte, partitePerse, numeroSegnalazioniRicevute.
     *
     * @return La stringa contenente i dati essenziali del giocatore.
     */
    @Override
    public String toString(){
        return this.getNomeUtente()+";"+this.getLivello()+";"+this.getGrado()+";"+this.getPartiteVinte()+";"+this.getPartitePerse()+";"+this.getSegnalazioniRicevute().size();
    }

    /**
     * Restituisce il grado del giocatore.
     *
     * @return Il {@link GradoGiocatore} del giocatore.
     */
    public GradoGiocatore getGrado() {
        return grado;
    }

    /**
     * Restituisce lo stato online/offline del giocatore.
     *
     * @return {@code true} se il giocatore è online, {@code false} altrimenti.
     */
    public boolean getStatoGiocatore(){
        return statoGiocatore;
    }

    /**
     * Restituisce il riferimento alla {@link Lobby} creata dal giocatore.
     *
     * @return La lobby di cui il giocatore è l'host, oppure null se tale lobby non esiste.
     */
    public Lobby getLobbyCreata() {
        return lobbyCreata;
    }
}