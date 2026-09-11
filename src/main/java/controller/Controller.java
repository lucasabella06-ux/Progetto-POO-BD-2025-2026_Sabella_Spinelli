package controller;

import dao.*;
import implementazioneTextDAO.*;
import model.*;

import java.util.ArrayList;

import java.util.Date;
import java.util.Random;

import exception.*;

/**
 * La classe Controller rappresenta il coordinatore fondamentale del sistema, gestendo
 * il flusso dei dati tra le classi del {@link model}, della {@link gui} e della {@link dao}.
 * In particolare, carica in memoria le liste delle entità presenti nel sistema, gestisce
 * le operazioni eseguibili dai {@link Giocatore} o dai {@link Moderatore} loggati, e come esse
 * influiscono sulle altre entità del sistema.
 *
 */
public class Controller {
    /**
     * Elenco dei {@link Giocatore} presenti nel sistema.
     */
    ArrayList<Giocatore> listaGiocatori;
    /**
     * Elenco dei {@link Moderatore} presenti nel sistema.
     */
    ArrayList<Moderatore> listaModeratori;
    /**
     * Elenco delle {@link PartitaPubblica} presenti nel sistema.
     */
    ArrayList<PartitaPubblica> listaPartitePubbliche;
    /**
     * Elenco delle {@link Lobby} presenti nel sistema.
     */
    ArrayList<Lobby> listaLobby;
    /**
     * Elenco delle {@link PartitaAmichevole} presenti nel sistema.
     */
    ArrayList<PartitaAmichevole> listaPartiteAmichevoli;
    /**
     * Elenco dei {@link Giocatore} banditi dal sistema.
     */
    ArrayList<Giocatore> listaGiocatoriBanditi;
    /**
     * La {@link Classifica} raffigurante i migliori giocatori del sistema.
     */
    Classifica classifica;
    /**
     * Il {@link Giocatore} attualmente loggato nel sistema.
     */
    Giocatore giocatoreLoggato;
    /**
     * Il {@link Moderatore} attualmente loggato nel sistema.
     */
    Moderatore moderatoreLoggato;
    /**
     * Generatore casuale utilizzato per completare alcuni aspetti della logica del programma.
     */
    Random rand = new Random();

    /**
     * Istanzia un nuovo oggetto Controller, allocando le relative liste necessarie per
     * contenere i dati del sistema.
     */
    public Controller() {
        this.listaGiocatori = new ArrayList<Giocatore>();
        this.listaModeratori = new ArrayList<Moderatore>();
        this.listaPartitePubbliche = new ArrayList<PartitaPubblica>();
        this.listaLobby = new ArrayList<Lobby>();
        this.listaPartiteAmichevoli = new ArrayList<PartitaAmichevole>();
        this.listaGiocatoriBanditi = new ArrayList<Giocatore>();
    }

    /**
     * Recupera l'elenco completo dei {@link Giocatore} presenti nel sistema, interrogando
     * l'interfaccia {@link GiocatoriDAO} nella sua implementazione {@link GiocatoriDAOImplText},
     * fornendo il file di testo dove reperire i dati ricercati.
     * In base al valore di isAttivo nella lista di riferimento, il giocatore istanziato
     * viene inserito o nella lista dei giocatori {@code true} o in quella dei giocatori banditi
     * {@code false}
     */
    public void prendiGiocatori(){
        ArrayList<String> listaNomi = new ArrayList<String>();
        ArrayList<String> listaEmail = new ArrayList<String>();
        ArrayList<String> listaPassword = new ArrayList<String>();
        ArrayList<String> listaIdGiocatori = new ArrayList<String>();
        ArrayList<Integer> listaLivelli = new ArrayList<Integer>();
        ArrayList<Integer> listaPartiteVinte = new ArrayList<Integer>();
        ArrayList<Integer> listaPartitePerse = new ArrayList<Integer>();
        ArrayList<String> listaIsAttivo = new ArrayList<String>();
        GiocatoriDAO giocatoriDAO = new GiocatoriDAOImplText("dati_txt/Giocatori.txt");
        giocatoriDAO.generaGiocatori(listaNomi, listaEmail, listaPassword, listaIdGiocatori, listaLivelli, listaPartiteVinte, listaPartitePerse, listaIsAttivo);
        for(int i = 0; i < listaNomi.size(); i++)
        {
            if(listaIsAttivo.get(i).equals("true")) {
                listaGiocatori.add(new Giocatore(listaNomi.get(i), listaEmail.get(i), listaPassword.get(i), listaIdGiocatori.get(i),
                        listaLivelli.get(i), listaPartiteVinte.get(i), listaPartitePerse.get(i)));
            }
            else{
                listaGiocatoriBanditi.add(new Giocatore(listaNomi.get(i), listaEmail.get(i), listaPassword.get(i), listaIdGiocatori.get(i),
                        listaLivelli.get(i), listaPartiteVinte.get(i), listaPartitePerse.get(i)));
            }
        }
    }

    /**
     * Recupera l'elenco completo dei {@link Moderatore} presenti nel sistema, interrogando
     * l'interfaccia {@link ModeratoriDAO} nella sua implementazione {@link ModeratoriDAOImplText},
     * fornendo il file di testo dove reperire i dati ricercati. Tali dati vengono utilizzati
     * per istanziare nuovi oggetti {@link Moderatore}.
     */
    public void prendiModeratori(){
        ArrayList<String> listaNomi = new ArrayList<String>();
        ArrayList<String> listaEmail = new ArrayList<String>();
        ArrayList<String> listaPassword = new ArrayList<String>();
        ArrayList<String> listaCodiciModeratori = new ArrayList<String>();
        ArrayList<Integer> listaOrariInizioAttivita = new ArrayList<Integer>();
        ArrayList<Integer> listaOrariFineAttivita = new ArrayList<Integer>();
        ModeratoriDAO moderatoriDAO = new ModeratoriDAOImplText("dati_txt/Moderatori.txt");
        moderatoriDAO.generaModeratori(listaNomi, listaEmail, listaPassword, listaCodiciModeratori, listaOrariInizioAttivita, listaOrariFineAttivita);
        for(int i = 0; i < listaNomi.size(); i++)
        {
            listaModeratori.add(new Moderatore(listaNomi.get(i), listaEmail.get(i), listaPassword.get(i), listaCodiciModeratori.get(i),
                        listaOrariInizioAttivita.get(i), listaOrariFineAttivita.get(i)));
        }
    }

    /**
     * Recupera l'elenco completo delle {@link PartitaPubblica} presenti nel sistema, interrogando
     * l'interfaccia {@link PartitePubblicheDAO} nella sua implementazione {@link PartitePubblicheDAOImplText},
     * fornendo il file di testo dove reperire i dati ricercati. Tali dati vengono utilizzati
     * per istanziare nuovi oggetti {@link PartitaPubblica}, ricercando il moderatore necessario
     * per il costruttore nella lista dei moderatori.
     */
    public void prendiPartitePubbliche(){
        ArrayList<String> listaCodiciPartitePubbliche = new ArrayList<String>();
        ArrayList<String> listaVideogiochi = new ArrayList<String>();
        ArrayList<Integer> listaCapienze = new ArrayList<Integer>();
        ArrayList<String> listaModeratoriPartite = new ArrayList<String>();
        PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/PartitePubbliche.txt");
        partitePubblicheDAO.generaPartitePubbliche(listaCodiciPartitePubbliche, listaVideogiochi, listaCapienze, listaModeratoriPartite);
        for(int i = 0; i < listaCodiciPartitePubbliche.size(); i++)
        {
            Moderatore moderatore = null;
            for(Moderatore m: listaModeratori){
                if(m.getNomeUtente().equals(listaModeratoriPartite.get(i))){
                    moderatore = m;
                    break;
                }
                }
            listaPartitePubbliche.add(new PartitaPubblica(listaCodiciPartitePubbliche.get(i), listaVideogiochi.get(i), listaCapienze.get(i), moderatore));
        }
    }

    /**
     * Recupera l'elenco completo dei {@link Giocatore} partecipanti alle {@link PartitaPubblica} presenti nel
     * sistema, interrogando l'interfaccia {@link PartitePubblicheDAO} nella sua implementazione
     * {@link PartitePubblicheDAOImplText}, fornendo il file di testo dove reperire i dati ricercati. Tali
     * dati vengono utilizzati per far entrare ogni giocatore nella rispettiva partita pubblica.
     */
    public void prendiPartecipantiAllePartitePubbliche(){
        ArrayList<String> listaNomi = new ArrayList<String>();
        ArrayList<String> listaCodiciPartitePubbliche = new ArrayList<String>();
        PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/PartecipazioniGiocatoriPartitePubbliche.txt");
        partitePubblicheDAO.generaPartecipantiPartitePubbliche(listaNomi, listaCodiciPartitePubbliche);
        for(int i = 0; i < listaNomi.size(); i++)
        {
            Giocatore partecipante = null;
            PartitaPubblica partita = null;
            for(Giocatore g: listaGiocatori){
                if(g.getNomeUtente().equals(listaNomi.get(i))){
                    partecipante = g;
                    break;
                }
            }
            for(PartitaPubblica p: listaPartitePubbliche){
                if(p.getCodicePartita().equals(listaCodiciPartitePubbliche.get(i))){
                    partita = p;
                    break;
                }
            }
            partecipante.entraInPartita(partita);
        }
    }

