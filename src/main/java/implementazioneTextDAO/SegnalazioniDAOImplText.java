package implementazioneTextDAO;

import dao.SegnalazioniDAO;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * La classe SegnalazioniDAOImplText è un implementazione di {@link SegnalazioniDAO} che utilizza
 * file di testo come sistema di persistenza dei dati.
 */
public class SegnalazioniDAOImplText implements SegnalazioniDAO{
    /**
     Il percorso o il nome del file di testo dove sono memorizzati i dati relativi alle {@link model.Segnalazione}
     */
    private String nomeFile;

    /**
     * Istanzia un nuovo oggetto SegnalazioniDAOImplText fornendo uno specifico file di testo.
     *
     * @param nomeFile Il percorso o il nome del file di testo dove sono memorizzati i dati relativi alle
     * {@link model.Segnalazione}
     */
    public SegnalazioniDAOImplText(String nomeFile){
        this.nomeFile = nomeFile;
    }

    /**
     * Legge il file di testo riga per riga, utilizzando il carattere ";" per suddividere i dati
     * formattati della {@link model.Segnalazione} presente sulla riga e popolando le liste fornite.
     * Se la riga letta non è ben formattata o difetta di qualche dato, viene saltata.
     * @param listaAutoriSegnalazioni Lista in cui inserire i nomi utente dei {@link model.Giocatore} che hanno
     *                                effettuato le {@link model.Segnalazione}.
     * @param listaGiocatoriSegnalati Lista in cui inserire i nomi utente dei {@link model.Giocatore} che verso
     *                                cui sono rivolte le {@link model.Segnalazione}.
     * @param listaDate               Lista in cui inserire le date in cui sono state effettuate le
     *                                {@link model.Segnalazione}.
     * @param listaMotivi             Lista in cui inserire i motivi per cui sono state effettuate le
     *                                {@link model.Segnalazione}.
     *
     * @throws RuntimeException Se la lettura del file non riesce o se la stringa relativa alla data della segnalazione
     *                          non è ben formattata.
     */
    public void generaSegnalazioni(ArrayList<String> listaAutoriSegnalazioni, ArrayList<String> listaGiocatoriSegnalati, ArrayList<Date> listaDate, ArrayList<String> listaMotivi){
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String rigaSegnalazione;
            SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            while ((rigaSegnalazione = reader.readLine()) != null) {
                String[] datiSegnalazione = rigaSegnalazione.split(";");
                if (datiSegnalazione.length != 4) continue;
                listaAutoriSegnalazioni.add(datiSegnalazione[0]);
                listaGiocatoriSegnalati.add(datiSegnalazione[1]);
                listaDate.add(parser.parse(datiSegnalazione[2]));
                listaMotivi.add(datiSegnalazione[3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile caricare dati segnalazioni. Riavviare l'applicazione");
        } catch (ParseException e) {
            throw new RuntimeException("Impossibile caricare dati segnalazioni. Riavviare l'applicazione");
        }
    }

    /**
     * Scrive in coda al file di testo (append del FileWriter settato a true) i dati
     * di una nuova segnalazione, specificandone il nome utente del giocatore che l'ha
     * effettuata e di quello che l'ha ricevuta.
     * @param autoreSegnalazione Il {@link model.Giocatore} che ha effettuato la {@link model.Segnalazione}.
     * @param giocatoreSegnalato Il {@link model.Giocatore} verso cui è rivolta la {@link model.Segnalazione}.
     * @param data               La data esatta in cui è stata registrata la {@link model.Segnalazione}.
     * @param motivo             Il motivo per cui è stata effettuata la {@link model.Segnalazione}.
     *
     * @throws RuntimeException Se c'è un errore di apertura o di scrittura sul file.
     */
    public void aggiungiSegnalazione(String autoreSegnalazione, String giocatoreSegnalato, Date data, String motivo){
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile, true)))){
            writer.print("\n"+autoreSegnalazione + ";" + giocatoreSegnalato + ";" + data.toString() + ";" + motivo);
            if(writer.checkError()){
                System.out.println("Errore nella scrittura del file.");
                throw new RuntimeException("Impossibile inserire dati della segnalazione, riprovare o riavviare l'applicazione.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile inserire dati della segnalazione, riprovare o riavviare l'applicazione.");
        }
    }
}
