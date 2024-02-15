import java.util.Objects;

/**
 * This abstract class represents a literal in propositional logic,
 * which can be an atom or a negated atom.
 */
public abstract class Literal {

    //FIELDS
    private final String name; //rappresenta il nome del letterale

    //CONSTRUCTORS

    /**
     * @param name the name of literal
     * @throws NullPointerException if the "name" parameter is null
     * @throws IllegalArgumentException if the "name" parameter is a empty string.
     * 
     * this constructor is used by the subclasses to instantiate the literal, 
     * with the name given as a parameter.
     */
    protected Literal(String name) {
        Objects.requireNonNull(name);

        if (name.equals("")) {
            throw new IllegalArgumentException("il nome non può essere vuoto");
        }
        
        this.name = name;
    }

    //METHODS
    
    /**
     * @return the name of this literal.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the opposite of the literal this. if the literal is an instance of the Atom class 
     * it returns the corresponding NegAtom instance, and vice versa.
     */
    public abstract Literal getOpposite();

    @Override
    public int hashCode() {
        return 1;
    }
}