    /**
     * Recupera l'elenco completo dei {@link Moderatore} gestori delle {@link PartitaPubblica} presenti nel
     * sistema, oltre a quelli utilizzati per i costruttori delle partite pubbliche, interrogando l'interfaccia
     * {@link PartitePubblicheDAO} nella sua implementazione {@link PartitePubblicheDAOImplText}, fornendo il
     * file di testo dove reperire i dati ricercati. Tali dati vengono utilizzati per far gestire ad ogni
     * moderatore le rispettive partite pubbliche.
     */
    public void prendiModeratoriExtraPartitePubbliche(){
        ArrayList<String> listaNomi = new ArrayList<String>();
        ArrayList<String> listaCodiciPartitePubbliche = new ArrayList<String>();
        PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/GestioniExtraModeratoriPartite.txt");
        partitePubblicheDAO.generaGestioniExtraPartitePubbliche(listaNomi, listaCodiciPartitePubbliche);
        for(int i = 0; i < listaNomi.size(); i++)
        {
            Moderatore moderatore = null;
            PartitaPubblica partita = null;
            for(Moderatore m: listaModeratori){
                if(m.getNomeUtente().equals(listaNomi.get(i))){
                    moderatore = m;
                    break;
                }
            }
            for(PartitaPubblica p: listaPartitePubbliche){
                if(p.getCodicePartita().equals(listaCodiciPartitePubbliche.get(i))){
                    partita = p;
                    break;
                }
            }
            moderatore.aggiungiPartitaGestita(partita);
            partita.getModeratori().add(moderatore);
        }
    }

    /**
     * Recupera l'elenco completo delle {@link Lobby} presenti nel sistema, interrogando
     * l'interfaccia {@link LobbyDAO} nella sua implementazione {@link LobbyDAOImplText},
     * fornendo il file di testo dove reperire i dati ricercati. Tali dati vengono utilizzati
     * per istanziare nuovi oggetti {@link Lobby}, ricercando il {@link Giocatore} necessario
     * come host per il costruttore nella lista dei giocatori.
     */
    public void prendiLobby(){
        ArrayList<String> listaCodiciLobby = new ArrayList<String>();
        ArrayList<Integer> listaCapienzeMax= new ArrayList<Integer>();
        ArrayList<String> listaHost = new ArrayList<String>();
        LobbyDAO lobbyDAO = new LobbyDAOImplText("dati_txt/Lobby.txt");
        lobbyDAO.generaLobby(listaCodiciLobby, listaCapienzeMax, listaHost);
        for(int i = 0; i < listaCodiciLobby.size(); i++)
        {
            Giocatore host = null;
            for(Giocatore g: listaGiocatori){
                if(g.getNomeUtente().equals(listaHost.get(i))){
                    host = g;
                    break;
                }
            }
            host.creaLobby(listaCodiciLobby.get(i), listaCapienzeMax.get(i));
            listaLobby.add(host.getLobbyAttuale());
        }
    }

    /**
     * Recupera l'elenco completo dei {@link Giocatore} partecipanti alle {@link Lobby} presenti nel
     * sistema, interrogando l'interfaccia {@link LobbyDAO} nella sua implementazione
     * {@link LobbyDAOImplText}, fornendo il file di testo dove reperire i dati ricercati. Tali
     * dati vengono utilizzati per far entrare ogni giocatore nella rispettiva lobby.
     */
    public void prendiPartecipantiAlleLobby(){
        ArrayList<String> listaNomi = new ArrayList<String>();
        ArrayList<String> listaCodiciLobby = new ArrayList<String>();
        LobbyDAO lobbyDAO = new LobbyDAOImplText("dati_txt/PartecipazioniGiocatoriLobby.txt");
        lobbyDAO.generaPartecipantiLobby(listaNomi, listaCodiciLobby);
        for(int i = 0; i < listaNomi.size(); i++)
        {
            Giocatore partecipante = null;
            Lobby lobby = null;
            for(Giocatore g: listaGiocatori){
                if(g.getNomeUtente().equals(listaNomi.get(i))){
                    partecipante = g;
                    break;
                }
            }
            for(Lobby l: listaLobby){
                if(l.getCodiceLobby().equals(listaCodiciLobby.get(i))){
                    lobby = l;
                    break;
                }
            }
            partecipante.entraInLobby(lobby);
        }
    }

    /**
     * Recupera l'elenco completo delle {@link PartitaAmichevole} presenti nel
     * sistema, interrogando l'interfaccia {@link PartiteAmichevoliDAO} nella sua implementazione
     * {@link PartiteAmichevoliDAOImplText}, fornendo il file di testo dove reperire i dati ricercati. Tali
     * dati vengono utilizzati per far creare ad ogni lobby di cui viene fornito il codice
     * la rispettiva partita amichevole.
     */
    public void prendiPartiteAmichevoli(){
        ArrayList<String> listaCodiciLobby = new ArrayList<String>();
        ArrayList<String> listaCodiciPartiteAmichevoli = new ArrayList<String>();
        ArrayList<String> listaVideogiochi = new ArrayList<String>();
        ArrayList<Integer> listaCapienze = new ArrayList<Integer>();
        PartiteAmichevoliDAO partiteAmichevoliDAO = new PartiteAmichevoliDAOImplText("dati_txt/PartiteAmichevoli.txt");
        partiteAmichevoliDAO.generaPartiteAmichevoli(listaCodiciLobby, listaCodiciPartiteAmichevoli, listaVideogiochi, listaCapienze);
        for(int i = 0; i < listaCodiciLobby.size(); i++)
        {
            Lobby lobby = null;
            for(Lobby l: listaLobby){
                if(l.getCodiceLobby().equals(listaCodiciLobby.get(i))){
                    lobby = l;
                    break;
                }
            }
            listaPartiteAmichevoli.add(lobby.creaPartitaAmichevole(listaCodiciPartiteAmichevoli.get(i), listaVideogiochi.get(i), listaCapienze.get(i)));
        }
    }

    /**
     * Istanzia e riempie la {@link Classifica} di tutti i giocatori presenti nel sistema,
     * chiamandone poi il metodo responsabile di ordinare i giocatori e tagliare la grandezza
     * della classifica al numero di posizioni prefissato.
     */
    public void generaClassifica(){
        classifica = new Classifica(listaGiocatori.get(0));
        for(Giocatore g: listaGiocatori){
            classifica.aggiungiGiocatore(g);
        }
        classifica.ordinaGiocatori();
    }

    /**
     * Recupera l'elenco completo delle {@link Segnalazione} presenti nel
     * sistema, interrogando l'interfaccia {@link SegnalazioniDAO} nella sua implementazione
     * {@link SegnalazioniDAOImplText}, fornendo il file di testo dove reperire i dati ricercati. Tali
     * dati vengono utilizzati per recuperare i {@link Giocatore} coinvolti nella segnalazione
     * e istanziare tale segnalazione chiamando il metodo responsabile di tale operazione
     * presente in {@link Giocatore}.
     */
    public void prendiSegnalazioni() {
        ArrayList<String> listaAutoriSegnalazioni = new ArrayList<String>();
        ArrayList<String> listaGiocatoriSegnalati = new ArrayList<String>();
        ArrayList<Date> listaDate = new ArrayList<Date>();
        ArrayList<String> listaMotivi = new ArrayList<String>();
        SegnalazioniDAO segnalazioniDAO = new SegnalazioniDAOImplText("dati_txt/Segnalazioni.txt");
        segnalazioniDAO.generaSegnalazioni(listaAutoriSegnalazioni, listaGiocatoriSegnalati, listaDate, listaMotivi);
        for (int i = 0; i < listaAutoriSegnalazioni.size(); i++) {
            Giocatore autoreSegnalazione = null;
            Giocatore giocatoreSegnalato = null;
            for (Giocatore g : listaGiocatori) {
                if (g.getNomeUtente().equals(listaAutoriSegnalazioni.get(i))) {
                    autoreSegnalazione = g;
                    break;
                }
            }
            for (Giocatore g : listaGiocatori) {
                if (g.getNomeUtente().equals(listaGiocatoriSegnalati.get(i))) {
                    giocatoreSegnalato = g;
                    break;
                }
            }
            autoreSegnalazione.effettuaSegnalazione(giocatoreSegnalato, listaDate.get(i), Segnalazione.Motivo.valueOf(listaMotivi.get(i).trim().replace(" ","_").toUpperCase()));
        }
    }

    /**
     * Verifica la riuscita del login di un {@link Giocatore} nel sistema secondo le credenziali fornite.
     * Il metodo verifica che i parametri forniti non siano vuoti, poi li confronta con i dati
     * prima dei giocatori e poi dei giocatori banditi presenti nel sistema. Se il login riesce il
     * profilo del giocatore che ha fatto il login viene settato allo stato online {@code true}
     * e assegnato all'attributo giocatoreLoggato.
     *
     * @param email    L'indirizzo email inserito nel form di login.
     * @param password La password inserita nel form di login.
     * @return {@code true} se il login va a buon fine, {@code false} se non viene trovato
     * nessun account (un valore viene restituito solo per la ricerca sui giocatori non banditi).
     * @throws EmailVuotaException       Se il parametro email passato risulta vuoto
     * @throws PasswordVuotaException    Se il parametro password passato risulta vuoto
     * @throws GiocatoreBannatoException Se le credenziali fornite corrispondono a quelle di
     *                                   un {@link Giocatore} bannato dal sistema.
     */
    public boolean verificaLoginGiocatore(String email, String password) throws EmailVuotaException, PasswordVuotaException, GiocatoreBannatoException
    {
        if(email.isBlank()){
            throw new EmailVuotaException("Nessuna email inserita");
        }
        if(password.isBlank())
        {
            throw new PasswordVuotaException("Nessuna password inserita");
        }
        else{
            for(Giocatore g: listaGiocatori){
                if (g.login(email, password)){
                    giocatoreLoggato = g;
                    giocatoreLoggato.setStatoGiocatore(true);
                    return true;
                }
            }
            for(Giocatore g: listaGiocatoriBanditi){
                if (g.login(email,password))
                {
                    throw new GiocatoreBannatoException("Il tuo account è stato bannato dai nostri moderatori.");
                }
            }
            return false;
        }
    }

