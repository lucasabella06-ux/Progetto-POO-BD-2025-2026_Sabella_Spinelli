package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * La classe SchermataPartitaModeratore istanzia il form grafico per la visualizzazione da parte del
 * {@link model.Moderatore} loggato nel sistema dell'elenco dei partecipanti alla {@link model.PartitaPubblica}
 * che sta gestendo e ha selezionato nel precedente form {@link HomeModeratore}.
 * Permette al giocatore di visualizzare una tabella raffigurante informazioni sui giocatori presenti attualmente
 * nella partita pubblica, con la possibilità di visualizzare altri dettagli del profilo di tali giocatori oppure
 * di visualizzare l'elenco delle segnalazioni effettuate contro uno di essi, nel successivo form
 * {@link SchermataVisualizzazioneSegnalazioni}. Inoltre è permesso tornare al precedente form
 * {@link HomeModeratore} in due modi, sia tramite il pulsante indietro che libera il frame attuale e
 * non necessita di chiamare il controller, sia con il pulsante che termina la gestione della partita da parte
 * del moderatore, delegando al controller la gestione di tale terminazione e aggiornando i dati mostrati
 * nel frame chiamante.
 */
public class SchermataPartitaModeratore {
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameSchermataPartitaModeratore;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link MenuSelezionePartitaPubblica}.
     */
    private JFrame frameChiamante;
    /**
     * Il frame chiamante del frame di {@link HomeModeratore}, necessario per reistanziare il form precedente in modo
     * tale da aggiornare i dati mostrati all'interno di esso.
     */
    private JFrame frameChiamanteHome;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;
    /**
     * Il codice identificativo della partita pubblica di cui sono mostrati i partecipanti.
     */
    private String codicePartitaSelezionata;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelPartitaModeratore;
    /**
     * Il campo testuale che mostra il codice identificativo della partita pubblica di cui sono presentati
     * i partecipanti.
     */
    private JLabel labelPartita;
    /**
     * La tabella per la visualizzazione delle informazioni principali dei giocatori
     * presenti nella partita pubblica.
     */
    private JTable tabellaGiocatori;
    /**
     * Il pannello secondario a cui è associata la tabella dei giocatori partecipanti alla
     * partita pubblica.
     */
    private JScrollPane pannelloGiocatori;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e istanzia di nuovo il form {@link HomeModeratore}
     * affinchè mostri dati aggiornati.
     */
    private JButton terminaGestionePartitaButton;

    /**
     * Istanzia l'interfaccia grafica per il menu di visualizzazione dei giocatori partecipanti alla partita pubblica
     * che il moderatore loggato sta gestendo. In particolare, il costruttore imposta il layout visivo del form e
     * associa ad ogni elemento grafico il rispettivo ascoltatore. Utilizza il controller per ottenere le informazioni dei
     * giocatori partecipanti alla partita per riempire la tabella non modificabile. La selezione di una riga della
     * tabella tramite doppio click o click destro mostra al moderatore una finestra modale contenente una finestra modale
     * per la scelta tra due operazioni, visualizzare il profilo del giocatore selezionato oppure visualizzarne le
     * segnalazioni ricevute da altri giocatori. Nel primo caso, viene mostrata un'altra finestra modale contenente altri
     * dati del profilo del giocatore, nel secondo viene aperta la nuova schermata per la visualizzazione delle
     * segnalazioni ricevute dal giocatore {@link SchermataVisualizzazioneSegnalazioni}, nascondendo il frame attuale,
     * solo se per quel giocatore risultano segnalazioni ricevute.
     * Il pulsante per terminare la gestione della partita avvia la procedura di gestione di tale operazione, delegata
     * al controller, che aggiorna i dati relativi alla partita pubblica interessata, poi il precedente form
     * {@link HomeModeratore} viene reistanziato per mostrare dati aggiornati in linea con le verifiche apportate dai metodi
     * del controller. Il pulsante indietro riporta invece al frame precedente già istanziato liberando le risorse utilizzate
     * nel frame attuale.
     *
     * @param frameChiamante           Il frame a cui tornare in caso di selezione del pulsante di ritorno al
     *                                 menu precedente.
     * @param frameChiamanteHome       Il frame chiamante del frame di {@link HomeModeratore}, necessario per
     *                                 reistanziare il form precedente in modo tale da aggiornare i dati
     *                                 mostrati all'interno di esso.
     * @param controller               Il controller logico del sistema, condiviso con le finestre successive.
     * @param codicePartitaSelezionata Il codice identificativo della partita pubblica selezionata
     *                                 nel form {@link HomeModeratore}.
     */
    public SchermataPartitaModeratore(JFrame frameChiamante, JFrame frameChiamanteHome ,Controller controller, String codicePartitaSelezionata){
        this.frameChiamante = frameChiamante;
        this.frameChiamanteHome = frameChiamanteHome;
        this.controller = controller;
        this.codicePartitaSelezionata = codicePartitaSelezionata;
        questoFrameSchermataPartitaModeratore = new JFrame("Schermata Partita Moderatore");
        questoFrameSchermataPartitaModeratore.setContentPane(panelPartitaModeratore);
        questoFrameSchermataPartitaModeratore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labelPartita.setText("Partita "+codicePartitaSelezionata);
        String[] colonne = {"Nome Giocatore", "Segnalazioni Ricevute"};
        Object[][] dati = new Object[controller.mostraPartecipantiPartitaModeratore(codicePartitaSelezionata).size()][2];
        for (String s : controller.mostraPartecipantiPartitaModeratore(codicePartitaSelezionata)) {
            String[] datiGiocatore = s.split(";");
            int indice = controller.mostraPartecipantiPartitaModeratore(codicePartitaSelezionata).indexOf(s);
            dati[indice][0] = datiGiocatore[0];
            dati[indice][1] = datiGiocatore[5];
        }
        DefaultTableModel model = new DefaultTableModel(dati, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabellaGiocatori.setModel(model);
        questoFrameSchermataPartitaModeratore.pack();
        questoFrameSchermataPartitaModeratore.setVisible(true);

        tabellaGiocatori.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int giocatoreSelezionato = tabellaGiocatori.rowAtPoint(e.getPoint());
                if (giocatoreSelezionato >= 0) {
                    String[] opzioni = {"Visualizza profilo", "Visualizza Segnalazioni"};
                    String nomeUtenteGiocatore = tabellaGiocatori.getValueAt(giocatoreSelezionato, 0).toString();
                    int scelta = JOptionPane.showOptionDialog(null, "Selezionare un'azione", "Selezione", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[0]);
                    String[]datiGiocatore = controller.mostraDatiGiocatore(nomeUtenteGiocatore).split(";");
                    if(scelta == 0)
                    {
                        JOptionPane.showMessageDialog(null, datiGiocatore[0]+"\n Livello: "+datiGiocatore[1]+"\n Grado: "+datiGiocatore[2]+"\n Record: "+datiGiocatore[3]+" - "+datiGiocatore[4]);
                    }
                    if(scelta == 1)
                    {
                        if(controller.moderatoreLoggatoVisualizzaSegnalazioniGiocatore(nomeUtenteGiocatore).isEmpty()){
                            JOptionPane.showMessageDialog(null, "Il giocatore non ha ricevuto alcuna segnalazione");
                        }
                        else {
                            SchermataVisualizzazioneSegnalazioni frameVisualizzazioneSegnalazioni = new SchermataVisualizzazioneSegnalazioni(questoFrameSchermataPartitaModeratore, frameChiamante, frameChiamanteHome, controller, nomeUtenteGiocatore, codicePartitaSelezionata);
                        }
                    }
                }
            }
        });

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeModeratore frameHomeModeratoreAggiornato = new HomeModeratore(frameChiamanteHome, controller);
                questoFrameSchermataPartitaModeratore.dispose();
                frameChiamante.dispose();
            }
        });
        terminaGestionePartitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.moderatoreLoggatoTerminaGestionePartita(codicePartitaSelezionata);
                    HomeModeratore frameHomeModeratoreAggiornato = new HomeModeratore(frameChiamanteHome, controller);
                    questoFrameSchermataPartitaModeratore.dispose();
                    frameChiamante.dispose();
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE );
                }
            }
        });
    }
}
