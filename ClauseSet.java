import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 *  This class represents a formula in conjunctive normal form (CNF), 
 *  using a set of clauses.
 */
public class ClauseSet implements Iterable<Clause> {

    //FIELDS
    private Set<Clause> clauses; //the set containing the clauses

    //CONSTRUCTORS

    /**
     * Constructs a new, empty clause set.
     */
    public ClauseSet() {
        this.clauses = new HashSet<>();
    }

    /**
     * Constructs a new clause set, containing the specified clause.
     * 
     * @param c the clause to be placed into the clause set.
     * @throws NullPointerException - if c is null.
     */
    public ClauseSet(Clause c) {
        this();

        Objects.requireNonNull(c);
        this.clauses.add(c);
    }

    /**
     * Constructs a new clause set containing the clauses in the specified list.
     * 
     * @param list the list of clauses whose elements are to be placed
     *             into this clause set.
     * @throws NullPointerException - if the specified list is null.
     */
    public ClauseSet(List<Clause> list) {
        this.clauses = new HashSet<>(list);
    }

    //METHODS

    @Override
    public Iterator<Clause> iterator() {
        return this.clauses.iterator();
    }

    /**
     * adds a new clause to this set. if the clause is already present, 
     * the method does nothing.
     * 
     * @param c the clause to add to the set
     * @throws NullPointerException if the clause is null.
     */
    public void add(Clause c) {
        Objects.requireNonNull(c);
        this.clauses.add(c);
    }

    /**
     * removes a clause from the clauseset. 
     * If the clause is not present in the set, the method does nothing.
     * 
     * @param c the clause to be removed
     * @throws NullPointerException if the clause in input is null.
     */
    public void remove(Clause c) {
        Objects.requireNonNull(c);
        this.clauses.remove(c);
    }

    /**
     * removes all tautologies from the clauseset
     */
    public void removeTautologies() {
        List<Clause> tautologies = new ArrayList<>();

        for (Clause c : this.clauses) {
            if (c.isTautology()) {
                tautologies.add(c);
            }
        }

        for (Clause taut : tautologies) {
            this.clauses.remove(taut);
        }
    }

    /**
     * merges this clause set with the specified clauseset,
     * applying "union" operation of the set.
     * 
     * @param cset the clauseset to be merged with this clause set.
     */
    public void union(ClauseSet cset) {
        Objects.requireNonNull(cset);

        for (Clause c : cset.clauses) {
            this.clauses.add(c);
        }
    }

    /**
     * 
     * @return the number of clauses in the set
     */
    public int size() {
        return this.clauses.size();
    }

    /**
     * 
     * @return true, if the set does not contain any clauses.
     */
    public boolean isEmpty() {
        return this.clauses.isEmpty();
    }

    /**
     * 
     * @param c the clause we want to check in the this clause set.
     * @return true, if c is present in the set.
     * @throws NullPointerException if the clause c is null.
     */
    public boolean contains(Clause c) {
        Objects.requireNonNull(c);
        return this.clauses.contains(c);
    }

    /**
     * 
     * @param index the index of clause that must be returned
     * @return the clause witch have the index specified as a parameter
     * @return null if the clause set doesn't contains the index.
     */
    public Clause getByIndex(int index) {
        for (Clause c : this.clauses) {
            if (c.getIndex() == index) {
                return c;
            }
        }

        return null;
    }

    
    @Override
    public String toString() {
        if (this.isEmpty()) return "empty set";

        StringBuilder res = new StringBuilder();
        boolean first = true;

        for (Clause c : this.clauses) {
            
            if (first) {
                res.append(c.toString());
            } else {
                res.append("; " + c.toString());
            }

            first = false;
        }
        return res.toString();
    }
}
