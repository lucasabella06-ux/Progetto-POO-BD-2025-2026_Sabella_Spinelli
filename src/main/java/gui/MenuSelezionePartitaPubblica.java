package gui;

import controller.Controller;
import exception.GiocatoreGiaInPartitaException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * La classe MenuSelezionePartitaPubblica istanzia il form grafico per la scelta da parte del
 * {@link model.Giocatore} loggato nel sistema di una {@link model.PartitaPubblica} a cui partecipare.
 * Permette al giocatore di visualizzare una tabella raffigurante l'elenco delle partite pubbliche
 * presenti attualmente nel sistema, di filtrare tali partite secondo il videogioco selezionato
 * nel menu a tendina e di effettuare l'ingresso in una delle partite, generando il risultato della
 * partita pubblica se con l'ingresso del giocatore la partita ha raggiunto la capienza necessaria
 * per il suo svolgimento. In tal caso il risultato viene visualizzato tramite finestra modale
 * e al giocatore è concesso di accedere alla lista dei partecipanti della partita appena conclusa,
 * nel nuovo form {@link SchermataGiocatoriStorico}. Altrimenti il giocatore viene mandato nella
 * schermata d'attesa {@link SchermataPartitaPubblica}. Permane l'opzione di tornare al precedente
 * form {@link MenuSceltaPartite}.
 */
