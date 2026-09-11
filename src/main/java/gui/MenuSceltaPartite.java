package gui;

import controller.Controller;
import exception.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe MenuSceltaPartite istanzia il form grafico per la scelta di una modalità
 * di gioco da parte del {@link model.Giocatore} loggato nel sistema. Offre le opzioni
 * di entrare in una {@link model.PartitaPubblica} a scelta, mandando il giocatore nel
 * form {@link MenuSelezionePartitaPubblica}, o di entrare in una {@link model.Lobby}
 * creata da un altro giocatore mediante l'inserimento del codice della lobby, oppure di
 * creare una nuova lobby personale, mandando il giocatore nel form {@link MenuCreazioneLobby}.
 * Consente anche di tornare al precedente form {@link HomeGiocatore}.
 */
public class MenuSceltaPartite {
    /**
     * Il pulsante che, se premuto, apre il form di selezione della partita pubblica a
     * cui partecipare {@link MenuSelezionePartitaPubblica}.
     */
    private JButton partitaPubblicaButton;
    /**
     * Il pulsante che, se premuto, apre una finestra modale per l'inserimento del codice di
     * accesso alla lobby a cui il giocatore intende unirsi.
     */
    private JButton entraInLobbyButton;
    /**
     * Il pulsante che, se premuto, apre il form di creazione di una nuova lobby da
     * parte del giocatore {@link MenuCreazioneLobby}.
     */
    private JButton creaLobbyButton;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelSceltaPartite;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameMenuSceltaPartite;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link HomeGiocatore}.
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;

    /**
     * Istanzia l'interfaccia grafica per il menu di selezione della modalità di gioco del
     * giocatore loggato. In particolare, il costruttore imposta il layout visivo del form e associa ad
     * ogni pulsante il rispettivo ascoltatore. Il pulsante di ingresso in una partita pubblica
     * apre il nuovo form di selezione della partita pubblica {@link MenuSelezionePartitaPubblica} e nasconde
     * il frame attuale. Il pulsante di ingresso in una lobby prima delega al controller di verificare se
     * il giocatore risulti online, in caso positivo apre una finestra modale per l'inserimento del codice
     * d'accesso alla lobby a cui il giocatore intende unirsi, è ancora il controller a verificare la validità
     * di tale codice. In caso di esito positivo di tali controlli, il giocatore viene mandato nel nuovo form
     * {@link SchermataPartecipanteLobby}, notificando inoltre se all'interno della lobby in cui è entrato il
     * giocatore è stata già avviata una {@link model.PartitaAmichevole}. Il pulsante di creazione lobby
     * delega al controller la verifica dello stato online del giocatore e in caso positivo apre il nuovo
     * form {@link MenuCreazioneLobby} e nasconde il frame attuale. Il pulsante indietro riporta al frame
     * precedente liberando le risorse utilizzate nel frame attuale.
     *  @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     *  @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public MenuSceltaPartite(JFrame frameChiamante, Controller controller) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        questoFrameMenuSceltaPartite = new JFrame("Menu Scelta Partite");
        questoFrameMenuSceltaPartite.setContentPane(panelSceltaPartite);
        questoFrameMenuSceltaPartite.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameMenuSceltaPartite.pack();
        questoFrameMenuSceltaPartite.setVisible(true);

        partitaPubblicaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuSelezionePartitaPubblica frameMenuSelezionePartitaPubblica = new MenuSelezionePartitaPubblica(questoFrameMenuSceltaPartite, controller);
                questoFrameMenuSceltaPartite.setVisible(false);
            }
        });
        entraInLobbyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!controller.getStatoGiocatoreLoggato()) {
                    JOptionPane.showMessageDialog(null, "sei offline, non puoi entrare in una lobby!");
                } else {
                    String s = JOptionPane.showInputDialog("Inserisci codice lobby");
                    try {
                        controller.giocatoreLoggatoEntraInLobby(s);
                        JOptionPane.showMessageDialog(null, "Sei entrato con successo nella lobby!");
                        if(!controller.getStatoLobbyAttuale()){
                            JOptionPane.showMessageDialog(null, "L'host ha già avviato una partita amichevole, dovrai aspettare l'inizio della prossima.");
                        }
                        SchermataPartecipanteLobby frameSchermataPartecipanteLobby = new SchermataPartecipanteLobby(questoFrameMenuSceltaPartite, controller);
                        questoFrameMenuSceltaPartite.setVisible(false);
                    } catch (NullPointerException ex) {

                    } catch (GiocatoreOfflineException | CodiceLobbyVuotoException | GiocatoreGiaInLobbyException |
                             AccessoLobbyNonRiuscitoException | CapienzaMassimaLobbyRaggiuntaException |
                             RuntimeException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        creaLobbyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!controller.getStatoGiocatoreLoggato())
                {
                    JOptionPane.showMessageDialog(null,"Sei offline, non puoi creare una lobby!");
                }
                else {
                    MenuCreazioneLobby frameMenuCreazioneLobby = new MenuCreazioneLobby(questoFrameMenuSceltaPartite, controller);
                    questoFrameMenuSceltaPartite.setVisible(false);
                }
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                questoFrameMenuSceltaPartite.dispose();
            }
        });
    }
}
