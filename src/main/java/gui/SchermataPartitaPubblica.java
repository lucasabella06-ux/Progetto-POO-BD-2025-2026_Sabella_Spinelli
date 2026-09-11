package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe SchermataPartitaPubblica istanzia il form grafico per la schermata d'attesa del
 * {@link model.Giocatore} loggato entrato in una {@link model.PartitaPubblica}. Mostra un messaggio
 * d'attesa specificando il numero di giocatori presenti in partita e quelli necessari per il suo inizio,
 * valori recuperati tramite il {@link Controller}. Permette al giocatore di visualizzare l'elenco dei
 * partecipanti alla partita, mandandolo nel form {@link SchermataGiocatoriPartitaPubblica}, oppure di uscire
 * dalla partita, gestendo la corretta chiusura dei frame, non riportando il giocatore al frame precedente
 * ma direttamente a quello di {@link MenuSceltaPartite}.
 */
public class SchermataPartitaPubblica {
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameSchermataPartita;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuSelezionePartitaPubblica}.
     */
    private JFrame frameChiamante;
    /**
     * Il frame grafico a cui il giocatore tornerà in caso di uscita dalla partita
     */
    private JFrame frameSceltaPartite;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelPartitaPubblica;
    /**
     * Il campo testuale contenente il numero di giocatori presenti in partita e quello dei giocatori
     * necessari per l'avvio di essa.
     */
    private JLabel labelNumeroGiocatori;
    /**
     * Il pulsante che, se premuto, manda il giocatore nel nuovo form {@link SchermataGiocatoriPartitaPubblica}
     * dove visualizzare i profili dei partecipanti alla partita.
     */
    private JButton listaGiocatoriButton;
    /**
     * Il pulsante che, se premuto, fa uscire il giocatore dalla partita e gestisce la corretta chiusura dei
     * frame, riportando il giocatore al form {@link MenuSceltaPartite}
     */
    private JButton esciDallaPartitaButton;

    /**
     * Istanzia l'interfaccia grafica per la schermata d'attesa di giocatori all'interno della partita pubblica
     * a cui sta partecipando il giocatore loggato. In particolare, il costruttore imposta il layout visivo del
     * form e associa ad ogni pulsante il rispettivo ascoltatore. Utilizza il controller per ottenere le
     * informazioni sul numero di partecipanti presenti nella partita e sulla capienza della stessa
     * per impostare il relativo campo testuale. Il pulsante lista giocatori nasconde il frame attuale
     * e apre la schermata per la visualizzazione dell'elenco dei partecipanti alla partita
     * {@link SchermataGiocatoriPartitaPubblica}. Il pulsante di uscita dalla partita chiede al giocatore
     * ulteriore conferma e poi delega al controller la registrazione di tale uscita, catturando potenziali
     * errori di accesso o di scrittura su file di testo. Il giocatore viene poi riportato al frame di
     * {@link MenuSceltaPartite} mentre il frame attuale e quello chiamante sono liberati dalla memoria.
     *  @param frameChiamante Il frame a cui tornare in caso di selezione del pulsante di ritorno al
     *                        menu precedente.
     * @param frameSceltaPartite Il frame a cui tornare in caso di uscita dalla partita.
     * @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public SchermataPartitaPubblica(JFrame frameChiamante, JFrame frameSceltaPartite, Controller controller){
        this.frameChiamante = frameChiamante;
        this.frameSceltaPartite = frameSceltaPartite;
        this.controller = controller;
        questoFrameSchermataPartita = new JFrame("Schermata Partite Pubbliche");
        questoFrameSchermataPartita.setContentPane(panelPartitaPubblica);
        questoFrameSchermataPartita.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameSchermataPartita.pack();
        labelNumeroGiocatori.setText(controller.getNumeroPartecipantiPartitaGiocatoreLoggato()+"/"+controller.getCapienzaPartitaGiocatoreLoggato());
        questoFrameSchermataPartita.setVisible(true);
        listaGiocatoriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SchermataGiocatoriPartitaPubblica frameGiocatoriPartita = new SchermataGiocatoriPartitaPubblica(questoFrameSchermataPartita, controller);
                questoFrameSchermataPartita.setVisible(false);
            }
        });
        esciDallaPartitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta = JOptionPane.showConfirmDialog(null, "Vuoi uscire dalla partita?", null, JOptionPane.YES_NO_OPTION);
                if(risposta == JOptionPane.YES_OPTION)
                {
                    {
                        try {
                            controller.giocatoreLoggatoEsceDallaPartita();
                            JOptionPane.showMessageDialog(null, "Sei uscito dalla partita!");
                            frameSceltaPartite.setVisible(true);
                            frameChiamante.dispose();
                            questoFrameSchermataPartita.dispose();
                        }
                        catch(RuntimeException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                        }
                    }
                }
            }
        });
    }
}
