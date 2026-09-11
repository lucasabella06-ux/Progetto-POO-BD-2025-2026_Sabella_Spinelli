package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exception.*;

/**
 * La classe MenuLoginModeratore istanzia il form grafico delegato al login di un {@link model.Moderatore}.
 * Consente l'inserimento delle credenziali, le passa al {@link Controller} per la verifica delle stesse e,
 * in caso positivo, porta l'utente al form {@link HomeModeratore}. Offre inoltre la possibilità
 * di creare un nuovo account moderatore o di ritornare al precedente form {@link HomeMenu}.
 */
public class MenuLoginModeratore {
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelLoginModeratore;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link HomeMenu}.
     */
    private JFrame frameChiamante;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameLoginModeratore;
    /**
     * Il campo di testo per l'inserimento dell'indirizzo email del moderatore.
     */
    private JTextField campoEmail;
    /**
     * Il campo di testo per l'inserimento della password del moderatore.
     */
    private JTextField campoPassword;
    /**
     * Il campo di testo per l'inserimento del codice segreto univocp del moderatore.
     */
    private JTextField campoCodiceModeratore;
    /**
     * Il pulsante che, se premuto, apre il form di creazione di un nuovo moderatore.
     */
    private JButton creaNuovoModeratoreButton;
    /**
     * Il pulsante che, se premuto, fa partire la procedura di validazione delle credenziali inserite.
     */
    private JButton loginModeratoreButton;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;

    /**
     * Istanzia l'interfaccia grafica per il login del moderatore. In particolare, il costruttore imposta il
     * layout visivo del form e associa ad ogni pulsante il rispettivo ascoltatore. Il pulsante di login
     * raccoglie i dati inseriti nei campi di testo e li passa al controller per la loro convalida, con
     * finestre modali che gestiscono l'esito di tale verifica, catturando inoltre le eventuali eccezioni;
     * in caso di esito positivo, viene chiamato il successivo form {@link HomeModeratore}.
     * Il pulsante per la creazione di un nuovo moderatore nasconde il frame attuale e istanzia il form
     * successivo {@link MenuCreaNuovoModeratore}.
     * Il pulsante indietro riporta al frame precedente liberando le risorse utilizzate nel frame attuale.
     * @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     * @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     */
    public MenuLoginModeratore(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameLoginModeratore = new JFrame("MenuLoginModeratore");
        questoFrameLoginModeratore.setContentPane(panelLoginModeratore);
        questoFrameLoginModeratore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameLoginModeratore.pack();
        questoFrameLoginModeratore.setVisible(true);
        loginModeratoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(controller.verificaLoginModeratore(campoEmail.getText(), campoPassword.getText(), campoCodiceModeratore.getText())){
                        JOptionPane.showMessageDialog(null, "Login Riuscito!");
                        HomeModeratore frameHomeModeratore = new HomeModeratore(questoFrameLoginModeratore, controller);
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
                questoFrameLoginModeratore.setVisible(false);
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questoFrameLoginModeratore.setVisible(false);
                frameChiamante.setVisible(true);
            }
        });
    }
}
