package gui;

import controller.Controller;
import exception.CodiceModeratoreErratoException;
import exception.CodiceModeratoreVuotoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * La classe SchermataPartitaModeratore istanzia il form grafico per la visualizzazione da parte del
 * {@link model.Moderatore} loggato nel sistema dell'elenco delle segnalazioni ricevute dal {@link model.Giocatore}
 * selezionato dall'elenco dei partecipanti alla {@link model.PartitaPubblica} gestita nel precedente form
 * {@link SchermataPartitaModeratore}. Permette al giocatore di visualizzare una tabella raffigurante informazioni
 * sulle segnalazioni ricevute dal giocatore selezionato con la possibilità di bandire il profilo del giocatore, inserendo
 * nella finestra modale il codice d'accesso univoco del moderatore. Il {@link Controller} si occupa di gestire le verifiche
 * sul codice inserito e, in caso positivo, di registrare il ban del giocatore dal sistema. In caso di ban di un giocatore
 * il form precedente {@link SchermataPartitaModeratore} verrà reistanziato per mostrare dati aggiornati. Altrimenti si può
 * tornare indietro al frame chiamante che è stato passato al costruttore.
 */
public class SchermataVisualizzazioneSegnalazioni {
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameSchermataVisualizzazioneSegnalazioni;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuSelezionePartitaPubblica}.
     */
    private JFrame frameChiamante;
    /**
     * Il frame chiamante del frame di {@link SchermataPartitaModeratore}, necessario per reistanziare il form precedente
     * in modo tale da aggiornare i dati mostrati all'interno di esso.
     */
    private JFrame frameHomeModeratore;
    /**
     * Il frame chiamante del frame di {@link HomeModeratore}, necessario per reistanziare il form precedente
     * in modo tale da aggiornare i dati mostrati all'interno di esso.
     */
    private JFrame frameChiamanteHomeModeratore;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelVisualizzazioneSegnalazioni;
    /**
     * Il campo testuale che mostra il nome utente del giocatore di cui sono presentate
     * le segnalazioni ricevute.
     */
    private JLabel labelGiocatoreSelezionato;
    /**
     * La tabella per la visualizzazione delle informazioni principali delle segnalazioni ricevute
     * dal giocatore selezionato
     */
    private JTable tabellaSegnalazioni;
    /**
     * Il pannello secondario a cui è associata la tabella delle segnalazioni ricevute dal giocatore
     * selezionato.
     */
    private JScrollPane pannelloSegnalazioni;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e istanzia di nuovo il form {@link SchermataPartitaPubblica}
     * affinchè mostri dati aggiornati.
     */
    private JButton bandisciGiocatoreButton;
    /**
     * La stringa contenente il nome utente del giocatore di cui sono mostrate
     * le segnalazioni ricevute.
     */
    private String giocatoreSelezionato;
    /**
     * La stringa contenente il codice identificativo della partita pubblica da cui è stato selezionato
     * il giocatore, necessario per reistanziare il form precedente in modo tale da aggiornare i dati
     * mostrati all'interno di esso.
     */
    private String partitaSelezionata;

    /**
     * Istanzia l'interfaccia grafica per il menu di visualizzazione delle segnalazioni ricevute dal giocatore
     * selezionato dal moderatore loggato nel form {@link SchermataPartitaModeratore}.
     * In particolare, il costruttore imposta il layout visivo del form e associa ad ogni pulsante il rispettivo
     * ascoltatore. Utilizza il controller per ottenere le informazioni delle segnalazioni ricevute dal giocatore
     * selezionato per riempire la tabella non modificabile.
     * Il pulsante per bandire il giocatore apre la finestra modale per l'inserimento del codice d'accesso univoco
     * del moderatore. In caso di esito positivo dei controlli su tale codice è avviata la procedura di gestione del ban,
     * delegata al controller, che aggiorna i dati relativi al giocatore interessato, poi il precedente form
     * {@link SchermataPartitaModeratore} viene reistanziato per mostrare dati aggiornati in linea con le verifiche
     * apportate dai metodi del controller. Il pulsante indietro riporta invece al frame precedente già istanziato
     * liberando le risorse utilizzate nel frame attuale.
     *
     * @param frameChiamante           Il frame a cui tornare in caso di selezione del pulsante di ritorno al
     *                                 menu precedente.
     * @param frameHomeModeratore      Il frame chiamante del frame di {@link SchermataPartitaModeratore}, necessario
     *                                 per reistanziare il form precedente in modo tale da aggiornare i dati
     *                                 mostrati all'interno di esso.
     * @param frameChiamanteHomeModeratore Il frame chiamante del frame di {@link HomeModeratore}, necessario per
     *                                     reistanziare il form precedente in modo tale da aggiornare i dati mostrati
     *                                     all'interno di esso.
     * @param controller               Il controller logico del sistema, condiviso con le finestre successive.
     * @param giocatoreSelezionato     Il nome utente del giocatore selezionato nel form {@link SchermataPartitaModeratore}.
     * @param partitaSelezionata Il codice identificativo della partita pubblica selezionata
     *                           nel form {@link HomeModeratore}.
     */
    public SchermataVisualizzazioneSegnalazioni (JFrame frameChiamante, JFrame frameHomeModeratore, JFrame frameChiamanteHomeModeratore, Controller controller, String giocatoreSelezionato, String partitaSelezionata) {
        this.frameChiamante = frameChiamante;
        this.frameHomeModeratore = frameHomeModeratore;
        this.frameChiamanteHomeModeratore = frameChiamanteHomeModeratore;
        this.controller = controller;
        this.giocatoreSelezionato = giocatoreSelezionato;
        this.partitaSelezionata = partitaSelezionata;
        questoFrameSchermataVisualizzazioneSegnalazioni = new JFrame("Schermata Partite Pubbliche");
        questoFrameSchermataVisualizzazioneSegnalazioni.setContentPane(panelVisualizzazioneSegnalazioni);
        questoFrameSchermataVisualizzazioneSegnalazioni.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labelGiocatoreSelezionato.setText("Segnalazioni ricevute da " + giocatoreSelezionato);

        String[] colonne = {"Autore", "Data", "Motivo"};
        ArrayList<String> segnalazioniRicevuteGiocatore = controller.moderatoreLoggatoVisualizzaSegnalazioniGiocatore(giocatoreSelezionato);
        Object[][] dati = new Object[segnalazioniRicevuteGiocatore.size()][3];
        int indice = 0;
        for (String s : segnalazioniRicevuteGiocatore) {
            String[] datiSegnalazione = s.split(";");
            dati[indice][0] = datiSegnalazione[0];
            dati[indice][1] = datiSegnalazione[2];
            dati[indice][2] = datiSegnalazione[3];
            indice++;
        }
        DefaultTableModel model = new DefaultTableModel(dati, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabellaSegnalazioni.setModel(model);
        questoFrameSchermataVisualizzazioneSegnalazioni.pack();
        questoFrameSchermataVisualizzazioneSegnalazioni.setVisible(true);
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                questoFrameSchermataVisualizzazioneSegnalazioni.dispose();
            }
        });

        bandisciGiocatoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codiceModeratore = JOptionPane.showInputDialog("Inserire Codice Moderatore per confermare l'azione:");
                try {
                    controller.verificaCodiceModeratore(codiceModeratore);
                    JOptionPane.showMessageDialog(null, "Hai bandito il giocatore!");
                    controller.moderatoreLoggatoBandisceGiocatore(giocatoreSelezionato);
                    SchermataPartitaModeratore frameSchermataPartitaModeratoreAggiornato = new SchermataPartitaModeratore(frameHomeModeratore, frameChiamanteHomeModeratore, controller, partitaSelezionata );
                    frameChiamante.dispose();
                    questoFrameSchermataVisualizzazioneSegnalazioni.dispose();
                }catch(NullPointerException ex){

                }
                catch(CodiceModeratoreVuotoException | CodiceModeratoreErratoException | RuntimeException ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
