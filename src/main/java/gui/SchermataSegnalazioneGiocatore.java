package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe SchermataSegnalazioneGiocatore istanzia il form grafico per la schermata in cui
 * {@link model.Giocatore} loggato può segnalare un altro giocatore durante l'attesa dell'inizio di
 * una {@link model.PartitaPubblica} (nel form {@link SchermataPartitaPubblica} o dopo la conclusione
 * della stessa (nel form {@link SchermataGiocatoriStorico}. Mostra un campo testuale dinamico che
 * documenta il nome utente del giocatore che si sta segnalando e un menu a tendina dove selezionare
 * un motivo per la {@link model.Segnalazione}. Offre le opzioni di inviare la segnalazione oppure
 * di annullare la formulazione della stessa e quindi tornare indietro al frame precedente.
 */
public class SchermataSegnalazioneGiocatore {
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameSegnalazioneGiocatore;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratta di uno tra {@link SchermataGiocatoriPartitaPubblica}
     * e {@link SchermataGiocatoriStorico}.
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;
    /**
     * Il menu a tendina per selezionare una delle motivazioni fornite per la segnalazione..
     */
    private JComboBox boxMotivo;
    /**
     * Il pulsane che, se premuto, annulla l'operazione di segnalazione e riporta il giocatore al frame precedente
     */
    private JButton annullaButton;
    /**
     * Il pulsante che, se premuto, avvia la procedura di registrazione nel sistema della segnalazione effettuata.
     */
    private JButton inviaSegnalazioneButton;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelSegnalazioneGiocatore;
    /**
     * Il campo testuale dinamico contenente il nome utente del giocatore che si sta segnalando.
     */
    private JLabel labelCodice;
    /**
     * Il nome utente del giocatore che si sta segnalando.
     */
    private String nomeGiocatoreSegnalato;
    /**
     * Il titolo del frame grafico che ha chiamato questa schermata (si tratta di uno tra
     * {@link SchermataGiocatoriPartitaPubblica} e {@link SchermataGiocatoriStorico}.
     */
    private String nomeFrameChiamante;

    /**
     * Istanzia l'interfaccia grafica per la schermata d'attesa dove il giocatore loggato può segnalare
     * un altro giocatore presente nella partita pubblica a cui sta partecipando oppure che si è appena
     * conclusa. In particolare, il costruttore imposta il layout visivo del form e associa ad ogni pulsante
     * il rispettivo ascoltatore. Il pulsante di invio segnalazione delega al {@link Controller} la registrazione
     * di tale segnalazione nel sistema, differenziando in base a quale frame ha chiamato quello attuale
     * per indirizzare il controller a ricercare nel giusto elenco di giocatori. Il frame attuale viene poi
     * liberato dalla memoria e viene reso nuovamente visibile quello precedente. Il pulsante di annullamento
     * si limita a riportare il giocatore al frame precedente, liberando le risorse del frame attuale.
     *
     * @param frameChiamante        Il frame a cui tornare in caso di selezione del pulsante di ritorno
     *                              al menu precedente.
     * @param nomeFrameChiamante    Il titolo del frame chiamante, necessario per differenziare la
     *                              chiamata al metodo del controller.
     * @param controller            Il controller logico del sistema, condiviso con le finestre successive.
     * @param nomeGiocatoreSegnalato Il nome utente del giocatore che si è deciso di segnalare.
     */
    public SchermataSegnalazioneGiocatore(JFrame frameChiamante, String nomeFrameChiamante, Controller controller, String nomeGiocatoreSegnalato){
        this.frameChiamante = frameChiamante;
        this.nomeFrameChiamante = nomeFrameChiamante;
        this.controller = controller;
        this.nomeGiocatoreSegnalato = nomeGiocatoreSegnalato;
        questoFrameSegnalazioneGiocatore = new JFrame("Schermata Partite Pubbliche");
        questoFrameSegnalazioneGiocatore.setContentPane(panelSegnalazioneGiocatore);
        questoFrameSegnalazioneGiocatore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labelCodice.setText("Stai segnalando il giocatore "+ nomeGiocatoreSegnalato);
        boxMotivo.addItem("Comportamento Scorretto");
        boxMotivo.addItem("Trucchi");
        boxMotivo.addItem("Inattività Giocatore");
        boxMotivo.addItem("Sabotaggio");
        boxMotivo.addItem("Altro");
        questoFrameSegnalazioneGiocatore.pack();
        questoFrameSegnalazioneGiocatore.setVisible(true);

        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                questoFrameSegnalazioneGiocatore.dispose();
            }
        });

        inviaSegnalazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (nomeFrameChiamante.equalsIgnoreCase("SchermataGiocatoriPartita")) {
                        controller.giocatoreLoggatoInviaSegnalazione(nomeGiocatoreSegnalato, boxMotivo.getSelectedItem().toString());
                        JOptionPane.showMessageDialog(null, "Segnalazione inviata.");
                        frameChiamante.setVisible(true);
                        questoFrameSegnalazioneGiocatore.dispose();
                    }
                    if (nomeFrameChiamante.equalsIgnoreCase("SchermataGiocatoriStorico")) {
                        controller.giocatoreLoggatoInviaSegnalazioneStorico(nomeGiocatoreSegnalato, boxMotivo.getSelectedItem().toString());
                        JOptionPane.showMessageDialog(null, "Segnalazione inviata.");
                        frameChiamante.setVisible(true);
                        questoFrameSegnalazioneGiocatore.dispose();
                    }
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
        }
        });
    }
}
