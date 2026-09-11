package implementazioneTextDAO;

import dao.PartitePubblicheDAO;

import java.io.*;
import java.util.ArrayList;

 /**
 * La classe LobbyDAOImplText è un implementazione di {@link PartitePubblicheDAO} che utilizza
 * file di testo come sistema di persistenza dei dati.
 */
public class PartitePubblicheDAOImplText implements PartitePubblicheDAO {
     /**
      * Il percorso o il nome del file di testo dove sono memorizzati i dati relativi a alle
      * {@link model.PartitaPubblica}, o la partecipazione dei {@link model.Giocatore} ad esse,
      * oppure la loro gestione da parte dei {@link model.Moderatore}.
      */
    private String nomeFile;

     /**
      * Istanzia un nuovo oggetto LobbyDAOImplText fornendo uno specifico file di testo.
      *
      * @param nomeFile Il percorso o il nome del file di testo dove sono memorizzati i dati relativi a alle
      *                 {@link model.PartitaPubblica}, o la partecipazione dei {@link model.Giocatore} ad esse,
      *                 oppure la loro gestione da parte dei {@link model.Moderatore}.
      */
    public PartitePubblicheDAOImplText(String nomeFile){
        this.nomeFile = nomeFile;
    }

     /**
      * Legge il file di testo riga per riga, utilizzando il carattere ";" per suddividere i dati
      * formattati della {@link model.PartitaPubblica} presente sulla riga e popolando le liste fornite.
      * Se la riga letta non è ben formattata o difetta di qualche dato, viene saltata.
      * @param listaCodiciPartitePubbliche Lista in cui inserire i codici identificativi delle
      *                                    partite pubbliche recuperate.
      * @param listaVideogiochi            Lista in cui inserire videogiochi dove sono giocate
      *                                    le partite pubbliche recuperate.
      * @param listaCapienze               Lista in cui inserire le capienze delle
      *                                    partite pubbliche recuperate.
      * @param listaModeratoriPartite      Lista in cui inserire i nomi utente dei moderatori
      *                                    che gestiscono le partite pubbliche recuperate.
      *
      * @throws RuntimeException Se la lettura del file non riesce.
      */
    public void generaPartitePubbliche(ArrayList<String> listaCodiciPartitePubbliche, ArrayList<String> listaVideogiochi, ArrayList<Integer> listaCapienze, ArrayList<String> listaModeratoriPartite)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String rigaPartitaPubblica;
            while ((rigaPartitaPubblica = reader.readLine()) != null) {
                String[] datiPartitaPubblica = rigaPartitaPubblica.split(";");
                if (datiPartitaPubblica.length != 4) continue;
                listaCodiciPartitePubbliche.add(datiPartitaPubblica[0]);
                listaVideogiochi.add(datiPartitaPubblica[1]);
                listaCapienze.add(Integer.parseInt(datiPartitaPubblica[2]));
                listaModeratoriPartite.add(datiPartitaPubblica[3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile caricare dati delle partite pubbliche, riavviare l'applicazione");
        }
    }

     /**
      * Legge il file di testo riga per riga, utilizzando il carattere ";" per suddividere i dati
      * formattati della gestione da parte dei {@link model.Moderatore} della {@link model.PartitaPubblica}
      * sulla riga e popolando le liste fornite. Se la riga letta non è ben formattata o difetta di
      * qualche dato, viene saltata.
      * @param listaNomi                   Lista in cui inserire i nomi utente dei {@link model.Moderatore}
      *                                    che gestiscono le partite pubbliche recuperate.
      * @param listaCodiciPartitePubbliche Lista in cui inserire i codici identificativi delle
      *                                    partite pubbliche recuperate.
      *
      * @throws RuntimeException Se la lettura del file non riesce.
      */
    public void generaGestioniExtraPartitePubbliche(ArrayList<String> listaNomi, ArrayList<String> listaCodiciPartitePubbliche){
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String rigaGestione;
            while ((rigaGestione = reader.readLine()) != null) {
                String[] datiGestione = rigaGestione.split(";");
                if (datiGestione.length != 2) continue;
                listaNomi.add(datiGestione[0]);
                listaCodiciPartitePubbliche.add(datiGestione[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile caricare dati delle partite pubbliche, riavviare l'applicazione");
        }
    }


     /**
      * Legge il file di testo riga per riga, utilizzando il carattere ";" per suddividere i dati
      * formattati della partecipazione del {@link model.Giocatore} alla {@link model.PartitaPubblica}
      * presente sulla riga e popolando le liste fornite. Se la riga letta non è ben formattata o
      * difetta di qualche dato, viene saltata.
      * @param listaNomi                   Lista in cui inserire i nomi utente dei partecipanti alle
      *                                    partite pubbliche recuperate.
      * @param listaCodiciPartitePubbliche Lista in cui inserire i codici identificativi delle
      *                                    partite pubbliche recuperate.
      */
    public void generaPartecipantiPartitePubbliche(ArrayList<String> listaNomi, ArrayList<String> listaCodiciPartitePubbliche)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String rigaPartecipazione;
            while ((rigaPartecipazione = reader.readLine()) != null) {
                String[] datiPartecipazione = rigaPartecipazione.split(";");
                if (datiPartecipazione.length != 2) continue;
                listaNomi.add(datiPartecipazione[0]);
                listaCodiciPartitePubbliche.add(datiPartecipazione[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile caricare dati delle partite pubbliche, riavviare l'applicazione");
        }
    }

     /**
      * Sovrascrive il file di testo (append del FileWriter settato a false) eliminando
      * la riga relativa alla {@link model.PartitaPubblica} che è stato rimossa dal sistema,
      * di cui viene fornito il codice identificativo.
      * @param codicePartitaPubblica Il codice della partita pubblica da rimuovere.
      *
      * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
      * oppure in caso di errore di scrittura.
      */
    public void rimuoviPartitaPubblica(String codicePartitaPubblica){
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String linea;
            while ((linea = reader.readLine()) != null) {
                if(linea.isEmpty()){
                    continue;
                }
                String[] datiPartitaPubblica = linea.split(";");
                if (datiPartitaPubblica.length >= 4) {
                    String codiceAttuale = datiPartitaPubblica[0];
                    if (codiceAttuale.equalsIgnoreCase(codicePartitaPubblica)) {
                        continue;
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eliminazione partita pubblica non riuscita. Riprovare o riavviare l'applicazione");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))) {
            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Eliminazione partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eliminazione partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
        }
    }

     /**
      * Sovrascrive il file di testo (append del FileWriter settato a false) eliminando
      * le righe relative ai {@link model.Giocatore} partecipanti alla
      * {@link model.PartitaPubblica} che è stato rimossa dal sistema, di cui viene
      * fornito il codice identificativo.
      * @param codicePartitaPubblica Il codice della partita pubblica che è stata eliminata.
      */
    public void rimuoviPartecipantiPartitaPubblicaEliminata(String codicePartitaPubblica){
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String linea;
            while ((linea = reader.readLine()) != null) {
                if(linea.isEmpty()){
                    continue;
                }
                String[] datiPartecipazione = linea.split(";");

                if (datiPartecipazione.length >= 2) {
                    String codiceAttuale = datiPartecipazione[1];
                    if (codiceAttuale.equalsIgnoreCase(codicePartitaPubblica)) {
                        continue;
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eliminazione giocatori dalla partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))) {
            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Eliminazione giocatori dalla partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eliminazione giocatori dalla partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
        }
    }

     /**
      * Scrive in coda al file di testo (append del FileWriter settato a true) i dati dell'ingresso
      * di un nuovo partecipante alla partita pubblica.
      *
      * @param nomeUtente            Il nome utente del {@link model.Giocatore} che è entrato nella
      *                              partita pubblica.
      * @param codicePartitaPubblica Il codice della {@link model.PartitaPubblica} in cui è entrato
      *                              il giocatore.
      * @throws RuntimeException     Se c'è un errore di apertura o di scrittura sul file.
      */
     public void aggiungiGiocatoreAllaPartitaPubblica(String nomeUtente, String codicePartitaPubblica) {
         try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile, true)))){
            writer.print("\n"+nomeUtente + ";" + codicePartitaPubblica);
            if(writer.checkError()) {
                System.out.println("Errore nella scrittura del file.");
                throw new RuntimeException("Ingresso in partita non riuscito, riprovare o riavviare l'applicazione.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ingresso in partita non riuscito, riprovare o riavviare l'applicazione.");
        }
    }

     /**
      * Sovrascrive il file di testo (append del FileWriter settato a false) eliminando
      * la riga relativa alla partecipazione del {@link model.Giocatore}, di cui è fornito
      * il nome utente, che è uscito dalla {@link model.PartitaPubblica}, di cui viene
      * fornito il codice identificativo.
      * @param nomeUtente Il nome utente del {@link model.Giocatore} che è uscito dalla partita pubblica.
      *
      * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
      * oppure in caso di errore di scrittura.
      */
    public void rimuoviGiocatoreDallaPartitaPubblica(String nomeUtente){
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String linea;
            while ((linea = reader.readLine()) != null) {
                if(linea.isEmpty()){
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
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Uscita dalla partita pubblica non riuscita, riprovare o riavviare l'applicazione.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))) {
            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Uscita dalla partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Uscita dalla partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
        }
    }

     /**
      * Scrive in coda al file di testo (append del FileWriter settato a true) i dati della gestione
      * da parte di un ulteriore {@link model.Moderatore} della {@link model.PartitaPubblica}.
      *
      * @param nomeUtente            Il nome utente del {@link model.Moderatore} che sta gestendo
      *                              la partita pubblica.
      * @param codicePartitaPubblica Il codice della {@link model.PartitaPubblica} che è gestita
      *                              dal moderatore.
      * @throws RuntimeException     Se c'è un errore di apertura o di scrittura sul file.
      */
    public void aggiungiGestioneExtraPartitaPubblica(String nomeUtente, String codicePartitaPubblica){
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile, true)))){
            writer.print("\n"+nomeUtente + ";" + codicePartitaPubblica);
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Gestione partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Gestione partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
        }
    }

