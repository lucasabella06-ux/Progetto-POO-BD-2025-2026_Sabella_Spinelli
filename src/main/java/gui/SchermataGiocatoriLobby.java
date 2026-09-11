package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La classe SchermataGiocatoriLobby istanzia il form grafico per la visualizzazione da parte del
 * {@link model.Giocatore} loggato nel sistema dell'elenco dei partecipanti alla {@link model.Lobby}
 * in cui è entrato.
 * Permette al giocatore di visualizzare una tabella raffigurante informazioni sui giocatori
 * presenti attualmente nella lobby, con la possibilità di visualizzare altri dettagli del
 * profilo di tali giocatori e di tornare al precedente form {@link SchermataPartecipanteLobby}.
 */
public class SchermataGiocatoriLobby {
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelGiocatoriLobby;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;
    /**
     * La tabella per la visualizzazione delle informazioni principali dei giocatori
     * presenti nella lobby.
     */
    private JTable tabellaGiocatori;
    /**
     * Il pannello secondario a cui è associata la tabella dei giocatori partecipanti alla
     * lobby.
     */
    private JScrollPane pannelloGiocatori;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameSchermataGiocatoriLobby;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link SchermataPartecipanteLobby}.
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;

    /**
     * Istanzia l'interfaccia grafica per il menu di visualizzazione dei partecipanti alla lobby a cui si è unito
     * il giocatore loggato. In particolare, il costruttore imposta il layout visivo del form e associa ad
     * ogni elemento grafico il rispettivo ascoltatore. Utilizza il {@link Controller} per ottenere le informazioni
     * dei giocatori presenti nella lobby per riempire la tabella non modificabile.
     * La selezione di una riga della tabella tramite doppio click o click destro mostra al giocatore
     * una finestra modale contenente altri dati del profilo del giocatore selezionato, non vi sono opzioni per
     * segnalare i giocatori perchè le lobby sono gruppi di gioco privati non controllati, in cui è responsabilità
     * dei giocatori autogestirsi. Il pulsante indietro riporta al frame precedente liberando le risorse utilizzate
     * nel frame attuale.
     *  @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     *  @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public SchermataGiocatoriLobby(JFrame frameChiamante, Controller controller) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        questoFrameSchermataGiocatoriLobby = new JFrame("Schermata Giocatori Lobby");
        questoFrameSchermataGiocatoriLobby.setContentPane(panelGiocatoriLobby);
        questoFrameSchermataGiocatoriLobby.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] colonne = {"Nome Giocatore", "Livello"};
        Object[][] dati = new Object[controller.mostraPartecipantiLobby().size()][2];
        for (String s : controller.mostraPartecipantiLobby()) {
            String[] datiStorico = s.split(";");
            int indice = controller.mostraPartecipantiLobby().indexOf(s);
            dati[indice][0] = datiStorico[0];
            dati[indice][1] = datiStorico[1];
        }
        DefaultTableModel model = new DefaultTableModel(dati, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabellaGiocatori.setModel(model);
        questoFrameSchermataGiocatoriLobby.pack();
        questoFrameSchermataGiocatoriLobby.setVisible(true);

        tabellaGiocatori.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int giocatoreSelezionato = tabellaGiocatori.rowAtPoint(e.getPoint());
                if (giocatoreSelezionato >= 0) {
                    String nomeUtente = tabellaGiocatori.getValueAt(giocatoreSelezionato, 0).toString();
                    String[] datiGiocatore = controller.mostraDatiGiocatore(nomeUtente).split(";");
                    JOptionPane.showMessageDialog(null, datiGiocatore[0] + "\n Livello: " + datiGiocatore[1] + "\n Grado: " + datiGiocatore[2] + "\n Record: " + datiGiocatore[3] + " - " + datiGiocatore[4]);
                }
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                questoFrameSchermataGiocatoriLobby.dispose();
            }
        });
    }
}
