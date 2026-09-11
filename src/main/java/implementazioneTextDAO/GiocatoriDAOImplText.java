package implementazioneTextDAO;

import dao.GiocatoriDAO;

import java.io.*;
import java.util.ArrayList;

/**
 * La classe GiocatoriDAOImplText è un implementazione di {@link GiocatoriDAO} che utilizza
 * file di testo come sistema di persistenza dei dati.
 */
public class GiocatoriDAOImplText implements GiocatoriDAO {
    /**
     Il percorso o il nome del file di testo dove sono memorizzati i dati relativi ai {@link model.Giocatore}
     */
    String nomeFile;

    /**
     * Istanzia un nuovo oggetto GiocatoriDAOImplText fornendo uno specifico file di testo.
     *
     * @param nomeFile Il percorso o il nome del file di testo dove sono memorizzati i dati relativi ai {@link model.Giocatore}
     */
    public GiocatoriDAOImplText(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    /**
     * Legge il file di testo riga per riga, utilizzando il carattere ";" per suddividere i dati
     * formattati del {@link model.Giocatore} presente sulla riga e popolando le liste fornite.
     * Se la riga letta non è ben formattata o difetta di qualche dato, viene saltata.
     * @param listaNomi         Lista in cui inserire i nomi utente recuperati.
     * @param listaEmail        Lista in cui inserire le mail recuperate.
     * @param listaPassword     Lista in cui inserire le password recuperate.
     * @param listaIdGiocatori  Lista in cui inserire gli identificativi dei giocatori recuperati.
     * @param listaLivelli      Lista in cui inserire i livelli recuperati.
     * @param listaPartiteVinte Lista in cui inserire i valori delle partite vinte recuperati.
     * @param listaPartitePerse Lista in cui inserire i valori delle partite perse recuperati.
     * @param listaIsAttivo     Lista in cui inserire i valori isAttivo recuperati.
     *
     * @throws RuntimeException Se la lettura del file non riesce.
     */
    public void generaGiocatori(ArrayList<String> listaNomi, ArrayList<String> listaEmail, ArrayList<String> listaPassword,
    ArrayList<String> listaIdGiocatori, ArrayList<Integer> listaLivelli, ArrayList<Integer> listaPartiteVinte,
                                ArrayList<Integer> listaPartitePerse, ArrayList<String> listaIsAttivo) {
        try(BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String rigaGiocatore;
            while ((rigaGiocatore = reader.readLine()) != null) {
                String[] datiGiocatore = rigaGiocatore.split(";");
                if (datiGiocatore.length != 8) continue;
                listaNomi.add(datiGiocatore[0]);
                listaEmail.add(datiGiocatore[1]);
                listaPassword.add(datiGiocatore[2]);
                listaIdGiocatori.add(datiGiocatore[3]);
                listaLivelli.add(Integer.parseInt(datiGiocatore[4]));
                listaPartiteVinte.add(Integer.parseInt(datiGiocatore[5]));
                listaPartitePerse.add(Integer.parseInt(datiGiocatore[6]));
                listaIsAttivo.add(datiGiocatore[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile caricare dati dei giocatori, riavviare l'applicazione.");
        }
    }

    /**
     * Scrive in coda al file di testo (append del FileWriter settato a true) i dati
     * di un nuovo giocatore, considerando valori standard per il livello, le partite vinte e le
     * partite perse.
     * @param nomeUtente  Il nome utente del {@link model.Giocatore}
     * @param email       L'email del {@link model.Giocatore}
     * @param password    La password del {@link model.Giocatore}
     * @param idGiocatore Il codice identificativo univoco del {@link model.Giocatore}
     *
     * @throws RuntimeException Se c'è un errore di apertura o di scrittura sul file.
     */
    public void aggiungiGiocatore(String nomeUtente, String email, String password, String idGiocatore ){
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile, true)))) {
            writer.print("\n"+nomeUtente + ";" + email + ";" + password + ";" + idGiocatore + ";1;0;0;true");
            if(writer.checkError()){
                System.out.println("Errore nella scrittura del file.");
                throw new RuntimeException("Impossibile inserire dati del giocatore, riprovare o riavviare l'applicazione.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile inserire dati del giocatore, riprovare o riavviare l'applicazione.");
        }
    }

    /**
     * Sovrascrive il file di testo (append del FileWriter settato a false) aggiornando il campo
     * relativo al parametro isAttivo del {@link model.Giocatore} che è stato bandito dal sistema,
     * di cui viene fornito il nome utente.
     * @param nomeUtente Il nome utente del giocatore da rimuovere.
     *
     * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
     * oppure in caso di errore di scrittura.
     */
    public void rimuoviGiocatore(String nomeUtente) {
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile));) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isEmpty()) {
                    continue;
                }
                String[] datiGiocatore = linea.split(";");

                if (datiGiocatore.length >= 8) {
                    String nomeAttuale = datiGiocatore[0];
                    if (nomeAttuale.equalsIgnoreCase(nomeUtente)) {
                        String rigaModificata = datiGiocatore[0] + ";" + datiGiocatore[1] + ";" + datiGiocatore[2] + ";" + datiGiocatore[3] + ";" + datiGiocatore[4] + ";" + datiGiocatore[5] + ";" + datiGiocatore[6] + ";false";
                        righeAggiornate.add(rigaModificata);
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Rimozione Giocatore non riuscita. Riprovare o riavviare l'applicazione.");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))) {

            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Rimozione Giocatore non riuscita. Riprovare o riavviare l'applicazione.");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Rimozione Giocatore non riuscita. Riprovare o riavviare l'applicazione.");
        }
    }

    /**
     * Sovrascrive il file di testo (append del FileWriter settato a false) aggiornando il campo
     * relativo al parametro isAttivo del {@link model.Giocatore} che è stato riattivato nel sistema,
     * di cui viene fornito il nome utente.
     * @param nomeUtente Il nome utente del giocatore da riattivare.
     *
     * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
     * oppure in caso di errore di scrittura.

     */
    public void riattivaGiocatore(String nomeUtente){
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isEmpty()) {
                    continue;
                }
                String[] datiGiocatore = linea.split(";");

                if (datiGiocatore.length >= 8) {
                    String nomeAttuale = datiGiocatore[0];
                    if (nomeAttuale.equalsIgnoreCase(nomeUtente)) {
                        String rigaModificata = datiGiocatore[0] + ";" + datiGiocatore[1] + ";" + datiGiocatore[2] + ";" + datiGiocatore[3] + ";" + datiGiocatore[4] + ";" + datiGiocatore[5] + ";" + datiGiocatore[6] + ";true";
                        righeAggiornate.add(rigaModificata);
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        }
        catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Riattivazione Giocatore non riuscita. Riprovare o riavviare l'applicazione.");
            }

        try(PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))){
            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                    System.out.println("Errore in scrittura del file.");
                    throw new RuntimeException("Riattivazione Giocatore non riuscita. Riprovare o riavviare l'applicazione.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Riattivazione Giocatore non riuscita. Riprovare o riavviare l'applicazione.");
        }
    }

    /**
     * Sovrascrive il file di testo (append del FileWriter settato a false) aggiornando il campo
     * relativo al parametro livello del {@link model.Giocatore} di cui viene fornito il nome utente.
     * @param nomeUtente Il nome utente del giocatore di cui aggiornare il livello.
     *
     * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
     * oppure in caso di errore di scrittura.
     */
    public void aggiornaLivello(String nomeUtente){
        ArrayList<String> righeAggiornate = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isEmpty()) {
                    continue;
                }
                String[] datiGiocatore = linea.split(";");

                if (datiGiocatore.length >= 7) {
                    String nomeAttuale = datiGiocatore[0];
                    if (nomeAttuale.equalsIgnoreCase(nomeUtente)) {
                        int livello = Integer.parseInt(datiGiocatore[4]);
                        String rigaModificata = datiGiocatore[0] + ";" + datiGiocatore[1] + ";" + datiGiocatore[2] + ";" + datiGiocatore[3] + ";" + (livello + 1) + ";" + datiGiocatore[5] + ";" + datiGiocatore[6] + ";" + datiGiocatore[7];
                        righeAggiornate.add(rigaModificata);
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        }
        catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Errore nell'aggiornamento dei dati dei giocatori");
            }
        try(PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))){
            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Errore nell'aggiornamento dei dati dei giocatori");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nell'aggiornamento dei dati dei giocatori");
        }
    }

    /**
     * Sovrascrive il file di testo (append del FileWriter settato a false) aggiornando il campo
     * relativo al parametro partiteVinte del {@link model.Giocatore} di cui viene fornito il nome utente.
     * @param nomeUtente Il nome utente del giocatore di cui aggiornare le partite vinte.
     *
     * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
     * oppure in caso di errore di scrittura.

     */
    public void aggiornaPartiteVinte(String nomeUtente){
        ArrayList<String> righeAggiornate = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isEmpty()) {
                    continue;
                }
                String[] datiGiocatore = linea.split(";");

                if (datiGiocatore.length >= 7) {
                    String nomeAttuale = datiGiocatore[0];
                    if (nomeAttuale.equalsIgnoreCase(nomeUtente)) {
                        int partiteVinte = Integer.parseInt(datiGiocatore[5]);
                        String rigaModificata = datiGiocatore[0] + ";" + datiGiocatore[1] + ";" + datiGiocatore[2] + ";" + datiGiocatore[3] + ";" + datiGiocatore[4] + ";" + (partiteVinte + 1) + ";" + datiGiocatore[6] + ";" + datiGiocatore[7];
                        righeAggiornate.add(rigaModificata);
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Errore nell'aggiornamento dei dati dei giocatori");
        }
        try(PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))){
            for (String riga : righeAggiornate) {
                    writer.println(riga);
            }
            if(writer.checkError()){
                    System.out.println("Errore in scrittura del file.");
                    throw new RuntimeException("Errore nell'aggiornamento dei dati dei giocatori");
            }
        } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Errore nell'aggiornamento dei dati dei giocatori");
        }
    }
    /**
     * Sovrascrive il file di testo (append del FileWriter settato a false) aggiornando il campo
     * relativo al parametro partitePerse del {@link model.Giocatore} di cui viene fornito il nome utente.
     * @param nomeUtente Il nome utente del giocatore di cui aggiornare le partite perse.
     *
     * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
     * oppure in caso di errore di scrittura.

     */
    public void aggiornaPartitePerse(String nomeUtente){
        ArrayList<String> righeAggiornate = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isEmpty()) {
                    continue;
                }
                String[] datiGiocatore = linea.split(";");

                if (datiGiocatore.length >= 7) {
                    String nomeAttuale = datiGiocatore[0];
                    if (nomeAttuale.equalsIgnoreCase(nomeUtente)) {
                        int partitePerse = Integer.parseInt(datiGiocatore[6]);
                        String rigaModificata = datiGiocatore[0] + ";" + datiGiocatore[1] + ";" + datiGiocatore[2] + ";" + datiGiocatore[3] + ";" + datiGiocatore[4] + ";" + datiGiocatore[5] + ";" + (partitePerse + 1) + ";" + datiGiocatore[7];
                        righeAggiornate.add(rigaModificata);
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nell'aggiornamento dei dati dei giocatori");
        }
        try(PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))){
            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Errore nell'aggiornamento dei dati dei giocatori");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nell'aggiornamento dei dati dei giocatori");
        }
    }
}
