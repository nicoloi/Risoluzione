import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 * questa classe permette di istanziare una clausola, cioè una
 * disgiunzione di letterali, che possono essere negati o meno.
 */
public class Clause implements Iterable<Literal> {

    //ATTRIBUTI
    private Set<Literal> literals; //l'insieme che contiene i letterali della clausola.


    //COSTRUTTORI
    public Clause() {
        literals = new HashSet<>();
    }


    //METODI

    /**
     * @return un iteratore che permette di iterare sui letterali della clausola
     */
    @Override
    public Iterator<Literal> iterator() {
        return literals.iterator();
    }

    /**
     * questo metodo aggiunge un nuovo letterale alla clausola this.
     * se il letterale è già presente nella clausola this, allora non fa niente.
     * 
     * @param l il letterale da aggiungere alla clausola.
     * @throws NullPointerException se il parametro l è null.
     */
    public void addLiteral(Literal l) {
        Objects.requireNonNull(l);

        literals.add(l);
    }

    /**
     * questo metodo rimuove un letterale alla clausola this.
     * se il letterale non è presente nella clausola this, allora non fa niente.
     * 
     * @param l il letterale da rimuovere dalla clausola.
     * @throws NullPointerException se il parametro l è null.
     */
    public void removeLiteral(Literal l) {
        Objects.requireNonNull(l);

        literals.remove(l);
    }

    /**
     * 
     * @return il numero di letterali presenti nella clausola this.
     */
    public int size() {
        return literals.size();
    }

    /**
     * 
     * @return true se la clausola this è vuota
     */
    public boolean isEmpty() {
        return literals.isEmpty();
    }

    /**
     * 
     * @param l il letterale che vogliamo controllare nella clausola
     * @return true, se il letterale l è presente nella clausola this.
     * @throws NullPointerException se il parametro l è null.
     */
    public boolean contains(Literal l) {
        Objects.requireNonNull(l);
        
        return literals.contains(l);
    }

    /**
     * @return una rappresentazione testuale della clausola,
     *         ad esempio: "l1 | l2 | l3"
     *         dove la generica "l" rappresenta un letterale, e
     *         il simbolo "|" indica la disgiunzione tra i letterali. 
     */
    @Override
    public String toString() {
        //la clausola vuota rappresenta la contraddizione, indicata con {} 
        if (literals.isEmpty()) return "{}"; // ⊥

        String str = "";
        boolean first = true;

        for (Literal l: literals) {
            if (first) {
                str += l.toString();
            } else {
                str += " | " + l.toString();
            }

            first = false;
        }

        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Clause)) return false;

        Clause c = (Clause)obj;
        return this.literals.equals(c.literals);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
