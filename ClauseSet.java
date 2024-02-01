import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 *  Questa classe permette di rappresentare una formula in 
 *  forma normale congiuntiva, tramite un insieme di clausole
 *  che verranno poi messe in congiunzione logica, formando così
 *  una congiunzione di clausole.
 */
public class ClauseSet implements Iterable<Clause> {

    //ATTRIBUTI
    private Set<Clause> clauses; //l'insieme che contiene le clausole

    //COSTRUTTORI
    public ClauseSet() {
        clauses = new HashSet<>();
    }

    //METODI

    /**
     * @return un iteratore che permette di iterare sulle clausole dell'oggetto this
     */
    @Override
    public Iterator<Clause> iterator() {
        return clauses.iterator();
    }

    /**
     * questo metodo aggiunge una nuova clausola a this. se la clausola
     * è già presente, allora il metodo non fa niente.
     * 
     * @param c la clausola da aggiungere a this
     * @throws NullPointerException se il parametro c è null.
     */
    public void addClause(Clause c) {
        Objects.requireNonNull(c);
        clauses.add(c);
    }

    /**
     * Questo metodo rimuove una clausola da this. Se la clausola
     * non è presente in this, allora il metodo non fa niente.
     * 
     * @param c la clausola da rimuovere
     * @throws NullPointerException se il parametro c è null.
     */
    public void removeClause(Clause c) {
        Objects.requireNonNull(c);
        clauses.remove(c);
    }

    /**
     * 
     * @return il numero di clausole nel set this.
     */
    public int size() {
        return clauses.size();
    }

    /**
     * 
     * @return true, se this non contiene alcuna clausola.
     */
    public boolean isEmpty() {
        return clauses.isEmpty();
    }

    /**
     * 
     * @param c la clausola che vogliamo controllare nel set di clausole this.
     * @return true, se c è presente in this.
     * @throws NullPointerException se il parametro c è null.
     */
    public boolean contains(Clause c) {
        Objects.requireNonNull(c);
        return clauses.contains(c);
    }

    /**
     * @return una rappresentazione testuale del set di clausole this,
     *         come insieme di clausole.
     */
    @Override
    public String toString() {
        if (this.isEmpty()) return "empty set"; //restituisce l'insieme vuoto

        StringBuilder res = new StringBuilder();
        boolean first = true;

        res.append("{ ");
        for (Clause c : clauses) {
            
            if (first) {
                res.append(c.toString());
            } else {
                res.append("; " + c.toString());
            }

            first = false;
        }
        res.append(" }");
        return res.toString();
    }
}
