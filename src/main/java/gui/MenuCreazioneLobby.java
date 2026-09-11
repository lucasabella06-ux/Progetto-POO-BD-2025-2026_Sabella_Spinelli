package gui;

import controller.Controller;
import exception.CodiceLobbyGiaPresenteException;
import exception.CodiceLobbyVuotoException;
import exception.GiocatoreGiaInLobbyException;
import exception.GiocatoreOfflineException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe MenuCreazioneLobby istanzia il form grafico delegato alla creazione di una nuova
 * {@link model.Lobby} da parte del {@link model.Giocatore} loggato. Consente l'inserimento del codice di accesso
 * alla lobby e di scegliere la capienza massima della stessa tramite il selettore a scorrimento. Chiama il
 * {@link Controller} per la verifica della validità del codice inserito e dello stato online/offline del giocatore.
 * In caso positivo, porta l'utente al form {@link SchermataHostLobby}, liberando il frame attuale poichè il giocatore
 * non vi tornerà. Offre inoltre la possibilità di ritornare al precedente form {@link MenuSceltaPartite}.
 */
public class MenuCreazioneLobby {
    /**
     * Il campo testuale dove inserire il codice d'accesso alla lobby
     */
    private JTextField campoCodice;
    /**
     * Il selettore a scorrimento per impostare la capienza massima della lobby
     */
    private JSlider sliderCapienza;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelCreazioneLobby;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;
    /**
     * Il pulsante che, se premuto, fa partire la procedura di validazione del codice inserito
     * e di creazione di una nuova lobby.
     */
    private JButton creaLobbyButton;
    /**
     * Il campo testuale dove viene mostrato il valore del selettore della capienza.
     */
    private JLabel labelCapienzaMax;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameCreazioneLobby;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuSceltaPartite}.
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;

    /**
     * Istanzia l'interfaccia grafica per la creazione di una nuova lobby. In particolare, il
     * costruttore imposta il layout visivo del form e associa ad ogni elemento interattivo il rispettivo
     * ascoltatore. Il selettore a scorrimento è inizializzato in modo tale da avere valori minimi
     * e massimi prefissati, e che ogni scorrimento a destra o sinistra dell'indicatore valga 1.
     * Ad ogni cambiamento di stato del selettore i campi testuali legato ad esso varia dinamicamente.
     * Il pulsante di creazione raccoglie il codice inserito nel campo di testo e lo passa al controller
     * per la convalida, con finestre modali che gestiscono l'esito di tale verifica, catturando
     * inoltre le eventuali eccezioni; in caso di esito positivo, la nuova lobby viene istanziata
     * e salvata all'interno del sistema, poi viene chiamato il successivo form {@link SchermataHostLobby}.
     * Il pulsante indietro riporta al frame precedente liberando le risorse utilizzate nel frame attuale.
     *
     * @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     * @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public MenuCreazioneLobby(JFrame frameChiamante, Controller controller) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        questoFrameCreazioneLobby = new JFrame("Schermata Creazione Lobby");
        questoFrameCreazioneLobby.setContentPane(panelCreazioneLobby);
        questoFrameCreazioneLobby.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameCreazioneLobby.pack();
        BoundedRangeModel model = new DefaultBoundedRangeModel(2, 0, 2, 99);
        sliderCapienza.setModel(model);
        sliderCapienza.setValue(sliderCapienza.getMinimum());
        labelCapienzaMax.setText("Capienza selezionata: " + sliderCapienza.getValue());
        questoFrameCreazioneLobby.setVisible(true);

        sliderCapienza.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelCapienzaMax.setText("Capienza selezionata: "+ sliderCapienza.getValue());
            }
        });
        creaLobbyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.giocatoreLoggatoCreaLobby(campoCodice.getText(), sliderCapienza.getValue());
                    JOptionPane.showMessageDialog(null, "Lobby creata con successo!");
                    SchermataHostLobby frameHostLobby = new SchermataHostLobby (frameChiamante, controller);
                    questoFrameCreazioneLobby.dispose();
                }
                catch(GiocatoreOfflineException | CodiceLobbyVuotoException | CodiceLobbyGiaPresenteException |
                      GiocatoreGiaInLobbyException | RuntimeException ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                questoFrameCreazioneLobby.dispose();
            }
        });
    }
}
