import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 * questa classe permette di istanziare una clausola, cioè una
 * disgiunzione di letterali, che possono essere negati o meno.
 */
public class Clause implements Iterable<Literal> {

    //ATTRIBUTI STATICI
    private static int count = 0;

    //ATTRIBUTI
    private Set<Literal> literals; //l'insieme che contiene i letterali della clausola.
    private int index;

    //COSTRUTTORI
    public Clause() {
        this.literals = new HashSet<>();
        this.index = count;
        count++;
    }


    //METODI

    /**
     * @return l'indice della clausola this.
     */
    public int getIndex() {
        return index;
    }

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
     * @return una rappresentazione testuale della clausola, mediante
     *         rappresentazione insiemistica. ad esempio: "{a, b, ~c}"
     */
    @Override
    public String toString() {
        if (this.isEmpty()) return "{}"; //la clausola vuota rappresenta la contraddizione.

        StringBuilder res = new StringBuilder(literals.toString());
        res.setCharAt(0, '{');
        res.setCharAt(res.length() - 1, '}');

        return res.toString();
    }

    private boolean equals(Clause c) {
        return this.literals.equals(c.literals);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Clause) && (this.equals( (Clause)obj ));
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
