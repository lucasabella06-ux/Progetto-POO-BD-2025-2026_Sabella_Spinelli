package Model;

public abstract class Utente {
    private String nomeUtente;
    private String emailUtente;
    private String passwordUtente;

    public Utente(String nomeUtente, String emailUtente, String passwordUtente) {
        this.nomeUtente = nomeUtente;
        this.emailUtente = emailUtente;
        this.passwordUtente = passwordUtente;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public String getPasswordUtente() {
        return passwordUtente;
    }

    public void setPasswordUtente(String passwordUtente) {
        this.passwordUtente = passwordUtente;
    }

    public boolean login(String nomeUtenteInserito, String emailInserita, String passwordInserita){

        return(this.nomeUtente.equals(nomeUtenteInserito) && this.emailUtente.equals(emailInserita) && this.passwordUtente.equals(passwordInserita));
    }
}