    /**
     * Verifica la riuscita del login di un {@link Moderatore} nel sistema secondo le credenziali fornite.
     * Il metodo verifica che i parametri forniti non siano vuoti, poi li confronta con i dati
     * prima dei moderatori presenti nel sistema. Se il login riesce il profilo del moderatore che ha
     * fatto il login viene assegnato all'attributo moderatoreLoggato.
     *
     * @param email    L'indirizzo email inserito nel form di login.
     * @param password La password inserita nel form di login.
     * @param codiceModeratore Il codice d'accesso del moderatore inserito nel form di login.
     * @return {@code true} se il login va a buon fine, {@code false} se non viene trovato
     * nessun account con quelle credenziali.
     * @throws EmailVuotaException       Se il parametro email passato risulta vuoto
     * @throws PasswordVuotaException    Se il parametro password passato risulta vuoto
     * @throws CodiceModeratoreVuotoException Se il parametro codiceModeratore passato risulta vuoto
     */
    public boolean verificaLoginModeratore(String email, String password, String codiceModeratore)throws EmailVuotaException, PasswordVuotaException, CodiceModeratoreVuotoException
    {
        if(email.isBlank()){
            throw new EmailVuotaException("Nessuna email inserita.");
        }
        if(password.isBlank())
        {
            throw new PasswordVuotaException("Nessuna password inserita.");
        }
        if(codiceModeratore.isBlank())
        {
            throw new CodiceModeratoreVuotoException("Nessun codice moderatore inserito.");
        }
        else{
            for(Moderatore m: listaModeratori){
                if (m.login(email, password) && m.getCodiceAccessoModeratore().equals(codiceModeratore)){
                    moderatoreLoggato = m;
                    return true;
                }
            }
            return false;
        }
    }


    /**
     * Verifica la presenza dell'indirizzo email passato come stringa all'interno del sistema,
     * sia tra i giocatori (banditi e non) che tra i moderatori.
     * Viene utilizzato in fase di creazione di un nuovo {@link Giocatore} o {@link Moderatore},
     * per verificare che non ci siano account legati alla stessa mail
     *
     * @param email l'email passata nel form di creazione di un nuovo account
     * {@link Giocatore} o {@link Moderatore}.
     * @return {@code true} se viene trovato un account con la stessa email, {@code false} altrimenti.
     */
    public boolean cercaEmail(String email)
    {
        for(Giocatore g: listaGiocatori)
        {
            if(g.getEmailUtente().equalsIgnoreCase(email)){
                return true;
            }
        }

        for(Giocatore g: listaGiocatoriBanditi)
        {
            if(g.getEmailUtente().equalsIgnoreCase(email)){
                return true;
            }
        }

        for(Moderatore m: listaModeratori)
            if(m.getEmailUtente().equalsIgnoreCase(email))
            {
                return true;
            }
        return false;
    }

    /**
     * Verifica la presenza del nome utente passato come stringa all'interno del sistema,
     * sia tra i giocatori (banditi e non) che tra i moderatori.
     * Viene utilizzato in fase di creazione di un nuovo {@link Giocatore} o {@link Moderatore},
     * per verificare che non ci siano account con lo stesso nome utente.
     *
     * @param nomeUtente Il nome utente inserito nel form di creazione di un nuovo account
     *                   {@link Giocatore} o {@link Moderatore}.
     * @return {@code true} se viene trovato un account con lo stesso nome utente, {@code false} altrimenti.
     */
    public boolean cercaNomeUtente(String nomeUtente)
    {
        for(Giocatore g: listaGiocatori)
        {
            if(g.getNomeUtente().equalsIgnoreCase(nomeUtente)){
                return true;
            }
        }

        for(Giocatore g: listaGiocatoriBanditi)
        {
            if(g.getNomeUtente().equalsIgnoreCase(nomeUtente)){
                return true;
            }
        }

        for(Moderatore m: listaModeratori)
            if(m.getNomeUtente().equalsIgnoreCase(nomeUtente))
            {
                return true;
            }
        return false;
    }

    /**
     * Aggiunge un nuovo {@link Giocatore} al sistema verificando la validità dei valori inseriti, se
     * tutti i controlli danno esito positivo, il nuovo giocatore viene istanziato e l'implementazione
     * {@link GiocatoriDAOImplText} dell'interfaccia {@link GiocatoriDAO} si occupa di salvarne i dati
     * su file di testo. Infine il nuovo giocatore istanziato viene assegnato all'attributo giocatoreLoggato.
     *
     * @param email      L'email inserita nel form di creazione nuovo {@link Giocatore}.
     * @param password   La password inserita nel form di creazione nuovo {@link Giocatore}.
     * @param nomeUtente Il nome utente inserito nel form di creazione nuovo {@link Giocatore}.
     * @throws EmailVuotaException             Se il parametro email passato è vuoto.
     * @throws EmailNonValidaException         Se il parametro email non contiene il carattere basilare "@"
     *                                         per considerare valida l'email inserita.
     * @throws EmailGiaEsistenteException      Se esiste un altro account collegato all'indirizzo email inserito.
     * @throws PasswordVuotaException          Se il parametro password passato è vuoto.
     * @throws NomeUtenteVuotoException        Se il parametro nome utente passato è vuoto.
     * @throws PasswordNonValidaException      Se la password passata non contiene il numero minimo di
     *                                         caratteri richiesti.
     * @throws NomeUtenteGiaEsistenteException Se esiste un altro account con lo stesso nome utente di quello
     * inserito.
     */
    public void creaNuovoGiocatore(String email, String password, String nomeUtente) throws EmailVuotaException, EmailNonValidaException, EmailGiaEsistenteException, PasswordVuotaException, NomeUtenteVuotoException, PasswordNonValidaException, NomeUtenteGiaEsistenteException {
        if(email.isBlank()){
            throw new EmailVuotaException("Nessuna email inserita.");
        }
        if(!email.contains("@")){
            throw new EmailNonValidaException("L'email inserita non è valida, un'email deve contenere almeno il simbolo @.");
        }
        if(cercaEmail(email)){
            throw new EmailGiaEsistenteException("Esiste già un account con questa email.");
        }
        if(password.isBlank())
        {
            throw new PasswordVuotaException("Nessuna password inserita.");
        }
        if(password.length() < 8){
            throw new PasswordNonValidaException("Password troppo corta. Una password deve contenere almeno 8 caratteri.");
        }
        if(nomeUtente.isBlank())
        {
            throw new NomeUtenteVuotoException("Nessun nome utente inserito.");
        }
        if(cercaNomeUtente(nomeUtente)){
            throw new NomeUtenteGiaEsistenteException("Esiste già un account con questo nome utente.");
        }

        int totaleGiocatori = listaGiocatori.size()+listaGiocatoriBanditi.size();
        String idGiocatore = "G00"+(totaleGiocatori+1);
        GiocatoriDAO giocatoriDAO = new GiocatoriDAOImplText("dati_txt/Giocatori.txt");
        giocatoriDAO.aggiungiGiocatore(nomeUtente, email, password, idGiocatore );
        Giocatore nuovoGiocatore = new Giocatore(nomeUtente, email, password, idGiocatore);
        listaGiocatori.add(nuovoGiocatore);
        giocatoreLoggato = nuovoGiocatore;
    }

    /**
     * Aggiunge un nuovo {@link Moderatore} al sistema verificando la validità dei valori inseriti, se
     * tutti i controlli danno esito positivo, il nuovo moderatore viene istanziato e l'implementazione
     * {@link ModeratoriDAOImplText} dell'interfaccia {@link ModeratoriDAO} si occupa di salvarne i dati
     * su file di testo. Infine il nuovo moderatore istanziato viene assegnato all'attributo moderatoreLoggato.
     *
     * @param email      L'email inserita nel form di creazione nuovo {@link Moderatore}.
     * @param password   La password inserita nel form di creazione nuovo {@link Moderatore}.
     * @param nomeUtente Il nome utente inserito nel form di creazione nuovo {@link Moderatore}.
     * @param orarioInizioAttivita L'orario di inizio attività inserito nel form di creazione
     *                             nuovo {@link Moderatore}.
     * @param orarioFineAttivita   L'orario di fine attività inserito nel form di creazione
     *                             nuovo {@link Moderatore}.
     * @throws EmailVuotaException             Se il parametro email passato è vuoto.
     * @throws EmailNonValidaException         Se il parametro email non contiene il carattere basilare "@"
     *                                         per considerare valida l'email inserita.
     * @throws EmailGiaEsistenteException      Se esiste un altro account collegato all'indirizzo email inserito.
     * @throws PasswordVuotaException          Se il parametro password passato è vuoto.
     * @throws NomeUtenteVuotoException        Se il parametro nome utente passato è vuoto.
     * @throws PasswordNonValidaException      Se la password passata non contiene il numero minimo di
     *                                         caratteri richiesti.
     * @throws NomeUtenteGiaEsistenteException Se esiste un altro account con lo stesso nome utente di quello
     * inserito.
     */
    public void creaNuovoModeratore(String email, String password, String nomeUtente, int orarioInizioAttivita, int orarioFineAttivita) throws EmailVuotaException, EmailNonValidaException, EmailGiaEsistenteException, PasswordVuotaException, NomeUtenteVuotoException, PasswordNonValidaException, NomeUtenteGiaEsistenteException {
        if(email.isBlank()){
            throw new EmailVuotaException("Nessuna email inserita.");
        }
        if(!email.contains("@")){
            throw new EmailNonValidaException("L'email inserita non è valida, un'email deve contenere almeno il simbolo @.");
        }
        if(cercaEmail(email)){
            throw new EmailGiaEsistenteException("Esiste già un account con questa email.");
        }
        if(password.isBlank())
        {
            throw new PasswordVuotaException("Nessuna password inserita.");
        }
        if(password.length() < 8){
            throw new PasswordNonValidaException("Password troppo corta. Una password deve contenere almeno 8 caratteri.");
        }
        if(nomeUtente.isBlank())
        {
            throw new NomeUtenteVuotoException("Nessun nome utente inserito.");
        }
        if(cercaNomeUtente(nomeUtente)){
            throw new NomeUtenteGiaEsistenteException("Esiste già un account con questo nome utente.");
        }

        int totaleModeratori = listaModeratori.size();
        String nuovoCodiceModeratore = "MM11"+(totaleModeratori+1);
        ModeratoriDAO moderatoriDAO = new ModeratoriDAOImplText("dati_txt/Moderatori.txt");
        moderatoriDAO.aggiungiModeratore(nomeUtente, email, password, nuovoCodiceModeratore, orarioInizioAttivita, orarioFineAttivita);
        Moderatore nuovoModeratore = new Moderatore(nomeUtente, email, password,nuovoCodiceModeratore, orarioInizioAttivita, orarioFineAttivita);
        listaModeratori.add(nuovoModeratore);
        moderatoreLoggato = nuovoModeratore;
    }

