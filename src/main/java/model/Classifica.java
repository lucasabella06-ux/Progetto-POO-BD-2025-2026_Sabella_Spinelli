package model;

import java.util.ArrayList;

/**
 * La classe Classifica rappresenta una classifica dei migliori {@link Giocatore} del sistema.
 * Tutti i giocatori del sistema vengono messi in ordine meritocratico secondo le proprie statistiche,
 */
public class Classifica {
    /**
     * Il numero di posizioni visibili della classifica.
     */
    private final int numeroPosizioni = 25;
    /**
     * L'elenco dei {@link Giocatore} presenti in classifica.
     */
    private ArrayList<Giocatore> giocatoriInClassifica = new ArrayList<Giocatore>();

    /**
     * Istanzia un oggetto Classifica, partendo da un singolo {@link Giocatore} come primo elemento.
     *
     * @param g Il giocatore da cui parte l'istanza della classifica.
     */
    public Classifica(Giocatore g) {
        giocatoriInClassifica.add(g);
    }

    /**
     * Aggiunge un {@link Giocatore} all'elenco dei giocatori in classifica (i giocatori
     * in questo momento non sono per forza in ordine).
     *
     * @param giocatoreDaAggiungere Il giocatore da aggiungere all'elenco dei giocatori in classifica
     */
    public void aggiungiGiocatore(Giocatore giocatoreDaAggiungere)
    {
        giocatoriInClassifica.add(giocatoreDaAggiungere);
    }

    /**
     * Ordina i {@link Giocatore} presenti in classifica sfruttando il metodo di confronto compareTo
     * definito in {@link Giocatore}. Al termine dell'ordinamento, l'elenco dei giocatori in classifica
     * viene troncata per assicurare che il numero massimo di posizioni resti quello definito in
     * precedenza, rimuovendo quindi tutti i giocatori non abbastanza in alto per comparire in classifica.
     */
    public void ordinaGiocatori() {
        giocatoriInClassifica.sort(Giocatore::compareTo);
        if (giocatoriInClassifica.size() > numeroPosizioni) {
            giocatoriInClassifica.subList(numeroPosizioni, giocatoriInClassifica.size()).clear();
        }
    }

    /**
     * Restituisce l'elenco dei {@link Giocatore} presenti in classifica.
     *
     * @return Un ArrayList contenente tutti i giocatori presenti in classifica.
     */
    public ArrayList<Giocatore> getGiocatoriInClassifica() {
        return giocatoriInClassifica;
    }


}
