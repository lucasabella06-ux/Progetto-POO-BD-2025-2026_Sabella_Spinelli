package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exception.*;

/**
 * La classe MenuLoginGiocatore istanzia il form grafico delegato al login di un {@link model.Giocatore}.
 * Consente l'inserimento delle credenziali, le passa al {@link Controller} per la verifica delle stesse e,
 * in caso positivo, porta l'utente al form {@link HomeGiocatore}. Offre inoltre la possibilità
 * di creare un nuovo account giocatore o di ritornare al precedente form {@link HomeMenu}.
 */
public class MenuLoginGiocatore {
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
    private JFrame questoFrameLoginGiocatore;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelLoginGiocatore;
    /**
     * Il campo di testo per l'inserimento dell'indirizzo email del giocatore.
     */
    private JTextField campoEmail;
    /**
     * Il campo di testo per l'inserimento della password del giocatore.
     */
    private JTextField campoPassword;
    /**
     * Il pulsante che, se premuto, apre il form di creazione di un nuovo giocatore.
     */
    private JButton creaNuovoGiocatoreButton;
    /**
     * Il pulsante che, se premuto, fa partire la procedura di validazione delle credenziali inserite.
     */
    private JButton loginGiocatoreButton;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;

    /**
     * Istanzia l'interfaccia grafica per il login del giocatore. In particolare, il costruttore imposta il
     * layout visivo del form e associa ad ogni pulsante il rispettivo ascoltatore. Il pulsante di login
     * raccoglie i dati inseriti nei campi di testo e li passa al controller per la loro convalida, con
     * finestre modali che gestiscono l'esito di tale verifica, catturando inoltre le eventuali eccezioni;
     * in caso di esito positivo, viene chiamato il successivo form {@link HomeGiocatore}.
     * Il pulsante per la creazione di un nuovo giocatore nasconde il frame attuale e istanzia il form
     * successivo {@link MenuCreaNuovoGiocatore}.
     * Il pulsante indietro riporta al frame precedente liberando le risorse utilizzate nel frame attuale.
     * @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     * @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     */
    public MenuLoginGiocatore(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameLoginGiocatore = new JFrame("MenuLoginGiocatore");
        questoFrameLoginGiocatore.setContentPane(panelLoginGiocatore);
        questoFrameLoginGiocatore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameLoginGiocatore.pack();
        questoFrameLoginGiocatore.setVisible(true);
        loginGiocatoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(controller.verificaLoginGiocatore(campoEmail.getText(), campoPassword.getText())){
                       JOptionPane.showMessageDialog(null, "Login Riuscito!");
                        HomeGiocatore frameHomeGiocatore = new HomeGiocatore(frameChiamante, controller);
                        questoFrameLoginGiocatore.dispose();
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
                catch (GiocatoreBannatoException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
            }
        });
        creaNuovoGiocatoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuCreaNuovoGiocatore frameCreaGiocatore = new MenuCreaNuovoGiocatore(controller, questoFrameLoginGiocatore);
                questoFrameLoginGiocatore.setVisible(false);
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questoFrameLoginGiocatore.setVisible(false);
                frameChiamante.setVisible(true);
            }
        });
    }
}