    /**
     * Restituisce il nome utente del {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Una stringa contenente il nome utente del giocatore presente nel parametro giocatoreLoggato.
     */
    public String getNomeUtenteGiocatoreLoggato()
    {
        return giocatoreLoggato.getNomeUtente();
    }

    /**
     * Restituisce il codice identificativo univoco del {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Una stringa contenente l'id giocatore del giocatore presente nel parametro giocatoreLoggato.
     */
    public String getIdGiocatoreLoggato()
    {
        return giocatoreLoggato.getIdGiocatore();
    }

    /**
     * Restituisce il livello del {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Il valore numerico del livello del giocatore presente nel parametro giocatoreLoggato.
     */
    public int getLivelloGiocatoreLoggato()
    {
        return giocatoreLoggato.getLivello();
    }

    /**
     * Restituisce il grado del {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Una stringa contenente il grado del giocatore presente nel parametro giocatoreLoggato.
     */
    public String getGradoGiocatoreLoggato()
    {
        return giocatoreLoggato.getGrado().toString();
    }

    /**
     * Restituisce il numero di partite vinte dal {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Il numero di partite vinte dal giocatore presente nel parametro giocatoreLoggato.
     */
    public int getPartiteVinteGiocatoreLoggato()
    {
        return giocatoreLoggato.getPartiteVinte();
    }

    /**
     * Restituisce il numero di partite perse dal {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Il numero di partite perse dal giocatore presente nel parametro giocatoreLoggato.
     */
    public int getPartitePerseGiocatoreLoggato()
    {
        return giocatoreLoggato.getPartitePerse();
    }

    /**
     * Restituisce l'elenco dei risultati delle partite pubbliche giocate dal {@link Giocatore}
     * attualmente loggato nel sistema nel corso della sessione di gioco in atto.
     *
     * @return Un ArrayList contenente le stringhe descrittive dei risultati delle partite pubbliche
     * giocate dal giocatore presente nel parametro giocatoreLoggato.
     */
    public ArrayList<String> mostraStoricoGiocatoreLoggato() {
        ArrayList<String> listaDaRestituire = new ArrayList<String>();
        for(StoricoPartita sP: giocatoreLoggato.getStorico())
        {
            listaDaRestituire.add(sP.toString());
        }
        return listaDaRestituire;
    }

    /**
     * Restituisce l'elenco dei {@link Giocatore} presenti in classifica.
     *
     * @return Un ArrayList contenente le stringhe descrittive dei giocatori presenti in classifica.
     */
    public ArrayList<String> mostraClassifica(){
        ArrayList<String> listaDaRestituire = new ArrayList<String>();
        for(Giocatore g: classifica.getGiocatoriInClassifica()){
            listaDaRestituire.add(g.toString());
        }
        return listaDaRestituire;
    }

    /**
     * Aggiorna lo stato online/offline del giocatore.
     *
     * @param b Il valore booleano a cui settare il parametro statoGiocatore di {@link Giocatore}.
     */
    public void setStatoGiocatoreLoggato(boolean b)
    {
        giocatoreLoggato.setStatoGiocatore(b);
    }

    /**
     * Restituisce l'elenco delle {@link PartitaPubblica} presenti nel sistema.
     *
     * @return Un ArrayList contenente le rappresentazioni testuali delle partite pubbliche
     * presenti nel sistema.
     */
    public ArrayList<String> mostraPartitePubbliche(){
        ArrayList<String> listaDaRestituire = new ArrayList<String>();
        for(PartitaPubblica partitaP: listaPartitePubbliche){
            listaDaRestituire.add(partitaP.toString());
        }
        return listaDaRestituire;
    }

    /**
     * Consente al {@link Giocatore} attualmente loggato nel sistema di entrare in una {@link PartitaPubblica}
     * di sua scelta.
     * La partita pubblica viene cercata tramite il codice identificativo fornito nella lista
     * delle partite pubbliche nel sistema. Se l'ingresso del giocatore ha esito positivo
     * l'implementazione {@link PartitePubblicheDAOImplText} dell'interfaccia {@link PartitePubblicheDAO}
     * si occupa di registrare su file di testo la nuova partecipazione del giocatore.
     *
     * @param codicePartitaSelezionata Il codice identificativo della partita pubblica selezionata nel form di
     *                                 scelta partite.
     * @throws GiocatoreGiaInPartitaException Se il giocatore loggato si trova già in un'altra {@link Partita}
     * del sistema.
     */
    public void giocatoreLoggatoEntraInPartita(String codicePartitaSelezionata) throws GiocatoreGiaInPartitaException {
        PartitaPubblica partitaSelezionata = null;
        for(PartitaPubblica p: listaPartitePubbliche)
        {
            if(p.getCodicePartita().equalsIgnoreCase(codicePartitaSelezionata))
            {
                partitaSelezionata = p;
                break;
            }
        }
        if(partitaSelezionata != null) {
            if(!giocatoreLoggato.entraInPartita(partitaSelezionata)){
                if(giocatoreLoggato.getPartitaAttuale() != null){
                    throw new GiocatoreGiaInPartitaException("Sei già in un'altra partita, non puoi entrare.");
                }
            }
            else{
                PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/PartecipazioniGiocatoriPartitePubbliche.txt");
                partitePubblicheDAO.aggiungiGiocatoreAllaPartitaPubblica(giocatoreLoggato.getNomeUtente(), partitaSelezionata.getCodicePartita());
            }
        }
    }

    /**
     * Autorizza il {@link Giocatore} attualmente loggato nel sistema ad entrare in una {@link Lobby}
     * inserendone il codice d'accesso. La lobby viene cercata tramite il codice d'accesso fornito
     * nella lista delle lobby presenti nel sistema, non prima che venga verificata la validità di
     * tale codice. Se l'ingresso del giocatore ha esito positivo l'implementazione {@link LobbyDAOImplText}
     * dell'interfaccia {@link LobbyDAO} si occupa di registrare su file di testo la nuova partecipazione
     * del giocatore.
     *
     * @param codiceLobby Il codice d'accesso inserito nella finestra modale di ingresso nelle lobby.
     * @throws GiocatoreOfflineException              Se lo stato del giocatore loggato risulta offline.
     * @throws AccessoLobbyNonRiuscitoException       Se non viene trovata nessuna lobby con quel codice d'accesso.
     * @throws GiocatoreGiaInLobbyException           Se il giocatore loggato si trova già in un altra lobby.
     * @throws CodiceLobbyVuotoException              Se il codice passato come parametro risulta vuoto.
     * @throws CapienzaMassimaLobbyRaggiuntaException Se la {@link Lobby} viene trovata ma il numero di giocatori
     * al suo interno ha già pareggiato la capienza massima.
     */
    public void giocatoreLoggatoEntraInLobby(String codiceLobby) throws GiocatoreOfflineException, AccessoLobbyNonRiuscitoException, GiocatoreGiaInLobbyException, CodiceLobbyVuotoException, CapienzaMassimaLobbyRaggiuntaException {
        if(!giocatoreLoggato.getStatoGiocatore())
        {
            throw new GiocatoreOfflineException("Sei offline, non puoi entrare in lobby.");
        }
        Lobby lobbySelezionata = null;
        for(Lobby l: listaLobby)
        {
            if(codiceLobby.isBlank()){
                throw new CodiceLobbyVuotoException("Nessun codice inserito.");
            }
            if(l.getCodiceLobby().equalsIgnoreCase(codiceLobby))
            {
                lobbySelezionata = l;
                break;
            }


        }
        if(lobbySelezionata != null)
        {
            if(lobbySelezionata.getPartecipantiLobby().size() == lobbySelezionata.getCapienzaGiocatoriMax()){
                throw new CapienzaMassimaLobbyRaggiuntaException("La lobby ha raggiunto il massimo di giocatori consentiti, non puoi entrare.");
            }
            else if(!giocatoreLoggato.entraInLobby(lobbySelezionata))
            {
                if(giocatoreLoggato.getLobbyAttuale() != null || giocatoreLoggato.getLobbyCreata() != null){
                    throw new GiocatoreGiaInLobbyException("Sei già in un'altra lobby, non puoi entrare.");
                }
            }
            else{
                LobbyDAO lobbyDAO = new LobbyDAOImplText("dati_txt/PartecipazioniGiocatoriLobby.txt");
                lobbyDAO.aggiungiGiocatoreAllaLobby(giocatoreLoggato.getNomeUtente(), lobbySelezionata.getCodiceLobby());
            }
        }
        else{
            throw new AccessoLobbyNonRiuscitoException("Non esiste nessuna lobby con questo codice");
        }
    }