     /**
      * Sovrascrive il file di testo (append del FileWriter settato a false) eliminando
      * la riga relativa alla gestione del {@link model.Moderatore}, di cui è fornito
      * il nome utente, della {@link model.PartitaPubblica} di cui viene fornito il
      * codice identificativo.
      * @param nomeUtente Il nome utente del {@link model.Moderatore} che ha terminato la gestione
      *                   della {@link model.PartitaPubblica}.
      *
      * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
      * oppure in caso di errore di scrittura.
      */
    public void rimuoviGestioneExtraPartitaPubblica(String nomeUtente){
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String linea;
            while ((linea = reader.readLine()) != null) {
                if(linea.isEmpty()){
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
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Rimozione gestione partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))) {
            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Rimozione gestione partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Rimozione gestione partita pubblica non riuscita. Riprovare o riavviare l'applicazione.");
        }
    }

     /**
      * Sovrascrive il file di testo (append del FileWriter settato a false) aggiornando
      * la riga relativa alla {@link model.PartitaPubblica} il cui {@link model.Moderatore}
      * necessario per il costruttore è stato cambiato.
      * @param nomeUtente            Il nome utente del nuovo {@link model.Moderatore}
      *                              da aggiungere al campo moderatore legato al
      *                              costruttore della {@link model.PartitaPubblica}
      * @param codicePartitaPubblica Il codice della partita pubblica il cui moderatore
      *                              "principale" viene aggiornato.
      *
      * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
      * oppure in caso di errore di scrittura.
      */
    public void aggiornaModeratorePartitaPubblica(String nomeUtente, String codicePartitaPubblica){
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String linea;
            while ((linea = reader.readLine()) != null) {
                if(linea.isEmpty()){
                    continue;
                }
                String[] datiPartitaPubblica = linea.split(";");

                if (datiPartitaPubblica.length >= 4) {
                    String codiceAttuale = datiPartitaPubblica[0];
                    if (codiceAttuale.equalsIgnoreCase(codicePartitaPubblica)) {
                        String rigaModificata = datiPartitaPubblica[0]+";"+datiPartitaPubblica[1]+";"+datiPartitaPubblica[2]+";"+nomeUtente;
                        righeAggiornate.add(rigaModificata);
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Aggiornamento gestione partita pubblica non riuscito. Riprovare o riavviare l'applicazione");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))) {
            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Aggiornamento gestione partita pubblica non riuscito. Riprovare o riavviare l'applicazione.");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Aggiornamento gestione partita pubblica non riuscito. Riprovare o riavviare l'applicazione.");
        }
    }
}
