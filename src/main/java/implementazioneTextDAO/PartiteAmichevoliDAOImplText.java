package implementazioneTextDAO;

import dao.PartiteAmichevoliDAO;

import java.io.*;
import java.util.ArrayList;

/**
 * La classe LobbyDAOImplText è un implementazione di {@link PartiteAmichevoliDAO} che utilizza
 * file di testo come sistema di persistenza dei dati.
 */
public class PartiteAmichevoliDAOImplText implements PartiteAmichevoliDAO{
    /**
     Il percorso o il nome del file di testo dove sono memorizzati i dati relativi alle {@link model.PartitaAmichevole}
     */
    String nomeFile;

    /**
     * Istanzia un nuovo oggetto PartiteAmichevoliDAOImplText fornendo uno specifico file di testo.
     *
     * @param nomeFile Il percorso o il nome del file di testo dove sono memorizzati i dati relativi alle
     * {@link model.PartitaAmichevole}
     */
    public PartiteAmichevoliDAOImplText(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    /**
     * Legge il file di testo riga per riga, utilizzando il carattere ";" per suddividere i dati
     * formattati della {@link model.PartitaAmichevole} presente sulla riga e popolando le liste fornite.
     * Se la riga letta non è ben formattata o difetta di qualche dato, viene saltata.
     * @param listaCodiciLobby      Lista in cui inserire i codici delle lobby recuperati.
     * @param listaCodiciPartiteAmichevoli Lista in cui inserire i codici delle partite amichevoli
     *                                     recuperate.
     * @param listaVideogiochi             Lista in cui inserire i videogiochi su cui si giocano
     *                                     le partite amichevoli recuperate.
     * @param listaCapienze                Lista in cui inserire le capienze delle partite amichevoli
     *                                     recuperate.
     *
     * @throws RuntimeException Se la lettura del file non riesce.
     */
    public void generaPartiteAmichevoli(ArrayList<String> listaCodiciLobby, ArrayList<String> listaCodiciPartiteAmichevoli, ArrayList<String> listaVideogiochi, ArrayList<Integer> listaCapienze){
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String rigaPartitaAmichevole;
            while ((rigaPartitaAmichevole = reader.readLine()) != null) {
                String[] datiPartitaAmichevole = rigaPartitaAmichevole.split(";");
                if (datiPartitaAmichevole.length != 4) continue;
                listaCodiciLobby.add(datiPartitaAmichevole[0]);
                listaCodiciPartiteAmichevoli.add(datiPartitaAmichevole[1]);
                listaVideogiochi.add(datiPartitaAmichevole[2]);
                listaCapienze.add(Integer.parseInt(datiPartitaAmichevole[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile caricare dati delle partite amichevoli, riavviare l'applicazione");
        }
    }

    /**
     * Scrive in coda al file di testo (append del FileWriter settato a true) i dati
     * di una nuova partita amichevole, specificando il codice identificativo della
     * lobby che l'ha generata.
     * @param codiceLobby             Il codice identificativo della {@link model.Lobby} che ha generato
     *                                la {@link model.PartitaAmichevole}.
     * @param codicePartitaAmichevole Il codice identificativo della {@link model.PartitaAmichevole}.
     * @param videogioco              Il videogioco su cui si gioca la {@link model.PartitaAmichevole}
     * @param capienza                La capienza della {@link model.PartitaAmichevole}.
     *
     * @throws RuntimeException Se c'è un errore di apertura o di scrittura sul file.
     */
    public void aggiungiPartitaAmichevole(String codiceLobby, String codicePartitaAmichevole, String videogioco, int capienza){
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile, true)))){
            writer.print("\n"+codiceLobby + ";" + codicePartitaAmichevole + ";" + videogioco+";"+capienza);
            if(writer.checkError()){
                System.out.println("Errore nella scrittura del file.");
                throw new RuntimeException("Impossibile inserire dati della partita amichevole, riprovare o riavviare l'applicazione.");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossibile inserire dati della partita amichevole, riprovare o riavviare l'applicazione.");
        }
    }

    /**
     * Sovrascrive il file di testo (append del FileWriter settato a false) eliminando
     * la riga relativa alla {@link model.PartitaAmichevole} che è stata terminata
     * all'interno della {@link model.Lobby} che l'ha generata, di cui viene fornito
     * il codice identificativo.
     * @param codiceLobby Il codice della {@link model.Lobby} a cui appartiene la
     *                    partita amichevole da rimuovere.
     *
     * @throws RuntimeException In caso di mancata apertura del file di lettura o di scrittura,
     * oppure in caso di errore di scrittura.
     */
    public void rimuoviPartitaAmichevole(String codiceLobby){
        ArrayList<String> righeAggiornate = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))){
            String linea;
            while ((linea = reader.readLine()) != null) {
                if(linea.isEmpty()){
                    continue;
                }
                String[] datiPartitaAmichevole = linea.split(";");
                if (datiPartitaAmichevole.length >= 4) {
                    String codiceAttuale = datiPartitaAmichevole[0];
                    if (codiceAttuale.equalsIgnoreCase(codiceLobby)) {
                        continue;
                    } else {
                        righeAggiornate.add(linea);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, false))) {
            for (String riga : righeAggiornate) {
                writer.println(riga);
            }
            if(writer.checkError()){
                System.out.println("Errore in scrittura del file.");
                throw new RuntimeException("Eliminazione partita amichevole non riuscita. Riprovare o riavviare l'applicazione.");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eliminazione partita amichevole non riuscita. Riprovare o riavviare l'applicazione.");
        }
    }
}
