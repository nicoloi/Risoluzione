
/**
 * this class represents a literal that is a non-negated atom. 
 * Objects instantiated by this class are immutable
 */
public class Atom extends Literal {

    //CONSTRUCTORS

    /**
     * @param name the name of the atom
     * @throws NullPointerException if the "name" parameter is null
     * @throws IllegalArgumentException if the "name" parameter is a empty string.
     */
    public Atom(String name) {
        super(name);
    }
    
    //METHODS

    @Override
    public Literal getOpposite() {
        return new NegAtom(this.getName());
    }

    private boolean equals(Atom atm) {
        return this.getName().equals(atm.getName());
    }

    @Override
    public boolean equals(Object obj) {
	    return (obj instanceof Atom) && (this.equals( (Atom) obj));
    }


    @Override
    public String toString() {
        return this.getName();
    }
}
