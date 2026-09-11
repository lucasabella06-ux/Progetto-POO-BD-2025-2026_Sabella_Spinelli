package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La classe SchermataGiocatoriStorico istanzia il form grafico per la visualizzazione da parte del
 * {@link model.Giocatore} loggato nel sistema dell'elenco dei partecipanti alla sua stessa
 * {@link model.PartitaPubblica} che si è appena conclusa.
 * Permette al giocatore di visualizzare una tabella raffigurante informazioni sui giocatori che erano
 * presenti nella partita pubblica, con la possibilità di visualizzare altri dettagli del
 * profilo di tali giocatori oppure di effettuare una segnalazione contro uno di essi, nel successivo form
 * {@link SchermataSegnalazioneGiocatore}. Inoltre è permesso tornare al precedente form {@link MenuSceltaPartite}.
 */
public class SchermataGiocatoriStorico {
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelGiocatoriStorico;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;
    /**
     * Il pannello secondario a cui è associata la tabella dei giocatori partecipanti alla
     * partita pubblica.
     */
    private JScrollPane pannelloGiocatori;
    /**
     * La tabella per la visualizzazione delle informazioni principali dei giocatori
     * presenti nella partita pubblica.
     */
    private JTable tabellaGiocatori;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameSchermataGiocatoriStorico;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuSceltaPartite},
     * passato al momento dell'istanza da {@link MenuSelezionePartitaPubblica}.
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;

    /**
     * Istanzia l'interfaccia grafica per il menu di visualizzazione dei partecipanti alla partita pubblica appena
     * conclusa a cui ha partecipato il giocatore loggato. In particolare, il costruttore imposta il layout visivo
     * del form e associa ad ogni elemento grafico il rispettivo ascoltatore. Utilizza il {@link Controller} per ottenere
     * le informazioni dei giocatori partecipanti alla partita per riempire la tabella non modificabile.
     * La selezione di una riga della tabella tramite doppio click o click destro mostra al giocatore
     * una finestra modale per la scelta tra due operazioni, visualizzare il profilo del giocatore selezionato
     * oppure segnalarlo. Nel primo caso, viene mostrata un'altra finestra modale contenente altri dati del profilo
     * del giocatore, nel secondo si verifica tramite il {@link Controller} che il giocatore non stia provando a
     * segnalare sè stesso e poi viene aperta la nuova schermata per la segnalazione del giocatore
     * {@link SchermataSegnalazioneGiocatore}, nascondendo il frame attuale. Il pulsante indietro riporta al
     * frame di {@link MenuSceltaPartite} liberando le risorse utilizzate nel frame attuale.
     *  @param frameChiamante Il frame a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     *  @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public SchermataGiocatoriStorico(JFrame frameChiamante,Controller controller) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        questoFrameSchermataGiocatoriStorico = new JFrame("Schermata Giocatori Ultima Partita");
        questoFrameSchermataGiocatoriStorico.setContentPane(panelGiocatoriStorico);
        questoFrameSchermataGiocatoriStorico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameSchermataGiocatoriStorico.pack();
        String[] colonne = {"Nome Giocatore", "Livello"};
        Object[][] dati = new Object[controller.mostraPartecipantiUltimaPartitaGiocatoreLoggato().size()][2];
        for (String s : controller.mostraPartecipantiUltimaPartitaGiocatoreLoggato()) {
            String[] datiStorico = s.split(";");
            int indice = controller.mostraPartecipantiUltimaPartitaGiocatoreLoggato().indexOf(s);
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
        questoFrameSchermataGiocatoriStorico.setVisible(true);

        tabellaGiocatori.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int giocatoreSelezionato = tabellaGiocatori.rowAtPoint(e.getPoint());
                if (giocatoreSelezionato >= 0) {
                    String[] opzioni = {"Visualizza profilo", "Segnala Giocatore"};
                    String nomeUtente = tabellaGiocatori.getValueAt(giocatoreSelezionato, 0).toString();
                    int scelta = JOptionPane.showOptionDialog(null, "Selezionare un'azione", "Selezione", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[0]);
                    String[]datiGiocatore = controller.mostraDatiGiocatore(nomeUtente).split(";");
                    if(scelta == 0)
                    {
                        JOptionPane.showMessageDialog(null, datiGiocatore[0]+"\n Livello: "+datiGiocatore[1]+"\n Grado: "+datiGiocatore[2]+"\n Record: "+datiGiocatore[3]+" - "+datiGiocatore[4]);
                    }
                    if(scelta == 1)
                    {
                        if(controller.verificaGiocatoreSelezionato(nomeUtente)){
                            JOptionPane.showMessageDialog(null, "Non puoi segnalare te stesso!", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            SchermataSegnalazioneGiocatore frameSegnalazioneGiocatore = new SchermataSegnalazioneGiocatore(questoFrameSchermataGiocatoriStorico, "SchermataGiocatoriStorico", controller, datiGiocatore[0]);
                            questoFrameSchermataGiocatoriStorico.setVisible(false);
                        }
                    }
                }
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                questoFrameSchermataGiocatoriStorico.dispose();
            }
        });
    }
}
