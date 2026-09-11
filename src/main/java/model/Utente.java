package model;

/**
 * La classe astratta Utente rappresenta un utente generico all'interno del sistema.
 * Rappresenta la base per l'istanza delle classi relative a tipologie specifiche di utenti
 * ({@link Giocatore}, {@link Moderatore}).
 */
public abstract class Utente {
    /**
     * Il nome identificativo dell'utente.
     */
    protected String nomeUtente;
    /**
     * L'email dell'utente.
     */
    protected String emailUtente;
    /**
     * La password dell'utente.
     */
    protected String passwordUtente;

    /**
     * Istanzia un nuovo oggetto della classe Utente.
     *
     * @param nomeUtente     Il nome utente inserito dall'utente.
     * @param emailUtente    L'indirizzo email dell'utente.
     * @param passwordUtente La password inserita dall'utente.
     */
    public Utente(String nomeUtente, String emailUtente, String passwordUtente) {
        this.nomeUtente = nomeUtente;
        this.emailUtente = emailUtente;
        this.passwordUtente = passwordUtente;
    }

    /**
     * Restituisce il nomeUtente dell'utente.
     *
     * @return una String contenente il nomeUtente dell'utente.
     */
    public String getNomeUtente() {
        return nomeUtente;
    }

    /**
     * Aggiorna il nomeUtente dell'utente.
     *
     * @param nomeUtente il nuovo NomeUtente da impostare.
     */
    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return una String contenente l'email dell'utente.
     */
    public String getEmailUtente() {
        return emailUtente;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return una String contenente la password dell'utente.
     */
    public String getPasswordUtente() {
        return passwordUtente;
    }

    /**
     * Aggiorna la password dell'utente.
     *
     * @param passwordUtente la nuova password dell'utente.
     */
    public void setPasswordUtente(String passwordUtente) {
        this.passwordUtente = passwordUtente;
    }

    /**
     * Verifica se le credenziali inserite corrispondono a quelle dell'utente.
     * Il controllo sulla mail viene fatto senza distinguere tra maiuscole e minuscole, mentre il controllo sulla password è case-sensitive.
     *
     * @param emailInserita    L'email inserita durante il login.
     * @param passwordInserita the password inserita.
     * @return {@code true} se entrambe le credenziali sono corrette, {@code false} in caso contrario.
     */
    public boolean login(String emailInserita, String passwordInserita){

        return(this.emailUtente.equalsIgnoreCase(emailInserita) && this.passwordUtente.equals(passwordInserita));
    }
}
