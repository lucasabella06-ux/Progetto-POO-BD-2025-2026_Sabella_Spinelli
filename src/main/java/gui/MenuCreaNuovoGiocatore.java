package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Exception.*;

public class MenuCreaNuovoGiocatore {
    private JPanel panelCreaNuovoGiocatore;
    private JTextField campoEmail;
    private JTextField campoPassword;
    private JTextField campoNomeUtente;
    private JButton creaButton;
    private JFrame frameChiamante;
    private JFrame questoFrameCreaNuovoGiocatore;
    private Controller controller;

    public MenuCreaNuovoGiocatore(Controller controller, JFrame frameChiamante)
    {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameCreaNuovoGiocatore = new JFrame("MenuCreaNuovoGiocatore");
        questoFrameCreaNuovoGiocatore.setContentPane(panelCreaNuovoGiocatore);
        questoFrameCreaNuovoGiocatore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameCreaNuovoGiocatore.pack();
        questoFrameCreaNuovoGiocatore.setVisible(true);
        frameChiamante.setVisible(false);


        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.creaNuovoGiocatore(campoEmail.getText(), campoPassword.getText(), campoNomeUtente.getText());
                    JOptionPane.showMessageDialog(null, "Account creato con successo! Benvenuto!");
                    // passa al frame successivo
                }
                catch(EmailVuotaException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
                catch (PasswordVuotaException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
                catch(NomeUtenteVuotoException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
                catch(EmailGiaEsistenteException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
                catch(EmailNonValidaException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
