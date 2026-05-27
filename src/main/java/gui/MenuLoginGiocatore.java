package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Exception.*;

public class MenuLoginGiocatore {
    private Controller controller;
    private JFrame frameChiamante;
    private JFrame questoFrameLoginGiocatore;
    private JPanel panelLoginGiocatore;
    private JTextField campoEmail;
    private JTextField campoPassword;
    private JButton creaNuovoGiocatoreButton;
    private JButton loginGiocatoreButton;

    public MenuLoginGiocatore(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameLoginGiocatore = new JFrame("MenuLoginGiocatore");
        questoFrameLoginGiocatore.setContentPane(panelLoginGiocatore);
        questoFrameLoginGiocatore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameLoginGiocatore.pack();
        questoFrameLoginGiocatore.setVisible(true);
        frameChiamante.setVisible(false);
        loginGiocatoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(controller.verificaLoginGiocatore(campoEmail.getText(), campoPassword.getText())){
                       JOptionPane.showMessageDialog(null, "Login Riuscito!");
                        // passa al frame successivo
                    }
                    else{
                       JOptionPane.showMessageDialog(null, "Nessun account trovato.");
                    }
                    }
                catch(EmailVuotaException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
                catch (PasswordVuotaException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
            }
        });
        creaNuovoGiocatoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuCreaNuovoGiocatore frameCreaGiocatore = new MenuCreaNuovoGiocatore(controller, questoFrameLoginGiocatore);
            }
        });
    }


}