    /**
     * Permette al {@link Giocatore} attualmente loggato di creare una nuova {@link Lobby}
     * di sua proprietà. Se tutte le verifiche sul codice della lobby scelto e sullo stato
     * del giocatore danno esito positivo, il giocatore loggato crea la lobby e l'interfaccia
     * {@link LobbyDAO} nella sua implementazione {@link LobbyDAOImplText} si occupa di
     * registrarla permanentemente nel sistema.
     *
     * @param codiceLobby Il codice d'accesso alla lobby inserito dal giocatore nel form di creazione lobby.
     * @param capienzaMax La capienza massima della lobby scelta dal giocatore nel form di creazione lobby.
     * @throws GiocatoreOfflineException       Se lo stato del giocatore loggato risulta offline.
     * @throws CodiceLobbyGiaPresenteException Se esiste già un altra lobby con lo stesso codice d'accesso.
     * @throws GiocatoreGiaInLobbyException    Se il giocatore si trova già in un'altra lobby.
     * @throws CodiceLobbyVuotoException       Se il codice lobby inserito dal giocatore risulta vuoto.
     */
    public void giocatoreLoggatoCreaLobby(String codiceLobby, int capienzaMax) throws GiocatoreOfflineException, CodiceLobbyGiaPresenteException, GiocatoreGiaInLobbyException, CodiceLobbyVuotoException
    {
        if(giocatoreLoggato.getStatoGiocatore() == false)
        {
            throw new GiocatoreOfflineException("Sei offline, non puoi creare una lobby.");
        }
        if(codiceLobby.isBlank()){
            throw new CodiceLobbyVuotoException("Nessun codice inserito.");
        }
        for(Lobby l: listaLobby)
        {
            if(l.getCodiceLobby().equalsIgnoreCase(codiceLobby))
            {
                throw new CodiceLobbyGiaPresenteException("Esiste un'altra lobby con questo codice, riprovare inserendone un altro.");
            }
        }

        if(giocatoreLoggato.getLobbyAttuale() == null){
            LobbyDAO lobbyDAO = new LobbyDAOImplText("dati_txt/Lobby.txt");
            lobbyDAO.aggiungiLobby(codiceLobby, capienzaMax, giocatoreLoggato.getNomeUtente());
            giocatoreLoggato.creaLobby(codiceLobby, capienzaMax);
            listaLobby.add(giocatoreLoggato.getLobbyAttuale());
        }
        else{
            throw new GiocatoreGiaInLobbyException("Sei già in un'altra lobby, non puoi crearne un'altra.");
        }
    }

    /**
     * Restituisce il numero di partecipanti presenti nella {@link Partita} a cui sta partecipando
     * il {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Il numero di partecipanti della partita a cui sta partecipando il giocatore presente
     * nel parametro giocatoreLoggato.
     */
    public int getNumeroPartecipantiPartitaGiocatoreLoggato(){
        return giocatoreLoggato.getPartitaAttuale().getPartecipantiPartita().size();
    }

    /**
     * Restituisce la capienza della {@link Partita} a cui sta partecipando
     * il {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Il valore numerico della capienza della partita a cui sta partecipando il giocatore presente
     * nel parametro giocatoreLoggato.
     */
    public int getCapienzaPartitaGiocatoreLoggato(){
        return giocatoreLoggato.getPartitaAttuale().getCapienza();
    }

    /**
     * Verifica se la {@link Partita} a cui sta partecipando il {@link Giocatore} loggato
     * ha raggiunto la capienza necessaria per il suo svolgimento.
     *
     * @return {@code true} se la partita ha raggiunto la capienza necessaria, {@code false} altrimenti.
     */
    public boolean verificaPartecipantiPartitaGiocatoreLoggato(){
        if(giocatoreLoggato.getPartitaAttuale().getPartecipantiPartita().size() == giocatoreLoggato.getPartitaAttuale().getCapienza()){
            return true;
        }
        else return false;
    }

    /**
     * Consente al {@link Giocatore} attualmente loggato nel sistema di uscire dalla {@link PartitaPubblica}
     * a cui sta partecipando. L'implementazione {@link PartitePubblicheDAOImplText} dell'interfaccia
     * {@link PartitePubblicheDAO} si occupa di eliminare dal file di testo la partecipazione
     * del giocatore alla partita pubblica.
     */
    public void giocatoreLoggatoEsceDallaPartita(){
        PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/PartecipazioniGiocatoriPartitePubbliche.txt");
        partitePubblicheDAO.rimuoviGiocatoreDallaPartitaPubblica(giocatoreLoggato.getNomeUtente());
        giocatoreLoggato.esciDallaPartita();
    }

    /**
     * Restituisce l'elenco dei giocatori presenti nella {@link Partita} a cui il {@link Giocatore}
     * attualmente loggato nel sistema sta partecipando.
     *
     * @return Un ArrayList contenente le rappresentazioni testuali dei giocatori presenti nella {@link Partita}
     * a cui il giocatore presente nel parametro giocatoreLoggato sta partecipando.
     */
    public ArrayList<String> mostraPartecipantiPartita(){
        ArrayList<String> listaDaRestituire = new ArrayList<>();
        for (Giocatore g: giocatoreLoggato.getPartitaAttuale().getPartecipantiPartita()){
            listaDaRestituire.add(g.toString());
        }
        return listaDaRestituire;
    }

    /**
     * Restituisce l'elenco dei giocatori presenti nella {@link PartitaPubblica} che il
     * {@link Moderatore} attualmente loggato nel sistema sta gestendo.
     *
     * @param codicePartitaSelezionata Il codice identificativo della partita pubblica che il moderatore
     *                                 presente nel parametro moderatoreLoggato sta gestendo.
     * @return Un ArrayList contenente le rappresentazioni testuali dei giocatori presenti nella {@link PartitaPubblica}
     * che il moderatore presente nel parametro moderatoreLoggato sta gestendo.
     */
    public ArrayList<String> mostraPartecipantiPartitaModeratore(String codicePartitaSelezionata){
        ArrayList<String> listaDaRestituire = new ArrayList<>();
        PartitaPubblica partitaSelezionata = null;
        for (PartitaPubblica p: listaPartitePubbliche){
            if(codicePartitaSelezionata.equalsIgnoreCase(p.getCodicePartita()))
            {
                partitaSelezionata = p;
                break;
            }
        }
        for(Giocatore g: partitaSelezionata.getPartecipantiPartita())
        {
            listaDaRestituire.add(g.toString());
        }
        return listaDaRestituire;
    }

    /**
     * Gestisce la conclusione di una {@link PartitaPubblica} presente nel sistema. Registrandone
     * il risultato (generato randomicamente) negli storici e aggiornando le statistiche dei {@link Giocatore}
     * partecipanti. In particolare, le statistiche vengono aggiornate permanentemente su file di testo
     * dall'implementazione {@link GiocatoriDAOImplText} dell'interfaccia {@link GiocatoriDAO}, mentre
     * l'interfaccia {@link PartitePubblicheDAO} nella sua implementazione {@link PartitePubblicheDAOImplText}
     * si occupa di registrare l'uscita di tutti i giocatori dalla partita che si è conclusa.
     *
     * @return Un ArrayList di stringhe contenenti le informazioni da mostrare al giocatore al termine
     * della partita pubblica nella finestra modale relativa al risultato della stessa.
     */
    public ArrayList<String> mostraRisultatoPartita() {
        if (giocatoreLoggato.getPartitaAttuale() instanceof PartitaPubblica) {
            ArrayList<String> listaDaRestituire = new ArrayList<String>();
            ArrayList<Giocatore> listaPartecipanti = new ArrayList<Giocatore>();
            for(Giocatore g: giocatoreLoggato.getPartitaAttuale().getPartecipantiPartita()){
                listaPartecipanti.add(g);
            }
            ArrayList<Integer> listaLivelli = new ArrayList<Integer>();
            ArrayList<Integer> listaPartiteVinte = new ArrayList<Integer>();
            ArrayList<Integer> listaPartitePerse = new ArrayList<Integer>();
            PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/PartecipazioniGiocatoriPartitePubbliche.txt");
            for (Giocatore g : listaPartecipanti) {
                listaLivelli.add(g.getLivello());
                listaPartiteVinte.add(g.getPartiteVinte());
                listaPartitePerse.add(g.getPartitePerse());
                partitePubblicheDAO.rimuoviGiocatoreDallaPartitaPubblica(g.getNomeUtente());
            }
            PartitaPubblica partitaConclusa = (PartitaPubblica) giocatoreLoggato.getPartitaAttuale();
            Giocatore vincitore = partitaConclusa.getPartecipantiPartita().get(rand.nextInt(giocatoreLoggato.getPartitaAttuale().getCapienza()));
            partitaConclusa.aggiungiRisultato(vincitore, String.format("%02d:%02d", rand.nextInt(1, 60), rand.nextInt(0, 60)));
            if (vincitore.getNomeUtente().equals(giocatoreLoggato.getNomeUtente())) {
                listaDaRestituire.add("Hai vinto!");
            } else {
                listaDaRestituire.add("Hai perso.");
            }
            for (int i = 0; i < listaPartecipanti.size(); i++) {
                if (listaPartecipanti.get(i).getLivello() != listaLivelli.get(i)) {
                        GiocatoriDAO giocatoriDAO = new GiocatoriDAOImplText("dati_txt/Giocatori.txt");
                        giocatoriDAO.aggiornaLivello(listaPartecipanti.get(i).getNomeUtente());
                }
                if (listaPartecipanti.get(i).getPartiteVinte() != listaPartiteVinte.get(i)) {
                        GiocatoriDAOImplText giocatoriDAO = new GiocatoriDAOImplText("dati_txt/Giocatori.txt");
                        giocatoriDAO.aggiornaPartiteVinte(listaPartecipanti.get(i).getNomeUtente());
                }
                if (listaPartecipanti.get(i).getPartitePerse() != listaPartitePerse.get(i)) {
                        GiocatoriDAOImplText giocatoriDAO = new GiocatoriDAOImplText("dati_txt/Giocatori.txt");
                        giocatoriDAO.aggiornaPartitePerse(listaPartecipanti.get(i).getNomeUtente());
                }
            }
            listaDaRestituire.add(giocatoreLoggato.getStorico().getLast().toString());
            return listaDaRestituire;
        }
            return null;
    }

