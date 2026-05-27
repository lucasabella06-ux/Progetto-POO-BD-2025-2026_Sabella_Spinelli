package Controller;

import Model.*;

import java.util.ArrayList;

import java.util.Random;

import Exception.*;

public class Controller {
    ArrayList<Giocatore> listaGiocatori;
    ArrayList<Moderatore> listaModeratori;
    ArrayList<PartitaPubblica> listaPartitePubbliche;
    ArrayList<Lobby> listaLobby;
    Classifica classifica;
    Giocatore giocatoreLoggato;
    Moderatore moderatoreLoggato;
    Random rand = new Random();

    public Controller() {
        this.listaGiocatori = new ArrayList<Giocatore>();
        this.listaModeratori = new ArrayList<Moderatore>();
        this.listaPartitePubbliche = new ArrayList<PartitaPubblica>();
        this.listaLobby = new ArrayList<Lobby>();
    }

    public void generaGiocatori (){
        for (int i = 1; i <= 50; i++) {
            String nome = "Giocatore " + i;
            String email = "Giocatore" + i + "@gmail.com";
            String passwordUtente = "AAAA000"+i; // es: AAAA0001
            String idGiocatore = "G00"+i;      // es: G001

            listaGiocatori.add(new Giocatore(nome, email, passwordUtente, idGiocatore));
        }

        for(int i = 51; i <= 100; i++)
        {
            String nome = "Giocatore " + i;
            String email = "Giocatore" + i + "@gmail.com";
            String passwordUtente = "AAAA000"+i; // es: AAAA0001
            String idGiocatore = "G00"+i;
            int livello = rand.nextInt(100) + 1;
            int vinte = rand.nextInt((livello - 1) * 10, livello * 10 - 1);
            int perse = rand.nextInt(livello * 11);

            listaGiocatori.add(new Giocatore(nome, email, passwordUtente, idGiocatore, livello, vinte, perse));
        }

    }

    public void generaModeratori(){
        for(int i = 1; i <= 10; i++) {
            String nome = "Moderatore " + i;
            String email = "Moderatore" + i + "@gmail.com";
            String passwordUtente = "BBBB000" + i;
            String codiceAccessoModeratore = "MM11" +i;
            int orarioInizioAttivita = rand.nextInt(12) +1;
            int orarioFineAttivita = rand.nextInt(13, 24);

            listaModeratori.add(new Moderatore(nome,email, passwordUtente, codiceAccessoModeratore, orarioInizioAttivita, orarioFineAttivita));
        }
    }

    public void generaPartitePubbliche(){
        PartitaPubblica p1 = new PartitaPubblica("P001", "Call of Duty", listaModeratori.get(rand.nextInt(10)));
        PartitaPubblica p2 = new PartitaPubblica("P002", "Fall Guys", listaModeratori.get(rand.nextInt(10)));
        PartitaPubblica p3 = new PartitaPubblica("P003", "Fortnite", listaModeratori.get(rand.nextInt(10)));
        PartitaPubblica p4 = new PartitaPubblica("P004", "Apex Legends", listaModeratori.get(rand.nextInt(10)));
        PartitaPubblica p5 = new PartitaPubblica("P005", "Rainbow Six Siege", listaModeratori.get(rand.nextInt(10)));
        PartitaPubblica p6 = new PartitaPubblica("P006", "Rocket League", listaModeratori.get(rand.nextInt(10)));
        PartitaPubblica p7 = new PartitaPubblica("P001", "Brawl Stars", listaModeratori.get(rand.nextInt(10)));
        PartitaPubblica p8 = new PartitaPubblica("P001", "League of Legends", listaModeratori.get(rand.nextInt(10)));
        PartitaPubblica p9 = new PartitaPubblica("P001", "GTA 6", listaModeratori.get(rand.nextInt(10)));
        PartitaPubblica p10 = new PartitaPubblica("P001", "Warframe", listaModeratori.get(rand.nextInt(10)));

        listaPartitePubbliche.add(p1);
        listaPartitePubbliche.add(p2);
        listaPartitePubbliche.add(p3);
        listaPartitePubbliche.add(p4);
        listaPartitePubbliche.add(p5);
        listaPartitePubbliche.add(p6);
        listaPartitePubbliche.add(p7);
        listaPartitePubbliche.add(p8);
        listaPartitePubbliche.add(p9);
        listaPartitePubbliche.add(p10);
    }
    public void aggiungiGiocatoriAllePartite(){
        listaGiocatori.get(0).entraInPartita(listaPartitePubbliche.get(0));
        listaGiocatori.get(1).entraInPartita(listaPartitePubbliche.get(0));
        listaGiocatori.get(2).entraInPartita(listaPartitePubbliche.get(0));
        listaGiocatori.get(3).entraInPartita(listaPartitePubbliche.get(0));
        listaGiocatori.get(4).entraInPartita(listaPartitePubbliche.get(0));
        listaGiocatori.get(5).entraInPartita(listaPartitePubbliche.get(0));
        listaGiocatori.get(6).entraInPartita(listaPartitePubbliche.get(0));
        listaGiocatori.get(7).entraInPartita(listaPartitePubbliche.get(0));
        listaGiocatori.get(99).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(98).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(97).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(96).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(95).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(94).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(93).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(92).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(91).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(90).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(89).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(88).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(87).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(86).entraInPartita(listaPartitePubbliche.get(9));
        listaGiocatori.get(85).entraInPartita(listaPartitePubbliche.get(9));
    }

