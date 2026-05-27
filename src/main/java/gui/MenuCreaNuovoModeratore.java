package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Exception.*;

public class MenuCreaNuovoModeratore {
    private JPanel panelCreaNuovoModeratore;
    private JTextField campoPassword;
    private JTextField campoNomeUtente;
    private JTextField campoEmail;
    private JButton creaButton;
    private JFrame frameChiamante;
    private JFrame questoFrameCreaNuovoModeratore;
    private Controller controller;


    public MenuCreaNuovoModeratore(Controller controller, JFrame frameChiamante)
    {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameCreaNuovoModeratore = new JFrame("MenuCreaNuovoModeratore");
        questoFrameCreaNuovoModeratore.setContentPane(panelCreaNuovoModeratore);
        questoFrameCreaNuovoModeratore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameCreaNuovoModeratore.pack();
        questoFrameCreaNuovoModeratore.setVisible(true);
        frameChiamante.setVisible(false);


        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.creaNuovoModeratore(campoEmail.getText(), campoPassword.getText(), campoNomeUtente.getText());
                    JOptionPane.showMessageDialog(null, "Sei diventato un nuovo moderatore!" +
                            "\n Il tuo codice è il seguente: "+controller.getModeratoreLoggato().getCodiceAccessoModeratore()+
                            "Questo codice ti sarà richiesto ogni volta che eseguirai un'azione parte delle funzionalità dei moderatori.\n" +
                            "Conservalo con cura e utilizzalo con discrezione! Benvenuto!");
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