    /**
     * Registra l'uscita del {@link Giocatore} attualmente loggato dal sistema, settandone
     * il parametro relativo allo stato online/offline a {@code false}.
     */
    public void giocatoreLoggatoEsceDalSistema(){
        giocatoreLoggato.setStatoGiocatore(false);
    }

    /**
     * Restituisce i dati fondamentali del {@link Giocatore} di cui viene passato il nome utente,
     * sia tra i giocatori attivi nel sistema che tra quelli banditi.
     *
     * @param nomeUtente Il nome utente del giocatore ricercato.
     * @return Una stringa contenente i dati fondamentali del giocatore trovato, null se
     * non è trovato nessun giocatore con quel nome utente.
     */
    public String mostraDatiGiocatore(String nomeUtente)
    {
        for(Giocatore g: listaGiocatori){
            if (g.getNomeUtente().equals(nomeUtente)){
                return g.toString();
            }
        }
        for(Giocatore g: listaGiocatoriBanditi){
            if (g.getNomeUtente().equals(nomeUtente)){
                return g.toString();
            }
        }
        return null;
    }

    /**
     * Verifica se il nome utente fornito corrisponde a quello del {@link Giocatore} attualmente
     * loggato nel sistema, nel caso estremamente particolare in cui il giocatore loggato
     * provi per sbaglio a segnalare se stesso nel form di visualizzazione dei partecipanti
     * ad una {@link PartitaPubblica}
     *
     * @param nomeUtente Il nome utente del giocatore selezionato nel form di visualizzazione dei
     *                   partecipanti alla partita pubblica.
     * @return {@code true} se il nome utente passato è quello del giocatore presente
     * nel parametro giocatoreLoggato, {@code false} altrimenti.
     */
    public boolean verificaGiocatoreSelezionato(String nomeUtente)
    {
        if(nomeUtente.equals(giocatoreLoggato.getNomeUtente())){
            return true;
        }
        else return false;
    }

    /**
     * Consente al {@link Giocatore} attualmente loggato nel sistema di effettuare una
     * {@link Segnalazione} contro un altro giocatore incontrato nella
     * {@link PartitaPubblica} ancora non iniziata. In particolare, la stringa fornita relativa al motivo
     * della segnalazione viene trasformata in modo tale da corrispondere a uno degli elementi
     * dell'enumerazione Motivo della classe {@link Segnalazione} e l'interfaccia
     * {@link SegnalazioniDAO} nella sua implementazione {@link SegnalazioniDAOImplText } si occupa
     * di registrare su file di testo tale segnalazione
     *
     * @param nomeGiocatoreSegnalato Il nome utente del giocatore che il giocatore loggato
     *                               ha scelto di segnalare nel form di visualizzazione dei
     *                               partecipanti alla partita pubblica.
     * @param motivoSegnalazione     Il motivo della segnalazione selezionato dal giocatore
     *                               presente nel parametro giocatoreLoggato nel form di
     *                               invio segnalazione.
     */
    public void giocatoreLoggatoInviaSegnalazione(String nomeGiocatoreSegnalato, String motivoSegnalazione)
    {
        Giocatore giocatoreSegnalato = null;
        for(Giocatore g: giocatoreLoggato.getPartitaAttuale().getPartecipantiPartita())
        {
            if(nomeGiocatoreSegnalato.equals(g.getNomeUtente())){
                giocatoreSegnalato = g;
                break;
            }
        }
        Segnalazione.Motivo motivo = Segnalazione.Motivo.valueOf(motivoSegnalazione.trim().replace(" ","_").toUpperCase());
        SegnalazioniDAO segnalazioniDAO = new SegnalazioniDAOImplText("dati_txt/Segnalazioni.txt");
        Date data = new Date();
        segnalazioniDAO.aggiungiSegnalazione(giocatoreLoggato.getNomeUtente(), nomeGiocatoreSegnalato, data, motivo.toString());
        giocatoreLoggato.effettuaSegnalazione(giocatoreSegnalato, data, motivo );
    }

    /**
     * Consente al {@link Giocatore} attualmente loggato nel sistema di effettuare una
     * {@link Segnalazione} contro un altro giocatore incontrato nella
     * {@link PartitaPubblica} appena conclusa. In particolare, la stringa fornita relativa al motivo
     * della segnalazione viene trasformata in modo tale da corrispondere a uno degli elementi
     * dell'enumerazione Motivo della classe {@link Segnalazione} e l'interfaccia
     * {@link SegnalazioniDAO} nella sua implementazione {@link SegnalazioniDAOImplText } si occupa
     * di registrare su file di testo tale segnalazione
     *
     * @param nomeGiocatoreSegnalato Il nome utente del giocatore che il giocatore loggato
     *                               ha scelto di segnalare nel form di visualizzazione dei
     *                               partecipanti alla partita pubblica di cui ha visto il
     *                               risultato.
     * @param motivoSegnalazione     Il motivo della segnalazione selezionato dal giocatore
     *                               presente nel parametro giocatoreLoggato nel form di
     *                               invio segnalazione.
     */
    public void giocatoreLoggatoInviaSegnalazioneStorico(String nomeGiocatoreSegnalato, String motivoSegnalazione)
    {
        Giocatore giocatoreSegnalato = null;
        for(Giocatore g: giocatoreLoggato.getStorico().getLast().getPartecipantiPartita())
        {
            if(nomeGiocatoreSegnalato.equals(g.getNomeUtente())){
                giocatoreSegnalato = g;
                break;
            }
        }
        Segnalazione.Motivo motivo = Segnalazione.Motivo.valueOf(motivoSegnalazione.trim().replace(" ","_").toUpperCase());
        SegnalazioniDAO segnalazioniDAO = new SegnalazioniDAOImplText("dati_txt/Segnalazioni.txt");
        Date data = new Date();
        segnalazioniDAO.aggiungiSegnalazione(giocatoreLoggato.getNomeUtente(), nomeGiocatoreSegnalato, data, motivo.toString());
        giocatoreLoggato.effettuaSegnalazione(giocatoreSegnalato, data, motivo );
    }

    /**
     * Restituisce l'elenco dei {@link Giocatore} partecipanti dell'ultima {@link PartitaPubblica}
     * disputata dal giocatore attualmente loggato in seguito alla richiesta dello stesso di visualizzare
     * tale lista nella finestra modale figurante il risultato della partita pubblica conclusa.
     *
     * @return Un ArrayList contenente le rappresentazioni testuali dei giocatori presenti nella
     * {@link PartitaPubblica} appena conclusa cui il giocatore presente nel parametro giocatoreLoggato
     * ha partecipato.
     */
    public ArrayList<String> mostraPartecipantiUltimaPartitaGiocatoreLoggato()
    {
        ArrayList <String> listaDaRestituire = new ArrayList<String>();
        for(Giocatore g: giocatoreLoggato.getStorico().getLast().getPartecipantiPartita())
        {
            listaDaRestituire.add(g.toString());
        }
        return listaDaRestituire;
    }

    /**
     * Restituisce il numero di partecipanti presenti nella {@link Lobby} a cui si è unito
     * il {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Il numero di partecipanti della lobby a cui si è unito il giocatore presente
     * nel parametro giocatoreLoggato.
     */
    public int getNumeroPartecipantiLobbyGiocatoreLoggato(){
        return giocatoreLoggato.getLobbyAttuale().getPartecipantiLobby().size();
    }

    /**
     * Restituisce la capienza massima della {@link Lobby} a cui si è unito
     * il {@link Giocatore} attualmente loggato nel sistema.
     *
     * @return Il valore numerico della capienza massima della lobby a cui si è unito
     * il giocatore presente nel parametro giocatoreLoggato.
     */
    public int getCapienzaLobbyGiocatoreLoggato(){
        return giocatoreLoggato.getLobbyAttuale().getCapienzaGiocatoriMax();
    }

    /**
     * Restituisce il nome utente del {@link Giocatore} host della {@link Lobby}
     * a cui il giocatore attualmente loggato nel sistema si è unito.
     *
     * @return Una stringa contenente il nome utente dell'host della lobby
     * a cui il giocatore presente nel parametro giocatoreLoggato si è unito.
     */
    public String getHostLobbyGiocatoreLoggato(){
        return giocatoreLoggato.getLobbyAttuale().getHost().getNomeUtente();
    }

    /**
     * Consente al {@link Giocatore} attualmente loggato nel sistema di uscire dalla {@link Lobby}
     * a cui si era unito. L'implementazione {@link LobbyDAOImplText} dell'interfaccia
     * {@link LobbyDAO} si occupa di eliminare dal file di testo la partecipazione
     * del giocatore a tale lobby.
     */
    public void giocatoreLoggatoEsceDallaLobby(){
        LobbyDAO lobbyDAO = new LobbyDAOImplText("dati_txt/PartecipazioniGiocatoriLobby.txt");
        lobbyDAO.rimuoviGiocatoreDallaLobby(giocatoreLoggato.getNomeUtente());
        giocatoreLoggato.esciDallaLobby();
    }

    /**
     * Restituisce l'elenco dei giocatori presenti nella {@link Lobby} a cui il {@link Giocatore}
     * attualmente loggato nel sistema si è unito.
     *
     * @return Un ArrayList contenente le rappresentazioni testuali dei giocatori presenti nella {@link Lobby}
     * a cui si è unito il giocatore presente nel parametro giocatoreLoggato.
     */
    public ArrayList<String> mostraPartecipantiLobby(){
        ArrayList<String> listaDaRestituire = new ArrayList<>();
        for (Giocatore g: giocatoreLoggato.getLobbyAttuale().getPartecipantiLobby()){
            listaDaRestituire.add(g.toString());
        }
        return listaDaRestituire;
    }

    /**
     * Restituisce lo stato online/offline del {@link Giocatore} attualmente loggato
     * nel sistema.
     *
     * @return {@code true} se il giocatoreLoggato è online, {@code false} se è offline.
     */
    public boolean getStatoGiocatoreLoggato(){
        return giocatoreLoggato.getStatoGiocatore();
    }

