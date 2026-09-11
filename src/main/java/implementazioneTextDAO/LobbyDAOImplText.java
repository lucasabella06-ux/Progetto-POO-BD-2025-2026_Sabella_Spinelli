package implementazioneTextDAO;

import dao.LobbyDAO;

import java.io.*;
import java.util.ArrayList;

/**
 * La classe LobbyDAOImplText è un implementazione di {@link LobbyDAO} che utilizza
 * file di testo come sistema di persistenza dei dati.
 */
public class LobbyDAOImplText implements LobbyDAO {
    /**
     * Il percorso o il nome del file di testo dove sono memorizzati i dati relativi a {@link model.Lobby}
     * oppure alle partecipazioni dei {@link model.Giocatore} alle lobby.
     */
    String nomeFile;

    /**
     * Istanzia un nuovo oggetto LobbyDAOImplText fornendo uno specifico file di testo.
     *
     * @param nomeFile Il percorso o il nome del file di testo dove sono memorizzati i dati relativi a {@link model.Lobby}
     *                 oppure alle partecipazioni dei {@link model.Giocatore} alle lobby.
     */
    public LobbyDAOImplText(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    /**
     * Legge il file di testo riga per riga, utilizzando il carattere ";" per suddividere i dati
     * formattati della {@link model.Lobby} presente sulla riga e popolando le liste fornite.
     * Se la riga letta non è ben formattata o difetta di qualche dato, viene saltata.
     * @param listaCodiciLobby      Lista in cui inserire i codici delle lobby recuperati.
     * @param listaCapienzeMaxLobby Lista in cui inserire le capienze massime delle lobby
     *                              recuperate.
     * @param listaHost             Lista in cui inserire i nomi utente degli host delle
     *                              lobby recuperati.
     *
     * @throws RuntimeException Se la lettura del file non riesce.
     */
    public void generaLobby(ArrayList<String> listaCodiciLobby, ArrayList<Integer> listaCapienzeMaxLobby, ArrayList<String> listaHost){
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String rigaLobby;
            while ((rigaLobby = reader.readLine()) != null) {
                String[] datiLobby = rigaLobby.split(";");
                if (datiLobby.length != 3) continue;
                listaCodiciLobby.add(datiLobby[0]);
                listaCapienzeMaxLobby.add(Integer.parseInt(datiLobby[1]));
                listaHost.add(datiLobby[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile caricare dati lobby, riavviare l'applicazione");
        }
    }

    /**
     * Legge il file di testo riga per riga, utilizzando il carattere ";" per suddividere i dati
     * formattati della partecipazione del {@link model.Giocatore} alla {@link model.Lobby} presente
     * sulla riga e popolando le liste fornite. Se la riga letta non è ben formattata o difetta di
     * qualche dato, viene saltata.
     * @param listaNomi        Lista in cui inserire i nomi utenti dei partecipanti recuperati.
     * @param listaCodiciLobby Lista in cui inserire i codici delle lobby recuperati.
     *
     * @throws RuntimeException Se la lettura del file non riesce.
     */
    public void generaPartecipantiLobby(ArrayList<String> listaNomi, ArrayList<String> listaCodiciLobby){
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String rigaPartecipazione;
            while ((rigaPartecipazione = reader.readLine()) != null) {
                String[] datiPartecipazione = rigaPartecipazione.split(";");
                if (datiPartecipazione.length != 2) continue;
                listaNomi.add(datiPartecipazione[0]);
                listaCodiciLobby.add(datiPartecipazione[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare dati lobby, riavviare l'applicazione");
        }
    }

    /**
     * Scrive in coda al file di testo (append del FileWriter settato a true) i dati
     * di un nuova lobby.
     * @param codiceLobby Il codice univoco della {@link model.Lobby}.
     * @param capienzaMax La capienza massima della {@link model.Lobby}.
     * @param host        Il nome utente del {@link model.Giocatore} host della lobby.
     *
     * @throws RuntimeException Se c'è un errore di apertura o di scrittura sul file.
     */
    public void aggiungiLobby(String codiceLobby, int capienzaMax, String host){
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile, true)))){
            writer.print("\n"+codiceLobby + ";" + capienzaMax + ";" + host);
            if(writer.checkError()){
                System.out.println("Errore nella scrittura del file.");
                throw new RuntimeException("Creazione lobby non riuscita, riprovare o riavviare l'applicazione.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Creazione lobby non riuscita, riprovare o riavviare l'applicazione.");
        }
    }

    /**
     * Sovrascrive il file di testo (append del FileWriter settato a false) eliminando
     * la riga relativa alla {@link model.Lobby} che è stato cancellata dal sistema,
     * di cui viene fornito il codice identificativo.
     * @param codiceLobby Il codice della {@link model.Lobby} da rimuovere.
     *
     * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
     * oppure in caso di errore di scrittura.
     */
    public void rimuoviLobby(String codiceLobby){
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isEmpty()) {
                    continue;
                }
                String[] datiLobby = linea.split(";");
                if (datiLobby.length >= 3) {
                    String codiceAttuale = datiLobby[0];
                    if (codiceAttuale.equalsIgnoreCase(codiceLobby)) {
                        continue;
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eliminazione lobby non riuscita, riprovare o riavviare l'applicazione.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))) {

            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Eliminazione lobby non riuscita. Riprovare o riavviare l'applicazione.");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eliminazione lobby non riuscita. Riprovare o riavviare l'applicazione.");
        }
    }

    /**
     * Scrive in coda al file di testo (append del FileWriter settato a true) i dati dell'ingresso
     * di un nuovo partecipante alla lobby.
     * @param nomeUtente Il nome utente del {@link model.Giocatore} che si è unito alla Lobby
     * @param codiceLobby Il codice della {@link model.Lobby} a cui si è unito il giocatore.
     *
     * @throws RuntimeException Se c'è un errore di apertura o di scrittura sul file.
     */
    public void aggiungiGiocatoreAllaLobby(String nomeUtente, String codiceLobby){
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile, true)))){
            writer.print("\n"+nomeUtente + ";" + codiceLobby);
            if(writer.checkError()){
                System.out.println("Errore nella scrittura del file.");
                throw new RuntimeException("Ingresso in lobby non riuscito, riprovare o riavviare l'applicazione.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ingresso in lobby non riuscito, riprovare o riavviare l'applicazione.");
        }
    }


    /**
     * Sovrascrive il file di testo (append del FileWriter settato a false) eliminando
     * la riga relativa alla partecipazione del {@link model.Giocatore}, di cui è fornito
     * il nome utente, che è uscito dalla {@link model.Lobby} di cui viene fornito il codice
     * identificativo.
     * @param nomeUtente Il nome utente del {@link model.Giocatore} che è uscito dalla Lobby
     *
     * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
     * oppure in caso di errore di scrittura.
     */
    public void rimuoviGiocatoreDallaLobby(String nomeUtente){
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isEmpty()) {
                    continue;
                }
                String[] datiPartecipazioneLobby = linea.split(";");

                if (datiPartecipazioneLobby.length >= 2) {
                    String nomeAttuale = datiPartecipazioneLobby[0];
                    if (nomeAttuale.equalsIgnoreCase(nomeUtente)) {
                        continue;
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        }
           catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Uscita dalla lobby non riuscita, riprovare o riavviare l'applicazione.");
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))) {

                for (String riga : righeAggiornate) {
                    writer.println(riga);
                }
                if(writer.checkError()){
                    System.out.println("Errore in scrittura del file.");
                    throw new RuntimeException("Uscita dalla lobby non riuscita. Riprovare o riavviare l'applicazione.");
                }

            }
            catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Uscita dalla lobby non riuscita. Riprovare o riavviare l'applicazione.");
            }
    }
}
