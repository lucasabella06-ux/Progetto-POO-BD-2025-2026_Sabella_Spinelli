package gui;

import controller.Controller;
import exception.EmailVuotaException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe HomeMenu rappresenta il form di benvenuto del programma, da cui comincia
 * l'interfaccia grafica. Quando viene istanziata, istanzia il {@link Controller} da cui
 * dipenderà tutta la logica del programma, recuperando anche i dati di tutte le entità del
 * {@link model} necessarie per il sistema. Offre all'utente pulsanti che
 * portano alle aree di login {@link MenuLoginGiocatore} o {@link MenuLoginModeratore}.
 */
public class HomeMenu {
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel mainPanel;
    /**
     * Il pulsante che, se premuto, apre il form di login per i moderatori.
     */
    private JButton loginModeratoreButton;
    /**
     * Il pulsante che, se premuto, apre il form di login per i giocatori.
     */
    private JButton loginGiocatoreButton;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private static JFrame mainFrame = new JFrame("HomeMenu");

    /**
     * Istanzia l'interfaccia grafica del menu principale e gli ascoltatori di eventi
     * legati agli elementi del form. In particolare, il costruttore istanzia il {@link Controller}
     * del programma e recupera dai file di testo i dati fondamentali delle entità del
     * {@link model} per caricarli in memoria. Se il caricamento fallisce a causa di errori in
     * apertura dei file, viene mostrata una finestra modale di errore. Aggancia infine i listener
     * ai pulsanti con il fine di nascondere il frame principale per aprire una delle schermate di
     * login.
     */
    public HomeMenu() {
        controller = new Controller();
        try {
            controller.prendiGiocatori();
            controller.prendiModeratori();
            controller.generaClassifica();
            controller.prendiLobby();
            controller.prendiPartecipantiAlleLobby();
            controller.prendiPartitePubbliche();
            controller.prendiPartiteAmichevoli();
            controller.prendiPartecipantiAllePartitePubbliche();
            controller.prendiModeratoriExtraPartitePubbliche();
            controller.prendiSegnalazioni();
        } catch(RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
        }
            loginGiocatoreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MenuLoginGiocatore frameLoginGiocatore = new MenuLoginGiocatore(controller, mainFrame);
                    mainFrame.setVisible(false);
                }
            });
            loginModeratoreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MenuLoginModeratore frameLoginModeratore = new MenuLoginModeratore(controller, mainFrame);
                    mainFrame.setVisible(false);
                }
            });
    }

    /**
     * L'operazione di avvio del programma che lo lancia e rende visibile l'interfaccia grafica.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("HomeMenu");
        frame.setContentPane(new HomeMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
