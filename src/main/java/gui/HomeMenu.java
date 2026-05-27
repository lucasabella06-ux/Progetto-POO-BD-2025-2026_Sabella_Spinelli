package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeMenu {
    private JPanel mainPanel;
    private JButton loginModeratoreButton;
    private JButton loginGiocatoreButton;
    private Controller controller;
    private static JFrame mainFrame = new JFrame("HomeMenu");

    public HomeMenu() {
        controller = new Controller();
        controller.generaGiocatori();
        controller.generaModeratori();
        controller.generaClassifica();
        controller.generaLobby();
        controller.generaPartitePubbliche();
        controller.generaPartiteAmichevoli();
        controller.aggiungiGiocatoriAlleLobby();
        controller.aggiungiGiocatoriAllePartite();
        controller.generaRisultatoPartita();
        loginGiocatoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuLoginGiocatore frameLoginGiocatore = new MenuLoginGiocatore(controller, mainFrame);
            }
        });
        loginModeratoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuLoginModeratore frameLoginModeratore = new MenuLoginModeratore(controller, mainFrame);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomeMenu");
        frame.setContentPane(new HomeMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