public class MenuSelezionePartitaPubblica {
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelSelezionePartite;
    /**
     * La tabella per la visualizzazione delle informazioni principali delle partite pubbliche
     * presenti nel sistema
     */
    private JTable tabellaPartite;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;
    /**
     * Il pannello secondario a cui è associata la tabella delle partite.
     */
    private JScrollPane pannelloPartite;
    /**
     * Il menu a tendina per filtrare le partite pubbliche mostrate in base al videogioco.
     */
    private JComboBox filtroGiochi;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameMenuSelezionePartita;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuSceltaPartite}.
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;


    /**
     * Istanzia l'interfaccia grafica per il menu di selezione della partita pubblica da parte del
     * giocatore loggato. In particolare, il costruttore imposta il layout visivo del form e associa ad
     * ogni elemento grafico il rispettivo ascoltatore. Utilizza il controller per ottenere le informazioni
     * delle partite pubbliche presenti nel sistema per riempire la tabella non modificabile e popolare
     * il menù a tendina, evitando duplicati.
     * La selezione di una riga della tabella tramite doppio click o click destro richiede al giocatore
     * conferma dell'operazione e avvia la procedura di ingresso nella partita pubblica, catturando le
     * possibili eccezioni.
     * Se con l'ingresso del giocatore è raggiunta la capienza necessaria per l'avvio della partita ne
     * viene generato il risultato ed esso viene mostrato al giocatore tramite una finestra modale, la
     * quale consente anche di visualizzare i giocatori che hanno partecipato alla partita appena conclusa
     * nel form {@link SchermataGiocatoriStorico}. Se la partita non è ancora piena, viene aperta
     * la schermata d'attesa {@link SchermataPartitaPubblica}. Il menu a tendina viene riempito
     * con i videogiochi su cui saranno giocate le partite attualmente presenti e funge da filtro per
     * la tabella delle partite, che viene dinamicamente svuotata e nuovamente riempita al cambio della
     * selezione nel filtro. Il pulsante indietro riporta al frame precedente liberando le risorse utilizzate
     * nel frame attuale.
     *  @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     *  @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public MenuSelezionePartitaPubblica(JFrame frameChiamante, Controller controller)
    {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        questoFrameMenuSelezionePartita = new JFrame("Schermata Selezione Partite");
        questoFrameMenuSelezionePartita.setContentPane(panelSelezionePartite);
        questoFrameMenuSelezionePartita.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] colonne = {"Partita", "Gioco", "Numero Partecipanti", "Capienza"};
        Object[][] dati = new Object[controller.mostraPartitePubbliche().size()][4];
        for (String s : controller.mostraPartitePubbliche()) {
            String[] datiPartita = s.split(";");
            boolean esisteGia = false;
            for (int i = 0; i < filtroGiochi.getItemCount(); i++) {
                if (filtroGiochi.getItemAt(i).equals(datiPartita[1])) {
                    esisteGia = true;
                    break;
                }
            }
            if (!esisteGia) {
                filtroGiochi.addItem(datiPartita[1]);
            }
            int indice = controller.mostraPartitePubbliche().indexOf(s);
            dati[indice][0] = datiPartita[0];
            dati[indice][1] = datiPartita[1];
            dati[indice][2] = datiPartita[2];
            dati[indice][3] = datiPartita[3];
        }
        DefaultTableModel model = new DefaultTableModel(dati, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabellaPartite.setModel(model);
        filtroGiochi.setSelectedIndex(-1);
        questoFrameMenuSelezionePartita.pack();
        questoFrameMenuSelezionePartita.setVisible(true);


        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                questoFrameMenuSelezionePartita.dispose();
            }
        });


        tabellaPartite.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               int partitaSelezionata = tabellaPartite.rowAtPoint(e.getPoint());
               if(partitaSelezionata >= 0)
               {
                   int risposta = JOptionPane.showConfirmDialog(null, "Vuoi entrare in questa partita?", null, JOptionPane.YES_NO_OPTION);
                   if(risposta == JOptionPane.YES_OPTION)
                   {
                       String codicePartitaSelezionata = (String)tabellaPartite.getValueAt(partitaSelezionata, 0);
                       try {
                           controller.giocatoreLoggatoEntraInPartita(codicePartitaSelezionata);
                           JOptionPane.showMessageDialog(null, "Sei entrato in partita!");
                           if(!controller.verificaPartecipantiPartitaGiocatoreLoggato()) {
                               SchermataPartitaPubblica framePartitaPubblica = new SchermataPartitaPubblica(questoFrameMenuSelezionePartita, frameChiamante, controller);
                               questoFrameMenuSelezionePartita.setVisible(false);
                           }
                           else{
                               ArrayList<String> risultatoPartita = controller.mostraRisultatoPartita();
                               JLabel labelRisultato1 = new JLabel(risultatoPartita.getFirst());
                               JLabel labelRisultato2 = new JLabel(risultatoPartita.getLast());
                               JButton listaGiocatori = new JButton("Lista giocatori");
                               Object[] risultato = { labelRisultato1, labelRisultato2, listaGiocatori };

                               JOptionPane pane = new JOptionPane(
                                       risultato,
                                       JOptionPane.INFORMATION_MESSAGE,
                                       JOptionPane.DEFAULT_OPTION
                               );
                               JDialog dialog = pane.createDialog(null, "Risultato Partita");
                               listaGiocatori.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       dialog.dispose();

                                       SchermataGiocatoriStorico frameGiocatoriStorico = new SchermataGiocatoriStorico(frameChiamante, controller);
                                       questoFrameMenuSelezionePartita.setVisible(false);
                                   }
                               });
                               dialog.setVisible(true);

                               Object valoreSelezionato = pane.getValue();

                               if (valoreSelezionato instanceof Integer && (Integer) valoreSelezionato == JOptionPane.OK_OPTION){
                                   frameChiamante.setVisible(true);
                                   questoFrameMenuSelezionePartita.dispose();
                               }
                           }
                       }
                       catch(GiocatoreGiaInPartitaException | RuntimeException ex)
                       {
                           JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                       }
                   }
               }
            }
        });

        filtroGiochi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String giocoSelezionato = (String) filtroGiochi.getSelectedItem();
                model.setRowCount(0);
                for(String s: controller.mostraPartitePubbliche())
                {
                    String [] datiPartita = s.split(";");
                    String gioco = datiPartita[1];
                    if(gioco.equals(giocoSelezionato)){
                        model.addRow(new Object[]{
                                datiPartita[0], datiPartita[1], datiPartita[2], datiPartita[3]
                        });
                    }
                }
            }
        });
    }
}