    /**
     * Autorizza il {@link Giocatore} attualmente loggato nel sistema ad eliminare la
     * {@link Lobby} che aveva in precedenza creato come host. In particolare, l'interfaccia
     * {@link LobbyDAO} nella sua implementazione {@link LobbyDAOImplText} si occupa
     * di registrare l'eliminazione di tale lobby e di eliminare tutte le partecipazioni
     * di giocatori alla stessa, che viene poi rimossa anche dalla listaLobby del sistema.
     */
    public void giocatoreLoggatoEliminaLobby(){
        LobbyDAO lobbyDAO = new LobbyDAOImplText("dati_txt/Lobby.txt");
        Lobby lobbyDaEliminare = giocatoreLoggato.getLobbyAttuale();
        lobbyDAO.rimuoviLobby(lobbyDaEliminare.getCodiceLobby());
        lobbyDAO = new LobbyDAOImplText("dati_txt/PartecipazioniGiocatoriLobby.txt");
        for(Giocatore g: giocatoreLoggato.getLobbyAttuale().getPartecipantiLobby())
        {
            lobbyDAO.rimuoviGiocatoreDallaLobby(g.getNomeUtente());
        }
        listaLobby.remove(lobbyDaEliminare);
        giocatoreLoggato.eliminaLobby();
    }

    /**
     * Permette al {@link Giocatore} attualmente loggato nel sistema di creare una {@link PartitaAmichevole}
     * all'interno della {@link Lobby} di cui è l'host. In particolare, tale partita amichevole viene
     * salvata su file di testo dall'implementazione {@link PartiteAmichevoliDAOImplText} dell'interfaccia
     * {@link PartiteAmichevoliDAO} e viene aggiunta alla lista delle partite amichevoli presenti nel
     * sistema.
     *
     * @param videogioco Il videogioco selezionato dal giocatore presente nel parametro giocatoreLoggato
     *                   nel form di gestione lobby.
     */
    public void giocatoreLoggatoAvviaPartitaAmichevole(String videogioco){
        PartiteAmichevoliDAO partiteAmichevoliDAO = new PartiteAmichevoliDAOImplText("dati_txt/PartiteAmichevoli.txt");
        partiteAmichevoliDAO.aggiungiPartitaAmichevole(giocatoreLoggato.getLobbyCreata().getCodiceLobby(), "A00"+(listaPartiteAmichevoli.size()+1),videogioco, giocatoreLoggato.getLobbyCreata().getPartecipantiLobby().size());
        PartitaAmichevole a = giocatoreLoggato.getLobbyCreata().creaPartitaAmichevole(giocatoreLoggato.getLobbyCreata().getCodiceLobby(), videogioco, giocatoreLoggato.getLobbyCreata().getPartecipantiLobby().size());
        listaPartiteAmichevoli.add(a);
    }


    /**
     * Consente al {@link Giocatore} attualmente loggato nel sistema di terminare e cancellare la
     * {@link PartitaAmichevole} che si sta giocando all'interno della {@link Lobby} di cui è l'host.
     * In particolare, tale partita amichevole viene eliminata dal file di testo dall'implementazione
     * {@link PartiteAmichevoliDAOImplText} dell'interfaccia {@link PartiteAmichevoliDAO} e viene rimossa
     * dalla lista delle partite amichevoli presenti nel sistema.
     */
    public void giocatoreLoggatoEliminaPartitaAmichevole() {
        PartiteAmichevoliDAO partiteAmichevoliDao = new PartiteAmichevoliDAOImplText("dati_txt/PartiteAmichevoli.txt");
        partiteAmichevoliDao.rimuoviPartitaAmichevole(giocatoreLoggato.getLobbyCreata().getCodiceLobby());
        listaPartiteAmichevoli.remove(giocatoreLoggato.getLobbyCreata().getPartitaCreata());
        giocatoreLoggato.getLobbyCreata().eliminaPartitaAmichevole();
    }

    /**
     * Restituisce se all'interno della {@link Lobby} a cui il {@link Giocatore} attualmente loggato
     * si è appena unito è già stata avviata una {@link PartitaAmichevole} prima dell'ingresso del giocatore.
     *
     * @return {@code true} se nella lobby a cui si è unito il giocatore presente nel parametro giocatoreLoggato
     * è già stata avviata una partita amichevole, {@code false} altrimenti.
     */
    public boolean getStatoLobbyAttuale(){
        return giocatoreLoggato.getLobbyAttuale().getPartitaCreata() == null;
    }

    /**
     * Restituisce il nome utente del {@link Moderatore} attualmente loggato nel sistema.
     *
     * @return Una stringa contenente il nome utente del moderatore presente nel parametro moderatoreLoggato.
     */
    public String getNomeUtenteModeratoreLoggato(){
        return moderatoreLoggato.getNomeUtente();
    }

    /**
     * Restituisce il codice identificativo univoco del {@link Moderatore} attualmente loggato nel sistema.
     *
     * @return Una stringa contenente il codice identificativo del moderatore presente nel parametro moderatoreLoggato.
     */
    public String getCodiceModeratoreLoggato(){
        return moderatoreLoggato.getCodiceAccessoModeratore();
    }

    /**
     * Verifica se il codice inserito dal {@link Moderatore} loggato corrisponde al codice identificativo
     * univoco che il moderatore deve usare per svolgere le sue operazioni.
     *
     * @param codiceModeratore Il codice inserito dal moderatore presente nel parametro moderatoreLoggato
     *                         all'interno delle finestre modali che lo richiedono per svolgere operazioni
     *                         speciali del moderatore.
     * @throws CodiceModeratoreVuotoException  Se il codice fornito è vuoto.
     * @throws CodiceModeratoreErratoException Se il codice fornito non corrisponde a quello del moderatore loggato.
     */
    public void verificaCodiceModeratore(String codiceModeratore) throws CodiceModeratoreVuotoException, CodiceModeratoreErratoException{
        if(codiceModeratore.isBlank())throw new CodiceModeratoreVuotoException("Non hai inserito nessun codice.");
        if(!codiceModeratore.equals(moderatoreLoggato.getCodiceAccessoModeratore())) throw new CodiceModeratoreErratoException("Il codice inserito è errato.");
    }

    /**
     * Consente al {@link Moderatore} attualmente loggato nel sistema di gestire una {@link PartitaPubblica}
     * di sua scelta. La partita pubblica viene cercata tramite il codice identificativo fornito nella lista
     * delle partite pubbliche nel sistema. Se la partita viene trovata l'implementazione
     * {@link PartitePubblicheDAOImplText} dell'interfaccia {@link PartitePubblicheDAO}
     * si occupa di registrare su file di testo la nuova gestione del moderatore di tale partita pubblica.
     * @param codicePartitaSelezionata Il codice identificativo della partita pubblica selezionata dal
     *                                 moderatore presente nel parametro moderatoreLoggato nel menu home
     *                                 del moderatore.
     */
    public void moderatoreLoggatoGestisceNuovaPartita(String codicePartitaSelezionata){
        PartitaPubblica partitaSelezionata = null;
        for(PartitaPubblica p: listaPartitePubbliche)
        {
            if(p.getCodicePartita().equalsIgnoreCase(codicePartitaSelezionata))
            {
                partitaSelezionata = p;
                break;
            }
        }
        PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/GestioniExtraModeratoriPartite.txt");
        partitePubblicheDAO.aggiungiGestioneExtraPartitaPubblica(moderatoreLoggato.getNomeUtente(), partitaSelezionata.getCodicePartita());
        moderatoreLoggato.aggiungiPartitaGestita(partitaSelezionata);
        partitaSelezionata.getModeratori().add(moderatoreLoggato);
    }

    /**
     * Verifica se il {@link Moderatore} attualmente loggato nel sistema sta già gestendo la
     * {@link PartitaPubblica} di cui è fornito il codice identificativo, così da non chiedergli
     * nuovamente di inserire il suo codice per riguardare la lista dei partecipanti di tale
     * partita pubblica.
     *
     * @param codicePartitaSelezionata Il codice identificativo della partita pubblica selezionata dal
     *                                 moderatore presente nel parametro moderatoreLoggato nel menu home
     *                                 del moderatore.
     * @return {@code true} se la partita pubblica di cui è fornito il codice è già parte delle partite pubbliche
     * gestite dal moderatore presente nel parametro moderatoreLoggato, {@code false} altrimenti.
     *
     */
    public boolean moderatoreLoggatoGestiscePartita(String codicePartitaSelezionata){
        for(PartitaPubblica p: moderatoreLoggato.getPartiteGestite()){
            if(p.getCodicePartita().equalsIgnoreCase(codicePartitaSelezionata)){
                return true;
            }
        }
        return false;
    }

    /**
     * Rimuove una {@link PartitaPubblica} dal sistema nel caso in cui non ci sia più nessun {@link Moderatore}
     * a gestirla, forzando l'uscita di tutti i {@link Giocatore} partecipanti alla partita. Tale uscita viene
     * prima registrata permanentemenete su file di testo tramite l'implementazione {@link PartitePubblicheDAOImplText}
     * dell'interfaccia {@link PartitePubblicheDAO}.
     *
     * @param partitaDaEliminare the partita da eliminare
     */
    public void eliminaPartita(Partita partitaDaEliminare){
        PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/GestioniExtraModeratoriPartite.txt");
        partitePubblicheDAO.rimuoviPartecipantiPartitaPubblicaEliminata(partitaDaEliminare.getCodicePartita());
        for(int i = 0; i < partitaDaEliminare.getPartecipantiPartita().size(); i++)
        {
            partitaDaEliminare.getPartecipantiPartita().get(i).esciDallaPartita();
        }
    }

