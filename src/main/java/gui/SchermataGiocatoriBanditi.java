package gui;

import controller.Controller;
import exception.CodiceModeratoreErratoException;
import exception.CodiceModeratoreVuotoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * La classe SchermataGiocatoriBanditi istanzia il form grafico per la visualizzazione da parte del
 * {@link model.Moderatore} loggato nel sistema dell'elenco dei giocatori banditi dal sistema.
 * Permette al moderatore di visualizzare una tabella contenente i nomi utente dei giocatori banditi dal sistema,
 * con la possibilità di visualizzare altri dettagli del profilo di tali giocatori e di riattivarli
 * nel sistema, previa inserimento del codice d'accesso univoco da far convalidare al {@link Controller}.
 * Permane l'opzione di tornare al frame precedente.
 */
public class SchermataGiocatoriBanditi {
    /**
     * Il frame utilizzato come contenitore per la visualizzazione dell'interfaccia.
     */
    private JFrame questoFrameSchermataGiocatoriBanditi;
    /**
     * Il frame grafico che ha chiamato questa schermata (si tratterà sempre del frame di {@link HomeModeratore}.
     */
    private JFrame frameChiamante;
    /**
     * Il controller logico del sistema, condiviso con le finestre successive.
     */
    private Controller controller;
    /**
     * Il pannello principale dove sono posti tutti i componenti grafici del form.
     */
    private JPanel panelGiocatoriBanditi;
    /**
     * La tabella per la visualizzazione delle informazioni principali dei giocatori
     * presenti nella partita pubblica.
     */
    private JTable tabellaBanditi;
    /**
     * Il pannello secondario a cui è associata la tabella dei giocatori partecipanti alla
     * partita pubblica.
     */
    private JScrollPane pannelloBanditi;
    /**
     * Il pulsante che, se premuto, chiude il frame attuale e torna a quello chiamante.
     */
    private JButton indietroButton;

    /**
     * Istanzia l'interfaccia grafica per il menu di visualizzazione dei giocatori banditi dal sistema da parte
     * del moderatore loggato. In particolare, il costruttore imposta il layout visivo del form e associa ad ogni
     * elemento grafico il rispettivo ascoltatore. Utilizza il controller per ottenere le informazioni dei
     * giocatori banditi dal sistema per riempire la tabella non modificabile. La selezione di una riga della tabella
     * tramite doppio click o click destro mostra al moderatore una finestra modale contenente maggiori informazioni
     * sul profilo del giocatore selezionato e un'opzione per riattivare il suo profilo. Un click su quell'opzione
     * farà apparire un'altra finestra modale per l'inserimento del codice d'accesso univoco del moderatore,
     * il quale verrà convalidato dal controller, che in caso di convalida riuscita si occuperà anche
     * di aggiornare i dati del sistema per registrare la riattivazione del giocatore. Il pulsante indietro riporta
     * al frame precedente liberando le risorse utilizzate nel frame attuale.
     *  @param frameChiamante Il frame da cui è stato generato il form attuale a cui tornare in caso
     *                       di selezione del pulsante di ritorno al menu precedente.
     *  @param controller    Il controller logico del sistema, condiviso con le finestre successive.
     */
    public SchermataGiocatoriBanditi(JFrame frameChiamante, Controller controller){
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        questoFrameSchermataGiocatoriBanditi = new JFrame("Schermata Giocatori Banditi");
        questoFrameSchermataGiocatoriBanditi.setContentPane(panelGiocatoriBanditi);
        questoFrameSchermataGiocatoriBanditi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] colonna = {"Nome Giocatore"};
        ArrayList<String> listaGiocatoriBanditi = controller.mostraGiocatoriBanditi();
        int indice = 0;
        Object[][] dati = new Object[listaGiocatoriBanditi.size()][1];
        for (String s : listaGiocatoriBanditi) {
            String[] datiGiocatore = s.split(";");
            dati[indice][0] = datiGiocatore[0];
            indice++;
        }
        DefaultTableModel model = new DefaultTableModel(dati, colonna) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabellaBanditi.setModel(model);
        questoFrameSchermataGiocatoriBanditi.pack();
        questoFrameSchermataGiocatoriBanditi.setVisible(true);

        tabellaBanditi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int giocatoreSelezionato = tabellaBanditi.rowAtPoint(e.getPoint());
                if (giocatoreSelezionato >= 0) {
                    String[] opzione = {"Riattiva Giocatore"};
                    String nomeUtenteGiocatore = tabellaBanditi.getValueAt(giocatoreSelezionato, 0).toString();
                    String[]datiGiocatore = controller.mostraDatiGiocatore(nomeUtenteGiocatore).split(";");
                    int scelta = JOptionPane.showOptionDialog(null, datiGiocatore[0]+"\n Livello: "+datiGiocatore[1]+"\n Grado: "+datiGiocatore[2]+"\n Record: "+datiGiocatore[3]+" - "+datiGiocatore[4]+"\n Numero segnalazioni ricevute: "+datiGiocatore[5], "Profilo Giocatore Bandito", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzione, opzione[0]);
                    if(scelta == 0)
                    {
                        String codiceModeratore = JOptionPane.showInputDialog(null, "Inserisci il tuo codice moderatore per confermare l'azione");
                        try{
                            controller.verificaCodiceModeratore(codiceModeratore);
                            controller.moderatoreLoggatoRiattivaGiocatore(nomeUtenteGiocatore);
                            JOptionPane.showMessageDialog(null, "Hai riattivato il giocatore!");
                            frameChiamante.setVisible(true);
                            questoFrameSchermataGiocatoriBanditi.dispose();
                        }catch(NullPointerException ex){

                        }
                        catch(CodiceModeratoreVuotoException | RuntimeException | CodiceModeratoreErratoException ex)
                        {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                questoFrameSchermataGiocatoriBanditi.dispose();
            }
        });
    }
}
