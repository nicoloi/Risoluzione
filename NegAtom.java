
/**
 * this class represents a literal that is a negated atom.
 * Objects instantiated by this class are immutable
 */
public class NegAtom extends Literal{

    //CONSTRUCTORS

    /**
     * @param name the name of negated atom.
     */
    public NegAtom(String name) {
        super(name);
    }
    
    //METHODS

    @Override
    public Literal getOpposite() {
        return new Atom(this.getName());
    }

    private boolean equals(NegAtom natm) {
        return this.getName().equals(natm.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof NegAtom) && (this.equals( (NegAtom) obj ));
    }

    @Override
    public String toString() {
        return "~" + this.getName();
    }
}
