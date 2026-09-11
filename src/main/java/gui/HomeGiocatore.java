package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe HomeGiocatore istanzia il form grafico che svolge il ruolo di menu principale
 * del {@link model.Giocatore} che ha fatto l'accesso al sistema. Mostra le informazioni
 * principali del profilo, recuperate tramite il {@link Controller}. Consente di modificare
 * lo stato online/offline del giocatore, visualizzare lo storico delle partite giocate
 * durante l'attuale sessione di gioco e la classifica dei migliori giocatori del sistema,
 * mostrare tramite tabelle grafiche. Permette inoltre all'utente di accedere al successivo
 * form {@link MenuSceltaPartite} che dove selezionare una modalità di gioco oppure di uscire
 * dal sistema, tornando al form precedente (può essere {@link MenuLoginGiocatore} o
 * {@link MenuCreaNuovoGiocatore}).
 */
public class HomeGiocatore {
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelHomeGiocatore;
    /**
     * Il campo testuale che mostra il nome utente del giocatore loggato.
     */
    private JLabel nomeUtente;
    /**
     * Il campo testuale che mostra il codice identificativo univoco del giocatore loggato.
     */
    private JLabel idGiocatore;
    /**
     * Il campo testuale che mostra il livello del giocatore loggato.
     */
    private JLabel livelloGiocatore;
    /**
     * Il campo testuale che mostra il grado del giocatore loggato.
     */
    private JLabel gradoGiocatore;
    /**
     * Il pulsante che, se premuto, apre il form di selezione della modalità di gioco {@link MenuSceltaPartite}.
     */
    private JButton giocaButton;
    /**
     * Il pulsante che, se premuto, mostra al giocatore loggato una finestra modale contenente il record
     * partite vinte-partite perse del giocatore e una tabella in cui sono raccolti i risultati delle partite
     * pubbliche giocate dal giocatore durante la sessione di gioco in corso.
     */
    private JButton storicoPartiteButton;
    /**
     * Il pulsante che, se premuto, mostra al giocatore loggato una finestra modale contenente una tabella
     * in cui sono raccolti i dati dei migliori giocatori del sistema.
     */
    private JButton classificaButton;
    /**
     * Il selettore interattivo che permette al giocatore loggato di modificare il proprio stato online/offline.
     */
    private JCheckBox statoCheckBox;
    /**
     * Il pulsante che, se premuto, attiva la procedura di logout facendo uscire il giocatore loggato dal
     * sistema e riportando l'utente al frame chiamante.
     */
    private JButton esciButton;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà del frame di {@link MenuLoginGiocatore}
     * o di {@link MenuCreaNuovoGiocatore}).
     */
    private JFrame frameChiamante;
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameHomeGiocatore;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;

    /**
     * Istanzia l'interfaccia grafica per il menu principale del giocatore loggato, mostrandone
     * i dati del profilo a schermo e configurando gli ascoltatori di eventi legati agli elementi del form.
     * In particolare, il costruttore interroga il controller per impostare i campi testuali informativi e
     * il selettore dello stato online/offline del giocatore loggato sul valore {@code true}. L'ascoltatore
     * di tale selettore ne cambia dinamicamente lo sfondo e comunica al controller il cambio di stato.
     * Il pulsante di uscita esegue la procedura di logout del giocatore loggato, riportando l'utente al form
     * precedente. Il pulsante dello storico recupera i dati dello storico del giocatore, se sono vuoti mostra
     * solo il campo testuale con il record del giocatore loggato, altrimenti genera un modello di tabella non
     * modificabile da mostrare al giocatore con i dati scomposti del suo storico all'interno di un pannello a
     * scorrimento, inserito in una finestra modale. Il pulsante della classifica fa generare la classifica al
     * controller e ne recupera i dati, mostrandoli poi in un'altra finestra modale simile a quella utilizzata
     * per lo storico. Il pulsante gioca nasconde il frame attuale e manda il giocatore loggato nel nuovo form
     * {@link MenuSceltaPartite}.
     *
     * @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     * @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public HomeGiocatore(JFrame frameChiamante, Controller controller) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;
        questoFrameHomeGiocatore = new JFrame("HomeGiocatore");
        questoFrameHomeGiocatore.setContentPane(panelHomeGiocatore);
        questoFrameHomeGiocatore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questoFrameHomeGiocatore.pack();
        questoFrameHomeGiocatore.setVisible(true);
        nomeUtente.setText(controller.getNomeUtenteGiocatoreLoggato());
        idGiocatore.setText(controller.getIdGiocatoreLoggato());
        livelloGiocatore.setText("Livello "+controller.getLivelloGiocatoreLoggato());
        gradoGiocatore.setText("Grado: "+controller.getGradoGiocatoreLoggato());
        statoCheckBox.setSelected(true);
        statoCheckBox.setBackground(Color.GREEN);
        statoCheckBox.setText("Online");

        statoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(statoCheckBox.isSelected()){
                    statoCheckBox.setBackground(Color.GREEN);
                    statoCheckBox.setText("Online");
                    controller.setStatoGiocatoreLoggato(true);
                }
                if(!statoCheckBox.isSelected()){
                    statoCheckBox.setBackground(Color.RED);
                    statoCheckBox.setText("Offline");
                    controller.setStatoGiocatoreLoggato(false);
                }
            }
        });
        esciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.giocatoreLoggatoEsceDalSistema();
                JOptionPane.showMessageDialog(null,"Sei uscito con successo!");
                frameChiamante.setVisible(true);
                questoFrameHomeGiocatore.dispose();
            }
        });

        storicoPartiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.mostraStoricoGiocatoreLoggato().isEmpty()) {
                    JLabel recordGiocatore = new JLabel("Record: "+controller.getPartiteVinteGiocatoreLoggato()+" - "+ controller.getPartitePerseGiocatoreLoggato());
                    JOptionPane.showMessageDialog(null, recordGiocatore.getText()+"\nNon hai giocato nessuna partita nell'attuale sessione.");
                } else {
                    String[] colonne = {"Partita", "Vincitore", "Durata"};
                    Object[][] dati = new Object[controller.mostraStoricoGiocatoreLoggato().size()][3];
                    for (String s : controller.mostraStoricoGiocatoreLoggato()) {
                        String[] datiStorico = s.split(";");
                        int indice = controller.mostraStoricoGiocatoreLoggato().indexOf(s);
                        dati[indice][0] = datiStorico[0];
                        dati[indice][1] = datiStorico[1];
                        dati[indice][2] = datiStorico[2];
                    }
                    DefaultTableModel model = new DefaultTableModel(dati, colonne) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    JLabel recordGiocatore = new JLabel("Record: "+controller.getPartiteVinteGiocatoreLoggato()+" - "+ controller.getPartitePerseGiocatoreLoggato());
                    JTable table = new JTable(model);
                    JScrollPane pannelloStorico = new JScrollPane(table);
                    Object[]componenti = {recordGiocatore, pannelloStorico};
                    JOptionPane.showMessageDialog(null, componenti, "Storico Partite", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        classificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    controller.generaClassifica();
                    String[] colonne = {"Posizione","Nome Giocatore", "Livello", "Grado", "Vinte", "Perse"};
                    Object[][] dati = new Object[controller.mostraClassifica().size()][6];
                    int indice = 0;
                    for (String s : controller.mostraClassifica()) {
                        String[] datiGiocatore = s.split(";");
                        dati[indice][0] = indice + 1;
                        dati[indice][1] = datiGiocatore[0];
                        dati[indice][2] = datiGiocatore[1];
                        dati[indice][3] = datiGiocatore[2];
                        dati[indice][4] = datiGiocatore[3];
                        dati[indice][5] = datiGiocatore[4];
                        indice++;
                    }
                DefaultTableModel model = new DefaultTableModel(dati, colonne) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                JOptionPane.showMessageDialog(null, scrollPane, "Classifica", JOptionPane.PLAIN_MESSAGE);
            }
        });

        giocaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuSceltaPartite frameSceltaPartite = new MenuSceltaPartite (questoFrameHomeGiocatore, controller);
                questoFrameHomeGiocatore.setVisible(false);
            }
        });
    }
}
