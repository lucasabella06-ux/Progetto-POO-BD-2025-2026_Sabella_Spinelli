package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Exception.*;

public class MenuLoginModeratore {
    private JPanel panelLoginModeratore;
    private Controller controller;
    private JFrame frameChiamante;
    private JFrame questoFrameLoginModeratore;
    private JPanel panelLoginGiocatore;
    private JTextField campoEmail;
    private JTextField campoPassword;
    private JTextField campoCodiceModeratore;
    private JButton creaNuovoModeratoreButton;
    private JButton loginModeratoreButton;

    public MenuLoginModeratore(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameLoginModeratore = new JFrame("MenuLoginModeratore");
        questoFrameLoginModeratore.setContentPane(panelLoginModeratore);
        questoFrameLoginModeratore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameLoginModeratore.pack();
        questoFrameLoginModeratore.setVisible(true);
        frameChiamante.setVisible(false);
        loginModeratoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(controller.verificaLoginModeratore(campoEmail.getText(), campoPassword.getText(), campoCodiceModeratore.getText())){
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
                catch (CodiceModeratoreVuotoException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        creaNuovoModeratoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuCreaNuovoModeratore frameCreaModeratore = new MenuCreaNuovoModeratore(controller, questoFrameLoginModeratore);
            }
        });
    }
}
