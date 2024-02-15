import java.util.Set;
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

    public ClauseSet() {
        clauses = new HashSet<>();
    }

    //METHODS

    @Override
    public Iterator<Clause> iterator() {
        return clauses.iterator();
    }

    /**
     * adds a new clause to this set. if the clause is already present, 
     * the method does nothing.
     * 
     * @param c the clause to add to the set
     * @throws NullPointerException if the clause is null.
     */
    public void addClause(Clause c) {
        Objects.requireNonNull(c);
        clauses.add(c);
    }

    /**
     * removes a clause from the set. 
     * If the clause is not present in the set, the method does nothing.
     * 
     * @param c the clause to be removed
     * @throws NullPointerException if the clause is null.
     */
    public void removeClause(Clause c) {
        Objects.requireNonNull(c);
        clauses.remove(c);
    }

    /**
     * 
     * @return the number of clauses in the set
     */
    public int size() {
        return clauses.size();
    }

    /**
     * 
     * @return true, if the set does not contain any clauses.
     */
    public boolean isEmpty() {
        return clauses.isEmpty();
    }

    /**
     * 
     * @param c the clause we want to check in the this clause set.
     * @return true, if c is present in the set.
     * @throws NullPointerException if the clause c is null.
     */
    public boolean contains(Clause c) {
        Objects.requireNonNull(c);
        return clauses.contains(c);
    }

    /**
     * 
     * @param index the index of clause that must be returned
     * @return the clause witch have the index specified as a parameter
     * @return null if the clause set doesn't contains the index.
     */
    public Clause getByIndex(int index) {
        for (Clause c : clauses) {
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

        for (Clause c : clauses) {
            
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
