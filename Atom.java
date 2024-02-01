
/**
 * questa classe rappresenta un letterale dato da
 * un atomo non negato.
 * 
 * Gli oggetti istanziati da questa classe sono immutabili
 */
public class Atom extends Literal {

    //COSTRUTTORI

    /**
     * @param name il nome dell'atomo
     */
    public Atom(String name) {
        super(name);
    }
    
    //METODI

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

    /**
     * 
     * @return una rappresentazione in stringa del letterale, mediante il suo nome
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