    /**
     * Registra l'uscita del {@link Moderatore} attualmente loggato dal sistema, verificando se, per
     * ogni {@link PartitaPubblica} presente nelle sue partite gestite, è l'unico moderatore rimasto.
     * In tal caso la partita deve essere eliminata dal sistema e l'implementazione {@link PartitePubblicheDAOImplText}
     * dell'interfaccia {@link PartitePubblicheDAO} si occupa di registrare tale eliminazione.
     * Altrimenti, si verifica se il moderatore presente nel parametro moderatoreLoggato è il moderatore
     * utilizzato nel costruttore della partita o se la sua gestione è considerata "extra"; nel primo caso,
     * {@link PartitePubblicheDAOImplText} aggiorna nel file di testo il campo relativo al moderatore utilizzato
     * nel costruttore della partita pubblica presa in considerazione nel file di testo, prendendo il primo presente
     * tra le gestioni "extra" di tale partita, nel secondo viene soltanto cancellata la gestione "extra" del moderatore
     * loggato.
     */
    public void moderatoreLoggatoEsceDalSistema() {
        for (int i = 0; i < moderatoreLoggato.getPartiteGestite().size(); i++) {
            PartitaPubblica partitaGestita = moderatoreLoggato.getPartiteGestite().get(i);
            if (partitaGestita.getModeratori().size() == 1) {
                PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/PartitePubbliche.txt");
                partitePubblicheDAO.rimuoviPartitaPubblica(partitaGestita.getCodicePartita());
                moderatoreLoggato.rimuoviPartitaGestita(partitaGestita);
                partitaGestita.getModeratori().removeFirst();
                listaPartitePubbliche.remove(partitaGestita);
                this.eliminaPartita(partitaGestita);
                i = i - 1;
            } else {
                if (moderatoreLoggato.getNomeUtente().equals(partitaGestita.getModeratori().get(0).getNomeUtente())) {
                    PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/PartitePubbliche.txt");
                    partitePubblicheDAO.aggiornaModeratorePartitaPubblica(partitaGestita.getModeratori().get(1).getNomeUtente(), partitaGestita.getCodicePartita());
                    partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/GestioniExtraModeratoriPartite.txt.");
                    partitePubblicheDAO.rimuoviGestioneExtraPartitaPubblica(partitaGestita.getModeratori().get(1).getNomeUtente());
                } else {

                    PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/GestioniExtraModeratoriPartite.txt");
                    partitePubblicheDAO.rimuoviGestioneExtraPartitaPubblica(partitaGestita.getModeratori().get(1).getNomeUtente());
                }
                moderatoreLoggato.rimuoviPartitaGestita(partitaGestita);
                partitaGestita.getModeratori().remove(moderatoreLoggato);
            }
        }
    }

    /**
     * Registra la fine della gestione di una {@link PartitaPubblica} da parte del {@link Moderatore} attualmente
     * loggato nel sistema, verificando se, per la partita pubblica di cui è fornito il codice identificativo, è
     * l'unico moderatore rimasto. In tal caso la partita deve essere eliminata dal sistema e l'implementazione
     * {@link PartitePubblicheDAOImplText} dell'interfaccia {@link PartitePubblicheDAO} si occupa di registrare
     * tale eliminazione. Altrimenti, si verifica se il moderatore presente nel parametro moderatoreLoggato è il moderatore
     * utilizzato nel costruttore della partita o se la sua gestione è considerata "extra"; nel primo caso,
     * {@link PartitePubblicheDAOImplText} aggiorna nel file di testo il campo relativo al moderatore utilizzato
     * nel costruttore della partita pubblica presa in considerazione nel file di testo, prendendo il primo presente
     * tra le gestioni "extra" di tale partita, nel secondo viene soltanto cancellata la gestione "extra" del moderatore
     * loggato.
     * @param codicePartitaSelezionata Il codice identificativo della partita pubblica che il moderatore presente
     *                                 nel parametro moderatoreLoggato ha smesso di gestire.
     */
    public void moderatoreLoggatoTerminaGestionePartita(String codicePartitaSelezionata){
        PartitaPubblica partitaGestita = null;
        for(PartitaPubblica p: listaPartitePubbliche)
        {
            if(p.getCodicePartita().equalsIgnoreCase(codicePartitaSelezionata))
            {
                partitaGestita = p;
                break;
            }
        }
        if(partitaGestita.getModeratori().size() == 1)
        {
            PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/PartitePubbliche.txt");
            partitePubblicheDAO.rimuoviPartitaPubblica(partitaGestita.getCodicePartita());
            moderatoreLoggato.rimuoviPartitaGestita(partitaGestita);
            partitaGestita.getModeratori().removeFirst();
            listaPartitePubbliche.remove(partitaGestita);
            this.eliminaPartita(partitaGestita);
        }
        else{
            if(moderatoreLoggato.getNomeUtente().equals(partitaGestita.getModeratori().get(0).getNomeUtente())) {
                PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/PartitePubbliche.txt");
                partitePubblicheDAO.aggiornaModeratorePartitaPubblica(partitaGestita.getModeratori().get(1).getNomeUtente(),partitaGestita.getCodicePartita());
                partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/GestioniExtraModeratoriPartite.txt");
                partitePubblicheDAO.rimuoviGestioneExtraPartitaPubblica(partitaGestita.getModeratori().get(1).getNomeUtente());
            }
            else{

                PartitePubblicheDAO partitePubblicheDAO = new PartitePubblicheDAOImplText("dati_txt/GestioniExtraModeratoriPartite.txt");
                partitePubblicheDAO.rimuoviGestioneExtraPartitaPubblica(partitaGestita.getModeratori().get(1).getNomeUtente());
            }
            moderatoreLoggato.rimuoviPartitaGestita(partitaGestita);
            partitaGestita.getModeratori().remove(moderatoreLoggato);
        }
    }

    /**
     * Restituisce l'elenco delle {@link Segnalazione} effettuate contro il {@link Giocatore}
     * che il {@link Moderatore} attualmente loggato nel sistema sta valutando.
     *
     * @param nomeUtenteGiocatore Il nome utente del giocatore che il moderatore loggato sta valutando.
     * @return Un ArrayList contenente le rappresntazioni testuali di tutte le segnalazioni ricevute
     * dal giocatore di cui è fornito il nome utente.
     */
    public ArrayList<String> moderatoreLoggatoVisualizzaSegnalazioniGiocatore(String nomeUtenteGiocatore){
        ArrayList<String> listaDaRestituire = new ArrayList<String>();
        Giocatore giocatoreAnalizzato = null;
        for(Giocatore g: listaGiocatori){
            if(g.getNomeUtente().equals(nomeUtenteGiocatore)){
               giocatoreAnalizzato = g;
            }
        }
        for(Segnalazione s : giocatoreAnalizzato.getSegnalazioniRicevute()){
            listaDaRestituire.add(s.toString());
        }
        return listaDaRestituire;
    }

    /**
     * Autorizza il {@link Moderatore} attualmente loggato nel sistema a bandire dal sistema il
     * {@link Giocatore} di cui è fornito il nome utente.
     * In particolare, l'interfaccia {@link GiocatoriDAO} nella sua implementazione {@link GiocatoriDAOImplText}
     * aggiorna il campo relativo allo stato isAttivo del nome utente fornito, poi il giocatore
     * viene trovato nella lista dei giocatori attivi, aggiunto alla lista dei giocatori banditi
     * e rimosso da quella dei giocatori attivi.
     *
     * @param nomeUtenteGiocatoreBandito Il nome utente del giocatore che il moderatore presente nel parametro
     *                                   moderatoreLoggato ha deciso di bandire dal sistema.
     */
    public void moderatoreLoggatoBandisceGiocatore(String nomeUtenteGiocatoreBandito)
    {
        GiocatoriDAO giocatoriDAO = new GiocatoriDAOImplText("dati_txt/Giocatori.txt");
        giocatoriDAO.rimuoviGiocatore(nomeUtenteGiocatoreBandito);
        for(Giocatore g: listaGiocatori){
            if(g.getNomeUtente().equals(nomeUtenteGiocatoreBandito)){
                listaGiocatoriBanditi.add(g);
                moderatoreLoggato.bandisciGiocatore(g);
                listaGiocatori.remove(g);
                break;
            }
        }
    }

    /**
     * Restituisce l'elenco dei {@link Giocatore} banditi dal sistema in seguito alla richiesta
     * da parte del {@link Moderatore}.
     *
     * @return Un ArrayList contenente i dati fondamentali dei giocatori banditi dal sistema.
     */
    public ArrayList<String> mostraGiocatoriBanditi(){
        ArrayList<String> listaDaRestituire = new ArrayList<>();
        for(Giocatore g: listaGiocatoriBanditi){
                listaDaRestituire.add(g.toString());
        }
        return listaDaRestituire;
    }

    /**
     * Autorizza il {@link Moderatore} attualmente loggato nel sistema a riattivare nel sistema il
     * {@link Giocatore} di cui è fornito il nome utente.
     * In particolare, l'interfaccia {@link GiocatoriDAO} nella sua implementazione {@link GiocatoriDAOImplText}
     * aggiorna il campo relativo allo stato isAttivo del nome utente fornito, poi il giocatore
     * viene trovato nella lista dei giocatori banditi, aggiunto alla lista dei giocatori attivi
     * e rimosso da quella dei giocatori banditi.
     *
     * @param nomeUtenteGiocatore Il nome utente del giocatore che il moderatore presente nel parametro
     *                            moderatoreLoggato ha deciso di riattivare nel sistema.
     */
    public void moderatoreLoggatoRiattivaGiocatore(String nomeUtenteGiocatore){
        GiocatoriDAO giocatoriDAO = new GiocatoriDAOImplText("dati_txt/Giocatori.txt");
        giocatoriDAO.riattivaGiocatore(nomeUtenteGiocatore);
        for(Giocatore g: listaGiocatoriBanditi){
            if(g.getNomeUtente().equals(nomeUtenteGiocatore)){
                moderatoreLoggato.riattivaGiocatore(g);
                listaGiocatoriBanditi.remove(g);
                listaGiocatori.add(g);

                break;
            }
        }
    }
}
