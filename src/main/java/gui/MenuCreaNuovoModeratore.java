package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exception.*;

/**
 * La classe MenuCreaNuovoModeratore istanzia il form grafico delegato alla creazione di un nuovo
 * account {@link model.Moderatore}. Consente l'inserimento delle credenziali, le passa al
 * {@link Controller} per la verifica delle stesse e, in caso positivo, porta l'utente al form
 * {@link HomeModeratore}. Offre inoltre la possibilità di ritornare al precedente form {@link MenuLoginModeratore}.
 */
public class MenuCreaNuovoModeratore {
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelCreaNuovoModeratore;
    /**
     * Il campo di testo per l'inserimento della password del nuovo moderatore.
     */
    private JTextField campoPassword;
    /**
     * Il campo di testo per l'inserimento del nome utente del nuovo moderatore.
     */
    private JTextField campoNomeUtente;
    /**
     * Il campo di testo per l'inserimento dell'indirizzo email del nuovo moderatore.
     */
    private JTextField campoEmail;
    /**
     * Il pulsante che, se premuto, fa partire la procedura di validazione delle credenziali inserite
     * e di creazione di un nuovo moderatore.
     */
    private JButton creaButton;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;
    /**
     * Il selettore per l'impostazione dell'orario di inizio attività del nuovo moderatore.
     */
    private JSlider sliderOrarioInizio;
    /**
     * Il selettore per l'impostazione dell'orario di inizio attività del nuovo moderatore.
     */
    private JSlider sliderOrarioFine;
    /**
     * Il mampo testuale dinamico per mostrare il valore di sliderOrarioInizio.
     */
    private JLabel labelOrarioInizio;
    /**
     * Il mampo testuale dinamico per mostrare il valore di sliderOrarioInizio.
     */
    private JLabel labelOrarioFine;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuLoginGiocatore}.
     */
    private JFrame frameChiamante;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameCreaNuovoModeratore;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;


    /**
     * Istanzia l'interfaccia grafica per la creazione di un nuovo moderatore. In particolare, il
     * costruttore imposta il layout visivo del form e associa ad ogni elemento interattivo il rispettivo
     * ascoltatore. I selettori a scorrimento sono inizializzati in modo tale da avere valori minimi
     * e massimi prefissati, e che ogni scorrimento a destra o sinistra dell'indicatore valga 1.
     * Ad ogni cambiamento di stato degli slider i campi testuali legati ad essi variano dinamicamente.
     * Il pulsante di creazione raccoglie i dati inseriti nei campi di testo e li passa al controller
     * per la loro convalida, con finestre modali che gestiscono l'esito di tale verifica, catturando
     * inoltre le eventuali eccezioni; in caso di esito positivo, il nuovo moderatore viene istanziato
     * e salvato all'interno del sistema, poi viene chiamato il successivo form {@link HomeModeratore}.
     * Il pulsante indietro riporta al frame precedente liberando le risorse utilizzate nel frame attuale.
     * @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     * @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     */
    public MenuCreaNuovoModeratore(Controller controller, JFrame frameChiamante)
    {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameCreaNuovoModeratore = new JFrame("MenuCreaNuovoModeratore");
        questoFrameCreaNuovoModeratore.setContentPane(panelCreaNuovoModeratore);
        questoFrameCreaNuovoModeratore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BoundedRangeModel model = new DefaultBoundedRangeModel(1, 0, 1, 12);
        sliderOrarioInizio.setModel(model);
        sliderOrarioInizio.setValue(sliderOrarioInizio.getMinimum());
        labelOrarioInizio.setText("Orario Inizio Attività " + sliderOrarioInizio.getValue());
        model = new DefaultBoundedRangeModel(13, 0, 13, 24);
        sliderOrarioFine.setModel(model);
        sliderOrarioFine.setValue(sliderOrarioFine.getMinimum());
        labelOrarioFine.setText("Orario Fine Attività " + sliderOrarioFine.getValue());
        questoFrameCreaNuovoModeratore.pack();
        questoFrameCreaNuovoModeratore.setVisible(true);


        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.creaNuovoModeratore(campoEmail.getText(), campoPassword.getText(), campoNomeUtente.getText(), sliderOrarioInizio.getValue(), sliderOrarioFine.getValue());
                    JOptionPane.showMessageDialog(null, "Sei diventato un nuovo moderatore!" +
                            "\n Il tuo codice è il seguente: "+controller.getCodiceModeratoreLoggato()+
                            "\nQuesto codice ti sarà richiesto ogni volta che eseguirai un'azione parte delle funzionalità dei moderatori.\n" +
                            "Conservalo con cura e utilizzalo con discrezione! Benvenuto!");
                    HomeModeratore frameHomeModeratore = new HomeModeratore(frameChiamante, controller);
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
                questoFrameCreaNuovoModeratore.setVisible(false);
                frameChiamante.setVisible(true);
            }
        });

        sliderOrarioInizio.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelOrarioInizio.setText("Orario Inizio Attività: "+ sliderOrarioInizio.getValue());
            }
        });

        sliderOrarioFine.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelOrarioFine.setText("Orario Inizio Attività: "+ sliderOrarioFine.getValue());
            }
        });
    }
}