    public void generaLobby()
    {
        listaGiocatori.get(8).creaLobby("L001", 12);
        listaGiocatori.get(9).creaLobby("L002", 10);
        listaGiocatori.get(84).creaLobby("L003", 24);
        listaGiocatori.get(83).creaLobby("L004", 6);

        Lobby l1 = listaGiocatori.get(8).getLobbyAttuale();
        Lobby l2 = listaGiocatori.get(9).getLobbyAttuale();
        Lobby l3 = listaGiocatori.get(84).getLobbyAttuale();
        Lobby l4 = listaGiocatori.get(83).getLobbyAttuale();

        listaLobby.add(l1);
        listaLobby.add(l2);
        listaLobby.add(l3);
        listaLobby.add(l4);
    }

    public void aggiungiGiocatoriAlleLobby()
    {
        listaGiocatori.get(10).entraInLobby(listaLobby.get(0), "L001");
        listaGiocatori.get(11).entraInLobby(listaLobby.get(0), "L001");
        listaGiocatori.get(12).entraInLobby(listaLobby.get(0), "L001");
        listaGiocatori.get(13).entraInLobby(listaLobby.get(1), "L002");
        listaGiocatori.get(14).entraInLobby(listaLobby.get(2), "L003");
    }

    public void generaPartiteAmichevoli(){
        listaLobby.get(0).creaPartitaAmichevole("F001", "Valorant");
        listaLobby.get(1).creaPartitaAmichevole("F002", "EA Sports FC");
    }

    public void generaRisultatoPartita(){
        PartitaPubblica partitaConclusa = listaPartitePubbliche.get(0);
        int numeroRandom = rand.nextInt(8);
        partitaConclusa.aggiungiRisultato(partitaConclusa.getPartecipantiPartita().get(0), "25:40");
    }

    public void generaClassifica(){
        classifica = new Classifica(listaGiocatori.get(99));
        classifica.aggiungiGiocatore(listaGiocatori.get(98));
        classifica.aggiungiGiocatore(listaGiocatori.get(97));
        classifica.aggiungiGiocatore(listaGiocatori.get(96));
        classifica.aggiungiGiocatore(listaGiocatori.get(95));
        classifica.aggiungiGiocatore(listaGiocatori.get(94));
        classifica.aggiungiGiocatore(listaGiocatori.get(93));
        classifica.aggiungiGiocatore(listaGiocatori.get(92));
        classifica.aggiungiGiocatore(listaGiocatori.get(91));
        classifica.aggiungiGiocatore(listaGiocatori.get(90));
        classifica.rimuoviGiocatore(classifica.getGiocatoriInClassifica().get(9));
        classifica.aggiungiGiocatore(listaGiocatori.get(0));
        classifica.ordinaGiocatori();
    }

    public ArrayList<Giocatore> getListaGiocatori() {
        return listaGiocatori;
    }

    public ArrayList<Moderatore> getListaModeratori() {
        return listaModeratori;
    }

    public ArrayList<PartitaPubblica> getListaPartitePubbliche() {
        return listaPartitePubbliche;
    }

    public ArrayList<Lobby> getListaLobby() {
        return listaLobby;
    }

    public Giocatore getGiocatoreLoggato() {
        return giocatoreLoggato;
    }

    public Moderatore getModeratoreLoggato() {
        return moderatoreLoggato;
    }

    public boolean verificaLoginGiocatore(String email, String password) throws EmailVuotaException, PasswordVuotaException
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
                if (g.getEmailUtente().equalsIgnoreCase(email) && g.getPasswordUtente().equals(password)){
                    giocatoreLoggato = g;
                    return true;
                }
            }
            return false;
        }
    }

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
                if (m.getEmailUtente().equalsIgnoreCase(email) && m.getPasswordUtente().equals(password) && m.getCodiceAccessoModeratore().equals(codiceModeratore)){
                    moderatoreLoggato = m;
                    return true;
                }
            }
            return false;
        }
    }


    public boolean cercaEmail(String email)
    {
        for(Giocatore g: listaGiocatori)
        {
            if(g.getEmailUtente().equals(email)){
                return true;
            }
        }

        for(Moderatore m: listaModeratori)
            if(m.getEmailUtente().equals(email))
            {
                return true;
            }
        return false;
    }

    public void creaNuovoGiocatore(String email, String password, String nomeUtente) throws EmailVuotaException, EmailNonValidaException, EmailGiaEsistenteException, PasswordVuotaException, NomeUtenteVuotoException {
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
        if(nomeUtente.isBlank())
        {
            throw new NomeUtenteVuotoException("Nessun nome utente inserito.");
        }

        int totaleGiocatori = listaGiocatori.size();
        Giocatore nuovoGiocatore = new Giocatore(nomeUtente, email, password,"G00"+(totaleGiocatori+1));
        listaGiocatori.add(nuovoGiocatore);
        giocatoreLoggato = nuovoGiocatore;
    }

    public void creaNuovoModeratore(String email, String password, String nomeUtente) throws EmailVuotaException, EmailNonValidaException, EmailGiaEsistenteException, PasswordVuotaException, NomeUtenteVuotoException {
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
        if(nomeUtente.isBlank())
        {
            throw new NomeUtenteVuotoException("Nessun nome utente inserito.");
        }

        int totaleModeratori = listaModeratori.size();
        Moderatore nuovoModeratore = new Moderatore(nomeUtente, email, password,"M11"+(totaleModeratori+1));
        listaModeratori.add(nuovoModeratore);
        moderatoreLoggato = nuovoModeratore;
    }
}
