package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exception.*;

/**
 * La classe MenuCreaNuovoGiocatore istanzia il form grafico delegato alla creazione di un nuovo
 * account {@link model.Giocatore}. Consente l'inserimento delle credenziali, le passa al
 * {@link Controller} per la verifica delle stesse e, in caso positivo, porta l'utente al form
 * {@link HomeGiocatore}. Offre inoltre la possibilità di ritornare al precedente form {@link MenuLoginGiocatore}.
 */
public class MenuCreaNuovoGiocatore {
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelCreaNuovoGiocatore;
    /**
     * Il campo di testo per l'inserimento dell'indirizzo email del nuovo giocatore.
     */
    private JTextField campoEmail;
    /**
     * Il campo di testo per l'inserimento della password del nuovo giocatore.
     */
    private JTextField campoPassword;
    /**
     * Il campo di testo per l'inserimento del nome utente del nuovo giocatore.
     */
    private JTextField campoNomeUtente;
    /**
     * Il pulsante che, se premuto, fa partire la procedura di validazione delle credenziali inserite
     * e di creazione di un nuovo giocatore.
     */
    private JButton creaButton;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuLoginGiocatore}.
     */
    private JFrame frameChiamante;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameCreaNuovoGiocatore;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;

    /**
     * Istanzia l'interfaccia grafica per la creazione di un nuovo giocatore. In particolare, il
     * costruttore imposta il layout visivo del form e associa ad ogni elemento interattivo il rispettivo
     * ascoltatore. Il pulsante di creazione raccoglie i dati inseriti nei campi di testo e li
     * passa al controller per la loro convalida, con finestre modali che gestiscono l'esito di
     * tale verifica, catturando inoltre le eventuali eccezioni; in caso di esito positivo, il
     * nuovo giocatore viene istanziato e salvato all'interno del sistema, poi viene chiamato il
     * successivo form {@link HomeGiocatore}.
     * Il pulsante indietro riporta al frame precedente liberando le risorse utilizzate nel frame attuale.
     * @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     * @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     */
    public MenuCreaNuovoGiocatore(Controller controller, JFrame frameChiamante)
    {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameCreaNuovoGiocatore = new JFrame("MenuCreaNuovoGiocatore");
        questoFrameCreaNuovoGiocatore.setContentPane(panelCreaNuovoGiocatore);
        questoFrameCreaNuovoGiocatore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameCreaNuovoGiocatore.pack();
        questoFrameCreaNuovoGiocatore.setVisible(true);


        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.creaNuovoGiocatore(campoEmail.getText(), campoPassword.getText(), campoNomeUtente.getText());
                    JOptionPane.showMessageDialog(null, "Account creato con successo! Benvenuto!");
                    HomeGiocatore frameHomeGiocatore = new HomeGiocatore(frameChiamante, controller);
                    questoFrameCreaNuovoGiocatore.dispose();

                }
                catch(EmailVuotaException | EmailNonValidaException | EmailGiaEsistenteException |
                      PasswordVuotaException | PasswordNonValidaException | NomeUtenteVuotoException |
                      NomeUtenteGiaEsistenteException | RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questoFrameCreaNuovoGiocatore.setVisible(false);
                frameChiamante.setVisible(true);
            }
        });
    }

}
