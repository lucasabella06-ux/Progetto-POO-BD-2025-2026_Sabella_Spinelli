package implementazioneTextDAO;

import dao.ModeratoriDAO;

import java.io.*;
import java.util.ArrayList;

/**
 * La classe ModeratoriDAOImplText è un implementazione di {@link ModeratoriDAO} che utilizza
 * file di testo come sistema di persistenza dei dati.
 */
public class ModeratoriDAOImplText implements ModeratoriDAO {
    /**
     Il percorso o il nome del file di testo dove sono memorizzati i dati relativi ai {@link model.Moderatore}
     */
    String nomeFile;

    /**
     * Istanzia un nuovo oggetto ModeratoriDAOImplText fornendo uno specifico file di testo.
     *
     * @param nomeFile Il percorso o il nome del file di testo dove sono memorizzati i dati relativi ai {@link model.Giocatore}
     */
    public ModeratoriDAOImplText(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    /**
     * Riempie le liste fornite come parametri con i dati dei {@link model.Moderatore} presenti nel sistema.
     * @param listaNomi         Lista in cui inserire i nomi utente recuperati.
     * @param listaEmail        Lista in cui inserire le mail recuperate.
     * @param listaPassword     Lista in cui inserire le password recuperate.
     * @param listaCodiciModeratori Lista in cui inserire i codici d'accesso dei
     *                              {@link model.Moderatore} recuperati.
     * @param listaOrariInizioAttivita   Lista in cui inserire gli orari d'inizio attività
     *                              dei {@link model.Moderatore} recuperati.
     * @param listaOrariFineAttivita     Lista in cui inserire gli orari di fine attività
     *                              dei {@link model.Moderatore} recuperati.
     *
     * @throws RuntimeException Se la lettura del file non riesce.
     */
    public void generaModeratori(ArrayList<String> listaNomi, ArrayList<String> listaEmail, ArrayList<String> listaPassword,
                                  ArrayList<String> listaCodiciModeratori, ArrayList<Integer> listaOrariInizioAttivita,
                                  ArrayList<Integer> listaOrariFineAttivita){
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String rigaModeratore;
            while ((rigaModeratore = reader.readLine()) != null) {
                String[] datiModeratore = rigaModeratore.split(";");
                if (datiModeratore.length != 6) continue;
                listaNomi.add(datiModeratore[0]);
                listaEmail.add(datiModeratore[1]);
                listaPassword.add(datiModeratore[2]);
                listaCodiciModeratori.add(datiModeratore[3]);
                listaOrariInizioAttivita.add(Integer.parseInt(datiModeratore[4]));
                listaOrariFineAttivita.add(Integer.parseInt(datiModeratore[5]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile caricare dati moderatori, riavviare l'applicazione.");
        }

    }

    /**
     * Scrive in coda al file di testo (append del FileWriter settato a true) i dati
     * di un nuovo moderatore.
     * @param nomeUtente  Il nome utente del {@link model.Moderatore}.
     * @param email       L'email del {@link model.Moderatore}.
     * @param password    La password del {@link model.Moderatore}.
     * @param codiceModeratore     Il codice d'accesso univoco del {@link model.Moderatore}.
     * @param orarioInizioAttivita L'orario d'inizio attività del {@link model.Moderatore}.
     * @param orarioFineAttivita   L'orario di fine attività del {@link model.Moderatore}.
     *
     * @throws RuntimeException Se c'è un errore di apertura o di scrittura sul file.
     */
    public void aggiungiModeratore(String nomeUtente, String email, String password, String codiceModeratore, int orarioInizioAttivita, int orarioFineAttivita ){
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile, true)))){
            writer.print("\n"+nomeUtente + ";" + email + ";" + password + ";" + codiceModeratore + ";"+orarioInizioAttivita+";"+orarioFineAttivita);
            if(writer.checkError()){
                System.out.println("Errore nella scrittura del file.");
                throw new RuntimeException("Impossibile inserire dati del moderatore, riprovare o riavviare l'applicazione.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile inserire dati del moderatore, riprovare o riavviare l'applicazione.");
        }
    }
}
