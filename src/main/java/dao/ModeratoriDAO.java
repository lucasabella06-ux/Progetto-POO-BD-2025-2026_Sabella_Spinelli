package dao;

import java.util.ArrayList;

/**
 * L'interfaccia ModeratoriDAO gestisce la persistenza dei dati relativi ai {@link model.Moderatore} del sistema,
 * definendo metodi per la gestione di tali dati e il loro aggiornamento.
 */
public interface ModeratoriDAO {
    /**
     * Riempie le liste fornite come parametri con i dati dei {@link model.Moderatore} presenti nel sistema.
     *
     * @param listaNomi         Lista in cui inserire i nomi utente recuperati.
     * @param listaEmail        Lista in cui inserire le mail recuperate.
     * @param listaPassword     Lista in cui inserire le password recuperate.
     * @param listaCodiciModeratori Lista in cui inserire i codici d'accesso dei
     *                              {@link model.Moderatore} recuperati.
     * @param orariInizioAttivita   Lista in cui inserire gli orari d'inizio attività
     *                              dei {@link model.Moderatore} recuperati.
     * @param orariFineAttivita     Lista in cui inserire gli orari di fine attività
     *                              dei {@link model.Moderatore} recuperati.
     */
    public void generaModeratori(ArrayList<String> listaNomi, ArrayList<String> listaEmail, ArrayList<String> listaPassword,
                                ArrayList<String> listaCodiciModeratori, ArrayList<Integer> orariInizioAttivita,
                                ArrayList<Integer> orariFineAttivita);

    /**
     * Registra l'aggiunta di un nuovo {@link model.Moderatore} nel sistema.
     *
     * @param nomeUtente  Il nome utente del {@link model.Moderatore}.
     * @param email       L'email del {@link model.Moderatore}.
     * @param password    La password del {@link model.Moderatore}.
     * @param codiceModeratore     Il codice d'accesso univoco del {@link model.Moderatore}.
     * @param orarioInizioAttivita L'orario d'inizio attività del {@link model.Moderatore}.
     * @param orarioFineAttivita   L'orario di fine attività del {@link model.Moderatore}.
     */
    public void aggiungiModeratore(String nomeUtente, String email, String password, String codiceModeratore, int orarioInizioAttivita, int orarioFineAttivita );
}
