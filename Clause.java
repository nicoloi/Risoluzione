import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 * this class represents a clause. a clause is a disjunction of literals, 
 * which may or may not be negated.
 */
public class Clause implements Iterable<Literal> {

    //STATIC FIELDS 
    private static int count = 0;

    //FIELDS
    private Set<Literal> literals; //the set containing the literals of the clause.
    private int index;

    //CONSTRUCTORS

    /**
     * Constructs a new, empty clause.
     */
    public Clause() {
        this.literals = new HashSet<>();
        this.index = count;
        count++;
    }

    /**
     * Constructs a new clause, containing the specified literal.
     * 
     * @param l the literal to be placed into the clause
     * @throws NullPointerException - if l is null.
     */
    public Clause(Literal l) {
        this();
        
        Objects.requireNonNull(l);
        this.literals.add(l);
    }

    /**
     * Constructs a new clause containing the literals in the specified list.
     * 
     * @param list the list of literals whose elements are to be placed
     *             into this clause.
     * @throws NullPointerException - if the specified list is null.
     */
    public Clause(List<Literal> list) {
        this.literals = new HashSet<>(list);
        this.index = count;
        count++;
    }

    //STATIC METHODS

    /**
     * Unites the specified clauses. Use this method to avoid side effects
     *   on specified clauses
     * 
     * @param c1 the first clause.
     * @param c2 the second clause.
     * @return the clause that represents the union of the specified clauses.
     */
    public static Clause union(Clause c1, Clause c2) {
        Clause result = new Clause();

        result.union(c1);
        result.union(c2);

        return result;
    }
    

    //METHODS

    /**
     * @return the index of this clause.
     */
    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Literal> iterator() {
        return literals.iterator();
    }

    /**
     * this method adds a new literal to the clause. 
     * if the literal is already present in the this clause, it does nothing.
     * 
     * @param l the literal to add to the clause.
     * @throws NullPointerException if the parameter is null
     */
    public void add(Literal l) {
        Objects.requireNonNull(l);

        this.literals.add(l);
    }

    /**
     * this method removes a literal from the clause. 
     * if the literal is not present in the clause, it does nothing.
     * 
     * @param l the literal to be removed from the clause.
     * @throws NullPointerException if the parameter is null.
     */
    public void remove(Literal l) {
        Objects.requireNonNull(l);

        this.literals.remove(l);
    }

    /**
     * 
     * @return the number of literals in the clause.
     */
    public int size() {
        return this.literals.size();
    }
    
    /**
     *
     * @return true, if the Clause is a tautology.
     *         false, otherwise.
     */
    public boolean isTautology() {
        for (Literal l1 : this.literals) {
            for (Literal l2 : this.literals) { 
                if (l1.equals(l2.getOpposite())) return true;
            }
        }

        return false;
    }

    /**
     * unite this clause with the specified clause.
     * 
     * @param c the clause to be united with this clause.
     */
    public void union(Clause c) {
        Objects.requireNonNull(c);

        for (Literal l : c.literals) {
            this.literals.add(l);
        }
    }


    /**
     * 
     * @return true if the clause is empty.
     */
    public boolean isEmpty() {
        return this.literals.isEmpty();
    }

    /**
     * 
     * @param l the literal we want to check in the clause
     * @return true, if the literal is present in the clause.
     * @throws NullPointerException if the literal is null.
     */
    public boolean contains(Literal l) {
        Objects.requireNonNull(l);
        
        return this.literals.contains(l);
    }

    
    @Override
    public String toString() {
        if (this.isEmpty()) return "{}"; //the empty clause represents the contradiction.

        StringBuilder res = new StringBuilder(this.literals.toString());
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
