package gui;

import controller.Controller;
import exception.CodiceModeratoreErratoException;
import exception.CodiceModeratoreVuotoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La classe HomeModeratore istanzia il form grafico che svolge il ruolo di menu principale
 * del {@link model.Moderatore} che ha fatto l'accesso al sistema. Mostra il nome utente del moderatore
 * e una tabella contenente l'elenco delle partite pubbliche presenti nel sistema, recuperate tramite il
 * {@link Controller}. Permette al moderatore di gestire una o più partite pubbliche, muovendosi liberamente
 * tra esse tramite il successivo form {@link SchermataPartitaModeratore}. Inoltre il moderatore
 * può accedere all'elenco di tutti i giocatori che sono stati banditi dal sistema, passando al form
 * {@link SchermataGiocatoriBanditi}. L'opzione di uscita dal sistema riporta al form precedente
 * (può essere {@link MenuLoginModeratore} o {@link MenuCreaNuovoModeratore}), non prima di aver gestito
 * tramite il controller l'aggiornamento dei dati relativi alle {@link model.PartitaPubblica}.
 */
public class HomeModeratore {
    /**
     * Il pulsante che, se premuto, attiva la procedura di logout facendo uscire il moderatore loggato dal
     * sistema e riportando l'utente al frame chiamante.
     */
    private JButton esciButton;
    /**
     * Il campo testuale che mostra il nome utente del moderatore loggato.
     */
    private JLabel labelNomeUtente;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelHomeModeratore;
    /**
     * La tabella per la visualizzazione delle informazioni principali delle partite pubbliche
     * presenti nel sistema
     */
    private JTable tabellaPartite;
    /**
     * Il pannello secondario a cui è associata la tabella delle partite.
     */
    private JScrollPane pannelloPartite;
    /**
     * Il pulsante che, se premuto, manda il giocatore nel nuovo form {@link SchermataGiocatoriBanditi}
     * dove visualizzare i profili dei giocatori banditi dal sistema.
     */
    private JButton listaGiocatoriBanditiButton;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameHomeModeratore;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà del frame di {@link MenuLoginModeratore}
     * o di {@link MenuCreaNuovoModeratore}).
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;

    /**
     * Istanzia l'interfaccia grafica per il menu principale del moderatore loggato, mostrandone
     * i nome utente a schermo e configurando gli ascoltatori di eventi legati agli elementi del form.
     * In particolare, utilizza il controller per ottenere le informazioni delle partite pubbliche presenti
     * nel sistema per riempire la tabella non modificabile. La selezione di una riga della tabella tramite
     * doppio click o click destro richiede al moderatore di confermare l'operazione inserendo il codice univoco
     * assegnato al moderatore, se il codice inserito è validato dal controller viene avviata la procedura di
     * registrazione di una nuova gestione della partita pubblica, catturando le possibili eccezioni. In caso di
     * esito positivo, il moderatore viene mandato nel nuovo form {@link SchermataPartitaModeratore}. Se il moderatore
     * loggato gestisce già la partita selezionata, non viene richiesto l'inserimento del codice moderatore ma c'è
     * direttamente il passaggio al nuovo form con il frame attuale che viene nascosto. Il pulsante lista giocatori
     * nasconde il frame attuale e apre la schermata per la visualizzazione dell'elenco dei giocatori banditi dal sistema
     * {@link SchermataGiocatoriLobby}. Il pulsante di uscita esegue la procedura di logout del moderatore loggato,
     * riportando l'utente al form precedente e lasciando al controller la verifica di come l'uscita del moderatore
     * abbia effetto sui dati delle partite pubbliche.
     *  @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     *  @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public HomeModeratore(JFrame frameChiamante, Controller controller){
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameHomeModeratore = new JFrame("HomeGiocatore");
        questoFrameHomeModeratore.setContentPane(panelHomeModeratore);
        questoFrameHomeModeratore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labelNomeUtente.setText(controller.getNomeUtenteModeratoreLoggato());
        String[] colonne = {"Partita", "Gioco", "Numero Partecipanti", "Capienza", "Moderatori Presenti"};
        Object[][] dati = new Object[controller.mostraPartitePubbliche().size()][5];
        for (String s : controller.mostraPartitePubbliche()) {
            String[] datiPartita = s.split(";");
            int indice = controller.mostraPartitePubbliche().indexOf(s);
            dati[indice][0] = datiPartita[0];
            dati[indice][1] = datiPartita[1];
            dati[indice][2] = datiPartita[2];
            dati[indice][3] = datiPartita[3];
            dati[indice][4] = datiPartita[4];
        }
        DefaultTableModel model = new DefaultTableModel(dati, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabellaPartite.setModel(model);
        questoFrameHomeModeratore.pack();
        questoFrameHomeModeratore.setVisible(true);

        esciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.moderatoreLoggatoEsceDalSistema();
                    JOptionPane.showMessageDialog(null, "Sei uscito con successo!");
                    frameChiamante.setVisible(true);
                    questoFrameHomeModeratore.dispose();
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
            }
        });

        tabellaPartite.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int partitaSelezionata = tabellaPartite.rowAtPoint(e.getPoint());
                if(partitaSelezionata >= 0)
                {
                    String codicePartitaSelezionata = (String)tabellaPartite.getValueAt(partitaSelezionata, 0);
                    if(controller.moderatoreLoggatoGestiscePartita(codicePartitaSelezionata)) {
                        SchermataPartitaModeratore framePartitaModeratore = new SchermataPartitaModeratore(questoFrameHomeModeratore, frameChiamante, controller, codicePartitaSelezionata);
                        questoFrameHomeModeratore.setVisible(false);
                    }
                    else{
                        int risposta = JOptionPane.showConfirmDialog(null, "Vuoi gestire questa partita?", null, JOptionPane.YES_NO_OPTION);
                        if(risposta == JOptionPane.YES_OPTION)
                        {
                            String codiceModeratoreInserito = JOptionPane.showInputDialog(null, "Inserisci il tuo codice moderatore:");
                            try {
                                controller.verificaCodiceModeratore(codiceModeratoreInserito);
                                controller.moderatoreLoggatoGestisceNuovaPartita(codicePartitaSelezionata);
                                JOptionPane.showMessageDialog(null, "Stai gestendo la partita!");
                                SchermataPartitaModeratore framePartitaModeratore = new SchermataPartitaModeratore (questoFrameHomeModeratore, frameChiamante ,controller, codicePartitaSelezionata);
                                questoFrameHomeModeratore.setVisible(false);
                            }
                            catch(NullPointerException ex){

                            }
                            catch(CodiceModeratoreVuotoException | CodiceModeratoreErratoException | RuntimeException ex)
                            {
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                }
            }
        });
        listaGiocatoriBanditiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SchermataGiocatoriBanditi frameGiocatoriBanditi = new SchermataGiocatoriBanditi(questoFrameHomeModeratore, controller);
                questoFrameHomeModeratore.setVisible(false);
            }
        });
    }
}
