package dao;

import java.util.ArrayList;
import java.util.Date;

/**
 * L'interfaccia SegnalazioniDAO gestisce la persistenza dei dati relativi alle
 * {@link model.Segnalazione} effettuate dai {@link model.Giocatore} verso altri
 * giocatori nel sistema, definendo metodi per la gestione di tali dati e il
 * loro aggiornamento.
 */
public interface SegnalazioniDAO {
    /**
     * Riempie le liste fornite come parametri con i dati delle {@link model.Segnalazione} presenti nel sistema.
     *
     * @param listaAutoriSegnalazioni Lista in cui inserire i nomi utente dei {@link model.Giocatore} che hanno
     *                                effettuato le {@link model.Segnalazione}.
     * @param listaGiocatoriSegnalati Lista in cui inserire i nomi utente dei {@link model.Giocatore} che verso
     *                                cui sono rivolte le {@link model.Segnalazione}.
     * @param listaDate               Lista in cui inserire le date in cui sono state effettuate le
     *                                {@link model.Segnalazione}.
     * @param listaMotivi             Lista in cui inserire i motivi per cui sono state effettuate le
     *                                {@link model.Segnalazione}.
     */
    public void generaSegnalazioni(ArrayList<String> listaAutoriSegnalazioni, ArrayList<String> listaGiocatoriSegnalati, ArrayList<Date> listaDate, ArrayList<String> listaMotivi);

    /**
     * Registra l'aggiunta di una nuova {@link model.Segnalazione} nel sistema.
     *
     * @param autoreSegnalazione Il {@link model.Giocatore} che ha effettuato la {@link model.Segnalazione}.
     * @param giocatoreSegnalato Il {@link model.Giocatore} verso cui è rivolta la {@link model.Segnalazione}.
     * @param data               La data esatta in cui è stata registrata la {@link model.Segnalazione}.
     * @param motivo             Il motivo per cui è stata effettuata la {@link model.Segnalazione}.
     */
    public void aggiungiSegnalazione(String autoreSegnalazione, String giocatoreSegnalato, Date data, String motivo);
}
