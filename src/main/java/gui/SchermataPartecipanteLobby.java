package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe SchermataPartecipanteLobby istanzia il form grafico per la schermata d'attesa del
 * {@link model.Giocatore} loggato entrato in una {@link model.Lobby} come partecipante. Mostra il nome utente
 * dell'host della lobby e un messaggio d'attesa specificando il numero di giocatori presenti nella lobby
 * e la capienza massima della stessa, valori recuperati tramite il {@link Controller}. Permette al giocatore
 * di visualizzare l'elenco dei partecipanti alla lobby, mandandolo nel form {@link SchermataGiocatoriLobby}, oppure di uscire
 * dalla lobby, riportando il giocatore al precedente form {@link MenuSceltaPartite}
 */
public class SchermataPartecipanteLobby {
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameSchermataPartecipanteLobby;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuSceltaPartite}.
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;
    /**
     * Il pulsante che, se premuto, fa uscire il giocatore dalla lobby e riporta il giocatore al
     * form {@link MenuSceltaPartite}
     */
    private JButton esciDallaLobbyButton;
    /**
     * Il pulsante che, se premuto, manda il giocatore nel nuovo form {@link SchermataGiocatoriLobby}
     * dove visualizzare i profili dei partecipanti alla lobby.
     */
    private JButton listaGiocatoriButton;
    /**
     * Il campo testuale dove è mostrato il nome utente dell'host della lobby.
     */
    private JLabel labelHostLobby;
    /**
     * Il campo testuale dove sono mostrati il numero di giocatori presenti nella lobby e la capienza
     * massima della stessa.
     */
    private JLabel labelNumeroGiocatori;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelSchermataLobby;

    /**
     * Istanzia l'interfaccia grafica per la schermata d'attesa di avvio della partita amichevole
     * all'interno della lobby a cui si è unito il giocatore loggato. In particolare, il costruttore
     * imposta il layout visivo del form e associa ad ogni pulsante il rispettivo ascoltatore.
     * Utilizza il controller per ottenere le informazioni sull'host, il numero di partecipanti presenti della lobby
     * e sulla capienza della stessa per impostare i relativi campi testuali. Il pulsante lista giocatori nasconde
     * il frame attuale e apre la schermata per la visualizzazione dell'elenco dei partecipanti alla lobby
     * {@link SchermataGiocatoriLobby}. Il pulsante di uscita dalla lobby chiede al giocatore
     * ulteriore conferma e poi delega al controller la registrazione di tale uscita, catturando potenziali
     * errori di accesso o di scrittura su file di testo. Il giocatore viene poi riportato al frame di
     * {@link MenuSceltaPartite} mentre il frame attuale e quello chiamante sono liberati dalla memoria.
     *  @param frameChiamante Il frame a cui tornare in caso di selezione del pulsante di ritorno al
     *                        menu precedente.
     * @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public SchermataPartecipanteLobby(JFrame frameChiamante, Controller controller){
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        questoFrameSchermataPartecipanteLobby = new JFrame("Schermata Lobby");
        questoFrameSchermataPartecipanteLobby.setContentPane(panelSchermataLobby);
        questoFrameSchermataPartecipanteLobby.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labelHostLobby.setText("Lobby di "+controller.getHostLobbyGiocatoreLoggato());
        labelNumeroGiocatori.setText(controller.getNumeroPartecipantiLobbyGiocatoreLoggato()+"/"+controller.getCapienzaLobbyGiocatoreLoggato());
        questoFrameSchermataPartecipanteLobby.pack();
        questoFrameSchermataPartecipanteLobby.setVisible(true);
        listaGiocatoriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SchermataGiocatoriLobby frameGiocatoriLobby = new SchermataGiocatoriLobby (questoFrameSchermataPartecipanteLobby, controller);
                questoFrameSchermataPartecipanteLobby.setVisible(false);
            }
        });
        esciDallaLobbyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta = JOptionPane.showConfirmDialog(null, "Vuoi uscire dalla lobby?", null, JOptionPane.YES_NO_OPTION);
                if(risposta == JOptionPane.YES_OPTION)
                {
                        try {
                            controller.giocatoreLoggatoEsceDallaLobby();
                            JOptionPane.showMessageDialog(null, "Sei uscito dalla lobby!");
                            frameChiamante.setVisible(true);
                            questoFrameSchermataPartecipanteLobby.dispose();
                        }catch(RuntimeException ex){
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                        }
                }
            }
        });
    }
}
