package model;

import java.util.Date;

/**
 * La classe Segnalazione rappresenta la segnalazione che un {@link Giocatore} effettua
 * nei confronti di un altro all'interno del sistema per denunciare ai moderatori
 * comportamenti sleali o irregolari.
 */
public class Segnalazione {
    /**
     * Il {@link Giocatore} che effettua la segnalazione.
     */
    private Giocatore giocatoreSegnalante;
    /**
     * Il {@link Giocatore} che verso cui è rivolta la segnalazione.
     */
    private Giocatore giocatoreSegnalato;
    /**
     * La data e ora esatte in cui è stata registrata la segnalazione.
     */
    private Date dataSegnalazione;

    /**
     * Un'enumerazione che definisce i possibili motivi o cause per cui il
     * {@link Giocatore} autore della segnalazione ha effettuato la stessa.
     */
    public enum Motivo{

        /**
         * Utilizzo di linguaggio offensivo.
         */
        COMPORTAMENTO_SCORRETTO,
        /**
         * Alterazione delle regole del gioco su cui è giocata la {@link PartitaPubblica}.
         * tramite software di terze parti.
         */
        TRUCCHI,
        /**
         * Assenza di comandi di movimento nel corso della {@link PartitaPubblica}.
         */
        INATTIVITÀ_GIOCATORE,
        /**
         * Comportamento rivolto a falsare il risultato di una {@link PartitaPubblica},
         * in favore o a sfavore di un altro {@link Giocatore}.
         */
        SABOTAGGIO,
        /**
         * Qualunque altra violazione che differisca dalle altre già presentate.
         */
        ALTRO;
    }
    private Motivo motivoSegnalazione;

    /**
     * Istanzia un oggetto Segnalazione, aggiungendo tale segnalazione alle segnalazioni ricevute
     * dal {@link Giocatore} segnalato.
     *
     * @param giocatoreSegnalante Il {@link Giocatore} autore della segnalazione.
     * @param giocatoreSegnalato  Il {@link Giocatore} segnalato dall'autore della segnalazione.
     * @param dataSegnalazione    La data in cui è registrata la segnalazione.
     * @param motivoSegnalazione  La motivazione della giustificazione tra quelle presenti nell'enumerazione Motivo
     */
    public Segnalazione(Giocatore giocatoreSegnalante, Giocatore giocatoreSegnalato, Date dataSegnalazione, Motivo motivoSegnalazione) {
        this.giocatoreSegnalante = giocatoreSegnalante;
        this.giocatoreSegnalato = giocatoreSegnalato;
        this.dataSegnalazione = dataSegnalazione;
        this.motivoSegnalazione = motivoSegnalazione;
        giocatoreSegnalato.getSegnalazioniRicevute().add(this);
    }

    /**
     * Restituisce una rappresentazione testuale delle informazioni base di una segnalazione, separando
     * tali informazioni tramite un ";" secondo il seguente ordine: nome utente dell'autore della segnalazione,
     * nome utente del giocatore segnalato, data della segnalazione e motivo della stessa.
     * @return Una stringa contenente le informazioni di base della segnalazione
     */
    public String toString(){
        return (giocatoreSegnalante.getNomeUtente()+";"+giocatoreSegnalato.getNomeUtente()+";"+dataSegnalazione.toString()+";"+motivoSegnalazione.name());
    }
}
