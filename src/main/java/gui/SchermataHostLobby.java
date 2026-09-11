package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe SchermataHostLobby istanzia il form grafico per la schermata di gestione lobby del
 * {@link model.Giocatore} loggato che ha creato una {@link model.Lobby}. Mostra un messaggio di
 * benvenuto e specifica il numero di giocatori presenti nella lobby e la capienza massima della stessa,
 * valori recuperati tramite il {@link Controller}. Permette al giocatore di visualizzare l'elenco dei partecipanti
 * alla lobby, mandandolo nel form {@link SchermataGiocatoriLobby}, di avviare una {@link model.PartitaAmichevole}
 * selezionando il videogioco dal menu a tendina (si assume che una partita amichevole possa essere avviata
 * anche con il solo host partecipante), oppure di eliminare la lobby dal sistema riportando il giocatore al
 * precedente form {@link MenuSceltaPartite}, che gli è stato passato dal vero frame chiamante, quello
 * di {@link MenuCreazioneLobby}. Una volta avviata la partita amichevole, una finestra modale permetterà
 * all'host della lobby di decidere unilateralmente quando terminarla.
 */
public class SchermataHostLobby {
    /**
     * Il campo testuale dove sono mostrati il numero di giocatori presenti nella lobby e la capienza
     * massima della stessa.
     */
    private JLabel labelNumeroGiocatori;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelHostLobby;
    /**
     * Il pulsante che, se premuto, manda il giocatore nel nuovo form {@link SchermataGiocatoriLobby}
     * dove visualizzare i profili dei partecipanti alla lobby.
     */
    private JButton listaPartecipantiButton;
    /**
     * Il pulsante che, se premuto, autorizza l'eliminazione della lobby dal sistema e quindi rimanda
     * il giocatore nel form precedente {@link MenuSceltaPartite}
     */
    private JButton eliminaLobbyButton;
    /**
     * Il pulsante che, se premuto, fa partire la procedura di creazione di una nuova partita amichevole.
     */
    private JButton avviaPartitaButton;
    /**
     * Il menu a tendina utilizzato per scegliere il videogioco su cui giocare la partita amichevole.
     */
    private JComboBox boxSelezioneVideogioco;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameSchermataHostLobby;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuSceltaPartite}.
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema.
     */
    private Controller controller;

    /**
     * Istanzia l'interfaccia grafica per la schermata che il giocatore loggato visualizza a seguito
     * della creazione di una lobby. In particolare, il costruttore imposta il layout visivo del form e associa
     * ad ogni pulsante il rispettivo ascoltatore. Utilizza il controller per ottenere le informazioni sul numero
     * di partecipanti presenti e sulla capienza della lobby per impostare il relativo campo testuale. Il
     * pulsante lista giocatori nasconde il frame attuale e apre la schermata per la visualizzazione dell'elenco
     * dei partecipanti alla lobby {@link SchermataGiocatoriLobby}. Il pulsante di avvio della partita amichevole
     * delega al controller l'istanza e la registrazione della nuova partita amichevole, recuperando il videogioco
     * selezionato dal menu a tendina e poi mostra al giocatore una finestra modale con l'opzione di terminare la partita.
     * Il pulsante di eliminazione della lobby chiede al giocatore ulteriore conferma e poi delega al controller la
     * registrazione di tale eliminazione, catturando potenziali errori di accesso o di scrittura su file di testo.
     * Il giocatore viene poi riportato al frame di {@link MenuSceltaPartite} mentre il frame attuale è liberato
     * dalla memoria.
     *  @param frameChiamante Il frame a cui tornare in caso di selezione del pulsante di ritorno al
     *                        menu precedente.
     * @param controller    Il controller logico del sistema.
     */
    public SchermataHostLobby(JFrame frameChiamante, Controller controller) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        questoFrameSchermataHostLobby = new JFrame("Schermata Lobby");
        questoFrameSchermataHostLobby.setContentPane(panelHostLobby);
        questoFrameSchermataHostLobby.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labelNumeroGiocatori.setText(controller.getNumeroPartecipantiLobbyGiocatoreLoggato() + "/" + controller.getCapienzaLobbyGiocatoreLoggato());
        questoFrameSchermataHostLobby.pack();
        questoFrameSchermataHostLobby.setVisible(true);
        boxSelezioneVideogioco.addItem("Brawl Stars");
        boxSelezioneVideogioco.addItem("Fortnite");
        boxSelezioneVideogioco.addItem("Clash Royale");
        boxSelezioneVideogioco.addItem("Rocket League");
        boxSelezioneVideogioco.addItem("EA Sports FC");
        boxSelezioneVideogioco.addItem("GTA 6");
        boxSelezioneVideogioco.addItem("League of Legends");
        boxSelezioneVideogioco.addItem("Apex Legends");
        boxSelezioneVideogioco.addItem("Rainbow Six Siege");
        boxSelezioneVideogioco.addItem("Fall Guys");
        boxSelezioneVideogioco.addItem("Call Of Duty: Warzone");
        boxSelezioneVideogioco.addItem("Minecraft");
        listaPartecipantiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SchermataGiocatoriLobby frameGiocatoriLobby = new SchermataGiocatoriLobby(questoFrameSchermataHostLobby, controller);
                questoFrameSchermataHostLobby.setVisible(false);
            }
        });
        eliminaLobbyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta = JOptionPane.showConfirmDialog(null, "Vuoi eliminare la lobby?", null, JOptionPane.YES_NO_OPTION);
                if (risposta == JOptionPane.YES_OPTION) {
                    {
                        try {
                            controller.giocatoreLoggatoEliminaLobby();
                            JOptionPane.showMessageDialog(null, "Lobby eliminata con successo!");
                            frameChiamante.setVisible(true);
                            questoFrameSchermataHostLobby.dispose();
                        }catch(RuntimeException ex){
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                        }
                    }
                }
            }
        });
        avviaPartitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.giocatoreLoggatoAvviaPartitaAmichevole((String) boxSelezioneVideogioco.getSelectedItem());
                    Object[] cancellaPartita = {"Termina Partita"};
                    int scelta = JOptionPane.showOptionDialog(null, "Hai avviato la partita!", "Partita Amichevole Avviata", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE, null, cancellaPartita, cancellaPartita[0]);
                    if (scelta == 0) {
                        controller.giocatoreLoggatoEliminaPartitaAmichevole();
                        JOptionPane.showMessageDialog(null, "Partita Terminata!");
                    }
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
            }
        });
    }
}
