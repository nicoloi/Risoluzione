import java.util.Set;
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

    public Clause() {
        this.literals = new HashSet<>();
        this.index = count;
        count++;
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
    public void addLiteral(Literal l) {
        Objects.requireNonNull(l);

        literals.add(l);
    }

    /**
     * this method removes a literal from the clause. 
     * if the literal is not present in the clause, it does nothing.
     * 
     * @param l the literal to be removed from the clause.
     * @throws NullPointerException if the parameter is null.
     */
    public void removeLiteral(Literal l) {
        Objects.requireNonNull(l);

        literals.remove(l);
    }

    /**
     * 
     * @return the number of literals in the clause.
     */
    public int size() {
        return literals.size();
    }

    /**
     * 
     * @return true if the clause is empty.
     */
    public boolean isEmpty() {
        return literals.isEmpty();
    }

    /**
     * 
     * @param l the literal we want to check in the clause
     * @return true, if the literal is present in the clause.
     * @throws NullPointerException if the literal is null.
     */
    public boolean contains(Literal l) {
        Objects.requireNonNull(l);
        
        return literals.contains(l);
    }

    
    @Override
    public String toString() {
        if (this.isEmpty()) return "{}"; //the empty clause represents the contradiction.

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
